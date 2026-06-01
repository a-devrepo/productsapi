package com.nca.productsapi.dtos;

public record ProdutoRequestDTO(
        String nome,
        String descricao,
        Double preco,
        Integer quantidade
) {
}
