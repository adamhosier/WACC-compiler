package instructions;

import util.Register;

public class MoveInstruction extends Instruction {
    private Register rDest;
    private Register rSrc;
    private int iSrc;
    private char cSrc;
    private boolean isImmediate = false;
    private boolean isChar = false;

    public MoveInstruction(Register rDest, Register rSrc) {
        this.rDest = rDest;
        this.rSrc = rSrc;
    }

    public MoveInstruction(Register rDest, char cSrc) {
        this.rDest = rDest;
        this.cSrc = cSrc;
        isImmediate = true;
        isChar = true;
    }

    public MoveInstruction(Register rDest, int iSrc) {
        this.rDest = rDest;
        this.iSrc = iSrc;
        isImmediate = true;
    }

    public Register getSrc() {
        return rSrc;
    }

    public Register getDest() {
        return rDest;
    }

    public boolean isRegisterSrc() {
        return !isImmediate && !isChar;
    }

    @Override
    public String toCode() {
        return "MOV " + rDest + ", " + (isImmediate ? ("#" + (isChar ? "'" + cSrc + "'" : iSrc)) :  rSrc);
    }
}
