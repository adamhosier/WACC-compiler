package instructions;

public abstract class Instruction {

    public String toCode() {
        return this.getClass().getSimpleName() + ".toCode() not implemented";
    }
}
