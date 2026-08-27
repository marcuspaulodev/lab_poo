package com.labpoo.abstractfactory.windows;

import com.labpoo.abstractfactory.Checkbox;

public class WindowsCheckbox implements Checkbox {

    private boolean checked = false;

    @Override
    public void render() {
        System.out.println("[Windows] Renderizando checkbox quadrado com marca em azul.");
    }

    @Override
    public void toggle() {
        checked = !checked;
        System.out.println("[Windows] Checkbox alternado para: " + (checked ? "marcado" : "desmarcado"));
    }
}
