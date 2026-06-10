package com.olc1.golite.lexer;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class AnalizadorLexico {
    public List<Token> analizar(String entrada){
        List<Token> tokens = new ArrayList<>();

        try {
            Lexer lexer = new Lexer(new StringReader(entrada));

            Token token;

            do { 
                token = lexer.yylex();
                if (token != null){
                    tokens.add(token);
                }
            } while (token != null && token.getTipo() != TipoToken.EOF);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokens;
    }
}
