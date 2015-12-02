package instructions;

import util.Register;

public class OrInstruction extends Instruction {
    private final Register rDest;
    private final Register lhs;
    private final Operand2 rhs;

    public OrInstruction(Register rDest, Register lhs, Operand2 rhs) {
        this.rDest = rDest;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String toCode() {
        return "ORR " + rDest + ", " + lhs + ", " + rhs;
    }
}
