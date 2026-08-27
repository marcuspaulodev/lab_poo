package com.labpoo.factorymethod;

public class Truck implements Transport {

    @Override
    public void deliver() {
        System.out.println("[Rodoviario] Entregando carga por terra, em um caminhao.");
    }
}
