package instructions;

import util.Register;

public class NegateInstruction extends Instruction {
    private final Register rDest;
    private final Register rSrc;
    private final Operand2 op;

    public NegateInstruction(Register rDest, Register rSrc, Operand2 op) {
        this.rDest = rDest;
        this.rSrc = rSrc;
        this.op = op;
    }

    @Override
    public String toCode() {
        return "RSBS " + rDest + ", " + rSrc + ", " + op;
    }
}
