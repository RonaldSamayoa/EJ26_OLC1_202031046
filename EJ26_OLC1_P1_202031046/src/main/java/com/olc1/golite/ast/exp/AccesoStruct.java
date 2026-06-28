package com.olc1.golite.ast.exp;

public class AccesoStruct extends Expresion {

    private Expresion instancia;
    private String atributo;

    public AccesoStruct(
            Expresion instancia,
            String atributo) {

        this.instancia = instancia;
        this.atributo = atributo;
    }

    public Expresion getInstancia() {
        return instancia;
    }

    public String getAtributo() {
        return atributo;
    }
}