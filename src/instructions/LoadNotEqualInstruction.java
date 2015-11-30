package instructions;

import util.Register;

public class LoadNotEqualInstruction extends Instruction {
    private final Register reg;
    private final String func;

    public LoadNotEqualInstruction(Register reg, String func) {
        this.reg = reg;
        this.func = func;
    }

    @Override
    public String toCode() {
        return "LDRNE " + reg + ", =" + func;
    }
}
