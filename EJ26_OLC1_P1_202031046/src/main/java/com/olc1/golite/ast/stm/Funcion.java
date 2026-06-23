package com.olc1.golite.ast.stm;

import java.util.List;

public class Funcion extends Instruccion {

    private String nombre;
    private List<Parametro> parametros;
    private String tipoRetorno;
    private Bloque bloque;
    
    public Funcion(
        String nombre,
        List<Parametro> parametros,
        String tipoRetorno,
        Bloque bloque) {

        this.nombre = nombre;
        this.parametros = parametros;
        this.tipoRetorno = tipoRetorno;
        this.bloque = bloque;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Parametro> getParametros() {
        return parametros;
    }

    public Bloque getBloque() {
        return bloque;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }
}