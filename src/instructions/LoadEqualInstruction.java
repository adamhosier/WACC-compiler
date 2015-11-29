package instructions;

import util.Register;

public class LoadEqualInstruction extends Instruction {
    private final Register reg;
    private final String func;

    public LoadEqualInstruction(Register reg, String func) {
        this.reg = reg;
        this.func = func;
    }

    @Override
    public String toCode() {
        return "LDREQ " + reg + ", =" + func;
    }
}
