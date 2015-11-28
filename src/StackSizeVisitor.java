import antlr.WaccParser.ArrayLiterContext;
import antlr.WaccParser.StatContext;
import antlr.WaccParser.TypeContext;
import antlr.WaccParser.VarDeclarationContext;
import antlr.WaccParserBaseVisitor;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;

public class StackSizeVisitor extends WaccParserBaseVisitor<Void> {

    private int size;

    // size on stack for each type
    private final int INT_SIZE = 4;
    private final int BOOL_SIZE = 1;
    private final int CHAR_SIZE = 1;
    private final int STRING_SIZE = 4;
    private final int PAIR_SIZE = 4;

    public int getSize(ParseTree tree) {
        visitChildren((RuleNode) tree);
        return size;
    }

    @Override
    public Void visitStat(StatContext ctx) {
        // ignore inner scopes for now
        if (ctx.whileStat() != null || ctx.ifStat() != null
                || ctx.scopeStat() != null) {
            return null;
        }

        // proceed to calculate size if stat is not new scope
        visitChildren(ctx);
        return null;
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
