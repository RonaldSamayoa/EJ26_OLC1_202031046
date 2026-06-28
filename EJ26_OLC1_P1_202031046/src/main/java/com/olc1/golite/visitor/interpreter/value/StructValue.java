package com.olc1.golite.visitor.interpreter.value;

import java.util.HashMap;
import java.util.Map;

public class StructValue {

    private String tipo;
    private Map<String, ValueWrapper> atributos;

    public StructValue(String tipo) {

        this.tipo = tipo;
        this.atributos = new HashMap<>();
    }

    public String getTipo() {
        return tipo;
    }

    public Map<String, ValueWrapper> getAtributos() {
        return atributos;
    }

    public void setAtributo(String nombre, ValueWrapper valor) {
        atributos.put(nombre, valor);
    }

    public ValueWrapper getAtributo(String nombre) {
        return atributos.get(nombre);
    }
}