package com.labpoo.bridge;

public class ControleRemotoAvancado extends ControleRemoto {

    public ControleRemotoAvancado(Dispositivo dispositivo) {
        super(dispositivo);
    }

    public void mudo() {
        dispositivo.ajustarVolume(-100);
    }
}
