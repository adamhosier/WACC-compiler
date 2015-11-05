import org.antlr.v4.runtime.ParserRuleContext;

public class WaccVisitorErrorHandler {

    static final int ERROR_CODE = 200;

    public void typeMismatch(ParserRuleContext ctx, String expected, String actual) {
        String msg = "type mismatch expected " + expected + " got " + actual;
        throwError(ctx, msg);
    }

    private String getLine(ParserRuleContext ctx) {
        return "line " + ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine() + " ";
    }

    private void throwError(ParserRuleContext ctx, String msg) {
        System.err.println(getLine(ctx) + msg);
        System.exit(ERROR_CODE);
    }
}
