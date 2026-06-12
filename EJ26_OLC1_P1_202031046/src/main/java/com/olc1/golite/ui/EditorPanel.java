package com.olc1.golite.ui;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class EditorPanel extends JScrollPane{
    private JTextArea txtEditor;
    private boolean modificado = false;

    public EditorPanel() {
        txtEditor = new JTextArea();
        txtEditor.getDocument().addDocumentListener(
            new javax.swing.event.DocumentListener() {

                @Override
                public void insertUpdate(javax.swing.event.DocumentEvent e) {
                    modificado = true;
                }

                @Override
                public void removeUpdate(javax.swing.event.DocumentEvent e) {
                    modificado = true;
                }

                @Override
                public void changedUpdate(javax.swing.event.DocumentEvent e) {
                    modificado = true;
                }
            }
        );

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

    public boolean isModificado(){
        return modificado;
    }

    public void setModificado(boolean modificado){
        this.modificado = modificado;
    }

    public int getLineaActual(){
        try {
            int pos = txtEditor.getCaretPosition();
            return txtEditor.getLineOfOffset(pos)+1;
        } catch (Exception e) {
            return 1;
        }
    }

    public int getColumnaActual(){
        try {
            int pos = txtEditor.getCaretPosition();
            int linea = txtEditor.getLineOfOffset(pos);
            return pos - txtEditor.getLineStartOffset(linea)+1;
        } catch (Exception e) {
            return 1;
        }
    }
}
