import antlr.WaccParserBaseVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import static antlr.WaccParser.*;

public class AntlrVisitor extends WaccParserBaseVisitor<Void>{

    private SymbolTable st;
    private WaccVisitorErrorHandler errorHandler;

    // toggles if debugging output is written to stdout
    public boolean verbose = false;

    public AntlrVisitor() {
        errorHandler = new WaccVisitorErrorHandler();
        st = new SymbolTable();
    }

    /////////// UTILITY METHODS ////////////

    /*
     * Given a context, and list of token or rule identifiers, check if the contexts children matches the format of
     *  the given list.
     */
    private boolean matchGrammar(ParseTree ctx, int[] pattern) {
        if(ctx.getChildCount() != pattern.length) return false;
        for(int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree child = ctx.getChild(i);
            if(pattern[i] == WaccType.ALL.getId()) continue;
            if(!(child instanceof ParserRuleContext && ((ParserRuleContext) child).getRuleIndex() == pattern[i]) &&
               !(child instanceof TerminalNode      && ((ParserRuleContext) ctx).getToken(pattern[i], 0) == child )) {
                return false;
            }
        }
        return true;
    }

    /*
     * Gets the type of any rule or terminal node
     * Performs some type checking on expressions
     */
    private WaccType getType(ParseTree ctx) {
        //outputln(ruleNames[((ParserRuleContext) ctx).getRuleIndex()] + ": " + ctx.getText());
        if(matchGrammar(ctx, new int[]{RULE_baseType})) {
            return new WaccType(((TerminalNode) ctx.getChild(0).getChild(0)).getSymbol().getType());
        }
        if(matchGrammar(ctx, new int[]{RULE_type, OPEN_BRACKETS, CLOSE_BRACKETS})) {
            WaccType t = getType(ctx.getChild(0));
            return t.toArray();
        }
        if(matchGrammar(ctx, new int[]{RULE_expr})) {
           return getType(ctx.getChild(0));
        }
        if(matchGrammar(ctx, new int[]{INT_LIT})) return new WaccType(INT);
        if(matchGrammar(ctx, new int[]{BOOL_LIT})) return new WaccType(BOOL);
        if(matchGrammar(ctx, new int[]{CHAR_LIT})) return new WaccType(CHAR);
        if(matchGrammar(ctx, new int[]{STRING_LIT})) return new WaccType(STRING);
        if(matchGrammar(ctx, new int[]{RULE_pairLiter}) || matchGrammar(ctx, new int[]{PAIR})) {
            return new WaccType(PAIR);
        }
        if(matchGrammar(ctx, new int[]{RULE_ident})) {
            return st.lookupType(ctx.getChild(0));
        }
        if(matchGrammar(ctx, new int[]{RULE_type, RULE_ident})) {
            return getType(ctx.getChild(0));
        }
        if(matchGrammar(ctx, new int[]{RULE_arrayElem})) {
            return st.lookupType(ctx.getChild(0).getChild(0)).getBaseType();
        }
        if(matchGrammar(ctx, new int[]{RULE_unaryOper, RULE_expr})) {
            UnaryOperContext op = (UnaryOperContext) ctx.getChild(0);
            if(matchGrammar(op, new int[]{LEN}) || matchGrammar(op, new int[]{ORD})) return new WaccType(INT);
            if(matchGrammar(op, new int[]{CHR})) return new WaccType(CHAR);
            return getType(ctx.getChild(1));
        }
        if(matchGrammar(ctx, new int[]{RULE_expr, RULE_binaryOper, RULE_expr})) {
            // checks types match and returns type of result
            WaccType t1 = getType(ctx.getChild(0));

            int op = ((TerminalNode) ctx.getChild(1).getChild(0)).getSymbol().getType();
            WaccType top = WaccType.fromBinaryOperator(op);

            WaccType t2 = getType(ctx.getChild(2));

            if(!t1.equals(t2)) return WaccType.INVALID;

            if(top.getId() == INT) {
                return t1.equals(top) ? t1 : WaccType.INVALID;
            } else if(top.getId() == BOOL) {
                if(op == EQ) {
                    return top;
                }
                if(op == OR || op == AND) {
                    return t1.getId() == BOOL ? top : WaccType.INVALID;
                } else {
                    return t1.getId() == INT ? top : WaccType.INVALID;
                }
            } else {
                return WaccType.INVALID;
            }
        }
        if(matchGrammar(ctx, new int[]{OPEN_PARENTHESES, RULE_expr, CLOSE_PARENTHESES})) {
            return getType(ctx.getChild(1));
        }
        if(matchGrammar(ctx, new int[]{RULE_arrayLiter})) {
            ctx = ctx.getChild(0);
            if(ctx.getChildCount() == 2) {
                return WaccType.ALL;
            } else {
                WaccType type = getType(ctx.getChild(1));
                for(int i = 3; i < ctx.getChildCount() - 1; i += 2) {
                    if(!getType(ctx.getChild(i)).equals(type)) {
                        return WaccType.INVALID;
                    }
                }
                return type.toArray();
            }
        }
        if(matchGrammar(ctx, new int[]{RULE_pairType})) {
            return getType(ctx.getChild(0));
        }
        if(matchGrammar(ctx, new int[]{NEW_PAIR, OPEN_PARENTHESES, RULE_expr, COMMA, RULE_expr, CLOSE_PARENTHESES}) ||
                matchGrammar(ctx, new int[]{PAIR, OPEN_PARENTHESES, RULE_pairElemType, COMMA, RULE_pairElemType,
                        CLOSE_PARENTHESES})) {
            WaccType fst = getType(ctx.getChild(2));
            WaccType snd = getType(ctx.getChild(4));
            WaccType pair = new WaccType(fst.getId(), snd.getId());
            pair.setFstArray(fst.isArray());
            pair.setSndArray(snd.isArray());
            return pair;
        }
        if(matchGrammar(ctx, new int[]{RULE_pairElem})) {
            ctx = ctx.getChild(0);
            WaccType t = getType(ctx.getChild(1));
            WaccType tnew;
            if(matchGrammar(ctx, new int[]{FST, WaccType.ALL.getId()})) {
                tnew = new WaccType(t.getFstId());
                tnew.setArray(t.isFstArray());
            } else {
                tnew = new WaccType(t.getSndId());
                tnew.setArray(t.isSndArray());
            }
            return tnew;
        }
        if(matchGrammar(ctx, new int[]{RULE_funcCall})) {
            return st.lookupFunctionType(ctx.getChild(0).getChild(1).getText());
        }
        if(matchGrammar(ctx, new int[]{RULE_stat})) {
            return getType(ctx.getChild(0));
        }
        if(matchGrammar(ctx, new int[]{RETURN, RULE_expr})) {
            return getType(ctx.getChild(1));
        }
        if(matchGrammar(ctx, new int[]{RULE_stat, SEMICOLON, RULE_stat})) {
            return getType(ctx.getChild(2));
        }
        return WaccType.INVALID;
    }

