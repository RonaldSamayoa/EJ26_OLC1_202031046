package com.olc1.golite.ast.stm;

public class AtributoStruct {

    private String nombre;
    private String tipo;

    public AtributoStruct(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }
}