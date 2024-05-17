package br.com.fiap.postech.mappin.helper;

import br.com.fiap.postech.mappin.model.Entrega;
import br.com.fiap.postech.mappin.model.Pedido;

import java.util.List;
import java.util.UUID;

public class EntregaHelper {


    public static Entrega getEntrega(boolean geraId, UUID idPrimeiroPedido, UUID idSegundoPedido) {
        var pedidos = List.of(
                new Pedido(idPrimeiroPedido,null,null,null,null),
                new Pedido(idSegundoPedido,null,null,null,null)
        );
        UUID idEntrega = null;
        if (geraId) {
            idEntrega = UUID.randomUUID();
        }
        return new Entrega(
                idEntrega,
                "47978839010",
                "95880",
                "AGUARDANDO_ENTREGA",
                pedidos
        );
    }
}
