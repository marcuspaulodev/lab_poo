package com.labpoo.builder;

public interface ComputadorBuilder {
    ComputadorBuilder setCpu(String cpu);
    ComputadorBuilder setRam(String ram);
    ComputadorBuilder setArmazenamento(String armazenamento);
    ComputadorBuilder setPlacaDeVideo(String placaDeVideo);
    ComputadorBuilder setFonte(String fonte);
    Computador build();
}
