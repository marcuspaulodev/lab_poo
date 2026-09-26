package com.labpoo.facade;

public class PlayerStreaming {

    public void ligar() {
        System.out.println("Player: ligado");
    }

    public void reproduzir(String filme) {
        System.out.println("Player: reproduzindo \"" + filme + "\"");
    }

    public void parar() {
        System.out.println("Player: parado");
    }

    public void desligar() {
        System.out.println("Player: desligado");
    }
}
