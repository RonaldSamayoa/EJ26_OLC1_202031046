package com.olc1.golite.lexer;

public enum TipoToken {
    //palabras reservadas
    VAR,
    INT,
    FLOAT64, 
    STRING,
    BOOL,
    NIL,
    RUNE,

    IF,
    ELSE,

    BREAK, 
    CONTINUE,

    TRUE,
    FALSE, 

    //funciones embebidas
    FMT, 
    PRINTLN,
    STRCONV, 
    ATOI,
    PARSEFLOAT,
    REFLECT,
    TYPEOF,
    STRING_FUNC,

    //identificadores 
    IDENTIFICADOR,

    //literales
    ENTERO, 
    DECIMAL,
    CADENA,
    LITERAL_RUNE,

    //operadores aritmeticos
    MAS,
    MENOS,
    MULTIPLICACION,
    DIVISION,
    MODULO,

    //operadores relacionales
    MAYOR,
    MENOR,
    MAYOR_IGUAL,
    MENOR_IGUAL,
    IGUAL_IGUAL,
    DIFERENTE,

    //operadores logicos
    AND,
    OR,
    NOT,

    //asignacion
    IGUAL,

    //puntuacion
    PUNTO,
    COMA,
    PUNTO_COMA,
    DOS_PUNTOS,

    PARENTESIS_IZQ,
    PARENTESIS_DER,

    LLAVE_IZQ,
    LLAVE_DER,

    //ciclo for
    FOR,

    //asignacion compuesta
    MAS_IGUAL,
    MENOS_IGUAL,

    //incremento o decremento
    INCREMENTO,
    DECREMENTO,

    //especiales
    EOF,
    ERROR 
}
