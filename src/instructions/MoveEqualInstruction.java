package instructions;

import util.Register;

public class MoveEqualInstruction extends Instruction {
    private final Register rDest;

    private final Operand2 op;

    public MoveEqualInstruction(Register rDest, Operand2 op) {
        this.rDest = rDest;
        this.op = op;
    }

    @Override
    public String toCode() {
        return "MOVEQ " + rDest + ", " + op;
    }
}
