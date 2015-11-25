package instructions;

import util.Register;

public class MoveInstruction extends Instruction {
    private Register rDest;
    private Register rSrc;

    public MoveInstruction(Register rDest, Register rSrc) {
        this.rDest = rDest;
        this.rSrc = rSrc;
    }

    @Override
    public String toCode() {
        return "MOV " + rDest + ", " + rSrc;
    }
}
