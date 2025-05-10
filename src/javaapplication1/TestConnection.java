/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1;

/**
 *
 * @author ag045
 */

import javaapplication1.database.DatabaseConnection;
import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("Iniciando prueba de conexión...");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("✅ ¡Conexión exitosa a PostgreSQL!");
            System.out.println("URL utilizada: " + "DatabaseConnection.URL");
        } catch (Exception e) {
            System.err.println("❌ Error de conexión:");
            e.printStackTrace();
        }
    }
}