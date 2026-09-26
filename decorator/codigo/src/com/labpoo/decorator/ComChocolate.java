package com.labpoo.decorator;

public class ComChocolate extends DecoradorBebida {

    public ComChocolate(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String descricao() {
        return bebida.descricao() + ", chocolate";
    }

    @Override
    public double preco() {
        return bebida.preco() + 2.00;
    }
}
