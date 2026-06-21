package com.olc1.golite.parser;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.olc1.golite.ast.stm.Instruccion;
import com.olc1.golite.errors.ErrorCompilador;

import java_cup.runtime.Symbol;

public class AnalizadorSintactico {
    private List<Instruccion> ast;
    private List<ErrorCompilador> errores = new ArrayList<>();

    public List<ErrorCompilador> getErrores(){
        return errores;
    }

    public String analizar(String entrada){
        errores.clear();

        try{
            LexerCup lexer = new LexerCup(new StringReader(entrada));
            parser sintactico = new parser(lexer);
            Symbol resultado = sintactico.parse();

            ast = (List<Instruccion>) resultado.value;
    
            errores.addAll(sintactico.getErrores());
    
            if(errores.isEmpty()){
                return "Analisis sintactico correcto";
            }
    
            return "Analisis sintactico con errores";
    
        }catch(Exception e){
            errores.add(
                new ErrorCompilador(
                    "Sintactico",
                    e.getMessage(),
                    0,
                    0
                )
            );
            return "Error sintactico";
        }
    }

    public List<Instruccion> getAST() {
        return ast;
    }
}
