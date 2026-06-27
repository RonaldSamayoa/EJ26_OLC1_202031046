package com.olc1.golite.ast.stm;

import java.util.List;

public class Struct extends Instruccion {

    private String nombre;
    private List<AtributoStruct> atributos;

    public Struct(String nombre, List<AtributoStruct> atributos) {
        this.nombre = nombre;
        this.atributos = atributos;
    }

    public String getNombre() {
        return nombre;
    }

    public List<AtributoStruct> getAtributos() {
        return atributos;
    }
}