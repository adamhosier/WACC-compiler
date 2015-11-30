package instructions;

import util.Register;

public class LoadInstruction extends Instruction {
    private Register rSrc;
    private Object iSrc;

    private boolean isImmediate = false;
    private Register rDest;
    private int offset;

    public LoadInstruction(Register rDest, int iSrc) {
        this.rDest = rDest;
        this.iSrc = iSrc;
        isImmediate = true;
    }

    public LoadInstruction(Register rDest, String iSrc) {
        this.rDest = rDest;
        this.iSrc = iSrc;
        isImmediate = true;
    }

    public LoadInstruction(Register rDest, Register rSrc) {
        this.rDest = rDest;
        this.rSrc = rSrc;
    }

    public LoadInstruction(Register rDest, Register rSrc, int offset) {
        this(rDest, rSrc);
        this.offset = offset;
    }

    @Override
    public String toCode() {
        return "LDR " + rDest + ", " + (isImmediate ? "=" + iSrc
                : "[" + rSrc + (offset != 0 ? ", " + "#" + offset + "]" : "]"));
    }
}
