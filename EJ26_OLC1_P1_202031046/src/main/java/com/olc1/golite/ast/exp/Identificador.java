package com.olc1.golite.ast.exp;

public class Identificador extends Expresion {
    private String nombre;

    public Identificador(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }
}
