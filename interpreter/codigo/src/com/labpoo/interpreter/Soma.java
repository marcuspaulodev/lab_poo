package com.labpoo.interpreter;

public class Soma implements Expressao {

    private final Expressao esquerda;
    private final Expressao direita;

    public Soma(Expressao esquerda, Expressao direita) {
        this.esquerda = esquerda;
        this.direita = direita;
    }

    @Override
    public int interpretar(Contexto contexto) {
        return esquerda.interpretar(contexto) + direita.interpretar(contexto);
    }

    @Override
    public String toString() {
        return "(" + esquerda + " + " + direita + ")";
    }
}
