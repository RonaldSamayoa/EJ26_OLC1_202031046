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

/* COMENTARIOS*/
//comentario de una linea
"//".*      { }

//comentario multilinea
"/*"([^*]|\*+[^*/])*\*+"/"   { }


/*PALABRAS RESERVADAS*/

"var"         {return token(TipoToken.VAR, yytext()); }

"int"                 { return token(TipoToken.INT, yytext()); }
"float64"             { return token(TipoToken.FLOAT64, yytext()); }
"string"              { return token(TipoToken.STRING, yytext()); }
"bool"                { return token(TipoToken.BOOL, yytext()); }
"rune"          {return token(TipoToken.RUNE, yytext()); }            

"if"          {return token(TipoToken.IF, yytext()); }            
"else"        {return token(TipoToken.ELSE, yytext()); }

"for"                 { return token(TipoToken.FOR, yytext()); }

"break"               { return token(TipoToken.BREAK, yytext()); }
"continue"            { return token(TipoToken.CONTINUE, yytext()); }
"return"              { return token(TipoToken.RETURN, yytext()); }
"func"                { return token(TipoToken.FUNC, yytext()); }

"true"                { return token(TipoToken.TRUE, yytext()); }
"false"               { return token(TipoToken.FALSE, yytext()); }

"nil"                 { return token(TipoToken.NIL, yytext()); }

/*FUNCIONES*/
"fmt"                 { return token(TipoToken.FMT, yytext()); }
"Println"             { return token(TipoToken.PRINTLN, yytext()); }

"strconv"             { return token(TipoToken.STRCONV, yytext()); }
"Atoi"                { return token(TipoToken.ATOI, yytext()); }
"ParseFloat"          { return token(TipoToken.PARSEFLOAT, yytext()); }

"reflect"             { return token(TipoToken.REFLECT, yytext()); }
"TypeOf"              { return token(TipoToken.TYPEOF, yytext()); }

"switch"              { return token(TipoToken.SWITCH, yytext()); }
"case"                { return token(TipoToken.CASE, yytext()); }
"default"             { return token(TipoToken.DEFAULT, yytext()); }

/*OPERADORES COMPUESTOS*/
"++"                   {return token(TipoToken.INCREMENTO, yytext());}
"--"                   {return token(TipoToken.DECREMENTO, yytext());}
"+="                   {return token(TipoToken.MAS_IGUAL, yytext());}
"-="                   {return token(TipoToken.MENOS_IGUAL, yytext());}

/*OPERADORES RELACIONALES*/
">="                   {return token(TipoToken.MAYOR_IGUAL, yytext());}
"<="                   {return token(TipoToken.MENOR_IGUAL, yytext());}
"=="                   {return token(TipoToken.IGUAL_IGUAL, yytext());}
"!="                   {return token(TipoToken.DIFERENTE, yytext());}
">"                   {return token(TipoToken.MAYOR, yytext());}
"<"                   {return token(TipoToken.MENOR, yytext());}

/*OPERADORES LOGICOS*/
"&&"                   {return token(TipoToken.AND, yytext());}
"||"                   {return token(TipoToken.OR, yytext());}
"!"                   {return token(TipoToken.NOT, yytext());}


/*LITERALES*/
{DECIMAL}             { return token(TipoToken.DECIMAL, yytext()); }
{ENTERO}              { return token(TipoToken.ENTERO, yytext()); }

\"([^\"\\]|\\.)*\"    { return token(TipoToken.CADENA, yytext()); }

\'([^\'\\]|\\.)\'    { return token(TipoToken.LITERAL_RUNE, yytext()); }

/*OPERADORES ARITMETICOS */
"+"                   { return token(TipoToken.MAS, yytext()); }
"-"                   { return token(TipoToken.MENOS, yytext()); }
"*"                   { return token(TipoToken.MULTIPLICACION, yytext()); }
"/"                   { return token(TipoToken.DIVISION, yytext()); }
"%"                   { return token(TipoToken.MODULO, yytext()); }

/*ASIGNACION*/
"="                   { return token(TipoToken.IGUAL, yytext()); }

/*PUNTUACION*/
"."                   { return token(TipoToken.PUNTO, yytext()); }
","                   { return token(TipoToken.COMA, yytext()); }
":"                   { return token(TipoToken.DOS_PUNTOS, yytext()); }
";"                   { return token(TipoToken.PUNTO_COMA, yytext()); }

/*AGRUPACION*/
"("                   { return token(TipoToken.PARENTESIS_IZQ, yytext()); }
")"                   { return token(TipoToken.PARENTESIS_DER, yytext()); }

"{"                   { return token(TipoToken.LLAVE_IZQ, yytext()); }
"}"                   { return token(TipoToken.LLAVE_DER, yytext()); }

{IDENTIFICADOR}       { return token(TipoToken.IDENTIFICADOR, yytext()); }

<<EOF>>               { return token(TipoToken.EOF, "EOF"); }   

.                     { return token(TipoToken.ERROR, yytext()); }
