package com.company.ja.trabalhofinal.model;

public class Comentario {

    private Usuario usuario;
    private String comentario;

    public Comentario(){

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
