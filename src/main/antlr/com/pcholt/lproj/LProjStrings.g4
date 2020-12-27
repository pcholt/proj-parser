grammar LProjStrings;
@header {
    package com.pcholt.lproj;
}

lProjStrings
    : lines EOF
    ;

lines
    : line +
    ;

line: variable '=' value ';' ;
variable:
    Letter ( Letter | Digit | '.') * ;
value: StringLiteral;

StringLiteral
    :   '"' SCharSequence '"'
    ;

Letter:
    [a-zA-Z]
    ;

Digit:
    [0-9];

fragment SCharSequence
    : (SChar | EscapeSequence ) *
    ;

fragment SChar
    :   ~ ["]
    |   '\\\n'   // Added line
    |   '\\\r\n' // Added line
    ;

EscapeSequence
    :   SimpleEscapeSequence
    ;

SimpleEscapeSequence
    :   '\\' ['"\\]
    ;


Whitespace
    :   [ \t]+
        -> skip
    ;

Newline
    :   (   '\r' '\n'?
        |   '\n'
        )
        -> skip
    ;

BlockComment
    :   '/*' .*? '*/'
        -> skip
    ;

LineComment
    :   '//' ~[\r\n]*
        -> skip
    ;
