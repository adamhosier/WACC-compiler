package instructions;

public class BranchLinkOverflowInstruction extends Instruction {
    private String overflowFunc;

    public BranchLinkOverflowInstruction(String overflowFunc) {

        this.overflowFunc = overflowFunc;
    }

    @Override
    public String toCode() {
        return "BLVS " + overflowFunc;
    }
}
