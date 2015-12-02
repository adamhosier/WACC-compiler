package instructions;

import util.Register;

public class AddInstruction extends Instruction {
    private final Register rDest;
    private final Register rSrc;
    private final Operand2 op;

    public boolean setFlags = false;

    public AddInstruction(Register rDest, Register rSrc, Operand2 op) {
        this.rDest = rDest;
        this.rSrc = rSrc;
        this.op = op;
    }


    @Override
    public String toCode() {
        return "ADD" + (setFlags ? "S " : " ") + rDest + ", " + rSrc + ", " + op;
    }
}
