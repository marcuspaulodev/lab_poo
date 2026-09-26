package com.labpoo.facade;

public class Main {

    public static void main(String[] args) {
        HomeTheaterFacade homeTheater = new HomeTheaterFacade(
                new Luzes(), new Projetor(), new SistemaSom(), new PlayerStreaming());

        System.out.println("== Começando a sessão ==");
        homeTheater.assistirFilme("O Auto da Compadecida");

        System.out.println("== Fim da sessão ==");
        homeTheater.encerrarFilme();
    }
}
