package instructions;

import util.Register;

public class MoveNotEqualInstruction extends Instruction {
    private final Register rDest;

    private final Operand2 op;

    public MoveNotEqualInstruction(Register rDest, Operand2 op) {
        this.rDest = rDest;
        this.op = op;
    }

    @Override
    public String toCode() {
        return "MOVNE " + rDest + ", " + op;
    }
}
