// Generated from ./WaccParser.g4 by ANTLR 4.4
package antlr;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link WaccParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface WaccParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link WaccParser#readStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadStat(@NotNull WaccParser.ReadStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#argList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgList(@NotNull WaccParser.ArgListContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#arrayLiter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayLiter(@NotNull WaccParser.ArrayLiterContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#arrayElem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayElem(@NotNull WaccParser.ArrayElemContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#assignRhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignRhs(@NotNull WaccParser.AssignRhsContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#printStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStat(@NotNull WaccParser.PrintStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#assignLhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignLhs(@NotNull WaccParser.AssignLhsContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#unaryOper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOper(@NotNull WaccParser.UnaryOperContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#ident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdent(@NotNull WaccParser.IdentContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#printlnStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintlnStat(@NotNull WaccParser.PrintlnStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(@NotNull WaccParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#escapedChar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEscapedChar(@NotNull WaccParser.EscapedCharContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#incrementStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncrementStat(@NotNull WaccParser.IncrementStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#baseType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseType(@NotNull WaccParser.BaseTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#character}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharacter(@NotNull WaccParser.CharacterContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#pairLiter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairLiter(@NotNull WaccParser.PairLiterContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(@NotNull WaccParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#returnStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStat(@NotNull WaccParser.ReturnStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(@NotNull WaccParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#varAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarAssignment(@NotNull WaccParser.VarAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#pairElem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairElem(@NotNull WaccParser.PairElemContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#whileStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStat(@NotNull WaccParser.WhileStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#ifStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStat(@NotNull WaccParser.IfStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#intSign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntSign(@NotNull WaccParser.IntSignContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat(@NotNull WaccParser.StatContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#newPair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewPair(@NotNull WaccParser.NewPairContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#exitStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExitStat(@NotNull WaccParser.ExitStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(@NotNull WaccParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#varDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclaration(@NotNull WaccParser.VarDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#freeStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFreeStat(@NotNull WaccParser.FreeStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#forStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStat(@NotNull WaccParser.ForStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#pairType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairType(@NotNull WaccParser.PairTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#pairElemType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairElemType(@NotNull WaccParser.PairElemTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#scopeStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScopeStat(@NotNull WaccParser.ScopeStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc(@NotNull WaccParser.FuncContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#boolBinaryOper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolBinaryOper(@NotNull WaccParser.BoolBinaryOperContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(@NotNull WaccParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#comment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComment(@NotNull WaccParser.CommentContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#funcCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCall(@NotNull WaccParser.FuncCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link WaccParser#otherBinaryOper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOtherBinaryOper(@NotNull WaccParser.OtherBinaryOperContext ctx);
}