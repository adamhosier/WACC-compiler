package instructions;

import util.Register;

public class LoadEqualInstruction extends Instruction {
    private final Register reg;
    private final Operand2 op;

    public LoadEqualInstruction(Register reg, Operand2 func) {
        this.reg = reg;
        this.op = func;
    }

    @Override
    public String toCode() {
        return "LDREQ " + reg + ", " + op;
    }
}
