package com.labpoo.mediator;

public class Aeronave {

    private final String prefixo;
    private final TorreControle torre;

    public Aeronave(String prefixo, TorreControle torre) {
        this.prefixo = prefixo;
        this.torre = torre;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public void solicitarPouso() {
        System.out.println(prefixo + ": solicita pouso.");
        torre.solicitarPouso(this);
    }

    public void liberarPista() {
        System.out.println(prefixo + ": pousou e liberou a pista.");
        torre.liberarPista(this);
    }

    public void receber(String mensagem) {
        System.out.println("  -> " + prefixo + " recebe: " + mensagem);
    }
}
