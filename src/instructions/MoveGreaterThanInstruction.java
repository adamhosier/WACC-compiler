package instructions;

import util.Register;

public class MoveGreaterThanInstruction extends Instruction {
    private final Register rDest;

    private final Operand2 op;

    public MoveGreaterThanInstruction(Register rDest, Operand2 op) {
        this.rDest = rDest;
        this.op = op;
    }

    @Override
    public String toCode() {
        return "MOVGT " + rDest + ", " + op;
    }
}