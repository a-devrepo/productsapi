package com.nca.productsapi.repositories;

import com.nca.productsapi.entities.Produto;
import com.nca.productsapi.exceptions.RepositoryException;
import com.nca.productsapi.factories.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class ProdutoRepository {

    public void inserir(Produto obj) throws Exception {

        try (var connection = ConnectionFactory.getConnection()) {

            var statement = connection.prepareStatement(
                    """
                            INSERT INTO produtos(nome, descricao, preco, quantidade)
                              values(?,?,?,?)
                            """);

            statement.setString(1, obj.getNome());
            statement.setString(2, obj.getDescricao());
            statement.setDouble(3, obj.getPreco());
            statement.setDouble(4, obj.getQuantidade());

            statement.execute();

        } catch (SQLException e) {
            throw new RepositoryException("ERRO AO INSERIR DADOS: " + e.getMessage());
        }
    }
}