package instructions;

import util.Register;

public class PopInstruction extends Instruction {
    private Register r;

    public PopInstruction(Register r) {
        this.r = r;
    }

    @Override
    public String toCode() {
        return "POP {" + r.toString() + "}";
    }
}
