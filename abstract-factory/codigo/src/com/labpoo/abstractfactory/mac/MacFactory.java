package com.labpoo.abstractfactory.mac;

import com.labpoo.abstractfactory.Button;
import com.labpoo.abstractfactory.Checkbox;
import com.labpoo.abstractfactory.GUIFactory;

public class MacFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
}
