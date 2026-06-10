package com.olc1.golite.parser;
import java_cup.runtime.Symbol;

%%

%public
%class LexerCup
%unicode
%cup
%line
%column


%{
    private Symbol symbol (int type){
        return new Symbol(type, yyline + 1, yycolumn + 1);
    }    

    private Symbol symbol (int type, Object value){
        return new Symbol(type, yyline + 1, yycolumn + 1, value);
    }
%}

LETRA = [a-zA-Z]
DIGITO = [0-9]

IDENTIFICADOR = {LETRA}({LETRA}|{DIGITO}|_)*
ENTERO = {DIGITO}+

%%
[ \t\r\n]+    { }

"var"       {return symbol(sym.VAR);}
"="       {return symbol(sym.IGUAL);}
";"       {return symbol(sym.PUNTO_COMA);}
"."       {return symbol(sym.PUNTO);}
"("       {return symbol(sym.PARENTESIS_IZQ);}
")"       {return symbol(sym.PARENTESIS_DER);}
"fmt"       {return symbol(sym.FMT);}
"Println"       {return symbol(sym.PRINTLN);}

{ENTERO}              { return symbol(sym.ENTERO,yytext());}

\"([^\"\\]|\\.)*\"    { return symbol(sym.CADENA, yytext()); }

{IDENTIFICADOR}       { return symbol(sym.IDENTIFICADOR, yytext()); }

<<EOF>>               { return symbol(sym.EOF);}   

.                     { }
