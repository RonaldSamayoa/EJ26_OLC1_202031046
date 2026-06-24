package com.olc1.golite.ast.exp;

import java.util.List;

public class SliceLiteral extends Expresion {

    private List<Expresion> elementos;

    public SliceLiteral(
            List<Expresion> elementos) {

        this.elementos = elementos;
    }

    public List<Expresion> getElementos() {
        return elementos;
    }
}