lexer grammar WaccLexer;

COMMENT : '#' ~[\r\n]* [\r\n] -> skip ;
WS : [ \n\r\t]+ -> channel(HIDDEN) ;

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
BOOL : 'bool';
CHAR : 'char' ;
STRING : 'string' ;
PAIR : 'pair' ;

BOOL_LIT : TRUE | FALSE ;
TRUE : 'true' ;
FALSE : 'false' ;

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

fragment SINGLE_QUOTE : '\'' ;
fragment DOUBLE_QUOTE : '"' ;

NULL : 'null' ;

fragment DIGIT : '0'..'9' ;
fragment LOWERCASE : 'a'..'z' ;
fragment UPPERCASE : 'A'..'Z' ;
fragment UNDERSCORE : '_' ;

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

// literals
INT_LIT : (PLUS | MINUS)? DIGIT+ ;
DIGIT_LIT : DIGIT ;
CHARACTER_LIT : CHARACTER ;
ESCAPED_CHAR_LIT : ESCAPED_CHAR ;
CHAR_LIT : SINGLE_QUOTE CHARACTER SINGLE_QUOTE ;
STRING_LIT : DOUBLE_QUOTE (~'"')* DOUBLE_QUOTE ;


