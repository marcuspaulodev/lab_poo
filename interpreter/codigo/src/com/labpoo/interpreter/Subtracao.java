package com.labpoo.interpreter;

public class Subtracao implements Expressao {

    private final Expressao esquerda;
    private final Expressao direita;

    public Subtracao(Expressao esquerda, Expressao direita) {
        this.esquerda = esquerda;
        this.direita = direita;
    }

    @Override
    public int interpretar(Contexto contexto) {
        return esquerda.interpretar(contexto) - direita.interpretar(contexto);
    }

    @Override
    public String toString() {
        return "(" + esquerda + " - " + direita + ")";
    }
}
