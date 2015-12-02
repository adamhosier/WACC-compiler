package instructions;

import util.Register;

public class AndInstruction extends Instruction {
    private final Register rDest;
    private final Register rLhs;
    private final Operand2 rhs;

    public AndInstruction(Register rDest, Register rLhs, Operand2 rhs) {
        this.rDest = rDest;
        this.rLhs = rLhs;
        this.rhs = rhs;
    }

    @Override
    public String toCode() {
        return "AND  " + rDest + ", " + rLhs + ", " + rhs;
    }
}
