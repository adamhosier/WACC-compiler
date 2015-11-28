package instructions;

import util.Register;

public class SubInstruction extends Instruction {
    private final Register rDst;
    private final Register rOp;
    private final int op;


    public SubInstruction(Register rDst, Register rOp, int op) {
        this.rDst = rDst;
        this.rOp = rOp;
        this.op = op;
    }

    @Override
    public String toCode() {
        return "SUB " + rDst + ", " + rOp + ", #" + op;
    }
}
