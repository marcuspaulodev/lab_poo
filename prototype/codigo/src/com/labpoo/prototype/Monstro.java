package com.labpoo.prototype;

import java.util.ArrayList;
import java.util.List;

public class Monstro implements Prototype<Monstro> {

    private String nome;
    private int hp;
    private int ataque;
    private List<String> loot;

    public Monstro(String nome, int hp, int ataque, List<String> loot) {
        this.nome = nome;
        this.hp = hp;
        this.ataque = ataque;
        this.loot = new ArrayList<>(loot);
    }

    protected Monstro(Monstro original) {
        this.nome = original.nome;
        this.hp = original.hp;
        this.ataque = original.ataque;
        this.loot = new ArrayList<>(original.loot);
    }

    @Override
    public Monstro clonar() {
        return new Monstro(this);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getLoot() {
        return loot;
    }

    @Override
    public String toString() {
        return String.format("%s [hp=%d, ataque=%d, loot=%s]", nome, hp, ataque, loot);
    }
}
