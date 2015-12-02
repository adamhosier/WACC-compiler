package instructions;

import util.Register;

/**
 * Created by ah3114 on 02/12/15.
 */
public class MultiplyInstruction extends Instruction {
    private final Register rDest;
    private final Register rOverflow;
    private final Register lhs;
    private final Register rhs;

    public MultiplyInstruction(Register rDest, Register rOverflow, Register lhs, Register rhs) {
        this.rDest = rDest;
        this.rOverflow = rOverflow;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String toCode() {
        return "SMULL " + rDest + ", " + rOverflow + ", " + lhs + ", " + rhs;
    }
}
