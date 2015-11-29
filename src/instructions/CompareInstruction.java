package instructions;

import util.Register;

public class CompareInstruction extends Instruction {
    private final Register reg;
    private final int i;

    public CompareInstruction(Register reg, int i) {
        this.reg = reg;
        this.i = i;
    }

    @Override
    public String toCode() {
        return "CMP " + reg + ", #" + i;
    }
}
