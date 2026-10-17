package com.labpoo.observer;

public class AlertaCalor implements Observador {

    private final int limite;

    public AlertaCalor(int limite) {
        this.limite = limite;
    }

    @Override
    public void atualizar(int temperatura) {
        if (temperatura >= limite) {
            System.out.println("  [Alerta]    Calor extremo! " + temperatura + " °C (limite " + limite + " °C)");
        }
    }
}
