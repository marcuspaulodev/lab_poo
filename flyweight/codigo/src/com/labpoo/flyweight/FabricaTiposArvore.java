package com.labpoo.flyweight;

import java.util.HashMap;
import java.util.Map;

public class FabricaTiposArvore {

    private static final Map<String, TipoArvore> cache = new HashMap<>();

    public static TipoArvore obterTipo(String nome, String cor, String textura) {
        String chave = nome + "_" + cor + "_" + textura;
        return cache.computeIfAbsent(chave, k -> new TipoArvore(nome, cor, textura));
    }

    public static int totalTipos() {
        return cache.size();
    }
}
