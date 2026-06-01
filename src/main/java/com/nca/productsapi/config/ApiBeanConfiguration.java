package com.nca.productsapi.config;

import com.nca.productsapi.repositories.ProdutoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiBeanConfiguration {

    @Bean
    public ProdutoRepository produtoRepository(){
        return new ProdutoRepository();
    }
}
