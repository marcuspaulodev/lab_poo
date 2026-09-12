package com.labpoo.prototype;

import java.util.List;

public class Dragao extends Monstro {

    public Dragao() {
        super("Dragao", 500, 80, List.of("Escama de Dragao", "Tesouro Ancestral", "Ovo de Dragao"));
    }

    protected Dragao(Dragao original) {
        super(original);
    }

    @Override
    public Dragao clonar() {
        return new Dragao(this);
    }
}
