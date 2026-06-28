package com.olc1.golite.ast.exp;

public class CampoStruct {

    private String nombre;
    private Expresion valor;

    public CampoStruct(String nombre, Expresion valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public Expresion getValor() {
        return valor;
    }

}