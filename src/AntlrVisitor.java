import antlr.*;
import static antlr.WaccParser.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import javax.swing.text.html.parser.Parser;

public class AntlrVisitor extends WaccParserBaseVisitor<Void>{

    SymbolTable st = new SymbolTable();

    // toggles if output is written to stdout
    public boolean verbose = false;

    /////////// UTILITY METHODS ////////////

    private boolean matchGrammar(ParserRuleContext ctx, int[] pattern) {
        for(int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree child = ctx.getChild(i);
            if(child instanceof ParserRuleContext && ((ParserRuleContext) child).getRuleIndex() == pattern[i]) {

            } else if (child instanceof TerminalNode && ctx.getToken(pattern[i], 0) == child ) {

            } else {
                return false;
            }
        }
        return true;
    }

    private boolean isIntExpr(ParserRuleContext ctx) {
        return false;
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
        outputln("Visited declaration " + ctx.getText());
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
            ctx.accept(this);
        if(matchGrammar(ctx, new int[]{RULE_arrayElem}))
            ctx.accept(this);
        if(matchGrammar(ctx, new int[]{RULE_pairElem}))
            ctx.accept(this);
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
            visit(ctx.getChild(2));
            visit(ctx.getChild(4));
        if(matchGrammar(ctx, new int[]{RULE_pairElem}))
            visit(ctx.getChild(0));
//        if(matchGrammar(ctx, new int[]{CALL, RULE_ident, OPEN_PARENTHESES, argList?, CLOSE_PARENTHESES})
        return null;
    }

    ////////// Visit arg list //////////

    public Void visitArgList(ArgListContext ctx) {
        for (int i = 0; i < ctx.getChildCount(); i += 2) {
            visit(ctx.getChild(0));
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
        if(matchGrammar(ctx, new int[]{RULE_intLiter}))
            visitExprIntLiter(ctx);
        if(matchGrammar(ctx, new int[]{RULE_boolLiter}))
            visitExprBoolLiter(ctx);
        if(matchGrammar(ctx, new int[]{RULE_charLiter}))
            visitExprCharLiter(ctx);
        if(matchGrammar(ctx, new int[]{RULE_strLiter}))
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
        if (i > Integer.MAX_VALUE || i < Integer.MIN_VALUE) {
            // overflow exception
        }
    }

    private void visitExprBoolLiter(ExprContext ctx) { outputln("Visited bool literal"); }

    private void visitExprCharLiter(ExprContext ctx) { outputln("Visited char literal"); }

    private void visitExprStringLiter(ExprContext ctx) { outputln("Visited string literal"); }

    private void visitExprPairLiter(ExprContext ctx) { outputln("Visited pair literal"); }

    private void visitExprIdent(ParserRuleContext ctx) { outputln("Visited ident"); }

    private void visitExprArrayElem(ParserRuleContext ctx) {
        outputln("Visited array elem");
        visitChildren(ctx);
        // check expr is integer
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