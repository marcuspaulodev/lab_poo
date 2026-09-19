package com.labpoo.bridge;

public interface Dispositivo {
    void ligar();

    void desligar();

    void ajustarVolume(int variacao);

    String status();
}
