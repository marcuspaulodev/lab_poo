package com.labpoo.observer;

public class PainelTemperatura implements Observador {

    @Override
    public void atualizar(int temperatura) {
        System.out.println("  [Painel]    Temperatura atual: " + temperatura + " °C");
    }
}
