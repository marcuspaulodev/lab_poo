package com.labpoo.builder;

public class Main {

    public static void main(String[] args) {
        DiretorMontagem diretor = new DiretorMontagem();

        Computador gamer = diretor.montarComputadorGamer(new ComputadorBuilderPadrao());
        Computador escritorio = diretor.montarComputadorEscritorio(new ComputadorBuilderPadrao());

        System.out.println("Configuracao Gamer (via Diretor):");
        System.out.println(gamer);
        System.out.println();

        System.out.println("Configuracao Escritorio (via Diretor):");
        System.out.println(escritorio);
        System.out.println();

        Computador personalizado = new ComputadorBuilderPadrao()
                .setCpu("AMD Ryzen 7")
                .setRam("16GB DDR5")
                .setArmazenamento("1TB NVMe SSD")
                .setPlacaDeVideo("RTX 4060")
                .setFonte("650W")
                .build();

        System.out.println("Configuracao Personalizada (sem Diretor):");
        System.out.println(personalizado);
    }
}
