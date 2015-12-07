package instructions;

public class BranchLinkEqualInstruction extends Instruction {
    private String funcName;

    public BranchLinkEqualInstruction(String funcName) {
        this.funcName = funcName;
    }

    @Override
    public String toCode() {
        return "BLEQ " + funcName;
    }
}
