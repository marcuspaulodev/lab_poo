package com.labpoo.prototype;

public class Main {

    public static void main(String[] args) {
        MonstroRegistro registro = new MonstroRegistro();
        registro.registrar("goblin", new Goblin());
        registro.registrar("dragao", new Dragao());

        Monstro goblin1 = registro.criarMonstro("goblin");
        Monstro goblin2 = registro.criarMonstro("goblin");
        Monstro dragao1 = registro.criarMonstro("dragao");

        System.out.println("Antes de modificar o clone:");
        System.out.println(goblin1);
        System.out.println(goblin2);
        System.out.println(dragao1);
        System.out.println();

        goblin2.setNome("Goblin Batedor");
        goblin2.getLoot().add("Mapa Roubado");

        System.out.println("Depois de modificar apenas goblin2:");
        System.out.println(goblin1);
        System.out.println(goblin2);
        System.out.println();

        System.out.println("goblin1 == goblin2? " + (goblin1 == goblin2));
        System.out.println("goblin1.getLoot() == goblin2.getLoot()? "
                + (goblin1.getLoot() == goblin2.getLoot()));
    }
}
