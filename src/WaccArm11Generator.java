import antlr.WaccParserBaseVisitor;
import instructions.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import util.*;

import java.util.Comparator;
import java.util.PriorityQueue;

import static antlr.WaccParser.*;

public class WaccArm11Generator extends WaccParserBaseVisitor<Register> {

    private Arm11Program state = new Arm11Program();
    private Registers registers = new Registers();
    private SymbolTable st;
    private int stackOffset;
    private int currOffset;

    // size on stack for each type
    private static final int INT_SIZE = 4;
    private static final int BOOL_SIZE = 1;
    private static final int CHAR_SIZE = 1;
    private static final int STRING_SIZE = 4;
    private static final int PAIR_SIZE = 4;

    private static final boolean IS_BYTE = true;

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
    public Register visitChildren(RuleNode tree) {
        PriorityQueue<ParseTree> children = new PriorityQueue<ParseTree>(1, new Comparator<ParseTree>() {
            @Override
            public int compare(ParseTree p1, ParseTree p2) {
                return compareWeights(p1, p2);
            }
        });

        for(int i = 0; i < tree.getChildCount(); i++) {
            children.add(tree.getChild(i));
        }

        Register result = null;
        for(ParseTree child : children) {
            result = visit(child);
        }

        return result;
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
    public Register visitProg(ProgContext ctx) {
        state.startFunction("main");

        // visit and calculate offset
        StackSizeVisitor sizeVisitor = new StackSizeVisitor();
        stackOffset = sizeVisitor.getSize(ctx);

        state.add(new SubInstruction(Registers.sp, Registers.sp, stackOffset));

        visitChildren(ctx);

        // check if return register has been filled
        if(!registers.isInUse("r0")) {
            state.add(new LoadInstruction(Registers.r0, 0));
        }

        state.add(new AddInstruction(Registers.sp, Registers.sp, stackOffset));

        state.endMainFunction();
        state.add(new TextDirective());
        state.add(new GlobalDirective("main"));
        return null;
    }

    @Override
    public Register visitFunc(FuncContext ctx) {
        String ident = ctx.ident().getText();
        state.startFunction(ident);
        //TODO: param list
        visit(ctx.stat());
        state.endFunction();
        return null;
    }

    @Override
    public Register visitExitStat(ExitStatContext ctx) {
        Register result = visit(ctx.expr());

        state.add(new MoveInstruction(registers.getReturnRegister(), result));
        state.add(new BranchLinkInstruction("exit"));

        registers.freeReturnRegisters();

        state.add(new LoadInstruction(registers.getReturnRegister(), 0));
        return null;
    }

    @Override
    public Register visitExpr(ExprContext ctx) {
        if(ctx.INT_LIT() != null) {
            int i = Integer.parseInt(ctx.INT_LIT().getSymbol().getText());
            Register nextRegister = registers.getRegister();
            state.add(new LoadInstruction(nextRegister, i));
            return nextRegister;
        }
        if(ctx.BOOL_LIT() != null) {
            int bool = ctx.BOOL_LIT().getSymbol().getText().equals("true") ? 1 : 0;
            Register nextRegister = registers.getRegister();
            state.add(new MoveInstruction(nextRegister, bool));
            return nextRegister;
        }
        if(ctx.CHAR_LIT() != null) {
            char c = ctx.CHAR_LIT().getSymbol().getText().charAt(0);
            Register nextRegister = registers.getRegister();
            state.add(new MoveInstruction(nextRegister, c));
            return nextRegister;
        }
        if(ctx.STRING_LIT() != null) {
            // get string and strip quotes
            String s = ctx.STRING_LIT().getSymbol().getText();
            s = s.substring(1, s.length() - 1);

            String label = state.addMsgLabel(s);
            Register nextRegister = registers.getRegister();
            state.add(new LoadInstruction(nextRegister, label));
            return nextRegister;
        }

        return null;
    }

    @Override
    public Register visitIntSign(IntSignContext ctx) {
        return super.visitIntSign(ctx);
    }

    @Override
    public Register visitAssignRhs(AssignRhsContext ctx) {
        return super.visitAssignRhs(ctx);
    }

    @Override
    public Register visitArgList(ArgListContext ctx) {
        return super.visitArgList(ctx);
    }

    @Override
    public Register visitParam(ParamContext ctx) {
        return super.visitParam(ctx);
    }

    @Override
    public Register visitVarAssignment(VarAssignmentContext ctx) {
        return super.visitVarAssignment(ctx);
    }

    @Override
    public Register visitParamList(ParamListContext ctx) {
        return super.visitParamList(ctx);
    }

    @Override
    public Register visitType(TypeContext ctx) {
        return super.visitType(ctx);
    }

    @Override
    public Register visitOtherBinaryOper(OtherBinaryOperContext ctx) {
        return super.visitOtherBinaryOper(ctx);
    }

    @Override
    public Register visitCharacter(CharacterContext ctx) {
        return super.visitCharacter(ctx);
    }

    @Override
    public Register visitNewPair(NewPairContext ctx) {
        return super.visitNewPair(ctx);
    }

    @Override
    public Register visitBoolBinaryOper(BoolBinaryOperContext ctx) {
        return super.visitBoolBinaryOper(ctx);
    }

    @Override
    public Register visitIdent(IdentContext ctx) {
        return super.visitIdent(ctx);
    }

    @Override
    public Register visitBaseType(BaseTypeContext ctx) {
        return super.visitBaseType(ctx);
    }

    @Override
    public Register visitScopeStat(ScopeStatContext ctx) {
        return super.visitScopeStat(ctx);
    }

    @Override
    public Register visitPairLiter(PairLiterContext ctx) {
        return super.visitPairLiter(ctx);
    }

    @Override
    public Register visitPairElemType(PairElemTypeContext ctx) {
        return super.visitPairElemType(ctx);
    }

    @Override
    public Register visitStat(StatContext ctx) {
        if(ctx.SEMICOLON() != null) {
            visit(ctx.stat(0));
            return visit(ctx.stat(1));
        } else {
            return visitChildren(ctx);
        }
    }

    @Override
    public Register visitReadStat(ReadStatContext ctx) {
        return super.visitReadStat(ctx);
    }

    @Override
    public Register visitVarDeclaration(VarDeclarationContext ctx) {
        Register dst = registers.getRegister();
        TypeContext type = ctx.type();
        ExprContext expr = ctx.assignRhs().expr();
        int offset;
        if (expr != null) {
            Register src = visit(expr);
            currOffset += getSize(type);
            offset = stackOffset - currOffset;

            if (type.baseType().BOOL() != null || type.baseType().CHAR() != null) {
                state.add(new StoreInstruction(src, Registers.sp, offset, IS_BYTE));
            } else {
                state.add(new StoreInstruction(src, Registers.sp, offset));
            }

            registers.free(src);
            st.setAddress(ctx.ident().getText(), offset);
        }
        return null;
    }

    private int getSize(TypeContext ctx) {
        if (ctx.baseType().INT() != null) {
            return INT_SIZE;
        }
        if (ctx.baseType().BOOL() != null) {
            return BOOL_SIZE;
        }
        if (ctx.baseType().CHAR() != null) {
            return CHAR_SIZE;
        }
        if (ctx.baseType().STRING() != null) {
            return STRING_SIZE;
        }
        if (ctx.pairType() != null) {
            return PAIR_SIZE;
        }
        return 0;
    }

    @Override
    public Register visitReturnStat(ReturnStatContext ctx) {
        return super.visitReturnStat(ctx);
    }

    @Override
    public Register visitPrintStat(PrintStatContext ctx) {
        visitPrint(ctx.expr(), false);
        return null;
    }

    @Override
    public Register visitPrintlnStat(PrintlnStatContext ctx) {
        visitPrint(ctx.expr(), true);
        return null;
    }

    private void visitPrint(ExprContext expr, boolean ln) {
        Register msgReg = visit(expr);

        WaccType identType = null;
        if(expr.ident() != null) identType = st.lookupType(expr.ident().getText());

        // print string
        if(expr.STRING_LIT() != null || new WaccType(STRING).equals(identType)) {
            state.add(new MoveInstruction(Registers.r0, msgReg));
            state.add(new BranchLinkInstruction(Arm11Program.PRINT_STRING_NAME));

            if (!state.functionDeclared(Arm11Program.PRINT_STRING_NAME)) {
                state.addPrintString();
            }

        }

        // print bool
        if(expr.BOOL_LIT() != null || new WaccType(BOOL).equals(identType)) {
            int val;
            if(expr.BOOL_LIT() != null) val = expr.BOOL_LIT().getSymbol().getText().equals("true") ? 1 : 0;
            if(identType != null) val = 1; //TODO: if the param is an expression?

            state.add(new MoveInstruction(Registers.r0, msgReg));
            state.add(new BranchLinkInstruction(Arm11Program.PRINT_BOOL_NAME));

            if (!state.functionDeclared(Arm11Program.PRINT_BOOL_NAME)) {
                state.addPrintBool();
            }
        }

        // print ln
        if(ln) state.add(new BranchLinkInstruction(Arm11Program.PRINTLN_NAME));

        if(ln && !state.functionDeclared(Arm11Program.PRINTLN_NAME)) {
            state.addPrintlnFunc();
        }

        registers.free(msgReg);
    }

    @Override
    public Register visitPairElem(PairElemContext ctx) {
        return super.visitPairElem(ctx);
    }

    @Override
    public Register visitArrayElem(ArrayElemContext ctx) {
        return super.visitArrayElem(ctx);
    }

    @Override
    public Register visitEscapedChar(EscapedCharContext ctx) {
        return super.visitEscapedChar(ctx);
    }

    @Override
    public Register visitFreeStat(FreeStatContext ctx) {
        return super.visitFreeStat(ctx);
    }

    @Override
    public Register visitWhileStat(WhileStatContext ctx) {
        return super.visitWhileStat(ctx);
    }

    @Override
    public Register visitUnaryOper(UnaryOperContext ctx) {
        return super.visitUnaryOper(ctx);
    }

    @Override
    public Register visitIfStat(IfStatContext ctx) {
        return super.visitIfStat(ctx);
    }

    @Override
    public Register visitPairType(PairTypeContext ctx) {
        return super.visitPairType(ctx);
    }

    @Override
    public Register visitArrayLiter(ArrayLiterContext ctx) {
        return super.visitArrayLiter(ctx);
    }

    @Override
    public Register visitAssignLhs(AssignLhsContext ctx) {
        return super.visitAssignLhs(ctx);
    }

    @Override
    public Register visitComment(CommentContext ctx) {
        return super.visitComment(ctx);
    }

    @Override
    public Register visitFuncCall(FuncCallContext ctx) {
        return super.visitFuncCall(ctx);
    }
}
