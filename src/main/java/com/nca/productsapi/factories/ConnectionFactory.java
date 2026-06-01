package com.nca.productsapi.factories;

import com.nca.productsapi.exceptions.InfrastructureException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL =
            "jdbc:postgresql://localhost:5435/bd_productsapi";

    private static final String USER = "user_productsapi";
    private static final String PASSWORD = "pass_productsapi";

    public static Connection getConnection() {
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
