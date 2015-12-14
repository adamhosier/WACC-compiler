package util;

import antlr.WaccParser;
import antlr.WaccParserBaseVisitor;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import static antlr.WaccParser.*;

public class WeightAnalyser extends WaccParserBaseVisitor<Integer> {

    @Override
    public Integer visitChildren(RuleNode node) {
        int result = 0;
        for(int i = 0; i < node.getChildCount(); i++) {
            result += visit(node.getChild(i));
        }
        return result;
    }

    @Override
    public Integer visitTerminal(TerminalNode node) {
        return 0;
    }

    @Override
    public Integer visitVarAssignment(VarAssignmentContext ctx) {
        return 1;
    }

    @Override
    public Integer visitPairElem(PairElemContext ctx) {
        return 1;
    }

    @Override
    public Integer visitArrayElem(ArrayElemContext ctx) {
        return 1;
    }

    @Override
    public Integer visitExpr(ExprContext ctx) {
        if(ctx.boolBinaryOper() != null || ctx.otherBinaryOper() != null) {
            return visitChildren(ctx);
        } else {
            return 1;
        }
    }
}
