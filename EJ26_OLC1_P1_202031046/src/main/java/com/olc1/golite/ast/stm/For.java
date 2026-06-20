package com.olc1.golite.ast.stm;

import com.olc1.golite.ast.exp.Expresion;

public class For extends Instruccion {

    private Instruccion inicializacion;
    private Expresion condicion;
    private Instruccion actualizacion;
    private Bloque bloque;

    public For(
            Instruccion inicializacion,
            Expresion condicion,
            Instruccion actualizacion,
            Bloque bloque) {

        this.inicializacion = inicializacion;
        this.condicion = condicion;
        this.actualizacion = actualizacion;
        this.bloque = bloque;
    }

    public Instruccion getInicializacion() {
        return inicializacion;
    }

    public Expresion getCondicion() {
        return condicion;
    }

    public Instruccion getActualizacion() {
        return actualizacion;
    }

    public Bloque getBloque() {
        return bloque;
    }
}