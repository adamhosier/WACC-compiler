package instructions;

public class LtorgDirective extends Directive {
    public LtorgDirective() {
        indentation = 1;
    }

    @Override
    public String toCode() {
        return ".ltorg";
    }
}
