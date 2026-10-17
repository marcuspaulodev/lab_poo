package com.labpoo.iterator;

public class Musica {

    private final String titulo;
    private final String artista;

    public Musica(String titulo, String artista) {
        this.titulo = titulo;
        this.artista = artista;
    }

    @Override
    public String toString() {
        return titulo + " — " + artista;
    }
}
