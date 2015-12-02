package instructions;

public class Comment extends Instruction {
    private String comment;

    public Comment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toCode() {
        return "# " + comment;
    }
}
