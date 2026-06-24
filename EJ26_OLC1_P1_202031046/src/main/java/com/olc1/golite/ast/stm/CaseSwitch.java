package com.olc1.golite.ast.stm;

import com.olc1.golite.ast.exp.Expresion;

public class CaseSwitch {

    private Expresion valor;
    private Bloque bloque;

    public CaseSwitch(
            Expresion valor,
            Bloque bloque) {

        this.valor = valor;
        this.bloque = bloque;
    }

    public Expresion getValor() {
        return valor;
    }

    public Bloque getBloque() {
        return bloque;
    }
}