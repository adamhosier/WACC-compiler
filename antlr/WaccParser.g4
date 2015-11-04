parser grammar WaccParser;

options {
  tokenVocab=WaccLexer;
}

prog: BEGIN (func)* stat END EOF ;

func: type ident OPEN_PARENTHESES (paramList)? CLOSE_PARENTHESES IS stat END ;

paramList: param (COMMA param)* ;

param: type ident ;

stat
  : SKIP
  | type ident EQUALS assignRhs
  | assignLhs EQUALS assignRhs
  | READ assignLhs 
  | FREE expr
  | RETURN expr
  | EXIT expr
  | PRINT expr
  | PRINTLN expr
  | IF expr THEN stat ELSE stat FI
  | WHILE expr DO stat DONE
  | BEGIN stat END
  | <assoc=right> stat SEMICOLON stat
  ;

assignLhs
  : ident
  | arrayElem
  | pairElem
  ;

assignRhs
  : expr
  | arrayLiter
  | NEW_PAIR OPEN_PARENTHESES expr COMMA expr CLOSE_PARENTHESES
  | pairElem
  | CALL ident OPEN_PARENTHESES argList? CLOSE_PARENTHESES
  ;

argList: expr (COMMA expr)* ;

pairElem
  : FST expr
  | SND expr
  ;

type
  : baseType
  | type OPEN_BRACKETS CLOSE_BRACKETS 
  | pairType
  ;

baseType
  : INT
  | BOOL
  | CHAR
  | STRING
  ;

pairType
  : PAIR OPEN_PARENTHESES pairElemType COMMA pairElemType CLOSE_PARENTHESES ;

pairElemType
  : baseType
  | type OPEN_BRACKETS CLOSE_BRACKETS 
  | PAIR 
  ;

expr
  : intLiter
  | boolLiter
  | charLiter
  | strLiter
  | pairLiter
  | ident
  | arrayElem
  | unaryOper expr
  | expr binaryOper expr
  | OPEN_PARENTHESES expr CLOSE_PARENTHESES
  ;

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

intLiter : INT_LIT;
  
digit : DIGIT_LIT ;

intSign 
  : PLUS
  | MINUS
  ;

boolLiter : BOOL_LIT ;

charLiter : CHAR_LIT ;

strLiter : STRING_LIT ;

character : CHARACTER_LIT ;

escapedChar : ESCAPED_CHAR_LIT ;

arrayLiter : OPEN_BRACKETS (expr (COMMA expr)*)? CLOSE_BRACKETS ;

pairLiter : NULL ;

comment : COMMENT ; 
