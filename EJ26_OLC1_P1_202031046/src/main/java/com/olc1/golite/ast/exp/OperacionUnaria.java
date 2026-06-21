package com.olc1.golite.ast.exp;

public class OperacionUnaria extends Expresion {

    private String operador;
    private Expresion expresion;

    public OperacionUnaria(
            String operador,
            Expresion expresion){

        this.operador = operador;
        this.expresion = expresion;
    }

    public String getOperador() {
        return operador;
    }
    
    public Expresion getExpresion() {
        return expresion;
    }
}
