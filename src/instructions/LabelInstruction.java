package instructions;

public class LabelInstruction extends Instruction {

    protected String ident;

    public LabelInstruction(String ident) {
        this.indentation = 0;
        this.ident = ident;
    }

    @Override
    public String toCode() {
        return ident + ":";
    }
}
