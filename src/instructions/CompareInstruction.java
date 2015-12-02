package instructions;

import util.Register;

public class CompareInstruction extends Instruction {
    private final Register reg;
    private final Operand2 op;

    public CompareInstruction(Register reg, Operand2 op) {
        this.reg = reg;
        this.op = op;
    }

    @Override
    public String toCode() {
        return "CMP " + reg + ", " + op;
    }
}
