package com.labpoo.command;

public class InserirTextoComando implements Comando {

    private final EditorTexto editor;
    private final String trecho;

    public InserirTextoComando(EditorTexto editor, String trecho) {
        this.editor = editor;
        this.trecho = trecho;
    }

    @Override
    public void executar() {
        editor.inserir(trecho);
    }

    @Override
    public void desfazer() {
        editor.apagar(trecho.length());
    }

    @Override
    public String descricao() {
        return "inserir \"" + trecho + "\"";
    }
}
