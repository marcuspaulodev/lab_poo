package com.labpoo.prototype;

import java.util.HashMap;
import java.util.Map;

public class MonstroRegistro {

    private final Map<String, Monstro> prototipos = new HashMap<>();

    public void registrar(String tipo, Monstro prototipo) {
        prototipos.put(tipo, prototipo);
    }

    public Monstro criarMonstro(String tipo) {
        Monstro prototipo = prototipos.get(tipo);
        if (prototipo == null) {
            throw new IllegalArgumentException("Nenhum prototipo registrado para o tipo: " + tipo);
        }
        return prototipo.clonar();
    }
}
