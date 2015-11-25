package util;

import java.util.*;

public class Registers {

    private static final int MIN_REG_ID = 0;
    private static final int MAX_REG_ID = 15;
    private static final int MIN_RETURN_REG_ID = MIN_REG_ID;
    private static final int MAX_RETURN_REG_ID = 3;
    private static final int MIN_GENERAL_REG_ID = MAX_RETURN_REG_ID + 1;
    private static final int MAX_GENERAL_REG_ID = 12;
    private static final int MIN_SPECIAL_REG_ID = MAX_GENERAL_REG_ID + 1;
    private static final int MAX_SPECIAL_REG_ID = MAX_REG_ID;

    public static Register r0  = new Register("r0");
    public static Register r1  = new Register("r1");
    public static Register r2  = new Register("r2");
    public static Register r3  = new Register("r3");
    public static Register r4  = new Register("r4");
    public static Register r5  = new Register("r5");
    public static Register r6  = new Register("r6");
    public static Register r7  = new Register("r7");
    public static Register r8  = new Register("r8");
    public static Register r9  = new Register("r9");
    public static Register r10 = new Register("r10");
    public static Register r11 = new Register("r11");
    public static Register r12 = new Register("r12");
    public static Register r13 = new Register("sp");
    public static Register r14 = new Register("lr");
    public static Register r15 = new Register("pc");
    public static Register sp  = new Register("sp");
    public static Register lr  = new Register("lr");
    public static Register pc  = new Register("pc");

    private Map<String, Register> regs = new HashMap<>();
    private Set<Register> inUse = new HashSet<>();

    public Registers() {
        //general purpose
        for(int i = MIN_REG_ID; i <= MAX_REG_ID; i++) {
            String name = intToName(i);
            regs.put(name, new Register(name));
        }
    }

    /*
     * Given an integer id, returns the string name associated with it
     * e.g. 4 becomes "r4", 13 becomes "sp"
     */
    private String intToName(int i) {
        if(i <= MAX_GENERAL_REG_ID) {
            return "r" + i;
        }
        switch(i) {
            case 13: return "sp";
            case 14: return "lr";
            case 15: return "pc";
            default: throw new RuntimeException("Invalid register id " + i + " passed to Registers::intToName");
        }
    }

    /*
     * changes the name stored in the register given by [oldName] and created a new reference in [regs] to it
     */
    private void changeRegName(String oldName, String newName) {
        Register r = regs.get(oldName);
        r.setName(newName);
        regs.put(newName, r);
    }

    public Register getReturnRegister() {
        return getReg(MIN_RETURN_REG_ID, MAX_RETURN_REG_ID);
    }

    public Register getRegister() {
        return getReg(MIN_GENERAL_REG_ID, MAX_GENERAL_REG_ID);
    }

    /*
     * Gets an available register in the range [from] -> [to] and marks it as in use
     */
    private Register getReg(int from, int to) {
        for(int i = from; i < to; i++) {
            Register r = regs.get(intToName(i));
            if(!inUse.contains(r)) {
                inUse.add(r);
                return r;
            }
        }
        //TODO: No free registers
        return null;
    }

    /*
     * Marks all return registers as not in use
     */
    public void freeReturnRegisters() {
        for(int i = MIN_RETURN_REG_ID; i < MAX_RETURN_REG_ID; i++) {
            free(regs.get(intToName(i)));
        }
    }

    /*
     * Marks a register as not in use
     */
    public void free(Register r) {
        inUse.remove(r);
    }
}
