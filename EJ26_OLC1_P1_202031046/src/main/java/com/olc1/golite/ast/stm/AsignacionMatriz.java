package com.olc1.golite.ast.stm;

import com.olc1.golite.ast.exp.Expresion;

public class AsignacionMatriz extends Instruccion {

    private String identificador;
    private Expresion fila;
    private Expresion columna;
    private Expresion valor;

    public AsignacionMatriz(
            String identificador,
            Expresion fila,
            Expresion columna,
            Expresion valor) {

        this.identificador = identificador;
        this.fila = fila;
        this.columna = columna;
        this.valor = valor;
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

    public Expresion getValor() {
        return valor;
    }
}