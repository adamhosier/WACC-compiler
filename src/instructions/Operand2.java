package instructions;

import util.Register;

public class Operand2 {

    private boolean isReg = false;
    private boolean isImm = false;
    private Register reg;
    int offset = 0;

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

    public Operand2(Register reg, int offset) {
        this(reg, true);
        this.offset = offset;
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
        if(isReg) return (isImm ? "[" : "") + reg + (offset != 0 ? ", #" + offset : "") + (isImm ? "]" : "");
        if(isInt) return "=" + i;
        if(isStr) return "=" + s;
        return null;
    }
}
