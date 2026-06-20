package com.olc1.golite.ast.stm;
import java.util.List;

public class Bloque extends Instruccion {
    private List<Instruccion> instrucciones;

    public Bloque(List<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public List<Instruccion> getInstrucciones() {
        return instrucciones;
    }
}
