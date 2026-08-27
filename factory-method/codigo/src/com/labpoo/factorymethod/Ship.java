package com.labpoo.factorymethod;

public class Ship implements Transport {

    @Override
    public void deliver() {
        System.out.println("[Maritimo] Entregando carga por agua, em um navio.");
    }
}
