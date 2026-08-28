package com.labpoo.factorymethod;

public class Main {

    public static void main(String[] args) {
        String modal = args.length > 0 ? args[0] : "road";

        Logistics logistics = modal.equalsIgnoreCase("sea")
                ? new SeaLogistics()
                : new RoadLogistics();

        System.out.println("Modal escolhido: " + modal);
        System.out.println("Creator concreto: " + logistics.getClass().getSimpleName());
        System.out.println();

        logistics.planDelivery();
    }
}
