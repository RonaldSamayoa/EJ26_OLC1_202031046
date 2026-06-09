package com.olc1.golite.errors;

//representar un error lexico, sintactico o semantico
public class ErrorCompilador {
    private String tipo;
    private String descripcion;
    private int linea;
    private int columna;

    public ErrorCompilador(String tipo, String descripcion, int linea, int columna){
        this.tipo=tipo;
        this.descripcion=descripcion;
        this.linea=linea;
        this.columna=columna;
    }

    public String getTipo(){
        return tipo;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public int getlinea(){
        return linea;
    }

    public int getColumna(){
        return columna;
    }

    @Override
    public String toString() {
        return "["+tipo + "]" + descripcion + "(linea: " + linea 
        + ", columna: " + columna + ")";
    }
}