    /*
     * Converts generic object into a WaccType if possible
     */
    private WaccType objToType(Object o) {
        if(o instanceof ParseTree) return getType((ParseTree) o);
        if(o instanceof Integer) return new WaccType((Integer) o);
        if(o instanceof WaccType) return (WaccType) o;
        return WaccType.INVALID;
    }

    /*
     * Checks that two types are equal
     * Parameters can be ParseTrees, Integers or WaccTypes
     */
    private boolean typesMatch(Object lhs, Object rhs) {
        WaccType typel = objToType(lhs);
        WaccType typer = objToType(rhs);;
        return typel.isValid() && typer.isValid() && (typel.isAll() || typer.isAll() ||  typel.equals(typer))   ;
    }

    /////////// VISITOR METHODS ////////////

    public Void visitProg(ProgContext ctx) {
        System.out.println("====");
        outputln("Visited main program entry");

        // visit function definitions
        for(int i = 1; i < ctx.getChildCount() - 3; i++) {
            FuncContext func = (FuncContext) ctx.getChild(i);
            String ident = func.ident().getText();
            outputln("Declaring func " + ident);
            st.addFunction(ident, getType(func.type()));
            if(func.paramList() != null) visit(func.paramList());
        }

        visitChildren(ctx);
        System.out.println("====");
        return null;
    }

    public Void visitFunc(FuncContext ctx) {
        String ident = ctx.ident().getText();
        WaccType expReturn = st.lookupFunctionType(ident);

        outputln("Visited function " + ident);

        visit(ctx.type());

        st.newScope();

        st.enterFunction(ident);

        visit(ctx.stat());

        WaccType accReturn = getType(ctx.stat());

        if(!typesMatch(expReturn, accReturn)) {
            errorHandler.typeMismatch(ctx, expReturn, accReturn);
        }

        st.endScope();

        return null;
    }

