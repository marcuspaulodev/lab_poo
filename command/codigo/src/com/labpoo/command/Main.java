package com.labpoo.command;

public class Main {

    public static void main(String[] args) {
        EditorTexto editor = new EditorTexto();
        GerenciadorComandos gerenciador = new GerenciadorComandos();

        gerenciador.executar(new InserirTextoComando(editor, "Olá"));
        mostrar(editor);
        gerenciador.executar(new InserirTextoComando(editor, " mundo"));
        mostrar(editor);
        gerenciador.executar(new ApagarTextoComando(editor, 6));
        mostrar(editor);

        gerenciador.desfazer();
        mostrar(editor);
        gerenciador.desfazer();
        mostrar(editor);
        gerenciador.desfazer();
        mostrar(editor);
        gerenciador.desfazer();
    }

    private static void mostrar(EditorTexto editor) {
        System.out.println("  Texto: \"" + editor.conteudo() + "\"");
    }
}
