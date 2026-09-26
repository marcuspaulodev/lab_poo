package com.labpoo.facade;

public class SistemaSom {

    public void ligar() {
        System.out.println("Som: ligado");
    }

    public void modoSurround() {
        System.out.println("Som: modo surround 5.1");
    }

    public void ajustarVolume(int volume) {
        System.out.println("Som: volume em " + volume);
    }

    public void desligar() {
        System.out.println("Som: desligado");
    }
}