    public Void visitParamList(ParamListContext ctx) {
        FuncContext f = (FuncContext) ctx.getParent();
        for(int i = 0; i < ctx.getChildCount(); i += 2) {
            ParamContext param = (ParamContext) ctx.getChild(i);
            String paramName = param.ident().getText();
            WaccType paramType = getType(param);
            st.addParamToFunction(f.ident().getText(), paramName, paramType);
        }
        return null;
    }

    public Void visitStat(StatContext ctx) {
        if(matchGrammar(ctx, new int[]{SKIP}))
            return null;
        if(matchGrammar(ctx, new int[]{RULE_type, RULE_ident, EQUALS, RULE_assignRhs}))
            visitStatDeclaration(ctx);
        if(matchGrammar(ctx, new int[]{RULE_assignLhs, EQUALS, RULE_assignRhs}))
            visitStatAssignment(ctx);
        if(matchGrammar(ctx, new int[]{READ, RULE_assignLhs}))
            visitStatRead(ctx);
        if(matchGrammar(ctx, new int[]{FREE, RULE_expr}))
            visitStatFree(ctx);
        if(matchGrammar(ctx, new int[]{RETURN, RULE_expr}))
            visitStatReturn(ctx);
        if(matchGrammar(ctx, new int[]{EXIT, RULE_expr}))
            visitStatExit(ctx);
        if(matchGrammar(ctx, new int[]{PRINT, RULE_expr})
                || matchGrammar(ctx, new int[]{PRINTLN, RULE_expr}))
            visitStatPrint(ctx);
        if(matchGrammar(ctx, new int[]{IF, RULE_expr, THEN, RULE_stat, ELSE, RULE_stat, FI}))
            visitStatIf(ctx);
        if(matchGrammar(ctx, new int[]{WHILE, RULE_expr, DO, RULE_stat, DONE}))
            visitStatWhile(ctx);
        if(matchGrammar(ctx, new int[]{BEGIN, RULE_stat, END}))
            visitStatNewScope(ctx);
        if(matchGrammar(ctx, new int[]{RULE_stat, SEMICOLON, RULE_stat}))
            visitStatComposition(ctx);
        return null;
    }

    private void visitStatDeclaration(StatContext ctx) {
        outputln("Visited declaration");
        outputln("Declaring var " + ctx.ident().getText());

        WaccType exp = getType(ctx.type());
        outputln("  Expected type: " + exp);

        WaccType acc = getType(ctx.assignRhs());
        outputln("  Actual type: " + acc);

        if(!typesMatch(acc, exp)) {
            errorHandler.typeMismatch(ctx, exp, acc);
        }

        boolean success = st.addVariable(ctx.ident().getText(), acc);
        if(!success) {
            errorHandler.variableRedeclaration(ctx, ctx.ident().getText());
        }

        // visit assignRhs
        visit(ctx.getChild(3));
    }

    private void visitStatAssignment(StatContext ctx) {
        outputln("Visited assigment");

        AssignLhsContext lhs = ctx.assignLhs();

        String ident = null;
        WaccType acc = getType(ctx.assignRhs());

        if(matchGrammar(lhs, new int[]{RULE_ident})) {
            ident = lhs.ident().getText();

            if(!st.isDeclared(ident)) {
                errorHandler.symbolNotFound(ctx, ident);
            }

            WaccType exp = st.lookupType(ident);
            if(!typesMatch(exp, ctx.assignRhs())) {
                errorHandler.typeMismatch(ctx, exp, acc);
            }
        } else if(matchGrammar(lhs, new int[]{RULE_arrayElem})) {
            ident = lhs.arrayElem().ident().getText();

            if(!st.isDeclared(ident)) {
                errorHandler.symbolNotFound(ctx, ident);
            }

            WaccType exp = st.lookupType(ident);
            if(!typesMatch(exp.getBaseType(), ctx.assignRhs())) {
                errorHandler.typeMismatch(ctx, exp.getBaseType(), acc);
            }
        } else if(matchGrammar(lhs, new int[]{RULE_pairElem})) {
            ident = lhs.pairElem().expr().ident().getText();
        }

        outputln("  Assigning to " + ident);
    }

