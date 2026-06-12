package com.olc1.golite.lexer;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.olc1.golite.errors.ErrorCompilador;

public class AnalizadorLexico {
    private List<ErrorCompilador> errores = new ArrayList<>();
    
    public List<ErrorCompilador> getErrores(){
        return errores;
    }

    public List<Token> analizar(String entrada){
        List<Token> tokens = new ArrayList<>();

        try {
            Lexer lexer = new Lexer(new StringReader(entrada));

            Token token;

            do { 
                token = lexer.yylex();
                if (token != null){
                    tokens.add(token);

                    if(token.getTipo() == TipoToken.ERROR){
                        errores.add(new ErrorCompilador("Lexico", 
                        "Caracter no reconocido: " + token.getLexema(),
                         token.getLinea(), token.getColumna()));
                    }
                }
            } while (token != null && token.getTipo() != TipoToken.EOF);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokens;
    }
}
