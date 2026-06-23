package com.olc1.golite.ui;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.olc1.golite.ast.stm.Instruccion;
import com.olc1.golite.errors.ErrorCompilador;
import com.olc1.golite.lexer.AnalizadorLexico;
import com.olc1.golite.lexer.Token;
import com.olc1.golite.parser.AnalizadorSintactico;
import com.olc1.golite.visitor.interpreter.InterpreterVisitor;

public class MainFrame extends JFrame {
    private List<Token> ultimoReporteTokens;
    private List<ErrorCompilador> ultimoReporteErrores;
    private JTabbedPane tabbedEditor;
    private ConsolePanel consola;
    private int contadorArchivos = 1;
    private JLabel lblEstado;

    public MainFrame() {
        initComponents();
    }

    private EditorPanel getEditorActual(){
        return (EditorPanel) tabbedEditor.getSelectedComponent();
    }

    private void guardarEditor(EditorPanel editor, String nombreArchivo){
        try {
            FileWriter writer = new FileWriter(nombreArchivo);

            writer.write(editor.getTexto());
            writer.close();
            editor.setModificado(false);

        } catch (Exception ex) {
            consola.append("Error al guardar: " + ex.getMessage());
        }
    }

    private void actualizarEstado(){
        if (lblEstado == null){
            return;
        }

        EditorPanel editor = getEditorActual();

        if (editor!=null){
            lblEstado.setText("Linea:  " + editor.getLineaActual()
            + "  Columna:  " + editor.getColumnaActual());
        }
    }

    private boolean hayCambiosSinGuardar(){

        for(int i = 0; i < tabbedEditor.getTabCount(); i++){
            EditorPanel editor = (EditorPanel) tabbedEditor.getComponentAt(i);
    
            if(editor.isModificado()){
                return true;
            }
        }
        return false;
    }

    private void initComponents() {
        setTitle("GoLite!!!");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JMenuBar barra = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        JMenu menuEjecutar = new JMenu("Ejecutar");
        JMenu menuReportes = new JMenu("Reportes");
        
        

        JMenuItem itemNuevo = new JMenuItem("Nuevo");
        JMenuItem itemAbrir = new JMenuItem("Abrir");
        JMenuItem itemGuardar = new JMenuItem("Guardar");
        JMenuItem itemGuardarComo = new JMenuItem("Guardar Como");
        JMenuItem itemCerrar = new JMenuItem("Cerrar Pestaña");
        JMenuItem itemReporteTokens = new JMenuItem("Reporte de tokens");
        JMenuItem itemReporteErrores = new JMenuItem("Reporte de errores");
        
        JMenuItem itemProbar = new JMenuItem("Probar");

        //menu de archivo
        menuArchivo.add(itemNuevo);
        menuArchivo.add(itemAbrir);
        menuArchivo.add(itemGuardar);
        menuArchivo.add(itemGuardarComo);
        menuArchivo.add(itemCerrar);
        
        menuEjecutar.add(itemProbar);

        menuReportes.add(itemReporteTokens);
        menuReportes.add(itemReporteErrores);

        barra.add(menuArchivo);
        barra.add(menuEjecutar);
        barra.add(menuReportes);

        setJMenuBar(barra);

        //editor
        tabbedEditor = new JTabbedPane();
        EditorPanel editorInicial = new EditorPanel();
        tabbedEditor.addTab("SinTitulo1.glt", editorInicial);
        editorInicial.gTextArea().addCaretListener(evt -> actualizarEstado());

        //PRUEBA EN CONSOLA
        consola = new ConsolePanel();

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,tabbedEditor, consola);

        split.setDividerLocation(550);

        add(split, BorderLayout.CENTER);
        lblEstado = new JLabel("Linea: 1  Columna: 1");
        add(lblEstado, BorderLayout.SOUTH);
        tabbedEditor.addChangeListener(e -> actualizarEstado());

        //ITEMS
        //abrir nueva pestania
        itemNuevo.addActionListener(e -> {
            contadorArchivos++;
            EditorPanel nuevoEditor = new EditorPanel();
            nuevoEditor.gTextArea().addCaretListener(evt -> actualizarEstado());
            tabbedEditor.addTab("SinTitulo" + contadorArchivos + ".glt", nuevoEditor);

            tabbedEditor.setSelectedComponent(nuevoEditor);
        });

        //abrir un documento (en diferente pestania)
        itemAbrir.addActionListener(e-> {
            JFileChooser chooser = new JFileChooser();

            chooser.setFileFilter(new FileNameExtensionFilter(
                            "Archivos GoLite (*.glt)",
                            "glt"));

            int opcion = chooser.showOpenDialog(this);

            if (opcion == JFileChooser.APPROVE_OPTION) {
                try {
                    File archivo = chooser.getSelectedFile();
                    String contenido = Files.readString(archivo.toPath());
                    EditorPanel editor = new EditorPanel();
                    editor.setTexto(contenido);
                    editor.gTextArea().addCaretListener(evt -> actualizarEstado());

                    tabbedEditor.addTab(archivo.getName(),editor);
                    tabbedEditor.setSelectedComponent(editor);

                } catch (Exception ex) {
                    consola.append("Error al abrir archivo: " + ex.getMessage());
                }
            }
        });

