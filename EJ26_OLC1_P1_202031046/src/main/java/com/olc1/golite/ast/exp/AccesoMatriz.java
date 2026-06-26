package com.olc1.golite.ast.exp;

public class AccesoMatriz extends Expresion {

    private String identificador;
    private Expresion fila;
    private Expresion columna;

    public AccesoMatriz(
            String identificador,
            Expresion fila,
            Expresion columna) {

        this.identificador = identificador;
        this.fila = fila;
        this.columna = columna;
    }

    public String getIdentificador() {
        return identificador;
    }

    public Expresion getFila() {
        return fila;
    }

    public Expresion getColumna() {
        return columna;
    }
}