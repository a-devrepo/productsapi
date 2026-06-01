package com.nca.productsapi.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Produto {

    private Integer id;
    private String nome;
    private String descricao;
    private Double preco;
    private Integer quantidade;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private LocalDateTime dataExclusao;
    private Integer ativo;
}