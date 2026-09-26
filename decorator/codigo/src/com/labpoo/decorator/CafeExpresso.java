package com.labpoo.decorator;

public class CafeExpresso implements Bebida {

    @Override
    public String descricao() {
        return "Café expresso";
    }

    @Override
    public double preco() {
        return 5.00;
    }
}
