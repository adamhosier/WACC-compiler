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

    /////////// VISITOR METHODS ////////////

    public Void visitProg(ProgContext ctx) {
        outputln("V isited main program entry");
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