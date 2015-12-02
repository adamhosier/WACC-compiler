package instructions;

import util.Register;

public class LoadSignedByteInstruction extends Instruction {
    private final Register rDest;
    private Operand2 op;

    public LoadSignedByteInstruction(Register rDest, Operand2 op) {
        this.rDest = rDest;
        this.op = op;
    }

    @Override
    public String toCode() {
        return "LDRSB " + rDest + ", " + op;
    }
}
