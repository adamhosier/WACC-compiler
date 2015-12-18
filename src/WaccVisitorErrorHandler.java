import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import util.WaccType;

public class WaccVisitorErrorHandler {

    static final int ERROR_CODE_SEMANTIC = 200;
    static final int ERROR_CODE_SYNTAX = 100;
    static final int CHARACTER_MAX_VALUE = 255;
    static final int CHARACTER_MIN_VALUE = 0;
    static final int INTEGER_MAX_VALUE = (int) (Math.pow(2, 31) - 1);
    static final int INTEGER_MIN_VALUE = (int) -Math.pow(2, 31);

    public void typeMismatch(ParseTree ctx, WaccType expected, WaccType actual) {
        String msg = "type mismatch, expected " + expected + " got " + actual;
        throwError(ctx, msg, ERROR_CODE_SEMANTIC);
    }
    
    public void typeMismatch(ParseTree ctx, WaccType expected1, 
                             WaccType expected2, WaccType actual) {
        String msg = "type mismatch, expected " + expected1 + " or " 
                      + expected2 + " got " + actual;
        throwError(ctx, msg, ERROR_CODE_SEMANTIC);
    }

    public void freeTypeMismatch(ParseTree ctx, WaccType actual) {
        String msg = "incompatible target for free statement";
        throwError(ctx, msg, ERROR_CODE_SEMANTIC);
    }

    public void symbolNotFound(ParseTree ctx, String ident) {
        String msg = "symbol '" + ident + "' not found";
        throwError(ctx, msg, ERROR_CODE_SEMANTIC);
    }

    public void integerOverflow(ParseTree ctx) {
        overflow(ctx, "Integer", INTEGER_MIN_VALUE, INTEGER_MAX_VALUE);
    }
    
    public void characterOverflow(ParseTree ctx) {
        overflow(ctx, "Character", CHARACTER_MIN_VALUE, CHARACTER_MAX_VALUE);
    }

    public void invalidOperator(ParseTree ctx, String op) {
        String msg = "invalid binary operator usage (" + op + ")";
        throwError(ctx, msg, ERROR_CODE_SEMANTIC);
    }

    public void invalidNumberOfArgs(ParseTree ctx, String funcIdent) {
        String msg = "invalid number of arguments passed to function \'" + funcIdent + '\'';
        throwError(ctx, msg, ERROR_CODE_SEMANTIC);
    }

    public void incompatibleArrayElemTypes(ParseTree ctx) {
        String msg = "incompatible types in array literal";
        throwError(ctx, msg, ERROR_CODE_SEMANTIC);
    }

    public void variableRedeclaration(ParseTree ctx, String ident) {
        String msg = "variable '" + ident + "' redeclared";
        throwError(ctx, msg, ERROR_CODE_SEMANTIC);
    }

    public void functionRedeclaration(ParseTree ctx, String ident) {
        String msg = "function '" + ident + "' redeclared";
        throwError(ctx, msg, ERROR_CODE_SEMANTIC);
    }

    public void assignmentToFunction(ParseTree ctx, String ident) {
        String msg = "assignment to function '" + ident + "'";
        throwError(ctx, msg, ERROR_CODE_SEMANTIC);
    }

    public void unprintableType(ParseTree ctx, String text) {
        String msg = "'" + text + "' is not a printable expression";
        throwError(ctx, msg, ERROR_CODE_SEMANTIC);
    }

    public void unreachableCode(ParseTree ctx) {
        String msg = "unreachable code detected";
        throwError(ctx, msg, ERROR_CODE_SEMANTIC);
    }

    public void missingReturnStatement(ParseTree ctx, String funcName) {
        String msg = "function '" + funcName + "' missing return statement";
        throwError(ctx, msg, ERROR_CODE_SYNTAX);
    }

    private void overflow(ParseTree ctx, String type, int minRange, int maxRange) {
        String msg = type + " overflow, expected " + type + " between " + minRange + " and " + maxRange;
        throwError(ctx, msg, ERROR_CODE_SYNTAX);
    }

    public void nonValidStatement(ParseTree ctx) {
        String msg = "for loop initialiser is not a valid statement";
        throwError(ctx, msg, ERROR_CODE_SYNTAX);
    }

    private String getLine(ParseTree ctx) {
        int line = 0;
        int pos = 0;
        if(ctx instanceof ParserRuleContext) {
            ParserRuleContext ctxp = (ParserRuleContext) ctx;
            line = ctxp.getStart().getLine();
            pos = ctxp.getStart().getCharPositionInLine();
        } else if(ctx instanceof TerminalNode) {
            TerminalNode ctxt = (TerminalNode) ctx;
            line = ctxt.getSymbol().getLine();
            pos = ctxt.getSymbol().getLine();
        }
        return "line " + line + ":" + pos + " ";
    }

    private void throwError(ParseTree ctx, String msg, int code) {
        System.err.println(getLine(ctx) + msg);
        System.exit(code);
    }
}
