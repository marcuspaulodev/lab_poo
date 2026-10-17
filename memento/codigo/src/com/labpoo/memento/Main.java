package com.labpoo.memento;

public class Main {

    public static void main(String[] args) {
        Personagem heroi = new Personagem("Aria");
        Checkpoints checkpoints = new Checkpoints(heroi);
        mostrar(heroi);

        checkpoints.salvar();
        heroi.avancarFase("Floresta");
        heroi.subirNivel();
        heroi.sofrerDano(30);
        mostrar(heroi);

        checkpoints.salvar();
        heroi.avancarFase("Castelo");
        heroi.sofrerDano(70);
        mostrar(heroi);

        checkpoints.voltar();
        mostrar(heroi);
        checkpoints.voltar();
        mostrar(heroi);
        checkpoints.voltar();
    }

    private static void mostrar(Personagem personagem) {
        System.out.println("  " + personagem);
    }
}
