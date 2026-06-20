package com.olc1.golite.ast.stm;

import com.olc1.golite.ast.exp.Expresion;

public class If extends Instruccion {

    private Expresion condicion;
    private Instruccion bloqueThen;
    private Instruccion bloqueElse;

    public If(
            Expresion condicion,
            Instruccion bloqueThen,
            Instruccion bloqueElse) {

        this.condicion = condicion;
        this.bloqueThen = bloqueThen;
        this.bloqueElse = bloqueElse;
    }

    public Expresion getCondicion() {
        return condicion;
    }

    public Instruccion getBloqueThen() {
        return bloqueThen;
    }

    public Instruccion getBloqueElse() {
        return bloqueElse;
    }
}
