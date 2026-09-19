package com.labpoo.adapter;

public class ReprodutorMp4 implements ReprodutorAvancado {

    @Override
    public void reproduzirVlc(String nomeArquivo) {
    }

    @Override
    public void reproduzirMp4(String nomeArquivo) {
        System.out.println("Tocando arquivo mp4: " + nomeArquivo);
    }
}
