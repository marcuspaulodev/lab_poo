package com.labpoo.builder;

public class Computador {

    private final String cpu;
    private final String ram;
    private final String armazenamento;
    private final String placaDeVideo;
    private final String fonte;

    Computador(String cpu, String ram, String armazenamento, String placaDeVideo, String fonte) {
        this.cpu = cpu;
        this.ram = ram;
        this.armazenamento = armazenamento;
        this.placaDeVideo = placaDeVideo;
        this.fonte = fonte;
    }

    @Override
    public String toString() {
        return String.format(
                "Computador [cpu=%s, ram=%s, armazenamento=%s, placaDeVideo=%s, fonte=%s]",
                cpu, ram, armazenamento,
                placaDeVideo == null ? "integrada" : placaDeVideo,
                fonte);
    }
}
