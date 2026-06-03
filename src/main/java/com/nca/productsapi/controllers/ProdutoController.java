package com.nca.productsapi.controllers;

import com.nca.productsapi.dtos.ProdutoRequestDTO;
import com.nca.productsapi.dtos.ProdutoResponseDTO;
import com.nca.productsapi.entities.Produto;
import com.nca.productsapi.exceptions.RepositoryException;
import com.nca.productsapi.repositories.ProdutoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    private ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criar(@RequestBody ProdutoRequestDTO dto) {

        try {
            var produto = toModel(dto);
            repository.inserir(produto);

            return ResponseEntity.ok("Produto criado com sucesso");
        } catch (RepositoryException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/alterar")
    public ResponseEntity<?> alterar() {
        return ResponseEntity.ok("Produto alterado com sucesso");
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluir() {
        return ResponseEntity.ok("Produto excluído com sucesso");
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listar(String nome) {

        try {
            var lista = repository.obterPorNome(nome);
            var listaDTO = toDTOList(lista);
            return ResponseEntity.ok(listaDTO);

        } catch (RepositoryException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    private Produto toModel(ProdutoRequestDTO dto) {
        var produto = new Produto();
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setQuantidade(dto.quantidade());
        return produto;
    }

    private ProdutoResponseDTO toDTO(Produto produto) {
        var dto = new ProdutoResponseDTO(produto.getId(), produto.getNome(),
                produto.getDescricao(), produto.getPreco(), produto.getQuantidade(),
                produto.getQuantidade() * produto.getPreco());
        return dto;
    }

    private List<ProdutoResponseDTO> toDTOList(List<Produto> produtos) {
        return produtos.stream().map(this::toDTO).toList();
    }
}
