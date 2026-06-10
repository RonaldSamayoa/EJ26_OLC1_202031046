package com.olc1.golite.ui;
import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import com.olc1.golite.lexer.AnalizadorLexico;
import com.olc1.golite.lexer.Token;

public class MainFrame extends JFrame {

    private JTabbedPane tabbedEditor;

    private EditorPanel editor;

    private ConsolePanel consola;

    public MainFrame() {
        initComponents();
    }

    private void initComponents() {

        setTitle("GoLite!!!");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar barra = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        JMenu menuEjecutar = new JMenu("Ejecutar");
        JMenu menuReportes = new JMenu("Reportes");

        JMenuItem itemNuevo = new JMenuItem("Nuevo");
        JMenuItem itemProbar = new JMenuItem("Probar");

        menuArchivo.add(itemNuevo);
        menuEjecutar.add(itemProbar);

        barra.add(menuArchivo);
        barra.add(menuEjecutar);
        barra.add(menuReportes);

        setJMenuBar(barra);

        //editor
        editor = new EditorPanel();

        tabbedEditor = new JTabbedPane();

        tabbedEditor.addTab("Archivo1.go", editor);

        //prueba consola
        consola = new ConsolePanel();

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,tabbedEditor, consola);

        split.setDividerLocation(550);

        add(split, BorderLayout.CENTER);

        itemProbar.addActionListener(e -> {
            consola.clear();
            String codigo = editor.getTexto();
            
            AnalizadorLexico analizador = new AnalizadorLexico();

            List<Token> tokens = analizador.analizar(codigo);

            for (Token token :tokens){
                consola.append(token.toString());
            }
        });
    }

}
