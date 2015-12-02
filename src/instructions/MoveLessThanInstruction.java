package instructions;

import util.Register;

public class MoveLessThanInstruction extends Instruction {
    private final Register rDest;

    private final Operand2 op;

    public MoveLessThanInstruction(Register rDest, Operand2 op) {
        this.rDest = rDest;
        this.op = op;
    }

    @Override
    public String toCode() {
        return "MOVLT " + rDest + ", " + op;
    }
}