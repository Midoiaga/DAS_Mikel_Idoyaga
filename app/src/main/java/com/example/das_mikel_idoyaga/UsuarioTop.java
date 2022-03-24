package com.example.das_mikel_idoyaga;

//Clase para facilitar la creacion del VistView
public class UsuarioTop {
    private String usuario;
    private String puntuacion;

    //Contructor
    public UsuarioTop(String usuario, String puntuacion) {
        this.usuario = usuario;
        this.puntuacion = puntuacion;
    }


    //Getter y setters
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getPuntuacion() {
        return puntuacion;
    }

    public String getUsuario() {
        return usuario;
    }
}
