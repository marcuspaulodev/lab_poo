package com.labpoo.flyweight;

public class TipoArvore {

    private final String nome;
    private final String cor;
    private final String textura;

    public TipoArvore(String nome, String cor, String textura) {
        this.nome = nome;
        this.cor = cor;
        this.textura = textura;
    }

    public void desenhar(int x, int y) {
        System.out.println("Desenhando " + nome + " (" + cor + ", " + textura + ") em (" + x + ", " + y + ")");
    }
}
