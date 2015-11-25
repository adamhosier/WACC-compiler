package instructions;

public abstract class Instruction {

    public int indentation;

    public Instruction() {
        indentation = 1;
    }

    public String toCode() {
        return this.getClass().getSimpleName() + ".toCode() not implemented";
    }
}
