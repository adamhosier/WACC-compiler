package instructions;

import util.Register;

public class MoveLessThanEqualInstruction extends Instruction {
    private final Register rDest;

    private final Operand2 op;

    public MoveLessThanEqualInstruction(Register rDest, Operand2 op) {
        this.rDest = rDest;
        this.op = op;
    }

    @Override
    public String toCode() {
        return "MOVLE " + rDest + ", " + op;
    }
}