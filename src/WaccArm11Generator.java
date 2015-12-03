import antlr.WaccParser.ExprContext;
import antlr.WaccParser.StatContext;
import antlr.WaccParserBaseVisitor;
import instructions.*;

import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import util.*;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import static antlr.WaccParser.*;

public class WaccArm11Generator extends WaccParserBaseVisitor<Register> {

    private Arm11Program state = new Arm11Program();
    private Registers registers = new Registers();
    private SymbolTable st;
    private int stackOffset;
    private int currOffset;
    private int ifStatementCounter = 0;
    private int WhileStatementCounter = 0;

    // size on stack for each type
    private static final int INT_SIZE = 4;
    private static final int BOOL_SIZE = 1;
    private static final int CHAR_SIZE = 1;
    private static final int STRING_SIZE = 4;
    private static final int PAIR_SIZE = 4;
    private static final int ARRAY_SIZE = 4;
    private static final int REG_SIZE = 4;
    private static final int BOOL_CHAR_SIZE = 1;

    private static final boolean IS_BYTE = true;

    private static final String MALLOC = "malloc";

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

        if(stackOffset != 0) state.add(new SubInstruction(Registers.sp, Registers.sp, new Operand2('#', stackOffset)));

        visitChildren(ctx);

        // check if return register has been filled
        if(!registers.isInUse("r0")) {
            state.add(new LoadInstruction(Registers.r0, new Operand2(0)));
        }

        if(stackOffset != 0) state.add(new AddInstruction(Registers.sp, Registers.sp, new Operand2('#', stackOffset)));

