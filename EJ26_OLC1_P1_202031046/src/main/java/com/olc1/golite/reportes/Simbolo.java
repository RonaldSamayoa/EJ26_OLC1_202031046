package com.olc1.golite.reportes;

public class Simbolo {

    private String identificador;
    private String tipoSimbolo;
    private String tipoDato;
    private String ambito;

    public Simbolo(
            String identificador,
            String tipoSimbolo,
            String tipoDato,
            String ambito) {

        this.identificador = identificador;
        this.tipoSimbolo = tipoSimbolo;
        this.tipoDato = tipoDato;
        this.ambito = ambito;
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getTipoSimbolo() {
        return tipoSimbolo;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public String getAmbito() {
        return ambito;
    }

}