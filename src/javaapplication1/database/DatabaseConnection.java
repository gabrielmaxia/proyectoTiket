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
    // === DATOS ACTUALIZADOS CON TU CONFIGURACIÓN DE RENDER ===
    private static final String URL = "jdbc:postgresql://dpg-d060g6idbo4c73e37ss0-a.oregon-postgres.render.com:5432/tiketbase";
    private static final String USER = "tiketbase_user";
    private static final String PASSWORD = "FQj0padJz85XDtfNvwz1CClROM1WxbGW";

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