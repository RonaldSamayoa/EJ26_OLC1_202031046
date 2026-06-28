package com.olc1.golite.ast.stm;

import java.util.List;

public class FuncionStruct extends Instruccion {

    private String tipoStruct;
    private String referencia;
    private String nombre;

    private List<Parametro> parametros;
    private String tipoRetorno;
    private Bloque bloque;

    public FuncionStruct(
            String tipoStruct,
            String referencia,
            String nombre,
            List<Parametro> parametros,
            String tipoRetorno,
            Bloque bloque) {

        this.tipoStruct = tipoStruct;
        this.referencia = referencia;
        this.nombre = nombre;
        this.parametros = parametros;
        this.tipoRetorno = tipoRetorno;
        this.bloque = bloque;
    }

    public String getTipoStruct() {
        return tipoStruct;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Parametro> getParametros() {
        return parametros;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public Bloque getBloque() {
        return bloque;
    }
}