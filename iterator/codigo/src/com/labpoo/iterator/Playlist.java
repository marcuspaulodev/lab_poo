package com.labpoo.iterator;

public class Playlist implements ColecaoIteravel<Musica> {

    private Musica[] musicas = new Musica[2];
    private int total = 0;

    public void adicionar(Musica musica) {
        if (total == musicas.length) {
            Musica[] maior = new Musica[musicas.length * 2];
            System.arraycopy(musicas, 0, maior, 0, total);
            musicas = maior;
        }
        musicas[total++] = musica;
    }

    int total() {
        return total;
    }

    Musica musicaNa(int posicao) {
        return musicas[posicao];
    }

    @Override
    public Iterador<Musica> criarIterador() {
        return new IteradorPlaylist(this);
    }

    public Iterador<Musica> criarIteradorReverso() {
        return new IteradorReversoPlaylist(this);
    }
}
