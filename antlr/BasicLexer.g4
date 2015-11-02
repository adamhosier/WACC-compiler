lexer grammar BasicLexer;

BEGIN : 'begin' ;
END : 'end' ;
IS : 'is' ;

// statements
SKIP : 'skip' ;
READ : 'read' ;
FREE : 'free' ;
RETURN : 'return' ;
EXIT : 'exit' ;
PRINT : 'print' ;
PRINTLN : 'println';
IF : 'if' ;
THEN : 'then' ;
ELSE : 'else' ;
FI : 'fi' ;
WHILE : 'while' ;
DO : 'do' ;
DONE : 'done' ;

NEW_PAIR : 'newpair' ;
CALL : 'call' ;

FST : 'fst' ;
SND : 'snd' ;

// base-types
INT : 'int' ;
BOOL : 'bool' ;
CHAR : 'char' ;
STRING : 'string' ;
PAIR : 'pair' ;

//operators
//unary
NOT : '!' ;
LEN : 'len' ;
ORD : 'ord' ;
CHR : 'chr' ;
//binary
MULT : '*' ;
DIV : '/' ;
MOD : '%' ;
PLUS : '+' ;
MINUS : '-' ;
EQUALS : '=' ;
GREATER_THAN : '>' ;
GREATER_THAN_EQ : '>=' ;
LESS_THAN : '<' ;
LESS_THAN_EQ : '<=' ;
EQ : '==' ;
NOT_EQ : '!=' ;
AND : '&&' ;
OR : '||' ;

//brackets
OPEN_PARENTHESES : '(' ;
CLOSE_PARENTHESES : ')' ;
OPEN_BRACKETS : '[' ;
CLOSE_BRACKETS : ']' ;

COMMA : ',' ;
SEMICOLON : ';' ;

SINGLE_QUOTE : '\'' ;
DOUBLE_QUOTE : '"' ;

NULL : 'null' ;

fragment DIGIT : '0'..'9' ;
fragment LOWERCASE : 'a'..'z' ;
fragment UPPERCASE : 'A'..'Z' ;
fragment UNDERSCORE : '_' ;

IDENT
  :
  (UNDERSCORE
  | LOWERCASE
  | UPPERCASE)
  (UNDERSCORE
  | LOWERCASE
  | UPPERCASE
  | DIGIT)*
  ;

fragment ESCAPED_CHAR
  :
  ('\\' '0'
  | '\\' 'b'
  | '\\' 'n'
  | '\\' 'f'
  | '\\' 'r'
  | '\\' DOUBLE_QUOTE
  | '\\' SINGLE_QUOTE
  | '\\' '\\')
  ;
fragment CHARACTER
  :
  ~('\''
  | '"'
  | '\\')
  | ESCAPED_CHAR
  ;

// literals
INTEGER : DIGIT+ ;
TRUE : 'true' ;
FALSE : 'false' ;
CHAR_LIT : SINGLE_QUOTE CHARACTER SINGLE_QUOTE ;
STRING_LIT : DOUBLE_QUOTE CHARACTER* DOUBLE_QUOTE ;

COMMENT : '#' ~[\r\n]* [\r\n] -> skip ;
WHITESPACE : (' '|'\n'|'\t'|'\r') -> skip ;