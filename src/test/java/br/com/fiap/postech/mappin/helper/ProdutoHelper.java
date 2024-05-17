package br.com.fiap.postech.mappin.helper;

import br.com.fiap.postech.mappin.model.Produto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProdutoHelper {
    public static Produto getProduto(boolean geraId) {
        UUID idProduto = null;
        if (geraId) {
            idProduto = UUID.randomUUID();
        }
        return new Produto(
                idProduto,
                "Geladeira de Cerveja",
                123,
                123d,
                LocalDateTime.now()
        );
    }
}
