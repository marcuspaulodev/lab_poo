package com.labpoo.observer;

import java.util.ArrayList;
import java.util.List;

public class EstacaoMeteorologica implements Sujeito {

    private final List<Observador> observadores = new ArrayList<>();
    private int temperatura;

    @Override
    public void adicionar(Observador observador) {
        observadores.add(observador);
    }

    @Override
    public void remover(Observador observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificar() {
        for (Observador observador : observadores) {
            observador.atualizar(temperatura);
        }
    }

    public void medir(int temperatura) {
        System.out.println("Estação mediu " + temperatura + " °C");
        this.temperatura = temperatura;
        notificar();
    }
}
