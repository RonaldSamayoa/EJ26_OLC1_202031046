package com.olc1.golite.ast.exp;

import java.util.List;

public class LlamadaFuncionStruct extends Expresion {

    private Expresion instancia;

    private String nombre;

    private List<Expresion> argumentos;

    public LlamadaFuncionStruct(
            Expresion instancia,
            String nombre,
            List<Expresion> argumentos) {

        this.instancia = instancia;
        this.nombre = nombre;
        this.argumentos = argumentos;
    }

    public Expresion getInstancia() {
        return instancia;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Expresion> getArgumentos() {
        return argumentos;
    }
}