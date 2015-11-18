import instructions.Instruction;
import instructions.LabelInstruction;

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

    public void addFunction(String ins) {
        instructions.add(new LabelInstruction(ins));
        instructions.add(new PushInstruction());
    }

    public String toCode() {
        StringBuilder program = new StringBuilder();

        //generate .data

        //generate .text

        for(Instruction ins : instructions) {
            program.append(ins.toCode());
            program.append('\n');
        }

        return program.toString();
    }
}
