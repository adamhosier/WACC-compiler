package instructions;

public class BranchLinkInstruction extends Instruction {

    String label;

    public BranchLinkInstruction(String label) {
        this.label = label;
    }

    @Override
    public String toCode() {
        return "BL " + label;
    }
}
