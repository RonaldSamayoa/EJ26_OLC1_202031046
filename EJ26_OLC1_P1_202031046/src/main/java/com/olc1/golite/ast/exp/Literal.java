package com.olc1.golite.ast.exp;

public class Literal extends Expresion {
    private Object valor;

    public Literal(Object valor) {
        this.valor = valor;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
}
