package com.olc1.golite.ast.stm;

import java.util.List;

import com.olc1.golite.ast.exp.Expresion;

public class Switch extends Instruccion {

    private Expresion expresion;

    private List<CaseSwitch> casos;

    private Bloque bloqueDefault;

    public Switch(
            Expresion expresion,
            List<CaseSwitch> casos,
            Bloque bloqueDefault) {

        this.expresion = expresion;
        this.casos = casos;
        this.bloqueDefault = bloqueDefault;
    }

    public Expresion getExpresion() {
        return expresion;
    }

    public List<CaseSwitch> getCasos() {
        return casos;
    }

    public Bloque getBloqueDefault() {
        return bloqueDefault;
    }
}