package com.labpoo.composite;

public class Arquivo implements ItemSistemaArquivos {

    private final String nome;
    private final int tamanho;

    public Arquivo(String nome, int tamanho) {
        this.nome = nome;
        this.tamanho = tamanho;
    }

    @Override
    public String nome() {
        return nome;
    }

    @Override
    public int tamanho() {
        return tamanho;
    }

    @Override
    public void exibir(String indentacao) {
        System.out.println(indentacao + nome + " (" + tamanho + " KB)");
    }
}
