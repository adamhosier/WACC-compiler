package instructions;

public class BranchLinkNotEqualInstruction extends Instruction {
    private String branchFunc;

    public BranchLinkNotEqualInstruction(String branchFunc) {

        this.branchFunc = branchFunc;
    }

    @Override
    public String toCode() {
        return "BLNE " + branchFunc;
    }
}
