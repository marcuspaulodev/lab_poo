package com.labpoo.bridge;

public class ControleRemoto {

    protected final Dispositivo dispositivo;

    public ControleRemoto(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public void ligar() {
        dispositivo.ligar();
    }

    public void desligar() {
        dispositivo.desligar();
    }

    public void aumentarVolume() {
        dispositivo.ajustarVolume(10);
    }

    public void diminuirVolume() {
        dispositivo.ajustarVolume(-10);
    }

    public String status() {
        return dispositivo.status();
    }
}
