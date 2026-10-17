package com.labpoo.interpreter;

import java.util.ArrayDeque;
import java.util.Deque;

public class AnalisadorPosfixo {

    public Expressao analisar(String sentenca) {
        Deque<Expressao> pilha = new ArrayDeque<>();
        for (String token : sentenca.trim().split("\\s+")) {
            switch (token) {
                case "+" -> {
                    Expressao direita = pilha.pop();
                    pilha.push(new Soma(pilha.pop(), direita));
                }
                case "-" -> {
                    Expressao direita = pilha.pop();
                    pilha.push(new Subtracao(pilha.pop(), direita));
                }
                case "*" -> {
                    Expressao direita = pilha.pop();
                    pilha.push(new Multiplicacao(pilha.pop(), direita));
                }
                default -> pilha.push(terminal(token));
            }
        }
        return pilha.pop();
    }

    private Expressao terminal(String token) {
        if (token.matches("-?\\d+")) {
            return new Numero(Integer.parseInt(token));
        }
        return new Variavel(token);
    }
}
