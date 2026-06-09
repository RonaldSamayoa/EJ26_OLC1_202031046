package com.olc1.golite.lexer;

%%

%public
%class Lexer
%unicode
%type Object

%%

[ \t\r\n]+    { }

.             { }