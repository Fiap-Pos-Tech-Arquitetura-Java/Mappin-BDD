package br.com.fiap.postech.mappin.model;

import java.util.UUID;

public record Cliente(
        UUID id,
        String nome,
        String cpf,
        Endereco endereco
) {
}
