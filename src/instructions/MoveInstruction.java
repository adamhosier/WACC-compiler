package instructions;

import util.Register;

public class MoveInstruction extends Instruction {
    private Register rDest;
    private Register rSrc;
    private int iSrc;
    private boolean isImmediate = false;

    public MoveInstruction(Register rDest, Register rSrc) {
        this.rDest = rDest;
        this.rSrc = rSrc;
    }

    public MoveInstruction(Register rDest, int iSrc) {
        this.rDest = rDest;
        this.iSrc = iSrc;
        isImmediate = true;
    }

    public MoveInstruction(Register rDest, char iSrc) {
        this.rDest = rDest;
        this.iSrc = iSrc;
        isImmediate = true;
    }

    @Override
    public String toCode() {
        return "MOV " + rDest + ", " + (isImmediate ? "#" + iSrc :  rDest);
    }
}
