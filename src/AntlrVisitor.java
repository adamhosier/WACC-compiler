import antlr.*;
import static antlr.WaccParser.*;

import com.sun.xml.internal.rngom.digested.DDataPattern;
import org.antlr.v4.runtime.LexerNoViableAltException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

public class AntlrVisitor extends WaccParserBaseVisitor<Void>{

    private SymbolTable st;
    private WaccVisitorErrorHandler errorHandler;

    // toggles if output is written to stdout
    public boolean verbose = false;

    public AntlrVisitor() {
        errorHandler = new WaccVisitorErrorHandler();
        st = new SymbolTable(errorHandler);
    }

    /////////// UTILITY METHODS ////////////

    /*
     * Given a context, and list of token or rule identifiers, check if the contexts children matches the format of
     *  the given list.
     */
    private boolean matchGrammar(ParserRuleContext ctx, int[] pattern) {
        if(ctx.getChildCount() != pattern.length) return false;
        for(int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree child = ctx.getChild(i);
            if(pattern[i] == WaccType.ALL.getId()) continue;
            if(!(child instanceof ParserRuleContext && ((ParserRuleContext) child).getRuleIndex() == pattern[i]) &&
               !(child instanceof TerminalNode      && ctx.getToken(pattern[i], 0) == child )) {
                return false;
            }
        }
        return true;
    }

    private WaccType getType(ParserRuleContext ctx) {
        outputln(ruleNames[ctx.getRuleIndex()] + ": " + ctx.getText());
        if(matchGrammar(ctx, new int[]{RULE_baseType})) {
            ctx = (ParserRuleContext) ctx.getChild(0);
            return new WaccType(((TerminalNode) ctx.getChild(0)).getSymbol().getType());
        }
        if(matchGrammar(ctx, new int[]{RULE_expr})) {
           return getType((ParserRuleContext) ctx.getChild(0));
        }
        if(matchGrammar(ctx, new int[]{INT_LIT})) return new WaccType(INT);
        if(matchGrammar(ctx, new int[]{BOOL_LIT})) return new WaccType(BOOL);
        if(matchGrammar(ctx, new int[]{CHAR_LIT})) return new WaccType(CHAR);
        if(matchGrammar(ctx, new int[]{STRING_LIT})) return new WaccType(STRING);
        if(matchGrammar(ctx, new int[]{RULE_pairLiter})) return new WaccType(PAIR);
        if(matchGrammar(ctx, new int[]{RULE_ident})) {
            return st.lookupType(ctx.getChild(0));
        }
        if(matchGrammar(ctx, new int[]{RULE_arrayElem})) {
            //it is not the job of getType to check that the index expression is valid
            return st.lookupType(ctx.getChild(0));
        }
        if(matchGrammar(ctx, new int[]{RULE_unaryOper, RULE_expr})) {
            return getType((ParserRuleContext) ctx.getChild(1));
        }
        if(matchGrammar(ctx, new int[]{RULE_expr, RULE_binaryOper, RULE_expr})) {
            // checks types match and returns type of result
            WaccType t1 = getType((ParserRuleContext) ctx.getChild(0));
            WaccType t2 = getType((ParserRuleContext) ctx.getChild(2));
            int op = ((TerminalNode) ctx.getChild(1).getChild(0)).getSymbol().getType();
            return t1.equals(t2) ? WaccType.fromBinaryOperator(op) : WaccType.INVALID;
        }
        if(matchGrammar(ctx, new int[]{OPEN_PARENTHESES, RULE_expr, CLOSE_PARENTHESES})) {
            return getType((ParserRuleContext) ctx.getChild(1));
        }
        if(matchGrammar(ctx, new int[]{RULE_arrayLiter})) {
            ctx = (ParserRuleContext) ctx.getChild(0);
            if(ctx.getChildCount() == 2) {
                return WaccType.ALL;
            } else {
                WaccType type = getType((ParserRuleContext) ctx.getChild(1));
                for(int i = 2; i < ctx.getChildCount() - 1; i += 2) {
                    if(!getType((ParserRuleContext) ctx.getChild(i)).equals(type)) {
                        return WaccType.INVALID;
                    }
                }
                return type;
            }
        }
        if(matchGrammar(ctx, new int[]{NEW_PAIR, OPEN_PARENTHESES, RULE_expr, COMMA, RULE_expr, CLOSE_PARENTHESES})) {
            WaccType type = new WaccType(getType((ParserRuleContext) ctx.getChild(2)).getId(),
                                         getType((ParserRuleContext) ctx.getChild(4)).getId());
        }
        if(matchGrammar(ctx, new int[]{RULE_pairElem})) {
            ParserRuleContext ctxp = (ParserRuleContext) ctx.getChild(0);
            WaccType t = getType((ParserRuleContext) ctxp.getChild(1));
            if(matchGrammar(ctxp, new int[]{FST, WaccType.ALL.getId()})) {
                return new WaccType(t.getFstId());
            } else {
                return new WaccType(t.getSndId());
            }
        }
        if(matchGrammar(ctx, new int[]{RULE_funcCall})) {
            return st.lookupType(ctx.getChild(0).getChild(1));
        }
        if(matchGrammar(ctx, new int[]{RULE_ident})) {
            return st.lookupType(ctx.getChild(0).getText());
        }
        if(matchGrammar(ctx, new int[]{RULE_arrayElem})){
            return getType((ParserRuleContext) ctx.getChild(0).getChild(0));
        }
        return WaccType.INVALID;
    }

