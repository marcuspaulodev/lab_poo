package com.labpoo.memento;

import java.util.ArrayDeque;
import java.util.Deque;

public class Checkpoints {

    private final Personagem personagem;
    private final Deque<Personagem.Memento> historico = new ArrayDeque<>();

    public Checkpoints(Personagem personagem) {
        this.personagem = personagem;
    }

    public void salvar() {
        Personagem.Memento memento = personagem.salvar();
        historico.push(memento);
        System.out.println("Checkpoint salvo: " + memento.rotulo());
    }

    public void voltar() {
        if (historico.isEmpty()) {
            System.out.println("Nenhum checkpoint para voltar");
            return;
        }
        Personagem.Memento memento = historico.pop();
        personagem.restaurar(memento);
        System.out.println("Voltou para:      " + memento.rotulo());
    }
}
