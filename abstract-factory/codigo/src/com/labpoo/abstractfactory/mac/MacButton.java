package com.labpoo.abstractfactory.mac;

import com.labpoo.abstractfactory.Button;

public class MacButton implements Button {

    @Override
    public void render() {
        System.out.println("[Mac] Renderizando botao com cantos arredondados, estilo Aqua.");
    }

    @Override
    public void onClick() {
        System.out.println("[Mac] Botao clicado -> animacao suave de destaque.");
    }
}
