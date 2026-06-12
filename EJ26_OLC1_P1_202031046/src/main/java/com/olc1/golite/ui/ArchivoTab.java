package com.olc1.golite.ui;

import java.io.File;

public class ArchivoTab {
    private File archivo;
    private boolean modificado;

    public ArchivoTab(){
        this.archivo = null;
        this.modificado = false;
    }

    public File getArchivo(){
        return archivo;
    }

    public void setArchivo(File archivo){
        this.archivo = archivo;
    }

    public boolean isModificado(){
        return modificado;
    }

    public void setModificado(boolean modificado){
        this.modificado = modificado;
    }
}
