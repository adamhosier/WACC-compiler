package util;

import instructions.*;

import java.util.LinkedList;
import java.util.List;

//.text
//   ???
//.global Main
//main:

public class Arm11Program {

    List<Instruction> instructions = new LinkedList<>();

    public Arm11Program() {
    }

    public void add(Instruction ins) {
        instructions.add(ins);
    }

    public void startFunction(String ins) {
        instructions.add(new LabelInstruction(ins));
        instructions.add(new PushInstruction(Registers.lr));
    }

    public void endFunction() {
        instructions.add(new PopInstruction(Registers.pc));
        instructions.add(new LtorgDirective());
    }

    public String toCode() {
        StringBuilder program = new StringBuilder();

        //generate .data

        //generate .text

        for(Instruction ins : instructions) {
            for(int i = 0; i < ins.indentation; i++) program.append("\t");
            program.append(ins.toCode());
            program.append('\n');
        }

        return program.toString();
    }
}
