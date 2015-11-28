package util;

import instructions.*;

import java.util.*;

//.text
//   ???
//.global Main
//main:

public class Arm11Program {

    public static final String PRINT_FUNC_NAME = "p_print_string";
    public static final String PRINTLN_FUNC_NAME = "p_print_ln";

    Map<String, List<Instruction>> functions = new LinkedHashMap<>();
    Stack<List<Instruction>> scope = new Stack<>();
    List<Instruction> currentFunction;
    List<Instruction> globalCode = new LinkedList<>();
    int numMsgLabels = 0;
    private String printFunc;
    private String printlnFunc;

    public Arm11Program() {
        functions.put("global", globalCode);
        scope.push(globalCode);
        currentFunction = globalCode;
    }

    public void add(Instruction ins) {
        if(currentFunction == null) globalCode.add(ins);
        else currentFunction.add(ins);
    }

    public String addMsgLabel(String label) {
        if(numMsgLabels == 0) globalCode.add(new DataLabel());
        MsgLabel instruction = new MsgLabel(label, numMsgLabels);
        globalCode.add(instruction);
        numMsgLabels++;
        return instruction.getIdent();
    }

    public void addPrintFunc() {
        String label = addMsgLabel("%.*s\\0");
        printFunc = label;
        startFunction(PRINT_FUNC_NAME);
        add(new LoadInstruction(Registers.r1, Registers.r0));
        add(new AddInstruction(Registers.r2, Registers.r0, 4));
        add(new LoadInstruction(Registers.r0, printFunc));
        add(new AddInstruction(Registers.r0, Registers.r0, 4));
        add(new BranchLinkInstruction("printf"));
        add(new MoveInstruction(Registers.r0, 0));
        add(new BranchLinkInstruction("fflush"));
        endFunction();
    }


    public void addPrintlnFunc() {
        String label = addMsgLabel("\\0");
        printlnFunc = label;
        startFunction(PRINTLN_FUNC_NAME);
        add(new LoadInstruction(Registers.r0, printlnFunc));
        add(new AddInstruction(Registers.r0, Registers.r0, 4));
        add(new BranchLinkInstruction("puts"));
        add(new MoveInstruction(Registers.r0, 0));
        add(new BranchLinkInstruction("fflush"));
        endFunction();
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
        currentFunction.add(new LtorgDirective());
        scope.pop();
        currentFunction = scope.peek();
    }

    public boolean functionDeclared(String name) {
        return functions.containsKey(name);
    }

    public String getPrintFunc() {
        return printFunc;
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
