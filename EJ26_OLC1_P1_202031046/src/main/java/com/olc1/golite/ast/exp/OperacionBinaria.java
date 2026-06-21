package com.olc1.golite.ast.exp;

public class OperacionBinaria extends Expresion {
    private Expresion izquierda;
    private String operador;
    private Expresion derecha;

    public OperacionBinaria(
            Expresion izquierda,
            String operador,
            Expresion derecha){

        this.izquierda = izquierda;
        this.operador = operador;
        this.derecha = derecha;
    }

    public Expresion getIzquierda() {
        return izquierda;
    }
    
    public String getOperador() {
        return operador;
    }
    
    public Expresion getDerecha() {
        return derecha;
    }
}
