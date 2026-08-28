package com.labpoo.abstractfactory;

import com.labpoo.abstractfactory.mac.MacFactory;
import com.labpoo.abstractfactory.windows.WindowsFactory;

public class Main {

    public static void main(String[] args) {
        String os = args.length > 0 ? args[0] : System.getProperty("os.name", "windows");

        GUIFactory factory = os.toLowerCase().contains("mac")
                ? new MacFactory()
                : new WindowsFactory();

        System.out.println("Sistema operacional: " + os);
        System.out.println("Factory escolhida: " + factory.getClass().getSimpleName());
        System.out.println();

        Application app = new Application(factory);
        app.render();
        app.simulateUserInteraction();
    }
}