    private void visitStatRead(StatContext ctx) {
        outputln("Visited read");

        String ident = ctx.assignLhs().getChild(0).getText();

        if(!st.isDeclared(ident)) {
            errorHandler.symbolNotFound(ctx, ident);
        }

        visit(ctx.getChild(0));
    }

    private void visitStatFree(StatContext ctx) {
        outputln("Visited free");
        ExprContext ctxExpr = (ExprContext) ctx.getChild(1);
        WaccType exprType = getType(ctxExpr);
        if (!typesMatch(new WaccType(RULE_pairType), exprType)
                || !typesMatch(new WaccType(RULE_arrayElem), exprType)) {
            errorHandler.freeTypeMismatch(ctxExpr, exprType);
        }
        visit(ctx.getChild(1));
    }

    private void visitStatReturn(StatContext ctx) {
        outputln("Visited return");
        visit(ctx.getChild(1));
    }

    private void visitStatExit(StatContext ctx) {
        outputln("Visited exit");
        ExprContext ctxExpr = (ExprContext) ctx.getChild(1);
        WaccType exprType = getType(ctxExpr);
        if (!typesMatch(INT, exprType)) {
            errorHandler.typeMismatch(ctxExpr, new WaccType(INT), exprType);
        }
        visit(ctxExpr);
    }

    private void visitStatPrint(StatContext ctx) {
        outputln("Visited print");
        visit(ctx.getChild(1));
    }

    private void visitStatIf(StatContext ctx) {
        outputln("Visited if");
    }

    private void visitStatWhile(StatContext ctx) {
        outputln("Visited while");
    }

    private void visitStatNewScope(StatContext ctx) {
        outputln("Scope started (BEGIN)");
        st.newScope();
        visitChildren(ctx);
        outputln("Scope ended (END)");
        st.endScope();
    }

    private void visitStatComposition(StatContext ctx) {
        visit(ctx.getChild(0));
        visit(ctx.getChild(2));
    }

    public Void visitIdent(IdentContext ctx) {
        outputln("Visited ident");
        if (!st.isDeclared(ctx.getText())) {
            errorHandler.identNotFound(ctx);
        }
        return null;
    }


    ////////// Visit assignLhs //////////

    public Void visitAssignLhs(AssignLhsContext ctx) {
        if(matchGrammar(ctx, new int[]{RULE_ident}))
            visit(ctx.getChild(0));
        if(matchGrammar(ctx, new int[]{RULE_arrayElem}))
            visit(ctx.getChild(0));
        if(matchGrammar(ctx, new int[]{RULE_pairElem}))
            visit(ctx.getChild(0));
        return null;
    }

    ////////// Visit assignRhs //////////

    public Void visitAssignRhs(AssignRhsContext ctx) {
        outputln("Visited assignRhs");
        if(matchGrammar(ctx, new int[]{RULE_expr}))
            ctx.getChild(0).accept(this);
        if(matchGrammar(ctx, new int[]{RULE_arrayLiter}))
            ctx.getChild(0).accept(this);
        if(matchGrammar(ctx, new int[]{NEW_PAIR, OPEN_PARENTHESES, RULE_expr,
                COMMA, RULE_expr, CLOSE_PARENTHESES})) {
            ctx.getChild(2).accept(this);
            ctx.getChild(4).accept(this);
        }
        if(matchGrammar(ctx, new int[]{RULE_pairElem}))
            visit(ctx.getChild(0));
        if(matchGrammar(ctx, new int[]{RULE_funcCall}))
            visit(ctx.funcCall());
        return null;
    }

    public Void visitFuncCall(FuncCallContext ctx) {
        visit(ctx.ident());
        // check that an argList exists
        if (ctx.getChildCount() > 4) {
            visitArgList((ArgListContext) ctx.getChild(3), ctx.ident().getText()); // visit arg list
        }
        return null;
    }

    private void visitArgList(ArgListContext ctx, String ident) {
        int size = st.getNumParams(ident);
        // check that number of args passed is correct
        if (ctx.getChildCount() != size) {
            errorHandler.invalidNumberOfArgs(ctx, ident);
        }
        // check each param matches type
        WaccType argType;
        WaccType paramType;
        for (int i = 0; i < ctx.getChildCount(); i += 2) {
            visit(ctx.getChild(i));
            argType = getType(ctx.getChild(i));
            paramType = st.getFunctionParamType(ident, i / 2);
            if (!typesMatch(argType, paramType)) {
                errorHandler.typeMismatch(ctx.getChild(i), paramType, argType);
            }
        }
    }

