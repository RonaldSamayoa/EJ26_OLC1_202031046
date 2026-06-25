package com.olc1.golite.ast.exp;

public class StringsJoin extends Expresion {

    private Expresion slice;
    private Expresion separador;

    public StringsJoin(
            Expresion slice,
            Expresion separador) {

        this.slice = slice;
        this.separador = separador;
    }

    public Expresion getSlice() {
        return slice;
    }

    public Expresion getSeparador() {
        return separador;
    }
}