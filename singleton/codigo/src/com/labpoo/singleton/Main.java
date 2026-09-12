package com.labpoo.singleton;

public class Main {

    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        System.out.println("logger1 == logger2? " + (logger1 == logger2));
        System.out.println();

        ServicoPedidos pedidos = new ServicoPedidos();
        ServicoPagamentos pagamentos = new ServicoPagamentos();

        pedidos.processarPedido("1001");
        pagamentos.confirmarPagamento("1001");
        pedidos.processarPedido("1002");

        System.out.println();
        System.out.println("Historico completo (visto a partir de logger1):");
        logger1.getHistorico().forEach(System.out::println);

        System.out.println();
        System.out.println("Tamanho do historico visto a partir de logger2: "
                + logger2.getHistorico().size());
    }
}