    private WaccType objToType(Object o) {
        if(o instanceof ParserRuleContext) return getType((ParserRuleContext) o);
        if(o instanceof Integer) return new WaccType((Integer) o);
        if(o instanceof WaccType) return (WaccType) o;
        return WaccType.INVALID;
    }

    private boolean typesMatch(Object lhs, Object rhs) {
        WaccType typel = objToType(lhs);
        WaccType typer = objToType(rhs);
        return typel.isValid() && typer.isValid() && lhs.equals(rhs);
    }

    private String getTypeString(ParserRuleContext ctx) {
        return getType(ctx).toString();
    }

    /////////// VISITOR METHODS ////////////

    public Void visitProg(ProgContext ctx) {
        outputln("Visited main program entry");
        return visitChildren(ctx);
    }

    public Void visitFunc(FuncContext ctx) {
        output("Function " + ctx.ident().getText());
        return visitChildren(ctx);
    }

    public Void visitStat(StatContext ctx) {
        if(matchGrammar(ctx, new int[]{SKIP}))
            return null;
        if(matchGrammar(ctx, new int[]{RULE_type, RULE_ident, EQUALS, RULE_assignRhs}))
            visitStatDeclaration(ctx);
        if(matchGrammar(ctx, new int[]{RULE_assignLhs, EQUALS, RULE_assignRhs}))
            visitStatAssignment(ctx);
        if(matchGrammar(ctx, new int[]{READ, RULE_assignLhs}))
            visitStatRead(ctx);
        if(matchGrammar(ctx, new int[]{FREE, RULE_expr}))
            visitStatFree(ctx);
        if(matchGrammar(ctx, new int[]{RETURN, RULE_expr}))
            visitStatReturn(ctx);
        if(matchGrammar(ctx, new int[]{EXIT, RULE_expr}))
            visitStatExit(ctx);
        if(matchGrammar(ctx, new int[]{PRINT, RULE_expr}))
            visitStatPrint(ctx);
        if(matchGrammar(ctx, new int[]{PRINTLN, RULE_expr}))
            visitStatPrintln(ctx);
        if(matchGrammar(ctx, new int[]{IF, RULE_expr, THEN, RULE_stat, ELSE, RULE_stat, FI}))
            visitStatIf(ctx);
        if(matchGrammar(ctx, new int[]{WHILE, RULE_expr, DO, RULE_stat, DONE}))
            visitStatWhile(ctx);
        if(matchGrammar(ctx, new int[]{BEGIN, RULE_stat, END}))
            visitStatNewScope(ctx);
        if(matchGrammar(ctx, new int[]{RULE_stat, SEMICOLON, RULE_stat}))
            visitStatComposition(ctx);
        return null;
    }

    private void visitStatDeclaration(StatContext ctx) {
        outputln("Visited declaration");
        outputln("Declaring var " + ctx.ident().getText());

        WaccType exp = getType(ctx.type());
        WaccType acc = getType(ctx.assignRhs());

        outputln("  Expected type: " + exp);
        outputln("  Actual type: " + acc);

        if(!typesMatch(ctx.type(), ctx.assignRhs())) {
            errorHandler.typeMismatch(ctx, exp, acc);
        }

        st.addVariable(ctx.ident().getText(), acc);
    }

