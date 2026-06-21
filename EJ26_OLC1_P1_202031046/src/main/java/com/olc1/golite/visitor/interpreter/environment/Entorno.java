package com.olc1.golite.visitor.interpreter.environment;

import java.util.HashMap;
import java.util.Map;

import com.olc1.golite.visitor.interpreter.value.ValueWrapper;

public class Entorno {

    private final Map<String, ValueWrapper> variables;
    private final Entorno anterior;

    public Entorno() {
        this.variables = new HashMap<>();
        this.anterior = null;
    }

    public Entorno(Entorno anterior) {
        this.variables = new HashMap<>();
        this.anterior = anterior;
    }

    public void declarar(String id, ValueWrapper valor) {
        variables.put(id, valor);
    }

    public boolean existeLocal(String id) {
        return variables.containsKey(id);
    }

    public boolean existe(String id) {

        if (variables.containsKey(id)) {
            return true;
        }

        if (anterior != null) {
            return anterior.existe(id);
        }

        return false;
    }

    public ValueWrapper obtener(String id) {

        if (variables.containsKey(id)) {
            return variables.get(id);
        }

        if (anterior != null) {
            return anterior.obtener(id);
        }

        return null;
    }

    public void asignar(String id, ValueWrapper valor) {

        if (variables.containsKey(id)) {
            variables.put(id, valor);
            return;
        }

        if (anterior != null) {
            anterior.asignar(id, valor);
            return;
        }

        throw new RuntimeException(
            "Variable no definida: " + id
        );
    }
}