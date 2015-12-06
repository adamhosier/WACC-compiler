package instructions;

public class BranchInstruction extends Instruction {

    String label;

    public BranchInstruction(String label) {
        this.label = label;
    }

    @Override
    public String toCode() {
        return "B " + label;
    }
}