    private void visitStatAssignment(StatContext ctx) {
        outputln("Visited assigment");
        outputln("  Assigning ");

        String ident = ctx.assignLhs().getChild(0).getText();

        if(!st.isDeclared(ident)) {
            errorHandler.symbolNotFound(ctx, ident);
        }

        WaccType exp = st.lookupType(ident);
        WaccType acc = getType(ctx.assignRhs());
        if(!typesMatch(exp, ctx.assignRhs())) {
            errorHandler.typeMismatch(ctx, exp, acc);
        }
    }

    private void visitStatRead(StatContext ctx) {
        outputln("Visited read");
    }

    private void visitStatFree(StatContext ctx) {
        outputln("Visited free");
        visit(ctx.children.get(1));
    }

    private void visitStatReturn(StatContext ctx) {
        outputln("Visited return");
    }

    private void visitStatExit(StatContext ctx) {
        outputln("Visited exit");
    }

    private void visitStatPrint(StatContext ctx) {
        outputln("Visited print");
        visit(ctx.children.get(1));
    }

    private void visitStatPrintln(StatContext ctx) {
        outputln("Visited println");
        visit(ctx.children.get(1));
    }

    private void visitStatIf(StatContext ctx) {
        outputln("Visited if");
    }

    private void visitStatWhile(StatContext ctx) {
        outputln("Visited while");
    }

    private void visitStatNewScope(StatContext ctx) {
        outputln("Scope started (BEGIN)");
        st.newScope();
        visitChildren(ctx);
        outputln("Scope ended (END)");
        st.endScope();
    }

    private void visitStatComposition(StatContext ctx) {
        visit(ctx.getChild(0));
        visit(ctx.getChild(2));
    }

    public Void visitIdent(IdentContext ctx) {
        outputln("Visited ident");
        WaccType ident = st.lookupType(ctx.getText());
        if (ident == null) {
            errorHandler.identNotFound(ctx);
        }
        return null;
    }

    public Void visitParamList(ParamListContext ctx) {
        output("(");
        for(int i = 0; i < ctx.getChildCount(); i++){
            if(i != 0) output(", ");
            visit(ctx.children.get(i));
        }
        outputln(")");
        return null;
    }

    public Void visitParam(ParamContext ctx) {
        output(ctx.type().getText() + " " + ctx.ident().getText());
        return null;
    }


    ////////// Visit assignLhs //////////

    public Void visitAssignLhs(AssignLhsContext ctx) {
        if(matchGrammar(ctx, new int[]{RULE_ident}))
            visit(ctx.getChild(0));
        if(matchGrammar(ctx, new int[]{RULE_arrayElem}))
            visit(ctx.getChild(0));
        if(matchGrammar(ctx, new int[]{RULE_pairElem}))
            visit(ctx.getChild(0));
        return null;
    }

    ////////// Visit assignRhs //////////

    public Void visitAssignRhs(AssignRhsContext ctx) {
        if(matchGrammar(ctx, new int[]{RULE_expr}))
            ctx.getChild(0).accept(this);
        if(matchGrammar(ctx, new int[]{RULE_arrayLiter}))
            ctx.getChild(0).accept(this);
        if(matchGrammar(ctx, new int[]{NEW_PAIR, OPEN_PARENTHESES, RULE_expr,
                COMMA, RULE_expr, CLOSE_PARENTHESES}))
            ctx.getChild(2).accept(this);
            ctx.getChild(4).accept(this);
        if(matchGrammar(ctx, new int[]{RULE_pairElem}))
            visit(ctx.getChild(0));
        if(matchGrammar(ctx, new int[]{RULE_funcCall}))
            visitFuncCall(ctx);
        return null;
    }

    private void visitFuncCall(AssignRhsContext ctx) {
        IdentContext ident = (IdentContext) ctx.getChild(1);
        visit(ident);
        // check that an argList exists
        if (ctx.getChildCount() > 4) {
            visitArgList((ArgListContext) ctx.getChild(3), ident.getText()); // visit arg list
        }
    }

    private void visitArgList(ArgListContext ctx, String ident) {
        ParamListContext params = st.getParamList(ident);
        // check that number of args passed is correct
        if (ctx.getChildCount() != params.getChildCount()) {
            errorHandler.invalidNumberOfArgs(ctx, ident);
        }
        // check each param matches type
        WaccType argType;
        WaccType paramType;
        for (int i = 0; i < ctx.getChildCount(); i += 2) {
            visit(ctx.getChild(i));
            argType = getType((ParserRuleContext) ctx.getChild(i));
            paramType = getType((ParserRuleContext) params.getChild(i));
            if (!typesMatch(argType, paramType)) {
                errorHandler.typeMismatch((ParserRuleContext) ctx.getChild(i), paramType, argType);
            }
        }
    }

