package instructions;

import util.Register;

public class MoveGreaterThanEqualInstruction extends Instruction {
    private final Register rDest;

    private final Operand2 op;

    public MoveGreaterThanEqualInstruction(Register rDest, Operand2 op) {
        this.rDest = rDest;
        this.op = op;
    }

    @Override
    public String toCode() {
        return "MOVGE " + rDest + ", " + op;
    }
}