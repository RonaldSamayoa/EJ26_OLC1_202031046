package com.olc1.golite.ast.stm;

import java.util.List;

import com.olc1.golite.ast.exp.Expresion;

public class Print extends Instruccion {
    private List<Expresion> expresiones;

    public Print(List<Expresion> expresiones) {
        this.expresiones = expresiones;
    }

    public List<Expresion> getExpresiones() {
        return expresiones;
    }
}
