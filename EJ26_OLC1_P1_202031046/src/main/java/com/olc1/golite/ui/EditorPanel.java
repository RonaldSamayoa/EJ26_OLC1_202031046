package com.olc1.golite.ui;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class EditorPanel extends JScrollPane{
    private JTextArea txtEditor;

    public EditorPanel() {
        txtEditor = new JTextArea();
        setViewportView(txtEditor);
    }

    public String getTexto(){
        return txtEditor.getText();
    }

    public void setTexto(String texto){
        txtEditor.setText(texto);
    }

    public JTextArea gTextArea(){
        return txtEditor;
    }
}
