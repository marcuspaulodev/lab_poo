package com.labpoo.abstractfactory.windows;

import com.labpoo.abstractfactory.Button;
import com.labpoo.abstractfactory.Checkbox;
import com.labpoo.abstractfactory.GUIFactory;

public class WindowsFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}
