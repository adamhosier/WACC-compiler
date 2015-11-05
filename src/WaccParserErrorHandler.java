import org.antlr.v4.runtime.*;

public class WaccParserErrorHandler extends DefaultErrorStrategy {

    static final int ERROR_CODE = 100;

    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        System.exit(ERROR_CODE);
    }

    @Override
    public Token recoverInline(Parser recognizer) {
        System.exit(ERROR_CODE);
        return null;
    }

    @Override
    public void sync(Parser recognizer) { }
}