    ////////// Visit Pair Elem //////////

    public Void visitPairElem(PairElemContext ctx) {
        if(matchGrammar(ctx, new int[]{FST, RULE_expr})
                || matchGrammar(ctx, new int[]{SND, RULE_expr}))
            visit(ctx.getChild(1));
        return null;
    }

    ////////// Visit array elem //////////

    public Void visitArrayElem(ArrayElemContext ctx) {
        outputln("Visited array elem");
        IdentContext ctxi = (IdentContext) ctx.getChild(0);
        // Check identifier exists
        visit(ctxi);

        int[] arrayLengths = st.getArrayLength(ctxi.getText());
        int index = 0;
//        int index = Integer.parseInt(ctx.getChild(2).getText());
//        // ONLY CHECKS IF LEADING DIMENSION IN BOUNDS
//        if (index >= arrayLength || index < 0) {
//            errorHandler.arrayOutOfBounds((ParserRuleContext) ctx.getChild(2), index);
//        }

        // Check that each index expression is integer
        for (int i = 2; i < ctx.getChildCount() - 1; i += 3) {
            ParserRuleContext ctxExpr = (ParserRuleContext) ctx.getChild(i);
            int index = Integer.parseInt(ctxExpr.getText());
            if (!typesMatch(INT_LIT, ctxExpr)) {
                errorHandler.typeMismatch(ctxExpr, new WaccType(INT_LIT), getType(ctxExpr));
            }
            if (index >= arrayLengths[index] || index < 0) {
                errorHandler.arrayOutOfBounds((ParserRuleContext) ctx.getChild(2), index);
            }
            index++;
        }
        return null;
    }

    ////////// Visit array liter //////////

    public Void visitArrayLiter(ArrayLiterContext ctx) {
        int childCount = ctx.getChildCount();
        if (childCount > 2) {
            WaccType type = getType((ParserRuleContext) ctx.getChild(2));
            for (int i = 4; i < childCount - 1; i += 2) {
                ExprContext ctxExpr = (ExprContext) ctx.getChild(i);
                WaccType nextType = getType(ctxExpr);
                if (!typesMatch(type, nextType)) {
                    errorHandler.incompatibleArrayElemTypes(ctxExpr);
                }
            }
        }
        return null;
    }

    ////////// Visit Expression //////////

    public Void visitExpr(ExprContext ctx) {
        if(matchGrammar(ctx, new int[]{INT_LIT}))
            visitExprIntLiter(ctx);
        if(matchGrammar(ctx, new int[]{BOOL_LIT}))
            visitExprBoolLiter(ctx);
        if(matchGrammar(ctx, new int[]{CHAR_LIT}))
            visitExprCharLiter(ctx);
        if(matchGrammar(ctx, new int[]{STRING_LIT}))
            visitExprStringLiter(ctx);
        if(matchGrammar(ctx, new int[]{RULE_pairLiter}))
            visitExprPairLiter(ctx);
        if(matchGrammar(ctx, new int[]{RULE_ident})
                || matchGrammar(ctx, new int[]{RULE_arrayElem}))
            visit(ctx.getChild(0));
        if(matchGrammar(ctx, new int[]{RULE_unaryOper, RULE_expr}))
            visitExprUnaryOper(ctx);
        if(matchGrammar(ctx, new int[]{RULE_expr, RULE_binaryOper, RULE_expr}))
            visitExprBinaryOper(ctx);
        if(matchGrammar(ctx, new int[]{OPEN_PARENTHESES, RULE_expr, CLOSE_PARENTHESES}))
            visitExprParentheses(ctx);
        return null;
    }

    private void visitExprIntLiter(ExprContext ctx) {
        outputln("Visited int literal");
        int i = Integer.parseInt(ctx.getText());
        System.out.println(i);
        if (i < Integer.MIN_VALUE || i > Integer.MAX_VALUE) {
            errorHandler.integerOverflow(ctx);
        }
    }

    private void visitExprBoolLiter(ExprContext ctx) { outputln("Visited bool literal"); }

    private void visitExprCharLiter(ExprContext ctx) {
        outputln("Visited char literal");
        char c = ctx.getText().charAt(0);
        if (c < 0 || c > 255) {
            errorHandler.characterOverflow(ctx);
        }
    }

    private void visitExprStringLiter(ExprContext ctx) { outputln("Visited string literal"); }

