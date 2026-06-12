package com.olc1.golite.ui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.olc1.golite.lexer.Token;

public class FrameReporteTokens extends JFrame {
    public FrameReporteTokens(List<Token> tokens){

        setTitle("Reporte de Tokens");
        setSize(800,600);
        setLocationRelativeTo(null);

        String[] columnas = {
            "Tipo",
            "Lexema",
            "Linea",
            "Columna"
        };

        DefaultTableModel modelo =
                new DefaultTableModel(columnas,0);

        for(Token token : tokens){

            modelo.addRow(new Object[]{
                token.getTipo(),
                token.getLexema(),
                token.getLinea(),
                token.getColumna()
            });
        }

        JTable tabla = new JTable(modelo);

        add(new JScrollPane(tabla));
    }
}
