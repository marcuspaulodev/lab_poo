package com.labpoo.abstractfactory;

public class Application {

    private final Button button;
    private final Checkbox checkbox;

    public Application(GUIFactory factory) {
        this.button = factory.createButton();
        this.checkbox = factory.createCheckbox();
    }

    public void render() {
        button.render();
        checkbox.render();
    }

    public void simulateUserInteraction() {
        button.onClick();
        checkbox.toggle();
    }
}
