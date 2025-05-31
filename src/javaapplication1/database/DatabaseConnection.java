/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ag045
 */
package javaapplication1.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static final String URL = "jdbc:postgresql://ep-floral-river-a8hadh0h-pooler.eastus2.azure.neon.tech:5432/neondb";
    private static final String USER = "neondb_owner";
    private static final String PASSWORD = "npg_uiOLCwQYa9d2";

    static {
        try {
            // === REGISTRO EXPLÍCITO DEL DRIVER ===
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver JDBC de PostgreSQL", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}