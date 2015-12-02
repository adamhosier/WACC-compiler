package instructions;

public class BranchLinkOverflow extends Instruction {
    private String overflowFunc;

    public BranchLinkOverflow(String overflowFunc) {

        this.overflowFunc = overflowFunc;
    }

    @Override
    public String toCode() {
        return "BLVS " + overflowFunc;
    }
}
