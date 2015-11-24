package util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Registers {

    private static final int MAX_REG_ID = 15;
    private static Map<String, Register> regs = new HashMap<>();

    private static List<Register> registersInUse = new LinkedList<>();

    static {
        //general purpose
        for(int i = 0; i <= MAX_REG_ID; i++) {
            String name = "r" + i;
            regs.put(name, new Register(name));
        }
        changeRegName("r13", "sp");
        changeRegName("r14", "lr");
        changeRegName("r15", "pc");
    }

    public static Register r0 = regs.get("r0");
    public static Register r1 = regs.get("r1");
    public static Register r2 = regs.get("r2");
    public static Register r3 = regs.get("r3");
    public static Register r4 = regs.get("r4");
    public static Register r5 = regs.get("r5");
    public static Register r6 = regs.get("r6");
    public static Register r7 = regs.get("r7");
    public static Register r8 = regs.get("r8");
    public static Register r9 = regs.get("r9");
    public static Register r10 = regs.get("r10");
    public static Register r11 = regs.get("r11");
    public static Register r12 = regs.get("r12");
    public static Register r13 = regs.get("sp");
    public static Register r14 = regs.get("lr");
    public static Register r15 = regs.get("pc");
    public static Register sp = regs.get("sp");
    public static Register lr = regs.get("lr");
    public static Register pc = regs.get("pc");


    /*
     * changes the name stored in the register given by [oldName] and created a new reference in [regs] to it
     */
    private static void changeRegName(String oldName, String newName) {
        Register r = regs.get(oldName);
        r.setName(newName);
        regs.put(newName, r);
    }

    public static Register getReturnRegister() {

    }

}
