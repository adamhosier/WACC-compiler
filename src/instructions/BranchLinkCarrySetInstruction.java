package instructions;

public class BranchLinkCarrySetInstruction extends Instruction {
    private String funcName;

    public BranchLinkCarrySetInstruction(String funcName) {
        this.funcName = funcName;
    }

    @Override
    public String toCode() {
        return "BLCS " + funcName;
    }
}
