package com.labpoo.interpreter;

public class Variavel implements Expressao {

    private final String nome;

    public Variavel(String nome) {
        this.nome = nome;
    }

    @Override
    public int interpretar(Contexto contexto) {
        return contexto.valor(nome);
    }

    @Override
    public String toString() {
        return nome;
    }
}
