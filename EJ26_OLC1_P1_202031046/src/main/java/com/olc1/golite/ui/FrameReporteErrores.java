package com.olc1.golite.ui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.olc1.golite.errors.ErrorCompilador;

public class FrameReporteErrores extends JFrame{
    public FrameReporteErrores(
            List<ErrorCompilador> errores){

        setTitle("Reporte de Errores");
        setSize(900,600);
        setLocationRelativeTo(null);

        String[] columnas = {
            "Tipo",
            "Descripcion",
            "Linea",
            "Columna"
        };

        DefaultTableModel modelo =
                new DefaultTableModel(columnas,0);

        for(ErrorCompilador error : errores){

            modelo.addRow(new Object[]{
                error.getTipo(),
                error.getDescripcion(),
                error.getLinea(),
                error.getColumna()
            });
        }

        JTable tabla = new JTable(modelo);

        add(new JScrollPane(tabla));
    }
}
