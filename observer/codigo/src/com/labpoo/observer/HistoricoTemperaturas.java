package com.labpoo.observer;

import java.util.ArrayList;
import java.util.List;

public class HistoricoTemperaturas implements Observador {

    private final List<Integer> registros = new ArrayList<>();

    @Override
    public void atualizar(int temperatura) {
        registros.add(temperatura);
        System.out.println("  [Histórico] " + registros.size() + " medição(ões) registrada(s)");
    }

    public List<Integer> registros() {
        return registros;
    }
}
