package com.labpoo.bridge;

public class Radio implements Dispositivo {

    private boolean ligada = false;
    private int volume = 20;

    @Override
    public void ligar() {
        ligada = true;
    }

    @Override
    public void desligar() {
        ligada = false;
    }

    @Override
    public void ajustarVolume(int variacao) {
        volume = Math.max(0, Math.min(100, volume + variacao));
    }

    @Override
    public String status() {
        return "Radio " + (ligada ? "ligado" : "desligado") + ", volume " + volume;
    }
}
