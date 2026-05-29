package com.nca.productsapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    @PostMapping("/criar")
    public ResponseEntity<?> criar(){
        return ResponseEntity.ok("Produto criado com sucesso");
    }

    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(){
        return ResponseEntity.ok("Produto alterado com sucesso");
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluir(){
        return ResponseEntity.ok("Produto excluído com sucesso");
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok("Lista de produtos obtida com sucesso");
    }
}
