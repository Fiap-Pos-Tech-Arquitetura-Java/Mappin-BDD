package br.com.fiap.postech.mappin.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record Produto(
        UUID id,
        String nome,
        Integer quantidade,
        Double preco,
        LocalDateTime dataAtualizacao
) {
}
