package com.labpoo.adapter;

public class ReprodutorVlc implements ReprodutorAvancado {

    @Override
    public void reproduzirVlc(String nomeArquivo) {
        System.out.println("Tocando arquivo vlc: " + nomeArquivo);
    }

    @Override
    public void reproduzirMp4(String nomeArquivo) {
    }
}