    private void visitExprPairLiter(ExprContext ctx) { outputln("Visited pair literal"); }

//    private void visitExprIdent(ParserRuleContext ctx) {
//        outputln("Visited ident");
//        WaccType ident = st.lookupType(ctx.getChild(0).getText());
//        if (ident == null) {
//            errorHandler.identNotFound((ParserRuleContext) ctx.getChild(0));
//        }
//    }

//    private void visitExprArrayElem(ParserRuleContext ctx) {
//        outputln("Visited array elem");
//        ParserRuleContext ctxi = (ParserRuleContext) ctx.getChild(0);
//        // Check identifier exists
//        visitExprIdent(ctxi);
//
//        int arrayLength = st.getArrayLength(ctxi.getText());
//        int index = Integer.parseInt(ctx.getChild(2).getText());
//        // ONLY CHECKS IF LEADING DIMENSION IN BOUNDS
//        if (index >= arrayLength || index < 0) {
//            errorHandler.arrayOutOfBounds((ParserRuleContext) ctx.getChild(2), index);
//        }
//
//        // Check that each index expression is integer
//        for (int i = 2; i < ctx.getChildCount() - 1; i += 3) {
//            ParserRuleContext ctxExpr = (ParserRuleContext) ctx.getChild(i);
//            if (!typesMatch(INT_LIT, ctxExpr)) {
//                errorHandler.typeMismatch(ctxExpr, new WaccType(INT_LIT), getType(ctxExpr));
//            }
//        }
//    }

    private void visitExprUnaryOper(ExprContext ctx) {
        outputln("Visited unary operation");
        ParserRuleContext ctxOp= (ParserRuleContext) ctx.getChild(0);
        ParserRuleContext ctxExpr = (ParserRuleContext) ctx.getChild(1);
        if (typesMatch(NOT, ctxOp)) {
            evalArgType(BOOL_LIT, ctxExpr);
        }
        if (typesMatch(MINUS, ctxOp) || typesMatch(CHR, ctxOp)) {
            evalArgType(INT_LIT, ctxExpr);
        }
        if (typesMatch(LEN, ctxOp))
            evalArgType(IDENT, ctxExpr);
        if (typesMatch(ORD, ctxOp)) {
            evalArgType(CHAR_LIT, ctxExpr);
        }
        visit(ctxExpr);
    }

    private void visitExprBinaryOper(ExprContext ctx) {
        outputln("Visited binary operation");
        ParserRuleContext ctxOp= (ParserRuleContext) ctx.getChild(1);
        ParserRuleContext ctxExpr1 = (ParserRuleContext) ctx.getChild(0);
        ParserRuleContext ctxExpr2 = (ParserRuleContext) ctx.getChild(2);

        if (typesMatch(MULT, ctxOp)
                || typesMatch(DIV, ctxOp)
                || typesMatch(MOD, ctxOp)
                || typesMatch(PLUS, ctxOp)
                || typesMatch(MINUS, ctxOp)
                || typesMatch(GREATER_THAN, ctxOp)
                || typesMatch(GREATER_THAN_EQ, ctxOp)
                || typesMatch(LESS_THAN, ctxOp)
                || typesMatch(LESS_THAN_EQ, ctxOp)
                || typesMatch(EQ, ctxOp)
                || typesMatch(NOT_EQ, ctxOp)) {
            evalArgType(INT_LIT, ctxExpr1);
            evalArgType(INT_LIT, ctxExpr2);
        }
        if (typesMatch(AND, ctxOp) || typesMatch(OR, ctxOp)) {
            evalArgType(BOOL_LIT, ctxExpr1);
            evalArgType(BOOL_LIT, ctxExpr2);
        }

        visit(ctx.getChild(0));
        visit(ctx.getChild(2));
    }

    private void evalArgType(int expectedTypeToken, ParserRuleContext ctxExpr) {
        if (!typesMatch(expectedTypeToken, ctxExpr)) {
            errorHandler.typeMismatch(ctxExpr, new WaccType(expectedTypeToken),
                                        getType(ctxExpr));
        }
    }

    private void visitExprParentheses(ExprContext ctx) {
        outputln("Visited expr parentheses");
        visit(ctx.getChild(1));
    }

    ////////// OUTPUT FUNCTIONS ////////////

    public void output(String s) {
        if(verbose) System.out.print(s);
    }

    public void outputln(String s) {
        if(verbose) System.out.println(s);
    }
}