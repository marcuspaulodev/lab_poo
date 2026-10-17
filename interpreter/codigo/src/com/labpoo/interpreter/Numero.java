package com.labpoo.interpreter;

public class Numero implements Expressao {

    private final int valor;

    public Numero(int valor) {
        this.valor = valor;
    }

    @Override
    public int interpretar(Contexto contexto) {
        return valor;
    }

    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}
