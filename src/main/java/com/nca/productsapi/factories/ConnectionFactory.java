package com.nca.productsapi.factories;

import com.nca.productsapi.exceptions.InfrastructureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionFactory {

    @Value("${spring.datasource.url}")
    private String URL;

    @Value("${spring.datasource.username}")
    private String USER = "user_productsapi";

    @Value("${spring.datasource.password}")
    private String PASSWORD = "pass_productsapi";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );
        } catch (SQLException e) {
            throw new InfrastructureException(
                    "ERRO AO CONECTAR COM O BANCO DE DADOS: " + e.getMessage()
            );
        }
    }
}
