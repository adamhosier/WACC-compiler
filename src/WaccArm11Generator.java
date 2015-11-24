import antlr.WaccParserBaseVisitor;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import util.Arm11Program;
import util.SymbolTable;

import java.util.PriorityQueue;

import static antlr.WaccParser.*;

public class WaccArm11Generator extends WaccParserBaseVisitor<Void> {

    private Arm11Program state = new Arm11Program();
    private SymbolTable st;

    public String generate() {
        return state.toCode();
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.st = symbolTable;
    }

    /*
     * Adds each child of [tree] to a priority queue, with priority given by the childs weight
     * Then visits each of these children starting at the one which uses the most registers for optimal register usage
     */
    @Override
    public Void visitChildren(RuleNode tree) {
        PriorityQueue<ParseTree> children = new PriorityQueue<>(this::compareWeights);
        for(int i = 0; i < tree.getChildCount(); i++) {
            children.add(tree.getChild(i));
        }
        children.forEach(this::visit);
        return null;
    }

    /*
     * Compares the amount of registers that two parsetrees will use in code generation
     * Returns negative if p1 uses more than p2, else positive
     */
    public int compareWeights(ParseTree p1, ParseTree p2) {
        int w1 = weight(p1);
        int w2 = weight(p2);
        return w1 - w2;
    }

    /*
     * TODO
     * Calculates how many registers [tree] will use in code generation
     */
    public int weight(ParseTree tree) {
        return 0;
    }

    ////////////// VISITOR METHODS /////////////

    @Override
    public Void visitProg(ProgContext ctx) {
        state.startFunction("main");
        visitChildren(ctx);
        state.endFunction();
        return null;
    }

    @Override
    public Void visitFunc(FuncContext ctx) {
        String ident = ctx.ident().getText();
        state.startFunction(ident);
        //TODO: param list
        visit(ctx.stat());
        state.endFunction();
        return null;
    }

    @Override
    public Void visitExitStat(ExitStatContext ctx) {
        return super.visitExitStat(ctx);
    }

    @Override
    public Void visitIntSign(IntSignContext ctx) {
        return super.visitIntSign(ctx);
    }

    @Override
    public Void visitAssignRhs(AssignRhsContext ctx) {
        return super.visitAssignRhs(ctx);
    }

    @Override
    public Void visitArgList(ArgListContext ctx) {
        return super.visitArgList(ctx);
    }

    @Override
    public Void visitParam(ParamContext ctx) {
        return super.visitParam(ctx);
    }

    @Override
    public Void visitVarAssignment(VarAssignmentContext ctx) {
        return super.visitVarAssignment(ctx);
    }

    @Override
    public Void visitParamList(ParamListContext ctx) {
        return super.visitParamList(ctx);
    }

    @Override
    public Void visitExpr(ExprContext ctx) {
        return super.visitExpr(ctx);
    }

    @Override
    public Void visitType(TypeContext ctx) {
        return super.visitType(ctx);
    }

    @Override
    public Void visitOtherBinaryOper(OtherBinaryOperContext ctx) {
        return super.visitOtherBinaryOper(ctx);
    }

    @Override
    public Void visitCharacter(CharacterContext ctx) {
        return super.visitCharacter(ctx);
    }

    @Override
    public Void visitNewPair(NewPairContext ctx) {
        return super.visitNewPair(ctx);
    }

    @Override
    public Void visitBoolBinaryOper(BoolBinaryOperContext ctx) {
        return super.visitBoolBinaryOper(ctx);
    }

    @Override
    public Void visitIdent(IdentContext ctx) {
        return super.visitIdent(ctx);
    }

    @Override
    public Void visitBaseType(BaseTypeContext ctx) {
        return super.visitBaseType(ctx);
    }

    @Override
    public Void visitScopeStat(ScopeStatContext ctx) {
        return super.visitScopeStat(ctx);
    }

    @Override
    public Void visitPairLiter(PairLiterContext ctx) {
        return super.visitPairLiter(ctx);
    }

    @Override
    public Void visitReadStat(ReadStatContext ctx) {
        return super.visitReadStat(ctx);
    }

    @Override
    public Void visitPairElemType(PairElemTypeContext ctx) {
        return super.visitPairElemType(ctx);
    }

    @Override
    public Void visitVarDeclaration(VarDeclarationContext ctx) {
        return super.visitVarDeclaration(ctx);
    }

    @Override
    public Void visitReturnStat(ReturnStatContext ctx) {
        return super.visitReturnStat(ctx);
    }

    @Override
    public Void visitPrintStat(PrintStatContext ctx) {
        return super.visitPrintStat(ctx);
    }

    @Override
    public Void visitPairElem(PairElemContext ctx) {
        return super.visitPairElem(ctx);
    }

    @Override
    public Void visitArrayElem(ArrayElemContext ctx) {
        return super.visitArrayElem(ctx);
    }

    @Override
    public Void visitEscapedChar(EscapedCharContext ctx) {
        return super.visitEscapedChar(ctx);
    }

    @Override
    public Void visitStat(StatContext ctx) {
        return super.visitStat(ctx);
    }

    @Override
    public Void visitFreeStat(FreeStatContext ctx) {
        return super.visitFreeStat(ctx);
    }

    @Override
    public Void visitWhileStat(WhileStatContext ctx) {
        return super.visitWhileStat(ctx);
    }

    @Override
    public Void visitUnaryOper(UnaryOperContext ctx) {
        return super.visitUnaryOper(ctx);
    }

    @Override
    public Void visitIfStat(IfStatContext ctx) {
        return super.visitIfStat(ctx);
    }

    @Override
    public Void visitPairType(PairTypeContext ctx) {
        return super.visitPairType(ctx);
    }

    @Override
    public Void visitArrayLiter(ArrayLiterContext ctx) {
        return super.visitArrayLiter(ctx);
    }

    @Override
    public Void visitAssignLhs(AssignLhsContext ctx) {
        return super.visitAssignLhs(ctx);
    }

    @Override
    public Void visitComment(CommentContext ctx) {
        return super.visitComment(ctx);
    }


    @Override
    public Void visitFuncCall(FuncCallContext ctx) {
        return super.visitFuncCall(ctx);
    }

    @Override
    public Void visitPrintlnStat(PrintlnStatContext ctx) {
        return super.visitPrintlnStat(ctx);
    }
}
