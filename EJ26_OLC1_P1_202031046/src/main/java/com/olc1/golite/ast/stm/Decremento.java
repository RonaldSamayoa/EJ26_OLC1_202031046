package com.olc1.golite.ast.stm;

public class Decremento extends Instruccion {

    private String identificador;

    public Decremento(String identificador) {
        this.identificador = identificador;
    }

    public String getIdentificador() {
        return identificador;
    }
}