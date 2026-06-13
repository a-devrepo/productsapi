package com.nca.productsapi.repositories;

import com.nca.productsapi.entities.Produto;
import com.nca.productsapi.exceptions.RepositoryException;
import com.nca.productsapi.factories.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ProdutoRepository {

    public void inserir(Produto obj) throws Exception {

        var sql =
                """
                        INSERT INTO produtos(nome, descricao, preco, quantidade)
                          values(?,?,?,?)
                        """;

        try (var connection = ConnectionFactory.getConnection();
             var preparedStatement = connection.prepareStatement(sql);
        ) {

            preparedStatement.setString(1, obj.getNome());
            preparedStatement.setString(2, obj.getDescricao());
            preparedStatement.setDouble(3, obj.getPreco());
            preparedStatement.setDouble(4, obj.getQuantidade());

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RepositoryException("ERRO AO INSERIR DADOS: " + e.getMessage());
        }
    }

    public List<Produto> obterPorNome(String nome) throws Exception {

        if (nome == null || nome.isBlank()) {
            return Collections.emptyList();
        }

        nome = nome.trim();
        
        var sql =
                """
                        SELECT id, nome, descricao, preco, quantidade
                        from produtos
                                    WHERE  ativo = 1 AND nome ilike ?
                                    ORDER BY nome
                        """;

        try (var connection = ConnectionFactory.getConnection();
             var preparedStatement = connection.prepareStatement(sql);
        ) {

            preparedStatement.setString(1, "%" + nome + "%");

            try (var result = preparedStatement.executeQuery();) {
                var lista = new ArrayList<Produto>();

                while (result.next()) {

                    var produto = new Produto();

                    produto.setId(result.getInt("id"));
                    produto.setNome(result.getString("nome"));
                    produto.setDescricao(result.getString("descricao"));
                    produto.setPreco(result.getDouble("preco"));
                    produto.setQuantidade(result.getInt("quantidade"));

                    lista.add(produto);
                }
                return lista;
            }
        } catch (SQLException e) {
            throw new RepositoryException("ERRO AO OBTER DADOS: " + e.getMessage());
        }
    }

    public boolean excluir(Integer id) throws Exception {

        var sql = """
                    update produtos
                    set ativo = 0, data_exclusao = now()
                    where id = ?
                """;

        try (var connection = ConnectionFactory.getConnection();
             var preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, id);
            var result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RepositoryException("ERRO AO EXCLUIR DADOS: " + e.getMessage());
        }
    }

    public Produto obterPorId(Integer id) throws Exception {

        var sql = """
                    select id, nome, descricao, preco, quantidade
                    from produtos
                    where ativo = 1 and id = ?
                """;

        try (var connection = ConnectionFactory.getConnection();
             var preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setInt(1, id);

            try (var result = preparedStatement.executeQuery();) {
                Produto produto = null;

                if (result.next()) {
                    produto = new Produto();

                    produto.setId(result.getInt("id"));
                    produto.setNome(result.getString("nome"));
                    produto.setDescricao(result.getString("descricao"));
                    produto.setPreco(result.getDouble("preco"));
                    produto.setQuantidade(result.getInt("quantidade"));
                }
                return produto;

            }
        } catch (SQLException e) {
            throw new RepositoryException("ERRO AO OBTER POR ID: " + e.getMessage());
        }
    }

    public boolean atualizar(Produto obj) throws Exception {

        var sql = """
                    update produtos 
                    set nome = ?, descricao = ?, preco = ?, quantidade = ?, data_atualizacao = now()
                    where ativo = 1 and id = ?
                """;

        try (var connection = ConnectionFactory.getConnection();
             var preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setString(1, obj.getNome());
            preparedStatement.setString(2, obj.getDescricao());
            preparedStatement.setDouble(3, obj.getPreco());
            preparedStatement.setInt(4, obj.getQuantidade());
            preparedStatement.setInt(5, obj.getId());
            var result = preparedStatement.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            throw new RepositoryException("ERRO AO ATUALIZAR DADOS: " + e.getMessage());
        }
    }
}