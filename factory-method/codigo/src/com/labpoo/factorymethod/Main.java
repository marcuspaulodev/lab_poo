package com.labpoo.factorymethod;

public class Main {

    public static void main(String[] args) {
        String modal = args.length > 0 ? args[0] : "road";

        // O cliente decide, em um unico ponto, QUAL Concrete Creator usar.
        // Dali em diante, so conhece a classe abstrata Logistics.
        Logistics logistics = modal.equalsIgnoreCase("sea")
                ? new SeaLogistics()
                : new RoadLogistics();

        System.out.println("Modal escolhido: " + modal);
        System.out.println("Creator concreto: " + logistics.getClass().getSimpleName());
        System.out.println();

        logistics.planDelivery();
    }
}
