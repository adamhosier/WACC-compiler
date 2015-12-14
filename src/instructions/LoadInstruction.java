package instructions;

import util.Register;

public class LoadInstruction extends Instruction {
    private final Register rDest;

    private final Operand2 op;
    private boolean isByte;
    public LoadInstruction(Register rDest, Operand2 op) {
        this.rDest = rDest;
        this.op = op;
    }

    public LoadInstruction(Register rDest, Operand2 op, boolean isByte) {
        this.rDest = rDest;
        this.op = op;
        this.isByte = isByte;
    }

    public Register getDest() {
        return rDest;
    }

    public Operand2 getSrc() {
        return op;
    }

    public Object getOffset() {
        return op.getOffset();
    }

    @Override
    public String toCode() {
        return (!isByte ?  "LDR " : "LDRSB ") + rDest + ", " + op;
    }
}
