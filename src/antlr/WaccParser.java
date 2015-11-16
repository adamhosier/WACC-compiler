// Generated from ./WaccParser.g4 by ANTLR 4.4
package antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class WaccParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		COMMENT=1, WS=2, BEGIN=3, END=4, IS=5, SKIP=6, READ=7, FREE=8, RETURN=9, 
		EXIT=10, PRINT=11, PRINTLN=12, IF=13, THEN=14, ELSE=15, FI=16, WHILE=17, 
		DO=18, DONE=19, NEW_PAIR=20, CALL=21, FST=22, SND=23, INT=24, BOOL=25, 
		CHAR=26, STRING=27, PAIR=28, BOOL_LIT=29, TRUE=30, FALSE=31, NOT=32, LEN=33, 
		ORD=34, CHR=35, MULT=36, DIV=37, MOD=38, PLUS=39, MINUS=40, EQUALS=41, 
		GREATER_THAN=42, GREATER_THAN_EQ=43, LESS_THAN=44, LESS_THAN_EQ=45, EQ=46, 
		NOT_EQ=47, AND=48, OR=49, OPEN_PARENTHESES=50, CLOSE_PARENTHESES=51, OPEN_BRACKETS=52, 
		CLOSE_BRACKETS=53, COMMA=54, SEMICOLON=55, NULL=56, IDENT=57, INT_LIT=58, 
		DIGIT_LIT=59, CHARACTER_LIT=60, ESCAPED_CHAR_LIT=61, CHAR_LIT=62, STRING_LIT=63;
	public static final String[] tokenNames = {
		"<INVALID>", "COMMENT", "WS", "'begin'", "'end'", "'is'", "'skip'", "'read'", 
		"'free'", "'return'", "'exit'", "'print'", "'println'", "'if'", "'then'", 
		"'else'", "'fi'", "'while'", "'do'", "'done'", "'newpair'", "'call'", 
		"'fst'", "'snd'", "'int'", "'bool'", "'char'", "'string'", "'pair'", "BOOL_LIT", 
		"'true'", "'false'", "'!'", "'len'", "'ord'", "'chr'", "'*'", "'/'", "'%'", 
		"'+'", "'-'", "'='", "'>'", "'>='", "'<'", "'<='", "'=='", "'!='", "'&&'", 
		"'||'", "'('", "')'", "'['", "']'", "','", "';'", "'null'", "IDENT", "INT_LIT", 
		"DIGIT_LIT", "CHARACTER_LIT", "ESCAPED_CHAR_LIT", "CHAR_LIT", "STRING_LIT"
	};
	public static final int
		RULE_prog = 0, RULE_func = 1, RULE_paramList = 2, RULE_param = 3, RULE_stat = 4, 
		RULE_varDeclaration = 5, RULE_varAssignment = 6, RULE_readStat = 7, RULE_freeStat = 8, 
		RULE_returnStat = 9, RULE_exitStat = 10, RULE_printStat = 11, RULE_printlnStat = 12, 
		RULE_ifStat = 13, RULE_whileStat = 14, RULE_scopeStat = 15, RULE_assignLhs = 16, 
		RULE_assignRhs = 17, RULE_newPair = 18, RULE_funcCall = 19, RULE_argList = 20, 
		RULE_pairElem = 21, RULE_type = 22, RULE_baseType = 23, RULE_pairType = 24, 
		RULE_pairElemType = 25, RULE_expr = 26, RULE_unaryOper = 27, RULE_boolBinaryOper = 28, 
		RULE_otherBinaryOper = 29, RULE_ident = 30, RULE_arrayElem = 31, RULE_intSign = 32, 
		RULE_character = 33, RULE_escapedChar = 34, RULE_arrayLiter = 35, RULE_pairLiter = 36, 
		RULE_comment = 37;
	public static final String[] ruleNames = {
		"prog", "func", "paramList", "param", "stat", "varDeclaration", "varAssignment", 
		"readStat", "freeStat", "returnStat", "exitStat", "printStat", "printlnStat", 
		"ifStat", "whileStat", "scopeStat", "assignLhs", "assignRhs", "newPair", 
		"funcCall", "argList", "pairElem", "type", "baseType", "pairType", "pairElemType", 
		"expr", "unaryOper", "boolBinaryOper", "otherBinaryOper", "ident", "arrayElem", 
		"intSign", "character", "escapedChar", "arrayLiter", "pairLiter", "comment"
	};

	@Override
	public String getGrammarFileName() { return "WaccParser.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public WaccParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(WaccParser.EOF, 0); }
		public List<FuncContext> func() {
			return getRuleContexts(FuncContext.class);
		}
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public FuncContext func(int i) {
			return getRuleContext(FuncContext.class,i);
		}
		public TerminalNode BEGIN() { return getToken(WaccParser.BEGIN, 0); }
		public TerminalNode END() { return getToken(WaccParser.END, 0); }
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(76); match(BEGIN);
			setState(80);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(77); func();
					}
					} 
				}
				setState(82);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			setState(83); stat(0);
			setState(84); match(END);
			setState(85); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncContext extends ParserRuleContext {
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
		public ParamListContext paramList() {
			return getRuleContext(ParamListContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public TerminalNode IS() { return getToken(WaccParser.IS, 0); }
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TerminalNode END() { return getToken(WaccParser.END, 0); }
		public FuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitFunc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncContext func() throws RecognitionException {
		FuncContext _localctx = new FuncContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_func);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87); type(0);
			setState(88); ident();
			setState(89); match(OPEN_PARENTHESES);
			setState(91);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << BOOL) | (1L << CHAR) | (1L << STRING) | (1L << PAIR))) != 0)) {
				{
				setState(90); paramList();
				}
			}

			setState(93); match(CLOSE_PARENTHESES);
			setState(94); match(IS);
			setState(95); stat(0);
			setState(96); match(END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamListContext extends ParserRuleContext {
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public List<TerminalNode> COMMA() { return getTokens(WaccParser.COMMA); }
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(WaccParser.COMMA, i);
		}
		public ParamListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitParamList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamListContext paramList() throws RecognitionException {
		ParamListContext _localctx = new ParamListContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_paramList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98); param();
			setState(103);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(99); match(COMMA);
				setState(100); param();
				}
				}
				setState(105);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106); type(0);
			setState(107); ident();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatContext extends ParserRuleContext {
		public FreeStatContext freeStat() {
			return getRuleContext(FreeStatContext.class,0);
		}
		public ExitStatContext exitStat() {
			return getRuleContext(ExitStatContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(WaccParser.SEMICOLON, 0); }
		public VarDeclarationContext varDeclaration() {
			return getRuleContext(VarDeclarationContext.class,0);
		}
		public TerminalNode SKIP() { return getToken(WaccParser.SKIP, 0); }
		public PrintStatContext printStat() {
			return getRuleContext(PrintStatContext.class,0);
		}
		public IfStatContext ifStat() {
			return getRuleContext(IfStatContext.class,0);
		}
		public VarAssignmentContext varAssignment() {
			return getRuleContext(VarAssignmentContext.class,0);
		}
		public WhileStatContext whileStat() {
			return getRuleContext(WhileStatContext.class,0);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public ReturnStatContext returnStat() {
			return getRuleContext(ReturnStatContext.class,0);
		}
		public PrintlnStatContext printlnStat() {
			return getRuleContext(PrintlnStatContext.class,0);
		}
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public ScopeStatContext scopeStat() {
			return getRuleContext(ScopeStatContext.class,0);
		}
		public ReadStatContext readStat() {
			return getRuleContext(ReadStatContext.class,0);
		}
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatContext stat() throws RecognitionException {
		return stat(0);
	}

	private StatContext stat(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		StatContext _localctx = new StatContext(_ctx, _parentState);
		StatContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_stat, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			switch (_input.LA(1)) {
			case SKIP:
				{
				setState(110); match(SKIP);
				}
				break;
			case INT:
			case BOOL:
			case CHAR:
			case STRING:
			case PAIR:
				{
				setState(111); varDeclaration();
				}
				break;
			case FST:
			case SND:
			case IDENT:
				{
				setState(112); varAssignment();
				}
				break;
			case READ:
				{
				setState(113); readStat();
				}
				break;
			case FREE:
				{
				setState(114); freeStat();
				}
				break;
			case RETURN:
				{
				setState(115); returnStat();
				}
				break;
			case EXIT:
				{
				setState(116); exitStat();
				}
				break;
			case PRINT:
				{
				setState(117); printStat();
				}
				break;
			case PRINTLN:
				{
				setState(118); printlnStat();
				}
				break;
			case IF:
				{
				setState(119); ifStat();
				}
				break;
			case WHILE:
				{
				setState(120); whileStat();
				}
				break;
			case BEGIN:
				{
				setState(121); scopeStat();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(129);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new StatContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_stat);
					setState(124);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(125); match(SEMICOLON);
					setState(126); stat(1);
					}
					} 
				}
				setState(131);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class VarDeclarationContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(WaccParser.EQUALS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public AssignRhsContext assignRhs() {
			return getRuleContext(AssignRhsContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public VarDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitVarDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDeclarationContext varDeclaration() throws RecognitionException {
		VarDeclarationContext _localctx = new VarDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_varDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132); type(0);
			setState(133); ident();
			setState(134); match(EQUALS);
			setState(135); assignRhs();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarAssignmentContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(WaccParser.EQUALS, 0); }
		public AssignLhsContext assignLhs() {
			return getRuleContext(AssignLhsContext.class,0);
		}
		public AssignRhsContext assignRhs() {
			return getRuleContext(AssignRhsContext.class,0);
		}
		public VarAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varAssignment; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitVarAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarAssignmentContext varAssignment() throws RecognitionException {
		VarAssignmentContext _localctx = new VarAssignmentContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_varAssignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137); assignLhs();
			setState(138); match(EQUALS);
			setState(139); assignRhs();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReadStatContext extends ParserRuleContext {
		public TerminalNode READ() { return getToken(WaccParser.READ, 0); }
		public AssignLhsContext assignLhs() {
			return getRuleContext(AssignLhsContext.class,0);
		}
		public ReadStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_readStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitReadStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReadStatContext readStat() throws RecognitionException {
		ReadStatContext _localctx = new ReadStatContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_readStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141); match(READ);
			setState(142); assignLhs();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FreeStatContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode FREE() { return getToken(WaccParser.FREE, 0); }
		public FreeStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_freeStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitFreeStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FreeStatContext freeStat() throws RecognitionException {
		FreeStatContext _localctx = new FreeStatContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_freeStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144); match(FREE);
			setState(145); expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStatContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RETURN() { return getToken(WaccParser.RETURN, 0); }
		public ReturnStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitReturnStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatContext returnStat() throws RecognitionException {
		ReturnStatContext _localctx = new ReturnStatContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_returnStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147); match(RETURN);
			setState(148); expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExitStatContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode EXIT() { return getToken(WaccParser.EXIT, 0); }
		public ExitStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exitStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitExitStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExitStatContext exitStat() throws RecognitionException {
		ExitStatContext _localctx = new ExitStatContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_exitStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150); match(EXIT);
			setState(151); expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrintStatContext extends ParserRuleContext {
		public TerminalNode PRINT() { return getToken(WaccParser.PRINT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public PrintStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_printStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitPrintStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrintStatContext printStat() throws RecognitionException {
		PrintStatContext _localctx = new PrintStatContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_printStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153); match(PRINT);
			setState(154); expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrintlnStatContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode PRINTLN() { return getToken(WaccParser.PRINTLN, 0); }
		public PrintlnStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_printlnStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitPrintlnStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrintlnStatContext printlnStat() throws RecognitionException {
		PrintlnStatContext _localctx = new PrintlnStatContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_printlnStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156); match(PRINTLN);
			setState(157); expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatContext extends ParserRuleContext {
		public TerminalNode THEN() { return getToken(WaccParser.THEN, 0); }
		public TerminalNode IF() { return getToken(WaccParser.IF, 0); }
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public TerminalNode FI() { return getToken(WaccParser.FI, 0); }
		public TerminalNode ELSE() { return getToken(WaccParser.ELSE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public IfStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitIfStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatContext ifStat() throws RecognitionException {
		IfStatContext _localctx = new IfStatContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_ifStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159); match(IF);
			setState(160); expr(0);
			setState(161); match(THEN);
			setState(162); stat(0);
			setState(163); match(ELSE);
			setState(164); stat(0);
			setState(165); match(FI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStatContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(WaccParser.WHILE, 0); }
		public TerminalNode DO() { return getToken(WaccParser.DO, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode DONE() { return getToken(WaccParser.DONE, 0); }
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public WhileStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitWhileStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatContext whileStat() throws RecognitionException {
		WhileStatContext _localctx = new WhileStatContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_whileStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(167); match(WHILE);
			setState(168); expr(0);
			setState(169); match(DO);
			setState(170); stat(0);
			setState(171); match(DONE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ScopeStatContext extends ParserRuleContext {
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public TerminalNode BEGIN() { return getToken(WaccParser.BEGIN, 0); }
		public TerminalNode END() { return getToken(WaccParser.END, 0); }
		public ScopeStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scopeStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitScopeStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScopeStatContext scopeStat() throws RecognitionException {
		ScopeStatContext _localctx = new ScopeStatContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_scopeStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173); match(BEGIN);
			setState(174); stat(0);
			setState(175); match(END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignLhsContext extends ParserRuleContext {
		public PairElemContext pairElem() {
			return getRuleContext(PairElemContext.class,0);
		}
		public ArrayElemContext arrayElem() {
			return getRuleContext(ArrayElemContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public AssignLhsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignLhs; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitAssignLhs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignLhsContext assignLhs() throws RecognitionException {
		AssignLhsContext _localctx = new AssignLhsContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_assignLhs);
		try {
			setState(180);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(177); ident();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(178); arrayElem();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(179); pairElem();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignRhsContext extends ParserRuleContext {
		public ArrayLiterContext arrayLiter() {
			return getRuleContext(ArrayLiterContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public PairElemContext pairElem() {
			return getRuleContext(PairElemContext.class,0);
		}
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public NewPairContext newPair() {
			return getRuleContext(NewPairContext.class,0);
		}
		public AssignRhsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignRhs; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitAssignRhs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignRhsContext assignRhs() throws RecognitionException {
		AssignRhsContext _localctx = new AssignRhsContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_assignRhs);
		try {
			setState(187);
			switch (_input.LA(1)) {
			case BOOL_LIT:
			case NOT:
			case LEN:
			case ORD:
			case CHR:
			case MINUS:
			case OPEN_PARENTHESES:
			case NULL:
			case IDENT:
			case INT_LIT:
			case CHAR_LIT:
			case STRING_LIT:
				enterOuterAlt(_localctx, 1);
				{
				setState(182); expr(0);
				}
				break;
			case OPEN_BRACKETS:
				enterOuterAlt(_localctx, 2);
				{
				setState(183); arrayLiter();
				}
				break;
			case NEW_PAIR:
				enterOuterAlt(_localctx, 3);
				{
				setState(184); newPair();
				}
				break;
			case FST:
			case SND:
				enterOuterAlt(_localctx, 4);
				{
				setState(185); pairElem();
				}
				break;
			case CALL:
				enterOuterAlt(_localctx, 5);
				{
				setState(186); funcCall();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NewPairContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode COMMA() { return getToken(WaccParser.COMMA, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
		public TerminalNode NEW_PAIR() { return getToken(WaccParser.NEW_PAIR, 0); }
		public NewPairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newPair; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitNewPair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NewPairContext newPair() throws RecognitionException {
		NewPairContext _localctx = new NewPairContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_newPair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189); match(NEW_PAIR);
			setState(190); match(OPEN_PARENTHESES);
			setState(191); expr(0);
			setState(192); match(COMMA);
			setState(193); expr(0);
			setState(194); match(CLOSE_PARENTHESES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncCallContext extends ParserRuleContext {
		public TerminalNode CALL() { return getToken(WaccParser.CALL, 0); }
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
		public ArgListContext argList() {
			return getRuleContext(ArgListContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public FuncCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcCall; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitFuncCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncCallContext funcCall() throws RecognitionException {
		FuncCallContext _localctx = new FuncCallContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_funcCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196); match(CALL);
			setState(197); ident();
			setState(198); match(OPEN_PARENTHESES);
			setState(200);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL_LIT) | (1L << NOT) | (1L << LEN) | (1L << ORD) | (1L << CHR) | (1L << MINUS) | (1L << OPEN_PARENTHESES) | (1L << NULL) | (1L << IDENT) | (1L << INT_LIT) | (1L << CHAR_LIT) | (1L << STRING_LIT))) != 0)) {
				{
				setState(199); argList();
				}
			}

			setState(202); match(CLOSE_PARENTHESES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgListContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public List<TerminalNode> COMMA() { return getTokens(WaccParser.COMMA); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(WaccParser.COMMA, i);
		}
		public ArgListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitArgList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgListContext argList() throws RecognitionException {
		ArgListContext _localctx = new ArgListContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_argList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(204); expr(0);
			setState(209);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(205); match(COMMA);
				setState(206); expr(0);
				}
				}
				setState(211);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PairElemContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode FST() { return getToken(WaccParser.FST, 0); }
		public TerminalNode SND() { return getToken(WaccParser.SND, 0); }
		public PairElemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pairElem; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitPairElem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairElemContext pairElem() throws RecognitionException {
		PairElemContext _localctx = new PairElemContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_pairElem);
		try {
			setState(216);
			switch (_input.LA(1)) {
			case FST:
				enterOuterAlt(_localctx, 1);
				{
				setState(212); match(FST);
				setState(213); expr(0);
				}
				break;
			case SND:
				enterOuterAlt(_localctx, 2);
				{
				setState(214); match(SND);
				setState(215); expr(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public PairTypeContext pairType() {
			return getRuleContext(PairTypeContext.class,0);
		}
		public TerminalNode OPEN_BRACKETS() { return getToken(WaccParser.OPEN_BRACKETS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode CLOSE_BRACKETS() { return getToken(WaccParser.CLOSE_BRACKETS, 0); }
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		return type(0);
	}

	private TypeContext type(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TypeContext _localctx = new TypeContext(_ctx, _parentState);
		TypeContext _prevctx = _localctx;
		int _startState = 44;
		enterRecursionRule(_localctx, 44, RULE_type, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			switch (_input.LA(1)) {
			case INT:
			case BOOL:
			case CHAR:
			case STRING:
				{
				setState(219); baseType();
				}
				break;
			case PAIR:
				{
				setState(220); pairType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(228);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TypeContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_type);
					setState(223);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(224); match(OPEN_BRACKETS);
					setState(225); match(CLOSE_BRACKETS);
					}
					} 
				}
				setState(230);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class BaseTypeContext extends ParserRuleContext {
		public TerminalNode BOOL() { return getToken(WaccParser.BOOL, 0); }
		public TerminalNode INT() { return getToken(WaccParser.INT, 0); }
		public TerminalNode STRING() { return getToken(WaccParser.STRING, 0); }
		public TerminalNode CHAR() { return getToken(WaccParser.CHAR, 0); }
		public BaseTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_baseType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitBaseType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BaseTypeContext baseType() throws RecognitionException {
		BaseTypeContext _localctx = new BaseTypeContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_baseType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << BOOL) | (1L << CHAR) | (1L << STRING))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PairTypeContext extends ParserRuleContext {
		public List<PairElemTypeContext> pairElemType() {
			return getRuleContexts(PairElemTypeContext.class);
		}
		public PairElemTypeContext pairElemType(int i) {
			return getRuleContext(PairElemTypeContext.class,i);
		}
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode COMMA() { return getToken(WaccParser.COMMA, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
		public TerminalNode PAIR() { return getToken(WaccParser.PAIR, 0); }
		public PairTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pairType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitPairType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairTypeContext pairType() throws RecognitionException {
		PairTypeContext _localctx = new PairTypeContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_pairType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233); match(PAIR);
			setState(234); match(OPEN_PARENTHESES);
			setState(235); pairElemType();
			setState(236); match(COMMA);
			setState(237); pairElemType();
			setState(238); match(CLOSE_PARENTHESES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PairElemTypeContext extends ParserRuleContext {
		public TerminalNode OPEN_BRACKETS() { return getToken(WaccParser.OPEN_BRACKETS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode PAIR() { return getToken(WaccParser.PAIR, 0); }
		public TerminalNode CLOSE_BRACKETS() { return getToken(WaccParser.CLOSE_BRACKETS, 0); }
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public PairElemTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pairElemType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitPairElemType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairElemTypeContext pairElemType() throws RecognitionException {
		PairElemTypeContext _localctx = new PairElemTypeContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_pairElemType);
		try {
			setState(246);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(240); baseType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(241); type(0);
				setState(242); match(OPEN_BRACKETS);
				setState(243); match(CLOSE_BRACKETS);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(245); match(PAIR);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public PairLiterContext pairLiter() {
			return getRuleContext(PairLiterContext.class,0);
		}
		public TerminalNode STRING_LIT() { return getToken(WaccParser.STRING_LIT, 0); }
		public TerminalNode CHAR_LIT() { return getToken(WaccParser.CHAR_LIT, 0); }
		public TerminalNode BOOL_LIT() { return getToken(WaccParser.BOOL_LIT, 0); }
		public TerminalNode INT_LIT() { return getToken(WaccParser.INT_LIT, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
		public ArrayElemContext arrayElem() {
			return getRuleContext(ArrayElemContext.class,0);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public OtherBinaryOperContext otherBinaryOper() {
			return getRuleContext(OtherBinaryOperContext.class,0);
		}
		public UnaryOperContext unaryOper() {
			return getRuleContext(UnaryOperContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public BoolBinaryOperContext boolBinaryOper() {
			return getRuleContext(BoolBinaryOperContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 52;
		enterRecursionRule(_localctx, 52, RULE_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(263);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(249); unaryOper();
				setState(250); expr(4);
				}
				break;
			case 2:
				{
				setState(252); match(INT_LIT);
				}
				break;
			case 3:
				{
				setState(253); match(BOOL_LIT);
				}
				break;
			case 4:
				{
				setState(254); match(CHAR_LIT);
				}
				break;
			case 5:
				{
				setState(255); match(STRING_LIT);
				}
				break;
			case 6:
				{
				setState(256); pairLiter();
				}
				break;
			case 7:
				{
				setState(257); ident();
				}
				break;
			case 8:
				{
				setState(258); arrayElem();
				}
				break;
			case 9:
				{
				setState(259); match(OPEN_PARENTHESES);
				setState(260); expr(0);
				setState(261); match(CLOSE_PARENTHESES);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(275);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(273);
					switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(265);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(266); otherBinaryOper();
						setState(267); expr(4);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(269);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(270); boolBinaryOper();
						setState(271); expr(3);
						}
						break;
					}
					} 
				}
				setState(277);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class UnaryOperContext extends ParserRuleContext {
		public TerminalNode LEN() { return getToken(WaccParser.LEN, 0); }
		public TerminalNode MINUS() { return getToken(WaccParser.MINUS, 0); }
		public TerminalNode NOT() { return getToken(WaccParser.NOT, 0); }
		public TerminalNode CHR() { return getToken(WaccParser.CHR, 0); }
		public TerminalNode ORD() { return getToken(WaccParser.ORD, 0); }
		public UnaryOperContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryOper; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitUnaryOper(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryOperContext unaryOper() throws RecognitionException {
		UnaryOperContext _localctx = new UnaryOperContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_unaryOper);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(278);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NOT) | (1L << LEN) | (1L << ORD) | (1L << CHR) | (1L << MINUS))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoolBinaryOperContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(WaccParser.AND, 0); }
		public TerminalNode OR() { return getToken(WaccParser.OR, 0); }
		public BoolBinaryOperContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolBinaryOper; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitBoolBinaryOper(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolBinaryOperContext boolBinaryOper() throws RecognitionException {
		BoolBinaryOperContext _localctx = new BoolBinaryOperContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_boolBinaryOper);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(280);
			_la = _input.LA(1);
			if ( !(_la==AND || _la==OR) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OtherBinaryOperContext extends ParserRuleContext {
		public TerminalNode LESS_THAN() { return getToken(WaccParser.LESS_THAN, 0); }
		public TerminalNode DIV() { return getToken(WaccParser.DIV, 0); }
		public TerminalNode LESS_THAN_EQ() { return getToken(WaccParser.LESS_THAN_EQ, 0); }
		public TerminalNode MULT() { return getToken(WaccParser.MULT, 0); }
		public TerminalNode MINUS() { return getToken(WaccParser.MINUS, 0); }
		public TerminalNode GREATER_THAN() { return getToken(WaccParser.GREATER_THAN, 0); }
		public TerminalNode PLUS() { return getToken(WaccParser.PLUS, 0); }
		public TerminalNode NOT_EQ() { return getToken(WaccParser.NOT_EQ, 0); }
		public TerminalNode MOD() { return getToken(WaccParser.MOD, 0); }
		public TerminalNode EQ() { return getToken(WaccParser.EQ, 0); }
		public TerminalNode GREATER_THAN_EQ() { return getToken(WaccParser.GREATER_THAN_EQ, 0); }
		public OtherBinaryOperContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_otherBinaryOper; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitOtherBinaryOper(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OtherBinaryOperContext otherBinaryOper() throws RecognitionException {
		OtherBinaryOperContext _localctx = new OtherBinaryOperContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_otherBinaryOper);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MULT) | (1L << DIV) | (1L << MOD) | (1L << PLUS) | (1L << MINUS) | (1L << GREATER_THAN) | (1L << GREATER_THAN_EQ) | (1L << LESS_THAN) | (1L << LESS_THAN_EQ) | (1L << EQ) | (1L << NOT_EQ))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentContext extends ParserRuleContext {
		public TerminalNode IDENT() { return getToken(WaccParser.IDENT, 0); }
		public IdentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ident; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitIdent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentContext ident() throws RecognitionException {
		IdentContext _localctx = new IdentContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_ident);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284); match(IDENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayElemContext extends ParserRuleContext {
		public TerminalNode OPEN_BRACKETS(int i) {
			return getToken(WaccParser.OPEN_BRACKETS, i);
		}
		public TerminalNode CLOSE_BRACKETS(int i) {
			return getToken(WaccParser.CLOSE_BRACKETS, i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public List<TerminalNode> OPEN_BRACKETS() { return getTokens(WaccParser.OPEN_BRACKETS); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> CLOSE_BRACKETS() { return getTokens(WaccParser.CLOSE_BRACKETS); }
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ArrayElemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayElem; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitArrayElem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayElemContext arrayElem() throws RecognitionException {
		ArrayElemContext _localctx = new ArrayElemContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_arrayElem);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(286); ident();
			setState(291); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(287); match(OPEN_BRACKETS);
					setState(288); expr(0);
					setState(289); match(CLOSE_BRACKETS);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(293); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntSignContext extends ParserRuleContext {
		public TerminalNode MINUS() { return getToken(WaccParser.MINUS, 0); }
		public TerminalNode PLUS() { return getToken(WaccParser.PLUS, 0); }
		public IntSignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intSign; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitIntSign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntSignContext intSign() throws RecognitionException {
		IntSignContext _localctx = new IntSignContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_intSign);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(295);
			_la = _input.LA(1);
			if ( !(_la==PLUS || _la==MINUS) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CharacterContext extends ParserRuleContext {
		public TerminalNode CHARACTER_LIT() { return getToken(WaccParser.CHARACTER_LIT, 0); }
		public CharacterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_character; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitCharacter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CharacterContext character() throws RecognitionException {
		CharacterContext _localctx = new CharacterContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_character);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(297); match(CHARACTER_LIT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EscapedCharContext extends ParserRuleContext {
		public TerminalNode ESCAPED_CHAR_LIT() { return getToken(WaccParser.ESCAPED_CHAR_LIT, 0); }
		public EscapedCharContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_escapedChar; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitEscapedChar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EscapedCharContext escapedChar() throws RecognitionException {
		EscapedCharContext _localctx = new EscapedCharContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_escapedChar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(299); match(ESCAPED_CHAR_LIT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayLiterContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode OPEN_BRACKETS() { return getToken(WaccParser.OPEN_BRACKETS, 0); }
		public List<TerminalNode> COMMA() { return getTokens(WaccParser.COMMA); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode CLOSE_BRACKETS() { return getToken(WaccParser.CLOSE_BRACKETS, 0); }
		public TerminalNode COMMA(int i) {
			return getToken(WaccParser.COMMA, i);
		}
		public ArrayLiterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayLiter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitArrayLiter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayLiterContext arrayLiter() throws RecognitionException {
		ArrayLiterContext _localctx = new ArrayLiterContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_arrayLiter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(301); match(OPEN_BRACKETS);
			setState(310);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL_LIT) | (1L << NOT) | (1L << LEN) | (1L << ORD) | (1L << CHR) | (1L << MINUS) | (1L << OPEN_PARENTHESES) | (1L << NULL) | (1L << IDENT) | (1L << INT_LIT) | (1L << CHAR_LIT) | (1L << STRING_LIT))) != 0)) {
				{
				setState(302); expr(0);
				setState(307);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(303); match(COMMA);
					setState(304); expr(0);
					}
					}
					setState(309);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(312); match(CLOSE_BRACKETS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PairLiterContext extends ParserRuleContext {
		public TerminalNode NULL() { return getToken(WaccParser.NULL, 0); }
		public PairLiterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pairLiter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitPairLiter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairLiterContext pairLiter() throws RecognitionException {
		PairLiterContext _localctx = new PairLiterContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_pairLiter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(314); match(NULL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommentContext extends ParserRuleContext {
		public TerminalNode COMMENT() { return getToken(WaccParser.COMMENT, 0); }
		public CommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comment; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitComment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommentContext comment() throws RecognitionException {
		CommentContext _localctx = new CommentContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_comment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(316); match(COMMENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 4: return stat_sempred((StatContext)_localctx, predIndex);
		case 22: return type_sempred((TypeContext)_localctx, predIndex);
		case 26: return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2: return precpred(_ctx, 3);
		case 3: return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean type_sempred(TypeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1: return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean stat_sempred(StatContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3A\u0141\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\3\2\3\2\7\2Q\n\2\f\2\16\2"+
		"T\13\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\5\3^\n\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\4\3\4\3\4\7\4h\n\4\f\4\16\4k\13\4\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6}\n\6\3\6\3\6\3\6\7\6\u0082\n\6\f\6\16"+
		"\6\u0085\13\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n"+
		"\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3"+
		"\21\3\21\3\22\3\22\3\22\5\22\u00b7\n\22\3\23\3\23\3\23\3\23\3\23\5\23"+
		"\u00be\n\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\5\25"+
		"\u00cb\n\25\3\25\3\25\3\26\3\26\3\26\7\26\u00d2\n\26\f\26\16\26\u00d5"+
		"\13\26\3\27\3\27\3\27\3\27\5\27\u00db\n\27\3\30\3\30\3\30\5\30\u00e0\n"+
		"\30\3\30\3\30\3\30\7\30\u00e5\n\30\f\30\16\30\u00e8\13\30\3\31\3\31\3"+
		"\32\3\32\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u00f9"+
		"\n\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\3\34\5\34\u010a\n\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\7\34"+
		"\u0114\n\34\f\34\16\34\u0117\13\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3"+
		" \3!\3!\3!\3!\3!\6!\u0126\n!\r!\16!\u0127\3\"\3\"\3#\3#\3$\3$\3%\3%\3"+
		"%\3%\7%\u0134\n%\f%\16%\u0137\13%\5%\u0139\n%\3%\3%\3&\3&\3\'\3\'\3\'"+
		"\2\5\n.\66(\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\66"+
		"8:<>@BDFHJL\2\7\3\2\32\35\4\2\"%**\3\2\62\63\4\2&*,\61\3\2)*\u0143\2N"+
		"\3\2\2\2\4Y\3\2\2\2\6d\3\2\2\2\bl\3\2\2\2\n|\3\2\2\2\f\u0086\3\2\2\2\16"+
		"\u008b\3\2\2\2\20\u008f\3\2\2\2\22\u0092\3\2\2\2\24\u0095\3\2\2\2\26\u0098"+
		"\3\2\2\2\30\u009b\3\2\2\2\32\u009e\3\2\2\2\34\u00a1\3\2\2\2\36\u00a9\3"+
		"\2\2\2 \u00af\3\2\2\2\"\u00b6\3\2\2\2$\u00bd\3\2\2\2&\u00bf\3\2\2\2(\u00c6"+
		"\3\2\2\2*\u00ce\3\2\2\2,\u00da\3\2\2\2.\u00df\3\2\2\2\60\u00e9\3\2\2\2"+
		"\62\u00eb\3\2\2\2\64\u00f8\3\2\2\2\66\u0109\3\2\2\28\u0118\3\2\2\2:\u011a"+
		"\3\2\2\2<\u011c\3\2\2\2>\u011e\3\2\2\2@\u0120\3\2\2\2B\u0129\3\2\2\2D"+
		"\u012b\3\2\2\2F\u012d\3\2\2\2H\u012f\3\2\2\2J\u013c\3\2\2\2L\u013e\3\2"+
		"\2\2NR\7\5\2\2OQ\5\4\3\2PO\3\2\2\2QT\3\2\2\2RP\3\2\2\2RS\3\2\2\2SU\3\2"+
		"\2\2TR\3\2\2\2UV\5\n\6\2VW\7\6\2\2WX\7\2\2\3X\3\3\2\2\2YZ\5.\30\2Z[\5"+
		"> \2[]\7\64\2\2\\^\5\6\4\2]\\\3\2\2\2]^\3\2\2\2^_\3\2\2\2_`\7\65\2\2`"+
		"a\7\7\2\2ab\5\n\6\2bc\7\6\2\2c\5\3\2\2\2di\5\b\5\2ef\78\2\2fh\5\b\5\2"+
		"ge\3\2\2\2hk\3\2\2\2ig\3\2\2\2ij\3\2\2\2j\7\3\2\2\2ki\3\2\2\2lm\5.\30"+
		"\2mn\5> \2n\t\3\2\2\2op\b\6\1\2p}\7\b\2\2q}\5\f\7\2r}\5\16\b\2s}\5\20"+
		"\t\2t}\5\22\n\2u}\5\24\13\2v}\5\26\f\2w}\5\30\r\2x}\5\32\16\2y}\5\34\17"+
		"\2z}\5\36\20\2{}\5 \21\2|o\3\2\2\2|q\3\2\2\2|r\3\2\2\2|s\3\2\2\2|t\3\2"+
		"\2\2|u\3\2\2\2|v\3\2\2\2|w\3\2\2\2|x\3\2\2\2|y\3\2\2\2|z\3\2\2\2|{\3\2"+
		"\2\2}\u0083\3\2\2\2~\177\f\3\2\2\177\u0080\79\2\2\u0080\u0082\5\n\6\3"+
		"\u0081~\3\2\2\2\u0082\u0085\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084\3"+
		"\2\2\2\u0084\13\3\2\2\2\u0085\u0083\3\2\2\2\u0086\u0087\5.\30\2\u0087"+
		"\u0088\5> \2\u0088\u0089\7+\2\2\u0089\u008a\5$\23\2\u008a\r\3\2\2\2\u008b"+
		"\u008c\5\"\22\2\u008c\u008d\7+\2\2\u008d\u008e\5$\23\2\u008e\17\3\2\2"+
		"\2\u008f\u0090\7\t\2\2\u0090\u0091\5\"\22\2\u0091\21\3\2\2\2\u0092\u0093"+
		"\7\n\2\2\u0093\u0094\5\66\34\2\u0094\23\3\2\2\2\u0095\u0096\7\13\2\2\u0096"+
		"\u0097\5\66\34\2\u0097\25\3\2\2\2\u0098\u0099\7\f\2\2\u0099\u009a\5\66"+
		"\34\2\u009a\27\3\2\2\2\u009b\u009c\7\r\2\2\u009c\u009d\5\66\34\2\u009d"+
		"\31\3\2\2\2\u009e\u009f\7\16\2\2\u009f\u00a0\5\66\34\2\u00a0\33\3\2\2"+
		"\2\u00a1\u00a2\7\17\2\2\u00a2\u00a3\5\66\34\2\u00a3\u00a4\7\20\2\2\u00a4"+
		"\u00a5\5\n\6\2\u00a5\u00a6\7\21\2\2\u00a6\u00a7\5\n\6\2\u00a7\u00a8\7"+
		"\22\2\2\u00a8\35\3\2\2\2\u00a9\u00aa\7\23\2\2\u00aa\u00ab\5\66\34\2\u00ab"+
		"\u00ac\7\24\2\2\u00ac\u00ad\5\n\6\2\u00ad\u00ae\7\25\2\2\u00ae\37\3\2"+
		"\2\2\u00af\u00b0\7\5\2\2\u00b0\u00b1\5\n\6\2\u00b1\u00b2\7\6\2\2\u00b2"+
		"!\3\2\2\2\u00b3\u00b7\5> \2\u00b4\u00b7\5@!\2\u00b5\u00b7\5,\27\2\u00b6"+
		"\u00b3\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b6\u00b5\3\2\2\2\u00b7#\3\2\2\2"+
		"\u00b8\u00be\5\66\34\2\u00b9\u00be\5H%\2\u00ba\u00be\5&\24\2\u00bb\u00be"+
		"\5,\27\2\u00bc\u00be\5(\25\2\u00bd\u00b8\3\2\2\2\u00bd\u00b9\3\2\2\2\u00bd"+
		"\u00ba\3\2\2\2\u00bd\u00bb\3\2\2\2\u00bd\u00bc\3\2\2\2\u00be%\3\2\2\2"+
		"\u00bf\u00c0\7\26\2\2\u00c0\u00c1\7\64\2\2\u00c1\u00c2\5\66\34\2\u00c2"+
		"\u00c3\78\2\2\u00c3\u00c4\5\66\34\2\u00c4\u00c5\7\65\2\2\u00c5\'\3\2\2"+
		"\2\u00c6\u00c7\7\27\2\2\u00c7\u00c8\5> \2\u00c8\u00ca\7\64\2\2\u00c9\u00cb"+
		"\5*\26\2\u00ca\u00c9\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc"+
		"\u00cd\7\65\2\2\u00cd)\3\2\2\2\u00ce\u00d3\5\66\34\2\u00cf\u00d0\78\2"+
		"\2\u00d0\u00d2\5\66\34\2\u00d1\u00cf\3\2\2\2\u00d2\u00d5\3\2\2\2\u00d3"+
		"\u00d1\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4+\3\2\2\2\u00d5\u00d3\3\2\2\2"+
		"\u00d6\u00d7\7\30\2\2\u00d7\u00db\5\66\34\2\u00d8\u00d9\7\31\2\2\u00d9"+
		"\u00db\5\66\34\2\u00da\u00d6\3\2\2\2\u00da\u00d8\3\2\2\2\u00db-\3\2\2"+
		"\2\u00dc\u00dd\b\30\1\2\u00dd\u00e0\5\60\31\2\u00de\u00e0\5\62\32\2\u00df"+
		"\u00dc\3\2\2\2\u00df\u00de\3\2\2\2\u00e0\u00e6\3\2\2\2\u00e1\u00e2\f\4"+
		"\2\2\u00e2\u00e3\7\66\2\2\u00e3\u00e5\7\67\2\2\u00e4\u00e1\3\2\2\2\u00e5"+
		"\u00e8\3\2\2\2\u00e6\u00e4\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7/\3\2\2\2"+
		"\u00e8\u00e6\3\2\2\2\u00e9\u00ea\t\2\2\2\u00ea\61\3\2\2\2\u00eb\u00ec"+
		"\7\36\2\2\u00ec\u00ed\7\64\2\2\u00ed\u00ee\5\64\33\2\u00ee\u00ef\78\2"+
		"\2\u00ef\u00f0\5\64\33\2\u00f0\u00f1\7\65\2\2\u00f1\63\3\2\2\2\u00f2\u00f9"+
		"\5\60\31\2\u00f3\u00f4\5.\30\2\u00f4\u00f5\7\66\2\2\u00f5\u00f6\7\67\2"+
		"\2\u00f6\u00f9\3\2\2\2\u00f7\u00f9\7\36\2\2\u00f8\u00f2\3\2\2\2\u00f8"+
		"\u00f3\3\2\2\2\u00f8\u00f7\3\2\2\2\u00f9\65\3\2\2\2\u00fa\u00fb\b\34\1"+
		"\2\u00fb\u00fc\58\35\2\u00fc\u00fd\5\66\34\6\u00fd\u010a\3\2\2\2\u00fe"+
		"\u010a\7<\2\2\u00ff\u010a\7\37\2\2\u0100\u010a\7@\2\2\u0101\u010a\7A\2"+
		"\2\u0102\u010a\5J&\2\u0103\u010a\5> \2\u0104\u010a\5@!\2\u0105\u0106\7"+
		"\64\2\2\u0106\u0107\5\66\34\2\u0107\u0108\7\65\2\2\u0108\u010a\3\2\2\2"+
		"\u0109\u00fa\3\2\2\2\u0109\u00fe\3\2\2\2\u0109\u00ff\3\2\2\2\u0109\u0100"+
		"\3\2\2\2\u0109\u0101\3\2\2\2\u0109\u0102\3\2\2\2\u0109\u0103\3\2\2\2\u0109"+
		"\u0104\3\2\2\2\u0109\u0105\3\2\2\2\u010a\u0115\3\2\2\2\u010b\u010c\f\5"+
		"\2\2\u010c\u010d\5<\37\2\u010d\u010e\5\66\34\6\u010e\u0114\3\2\2\2\u010f"+
		"\u0110\f\4\2\2\u0110\u0111\5:\36\2\u0111\u0112\5\66\34\5\u0112\u0114\3"+
		"\2\2\2\u0113\u010b\3\2\2\2\u0113\u010f\3\2\2\2\u0114\u0117\3\2\2\2\u0115"+
		"\u0113\3\2\2\2\u0115\u0116\3\2\2\2\u0116\67\3\2\2\2\u0117\u0115\3\2\2"+
		"\2\u0118\u0119\t\3\2\2\u01199\3\2\2\2\u011a\u011b\t\4\2\2\u011b;\3\2\2"+
		"\2\u011c\u011d\t\5\2\2\u011d=\3\2\2\2\u011e\u011f\7;\2\2\u011f?\3\2\2"+
		"\2\u0120\u0125\5> \2\u0121\u0122\7\66\2\2\u0122\u0123\5\66\34\2\u0123"+
		"\u0124\7\67\2\2\u0124\u0126\3\2\2\2\u0125\u0121\3\2\2\2\u0126\u0127\3"+
		"\2\2\2\u0127\u0125\3\2\2\2\u0127\u0128\3\2\2\2\u0128A\3\2\2\2\u0129\u012a"+
		"\t\6\2\2\u012aC\3\2\2\2\u012b\u012c\7>\2\2\u012cE\3\2\2\2\u012d\u012e"+
		"\7?\2\2\u012eG\3\2\2\2\u012f\u0138\7\66\2\2\u0130\u0135\5\66\34\2\u0131"+
		"\u0132\78\2\2\u0132\u0134\5\66\34\2\u0133\u0131\3\2\2\2\u0134\u0137\3"+
		"\2\2\2\u0135\u0133\3\2\2\2\u0135\u0136\3\2\2\2\u0136\u0139\3\2\2\2\u0137"+
		"\u0135\3\2\2\2\u0138\u0130\3\2\2\2\u0138\u0139\3\2\2\2\u0139\u013a\3\2"+
		"\2\2\u013a\u013b\7\67\2\2\u013bI\3\2\2\2\u013c\u013d\7:\2\2\u013dK\3\2"+
		"\2\2\u013e\u013f\7\3\2\2\u013fM\3\2\2\2\25R]i|\u0083\u00b6\u00bd\u00ca"+
		"\u00d3\u00da\u00df\u00e6\u00f8\u0109\u0113\u0115\u0127\u0135\u0138";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}