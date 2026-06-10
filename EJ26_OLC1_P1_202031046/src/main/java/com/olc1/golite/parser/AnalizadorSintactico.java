package com.olc1.golite.parser;

import java.io.StringReader;

public class AnalizadorSintactico {
    public String analizar(String entrada){
        try {
            LexerCup lexer = new LexerCup(new StringReader(entrada));

            parser sintactico = new parser(lexer);

            sintactico.parse();
            return "Analisis sintactico correcto ";

        } catch (Exception e) {
            return "Error sintactico:\n" + e.getMessage();
        }
    }
}
