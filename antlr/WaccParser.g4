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
  | stat SEMICOLON stat
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
  | CALL ident OPEN_PARENTHESES argList CLOSE_PARENTHESES
  ;

argList: expr (COMMA expr)* ;

pairElem
  : FST expr
  | SND expr
  ;

type
  : baseType
  | arrayType
  | pairType
  ;

baseType
  : INT
  | BOOL
  | CHAR
  | STRING
  ;

arrayType: type OPEN_BLACKETS CLOSE_BRACKETS ;

pairType: PAIR OPEN_PARENTHESES pairElemType COMMA pairElemType 
          CLOSE_PARENTHESES ;

pairElemType
  : baseType
  | arrayType
  | pairType
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
