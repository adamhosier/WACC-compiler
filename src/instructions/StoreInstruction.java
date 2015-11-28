package instructions;

import util.Register;

public class StoreInstruction extends Instruction {
    private final Register rSrc;
    private final Register rDst;
    private final int offset;
    private boolean isByte;

    public StoreInstruction(Register rSrc, Register rDst, int offset) {
        this.rSrc = rSrc;
        this.rDst = rDst;
        this.offset = offset;
    }

    public StoreInstruction(Register rSrc, Register rDst, int offset, boolean isByte) {
        this.rSrc = rSrc;
        this.rDst = rDst;
        this.offset = offset;
        this.isByte = isByte;
    }

    @Override
    public String toCode() {
        return (!isByte ? "STR " : "STRB ") + rSrc + ", "
                + "[" + rDst + (offset != 0 ? ", " + offset + "]" : "]");
    }
}
