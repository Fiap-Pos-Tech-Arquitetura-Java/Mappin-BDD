package br.com.fiap.postech.mappin.model;

import java.util.List;
import java.util.UUID;

public record Pedido(
        UUID id,
        UUID idCliente,
        Double valorTotal,
        String status,
        List<Item> itens
) {
}
