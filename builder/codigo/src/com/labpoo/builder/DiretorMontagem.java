package com.labpoo.builder;

public class DiretorMontagem {

    public Computador montarComputadorGamer(ComputadorBuilder builder) {
        return builder
                .setCpu("Intel Core i9")
                .setRam("32GB DDR5")
                .setArmazenamento("2TB NVMe SSD")
                .setPlacaDeVideo("RTX 4080")
                .setFonte("850W")
                .build();
    }

    public Computador montarComputadorEscritorio(ComputadorBuilder builder) {
        return builder
                .setCpu("Intel Core i3")
                .setRam("8GB DDR4")
                .setArmazenamento("512GB SSD")
                .setFonte("400W")
                .build();
    }
}
