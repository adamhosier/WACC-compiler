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
		EXIT=10, PRINT=11, PRINTLN=12, IF=13, THEN=14, ELSE=15, FI=16, FOR=17, 
		WHILE=18, DO=19, DONE=20, NEW_PAIR=21, CALL=22, FST=23, SND=24, INT=25, 
		BOOL=26, CHAR=27, STRING=28, PAIR=29, BOOL_LIT=30, TRUE=31, FALSE=32, 
		NOT=33, LEN=34, ORD=35, CHR=36, INC=37, DEC=38, MULT=39, DIV=40, MOD=41, 
		PLUS=42, MINUS=43, EQUALS=44, GREATER_THAN=45, GREATER_THAN_EQ=46, LESS_THAN=47, 
		LESS_THAN_EQ=48, EQ=49, NOT_EQ=50, AND=51, OR=52, OPEN_PARENTHESES=53, 
		CLOSE_PARENTHESES=54, OPEN_BRACKETS=55, CLOSE_BRACKETS=56, COMMA=57, SEMICOLON=58, 
		NULL=59, IDENT=60, INC_IDENT=61, DEC_IDENT=62, INT_LIT=63, DIGIT_LIT=64, 
		CHARACTER_LIT=65, ESCAPED_CHAR_LIT=66, CHAR_LIT=67, STRING_LIT=68;
	public static final String[] tokenNames = {
		"<INVALID>", "COMMENT", "WS", "'begin'", "'end'", "'is'", "'skip'", "'read'", 
		"'free'", "'return'", "'exit'", "'print'", "'println'", "'if'", "'then'", 
		"'else'", "'fi'", "'for'", "'while'", "'do'", "'done'", "'newpair'", "'call'", 
		"'fst'", "'snd'", "'int'", "'bool'", "'char'", "'string'", "'pair'", "BOOL_LIT", 
		"'true'", "'false'", "'!'", "'len'", "'ord'", "'chr'", "'++'", "'--'", 
		"'*'", "'/'", "'%'", "'+'", "'-'", "'='", "'>'", "'>='", "'<'", "'<='", 
		"'=='", "'!='", "'&&'", "'||'", "'('", "')'", "'['", "']'", "','", "';'", 
		"'null'", "IDENT", "INC_IDENT", "DEC_IDENT", "INT_LIT", "DIGIT_LIT", "CHARACTER_LIT", 
		"ESCAPED_CHAR_LIT", "CHAR_LIT", "STRING_LIT"
	};
	public static final int
		RULE_prog = 0, RULE_func = 1, RULE_paramList = 2, RULE_param = 3, RULE_stat = 4, 
		RULE_varDeclaration = 5, RULE_varAssignment = 6, RULE_incrementStat = 7, 
		RULE_readStat = 8, RULE_freeStat = 9, RULE_returnStat = 10, RULE_exitStat = 11, 
		RULE_printStat = 12, RULE_printlnStat = 13, RULE_ifStat = 14, RULE_forStat = 15, 
		RULE_whileStat = 16, RULE_scopeStat = 17, RULE_assignLhs = 18, RULE_assignRhs = 19, 
		RULE_newPair = 20, RULE_funcCall = 21, RULE_argList = 22, RULE_pairElem = 23, 
		RULE_type = 24, RULE_baseType = 25, RULE_pairType = 26, RULE_pairElemType = 27, 
		RULE_expr = 28, RULE_unaryOper = 29, RULE_boolBinaryOper = 30, RULE_otherBinaryOper = 31, 
		RULE_ident = 32, RULE_arrayElem = 33, RULE_intSign = 34, RULE_character = 35, 
		RULE_escapedChar = 36, RULE_arrayLiter = 37, RULE_pairLiter = 38, RULE_comment = 39;
	public static final String[] ruleNames = {
		"prog", "func", "paramList", "param", "stat", "varDeclaration", "varAssignment", 
		"incrementStat", "readStat", "freeStat", "returnStat", "exitStat", "printStat", 
		"printlnStat", "ifStat", "forStat", "whileStat", "scopeStat", "assignLhs", 
		"assignRhs", "newPair", "funcCall", "argList", "pairElem", "type", "baseType", 
		"pairType", "pairElemType", "expr", "unaryOper", "boolBinaryOper", "otherBinaryOper", 
		"ident", "arrayElem", "intSign", "character", "escapedChar", "arrayLiter", 
		"pairLiter", "comment"
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
		public FuncContext func(int i) {
			return getRuleContext(FuncContext.class,i);
		}
		public TerminalNode BEGIN() { return getToken(WaccParser.BEGIN, 0); }
		public TerminalNode END() { return getToken(WaccParser.END, 0); }
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
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
			setState(80); match(BEGIN);
			setState(84);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(81); func();
					}
					} 
				}
				setState(86);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			setState(87); stat(0);
			setState(88); match(END);
			setState(89); match(EOF);
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
		public TerminalNode IS() { return getToken(WaccParser.IS, 0); }
		public ParamListContext paramList() {
			return getRuleContext(ParamListContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TerminalNode END() { return getToken(WaccParser.END, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
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
			setState(91); type(0);
			setState(92); ident();
			setState(93); match(OPEN_PARENTHESES);
			setState(95);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << BOOL) | (1L << CHAR) | (1L << STRING) | (1L << PAIR))) != 0)) {
				{
				setState(94); paramList();
				}
			}

			setState(97); match(CLOSE_PARENTHESES);
			setState(98); match(IS);
			setState(99); stat(0);
			setState(100); match(END);
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
			setState(102); param();
			setState(107);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(103); match(COMMA);
				setState(104); param();
				}
				}
				setState(109);
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
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
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
			setState(110); type(0);
			setState(111); ident();
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
		public TerminalNode SEMICOLON() { return getToken(WaccParser.SEMICOLON, 0); }
		public VarDeclarationContext varDeclaration() {
			return getRuleContext(VarDeclarationContext.class,0);
		}
		public WhileStatContext whileStat() {
			return getRuleContext(WhileStatContext.class,0);
		}
		public IfStatContext ifStat() {
			return getRuleContext(IfStatContext.class,0);
		}
		public ReadStatContext readStat() {
			return getRuleContext(ReadStatContext.class,0);
		}
		public ScopeStatContext scopeStat() {
			return getRuleContext(ScopeStatContext.class,0);
		}
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public PrintlnStatContext printlnStat() {
			return getRuleContext(PrintlnStatContext.class,0);
		}
		public IncrementStatContext incrementStat() {
			return getRuleContext(IncrementStatContext.class,0);
		}
		public VarAssignmentContext varAssignment() {
			return getRuleContext(VarAssignmentContext.class,0);
		}
		public FreeStatContext freeStat() {
			return getRuleContext(FreeStatContext.class,0);
		}
		public ForStatContext forStat() {
			return getRuleContext(ForStatContext.class,0);
		}
		public PrintStatContext printStat() {
			return getRuleContext(PrintStatContext.class,0);
		}
		public ExitStatContext exitStat() {
			return getRuleContext(ExitStatContext.class,0);
		}
		public TerminalNode SKIP() { return getToken(WaccParser.SKIP, 0); }
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public ReturnStatContext returnStat() {
			return getRuleContext(ReturnStatContext.class,0);
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
			setState(128);
			switch (_input.LA(1)) {
			case SKIP:
				{
				setState(114); match(SKIP);
				}
				break;
			case INT:
			case BOOL:
			case CHAR:
			case STRING:
			case PAIR:
				{
				setState(115); varDeclaration();
				}
				break;
			case FST:
			case SND:
			case IDENT:
				{
				setState(116); varAssignment();
				}
				break;
			case INC_IDENT:
			case DEC_IDENT:
				{
				setState(117); incrementStat();
				}
				break;
			case READ:
				{
				setState(118); readStat();
				}
				break;
			case FREE:
				{
				setState(119); freeStat();
				}
				break;
			case RETURN:
				{
				setState(120); returnStat();
				}
				break;
			case EXIT:
				{
				setState(121); exitStat();
				}
				break;
			case PRINT:
				{
				setState(122); printStat();
				}
				break;
			case PRINTLN:
				{
				setState(123); printlnStat();
				}
				break;
			case IF:
				{
				setState(124); ifStat();
				}
				break;
			case WHILE:
				{
				setState(125); whileStat();
				}
				break;
			case FOR:
				{
				setState(126); forStat();
				}
				break;
			case BEGIN:
				{
				setState(127); scopeStat();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(135);
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
					setState(130);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(131); match(SEMICOLON);
					setState(132); stat(1);
					}
					} 
				}
				setState(137);
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
		public AssignRhsContext assignRhs() {
			return getRuleContext(AssignRhsContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
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
			setState(138); type(0);
			setState(139); ident();
			setState(140); match(EQUALS);
			setState(141); assignRhs();
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
			setState(143); assignLhs();
			setState(144); match(EQUALS);
			setState(145); assignRhs();
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

	public static class IncrementStatContext extends ParserRuleContext {
		public TerminalNode INC_IDENT() { return getToken(WaccParser.INC_IDENT, 0); }
		public TerminalNode DEC_IDENT() { return getToken(WaccParser.DEC_IDENT, 0); }
		public IncrementStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_incrementStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitIncrementStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IncrementStatContext incrementStat() throws RecognitionException {
		IncrementStatContext _localctx = new IncrementStatContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_incrementStat);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			_la = _input.LA(1);
			if ( !(_la==INC_IDENT || _la==DEC_IDENT) ) {
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

	public static class ReadStatContext extends ParserRuleContext {
		public AssignLhsContext assignLhs() {
			return getRuleContext(AssignLhsContext.class,0);
		}
		public TerminalNode READ() { return getToken(WaccParser.READ, 0); }
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
		enterRule(_localctx, 16, RULE_readStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149); match(READ);
			setState(150); assignLhs();
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
		public TerminalNode FREE() { return getToken(WaccParser.FREE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
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
		enterRule(_localctx, 18, RULE_freeStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152); match(FREE);
			setState(153); expr(0);
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
		public TerminalNode RETURN() { return getToken(WaccParser.RETURN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
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
		enterRule(_localctx, 20, RULE_returnStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155); match(RETURN);
			setState(156); expr(0);
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
		enterRule(_localctx, 22, RULE_exitStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158); match(EXIT);
			setState(159); expr(0);
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
		enterRule(_localctx, 24, RULE_printStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161); match(PRINT);
			setState(162); expr(0);
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
		enterRule(_localctx, 26, RULE_printlnStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164); match(PRINTLN);
			setState(165); expr(0);
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
		public TerminalNode FI() { return getToken(WaccParser.FI, 0); }
		public TerminalNode ELSE() { return getToken(WaccParser.ELSE, 0); }
		public TerminalNode IF() { return getToken(WaccParser.IF, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode THEN() { return getToken(WaccParser.THEN, 0); }
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
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
		enterRule(_localctx, 28, RULE_ifStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(167); match(IF);
			setState(168); expr(0);
			setState(169); match(THEN);
			setState(170); stat(0);
			setState(171); match(ELSE);
			setState(172); stat(0);
			setState(173); match(FI);
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

	public static class ForStatContext extends ParserRuleContext {
		public TerminalNode DONE() { return getToken(WaccParser.DONE, 0); }
		public List<TerminalNode> SEMICOLON() { return getTokens(WaccParser.SEMICOLON); }
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode DO() { return getToken(WaccParser.DO, 0); }
		public TerminalNode FOR() { return getToken(WaccParser.FOR, 0); }
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public TerminalNode SEMICOLON(int i) {
			return getToken(WaccParser.SEMICOLON, i);
		}
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
		public ForStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitForStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatContext forStat() throws RecognitionException {
		ForStatContext _localctx = new ForStatContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_forStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175); match(FOR);
			setState(176); match(OPEN_PARENTHESES);
			setState(177); stat(0);
			setState(178); match(SEMICOLON);
			setState(179); expr(0);
			setState(180); match(SEMICOLON);
			setState(181); stat(0);
			setState(182); match(CLOSE_PARENTHESES);
			setState(183); match(DO);
			setState(184); stat(0);
			setState(185); match(DONE);
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
		public TerminalNode DONE() { return getToken(WaccParser.DONE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode DO() { return getToken(WaccParser.DO, 0); }
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public TerminalNode WHILE() { return getToken(WaccParser.WHILE, 0); }
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
		enterRule(_localctx, 32, RULE_whileStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187); match(WHILE);
			setState(188); expr(0);
			setState(189); match(DO);
			setState(190); stat(0);
			setState(191); match(DONE);
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
		public TerminalNode BEGIN() { return getToken(WaccParser.BEGIN, 0); }
		public TerminalNode END() { return getToken(WaccParser.END, 0); }
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
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
		enterRule(_localctx, 34, RULE_scopeStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193); match(BEGIN);
			setState(194); stat(0);
			setState(195); match(END);
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
		public ArrayElemContext arrayElem() {
			return getRuleContext(ArrayElemContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public PairElemContext pairElem() {
			return getRuleContext(PairElemContext.class,0);
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
		enterRule(_localctx, 36, RULE_assignLhs);
		try {
			setState(200);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(197); ident();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(198); arrayElem();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(199); pairElem();
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
		public NewPairContext newPair() {
			return getRuleContext(NewPairContext.class,0);
		}
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ArrayLiterContext arrayLiter() {
			return getRuleContext(ArrayLiterContext.class,0);
		}
		public PairElemContext pairElem() {
			return getRuleContext(PairElemContext.class,0);
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
		enterRule(_localctx, 38, RULE_assignRhs);
		try {
			setState(207);
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
				setState(202); expr(0);
				}
				break;
			case OPEN_BRACKETS:
				enterOuterAlt(_localctx, 2);
				{
				setState(203); arrayLiter();
				}
				break;
			case NEW_PAIR:
				enterOuterAlt(_localctx, 3);
				{
				setState(204); newPair();
				}
				break;
			case FST:
			case SND:
				enterOuterAlt(_localctx, 4);
				{
				setState(205); pairElem();
				}
				break;
			case CALL:
				enterOuterAlt(_localctx, 5);
				{
				setState(206); funcCall();
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
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode NEW_PAIR() { return getToken(WaccParser.NEW_PAIR, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode COMMA() { return getToken(WaccParser.COMMA, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
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
		enterRule(_localctx, 40, RULE_newPair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209); match(NEW_PAIR);
			setState(210); match(OPEN_PARENTHESES);
			setState(211); expr(0);
			setState(212); match(COMMA);
			setState(213); expr(0);
			setState(214); match(CLOSE_PARENTHESES);
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
		public ArgListContext argList() {
			return getRuleContext(ArgListContext.class,0);
		}
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode CALL() { return getToken(WaccParser.CALL, 0); }
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
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
		enterRule(_localctx, 42, RULE_funcCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216); match(CALL);
			setState(217); ident();
			setState(218); match(OPEN_PARENTHESES);
			setState(220);
			_la = _input.LA(1);
			if (((((_la - 30)) & ~0x3f) == 0 && ((1L << (_la - 30)) & ((1L << (BOOL_LIT - 30)) | (1L << (NOT - 30)) | (1L << (LEN - 30)) | (1L << (ORD - 30)) | (1L << (CHR - 30)) | (1L << (MINUS - 30)) | (1L << (OPEN_PARENTHESES - 30)) | (1L << (NULL - 30)) | (1L << (IDENT - 30)) | (1L << (INT_LIT - 30)) | (1L << (CHAR_LIT - 30)) | (1L << (STRING_LIT - 30)))) != 0)) {
				{
				setState(219); argList();
				}
			}

			setState(222); match(CLOSE_PARENTHESES);
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
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(WaccParser.COMMA); }
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
		enterRule(_localctx, 44, RULE_argList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(224); expr(0);
			setState(229);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(225); match(COMMA);
				setState(226); expr(0);
				}
				}
				setState(231);
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
		public TerminalNode SND() { return getToken(WaccParser.SND, 0); }
		public TerminalNode FST() { return getToken(WaccParser.FST, 0); }
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
		enterRule(_localctx, 46, RULE_pairElem);
		try {
			setState(236);
			switch (_input.LA(1)) {
			case FST:
				enterOuterAlt(_localctx, 1);
				{
				setState(232); match(FST);
				setState(233); expr(0);
				}
				break;
			case SND:
				enterOuterAlt(_localctx, 2);
				{
				setState(234); match(SND);
				setState(235); expr(0);
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
		public TerminalNode CLOSE_BRACKETS() { return getToken(WaccParser.CLOSE_BRACKETS, 0); }
		public TerminalNode OPEN_BRACKETS() { return getToken(WaccParser.OPEN_BRACKETS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public PairTypeContext pairType() {
			return getRuleContext(PairTypeContext.class,0);
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
		int _startState = 48;
		enterRecursionRule(_localctx, 48, RULE_type, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(241);
			switch (_input.LA(1)) {
			case INT:
			case BOOL:
			case CHAR:
			case STRING:
				{
				setState(239); baseType();
				}
				break;
			case PAIR:
				{
				setState(240); pairType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(248);
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
					setState(243);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(244); match(OPEN_BRACKETS);
					setState(245); match(CLOSE_BRACKETS);
					}
					} 
				}
				setState(250);
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
		public TerminalNode STRING() { return getToken(WaccParser.STRING, 0); }
		public TerminalNode CHAR() { return getToken(WaccParser.CHAR, 0); }
		public TerminalNode INT() { return getToken(WaccParser.INT, 0); }
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
		enterRule(_localctx, 50, RULE_baseType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(251);
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
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public PairElemTypeContext pairElemType(int i) {
			return getRuleContext(PairElemTypeContext.class,i);
		}
		public List<PairElemTypeContext> pairElemType() {
			return getRuleContexts(PairElemTypeContext.class);
		}
		public TerminalNode PAIR() { return getToken(WaccParser.PAIR, 0); }
		public TerminalNode COMMA() { return getToken(WaccParser.COMMA, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
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
		enterRule(_localctx, 52, RULE_pairType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(253); match(PAIR);
			setState(254); match(OPEN_PARENTHESES);
			setState(255); pairElemType();
			setState(256); match(COMMA);
			setState(257); pairElemType();
			setState(258); match(CLOSE_PARENTHESES);
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
		public TerminalNode CLOSE_BRACKETS() { return getToken(WaccParser.CLOSE_BRACKETS, 0); }
		public TerminalNode PAIR() { return getToken(WaccParser.PAIR, 0); }
		public TerminalNode OPEN_BRACKETS() { return getToken(WaccParser.OPEN_BRACKETS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
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
		enterRule(_localctx, 54, RULE_pairElemType);
		try {
			setState(266);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(260); baseType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(261); type(0);
				setState(262); match(OPEN_BRACKETS);
				setState(263); match(CLOSE_BRACKETS);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(265); match(PAIR);
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
		public TerminalNode CHAR_LIT() { return getToken(WaccParser.CHAR_LIT, 0); }
		public UnaryOperContext unaryOper() {
			return getRuleContext(UnaryOperContext.class,0);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public OtherBinaryOperContext otherBinaryOper() {
			return getRuleContext(OtherBinaryOperContext.class,0);
		}
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
		public TerminalNode INT_LIT() { return getToken(WaccParser.INT_LIT, 0); }
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public ArrayElemContext arrayElem() {
			return getRuleContext(ArrayElemContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public PairLiterContext pairLiter() {
			return getRuleContext(PairLiterContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TerminalNode BOOL_LIT() { return getToken(WaccParser.BOOL_LIT, 0); }
		public TerminalNode STRING_LIT() { return getToken(WaccParser.STRING_LIT, 0); }
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
		int _startState = 56;
		enterRecursionRule(_localctx, 56, RULE_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(283);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(269); unaryOper();
				setState(270); expr(4);
				}
				break;
			case 2:
				{
				setState(272); match(INT_LIT);
				}
				break;
			case 3:
				{
				setState(273); match(BOOL_LIT);
				}
				break;
			case 4:
				{
				setState(274); match(CHAR_LIT);
				}
				break;
			case 5:
				{
				setState(275); match(STRING_LIT);
				}
				break;
			case 6:
				{
				setState(276); pairLiter();
				}
				break;
			case 7:
				{
				setState(277); ident();
				}
				break;
			case 8:
				{
				setState(278); arrayElem();
				}
				break;
			case 9:
				{
				setState(279); match(OPEN_PARENTHESES);
				setState(280); expr(0);
				setState(281); match(CLOSE_PARENTHESES);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(295);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(293);
					switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(285);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(286); otherBinaryOper();
						setState(287); expr(4);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(289);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(290); boolBinaryOper();
						setState(291); expr(3);
						}
						break;
					}
					} 
				}
				setState(297);
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
		public TerminalNode NOT() { return getToken(WaccParser.NOT, 0); }
		public TerminalNode ORD() { return getToken(WaccParser.ORD, 0); }
		public TerminalNode LEN() { return getToken(WaccParser.LEN, 0); }
		public TerminalNode MINUS() { return getToken(WaccParser.MINUS, 0); }
		public TerminalNode CHR() { return getToken(WaccParser.CHR, 0); }
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
		enterRule(_localctx, 58, RULE_unaryOper);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(298);
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
		enterRule(_localctx, 60, RULE_boolBinaryOper);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(300);
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
		public TerminalNode LESS_THAN_EQ() { return getToken(WaccParser.LESS_THAN_EQ, 0); }
		public TerminalNode MULT() { return getToken(WaccParser.MULT, 0); }
		public TerminalNode GREATER_THAN_EQ() { return getToken(WaccParser.GREATER_THAN_EQ, 0); }
		public TerminalNode NOT_EQ() { return getToken(WaccParser.NOT_EQ, 0); }
		public TerminalNode PLUS() { return getToken(WaccParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(WaccParser.MINUS, 0); }
		public TerminalNode EQ() { return getToken(WaccParser.EQ, 0); }
		public TerminalNode MOD() { return getToken(WaccParser.MOD, 0); }
		public TerminalNode LESS_THAN() { return getToken(WaccParser.LESS_THAN, 0); }
		public TerminalNode DIV() { return getToken(WaccParser.DIV, 0); }
		public TerminalNode GREATER_THAN() { return getToken(WaccParser.GREATER_THAN, 0); }
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
		enterRule(_localctx, 62, RULE_otherBinaryOper);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(302);
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
		enterRule(_localctx, 64, RULE_ident);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(304); match(IDENT);
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
		public List<TerminalNode> CLOSE_BRACKETS() { return getTokens(WaccParser.CLOSE_BRACKETS); }
		public TerminalNode OPEN_BRACKETS(int i) {
			return getToken(WaccParser.OPEN_BRACKETS, i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public List<TerminalNode> OPEN_BRACKETS() { return getTokens(WaccParser.OPEN_BRACKETS); }
		public TerminalNode CLOSE_BRACKETS(int i) {
			return getToken(WaccParser.CLOSE_BRACKETS, i);
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
		enterRule(_localctx, 66, RULE_arrayElem);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(306); ident();
			setState(311); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(307); match(OPEN_BRACKETS);
					setState(308); expr(0);
					setState(309); match(CLOSE_BRACKETS);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(313); 
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
		public TerminalNode PLUS() { return getToken(WaccParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(WaccParser.MINUS, 0); }
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
		enterRule(_localctx, 68, RULE_intSign);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(315);
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
		enterRule(_localctx, 70, RULE_character);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(317); match(CHARACTER_LIT);
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
		enterRule(_localctx, 72, RULE_escapedChar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(319); match(ESCAPED_CHAR_LIT);
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
		public TerminalNode CLOSE_BRACKETS() { return getToken(WaccParser.CLOSE_BRACKETS, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(WaccParser.COMMA); }
		public TerminalNode OPEN_BRACKETS() { return getToken(WaccParser.OPEN_BRACKETS, 0); }
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
		enterRule(_localctx, 74, RULE_arrayLiter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(321); match(OPEN_BRACKETS);
			setState(330);
			_la = _input.LA(1);
			if (((((_la - 30)) & ~0x3f) == 0 && ((1L << (_la - 30)) & ((1L << (BOOL_LIT - 30)) | (1L << (NOT - 30)) | (1L << (LEN - 30)) | (1L << (ORD - 30)) | (1L << (CHR - 30)) | (1L << (MINUS - 30)) | (1L << (OPEN_PARENTHESES - 30)) | (1L << (NULL - 30)) | (1L << (IDENT - 30)) | (1L << (INT_LIT - 30)) | (1L << (CHAR_LIT - 30)) | (1L << (STRING_LIT - 30)))) != 0)) {
				{
				setState(322); expr(0);
				setState(327);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(323); match(COMMA);
					setState(324); expr(0);
					}
					}
					setState(329);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(332); match(CLOSE_BRACKETS);
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
		enterRule(_localctx, 76, RULE_pairLiter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(334); match(NULL);
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
		enterRule(_localctx, 78, RULE_comment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(336); match(COMMENT);
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
		case 24: return type_sempred((TypeContext)_localctx, predIndex);
		case 28: return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean stat_sempred(StatContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 1);
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

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3F\u0155\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\3\2\3\2\7\2U\n"+
		"\2\f\2\16\2X\13\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\5\3b\n\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\4\3\4\3\4\7\4l\n\4\f\4\16\4o\13\4\3\5\3\5\3\5\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u0083\n\6\3\6\3\6\3"+
		"\6\7\6\u0088\n\6\f\6\16\6\u008b\13\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3"+
		"\b\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16"+
		"\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\5\24\u00cb\n\24\3\25\3\25"+
		"\3\25\3\25\3\25\5\25\u00d2\n\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\27"+
		"\3\27\3\27\3\27\5\27\u00df\n\27\3\27\3\27\3\30\3\30\3\30\7\30\u00e6\n"+
		"\30\f\30\16\30\u00e9\13\30\3\31\3\31\3\31\3\31\5\31\u00ef\n\31\3\32\3"+
		"\32\3\32\5\32\u00f4\n\32\3\32\3\32\3\32\7\32\u00f9\n\32\f\32\16\32\u00fc"+
		"\13\32\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\5\35\u010d\n\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\3\36\3\36\3\36\3\36\3\36\3\36\5\36\u011e\n\36\3\36\3\36\3\36\3\36\3\36"+
		"\3\36\3\36\3\36\7\36\u0128\n\36\f\36\16\36\u012b\13\36\3\37\3\37\3 \3"+
		" \3!\3!\3\"\3\"\3#\3#\3#\3#\3#\6#\u013a\n#\r#\16#\u013b\3$\3$\3%\3%\3"+
		"&\3&\3\'\3\'\3\'\3\'\7\'\u0148\n\'\f\'\16\'\u014b\13\'\5\'\u014d\n\'\3"+
		"\'\3\'\3(\3(\3)\3)\3)\2\5\n\62:*\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36"+
		" \"$&(*,.\60\62\64\668:<>@BDFHJLNP\2\b\3\2?@\3\2\33\36\4\2#&--\3\2\65"+
		"\66\4\2)-/\64\3\2,-\u0157\2R\3\2\2\2\4]\3\2\2\2\6h\3\2\2\2\bp\3\2\2\2"+
		"\n\u0082\3\2\2\2\f\u008c\3\2\2\2\16\u0091\3\2\2\2\20\u0095\3\2\2\2\22"+
		"\u0097\3\2\2\2\24\u009a\3\2\2\2\26\u009d\3\2\2\2\30\u00a0\3\2\2\2\32\u00a3"+
		"\3\2\2\2\34\u00a6\3\2\2\2\36\u00a9\3\2\2\2 \u00b1\3\2\2\2\"\u00bd\3\2"+
		"\2\2$\u00c3\3\2\2\2&\u00ca\3\2\2\2(\u00d1\3\2\2\2*\u00d3\3\2\2\2,\u00da"+
		"\3\2\2\2.\u00e2\3\2\2\2\60\u00ee\3\2\2\2\62\u00f3\3\2\2\2\64\u00fd\3\2"+
		"\2\2\66\u00ff\3\2\2\28\u010c\3\2\2\2:\u011d\3\2\2\2<\u012c\3\2\2\2>\u012e"+
		"\3\2\2\2@\u0130\3\2\2\2B\u0132\3\2\2\2D\u0134\3\2\2\2F\u013d\3\2\2\2H"+
		"\u013f\3\2\2\2J\u0141\3\2\2\2L\u0143\3\2\2\2N\u0150\3\2\2\2P\u0152\3\2"+
		"\2\2RV\7\5\2\2SU\5\4\3\2TS\3\2\2\2UX\3\2\2\2VT\3\2\2\2VW\3\2\2\2WY\3\2"+
		"\2\2XV\3\2\2\2YZ\5\n\6\2Z[\7\6\2\2[\\\7\2\2\3\\\3\3\2\2\2]^\5\62\32\2"+
		"^_\5B\"\2_a\7\67\2\2`b\5\6\4\2a`\3\2\2\2ab\3\2\2\2bc\3\2\2\2cd\78\2\2"+
		"de\7\7\2\2ef\5\n\6\2fg\7\6\2\2g\5\3\2\2\2hm\5\b\5\2ij\7;\2\2jl\5\b\5\2"+
		"ki\3\2\2\2lo\3\2\2\2mk\3\2\2\2mn\3\2\2\2n\7\3\2\2\2om\3\2\2\2pq\5\62\32"+
		"\2qr\5B\"\2r\t\3\2\2\2st\b\6\1\2t\u0083\7\b\2\2u\u0083\5\f\7\2v\u0083"+
		"\5\16\b\2w\u0083\5\20\t\2x\u0083\5\22\n\2y\u0083\5\24\13\2z\u0083\5\26"+
		"\f\2{\u0083\5\30\r\2|\u0083\5\32\16\2}\u0083\5\34\17\2~\u0083\5\36\20"+
		"\2\177\u0083\5\"\22\2\u0080\u0083\5 \21\2\u0081\u0083\5$\23\2\u0082s\3"+
		"\2\2\2\u0082u\3\2\2\2\u0082v\3\2\2\2\u0082w\3\2\2\2\u0082x\3\2\2\2\u0082"+
		"y\3\2\2\2\u0082z\3\2\2\2\u0082{\3\2\2\2\u0082|\3\2\2\2\u0082}\3\2\2\2"+
		"\u0082~\3\2\2\2\u0082\177\3\2\2\2\u0082\u0080\3\2\2\2\u0082\u0081\3\2"+
		"\2\2\u0083\u0089\3\2\2\2\u0084\u0085\f\3\2\2\u0085\u0086\7<\2\2\u0086"+
		"\u0088\5\n\6\3\u0087\u0084\3\2\2\2\u0088\u008b\3\2\2\2\u0089\u0087\3\2"+
		"\2\2\u0089\u008a\3\2\2\2\u008a\13\3\2\2\2\u008b\u0089\3\2\2\2\u008c\u008d"+
		"\5\62\32\2\u008d\u008e\5B\"\2\u008e\u008f\7.\2\2\u008f\u0090\5(\25\2\u0090"+
		"\r\3\2\2\2\u0091\u0092\5&\24\2\u0092\u0093\7.\2\2\u0093\u0094\5(\25\2"+
		"\u0094\17\3\2\2\2\u0095\u0096\t\2\2\2\u0096\21\3\2\2\2\u0097\u0098\7\t"+
		"\2\2\u0098\u0099\5&\24\2\u0099\23\3\2\2\2\u009a\u009b\7\n\2\2\u009b\u009c"+
		"\5:\36\2\u009c\25\3\2\2\2\u009d\u009e\7\13\2\2\u009e\u009f\5:\36\2\u009f"+
		"\27\3\2\2\2\u00a0\u00a1\7\f\2\2\u00a1\u00a2\5:\36\2\u00a2\31\3\2\2\2\u00a3"+
		"\u00a4\7\r\2\2\u00a4\u00a5\5:\36\2\u00a5\33\3\2\2\2\u00a6\u00a7\7\16\2"+
		"\2\u00a7\u00a8\5:\36\2\u00a8\35\3\2\2\2\u00a9\u00aa\7\17\2\2\u00aa\u00ab"+
		"\5:\36\2\u00ab\u00ac\7\20\2\2\u00ac\u00ad\5\n\6\2\u00ad\u00ae\7\21\2\2"+
		"\u00ae\u00af\5\n\6\2\u00af\u00b0\7\22\2\2\u00b0\37\3\2\2\2\u00b1\u00b2"+
		"\7\23\2\2\u00b2\u00b3\7\67\2\2\u00b3\u00b4\5\n\6\2\u00b4\u00b5\7<\2\2"+
		"\u00b5\u00b6\5:\36\2\u00b6\u00b7\7<\2\2\u00b7\u00b8\5\n\6\2\u00b8\u00b9"+
		"\78\2\2\u00b9\u00ba\7\25\2\2\u00ba\u00bb\5\n\6\2\u00bb\u00bc\7\26\2\2"+
		"\u00bc!\3\2\2\2\u00bd\u00be\7\24\2\2\u00be\u00bf\5:\36\2\u00bf\u00c0\7"+
		"\25\2\2\u00c0\u00c1\5\n\6\2\u00c1\u00c2\7\26\2\2\u00c2#\3\2\2\2\u00c3"+
		"\u00c4\7\5\2\2\u00c4\u00c5\5\n\6\2\u00c5\u00c6\7\6\2\2\u00c6%\3\2\2\2"+
		"\u00c7\u00cb\5B\"\2\u00c8\u00cb\5D#\2\u00c9\u00cb\5\60\31\2\u00ca\u00c7"+
		"\3\2\2\2\u00ca\u00c8\3\2\2\2\u00ca\u00c9\3\2\2\2\u00cb\'\3\2\2\2\u00cc"+
		"\u00d2\5:\36\2\u00cd\u00d2\5L\'\2\u00ce\u00d2\5*\26\2\u00cf\u00d2\5\60"+
		"\31\2\u00d0\u00d2\5,\27\2\u00d1\u00cc\3\2\2\2\u00d1\u00cd\3\2\2\2\u00d1"+
		"\u00ce\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d1\u00d0\3\2\2\2\u00d2)\3\2\2\2"+
		"\u00d3\u00d4\7\27\2\2\u00d4\u00d5\7\67\2\2\u00d5\u00d6\5:\36\2\u00d6\u00d7"+
		"\7;\2\2\u00d7\u00d8\5:\36\2\u00d8\u00d9\78\2\2\u00d9+\3\2\2\2\u00da\u00db"+
		"\7\30\2\2\u00db\u00dc\5B\"\2\u00dc\u00de\7\67\2\2\u00dd\u00df\5.\30\2"+
		"\u00de\u00dd\3\2\2\2\u00de\u00df\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0\u00e1"+
		"\78\2\2\u00e1-\3\2\2\2\u00e2\u00e7\5:\36\2\u00e3\u00e4\7;\2\2\u00e4\u00e6"+
		"\5:\36\2\u00e5\u00e3\3\2\2\2\u00e6\u00e9\3\2\2\2\u00e7\u00e5\3\2\2\2\u00e7"+
		"\u00e8\3\2\2\2\u00e8/\3\2\2\2\u00e9\u00e7\3\2\2\2\u00ea\u00eb\7\31\2\2"+
		"\u00eb\u00ef\5:\36\2\u00ec\u00ed\7\32\2\2\u00ed\u00ef\5:\36\2\u00ee\u00ea"+
		"\3\2\2\2\u00ee\u00ec\3\2\2\2\u00ef\61\3\2\2\2\u00f0\u00f1\b\32\1\2\u00f1"+
		"\u00f4\5\64\33\2\u00f2\u00f4\5\66\34\2\u00f3\u00f0\3\2\2\2\u00f3\u00f2"+
		"\3\2\2\2\u00f4\u00fa\3\2\2\2\u00f5\u00f6\f\4\2\2\u00f6\u00f7\79\2\2\u00f7"+
		"\u00f9\7:\2\2\u00f8\u00f5\3\2\2\2\u00f9\u00fc\3\2\2\2\u00fa\u00f8\3\2"+
		"\2\2\u00fa\u00fb\3\2\2\2\u00fb\63\3\2\2\2\u00fc\u00fa\3\2\2\2\u00fd\u00fe"+
		"\t\3\2\2\u00fe\65\3\2\2\2\u00ff\u0100\7\37\2\2\u0100\u0101\7\67\2\2\u0101"+
		"\u0102\58\35\2\u0102\u0103\7;\2\2\u0103\u0104\58\35\2\u0104\u0105\78\2"+
		"\2\u0105\67\3\2\2\2\u0106\u010d\5\64\33\2\u0107\u0108\5\62\32\2\u0108"+
		"\u0109\79\2\2\u0109\u010a\7:\2\2\u010a\u010d\3\2\2\2\u010b\u010d\7\37"+
		"\2\2\u010c\u0106\3\2\2\2\u010c\u0107\3\2\2\2\u010c\u010b\3\2\2\2\u010d"+
		"9\3\2\2\2\u010e\u010f\b\36\1\2\u010f\u0110\5<\37\2\u0110\u0111\5:\36\6"+
		"\u0111\u011e\3\2\2\2\u0112\u011e\7A\2\2\u0113\u011e\7 \2\2\u0114\u011e"+
		"\7E\2\2\u0115\u011e\7F\2\2\u0116\u011e\5N(\2\u0117\u011e\5B\"\2\u0118"+
		"\u011e\5D#\2\u0119\u011a\7\67\2\2\u011a\u011b\5:\36\2\u011b\u011c\78\2"+
		"\2\u011c\u011e\3\2\2\2\u011d\u010e\3\2\2\2\u011d\u0112\3\2\2\2\u011d\u0113"+
		"\3\2\2\2\u011d\u0114\3\2\2\2\u011d\u0115\3\2\2\2\u011d\u0116\3\2\2\2\u011d"+
		"\u0117\3\2\2\2\u011d\u0118\3\2\2\2\u011d\u0119\3\2\2\2\u011e\u0129\3\2"+
		"\2\2\u011f\u0120\f\5\2\2\u0120\u0121\5@!\2\u0121\u0122\5:\36\6\u0122\u0128"+
		"\3\2\2\2\u0123\u0124\f\4\2\2\u0124\u0125\5> \2\u0125\u0126\5:\36\5\u0126"+
		"\u0128\3\2\2\2\u0127\u011f\3\2\2\2\u0127\u0123\3\2\2\2\u0128\u012b\3\2"+
		"\2\2\u0129\u0127\3\2\2\2\u0129\u012a\3\2\2\2\u012a;\3\2\2\2\u012b\u0129"+
		"\3\2\2\2\u012c\u012d\t\4\2\2\u012d=\3\2\2\2\u012e\u012f\t\5\2\2\u012f"+
		"?\3\2\2\2\u0130\u0131\t\6\2\2\u0131A\3\2\2\2\u0132\u0133\7>\2\2\u0133"+
		"C\3\2\2\2\u0134\u0139\5B\"\2\u0135\u0136\79\2\2\u0136\u0137\5:\36\2\u0137"+
		"\u0138\7:\2\2\u0138\u013a\3\2\2\2\u0139\u0135\3\2\2\2\u013a\u013b\3\2"+
		"\2\2\u013b\u0139\3\2\2\2\u013b\u013c\3\2\2\2\u013cE\3\2\2\2\u013d\u013e"+
		"\t\7\2\2\u013eG\3\2\2\2\u013f\u0140\7C\2\2\u0140I\3\2\2\2\u0141\u0142"+
		"\7D\2\2\u0142K\3\2\2\2\u0143\u014c\79\2\2\u0144\u0149\5:\36\2\u0145\u0146"+
		"\7;\2\2\u0146\u0148\5:\36\2\u0147\u0145\3\2\2\2\u0148\u014b\3\2\2\2\u0149"+
		"\u0147\3\2\2\2\u0149\u014a\3\2\2\2\u014a\u014d\3\2\2\2\u014b\u0149\3\2"+
		"\2\2\u014c\u0144\3\2\2\2\u014c\u014d\3\2\2\2\u014d\u014e\3\2\2\2\u014e"+
		"\u014f\7:\2\2\u014fM\3\2\2\2\u0150\u0151\7=\2\2\u0151O\3\2\2\2\u0152\u0153"+
		"\7\3\2\2\u0153Q\3\2\2\2\25Vam\u0082\u0089\u00ca\u00d1\u00de\u00e7\u00ee"+
		"\u00f3\u00fa\u010c\u011d\u0127\u0129\u013b\u0149\u014c";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}