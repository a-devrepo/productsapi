package com.nca.productsapi.dtos;

import java.time.LocalDateTime;

public record ProdutoResponseDTO(
        Integer id,
        String nome,
        String descricao,
        Double preco,
        Integer quantidade,
        Double total
) {
}
