package com.olc1.golite.ast.exp;

public class AccesoSlice extends Expresion {

    private String identificador;
    private Expresion indice;

    public AccesoSlice(
            String identificador,
            Expresion indice) {

        this.identificador = identificador;
        this.indice = indice;
    }

    public String getIdentificador() {
        return identificador;
    }

    public Expresion getIndice() {
        return indice;
    }
}