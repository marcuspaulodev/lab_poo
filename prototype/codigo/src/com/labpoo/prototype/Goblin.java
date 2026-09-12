package com.labpoo.prototype;

import java.util.List;

public class Goblin extends Monstro {

    public Goblin() {
        super("Goblin", 30, 5, List.of("Adaga Enferrujada", "Moeda de Cobre"));
    }

    protected Goblin(Goblin original) {
        super(original);
    }

    @Override
    public Goblin clonar() {
        return new Goblin(this);
    }
}
