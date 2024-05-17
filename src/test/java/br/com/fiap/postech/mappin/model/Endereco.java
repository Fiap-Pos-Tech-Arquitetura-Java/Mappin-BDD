package br.com.fiap.postech.mappin.model;

import java.util.UUID;

public record Endereco(
        UUID id,
        String rua,
        String numero,
        String cep,
        String cidade
) {
}
