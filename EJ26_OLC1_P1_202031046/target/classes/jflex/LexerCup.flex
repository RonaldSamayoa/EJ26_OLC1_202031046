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
DECIMAL = {DIGITO}+"."{DIGITO}+

%%
[ \t\r\n]+    { }


/* COMENTARIOS */
// una linea
"//".* { }

// multilinea
"/*"([^*]|\*+[^*/])*\*+"/" { }

"var"       {return symbol(sym.VAR);}
"if"       {return symbol(sym.IF);}
"else"       {return symbol(sym.ELSE);}
"for"       {return symbol(sym.FOR);}

"true"       {return symbol(sym.TRUE);}
"false"       {return symbol(sym.FALSE);}

"&&"       {return symbol(sym.AND);}
"||"       {return symbol(sym.OR);}
"!"       {return symbol(sym.NOT);}

">="       {return symbol(sym.MAYOR_IGUAL);}
"<="       {return symbol(sym.MENOR_IGUAL);}
"=="       {return symbol(sym.IGUAL_IGUAL);}
"!="       {return symbol(sym.DIFERENTE);}

">"       {return symbol(sym.MAYOR);}
"<"       {return symbol(sym.MENOR);}

"="       {return symbol(sym.IGUAL);}

"+"       {return symbol(sym.MAS);}
"-"       {return symbol(sym.MENOS);}
"*"       {return symbol(sym.MULTIPLICACION);}
"/"       {return symbol(sym.DIVISION);}

";"       {return symbol(sym.PUNTO_COMA);}
"."       {return symbol(sym.PUNTO);}

"("       {return symbol(sym.PARENTESIS_IZQ);}
")"       {return symbol(sym.PARENTESIS_DER);}
"{"       {return symbol(sym.LLAVE_IZQ);}
"}"       {return symbol(sym.LLAVE_DER);}

"fmt"       {return symbol(sym.FMT);}
"Println"       {return symbol(sym.PRINTLN);}

{DECIMAL}              { return symbol(sym.DECIMAL,yytext());}
{ENTERO}              { return symbol(sym.ENTERO,yytext());}

\"([^\"\\]|\\.)*\"    { return symbol(sym.CADENA, yytext()); }

{IDENTIFICADOR}       { return symbol(sym.IDENTIFICADOR, yytext()); }

<<EOF>>               { return symbol(sym.EOF);}   

.                     { }
