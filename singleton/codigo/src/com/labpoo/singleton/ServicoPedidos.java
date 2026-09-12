package com.labpoo.singleton;

public class ServicoPedidos {

    public void processarPedido(String id) {
        Logger.getInstance().log("Pedido " + id + " processado.");
    }
}
