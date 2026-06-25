package com.olc1.golite.ast.exp;

public class SliceIndex extends Expresion {

    private Expresion slice;
    private Expresion valor;

    public SliceIndex(
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