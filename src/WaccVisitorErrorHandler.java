import org.antlr.v4.runtime.ParserRuleContext;

public class WaccVisitorErrorHandler {

    static final int ERROR_CODE = 200;
    static final int CHARACTER_MAX_VALUE = 255;
    static final int CHARACTER_MIN_VALUE = 0;
    static final int INTEGER_MAX_VALUE = Integer.MAX_VALUE;
    static final int INTEGER_MIN_VALUE = Integer.MIN_VALUE;

    public void typeMismatch(ParserRuleContext ctx, String expected, String actual) {
        String msg = "type mismatch, expected " + expected + " got " + actual;
        throwError(ctx, msg);
    }
    
    public void arrayOutOfBounds(ParserRuleContext ctx, int index) {
        String msg = "array out of bounds at index " + index;
        throwError(ctx, msg);
    }
    
    public void arithmetic(ParserRuleContext ctx) {
        String msg = "arithmetic error, tried to divide by zero ";
        throwError(ctx, msg);
    }
    
    public void nullPointer(ParserRuleContext ctx, String variableName, String variableType) {
        String msg = "null pointer no assignment for the variable " 
                      + variableName + " with type " + variableType;
        throwError(ctx, msg);
    }
    
    public void integerOverflow(ParserRuleContext ctx) {
        overflow(ctx, "Integer", INTEGER_MIN_VALUE, INTEGER_MAX_VALUE);
    }
    
    public void characterOverflow(ParserRuleContext ctx) {
        overflow(ctx, "Character", CHARACTER_MIN_VALUE, CHARACTER_MAX_VALUE);
    }
    
    private void overflow(ParserRuleContext ctx, String type, int minRange, int maxRange) {
        String msg = type + " overflow, expected " + type + " between " + minRange + " and " + maxRange;
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
