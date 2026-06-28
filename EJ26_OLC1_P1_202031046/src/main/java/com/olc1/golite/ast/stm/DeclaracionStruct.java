package com.olc1.golite.ast.stm;

import com.olc1.golite.ast.exp.InstanciaStruct;

public class DeclaracionStruct extends Instruccion {

    private String tipoStruct;
    private String identificador;
    private InstanciaStruct instancia;

    public DeclaracionStruct(
            String tipoStruct,
            String identificador,
            InstanciaStruct instancia) {

        this.tipoStruct = tipoStruct;
        this.identificador = identificador;
        this.instancia = instancia;
    }

    public String getTipoStruct() {
        return tipoStruct;
    }

    public String getIdentificador() {
        return identificador;
    }

    public InstanciaStruct getInstancia() {
        return instancia;
    }
}