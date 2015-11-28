import antlr.WaccParser.ArrayLiterContext;
import antlr.WaccParser.TypeContext;
import antlr.WaccParser.VarDeclarationContext;
import antlr.WaccParserBaseVisitor;

public class StackSizeVisitor extends WaccParserBaseVisitor<Void> {

    private int size;

    // size on stack for each type
    private final int INT_SIZE = 4;
    private final int BOOL_SIZE = 1;
    private final int CHAR_SIZE = 1;
    private final int STRING_SIZE = 4;
    private final int PAIR_SIZE = 4;

    public int getSize() {
        return size;
    }

    @Override
    public Void visitType(TypeContext ctx) {
        // varDeclaration
        if (ctx.getParent() instanceof VarDeclarationContext) {
            if (ctx.baseType().INT() != null) {
                size += INT_SIZE;
            }
            if (ctx.baseType().BOOL() != null) {
                size += BOOL_SIZE;
            }
            if (ctx.baseType().CHAR() != null) {
                size += CHAR_SIZE;
            }
            if (ctx.baseType().STRING() != null) {
                size += STRING_SIZE;
            }
            if (ctx.pairType() != null) {
                size += PAIR_SIZE;
            }
        }
        return null;
    }

    @Override
    public Void visitArrayLiter(ArrayLiterContext ctx) {
        // do declaration
        // do assignment
        return null;
    }
}
