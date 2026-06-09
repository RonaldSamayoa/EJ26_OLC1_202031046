package com.olc1.golite.lexer;

public class Token {
    private TipoToken tipo;
    private String lexema;
    private int linea ;
    private int columna;

    public Token(TipoToken tipo, String lexema, int linea, int columna){
        this.tipo = tipo;
        this.lexema = lexema;
        this.linea = linea;
        this.columna = columna;
    }

    public TipoToken getTipo(){
        return tipo;
    }

    public String getLexema(){
        return lexema;
    }

    public int getLinea(){
        return linea;
    }

    public int getColumna(){
        return columna;
    }

    @Override
    public String toString() {
        return tipo + "->" + lexema + " [" + linea 
        + ", " + columna + "]";
    }
}
