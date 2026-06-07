package com.olc1.golite.ui;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConsolePanel extends JScrollPane{
    private JTextArea txtConsola;

    public ConsolePanel(){
        txtConsola = new JTextArea();
        txtConsola.setEditable(false);
        setViewportView(txtConsola);
    }

    public void append(String texto){
        txtConsola.append(texto+ "\n");
    }

    public void clear(){
        txtConsola.setText("");
    }
}
