package com.labpoo.command;

public class EditorTexto {

    private final StringBuilder texto = new StringBuilder();

    public void inserir(String trecho) {
        texto.append(trecho);
    }

    public String apagar(int quantidade) {
        int inicio = Math.max(0, texto.length() - quantidade);
        String apagado = texto.substring(inicio);
        texto.delete(inicio, texto.length());
        return apagado;
    }

    public String conteudo() {
        return texto.toString();
    }
}
