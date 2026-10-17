package com.labpoo.interpreter;

import java.util.HashMap;
import java.util.Map;

public class Contexto {

    private final Map<String, Integer> variaveis = new HashMap<>();

    public void definir(String nome, int valor) {
        variaveis.put(nome, valor);
    }

    public int valor(String nome) {
        Integer valor = variaveis.get(nome);
        if (valor == null) {
            throw new IllegalArgumentException("Variável não definida: " + nome);
        }
        return valor;
    }
}
