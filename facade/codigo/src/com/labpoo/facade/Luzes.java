package com.labpoo.facade;

public class Luzes {

    public void diminuir(int nivel) {
        System.out.println("Luzes: diminuindo para " + nivel + "%");
    }

    public void acender() {
        System.out.println("Luzes: acesas");
    }
}
