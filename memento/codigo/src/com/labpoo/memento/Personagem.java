package com.labpoo.memento;

public class Personagem {

    private final String nome;
    private String fase = "Vila";
    private int nivel = 1;
    private int vida = 100;

    public Personagem(String nome) {
        this.nome = nome;
    }

    public void avancarFase(String novaFase) {
        fase = novaFase;
    }

    public void subirNivel() {
        nivel++;
    }

    public void sofrerDano(int dano) {
        vida = Math.max(0, vida - dano);
    }

    public Memento salvar() {
        return new Memento(fase, nivel, vida);
    }

    public void restaurar(Memento memento) {
        fase = memento.fase;
        nivel = memento.nivel;
        vida = memento.vida;
    }

    @Override
    public String toString() {
        return nome + " | Fase: " + fase + " | Nível " + nivel + " | Vida " + vida;
    }

    public static final class Memento {

        private final String fase;
        private final int nivel;
        private final int vida;

        private Memento(String fase, int nivel, int vida) {
            this.fase = fase;
            this.nivel = nivel;
            this.vida = vida;
        }

        public String rotulo() {
            return fase;
        }
    }
}
