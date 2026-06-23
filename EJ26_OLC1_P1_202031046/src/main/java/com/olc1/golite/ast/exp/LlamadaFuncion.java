package com.olc1.golite.ast.exp;

import java.util.List;

public class LlamadaFuncion extends Expresion {

    private String nombre;
    private List<Expresion> argumentos;

    public LlamadaFuncion(
            String nombre,
            List<Expresion> argumentos) {

        this.nombre = nombre;
        this.argumentos = argumentos;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Expresion> getArgumentos() {
        return argumentos;
    }
}