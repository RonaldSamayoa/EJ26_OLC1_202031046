package com.olc1.golite.ast.exp;

public class Len extends Expresion {

    private Expresion expresion;

    public Len(
            Expresion expresion) {

        this.expresion = expresion;
    }

    public Expresion getExpresion() {
        return expresion;
    }
}