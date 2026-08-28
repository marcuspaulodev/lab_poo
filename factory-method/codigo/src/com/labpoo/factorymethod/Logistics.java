package com.labpoo.factorymethod;

public abstract class Logistics {

    // Factory Method: cada subclasse decide QUAL Transport concreto usar.
    protected abstract Transport createTransport();

    public final void planDelivery() {
        System.out.println("Planejando entrega...");
        Transport transport = createTransport();
        transport.deliver();
        System.out.println("Entrega concluida.\n");
    }
}
