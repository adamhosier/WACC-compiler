package util;

import instructions.*;

import java.util.*;

//.text
//   ???
//.global Main
//main:

public class Arm11Program {

    public static final String PRINT_STRING_NAME = "p_print_string";
    public static final String PRINT_BOOL_NAME = "p_print_bool";
    public static final String PRINT_INT_NAME = "p_print_int";
    public static final String PRINTLN_NAME = "p_print_ln";
    public static final String PRINT_CHAR_NAME = "putchar";
    public static final String READ_INT_NAME = "p_read_int";
    public static final String READ_CHAR_NAME = "p_read_char";

    public static String decode(String input) {
        return input.replace("\\0", "\0").replace("\\b", "\b").replace("\\n", "\n").replace("\\f", "\f").replace("\\r", "\r").replace("\\\"", "\"").replace("\\'", "'").replace("\\\\", "\\");
    }

    Map<String, List<Instruction>> functions = new LinkedHashMap<>();
    Stack<List<Instruction>> scope = new Stack<>();
    List<Instruction> currentFunction;
    List<Instruction> globalCode = new LinkedList<>();
    int numMsgLabels = 0;
    private String printStringFunc;
    private String printlnFunc;
    private String printTrueFunc;
    private String printFalseFunc;
    private String printIntFunc;
    private String readIntFunc;
    private String readCharFunc;

    public Arm11Program() {
        functions.put("global", globalCode);
        scope.push(globalCode);
        currentFunction = globalCode;
    }

    public void add(Instruction ins) {
        if(currentFunction == null) globalCode.add(ins);
        else currentFunction.add(ins);
    }

    public String addMsgLabel(String msg) {
        if(numMsgLabels == 0) globalCode.add(new DataLabel());
        MsgLabel instruction = new MsgLabel(msg, numMsgLabels);
        globalCode.add(instruction);
        numMsgLabels++;
        return instruction.getIdent();
    }

    public String getMsgLabel(String msg) {
        for(Instruction ins : globalCode) {
            if(ins instanceof MsgLabel && ((MsgLabel) ins).getMsg().equals(msg)) {
                return ((MsgLabel) ins).getIdent();
            }
        }
        return addMsgLabel(msg);
    }

    public void addPrintString() {
        printStringFunc = getMsgLabel("%.*s\\0");
        startFunction(PRINT_STRING_NAME);
        add(new LoadInstruction(Registers.r1, Registers.r0));
        add(new AddInstruction(Registers.r2, Registers.r0, 4));
        add(new LoadInstruction(Registers.r0, printStringFunc));
        endPrintFunction("printf");
    }


    public void addPrintBool() {
        printTrueFunc = getMsgLabel("true\\0");
        printFalseFunc = getMsgLabel("false\\0");
        startFunction(PRINT_BOOL_NAME);
        add(new CompareInstruction(Registers.r0, 0));
        add(new LoadNotEqualInstruction(Registers.r0, printTrueFunc));
        add(new LoadEqualInstruction(Registers.r0, printFalseFunc));
        endPrintFunction("printf");
    }

    public void addPrintInt() {
        printIntFunc = getMsgLabel("%d\\0");
        startFunction(PRINT_INT_NAME);
        add(new MoveInstruction(Registers.r1, Registers.r0));
        add(new LoadInstruction(Registers.r0, printIntFunc));
        endPrintFunction("printf");
    }

    public void addPrintlnFunc() {
        printlnFunc = addMsgLabel("\\0");
        startFunction(PRINTLN_NAME);
        add(new LoadInstruction(Registers.r0, printlnFunc));
        endPrintFunction("puts");
    }

    public void addReadInt() {
        readIntFunc = getMsgLabel("%d\\0");
        startFunction(READ_INT_NAME);
        add(new MoveInstruction(Registers.r1, Registers.r0));
        add(new LoadInstruction(Registers.r0, readIntFunc));
        endReadFunction();
    }

    public void addReadChar() {
        readCharFunc = getMsgLabel(" %c\\0");
        startFunction(READ_CHAR_NAME);
        add(new MoveInstruction(Registers.r1, Registers.r0));
        add(new LoadInstruction(Registers.r0, readCharFunc));
        endReadFunction();
    }

    public void startFunction(String name) {
        currentFunction = new LinkedList<>();
        functions.put(name, currentFunction);
        currentFunction.add(new LabelInstruction(name));
        currentFunction.add(new PushInstruction(Registers.lr));
        scope.push(currentFunction);
    }

    public void endFunction() {
        currentFunction.add(new PopInstruction(Registers.pc));
        scope.pop();
        currentFunction = scope.peek();
    }

    public void endUserFunction() {
        currentFunction.add(new PopInstruction(Registers.pc));
        currentFunction.add(new LtorgDirective());
        scope.pop();
        currentFunction = scope.peek();
    }


    public void endPrintFunction(String branch) {
        add(new AddInstruction(Registers.r0, Registers.r0, 4));
        add(new BranchLinkInstruction(branch));
        add(new MoveInstruction(Registers.r0, 0));
        add(new BranchLinkInstruction("fflush"));
        endFunction();
    }

    private void endReadFunction() {
        add(new AddInstruction(Registers.r0, Registers.r0, 4));
        add(new BranchLinkInstruction("scanf"));
        endFunction();
    }

    public boolean functionDeclared(String name) {
        return functions.containsKey(name);
    }

    public String toCode() {
        StringBuilder program = new StringBuilder();

        for(List<Instruction> func : functions.values()) {
            for(Instruction ins : func) {
                for(int i = 0; i < ins.indentation; i++) program.append("\t");
                program.append(ins.toCode());
                program.append('\n');
            }
            //program.append('\n');
        }

        return program.toString();
    }
}
