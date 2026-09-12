package com.labpoo.builder;

public class ComputadorBuilderPadrao implements ComputadorBuilder {

    private String cpu;
    private String ram;
    private String armazenamento;
    private String placaDeVideo;
    private String fonte;

    @Override
    public ComputadorBuilder setCpu(String cpu) {
        this.cpu = cpu;
        return this;
    }

    @Override
    public ComputadorBuilder setRam(String ram) {
        this.ram = ram;
        return this;
    }

    @Override
    public ComputadorBuilder setArmazenamento(String armazenamento) {
        this.armazenamento = armazenamento;
        return this;
    }

    @Override
    public ComputadorBuilder setPlacaDeVideo(String placaDeVideo) {
        this.placaDeVideo = placaDeVideo;
        return this;
    }

    @Override
    public ComputadorBuilder setFonte(String fonte) {
        this.fonte = fonte;
        return this;
    }

    @Override
    public Computador build() {
        if (cpu == null || ram == null || armazenamento == null || fonte == null) {
            throw new IllegalStateException("cpu, ram, armazenamento e fonte sao obrigatorios");
        }
        return new Computador(cpu, ram, armazenamento, placaDeVideo, fonte);
    }
}
