package instructions;

public class BranchLinkLessThanInstruction extends Instruction {
    private String funcName;

    public BranchLinkLessThanInstruction(String funcName) {
        this.funcName = funcName;
    }

    @Override
    public String toCode() {
        return "BLLT " + funcName;
    }
}
