package com.olc1.golite.lexer;

%%

%public
%class Lexer
%unicode
%line
%column
%type Token

%{
    private Token token (TipoToken tipo, String lexema){
        return new Token(tipo, lexema, yyline + 1, yycolumn + 1);
    }    
%}

LETRA = [a-zA-Z]
DIGITO = [0-9]

IDENTIFICADOR = {LETRA}({LETRA}|{DIGITO}|_)*
ENTERO = {DIGITO}+
DECIMAL = {DIGITO}+"."{DIGITO}+

%%

[ \t\r\n]+    { }

"var"         {return token(TipoToken.VAR, yytext()); }

"if"          {return token(TipoToken.IF, yytext()); }            
"else"        {return token(TipoToken.ELSE, yytext()); }

"for"                 { return token(TipoToken.FOR, yytext()); }

"break"               { return token(TipoToken.BREAK, yytext()); }
"continue"            { return token(TipoToken.CONTINUE, yytext()); }

"true"                { return token(TipoToken.TRUE, yytext()); }
"false"               { return token(TipoToken.FALSE, yytext()); }

"nil"                 { return token(TipoToken.NIL, yytext()); }

{DECIMAL}             { return token(TipoToken.DECIMAL, yytext()); }
{ENTERO}              { return token(TipoToken.ENTERO, yytext()); }

\"([^\"\\]|\\.)*\"    { return token(TipoToken.CADENA, yytext()); }

"+"                   { return token(TipoToken.MAS, yytext()); }
"-"                   { return token(TipoToken.MENOS, yytext()); }
"*"                   { return token(TipoToken.MULTIPLICACION, yytext()); }
"/"                   { return token(TipoToken.DIVISION, yytext()); }

"="                   { return token(TipoToken.IGUAL, yytext()); }

";"                   { return token(TipoToken.PUNTO_COMA, yytext()); }

"("                   { return token(TipoToken.PARENTESIS_IZQ, yytext()); }
")"                   { return token(TipoToken.PARENTESIS_DER, yytext()); }

"{"                   { return token(TipoToken.LLAVE_IZQ, yytext()); }
"}"                   { return token(TipoToken.LLAVE_DER, yytext()); }

{IDENTIFICADOR}       { return token(TipoToken.IDENTIFICADOR, yytext()); }

<<EOF>>               { return token(TipoToken.EOF, "EOF"); }   

.                     { return token(TipoToken.ERROR, yytext()); }