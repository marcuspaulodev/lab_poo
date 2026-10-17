package com.labpoo.iterator;

import java.util.NoSuchElementException;

public class IteradorPlaylist implements Iterador<Musica> {

    private final Playlist playlist;
    private int posicao = 0;

    public IteradorPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    @Override
    public boolean temProximo() {
        return posicao < playlist.total();
    }

    @Override
    public Musica proximo() {
        if (!temProximo()) {
            throw new NoSuchElementException("Fim da playlist");
        }
        return playlist.musicaNa(posicao++);
    }
}
