package com.olc1.golite.ast.stm;

import com.olc1.golite.ast.exp.Expresion;

public class Return extends Instruccion {

    private Expresion valor;

    public Return(Expresion valor) {
        this.valor = valor;
    }

    public Expresion getValor() {
        return valor;
    }
}