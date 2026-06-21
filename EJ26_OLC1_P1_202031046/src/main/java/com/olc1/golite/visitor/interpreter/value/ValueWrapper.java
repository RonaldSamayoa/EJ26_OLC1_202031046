package com.olc1.golite.visitor.interpreter.value;

public class ValueWrapper {

    private Object valor;

    public ValueWrapper(Object valor) {
        this.valor = valor;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
}