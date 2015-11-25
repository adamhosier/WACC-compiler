package instructions;

import util.Register;

public class LoadInstruction extends Instruction {
    private Register rSrc;
    private int iSrc;

    private boolean isImmediate = false;
    private Register rDest;

    public LoadInstruction(Register rDest, int iSrc) {
        this.rDest = rDest;
        this.iSrc = iSrc;
        isImmediate = true;
    }

    public LoadInstruction(Register rDest, Register rSrc) {
        this.rDest = rDest;
        this.rSrc = rSrc;
    }

    @Override
    public String toCode() {
        return "LDR " + rDest + ", " + (isImmediate ? "=" + iSrc : "[" + rDest + "]");
    }
}
