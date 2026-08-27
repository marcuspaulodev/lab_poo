package com.labpoo.abstractfactory.mac;

import com.labpoo.abstractfactory.Checkbox;

public class MacCheckbox implements Checkbox {

    private boolean checked = false;

    @Override
    public void render() {
        System.out.println("[Mac] Renderizando checkbox arredondado com marca em preto.");
    }

    @Override
    public void toggle() {
        checked = !checked;
        System.out.println("[Mac] Checkbox alternado para: " + (checked ? "marcado" : "desmarcado"));
    }
}
