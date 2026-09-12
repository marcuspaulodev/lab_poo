package com.labpoo.singleton;

public class ServicoPagamentos {

    public void confirmarPagamento(String id) {
        Logger.getInstance().log("Pagamento do pedido " + id + " confirmado.");
    }
}
