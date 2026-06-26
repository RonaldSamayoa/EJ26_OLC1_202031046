package com.olc1.golite.ast.exp;

import java.util.List;

public class MatrizLiteral extends Expresion {

    private List<SliceLiteral> filas;

    public MatrizLiteral(
            List<SliceLiteral> filas) {

        this.filas = filas;
    }

    public List<SliceLiteral> getFilas() {
        return filas;
    }
}