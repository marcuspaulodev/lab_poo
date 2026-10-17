package com.labpoo.mediator;

import java.util.ArrayDeque;
import java.util.Deque;

public class TorreAeroporto implements TorreControle {

    private final Deque<Aeronave> fila = new ArrayDeque<>();
    private Aeronave naPista;

    @Override
    public void solicitarPouso(Aeronave aeronave) {
        if (naPista == null) {
            autorizar(aeronave);
        } else {
            fila.addLast(aeronave);
            aeronave.receber("pista ocupada por " + naPista.getPrefixo()
                    + ", aguarde (posição " + fila.size() + " na fila)");
        }
    }

    @Override
    public void liberarPista(Aeronave aeronave) {
        if (aeronave != naPista) {
            throw new IllegalStateException(aeronave.getPrefixo() + " não está na pista");
        }
        naPista = null;
        if (!fila.isEmpty()) {
            autorizar(fila.pollFirst());
        }
    }

    private void autorizar(Aeronave aeronave) {
        naPista = aeronave;
        aeronave.receber("pouso autorizado");
        for (Aeronave outra : fila) {
            outra.receber(aeronave.getPrefixo() + " está na pista, mantenha a espera");
        }
    }
}
