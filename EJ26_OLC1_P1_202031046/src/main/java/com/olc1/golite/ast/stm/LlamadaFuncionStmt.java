package com.olc1.golite.ast.stm;

import com.olc1.golite.ast.exp.LlamadaFuncion;

public class LlamadaFuncionStmt extends Instruccion {

    private LlamadaFuncion llamada;

    public LlamadaFuncionStmt(
            LlamadaFuncion llamada) {

        this.llamada = llamada;
    }

    public LlamadaFuncion getLlamada() {
        return llamada;
    }
}