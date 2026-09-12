package com.labpoo.singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Logger {

    private static volatile Logger instancia;

    private final List<String> historico = new ArrayList<>();

    private Logger() {
        System.out.println("[Logger] Inicializando instância única (isso só acontece uma vez).");
    }

    public static Logger getInstance() {
        if (instancia == null) {
            synchronized (Logger.class) {
                if (instancia == null) {
                    instancia = new Logger();
                }
            }
        }
        return instancia;
    }

    public void log(String mensagem) {
        historico.add(mensagem);
        System.out.println("[LOG] " + mensagem);
    }

    public List<String> getHistorico() {
        return Collections.unmodifiableList(historico);
    }
}
