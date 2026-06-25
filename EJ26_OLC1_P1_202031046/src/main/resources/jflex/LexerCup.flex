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

"switch"      { return symbol(sym.SWITCH); }
"case"        { return symbol(sym.CASE); }
"default"     { return symbol(sym.DEFAULT); }

"break"      { return symbol(sym.BREAK); }
"continue"   { return symbol(sym.CONTINUE); }
"return"     { return symbol(sym.RETURN); }
"func"       { return symbol(sym.FUNC); }

"true"       {return symbol(sym.TRUE);}
"false"       {return symbol(sym.FALSE);}

"int"       {return symbol(sym.INT);}
"float64"       {return symbol(sym.FLOAT64);}
"string"       {return symbol(sym.STRING);}
"bool"       {return symbol(sym.BOOL);}
"rune"          {return symbol(sym.RUNE); }

"len"       { return symbol(sym.LEN); }
"append"    { return symbol(sym.APPEND);}

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

"+="       {return symbol(sym.MAS_IGUAL);}
"-="       {return symbol(sym.MENOS_IGUAL);}
"++"       {return symbol(sym.INCREMENTO);}
"--"       {return symbol(sym.DECREMENTO);}

"nil"       {return symbol(sym.NIL);}

"+"       {return symbol(sym.MAS);}
"-"       {return symbol(sym.MENOS);}
"*"       {return symbol(sym.MULTIPLICACION);}
"/"       {return symbol(sym.DIVISION);}
"%"       {return symbol(sym.MODULO);}

";"       {return symbol(sym.PUNTO_COMA);}
":"       { return symbol(sym.DOS_PUNTOS); }
"."       {return symbol(sym.PUNTO);}
","       {return symbol(sym.COMA);}

"("       {return symbol(sym.PARENTESIS_IZQ);}
")"       {return symbol(sym.PARENTESIS_DER);}
"{"       {return symbol(sym.LLAVE_IZQ);}
"}"       {return symbol(sym.LLAVE_DER);}
"["     { return symbol(sym.CORCHETE_IZQ); }
"]"     { return symbol(sym.CORCHETE_DER); }

"fmt"       {return symbol(sym.FMT);}
"Println"       {return symbol(sym.PRINTLN);}
"strconv"       {return symbol(sym.STRCONV);}
"Atoi"       {return symbol(sym.ATOI);}
"ParseFloat"       {return symbol(sym.PARSEFLOAT);}
"reflect"       {return symbol(sym.REFLECT);}
"TypeOf"       {return symbol(sym.TYPEOF);}

{DECIMAL}              { return symbol(sym.DECIMAL,yytext());}
{ENTERO}              { return symbol(sym.ENTERO,yytext());}

\"([^\"\\]|\\.)*\"    { return symbol(sym.CADENA, yytext()); }

\'([^\'\\]|\\.)\'    { return symbol(sym.LITERAL_RUNE, yytext()); }

{IDENTIFICADOR}       { return symbol(sym.IDENTIFICADOR, yytext()); }

<<EOF>>               { return symbol(sym.EOF);}   

.                     { }
