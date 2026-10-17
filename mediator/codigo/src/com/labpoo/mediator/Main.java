package com.labpoo.mediator;

public class Main {

    public static void main(String[] args) {
        TorreControle torre = new TorreAeroporto();
        Aeronave gol = new Aeronave("GOL1234", torre);
        Aeronave azul = new Aeronave("AZU4321", torre);
        Aeronave latam = new Aeronave("TAM3030", torre);

        gol.solicitarPouso();
        azul.solicitarPouso();
        latam.solicitarPouso();

        gol.liberarPista();
        azul.liberarPista();
        latam.liberarPista();
    }
}
