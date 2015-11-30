package instructions;

import util.Register;

public class LoadSignedByteInstruction extends Instruction {
    private final Register rDest;
    private final Register rSrc;

    public LoadSignedByteInstruction(Register rDest, Register rSrc) {
        this.rDest = rDest;
        this.rSrc = rSrc;
    }

    @Override
    public String toCode() {
        return "LDRSB " + rDest + ", [" + rSrc + "]";
    }
}
