package com.olc1.golite.visitor.interpreter;

public class ReturnException extends RuntimeException {

    private Object valor;

    public ReturnException(Object valor) {
        this.valor = valor;
    }

    public Object getValor() {
        return valor;
    }
}