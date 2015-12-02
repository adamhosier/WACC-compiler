package instructions;

import util.Register;

public class Operand2 {

    private boolean isReg = false;
    private boolean isImm = false;
    private Register reg;

    private boolean isInt = false;
    private int i;

    private boolean isStr = false;
    private String s;

    public Operand2(Register reg) {
        this.reg = reg;
        isReg = true;
    }

    public Operand2(Register reg, boolean isImm) {
        this(reg);
        this.isImm = isImm;
    }

    public Operand2(int i) {
        this.i = i;
        isInt = true;
    }

    public Operand2(String s) {
        this.s = s;
        isStr = true;
    }

    @Override
    public String toString() {
        if(isReg) return (isImm ? "[" : "") + reg + (isImm ? "]" : "");
        if(isInt) return "#" + i;
        if(isStr) return "=" + s;
        return null;
    }
}
