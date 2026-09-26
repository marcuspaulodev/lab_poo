package com.labpoo.composite;

import java.util.ArrayList;
import java.util.List;

public class Pasta implements ItemSistemaArquivos {

    private final String nome;
    private final List<ItemSistemaArquivos> filhos = new ArrayList<>();

    public Pasta(String nome) {
        this.nome = nome;
    }

    public void adicionar(ItemSistemaArquivos item) {
        filhos.add(item);
    }

    public void remover(ItemSistemaArquivos item) {
        filhos.remove(item);
    }

    @Override
    public String nome() {
        return nome;
    }

    @Override
    public int tamanho() {
        int total = 0;
        for (ItemSistemaArquivos filho : filhos) {
            total += filho.tamanho();
        }
        return total;
    }

    @Override
    public void exibir(String indentacao) {
        System.out.println(indentacao + nome + "/ (" + tamanho() + " KB)");
        for (ItemSistemaArquivos filho : filhos) {
            filho.exibir(indentacao + "  ");
        }
    }
}
