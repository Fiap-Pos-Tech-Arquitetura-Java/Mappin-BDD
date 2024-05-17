package br.com.fiap.postech.mappin.model;

import java.util.UUID;

public record Item(
        UUID id,
        UUID idProduto,
        Integer quantidade,
        Double valorProduto
) {
}
