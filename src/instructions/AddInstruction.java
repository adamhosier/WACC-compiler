package instructions;

import util.Register;

public class AddInstruction extends Instruction {
    private final Register rDest;
    private final Register rSrc;
    private final int amt;

    public AddInstruction(Register rDest, Register rSrc, int amt) {
        this.rDest = rDest;
        this.rSrc = rSrc;
        this.amt = amt;
    }

    @Override
    public String toCode() {
        return "ADD " + rDest + ", " + rSrc + ", #" + amt;
    }
}
