package com.labpoo.decorator;

public class Main {

    public static void main(String[] args) {
        Bebida simples = new CafeExpresso();
        imprimir(simples);

        Bebida comLeite = new ComLeite(new CafeExpresso());
        imprimir(comLeite);

        Bebida completo = new ComChantilly(new ComChocolate(new ComLeite(new CafeExpresso())));
        imprimir(completo);
    }

    private static void imprimir(Bebida bebida) {
        System.out.printf("%s: R$ %.2f%n", bebida.descricao(), bebida.preco());
    }
}
