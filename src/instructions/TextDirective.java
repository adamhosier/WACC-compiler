package instructions;

public class TextDirective extends Directive {
    @Override
    public String toCode() {
        return ".text\n";
    }
}
