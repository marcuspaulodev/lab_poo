package com.labpoo.facade;

public class HomeTheaterFacade {

    private final Luzes luzes;
    private final Projetor projetor;
    private final SistemaSom som;
    private final PlayerStreaming player;

    public HomeTheaterFacade(Luzes luzes, Projetor projetor, SistemaSom som, PlayerStreaming player) {
        this.luzes = luzes;
        this.projetor = projetor;
        this.som = som;
        this.player = player;
    }

    public void assistirFilme(String filme) {
        luzes.diminuir(10);
        projetor.ligar();
        projetor.modoWidescreen();
        som.ligar();
        som.modoSurround();
        som.ajustarVolume(20);
        player.ligar();
        player.reproduzir(filme);
    }

    public void encerrarFilme() {
        player.parar();
        player.desligar();
        som.desligar();
        projetor.desligar();
        luzes.acender();
    }
}