    ////////// Visit Pair Elem //////////

    public Void visitPairElem(PairElemContext ctx) {
        if(matchGrammar(ctx, new int[]{FST, RULE_expr})
                || matchGrammar(ctx, new int[]{SND, RULE_expr}))
            visit(ctx.getChild(1));
        return null;
    }

    ////////// Visit pair type //////////

    public Void visitPairType(PairTypeContext ctx) {

        return null;
    }

    ////////// Visit pair type //////////

    public Void visitPairElemType(PairElemTypeContext ctx) {

        return null;
    }

    ////////// Visit array elem //////////

    public Void visitArrayElem(ArrayElemContext ctx) {
        outputln("Visited array elem");
        IdentContext ctxi = (IdentContext) ctx.getChild(0);
        // Check identifier exists
        visit(ctxi);

        int[] arrayLengths = st.getArrayLength(ctxi.getText());

        // Check that each index expression is integer
        for (int i = 2; i < ctx.getChildCount() - 1; i += 3) {
            ParseTree ctxExpr = ctx.getChild(i);
            if (!typesMatch(INT, ctxExpr)) {
                errorHandler.typeMismatch(ctxExpr, new WaccType(INT), getType(ctxExpr));
            };
        }
        return null;
    }

    ////////// Visit array liter //////////

    public Void visitArrayLiter(ArrayLiterContext ctx) {
        outputln("Visited array literal");
        int childCount = ctx.getChildCount();
        if (childCount > 2) {
            WaccType type = getType(ctx.getChild(1));
            for (int i = 3; i < childCount - 1; i += 2) {
                ParseTree ctxExpr = ctx.getChild(i);
                WaccType nextType = getType(ctxExpr);
                if (!typesMatch(type, nextType)) {
                    errorHandler.incompatibleArrayElemTypes(ctxExpr);
                }
            }
        }
        return null;
    }

    ////////// Visit Expression //////////

    public Void visitExpr(ExprContext ctx) {
        if(matchGrammar(ctx, new int[]{INT_LIT}))
            visitExprIntLiter(ctx);
        if(matchGrammar(ctx, new int[]{BOOL_LIT}))
            visitExprBoolLiter(ctx);
        if(matchGrammar(ctx, new int[]{CHAR_LIT}))
            visitExprCharLiter(ctx);
        if(matchGrammar(ctx, new int[]{STRING_LIT}))
            visitExprStringLiter(ctx);
        if(matchGrammar(ctx, new int[]{RULE_pairLiter}))
            visitExprPairLiter(ctx);
        if(matchGrammar(ctx, new int[]{RULE_ident}) || matchGrammar(ctx, new int[]{RULE_arrayElem}))
            visit(ctx.getChild(0));
        if(matchGrammar(ctx, new int[]{RULE_unaryOper, RULE_expr}))
            visitExprUnaryOper(ctx);
        if(matchGrammar(ctx, new int[]{RULE_expr, RULE_binaryOper, RULE_expr}))
            visitExprBinaryOper(ctx);
        if(matchGrammar(ctx, new int[]{OPEN_PARENTHESES, RULE_expr, CLOSE_PARENTHESES}))
            visitExprParentheses(ctx);
        return null;
    }

    private void visitExprIntLiter(ExprContext ctx) {
        outputln("Visited int literal");
        int i;
        try {
            i = Integer.parseInt(ctx.getText());
        } catch(NumberFormatException e) {
            errorHandler.integerOverflow(ctx);
            return;
        }

        if (i < WaccVisitorErrorHandler.INTEGER_MIN_VALUE || i > WaccVisitorErrorHandler.INTEGER_MAX_VALUE) {
            errorHandler.integerOverflow(ctx);
        }
    }

    private void visitExprBoolLiter(ExprContext ctx) { outputln("Visited bool literal"); }

    private void visitExprCharLiter(ExprContext ctx) {
        outputln("Visited char literal");
        char c = ctx.getText().charAt(0);
        if (c < 0 || c > 255) {
            errorHandler.characterOverflow(ctx);
        }
    }

