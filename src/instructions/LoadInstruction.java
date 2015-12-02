package instructions;

import util.Register;

public class LoadInstruction extends Instruction {
    private Register rDest;
    private final Operand2 op;

    public LoadInstruction(Register rDest, Operand2 op) {
        this.rDest = rDest;
        this.op = op;
    }

    @Override
    public String toCode() {
        return "LDR " + rDest + ", " + op;
    }
}
