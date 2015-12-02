package instructions;

public class BranchLinkNotEqual extends Instruction {
    private String branchFunc;

    public BranchLinkNotEqual(String branchFunc) {

        this.branchFunc = branchFunc;
    }

    @Override
    public String toCode() {
        return "BLNE " + branchFunc;
    }
}
