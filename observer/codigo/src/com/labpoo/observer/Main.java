package com.labpoo.observer;

public class Main {

    public static void main(String[] args) {
        EstacaoMeteorologica estacao = new EstacaoMeteorologica();
        PainelTemperatura painel = new PainelTemperatura();
        HistoricoTemperaturas historico = new HistoricoTemperaturas();

        estacao.adicionar(painel);
        estacao.adicionar(new AlertaCalor(35));
        estacao.adicionar(historico);

        estacao.medir(28);
        estacao.medir(36);

        System.out.println("Painel removido da lista de observadores");
        estacao.remover(painel);
        estacao.medir(31);

        System.out.println("Histórico completo: " + historico.registros());
    }
}
