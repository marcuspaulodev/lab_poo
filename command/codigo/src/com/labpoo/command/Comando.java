package com.labpoo.command;

public interface Comando {

    void executar();

    void desfazer();

    String descricao();
}
