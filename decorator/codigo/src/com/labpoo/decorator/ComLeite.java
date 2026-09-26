package com.labpoo.decorator;

public class ComLeite extends DecoradorBebida {

    public ComLeite(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String descricao() {
        return bebida.descricao() + ", leite";
    }

    @Override
    public double preco() {
        return bebida.preco() + 1.50;
    }
}
