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

    private WaccType getType(ParserRuleContext ctx) {
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
    }

    private void visitStatReturn(StatContext ctx) {
        outputln("Visited return");
    }

    private void visitStatExit(StatContext ctx) {
        outputln("Visited exit");
    }

    private void visitStatPrint(StatContext ctx) {
        outputln("Visited print");
    }

    private void visitStatPrintln(StatContext ctx) {
        outputln("Visited println");
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

    ////////// OUTPUT FUNCTIONS ////////////

    public void output(String s) {
        if(verbose) System.out.print(s);
    }

    public void outputln(String s) {
        if(verbose) System.out.println(s);
    }
}