package com.labpoo.decorator;

public abstract class DecoradorBebida implements Bebida {

    protected final Bebida bebida;

    public DecoradorBebida(Bebida bebida) {
        this.bebida = bebida;
    }

    @Override
    public String descricao() {
        return bebida.descricao();
    }

    @Override
    public double preco() {
        return bebida.preco();
    }
}
