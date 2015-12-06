package instructions;

import util.Register;

public class StoreInstruction extends Instruction {
    private final Register rSrc;
    private final Register rDst;
    private final int offset;
    private boolean isByte = false;
    private boolean preIndex = false;

    public StoreInstruction(Register rSrc, Register rDst, int offset) {
        this.rSrc = rSrc;
        this.rDst = rDst;
        this.offset = offset;
    }

    // when isByte is set the store instruction will be STRB
    public StoreInstruction(Register rSrc, Register rDst, int offset, boolean isByte) {
        this.rSrc = rSrc;
        this.rDst = rDst;
        this.offset = offset;
        this.isByte = isByte;
    }

    public void setPreIndex() {
        preIndex = true;
    }

    @Override
    public String toCode() {
        return (!isByte ? "STR " : "STRB ") + rSrc + ", "
                + "[" + rDst + (offset != 0 ? ", " + "#" + offset + "]" : "]") + (preIndex ? "!" : "");
    }
}
