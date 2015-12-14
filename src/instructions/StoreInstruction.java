package instructions;

import sun.security.jgss.spi.GSSNameSpi;
import util.Register;

public class StoreInstruction extends Instruction {
    private final Register rSrc;
    private final Register rDest;
    private final int offset;
    private boolean isByte = false;
    private boolean preIndex = false;

    public StoreInstruction(Register rSrc, Register rDst, int offset) {
        this.rSrc = rSrc;
        this.rDest = rDst;
        this.offset = offset;
    }

    // when isByte is set the store instruction will be STRB
    public StoreInstruction(Register rSrc, Register rDst, int offset, boolean isByte) {
        this.rSrc = rSrc;
        this.rDest = rDst;
        this.offset = offset;
        this.isByte = isByte;
    }

    public Register getSrc() {
        return rSrc;
    }

    public Register getDest() {
        return rDest;
    }

    public void setPreIndex() {
        preIndex = true;
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public String toCode() {
        return (!isByte ? "STR " : "STRB ") + rSrc + ", "
                + "[" + rDest + (offset != 0 ? ", " + "#" + offset + "]" : "]") + (preIndex ? "!" : "");
    }
}
