parser grammar WaccParser;

options {
  tokenVocab=WaccLexer;
}

prog: BEGIN (func)* stat END EOF ;

func: type ident OPEN_PARENTHESES (param-list)? CLOSE_PARENTHESES IS stat END

param-list: param (COMMA param)*

param: type ident

stat
  : SKIP
  | type ident EQUALS assign-rhs
  | assign-lhs EQUALS assign-rhs
  | READ assign-lhs 
  | FREE expr
  | RETURN expr
  | EXIT expr
  | PRINT expr
  | PRINTLN expr
  | IF expr THEN stat ELSE stat FI
  | WHILE expr DO stat DONE
  | BEGIN stat END
  | stat SEMICOLON stat

assign-lhs
  : ident
  | array-elem
  | pair-elem

assign-rhs
  : expr
  | array-liter
  | NEW_PAIR OPEN_PARENTHESES expr COMMA expr CLOSE_PARENTHESES
  | CALL ident OPEN_PARENTHESES arg-list CLOSE_PARENTHESES

arg-list: expr (COMMA expr)*

pair-elem
  : FST expr
  | SND expr

type
  : base-type
  | array-type
  | pair-type

base-type
  : INT
  | BOOL
  | CHAR
  | STRING

array-type: type OPEN_BLACKETS CLOSE_BRACKETS

pair-type: PAIR OPEN_PARENTHESES pair-elem-type COMMA pair-elem-type 
           CLOSE_PARENTHESES

pair-elem-type
  : base-type
  | array-type
  | pair-type

expr
  : int-liter
  | bool-liter
  | char-liter
  | str-liter
  | pair-liter
  | ident
  | array-elem
  | unary-oper expr
  | expr binary-oper expr
  | OPEN_PARENTHESES expr CLOSE_PARENTHESES

unaryOper
  : NOT
  | MINUS
  | LEN
  | ORD
  | CHR
  ;

binaryOper
  : MULT 
  | DIV 
  | MOD 
  | PLUS 
  | MINUS 
  | GREATER_THAN 
  | GREATER_THAN_EQ 
  | LESS_THAN
  | LESS_THAN_EQ 
  | EQ 
  | NOT_EQ
  | AND  
  | OR 
  ;

ident : IDENT ; 

arrayElem : ident (OPEN_BRACKETS expr CLOSE_BRACKETS)+ ;

intLiter : intSign? digit+ ;
  
digit : DIGIT ;

intSign 
  : PLUS
  | MINUS
  ;

boolLiter : BOOL_LIT ;

charLiter : CHAR_LIT ;

strLiter : STRING_LIT ;

character : CHARACTER ;

escapedChar : ESCAPED_CHAR ;

arrayLiter : ARRAY_LIT ; -- cyrus implement on Basic Lexer Please

pairLiter : PAIR_LIT ; -- cyrus implement on Basic Lexer Please

comment : COMMENT ; 

