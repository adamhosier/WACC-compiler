package instructions;

import util.Register;

public class LoadLessThanInstruction extends Instruction {
    private final Register reg;
    private Operand2 op;

    public LoadLessThanInstruction(Register reg, Operand2 op) {
        this.reg = reg;
        this.op = op;
    }

    @Override
    public String toCode() {
        return "LDRLT " + reg + ", " + op;
    }
}
