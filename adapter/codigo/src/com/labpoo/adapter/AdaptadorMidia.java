package com.labpoo.adapter;

public class AdaptadorMidia implements ReprodutorMidia {

    private final ReprodutorAvancado reprodutorAvancado;

    public AdaptadorMidia(String tipoArquivo) {
        if (tipoArquivo.equalsIgnoreCase("vlc")) {
            reprodutorAvancado = new ReprodutorVlc();
        } else if (tipoArquivo.equalsIgnoreCase("mp4")) {
            reprodutorAvancado = new ReprodutorMp4();
        } else {
            throw new IllegalArgumentException("Formato nao suportado: " + tipoArquivo);
        }
    }

    @Override
    public void reproduzir(String tipoArquivo, String nomeArquivo) {
        if (tipoArquivo.equalsIgnoreCase("vlc")) {
            reprodutorAvancado.reproduzirVlc(nomeArquivo);
        } else if (tipoArquivo.equalsIgnoreCase("mp4")) {
            reprodutorAvancado.reproduzirMp4(nomeArquivo);
        }
    }
}
