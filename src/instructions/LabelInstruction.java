package instructions;

public class LabelInstruction extends Instruction {

    private String ident;

    public LabelInstruction(String ident) {
        this.ident = ident;
    }

    @Override
    public String toCode() {
        return ident + ":";
    }
}
