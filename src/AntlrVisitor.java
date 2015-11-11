import antlr.*;
import static antlr.WaccParser.*;

import org.antlr.v4.runtime.LexerNoViableAltException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import javax.swing.text.html.parser.Parser;

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

    private boolean isIntExpr(ParserRuleContext ctx) {
        return false;
    }

    private WaccType getType(ParserRuleContext ctx) {
        if(ctx instanceof TypeContext) {
            return new WaccType(ctx.getRuleIndex());
        }
        if(matchGrammar(ctx, new int[]{RULE_expr})) {
            ctx = (ParserRuleContext) ctx.getChild(0);
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
                return t1.equals(t2) ? t1 : WaccType.INVALID;
            }
            if(matchGrammar(ctx, new int[]{OPEN_PARENTHESES, RULE_expr, CLOSE_PARENTHESES})) {
                return getType((ParserRuleContext) ctx.getChild(1));
            }
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
        if(matchGrammar(ctx, new int[]{RULE_pairElem})) {
            ParserRuleContext ctxp = (ParserRuleContext) ctx.getChild(0);
            WaccType t = getType((ParserRuleContext) ctxp.getChild(1));
            if(matchGrammar(ctxp, new int[]{FST, WaccType.ALL.getId()})) {
                return new WaccType(t.getFstId());
            } else {
                return new WaccType(t.getSndId());
            }
        }
        return WaccType.INVALID;
    }

    private boolean typesMatch(Object lhs, ParserRuleContext rhs) {
        if(lhs instanceof ParserRuleContext) {
            return getType((ParserRuleContext) lhs).equals(getType(rhs));
        } else if(lhs instanceof Integer) {
            return getType(rhs).equals(lhs);
        } else {
            return false;
        }
    }

    private String getTypeString(ParserRuleContext ctx) {
        WaccType t = getType(ctx);
        if(t.isPair) {
            return "pair(" + tokenNames[t.getFstId()] + ", " + tokenNames[t.getSndId()] + ")";
        } else {
            return tokenNames[getType(ctx).getId()];
        }
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
        outputln("  Expected type: " + getType(ctx.type()));
        outputln("  Actual type: " + getType(ctx.assignRhs()));

        if(!typesMatch(ctx.type(), ctx.assignRhs())) {
            errorHandler.typeMismatch(ctx, getType(ctx.type()).toString(), getType(ctx.assignRhs()).toString());
        }
        st.addVariable(ctx.ident().getText(), ctx.type().getRuleIndex());
    }

    private void visitStatAssignment(StatContext ctx) {
        outputln("Visited assigment");
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
            visitExprIdent(ctx);
        if(matchGrammar(ctx, new int[]{RULE_arrayElem}))
            visitExprArrayElem(ctx);
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
//        if(matchGrammar(ctx, new int[]{CALL, RULE_ident, OPEN_PARENTHESES, argList?, CLOSE_PARENTHESES})
        return null;
    }

    ////////// Visit arg list //////////

    public Void visitArgList(ArgListContext ctx) {
        for (int i = 0; i < ctx.getChildCount(); i += 2) {
            ctx.getChild(i).accept(this);
        }
        return null;
    }

    ////////// Visit Pair Elem //////////

    public Void visitPairElem(PairElemContext ctx) {
        if(matchGrammar(ctx, new int[]{FST, RULE_expr}))
            ctx.getChild(1).accept(this);
        if(matchGrammar(ctx, new int[]{SND, RULE_expr}))
            ctx.getChild(1).accept(this);
        return null;
    }

    ////////// Visit array elem //////////

    public Void visitArrayElem(ArrayElemContext ctx) {
        return null;
    }

    ////////// Visit array liter //////////

    public Void visitArrayLiter(ArrayLiterContext ctx) {
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
        if(matchGrammar(ctx, new int[]{RULE_ident}))
            visitExprIdent(ctx);
        if(matchGrammar(ctx, new int[]{RULE_arrayElem}))
            visitExprArrayElem(ctx);
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

    private void visitExprIdent(ParserRuleContext ctx) {
        outputln("Visited ident");
        WaccType ident = st.lookupType(ctx.getChild(0).getText());
        if (ident == null) {
            errorHandler.identNotFound((ParserRuleContext) ctx.getChild(0));
        }
    }

    private void visitExprArrayElem(ParserRuleContext ctx) {
        outputln("Visited array elem");
        ParserRuleContext ctxi = (ParserRuleContext) ctx.getChild(0);
        // Check identifier exists
        visitExprIdent(ctxi);

        int arrayLength = st.getArrayLength(ctxi.getText());
        int index = Integer.parseInt(ctx.getChild(2).getText());
        // ONLY CHECKS IF LEADING DIMENSION IN BOUNDS
        if (index >= arrayLength) {
            errorHandler.arrayOutOfBounds((ParserRuleContext) ctx.getChild(2), index);
        }

        // Check that each index expression is integer
        for (int i = 2; i < ctx.getChildCount() - 1; i += 3) {
            ParserRuleContext ctxExpr = (ParserRuleContext) ctx.getChild(i);
            if (!typesMatch(INT_LIT, ctxExpr)) {
                errorHandler.typeMismatch(ctxExpr, tokenNames[INT_LIT], getTypeString(ctxExpr));
            }
        }
    }

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
            errorHandler.typeMismatch(ctxExpr, tokenNames[expectedTypeToken],
                                        getTypeString(ctxExpr));
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