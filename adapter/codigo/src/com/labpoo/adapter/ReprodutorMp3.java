package com.labpoo.adapter;

public class ReprodutorMp3 implements ReprodutorMidia {

    @Override
    public void reproduzir(String tipoArquivo, String nomeArquivo) {
        if (tipoArquivo.equalsIgnoreCase("mp3")) {
            System.out.println("Tocando arquivo mp3: " + nomeArquivo);
        } else if (tipoArquivo.equalsIgnoreCase("vlc") || tipoArquivo.equalsIgnoreCase("mp4")) {
            ReprodutorMidia adaptador = new AdaptadorMidia(tipoArquivo);
            adaptador.reproduzir(tipoArquivo, nomeArquivo);
        } else {
            System.out.println("Formato nao suportado: " + tipoArquivo);
        }
    }
}
