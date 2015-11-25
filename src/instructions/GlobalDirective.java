package instructions;

public class GlobalDirective extends Directive {
    private String name;

    public GlobalDirective(String name) {
        this.name = name;
    }

    @Override
    public String toCode() {
        return ".global " + name;
    }
}
