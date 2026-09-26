package com.labpoo.decorator;

public class ComChantilly extends DecoradorBebida {

    public ComChantilly(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String descricao() {
        return bebida.descricao() + ", chantilly";
    }

    @Override
    public double preco() {
        return bebida.preco() + 2.50;
    }
}
