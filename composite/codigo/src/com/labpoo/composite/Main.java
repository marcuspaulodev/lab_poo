package com.labpoo.composite;

public class Main {

    public static void main(String[] args) {
        Pasta src = new Pasta("src");
        src.adicionar(new Arquivo("Main.java", 4));
        src.adicionar(new Arquivo("Pasta.java", 6));

        Pasta docs = new Pasta("docs");
        docs.adicionar(new Arquivo("manual.pdf", 120));

        Pasta projeto = new Pasta("projeto");
        projeto.adicionar(src);
        projeto.adicionar(docs);
        projeto.adicionar(new Arquivo("README.md", 2));

        projeto.exibir("");
        System.out.println("Tamanho total: " + projeto.tamanho() + " KB");
    }
}
