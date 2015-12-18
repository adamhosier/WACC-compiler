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

    // stack offsets used to calculate variable positions on the stack
    private int stackOffset;
    private int currOffset;
    private int funcOffset;
    
    /* 'If' statement logic variables to assign correctly
     * numbered labels for branching around code
     */
    private int StatementCurrentLabel = -1;
    private int StatementMaxForScope = 0;
    private int StatementScopeStartingVal = -1;

    // size on stack for each type
    private static final int INT_SIZE = 4;
    private static final int BOOL_SIZE = 1;
    private static final int CHAR_SIZE = 1;
    private static final int STRING_SIZE = 4;
    private static final int PAIR_SIZE = 4;
    private static final int PAIR_HEAP_SIZE = 8;
    private static final int FST_OFFSET = 0;
    private static final int SND_OFFSET = 4;
    private static final int ARRAY_SIZE = 4;
    private static final int BOOL_CHAR_SIZE = 1;
    private static final int WORD_SIZE = 4;
    private static final int LSL_VALUE_2 = 2;
    private static final int MAX_STACK_OFFSET = 1024;

    private static final String MALLOC = "malloc";

    /*
     * Converts the program stored in [state] to a string of runnable assembly code
     */
    public String generate() {
        return state.toCode();
    }

    public Arm11Program getProgram() {
        return state;
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
        PriorityQueue<ParseTree> children = new PriorityQueue<>(1, new Comparator<ParseTree>() {
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
     * Calculates how many registers [tree] will use in code generation
     */
    public int weight(ParseTree tree) {
        WeightAnalyser analyser = new WeightAnalyser();
        return analyser.visit(tree);
    }

    ////////////// VISITOR METHODS /////////////

    @Override
    public Register visitProg(ProgContext ctx) {
        state.startFunction("main");

        // visit and calculate offset
        StackSizeVisitor sizeVisitor = new StackSizeVisitor();
        stackOffset = sizeVisitor.getSize(ctx);

        // deal with stak offsets of size greater than 1024
        if(stackOffset != 0 && stackOffset <= MAX_STACK_OFFSET) {
            state.add(new SubInstruction(Registers.sp, Registers.sp, new Operand2('#', stackOffset)));
        }
        if(stackOffset > MAX_STACK_OFFSET) {
            state.add(new SubInstruction(Registers.sp, Registers.sp, new Operand2('#', MAX_STACK_OFFSET)));
            state.add(new SubInstruction(Registers.sp, Registers.sp, new Operand2('#', stackOffset - MAX_STACK_OFFSET)));
        }

        visitChildren(ctx);

        if(stackOffset != 0 && stackOffset <= MAX_STACK_OFFSET) {
            state.add(new AddInstruction(Registers.sp, Registers.sp, new Operand2('#', stackOffset)));
        }
        if(stackOffset > MAX_STACK_OFFSET) {
            state.add(new AddInstruction(Registers.sp, Registers.sp, new Operand2('#', MAX_STACK_OFFSET)));
            state.add(new AddInstruction(Registers.sp, Registers.sp, new Operand2('#', stackOffset - MAX_STACK_OFFSET)));
        }

        // check if return register has been filled
        if(!registers.isInUse("r0")) {
            state.add(new LoadInstruction(Registers.r0, new Operand2(0)));
        }

        state.endUserFunction();
        state.add(new TextDirective());
        state.add(new GlobalDirective("main"));
        return null;
    }

    @Override
    public Register visitFunc(FuncContext ctx) {
        // save stack size for this scope
        StackSizeVisitor sizeVisitor = new StackSizeVisitor();
        funcOffset = sizeVisitor.getSize(ctx.stat());
        if(funcOffset != 0) state.add(new AddInstruction(Registers.sp, Registers.sp, new Operand2('#', funcOffset)));
        st.setStackSize(ctx.ident().getText(), funcOffset);
        st.enterNextScope();

        // add label and create function in state
        String ident = ctx.ident().getText();
        state.startFunction("f_" + ident);

        if(funcOffset != 0) state.add(new SubInstruction(Registers.sp, Registers.sp, new Operand2('#', funcOffset)));

        // add parameters and visit body
        if(ctx.paramList() != null) visit(ctx.paramList());
        visit(ctx.stat());

        // end function
        state.endUserFunction();
        st.exitScope();
        return null;
    }

    @Override
    public Register visitParamList(ParamListContext ctx) {
        String funcName = ((FuncContext) ctx.getParent()).ident().getText();
        List<Pair<WaccType, String>> params = st.getParamList(funcName);

        // calculate parameter offset and store it to symbol table for later use
        int offset = 0;
        for(Pair<WaccType, String> param : params) {
            offset += getIdentTypeSize(param.b);
            st.setAddress(param.b, offset);
        }

        return null;
    }

    @Override
    public Register visitFuncCall(FuncCallContext ctx) {
        // visit arg list only if it exists
        if(ctx.argList() != null) visit(ctx.argList());

        // branch to function and store result
        state.add(new BranchLinkInstruction("f_" + ctx.ident().getText()));
        if(funcOffset != 0) state.add(new AddInstruction(Registers.sp, Registers.sp, new Operand2('#', -funcOffset)));
        funcOffset = 0;
        Register next = registers.getRegister();
        state.add(new MoveInstruction(next, Registers.r0));
        return next;
    }

    @Override
    public Register visitArgList(ArgListContext ctx) {
        // load each argument into its own register
        int offset = 0;
        for (int i = ctx.expr().size() - 1; i >= 0; i--) {
            Register nextRegister = visit(ctx.expr(i));
            boolean isByte = state.getLastInstruction() instanceof MoveInstruction;
            offset = -(isByte ? BOOL_CHAR_SIZE : WORD_SIZE);
            funcOffset += offset;
            StoreInstruction str = new StoreInstruction(nextRegister, Registers.sp, offset, isByte);
            str.setPreIndex();
            state.add(str);
            registers.free(nextRegister);
        }
        return null;
    }

    @Override
    public Register visitExitStat(ExitStatContext ctx) {
        Register result = visit(ctx.expr());

        state.add(new MoveInstruction(Registers.r0, result));
        state.add(new BranchLinkInstruction("exit"));
        registers.freeReturnRegisters();

        // if no exit code is supplied, just return with 0
        if (ctx.expr().ident() == null) {
            state.add(new LoadInstruction(Registers.r0, new Operand2(0)));
        }
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
        if(ctx.pairLiter() != null) {
            return visit(ctx.pairLiter());
        }
        if(ctx.ident() != null) {
            String ident = ctx.ident().getText();
            int offset = st.getAddress(ident) - funcOffset;
            Register nextRegister = registers.getRegister();
            WaccType type = st.lookupType(ident);
            if(type.equals(new WaccType(BOOL)) || type.equals(new WaccType(CHAR))) {
                state.add(new LoadSignedByteInstruction(nextRegister, new Operand2(Registers.sp, offset)));
            } else {
                state.add(new LoadInstruction(nextRegister, new Operand2(Registers.sp, offset)));
            }
            return nextRegister;
        }
        if(ctx.arrayElem() != null) {
            String ident = ctx.arrayElem().ident().getText();
            int offset = st.getAddress(ident) - funcOffset;

            Register arrayRegister = registers.getRegister();
            state.add(new AddInstruction(arrayRegister, Registers.sp, new Operand2('#', offset)));

            for(int i = 0; i < ctx.arrayElem().expr().size(); i++) {
                Register indexRegister = visit(ctx.arrayElem().expr(i)); // get index of arrayElem
                state.add(new LoadInstruction(arrayRegister, new Operand2(arrayRegister, 0))); // get length of array
                addArrayBoundsCheck(indexRegister, arrayRegister);
                // indexes start after length at offset 0
                state.add(new AddInstruction(arrayRegister, arrayRegister, new Operand2('#', INT_SIZE)));
                if (getIdentTypeSize(ident) == BOOL_CHAR_SIZE) {
                    state.add(new AddInstruction(arrayRegister, arrayRegister, new Operand2(indexRegister)));
                } else {
                    /* gets the correct index, takes index expr
                       and multiplies by 4 (LSL #2) since reg indexes are 4 long
                    */
                    state.add(new AddInstruction(arrayRegister, arrayRegister, new Operand2(indexRegister), LSL_VALUE_2));
                }
                registers.free(indexRegister);
                registers.freeReturnRegisters();
            }
            state.add(new LoadInstruction(arrayRegister, new Operand2(arrayRegister, true), getIdentTypeSize(ident) == BOOL_CHAR_SIZE));
            return arrayRegister;
        }
        if(ctx.unaryOper() != null) {
            return visit(ctx.unaryOper());
        }
        if(ctx.boolBinaryOper() != null) {
            return visitBinOp(ctx.boolBinaryOper());
        }
        if(ctx.otherBinaryOper() != null) {
            return visitBinOp(ctx.otherBinaryOper());
        }
        if(ctx.OPEN_PARENTHESES() != null) {
            return visitExpr(ctx.expr(0));
        }
        return null;
    }

    @Override
    public Register visitUnaryOper(UnaryOperContext ctx) {
        // get antlr index of the operator
        int tokenIndex = ((TerminalNode) ctx.getChild(0)).getSymbol().getType();

        // visit associated expression
        ExprContext expr = ((ExprContext) ctx.getParent()).expr(0);
        Register exprReg = visit(expr);
        registers.free(exprReg);

        // store result in new register
        Register dest = registers.getRegister();

        switch(tokenIndex) {
            case NOT:
                state.add(new ExclusiveOrInstruction(dest, exprReg, new Operand2('#', 1)));
                return dest;
            case MINUS:
                state.add(new NegateInstruction(dest, exprReg, new Operand2('#', 0)));
                state.add(new BranchLinkOverflowInstruction(Arm11Program.OVERFLOW_NAME));
                if(!state.functionDeclared(Arm11Program.OVERFLOW_NAME)) state.addOverflowError();
                return dest;
            case LEN:
                String ident = expr.ident().getText();
                int offset = st.getAddress(ident);
                state.add(new LoadInstruction(dest, new Operand2(Registers.sp, offset)));
                state.add(new LoadInstruction(dest, new Operand2(dest, 0)));
                return dest;
            case ORD:
            case CHR:
                return dest;
            default:
                return null;
        }
    }

    private Register visitBinOp(ParseTree ctx) {
        // get antlr index of the operator
        int tokenIndex = ((TerminalNode) ctx.getChild(0)).getSymbol().getType();

        // visit associated expressions
        Register lhs = visit(((ExprContext) ctx.getParent()).expr(0));
        Register rhs = visit(((ExprContext) ctx.getParent()).expr(1));
        registers.free(lhs);
        registers.free(rhs);

        // store result in new register
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
    public Register visitVarAssignment(VarAssignmentContext ctx) {
        IdentContext id = ctx.assignLhs().ident();
        ArrayElemContext arrayElem = ctx.assignLhs().arrayElem();
        PairElemContext pairElemLhs = ctx.assignLhs().pairElem();

        ExprContext expr = ctx.assignRhs().expr();
        ArrayLiterContext arrayLiter = ctx.assignRhs().arrayLiter();
        NewPairContext newPair = ctx.assignRhs().newPair();
        FuncCallContext funcCall = ctx.assignRhs().funcCall();
        PairElemContext pairElemRhs = ctx.assignRhs().pairElem();

        int offset;
        int typeSize;
        boolean isBoolOrChar;
        if (id != null) {
            String ident = id.getText();
            offset = st.getAddress(ident);

           if (expr !=  null) {
                Register src = visit(expr);
                typeSize = getIdentTypeSize(ident);
                isBoolOrChar = typeSize == BOOL_CHAR_SIZE;
                state.add(new StoreInstruction(src, Registers.sp, offset, isBoolOrChar));
                registers.free(src);
            }

            if (arrayLiter != null) {
                typeSize = getIdentTypeSize(ident);
                Register heapPtr = visitArrayLiter(arrayLiter, typeSize);
                state.add(new StoreInstruction(heapPtr, Registers.sp, offset));
                registers.free(heapPtr);
            }

            if (newPair != null) {
                Register heapPtr = visit(newPair);
                state.add(new StoreInstruction(heapPtr, Registers.sp, offset));
                state.add(new BranchLinkInstruction(Arm11Program.NULL_PTR_NAME));
                registers.free(heapPtr);
            }

            if (pairElemRhs != null) {
                offset = st.getAddress(ident);
                typeSize = getIdentTypeSize(ident);
                isBoolOrChar = typeSize == BOOL_CHAR_SIZE;
                Register nextRegister = visit(pairElemRhs);
                state.add(new LoadInstruction(nextRegister, new Operand2(nextRegister, 0), isBoolOrChar));
                state.add(new StoreInstruction(nextRegister, Registers.sp, offset, isBoolOrChar));
                registers.free(nextRegister);
            }

            if (funcCall != null) {
                offset = st.getAddress(ident);
                typeSize = getIdentTypeSize(ident);
                isBoolOrChar = typeSize == BOOL_CHAR_SIZE;
                Register next = visitFuncCall(ctx.assignRhs().funcCall());
                state.add(new StoreInstruction(next, Registers.sp, offset, isBoolOrChar));
                registers.free(next);
            }
        }

        if (arrayElem != null) {
            expr = ctx.assignRhs().expr();
            String ident = arrayElem.ident().getText();
            offset = st.getAddress(ident);
            boolean isBoolOrCharArray = getIdentTypeSize(ident) == BOOL_CHAR_SIZE;
            boolean isString = new WaccType(STRING).equals(st.lookupType(ident));

            Register rhsRegister = visit(expr);
            Register arrayReg = registers.getRegister();
            Register indexRegister;
            state.add(new AddInstruction(arrayReg, Registers.sp, new Operand2('#', offset)));
            for(int i = 0; i < arrayElem.expr().size(); i++) {
                indexRegister = visit(arrayElem.expr(i));
                state.add(new LoadInstruction(arrayReg, new Operand2(arrayReg, true))); // offset 0 is length
                addArrayBoundsCheck(indexRegister, arrayReg);
                // offset of indexes start after the length which is at 0
                state.add(new AddInstruction(arrayReg, arrayReg, new Operand2('#', INT_SIZE)));
                // index always multiplied by 4 for nested arrays
                if (isBoolOrCharArray && i == arrayElem.expr().size() - 1 || isString) {
                    state.add(new AddInstruction(arrayReg, arrayReg, new Operand2(indexRegister)));
                } else {
                    // multiply index by 4 when !isBoolOrChar
                    state.add(new AddInstruction(arrayReg, arrayReg, new Operand2(indexRegister), LSL_VALUE_2));
                }
                registers.free(indexRegister);
                registers.freeReturnRegisters();
            }

            state.add(new StoreInstruction(rhsRegister, arrayReg, 0, isBoolOrCharArray || isString));
            registers.free(rhsRegister);
        }

        if (pairElemLhs != null) {
            expr = ctx.assignRhs().expr();
            Register nextRegister = visit(expr);
            Register pairElemRegister = visit(pairElemLhs);
            state.add(new StoreInstruction(nextRegister, pairElemRegister, 0));
            registers.free(nextRegister);
            registers.free(pairElemRegister);
        }

        return null;
    }

    private void addArrayBoundsCheck(Register reg1, Register reg2) {
        state.add(new MoveInstruction(registers.getReturnRegister(), reg1));
        state.add(new MoveInstruction(registers.getReturnRegister(), reg2));
        state.add(new BranchLinkInstruction(Arm11Program.ARRAY_BOUND_NAME));
        state.addArrayBoundError();
    }

    private int getIdentTypeSize(String ident) {
        WaccType type = st.lookupType(ident);
        return getIdSize(type.getId());
    }

    private int getIdSize(int id) {
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
    public Register visitNewPair(NewPairContext ctx) {
        Register heapPtr = heapMalloc(PAIR_HEAP_SIZE);
        boolean isBoolOrChar;
        for (int i = 0; i < ctx.expr().size(); i++) {
            Register nextRegister = visit(ctx.expr(i));
            if (state.getLastInstruction() instanceof MoveInstruction) {
                state.add(new LoadInstruction(Registers.r0, new Operand2(BOOL_CHAR_SIZE)));
                isBoolOrChar = true;
            } else {
                state.add(new LoadInstruction(Registers.r0, new Operand2(WORD_SIZE)));
                isBoolOrChar = false;
            }
            state.add(new BranchLinkInstruction(MALLOC));
            state.add(new StoreInstruction(nextRegister, Registers.r0, 0, isBoolOrChar));
            state.add(new StoreInstruction(Registers.r0, heapPtr, PAIR_SIZE * i));
            registers.free(nextRegister);
        }
        return heapPtr;
    }

    @Override
    public Register visitIdent(IdentContext ctx) {
        Register next = registers.getRegister();
        state.add(new AddInstruction(next, Registers.sp, new Operand2('#', st.getAddress(ctx.getText()))));
        return next;
    }


    @Override
    public Register visitScopeStat(ScopeStatContext ctx) {
        // find size of scope
        StackSizeVisitor sizeVisitor = new StackSizeVisitor();
        int offset = sizeVisitor.getSize(ctx.stat());

        stackOffset += offset;
        if(offset != 0) state.add(new SubInstruction(Registers.sp, Registers.sp, new Operand2('#', offset)));
        st.enterNextScope();

        visitChildren(ctx);

        if(offset != 0) state.add(new AddInstruction(Registers.sp, Registers.sp, new Operand2('#', offset)));
        stackOffset -= offset;
        st.exitScope();

        return null;
    }

    @Override
    public Register visitPairLiter(PairLiterContext ctx) {
        // loads next register with "null"
        Register next = registers.getRegister();
        state.add(new LoadInstruction(next, new Operand2(0)));
        return next;
    }


    @Override
    public Register visitStat(StatContext ctx) {
        // semicolon returns its statments in order, else just visit the children
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
        NewPairContext newPair = ctx.assignRhs().newPair();
        PairElemContext pairElem = ctx.assignRhs().pairElem();
        FuncCallContext funcCall = ctx.assignRhs().funcCall();

        int typeSize;
        boolean isBoolOrChar;
        int offset = 0;
        if (expr != null) {
            Register src = visit(expr);
            typeSize = getTypeSize(type);
            isBoolOrChar = typeSize == BOOL_CHAR_SIZE;
            currOffset += typeSize;
            offset = stackOffset - currOffset;

            state.add(new StoreInstruction(src, Registers.sp, offset, isBoolOrChar));

            registers.free(src);
        }
        if (arrayLiter != null) { // array declaration
            int arrLength = arrayLiter.expr().size();
            typeSize = 0;
            if (arrLength > 0) {
                if (arrayLiter.expr(0).ident() != null) { // nested arrays
                    typeSize = ARRAY_SIZE;
                } else {
                    typeSize = getTypeSize(type);
                }
            }

            Register heapPtr = visitArrayLiter(arrayLiter, typeSize);

            currOffset += ARRAY_SIZE;
            offset = stackOffset - currOffset;
            state.add(new StoreInstruction(heapPtr, Registers.sp, offset));
            registers.free(heapPtr);
        }

        if (newPair != null) {
            Register heapPtr = visit(newPair);
            currOffset += PAIR_SIZE;
            offset = stackOffset - currOffset;
            state.add(new StoreInstruction(heapPtr, Registers.sp, offset));
            registers.free(heapPtr);
        }

        if (pairElem != null) {
            typeSize = getTypeSize(type);
            isBoolOrChar = typeSize == BOOL_CHAR_SIZE;
            currOffset += typeSize;
            offset = stackOffset - currOffset;

            Register nextRegister = visit(pairElem);
            state.add(new LoadInstruction(nextRegister, new Operand2(nextRegister, 0), isBoolOrChar));
            state.add(new StoreInstruction(nextRegister, Registers.sp, offset, isBoolOrChar));
            registers.free(nextRegister);
        }

        if(funcCall != null) {
            currOffset += getTypeSize(type);
            offset = stackOffset - currOffset;
            typeSize = getTypeSize(type);
            isBoolOrChar = typeSize == BOOL_CHAR_SIZE;
            Register next = visitFuncCall(funcCall);
            state.add(new StoreInstruction(next, Registers.sp, offset, isBoolOrChar));
            registers.free(next);
        }

        st.setAddress(ctx.ident().getText(), offset);
        return null;
    }

    private Register heapMalloc(int heapSize) {
        state.add(new LoadInstruction(Registers.r0, new Operand2(heapSize)));
        state.add(new BranchLinkInstruction(MALLOC));
        Register heapPtr = registers.getRegister();
        state.add(new MoveInstruction(heapPtr, Registers.r0));
        registers.free(Registers.r0);
        return heapPtr;
    }

    private int getTypeSize(TypeContext ctx) {
        if (ctx.baseType() != null) {
            return getBaseTypeSize(ctx.baseType());
        }
        if (ctx.pairType() != null) {
            return PAIR_SIZE;
        }
        if (ctx.type() != null) {
            return getTypeSize(ctx.type());
        }
        return 0;
    }

    private int getBaseTypeSize(BaseTypeContext ctx) {
        if (ctx.INT() != null) {
            return INT_SIZE;
        }
        if (ctx.BOOL() != null) {
            return BOOL_SIZE;
        }
        if (ctx.CHAR() != null) {
            return CHAR_SIZE;
        }
        if (ctx.STRING() != null) {
            return STRING_SIZE;
        }
        return 0;
    }

    @Override
    public Register visitReturnStat(ReturnStatContext ctx) {
        // move return expression into return register
        Register returnReg = visit(ctx.expr());
        state.add(new MoveInstruction(Registers.r0, returnReg));

        // reset stack pointer
        if(funcOffset != 0) state.add(new AddInstruction(Registers.sp, Registers.sp, new Operand2('#', funcOffset)));
        funcOffset = 0;

        state.add(new PopInstruction(Registers.pc));
        registers.free(returnReg);
        return null;
    }

    @Override
    public Register visitIncrementStat(IncrementStatContext ctx) {
        String text = ctx.INC_IDENT().getText();
        String ident = text.substring(0, text.length() - 2);
        String operator = text.substring(text.length() - 2, text.length());

        int offset = st.getAddress(ident);
        boolean isBoolOrChar = getIdentTypeSize(ident) == BOOL_CHAR_SIZE;
        Register nextRegister = registers.getRegister();
        state.add(new LoadInstruction(nextRegister, new Operand2(registers.sp, offset), isBoolOrChar));
        if (operator.equals("++")) {
            state.add(new AddInstruction(nextRegister, nextRegister, new Operand2(1)));
        } else {
            state.add(new SubInstruction(nextRegister, nextRegister, new Operand2(1)));
        }
        state.add(new StoreInstruction(nextRegister, registers.sp, offset));
        registers.free(nextRegister);
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
            varType = WaccType.INVALID;
        }

        Register lhsReg = visit(ctx.assignLhs());

        // reading integers
        if(varType.equals(new WaccType(INT))) {
            state.add(new MoveInstruction(Registers.r0, lhsReg));
            state.add(new BranchLinkInstruction(Arm11Program.READ_INT_NAME));

            if(!state.functionDeclared(Arm11Program.READ_INT_NAME)) {
                state.addReadInt();
            }
        }

        // reading characters
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
        // visit printint expression
        Register msgReg = visit(expr);

        // find type of expression, and deal with reference cases
        WaccType exprType = null;
        if(expr.ident() != null) {
            exprType = st.lookupType(expr.ident().getText());

            if(exprType.isArray() || exprType.isPair()) {
                state.add(new MoveInstruction(Registers.r0, msgReg));
                state.add(new BranchLinkInstruction(Arm11Program.PRINT_REF_NAME));
                if(!state.functionDeclared(Arm11Program.PRINT_REF_NAME)) {
                    state.addPrintRef();
                }
            }
        } else if(expr.otherBinaryOper() != null) {
            exprType = WaccType.fromBinaryOp(((TerminalNode) expr.otherBinaryOper().getChild(0)).getSymbol().getType());
        } else if(expr.boolBinaryOper() != null) {
            exprType = WaccType.fromBinaryOp(((TerminalNode) expr.boolBinaryOper().getChild(0)).getSymbol().getType());
        } else if(expr.unaryOper() != null) {
            exprType = WaccType.fromUnaryOp(((TerminalNode) expr.unaryOper().getChild(0)).getSymbol().getType());
        }

        // print null pair
        if(expr.pairLiter() != null) {
            state.add(new MoveInstruction(Registers.r0, msgReg));
            state.add(new BranchLinkInstruction(Arm11Program.PRINT_REF_NAME));
            if(!state.functionDeclared(Arm11Program.PRINT_REF_NAME)) {
                state.addPrintRef();
            }
        }

        // print array elems
        if(expr.arrayElem() != null) {
            state.add(new MoveInstruction(Registers.r0, msgReg));
            String ident = expr.arrayElem().ident().getText();
            WaccType arrayType = st.lookupType(ident);
            int id = arrayType.getId();
            switch (id) {
                case STRING:
                    state.add(new BranchLinkInstruction(Arm11Program.PRINT_STRING_NAME));
                    if (!state.functionDeclared(Arm11Program.PRINT_STRING_NAME)) {
                        state.addPrintString();
                    }
                    break;
                case BOOL:
                    state.add(new BranchLinkInstruction(Arm11Program.PRINT_BOOL_NAME));
                    if (!state.functionDeclared(Arm11Program.PRINT_BOOL_NAME)) {
                        state.addPrintBool();
                    }
                    break;
                case INT:
                    state.add(new BranchLinkInstruction(Arm11Program.PRINT_INT_NAME));
                    if (!state.functionDeclared(Arm11Program.PRINT_INT_NAME)) {
                        state.addPrintInt();
                    }
                    break;
                case CHAR:
                    state.add(new BranchLinkInstruction(Arm11Program.PRINT_CHAR_NAME));
            }
        }

        // print string
        if(expr.STRING_LIT() != null || new WaccType(STRING).equals(exprType)) {
            state.add(new MoveInstruction(Registers.r0, msgReg));

            state.add(new BranchLinkInstruction(Arm11Program.PRINT_STRING_NAME));

            if (!state.functionDeclared(Arm11Program.PRINT_STRING_NAME)) {
                state.addPrintString();
            }

        }

        // print bool
        if(expr.BOOL_LIT() != null || new WaccType(BOOL).equals(exprType)) {
            state.add(new MoveInstruction(Registers.r0, msgReg));
            state.add(new BranchLinkInstruction(Arm11Program.PRINT_BOOL_NAME));

            if (!state.functionDeclared(Arm11Program.PRINT_BOOL_NAME)) {
                state.addPrintBool();
            }
        }

        // print int
        if(expr.INT_LIT() != null || new WaccType(INT).equals(exprType)) {
            state.add(new MoveInstruction(Registers.r0, msgReg));
            state.add(new BranchLinkInstruction(Arm11Program.PRINT_INT_NAME));

            if (!state.functionDeclared(Arm11Program.PRINT_INT_NAME)) {
                state.addPrintInt();
            }
        }

        // print char
        if(expr.CHAR_LIT() != null || new WaccType(CHAR).equals(exprType)) {
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
        // visit expression
        Register nextRegister = visit(ctx.expr());

        // check for null pointer
        state.add(new MoveInstruction(Registers.r0, nextRegister));
        state.add(new BranchLinkInstruction(Arm11Program.NULL_PTR_NAME));
        if(!state.functionDeclared(Arm11Program.NULL_PTR_NAME)) state.addNullPtrError();

        //  load fst or snd element
        int offset = ctx.FST() != null ? FST_OFFSET : SND_OFFSET;
        state.add(new LoadInstruction(nextRegister, new Operand2(nextRegister, offset)));
        return nextRegister;
    }

    @Override
    public Register visitFreeStat(FreeStatContext ctx) {
        // visit expression
        Register expr = visit(ctx.expr());

        // free the pair
        state.add(new MoveInstruction(Registers.r0, expr));
        state.add(new BranchLinkInstruction(Arm11Program.FREE_PAIR_NAME));
        if(!state.functionDeclared(Arm11Program.FREE_PAIR_NAME)) state.addFreePair();

        registers.free(expr);
        return null;
    }

    @Override
    public Register visitWhileStat(WhileStatContext ctx) {
      /* 'While' label start logic. Creates a new numbered 'while' label
       *  and finds the max 'while' label for the current scope 
       */
      StatementCurrentLabel++;
      StatementMaxForScope = Math.max(StatementCurrentLabel, StatementMaxForScope);
      
      // Code generation
      ExprContext condition = (ExprContext) ctx.getChild(1);
      state.add(new BranchInstruction("L" + (StatementCurrentLabel * 2)));
      
      st.enterNextScope();
      state.add(new LabelInstruction("L" + ((StatementCurrentLabel * 2) + 1)));
      visitStat(ctx.stat());
      state.add(new LabelInstruction("L" + (StatementCurrentLabel * 2)));
      Register reg = visitExpr(condition);
      state.add(new CompareInstruction(reg, new Operand2('#', 1)));
      state.add(new BranchEqualInstruction("L" + (StatementCurrentLabel * 2 + 1)));
      registers.free(reg);
      st.exitScope();
      
      /* 'While' label end logic. When the scope has been completed it
       *  sets up the labels so the next 'while' scope will start from the 
       *  right number
       */
      StatementCurrentLabel--;
      if (StatementCurrentLabel == StatementScopeStartingVal) {
        StatementCurrentLabel = StatementMaxForScope;
        StatementScopeStartingVal = StatementMaxForScope;
      }
      
      return null;
      
    }

    @Override
    public Register visitForStat(ForStatContext ctx) {
        StatementCurrentLabel++;
        StatementMaxForScope = Math.max(StatementCurrentLabel, StatementMaxForScope);

        state.add(new BranchInstruction("L" + (StatementCurrentLabel * 2)));

        st.enterNextScope();
        visitStat(ctx.stat(0));
        state.add(new LabelInstruction("L" + ((StatementCurrentLabel * 2) + 1)));
        visitStat(ctx.stat(2));
        state.add(new LabelInstruction("L" + (StatementCurrentLabel * 2)));
        visitStat(ctx.stat(1));
        Register reg = visitExpr(ctx.expr());
        state.add(new CompareInstruction(reg, new Operand2('#', 1)));
        state.add(new BranchEqualInstruction("L" + (StatementCurrentLabel * 2 + 1)));
        registers.free(reg);
        st.exitScope();

        return null;
    }

    @Override
    public Register visitIfStat(IfStatContext ctx) {  
      
      /* 'If' label start logic. Creates a new numbered 'if' label
       *  and finds the max 'if' label for the current scope 
       */
      StatementCurrentLabel++;
      StatementMaxForScope = Math.max(StatementCurrentLabel, StatementMaxForScope);
      
      // Code Generation 
      ExprContext condition = (ExprContext) ctx.getChild(1);
      Register reg = visitExpr(condition);
      state.add(new CompareInstruction(reg, new Operand2('#',0)));
      state.add(new BranchLinkEqualInstruction("L" + (StatementCurrentLabel * 2)));
      
      registers.free(reg);
      st.enterNextScope();
      reg = visitStat(ctx.stat(0));
      state.add(new BranchInstruction("L" + (StatementCurrentLabel * 2 + 1)));
      st.exitScope();
      
      registers.free(reg);
      st.enterNextScope();
      state.add(new LabelInstruction("L" + (StatementCurrentLabel * 2)));
      visit(ctx.stat(1));
      st.exitScope();
      
      state.add(new LabelInstruction("L" + (StatementCurrentLabel * 2 + 1)));
      
      /* 'If' label end logic. When the scope has been completed it
       *  sets up the labels so the next 'if' scope will start from the 
       *  right number
       */
      StatementCurrentLabel--;
      if (StatementCurrentLabel == StatementScopeStartingVal) {
        StatementCurrentLabel = StatementMaxForScope;
        StatementScopeStartingVal = StatementMaxForScope;
      }
      
      return null;
    }
    
    @Override
    public Register visitIfStatSmall(IfStatSmallContext ctx) {  
      
      /* 'If' label start logic. Creates a new numbered 'if' label
       *  and finds the max 'if' label for the current scope 
       */
      StatementCurrentLabel++;
      StatementMaxForScope = Math.max(StatementCurrentLabel, StatementMaxForScope);
      
      // Code Generation 
      ExprContext condition = (ExprContext) ctx.getChild(1);
      Register reg = visitExpr(condition);
      state.add(new CompareInstruction(reg, new Operand2('#',0)));
      state.add(new BranchLinkEqualInstruction("L" + (StatementCurrentLabel * 2)));
      
      registers.free(reg);
      st.enterNextScope();
      reg = visitStat(ctx.stat());
      state.add(new BranchInstruction("L" + (StatementCurrentLabel * 2 + 1)));
      st.exitScope();
      
      registers.free(reg);
      st.enterNextScope();
      state.add(new LabelInstruction("L" + (StatementCurrentLabel * 2)));
     
      
      state.add(new LabelInstruction("L" + (StatementCurrentLabel * 2 + 1)));
      
      /* 'If' label end logic. When the scope has been completed it
       *  sets up the labels so the next 'if' scope will start from the 
       *  right number
       */
      StatementCurrentLabel--;
      if (StatementCurrentLabel == StatementScopeStartingVal) {
        StatementCurrentLabel = StatementMaxForScope;
        StatementScopeStartingVal = StatementMaxForScope;
      }
      
      return null;
    }

    private Register visitArrayLiter(ArrayLiterContext ctx, int typeSize) {
        int arrLength = ctx.expr().size();
        int heapSize = arrLength * typeSize + INT_SIZE; // INT_SIZE IS TO STORE LENGTH OF ARRAY
        boolean isBoolOrChar = typeSize == BOOL_CHAR_SIZE;

        // set up heap memory allocation
        Register heapPtr = heapMalloc(heapSize);

        // process each array elem
        for (int i = 0; i < arrLength; i++) {
            Register src = visit(ctx.expr(i));
            state.add(new StoreInstruction(src, heapPtr, INT_SIZE + i * typeSize, isBoolOrChar));
            registers.free(src);
        }
        // process length
        Register lengthReg = registers.getRegister();
        state.add(new LoadInstruction(lengthReg, new Operand2(arrLength)));
        state.add(new StoreInstruction(lengthReg, heapPtr, 0));
        registers.free(lengthReg);
        return heapPtr;
    }
}
