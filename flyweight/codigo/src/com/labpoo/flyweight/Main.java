package com.labpoo.flyweight;

public class Main {

    public static void main(String[] args) {
        Floresta floresta = new Floresta();
        floresta.plantar(1, 2, "Ipê", "Amarelo", "ipe.png");
        floresta.plantar(5, 3, "Cajueiro", "Verde", "cajueiro.png");
        floresta.plantar(8, 7, "Ipê", "Amarelo", "ipe.png");
        floresta.plantar(4, 9, "Pau-brasil", "Vermelho", "paubrasil.png");
        floresta.desenhar();

        for (int i = 0; i < 100_000; i++) {
            floresta.plantar(i % 500, i / 500, "Cajueiro", "Verde", "cajueiro.png");
        }

        System.out.println("Árvores plantadas: " + floresta.totalArvores());
        System.out.println("Tipos de árvore criados: " + FabricaTiposArvore.totalTipos());
    }
}
