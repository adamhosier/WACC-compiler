package instructions;

import util.Register;

public class Operand2 {

    public static Operand2 any = new Operand2();

    private boolean isReg = false;
    private boolean isImm = false;
    private Register reg;
    int offset = 0;

    private boolean isInt = false;
    private char iChar = '=';
    private int i;

    private boolean isStr = false;
    private String s;
    
    private boolean asr;
    private int asrVal;

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

    public Operand2(char iChar, int i) {
        this(i);
        this.iChar = iChar;
    }

    public Operand2(String s) {
        this.s = s;
        isStr = true;
    }

    public Operand2() {
    }

    public void setAsr(int asr) {
        this.asr = true;
        this.asrVal = asr;
    }

    public Register getReg() {
        return reg;
    }

    @Override
    public String toString() {
        if(isReg) {
            if(isImm) return "[" + reg + (offset != 0 ? ", #" + offset : "") + "]";
            if(asr) return reg + ", ASR #" + asrVal;
            return reg.toString();
        }
        if(isInt) return iChar + "" + i;
        if(isStr) return "=" + s;
        return null;
    }
}
