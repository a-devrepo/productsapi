package com.nca.productsapi.repositories;

import com.nca.productsapi.entities.Produto;
import com.nca.productsapi.exceptions.RepositoryException;
import com.nca.productsapi.factories.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Produto> obterPorNome(String nome) throws Exception {

        try (var connection = ConnectionFactory.getConnection()) {

            var statement = connection.prepareStatement(
                    """
                            SELECT id, nome, descricao, preco, quantidade
                            from produtos
                                        WHERE nome ilike ?
                                        ORDER BY nome
                            """);

            statement.setString(1, "%" + nome + "%");

            var result = statement.executeQuery();

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
        } catch (SQLException e) {
            throw new RepositoryException("ERRO AO OBTER DADOS: " + e.getMessage());
        }
    }

    public boolean excluir(Integer id) throws Exception {

        try (var connection = ConnectionFactory.getConnection()) {
            var statement = connection.prepareStatement("""
                        update produtos 
                        set ativo = 0, data_exclusao = now()
                        where id = ?
                    """);

            statement.setInt(1, id);
            var result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RepositoryException("ERRO AO EXCLUIR DADOS: " + e.getMessage());
        }
    }

    public Produto obterPorId(Integer id) throws Exception {

        try (var connection = ConnectionFactory.getConnection()) {
            var statement = connection.prepareStatement("""
                        select id, nome, descricao, preco, quantidade
                        from produtos
                        where ativo = 1 and id = ?
                    """);

            statement.setInt(1, id);
            var result = statement.executeQuery();

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
        } catch (SQLException e) {
            throw new RepositoryException("ERRO AO OBTER POR ID: " + e.getMessage());
        }
    }

    public boolean atualizar(Produto obj) throws Exception {

        try (var connection = ConnectionFactory.getConnection()) {
            var statement = connection.prepareStatement("""
                        update produtos 
                        set nome = ?, descricao = ?, preco = ?, quantidade = ?, data_atualizacao = now()
                        where ativo = 1 and id = ?
                    """);

            statement.setString(1, obj.getNome());
            statement.setString(2, obj.getDescricao());
            statement.setDouble(3, obj.getPreco());
            statement.setInt(4, obj.getQuantidade());
            statement.setInt(5, obj.getId());
            var result = statement.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            throw new RepositoryException("ERRO AO ATUALIZAR DADOS: " + e.getMessage());
        }
    }
}