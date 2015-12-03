package instructions;

import util.Register;

public class LoadCarrySetInstruction extends Instruction {
    private final Register reg;
    private final Operand2 op;

    public LoadCarrySetInstruction(Register reg, Operand2 func) {
        this.reg = reg;
        this.op = func;
    }

    @Override
    public String toCode() {
        return "LDRCS " + reg + ", " + op;
    }
}
