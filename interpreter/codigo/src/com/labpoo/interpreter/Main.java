package com.labpoo.interpreter;

public class Main {

    public static void main(String[] args) {
        Expressao expressao = new AnalisadorPosfixo().analisar("x 5 + y 2 - *");
        System.out.println("Sentença: x 5 + y 2 - *");
        System.out.println("Árvore:   " + expressao);

        Contexto primeiro = new Contexto();
        primeiro.definir("x", 3);
        primeiro.definir("y", 10);
        System.out.println("Com x = 3 e y = 10: " + expressao.interpretar(primeiro));

        Contexto segundo = new Contexto();
        segundo.definir("x", 1);
        segundo.definir("y", 4);
        System.out.println("Com x = 1 e y = 4:  " + expressao.interpretar(segundo));

        Expressao manual = new Soma(new Numero(2), new Multiplicacao(new Variavel("x"), new Numero(4)));
        System.out.println("Árvore montada à mão: " + manual + " = " + manual.interpretar(primeiro));
    }
}
