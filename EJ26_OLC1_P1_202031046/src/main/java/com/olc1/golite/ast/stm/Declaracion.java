package com.olc1.golite.ast.stm;

import com.olc1.golite.ast.exp.Expresion;

public class Declaracion extends  Instruccion {
    private String identificador;
    private Expresion valor;

    public Declaracion(String identificador, Expresion valor) {
        this.identificador = identificador;
        this.valor = valor;
    }

    public String getIdentificador() {
        return identificador;
    }

    public Expresion getValor() {
        return valor;
    }
}
