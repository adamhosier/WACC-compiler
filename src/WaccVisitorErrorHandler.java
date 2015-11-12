import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

public class WaccVisitorErrorHandler {

    static final int ERROR_CODE = 200;
    static final int CHARACTER_MAX_VALUE = 255;
    static final int CHARACTER_MIN_VALUE = 0;
    static final int INTEGER_MAX_VALUE = (int) (Math.pow(2, 31) - 1);
    static final int INTEGER_MIN_VALUE = (int) -Math.pow(2, 31);

    public void typeMismatch(ParseTree ctx, WaccType expected, WaccType actual) {
        String msg = "type mismatch, expected " + expected + " got " + actual;
        throwError(ctx, msg);
    }
    
    public void arrayOutOfBounds(ParseTree ctx, int index) {
        String msg = "array out of bounds at index " + index;
        throwError(ctx, msg);
    }

    public void symbolNotFound(ParseTree ctx, String ident) {
        String msg = "symbol '" + ident + "' not found";
        throwError(ctx, msg);
    }

    public void integerOverflow(ParseTree ctx) {
        overflow(ctx, "Integer", INTEGER_MIN_VALUE, INTEGER_MAX_VALUE);
    }
    
    public void characterOverflow(ParseTree ctx) {
        overflow(ctx, "Character", CHARACTER_MIN_VALUE, CHARACTER_MAX_VALUE);
    }

    public void identNotFound(ParseTree ctx) {
        String ident = ctx.getChild(0).getText();
        String msg = "identifier not found: " + ident;
        throwError(ctx, msg);
    }

    public void invalidOperator(ParseTree ctx) {
        String msg = "invalid binary operator usage";
        throwError(ctx, msg);
    }


    public void invalidNumberOfArgs(ParseTree ctx, String funcIdent) {
        String msg = "invalid number of arguments passed to function \'" + funcIdent + '\'';
        throwError(ctx, msg);
    }

    public void incompatibleArrayElemTypes(ParseTree ctx) {
        String msg = "incompatible types in array literal";
        throwError(ctx, msg);
    }

    public void variableRedeclaration(ParseTree ctx, String text) {
        String msg = "variable '" + text + "' redeclared";
        throwError(ctx, msg);
    }

    private void overflow(ParseTree ctx, String type, int minRange, int maxRange) {
        String msg = type + " overflow, expected " + type + " between " + minRange + " and " + maxRange;
        throwError(ctx, msg);
    }

    private String getLine(ParserRuleContext ctx) {
        return "line " + ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine() + " ";
    }

    private void throwError(ParseTree ctx, String msg) {
        if(ctx instanceof ParserRuleContext) System.err.println(getLine((ParserRuleContext) ctx) + msg);
        else System.err.println(msg);
        System.exit(ERROR_CODE);
    }
}
