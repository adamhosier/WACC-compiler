package util;

import antlr.WaccParser;
import antlr.WaccParser.*;
import antlr.WaccParserBaseVisitor;
import org.antlr.v4.runtime.misc.NotNull;
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
    private final int ARRAY_SIZE = 4;

    public int getSize(ParseTree tree) {
        visitChildren((RuleNode) tree);
        return size;
    }

    private void getSizeOfType(TypeContext type) {
        if (type.baseType() != null) {
            if (type.baseType().INT() != null) {
                size += INT_SIZE;
            }
            if (type.baseType().BOOL() != null) {
                size += BOOL_SIZE;
            }
            if (type.baseType().CHAR() != null) {
                size += CHAR_SIZE;
            }
            if (type.baseType().STRING() != null) {
                size += STRING_SIZE;
            }
        }
        if (type.pairType() != null) {
            size += PAIR_SIZE;
        }
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
    public Void visitVarDeclaration(VarDeclarationContext ctx) {
        getSizeOfType(ctx.type());

        if (ctx.assignRhs().arrayLiter() != null) {
            size += ARRAY_SIZE;
        }

        return null;
    }

    @Override
    public Void visitFunc(@NotNull FuncContext ctx) {
        // skip functions when visiting global scope
        return null;
    }

    @Override
    public Void visitParam(ParamContext ctx) {
        getSizeOfType(ctx.type());
        return null;
    }

    //    @Override
//    public Void visitType(TypeContext ctx) {
//        // varDeclaration
//        if (ctx.getParent() instanceof VarDeclarationContext) {
//            if (ctx.baseType().INT() != null) {
//                size += INT_SIZE;
//            }
//            if (ctx.baseType().BOOL() != null) {
//                size += BOOL_SIZE;
//            }
//            if (ctx.baseType().CHAR() != null) {
//                size += CHAR_SIZE;
//            }
//            if (ctx.baseType().STRING() != null) {
//                size += STRING_SIZE;
//            }
//            if (ctx.pairType() != null) {
//                size += PAIR_SIZE;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public Void visitArrayLiter(ArrayLiterContext ctx) {
//        // do declaration
//        if (ctx.getParent().getParent().getChildCount() == 4) {
//
//        }
//        // do assignment
//        return null;
//    }
}
