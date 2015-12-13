package instructions;

import sun.security.jgss.spi.GSSNameSpi;
import util.Register;

public class PopInstruction extends Instruction {
    private Register r;

    public PopInstruction(Register r) {
        this.r = r;
    }

    public Register getReg() {
        return r;
    }

    @Override
    public String toCode() {
        return "POP {" + r.toString() + "}";
    }
}
