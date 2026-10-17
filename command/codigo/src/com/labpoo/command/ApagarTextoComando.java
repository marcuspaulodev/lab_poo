package com.labpoo.command;

public class ApagarTextoComando implements Comando {

    private final EditorTexto editor;
    private final int quantidade;
    private String trechoApagado = "";

    public ApagarTextoComando(EditorTexto editor, int quantidade) {
        this.editor = editor;
        this.quantidade = quantidade;
    }

    @Override
    public void executar() {
        trechoApagado = editor.apagar(quantidade);
    }

    @Override
    public void desfazer() {
        editor.inserir(trechoApagado);
    }

    @Override
    public String descricao() {
        return "apagar " + quantidade + " caracteres";
    }
}
