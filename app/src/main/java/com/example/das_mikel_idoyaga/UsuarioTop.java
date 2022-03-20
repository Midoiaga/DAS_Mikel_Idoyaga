package com.example.das_mikel_idoyaga;

public class UsuarioTop {
    private String usuario;
    private String puntuacion;

    public UsuarioTop(String usuario, String puntuacion) {
        this.usuario = usuario;
        this.puntuacion = puntuacion;
    }

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
