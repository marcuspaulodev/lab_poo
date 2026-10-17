package com.labpoo.iterator;

import java.util.NoSuchElementException;

public class IteradorReversoPlaylist implements Iterador<Musica> {

    private final Playlist playlist;
    private int posicao;

    public IteradorReversoPlaylist(Playlist playlist) {
        this.playlist = playlist;
        this.posicao = playlist.total() - 1;
    }

    @Override
    public boolean temProximo() {
        return posicao >= 0;
    }

    @Override
    public Musica proximo() {
        if (!temProximo()) {
            throw new NoSuchElementException("Início da playlist");
        }
        return playlist.musicaNa(posicao--);
    }
}
