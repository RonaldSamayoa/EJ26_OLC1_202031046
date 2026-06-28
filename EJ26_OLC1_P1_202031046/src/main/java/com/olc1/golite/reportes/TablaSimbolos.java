package com.olc1.golite.reportes;

import java.util.ArrayList;
import java.util.List;

public class TablaSimbolos {

    private static final List<Simbolo> simbolos =
            new ArrayList<>();

    public static void limpiar() {
        simbolos.clear();
    }

    public static void agregar(Simbolo simbolo) {
        simbolos.add(simbolo);
    }

    public static List<Simbolo> getSimbolos() {
        return simbolos;
    }

}