        state.endUserFunction();
        state.add(new TextDirective());
        state.add(new GlobalDirective("main"));
        return null;
    }

    @Override
    public Register visitFunc(FuncContext ctx) {
        // save stack size for this scope
        StackSizeVisitor sizeVisitor = new StackSizeVisitor();
        int size = sizeVisitor.getSize(ctx);
        st.setStackSize(ctx.ident().getText(), size);

        String ident = ctx.ident().getText();
        state.startFunction("f_" + ident);
        if(ctx.paramList() != null) visit(ctx.paramList());
        visit(ctx.stat());
        state.endUserFunction();
        return null;
    }

    @Override
    public Register visitParamList(ParamListContext ctx) {
        String funcName = ((FuncContext) ctx.getParent()).ident().getText();
        List<Pair<WaccType, String>> params = st.getParamList(funcName);

        int offset = 0;
        for(Pair<WaccType, String> param : params) {
            offset += 4; // TODO: find size of type param.b
            st.setAddress(param.b, offset);
        }

        return null;
    }

    @Override
    public Register visitParam(ParamContext ctx) {
        // only do this when the param is needed
        //Register next = registers.getRegister();
        //state.add(new LoadInstruction(next, Registers.sp, 4));
        //st.setRegister(ctx.ident().getText(), next);
        //return next;
        return null;
    }

    @Override
    public Register visitFuncCall(FuncCallContext ctx) {
        if(ctx.argList() != null) visit(ctx.argList());

        state.add(new BranchLinkInstruction("f_" + ctx.ident().getText()));
        int stackSize = st.getStackSize(ctx.ident().getText());
        if(stackSize != 0) state.add(new AddInstruction(Registers.sp, Registers.sp, new Operand2('#', stackSize)));
        Register next = registers.getRegister();
        state.add(new MoveInstruction(next, Registers.r0));
        state.add(new StoreInstruction(next, Registers.sp, 0));
        registers.free(next);

        return null;
    }

    @Override
    public Register visitArgList(ArgListContext ctx) {
        String funcName = ((FuncCallContext) ctx.getParent()).ident().getText();
        List<Pair<WaccType, String>> params = st.getParamList(funcName);

        for(int i = 0; i < params.size(); i++) {
            String paramName = params.get(i).b;
            Register exprReg = visit(ctx.expr(i));

            StoreInstruction ins = new StoreInstruction(exprReg, Registers.sp, -1 * st.getAddress(paramName));
            ins.preIndex = true;
            state.add(ins);

            registers.free(exprReg);
        }
        return null;
    }

    @Override
    public Register visitExitStat(ExitStatContext ctx) {
        Register result = visit(ctx.expr());

        state.add(new MoveInstruction(Registers.r0, result));
        state.add(new BranchLinkInstruction("exit"));

        registers.freeReturnRegisters();

        state.add(new LoadInstruction(Registers.r0, new Operand2(0)));
        return null;
    }

    @Override
    public Register visitExpr(ExprContext ctx) {
        if(ctx.INT_LIT() != null) {
            int i = Integer.parseInt(ctx.INT_LIT().getSymbol().getText());
            Register nextRegister = registers.getRegister();
            state.add(new LoadInstruction(nextRegister, new Operand2(i)));
            return nextRegister;
        }
        if(ctx.BOOL_LIT() != null) {
            int bool = ctx.BOOL_LIT().getSymbol().getText().equals("true") ? 1 : 0;
            Register nextRegister = registers.getRegister();
            state.add(new MoveInstruction(nextRegister, bool));
            return nextRegister;
        }
        if(ctx.CHAR_LIT() != null) {
            Register nextRegister = registers.getRegister();
            String text = ctx.CHAR_LIT().getSymbol().getText();
            char c = Arm11Program.decode(text).charAt(1);
            if (text.equals("'\\0'")) {
                state.add(new MoveInstruction(nextRegister, 0));
            } else {
                state.add(new MoveInstruction(nextRegister, c));
            }
            return nextRegister;
        }
        if(ctx.STRING_LIT() != null) {
            // get string and strip quotes
            String s = ctx.STRING_LIT().getSymbol().getText();
            s = s.substring(1, s.length() - 1);

            String label = state.addMsgLabel(s);
            Register nextRegister = registers.getRegister();
            state.add(new LoadInstruction(nextRegister, new Operand2(label)));
            return nextRegister;
        }
        if(ctx.ident() != null) {
            String ident = ctx.ident().getText();
            int offset = st.getAddress(ident);
            Register nextRegister = registers.getRegister();
            WaccType type = st.lookupType(ident);
            if(type.equals(new WaccType(BOOL))) {
                state.add(new LoadSignedByteInstruction(nextRegister, new Operand2(Registers.sp, offset)));
            } else {
                state.add(new LoadInstruction(nextRegister, new Operand2(Registers.sp, offset)));
            }
            return nextRegister;
        }
        if(ctx.arrayElem() != null) {
            String ident = ctx.arrayElem().ident().getText();
            int offset = st.getAddress(ident);

            Register arrayRegister = registers.getRegister();
            state.add(new AddInstruction(arrayRegister, Registers.sp, new Operand2('#', offset)));

            for(int i = 0; i < ctx.arrayElem().expr().size(); i += 3) {
                Register indexRegister = visit(ctx.arrayElem().expr(i)); // get index of arrayElem
                state.add(new LoadInstruction(arrayRegister, new Operand2(arrayRegister, 0))); // get length of array

                state.add(new MoveInstruction(registers.getReturnRegister(), indexRegister));
                state.add(new MoveInstruction(registers.getReturnRegister(), arrayRegister));

                state.add(new BranchLinkInstruction(Arm11Program.ARRAY_BOUND_NAME));
                state.addArrayBoundError();

                // indexes start after length at offset 0
                state.add(new AddInstruction(arrayRegister, arrayRegister, new Operand2('#', INT_SIZE)));

                if (getIdentTypeSize(ident) == BOOL_CHAR_SIZE) {
                    state.add(new AddInstruction(arrayRegister, arrayRegister, new Operand2(indexRegister)));
                } else {
                    /* gets the correct index, takes index expr
                       and multiplies by 4 (LSL #2) since reg indexes are 4 long
                    */
                    state.add(new AddInstruction(arrayRegister, arrayRegister, new Operand2(indexRegister), 2));
                }

                registers.free(indexRegister);
                registers.freeReturnRegisters();
            }

            state.add(new LoadInstruction(arrayRegister, new Operand2(arrayRegister, true), getIdentTypeSize(ident) == BOOL_CHAR_SIZE));
            return arrayRegister;
        }
        //TODO: MOVE THIS TO visitUnaryOper ???
        if(ctx.unaryOper() != null && ctx.unaryOper().LEN() != null) {
            String ident = ctx.expr(0).ident().getText();
            int offset = st.getAddress(ident);
            Register nextRegister = registers.getRegister();
            // array info stored on stack
            state.add(new LoadInstruction(nextRegister, new Operand2(Registers.sp, offset)));
            // array length is in first address
            state.add(new LoadInstruction(nextRegister, new Operand2(nextRegister)));
            return nextRegister;
        }

        if(ctx.boolBinaryOper() != null) {
            return visit(ctx.boolBinaryOper());
        }
        if(ctx.otherBinaryOper() != null) {
            return visit(ctx.otherBinaryOper());
        }
        return null;
    }

    @Override
    public Register visitOtherBinaryOper(OtherBinaryOperContext ctx) {
        return visitBinOp(ctx);
    }

    @Override
    public Register visitBoolBinaryOper(BoolBinaryOperContext ctx) {
        return visitBinOp(ctx);
    }

    private Register visitBinOp(ParseTree ctx) {
        int tokenIndex = ((TerminalNode) ctx.getChild(0)).getSymbol().getType();

        Register lhs = visit(((ExprContext) ctx.getParent()).expr(0));
        Register rhs = visit(((ExprContext) ctx.getParent()).expr(1));

        registers.free(lhs);
        registers.free(rhs);

        Register dest = registers.getRegister();

        switch(tokenIndex) {
            case AND:
                state.add(new AndInstruction(dest, lhs, new Operand2(rhs)));
                return dest;
            case OR:
                state.add(new OrInstruction(dest, lhs, new Operand2(rhs)));
                return dest;
            case MULT:
                Register overflow = registers.getRegister();
                state.add(new MultiplyInstruction(dest, overflow, lhs, rhs));
                Operand2 op2 = new Operand2(dest);
                op2.setAsr(31);
                state.add(new CompareInstruction(overflow, op2));
                state.add(new BranchLinkNotEqualInstruction(Arm11Program.OVERFLOW_NAME));
                if(!state.functionDeclared(Arm11Program.OVERFLOW_NAME)) state.addOverflowError();
                return dest;
            case DIV:
                state.add(new MoveInstruction(Registers.r0, lhs));
                state.add(new MoveInstruction(Registers.r1, rhs));
                state.add(new BranchLinkInstruction(Arm11Program.DIVIDE_BY_ZERO_NAME));
                state.addDivideByZeroError();
                state.add(new BranchLinkInstruction("__aeabi_idiv"));
                state.add(new MoveInstruction(dest, Registers.r0));
                return dest;
            case MOD:
                state.add(new MoveInstruction(Registers.r0, lhs));
                state.add(new MoveInstruction(Registers.r1, rhs));
                state.add(new BranchLinkInstruction(Arm11Program.DIVIDE_BY_ZERO_NAME));
                state.addDivideByZeroError();
                state.add(new BranchLinkInstruction("__aeabi_idivmod"));
                state.add(new MoveInstruction(dest, Registers.r1));
                return dest;
            case PLUS:
                AddInstruction adds = new AddInstruction(dest, lhs, new Operand2(rhs));
                adds.setFlags = true;
                state.add(adds);
                state.add(new BranchLinkOverflowInstruction(Arm11Program.OVERFLOW_NAME));
                if(!state.functionDeclared(Arm11Program.OVERFLOW_NAME)) state.addOverflowError();
                return dest;
            case MINUS:
                SubInstruction subs = new SubInstruction(dest, lhs, new Operand2(rhs));
                subs.setFlags = true;
                state.add(subs);
                state.add(new BranchLinkOverflowInstruction(Arm11Program.OVERFLOW_NAME));
                if(!state.functionDeclared(Arm11Program.OVERFLOW_NAME)) state.addOverflowError();
                return dest;
            case GREATER_THAN:
                state.add(new CompareInstruction(lhs, new Operand2(rhs)));
                state.add(new MoveGreaterThanInstruction(dest, new Operand2('#', 1)));
                state.add(new MoveLessThanEqualInstruction(dest, new Operand2('#', 0)));
                return dest;
            case GREATER_THAN_EQ:
                state.add(new CompareInstruction(lhs, new Operand2(rhs)));
                state.add(new MoveGreaterThanEqualInstruction(dest, new Operand2('#', 1)));
                state.add(new MoveLessThanInstruction(dest, new Operand2('#', 0)));
                return dest;
            case LESS_THAN:
                state.add(new CompareInstruction(lhs, new Operand2(rhs)));
                state.add(new MoveLessThanInstruction(dest, new Operand2('#', 1)));
                state.add(new MoveGreaterThanEqualInstruction(dest, new Operand2('#', 0)));
                return dest;
            case LESS_THAN_EQ:
                state.add(new CompareInstruction(lhs, new Operand2(rhs)));
                state.add(new MoveLessThanEqualInstruction(dest, new Operand2('#', 1)));
                state.add(new MoveGreaterThanInstruction(dest, new Operand2('#', 0)));
                return dest;
            case EQ:
                state.add(new CompareInstruction(lhs, new Operand2(rhs)));
                state.add(new MoveEqualInstruction(dest, new Operand2('#', 1)));
                state.add(new MoveNotEqualInstruction(dest, new Operand2('#', 0)));
                return dest;
            case NOT_EQ:
                state.add(new CompareInstruction(lhs, new Operand2(rhs)));
                state.add(new MoveNotEqualInstruction(dest, new Operand2('#', 1)));
                state.add(new MoveEqualInstruction(dest, new Operand2('#', 0)));
                return dest;
            default:
                return null;
        }
    }

    @Override
    public Register visitIntSign(IntSignContext ctx) {
        return super.visitIntSign(ctx);
    }

    @Override
    public Register visitAssignRhs(AssignRhsContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Register visitVarAssignment(VarAssignmentContext ctx) {
        IdentContext id = ctx.assignLhs().ident();
        ExprContext expr = ctx.assignRhs().expr();
        if (id != null) {
            String ident = id.getText();
            int offset = st.getAddress(ident);

            if (expr !=  null) {
                Register src = visit(expr);
                boolean isBoolOrChar = expr.BOOL_LIT() != null
                                        || expr.CHAR_LIT() != null;
                state.add(new StoreInstruction(src, Registers.sp, offset, isBoolOrChar));
                registers.free(src);
            }

            ArrayLiterContext arrayLiter = ctx.assignRhs().arrayLiter();
            if (arrayLiter != null) {
                int arrLength = arrayLiter.expr().size();
                int typeSize = getIdentTypeSize(ident);
                int heapSize = arrLength * typeSize + INT_SIZE; // INT_SIZE IS TO STORE LENGTH OF ARRAY
                boolean isBoolOrChar = typeSize == BOOL_CHAR_SIZE;

                // set up heap memory allocation
                state.add(new LoadInstruction(Registers.r0, new Operand2(heapSize)));
                state.add(new BranchLinkInstruction(MALLOC));
                Register heapPtr = registers.getRegister();
                state.add(new MoveInstruction(heapPtr, Registers.r0));

                // process each array elem
                for (int i = 0; i < arrLength; i++) {
                    Register src = visit(arrayLiter.expr(i));
                    state.add(new StoreInstruction(src, heapPtr, INT_SIZE + i * typeSize, isBoolOrChar));
                    registers.free(src);
                }
                // process length
                Register lengthReg = registers.getRegister();
                state.add(new LoadInstruction(lengthReg, new Operand2(arrLength)));
                state.add(new StoreInstruction(lengthReg, heapPtr, 0));
                registers.free(lengthReg);

                state.add(new StoreInstruction(heapPtr, Registers.sp, offset));
                registers.free(heapPtr);
            }
        }

        ArrayElemContext arrayElem = ctx.assignLhs().arrayElem();
        if (arrayElem != null) {
            if (expr != null) {
                Register nextRegister = visit(expr);
            }
        }
        return null;
    }

    private int getIdentTypeSize(String ident) {
        WaccType type = st.lookupType(ident);
        int id = type.getId();
        switch (id) {
            case INT: return INT_SIZE;
            case BOOL: return BOOL_SIZE;
            case CHAR: return CHAR_SIZE;
            case STRING: return STRING_SIZE;
            case PAIR: return PAIR_SIZE;
        }
        return 0;
    }

    @Override
    public Register visitType(TypeContext ctx) {
        return super.visitType(ctx);
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
    public Register visitIdent(IdentContext ctx) {
        Register next = registers.getRegister();
        state.add(new AddInstruction(next, Registers.sp, new Operand2('#', st.getAddress(ctx.getText()))));
        return next;
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
    public Register visitVarDeclaration(VarDeclarationContext ctx) {
        TypeContext type = ctx.type();
        ExprContext expr = ctx.assignRhs().expr();
        ArrayLiterContext arrayLiter = ctx.assignRhs().arrayLiter();
        int offset = 0;
        if (expr != null) {
            Register src = visit(expr);
            currOffset += getTypeSize(type);
            offset = stackOffset - currOffset;

            if (type.baseType().BOOL() != null || type.baseType().CHAR() != null) {
                state.add(new StoreInstruction(src, Registers.sp, offset, IS_BYTE));
            } else {
                state.add(new StoreInstruction(src, Registers.sp, offset));
            }

            // src register does not need to hold expr value anymore
            registers.free(src);
        } else if (arrayLiter != null) { // array declaration
            int arrLength = arrayLiter.expr().size();
            int typeSize;
            if (arrayLiter.expr(0).ident() != null) { // nested arrays
                typeSize = ARRAY_SIZE;
            } else {
                typeSize = getTypeSize(type);
            }
            int heapSize = arrLength * typeSize + INT_SIZE; // INT_SIZE IS TO STORE LENGTH OF ARRAY
            boolean isBoolOrChar = typeSize == BOOL_CHAR_SIZE;

            // set up heap memory allocation
            state.add(new LoadInstruction(Registers.r0, new Operand2(heapSize)));
            state.add(new BranchLinkInstruction(MALLOC));
            Register heapPtr = registers.getRegister();
            state.add(new MoveInstruction(heapPtr, Registers.r0));

            // process each array elem
            for (int i = 0; i < arrLength; i++) {
                Register src = visit(arrayLiter.expr(i));
                state.add(new StoreInstruction(src, heapPtr, INT_SIZE + i * typeSize, isBoolOrChar));
                registers.free(src);
            }
            // process length
            Register lengthReg = registers.getRegister();
            state.add(new LoadInstruction(lengthReg, new Operand2(arrLength)));
            state.add(new StoreInstruction(lengthReg, heapPtr, 0));
            registers.free(lengthReg);

            currOffset += REG_SIZE;
            offset = stackOffset - currOffset;
            state.add(new StoreInstruction(heapPtr, Registers.sp, offset));
            registers.free(heapPtr);
        }
        if(ctx.assignRhs().funcCall() != null) {
            visit(ctx.assignRhs().funcCall());
        }

        st.setAddress(ctx.ident().getText(), offset);
        return null;
    }

    private int getTypeSize(TypeContext ctx) {
        if (ctx.baseType() != null) {
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
        }
        if (ctx.pairType() != null) {
            return PAIR_SIZE;
        }
        if (ctx.type() != null) {
            return getTypeSize(ctx.type());
        }
        return 0;
    }

    @Override
    public Register visitReturnStat(ReturnStatContext ctx) {
        Register returnReg = visit(ctx.expr());
        state.add(new MoveInstruction(Registers.r0, returnReg));
        state.add(new PopInstruction(Registers.pc));
        registers.free(returnReg);
        return null;
    }

    @Override
    public Register visitReadStat(ReadStatContext ctx) {
        WaccType varType;
        AssignLhsContext lhs = ctx.assignLhs();
        if(lhs.ident() != null) {
            varType = st.lookupType(lhs.ident().getText());
        } else if(lhs.arrayElem() != null) {
            varType = st.lookupType(lhs.arrayElem().ident().getText());
        } else {
            varType = WaccType.INVALID; //TODO
        }

        Register lhsReg = visit(ctx.assignLhs());

        if(varType.equals(new WaccType(INT))) {
            state.add(new MoveInstruction(Registers.r0, lhsReg));
            state.add(new BranchLinkInstruction(Arm11Program.READ_INT_NAME));

            if(!state.functionDeclared(Arm11Program.READ_INT_NAME)) {
                state.addReadInt();
            }
        }

        if(varType.equals(new WaccType(CHAR))) {
            state.add(new MoveInstruction(Registers.r0, lhsReg));
            state.add(new BranchLinkInstruction(Arm11Program.READ_CHAR_NAME));

            if(!state.functionDeclared(Arm11Program.READ_CHAR_NAME)) {
                state.addReadChar();
            }
        }

        registers.free(lhsReg);
        return null;
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

        WaccType exprType = null;
        Instruction loadIns = null;
        if(expr.ident() != null) {
            exprType = st.lookupType(expr.ident().getText());
            int offset = st.getAddress(expr.ident().getText());
            loadIns = new LoadInstruction(msgReg, new Operand2(Registers.sp, offset));
        } else if(expr.otherBinaryOper() != null) {
            exprType = WaccType.fromBinaryOp(((TerminalNode) expr.otherBinaryOper().getChild(0)).getSymbol().getType());
        } else if(expr.boolBinaryOper() != null) {
            exprType = WaccType.fromBinaryOp(((TerminalNode) expr.boolBinaryOper().getChild(0)).getSymbol().getType());
        } else {
            msgReg = visitExpr(expr);
        }

        // print string
        if(expr.STRING_LIT() != null || new WaccType(STRING).equals(exprType)) {
            if(new WaccType(STRING).equals(exprType)) {
                state.add(loadIns);
            }
            state.add(new MoveInstruction(Registers.r0, msgReg));

            state.add(new BranchLinkInstruction(Arm11Program.PRINT_STRING_NAME));

            if (!state.functionDeclared(Arm11Program.PRINT_STRING_NAME)) {
                state.addPrintString();
            }

        }

        // print bool
        if(expr.BOOL_LIT() != null || new WaccType(BOOL).equals(exprType)) {
            if(new WaccType(BOOL).equals(exprType)) {
                //state.add(loadIns);
            }
            state.add(new MoveInstruction(Registers.r0, msgReg));
            state.add(new BranchLinkInstruction(Arm11Program.PRINT_BOOL_NAME));

            if (!state.functionDeclared(Arm11Program.PRINT_BOOL_NAME)) {
                state.addPrintBool();
            }
        }

        // print int
        if(expr.INT_LIT() != null || new WaccType(INT).equals(exprType)) {
            if(new WaccType(INT).equals(exprType)) {
                state.add(loadIns);
            }
            state.add(new MoveInstruction(Registers.r0, msgReg));
            state.add(new BranchLinkInstruction(Arm11Program.PRINT_INT_NAME));

            if (!state.functionDeclared(Arm11Program.PRINT_INT_NAME)) {
                state.addPrintInt();
            }
        }

        // print char
        if(expr.CHAR_LIT() != null || new WaccType(CHAR).equals(exprType)) {
            if(new WaccType(CHAR).equals(exprType)) {
                state.add(loadIns);
            }
            state.add(new MoveInstruction(Registers.r0, msgReg));
            state.add(new BranchLinkInstruction(Arm11Program.PRINT_CHAR_NAME));
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
      ExprContext condition = (ExprContext) ctx.getChild(1);
      Register reg = visitExpr(condition);
      state.add(new CompareInstruction(reg, new Operand2('#',0)));
      state.add(new BranchLinkEqualInstruction("L" + (ifStatementCounter * 2)));
      st.newScope();
      StatContext trueStats = (StatContext) ctx.getChild(3);
      StatContext falseStats = (StatContext) ctx.getChild(5);
      visitStat(trueStats);
      state.add(new BranchLinkInstruction("L" + (ifStatementCounter * 2 + 1)));
      st.endScope();
      st.newScope();
      state.add(new LabelInstruction("L" + (ifStatementCounter * 2)));
      visitStat(falseStats);
      st.endScope();
      state.add(new LabelInstruction("L" + (ifStatementCounter * 2 + 1)));
      ifStatementCounter++;
     
      return null;
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
}
