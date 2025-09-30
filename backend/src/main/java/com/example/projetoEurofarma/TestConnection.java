package com.example.projetoEurofarma;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/eurofarma_db?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "Mendel07@";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("✅ Conexão bem-sucedida!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
