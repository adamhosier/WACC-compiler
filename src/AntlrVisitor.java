import antlr.*;
import static antlr.WaccParser.*;
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
        if(matchGrammar(ctx, new int[]{RULE_expr})) {
            ctx = (ParserRuleContext) ctx.getChild(0);
            if(matchGrammar(ctx, new int[]{INT_LIT})) return new WaccType(INT);
            if(matchGrammar(ctx, new int[]{BOOL_LIT})) return new WaccType(BOOL);
            if(matchGrammar(ctx, new int[]{CHAR_LIT})) return new WaccType(CHAR);
            if(matchGrammar(ctx, new int[]{STRING_LIT})) return new WaccType(STRING);
            if(matchGrammar(ctx, new int[]{RULE_pairLiter})) return new WaccType(PAIR);
            if(matchGrammar(ctx, new int[]{RULE_ident})) {
                return new WaccType(st.lookupType(ctx.getChild(0)).getRuleIndex());
            }
            if(matchGrammar(ctx, new int[]{RULE_arrayElem})) {
                //it is not the job of getType to check that the index expression is valid
                return new WaccType(st.lookupType(ctx.getChild(0)).getRuleIndex());
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
            return new WaccType(st.lookupType(ctx.getChild(0).getChild(1)).getRuleIndex());
        }
        if(matchGrammar(ctx, new int[]{RULE_ident})) {
            return new WaccType(st.lookupType(ctx.getChild(0).getText()).getRuleIndex());
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

    private String getTypeString(ParserRuleContext ctx) {
        WaccType t = getType(ctx);
        if(t.isPair) {
            return "pair(" + t.getFstId() + ", " + t.getSndId() + ")";
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
        outputln("Declaring var " + ctx.ident().getText() + " as type " + ctx.type().getText());
        errorHandler.typeMismatch(ctx, ctx.type().getText(), getTypeString(ctx.assignRhs()));
        st.addVariable(ctx.ident().getText(), ctx.type());
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
        st.lookupType(ctx.getChild(0).getText());
        // throw error if ident not found
    }

    private void visitExprArrayElem(ParserRuleContext ctx) {
        // only 2d array covered
        outputln("Visited array elem");
        visitExprIdent((ParserRuleContext) ctx.getChild(0));
        for (int i = 2; i < ctx.getChildCount() - 1; i += 3) {
            String type = getTypeString((ParserRuleContext) ctx.getChild(i));
            if (!type.equals(tokenNames[INT])) {
                errorHandler.typeMismatch(ctx, tokenNames[INT], type);
            }
            // check expr is integer and in bounds(using sym tab)
        }
    }

    private void visitExprUnaryOper(ExprContext ctx) {
        outputln("Visited unary operation");
        visit(ctx.children.get(1));
    }

    private void visitExprBinaryOper(ExprContext ctx) {
        outputln("Visited binary operation");
        // check types of children
        visit(ctx.children.get(0));
        visit(ctx.children.get(2));
    }

    private void visitExprParentheses(ExprContext ctx) {
        outputln("Visited expr parentheses");
        visit(ctx.children.get(1));
    }

    ////////// OUTPUT FUNCTIONS ////////////

    public void output(String s) {
        if(verbose) System.out.print(s);
    }

    public void outputln(String s) {
        if(verbose) System.out.println(s);
    }
}