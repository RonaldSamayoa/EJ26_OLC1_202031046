package com.olc1.golite.ast.stm;

import com.olc1.golite.ast.exp.Expresion;

public class AsignacionStruct extends Instruccion {

    private Expresion instancia;
    private String atributo;
    private Expresion valor;

    public AsignacionStruct(
            Expresion instancia,
            String atributo,
            Expresion valor) {

        this.instancia = instancia;
        this.atributo = atributo;
        this.valor = valor;
    }

    public Expresion getInstancia() {
        return instancia;
    }

    public String getAtributo() {
        return atributo;
    }

    public Expresion getValor() {
        return valor;
    }

}