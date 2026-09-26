package com.labpoo.flyweight;

import java.util.ArrayList;
import java.util.List;

public class Floresta {

    private final List<Arvore> arvores = new ArrayList<>();

    public void plantar(int x, int y, String nome, String cor, String textura) {
        TipoArvore tipo = FabricaTiposArvore.obterTipo(nome, cor, textura);
        arvores.add(new Arvore(x, y, tipo));
    }

    public void desenhar() {
        for (Arvore arvore : arvores) {
            arvore.desenhar();
        }
    }

    public int totalArvores() {
        return arvores.size();
    }
}
