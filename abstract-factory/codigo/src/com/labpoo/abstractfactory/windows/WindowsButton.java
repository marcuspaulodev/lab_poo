package com.labpoo.abstractfactory.windows;

import com.labpoo.abstractfactory.Button;

public class WindowsButton implements Button {

    @Override
    public void render() {
        System.out.println("[Windows] Renderizando botao retangular, estilo Fluent (cinza claro).");
    }

    @Override
    public void onClick() {
        System.out.println("[Windows] Botao clicado -> som de clique do Windows.");
    }
}
