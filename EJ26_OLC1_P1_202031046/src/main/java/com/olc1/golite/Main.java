package com.olc1.golite;
import javax.swing.SwingUtilities;

import com.olc1.golite.ui.MainFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame ventana = new MainFrame();
            ventana.setVisible(true);
        });    
    }
}
