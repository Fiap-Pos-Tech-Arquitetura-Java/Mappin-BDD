package br.com.fiap.postech.mappin.helper;

import br.com.fiap.postech.mappin.model.Item;
import br.com.fiap.postech.mappin.model.Pedido;

import java.util.List;
import java.util.UUID;

public class PedidoHelper {
    public static Pedido getPedido(boolean geraId) {
        return getPedido(geraId, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
    }

    public static Pedido getPedido(boolean geraId, UUID idCliente, UUID primeiroProduto, UUID segundoProduto) {
        UUID idPedido = null;
        if (geraId) {
            idPedido = UUID.randomUUID();
        }
        return new Pedido(
                idPedido,
                idCliente,
                null,
                null,
                List.of(
                        new Item(UUID.randomUUID(), primeiroProduto,3, null),
                        new Item(UUID.randomUUID(), segundoProduto,5, null)
                )
        );
    }
}
