package instructions;

public class BranchEqualInstruction extends Instruction {

    String label;

    public BranchEqualInstruction(String label) {
        this.label = label;
    }

    @Override
    public String toCode() {
        return "BEQ " + label;
    }
}
