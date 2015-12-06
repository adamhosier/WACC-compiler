package instructions;

import util.Register;

public class ExclusiveOrInstruction extends Instruction {
    private final Register rDest;
    private final Register lhs;
    private final Operand2 rhs;

    public ExclusiveOrInstruction(Register rDest, Register lhs, Operand2 rhs) {
        this.rDest = rDest;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String toCode() {
        return "EOR " + rDest + ", " + lhs + ", " + rhs;
    }
}
