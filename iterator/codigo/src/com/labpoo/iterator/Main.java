package com.labpoo.iterator;

public class Main {

    public static void main(String[] args) {
        Playlist playlist = new Playlist();
        playlist.adicionar(new Musica("Asa Branca", "Luiz Gonzaga"));
        playlist.adicionar(new Musica("Garota de Ipanema", "Tom Jobim"));
        playlist.adicionar(new Musica("Aquarela", "Toquinho"));
        playlist.adicionar(new Musica("Construção", "Chico Buarque"));

        System.out.println("Ordem normal:");
        tocar(playlist.criarIterador());

        System.out.println("Ordem reversa:");
        tocar(playlist.criarIteradorReverso());

        Iterador<Musica> primeiro = playlist.criarIterador();
        Iterador<Musica> segundo = playlist.criarIterador();
        primeiro.proximo();
        primeiro.proximo();
        System.out.println("Dois iteradores independentes:");
        System.out.println("  primeiro está em: " + primeiro.proximo());
        System.out.println("  segundo está em:  " + segundo.proximo());
    }

    private static void tocar(Iterador<Musica> iterador) {
        while (iterador.temProximo()) {
            System.out.println("  Tocando: " + iterador.proximo());
        }
    }
}