    private void visitExprStringLiter(ExprContext ctx) { outputln("Visited string literal"); }

    private void visitExprPairLiter(ExprContext ctx) { outputln("Visited pair literal"); }

//    private void visitExprIdent(ParserRuleContext ctx) {
//        outputln("Visited ident");
//        WaccType ident = st.lookupType(ctx.getChild(0).getText());
//        if (ident == null) {
//            errorHandler.identNotFound((ParserRuleContext) ctx.getChild(0));
//        }
//    }

//    private void visitExprArrayElem(ParserRuleContext ctx) {
//        outputln("Visited array elem");
//        ParserRuleContext ctxi = (ParserRuleContext) ctx.getChild(0);
//        // Check identifier exists
//        visitExprIdent(ctxi);
//
//        int arrayLength = st.getArrayLength(ctxi.getText());
//        int index = Integer.parseInt(ctx.getChild(2).getText());
//        // ONLY CHECKS IF LEADING DIMENSION IN BOUNDS
//        if (index >= arrayLength || index < 0) {
//            errorHandler.arrayOutOfBounds((ParserRuleContext) ctx.getChild(2), index);
//        }
//
//        // Check that each index expression is integer
//        for (int i = 2; i < ctx.getChildCount() - 1; i += 3) {
//            ParserRuleContext ctxExpr = (ParserRuleContext) ctx.getChild(i);
//            if (!typesMatch(INT_LIT, ctxExpr)) {
//                errorHandler.typeMismatch(ctxExpr, new WaccType(INT_LIT), getType(ctxExpr));
//            }
//        }
//    }

    private void visitExprUnaryOper(ExprContext ctx) {
        outputln("Visited unary operation");
        ParserRuleContext ctxOp= (ParserRuleContext) ctx.getChild(0);
        ParserRuleContext ctxExpr = (ParserRuleContext) ctx.getChild(1);
        if (typesMatch(NOT, ctxOp)) {
            evalArgType(BOOL_LIT, ctxExpr);
        }
        if (typesMatch(MINUS, ctxOp) || typesMatch(CHR, ctxOp)) {
            evalArgType(INT_LIT, ctxExpr);
        }
        if (typesMatch(LEN, ctxOp))
            evalArgType(IDENT, ctxExpr);
        if (typesMatch(ORD, ctxOp)) {
            evalArgType(CHAR_LIT, ctxExpr);
        }
        visit(ctxExpr);
    }

    private void visitExprBinaryOper(ExprContext ctx) {
        outputln("Visited binary operation");
        WaccType ctxOp = getType(ctx.getChild(1));
        ParseTree ctxExpr1 = ctx.getChild(0);
        ParseTree ctxExpr2 = ctx.getChild(2);

        if (typesMatch(MULT, ctxOp) || typesMatch(DIV, ctxOp) || typesMatch(MOD, ctxOp) || typesMatch(PLUS, ctxOp)
                || typesMatch(MINUS, ctxOp) || typesMatch(GREATER_THAN, ctxOp) || typesMatch(GREATER_THAN_EQ, ctxOp)
                || typesMatch(LESS_THAN, ctxOp)|| typesMatch(LESS_THAN_EQ, ctxOp)) {
            evalArgType(INT, ctxExpr1);
            evalArgType(INT, ctxExpr2);
        }
        if (typesMatch(AND, ctxOp) || typesMatch(OR, ctxOp)) {
            evalArgType(BOOL, ctxExpr1);
            evalArgType(BOOL, ctxExpr2);
        }

        visit(ctx.getChild(0));
        visit(ctx.getChild(2));
    }

    private void evalArgType(int expectedTypeToken, ParseTree ctxExpr) {
        if (!typesMatch(expectedTypeToken, ctxExpr)) {
            errorHandler.typeMismatch(ctxExpr, new WaccType(expectedTypeToken),
                                        getType(ctxExpr));
        }
    }

    private void visitExprParentheses(ExprContext ctx) {
        outputln("Visited expr parentheses");
        visit(ctx.getChild(1));
    }

    ////////// OUTPUT FUNCTIONS ////////////

    public void output(String s) {
        if(verbose) System.out.print(s);
    }

    public void outputln(String s) {
        if(verbose) System.out.println(s);
    }
}