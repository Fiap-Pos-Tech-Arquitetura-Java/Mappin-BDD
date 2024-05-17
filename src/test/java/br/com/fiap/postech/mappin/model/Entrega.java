package br.com.fiap.postech.mappin.model;

import java.util.List;
import java.util.UUID;

public record Entrega(
        UUID id,
        String cpfEntregador,
        String cepRaiz,
        String status,
        List<Pedido> pedidos
) {
}
