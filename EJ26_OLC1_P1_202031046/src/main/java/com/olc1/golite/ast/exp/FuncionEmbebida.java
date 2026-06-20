package com.olc1.golite.ast.exp;

public class FuncionEmbebida extends Expresion {

    private String nombre;
    private Expresion argumento;

    public FuncionEmbebida(
            String nombre,
            Expresion argumento) {

        this.nombre = nombre;
        this.argumento = argumento;
    }

    public String getNombre() {
        return nombre;
    }

    public Expresion getArgumento() {
        return argumento;
    }
}