package com.labpoo.command;

import java.util.ArrayDeque;
import java.util.Deque;

public class GerenciadorComandos {

    private final Deque<Comando> historico = new ArrayDeque<>();

    public void executar(Comando comando) {
        comando.executar();
        historico.push(comando);
        System.out.println("Executou: " + comando.descricao());
    }

    public void desfazer() {
        if (historico.isEmpty()) {
            System.out.println("Nada para desfazer");
            return;
        }
        Comando comando = historico.pop();
        comando.desfazer();
        System.out.println("Desfez:   " + comando.descricao());
    }
}
