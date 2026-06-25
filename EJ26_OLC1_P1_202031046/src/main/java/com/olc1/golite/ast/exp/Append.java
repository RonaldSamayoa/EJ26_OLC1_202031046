package com.olc1.golite.ast.exp;

public class Append extends Expresion {

    private Expresion slice;
    private Expresion valor;

    public Append(
            Expresion slice,
            Expresion valor) {

        this.slice = slice;
        this.valor = valor;
    }

    public Expresion getSlice() {
        return slice;
    }

    public Expresion getValor() {
        return valor;
    }
}