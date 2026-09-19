package com.labpoo.bridge;

public class Main {

    public static void main(String[] args) {
        ControleRemoto controleTv = new ControleRemoto(new TV());
        controleTv.ligar();
        controleTv.aumentarVolume();
        controleTv.aumentarVolume();
        System.out.println(controleTv.status());

        ControleRemotoAvancado controleRadio = new ControleRemotoAvancado(new Radio());
        controleRadio.ligar();
        controleRadio.aumentarVolume();
        controleRadio.mudo();
        System.out.println(controleRadio.status());
    }
}
