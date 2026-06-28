package com.olc1.golite.ui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.olc1.golite.reportes.Simbolo;

public class FrameReporteSimbolos extends JFrame{

    public FrameReporteSimbolos(List<Simbolo> simbolos){

        setTitle("Reporte Tabla de Simbolos");
        setSize(800,600);
        setLocationRelativeTo(null);

        String[] columnas={
            "ID",
            "Tipo Simbolo",
            "Tipo Dato",
            "Ambito"
        };

        DefaultTableModel modelo=
                new DefaultTableModel(columnas,0);

        for(Simbolo s: simbolos){

            modelo.addRow(new Object[]{
                s.getIdentificador(),
                s.getTipoSimbolo(),
                s.getTipoDato(),
                s.getAmbito()
            });

        }

        JTable tabla=new JTable(modelo);

        add(new JScrollPane(tabla));

    }

}