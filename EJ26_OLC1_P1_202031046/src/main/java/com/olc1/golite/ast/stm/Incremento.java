package com.olc1.golite.ast.stm;

public class Incremento extends Instruccion {

    private String identificador;

    public Incremento(String identificador) {
        this.identificador = identificador;
    }

    public String getIdentificador() {
        return identificador;
    }
}