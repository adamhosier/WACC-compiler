package instructions;

import util.Register;

public class LoadNotEqualInstruction extends Instruction {
    private final Register reg;
    private Operand2 op;

    public LoadNotEqualInstruction(Register reg, Operand2 op) {
        this.reg = reg;
        this.op = op;
    }

    @Override
    public String toCode() {
        return "LDRNE " + reg + ", =" + op;
    }
}