        //guardar como: 
        itemGuardarComo.addActionListener(e -> {
            EditorPanel editor = getEditorActual();
            JFileChooser chooser = new JFileChooser();
        
            chooser.setFileFilter(new FileNameExtensionFilter(
                    "Archivos GoLite (*.glt)","glt"));
        
            int opcion = chooser.showSaveDialog(this);
        
            if (opcion == JFileChooser.APPROVE_OPTION) {
                File archivo = chooser.getSelectedFile();
                String ruta = archivo.getAbsolutePath();
        
                if (!ruta.endsWith(".glt")) {
                    ruta += ".glt";
                }
        
                guardarEditor(editor, ruta);
                int indice = tabbedEditor.getSelectedIndex();
        
                tabbedEditor.setTitleAt(indice,archivo.getName() );
            }
        });

        //guardar
        itemGuardar.addActionListener(e ->{
            itemGuardarComo.doClick();
        });

        //cerrar pestania
        itemCerrar.addActionListener(e -> {
            EditorPanel editor = getEditorActual();

            if (editor.isModificado()) {
                int opcion = JOptionPane.showConfirmDialog(
                    this,"Hay cambios sin guardar. ¿Desea cerrar?",
                    "Confirmar",JOptionPane.YES_NO_OPTION);

                if (opcion != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            if (tabbedEditor.getTabCount() > 1) {
                tabbedEditor.remove(tabbedEditor.getSelectedIndex());
            }

        });

        //ejecutar analisis
        itemProbar.addActionListener(e -> {
            consola.clear();
            String codigo = getEditorActual().getTexto();

            consola.append("==TOKENS==");
            consola.append("");
            
            AnalizadorLexico analizador = new AnalizadorLexico();

            List<Token> tokens = analizador.analizar(codigo);
            ultimoReporteTokens = tokens;
            ultimoReporteErrores = analizador.getErrores();

            for (Token token :tokens){
                consola.append(token.toString());
            }

            consola.append("");
            consola.append("==SINTACTICO==");
            consola.append("");

            AnalizadorSintactico sintactico = new AnalizadorSintactico();
            consola.append(sintactico.analizar(codigo));
            ultimoReporteErrores.addAll(sintactico.getErrores());
        
            if (sintactico.getErrores().isEmpty()) {
                InterpreterVisitor interpreter = new InterpreterVisitor(consola);
            
                consola.append("");
                consola.append("==AST==");
            
                for (Instruccion ins : sintactico.getAST()) {
                    consola.append(ins.getClass().getSimpleName());
                }
            
                try {
            
                    interpreter.ejecutar(sintactico.getAST());
            
                } catch (RuntimeException ex) {
            
                    consola.append("");
                    consola.append("==ERROR SEMANTICO==");
                    consola.append(ex.getMessage());
            
                }
            }
        });
    
        //CERRAR PROGRAMA
        addWindowListener(
            new java.awt.event.WindowAdapter() {
        
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    if(hayCambiosSinGuardar()){
                        int opcion = JOptionPane.showConfirmDialog(
                                MainFrame.this,
                                "Existen archivos con cambios sin guardar.\n¿Desea salir?",
                                "Salir", JOptionPane.YES_NO_CANCEL_OPTION
                            );
        
                        if(opcion == JOptionPane.CANCEL_OPTION){
                            return;
                        }
        
                        if(opcion == JOptionPane.YES_OPTION){
                            dispose();
                        }
        
                    }else{
                        int opcion = JOptionPane.showConfirmDialog(
                                MainFrame.this,
                                "¿Desea salir?",
                                "Salir",JOptionPane.YES_NO_OPTION
                            );
        
                        if(opcion == JOptionPane.YES_OPTION){
                            dispose();
                        }
                    }
                }
            }
        );
        
        //REPORTES
        itemReporteTokens.addActionListener(e -> {
            System.out.println("CLICK TOKENS");
            if(ultimoReporteTokens == null){
                JOptionPane.showMessageDialog(
                        this,"Primero ejecute un analisis");
                return;
            }
        
            FrameReporteTokens frame = new FrameReporteTokens(ultimoReporteTokens);
            frame.setVisible(true);
        });

        itemReporteErrores.addActionListener(e -> {
            if(ultimoReporteErrores == null){
                JOptionPane.showMessageDialog(this, "Primero ejecute un analisis");
                return;
            }
        
            FrameReporteErrores frame = new FrameReporteErrores(ultimoReporteErrores);
            frame.setVisible(true);
        });
    }
}
