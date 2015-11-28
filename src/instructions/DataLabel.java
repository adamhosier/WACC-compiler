package instructions;

public class DataLabel extends Directive {
    @Override
    public String toCode() {
        return ".data\n";
    }
}
