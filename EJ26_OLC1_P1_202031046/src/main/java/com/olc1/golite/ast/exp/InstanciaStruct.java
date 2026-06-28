package com.olc1.golite.ast.exp;

import java.util.List;

public class InstanciaStruct extends Expresion{

    private String nombreStruct;
    private List<CampoStruct> campos;

    public InstanciaStruct(
            String nombreStruct,
            List<CampoStruct> campos){

        this.nombreStruct = nombreStruct;
        this.campos = campos;
    }

    public String getNombreStruct() {
        return nombreStruct;
    }

    public List<CampoStruct> getCampos() {
        return campos;
    }

}