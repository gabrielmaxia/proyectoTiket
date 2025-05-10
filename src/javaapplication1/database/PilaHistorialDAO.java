/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1.database;

import java.sql.*;
import javaapplication1.exceptions.DataLoadException;

public class PilaHistorialDAO {
    
    // Deshacer último cambio de estado
    public boolean deshacerUltimoCambio(String ticketId) throws SQLException {
        String sql = "SELECT * FROM deshacer_ultimo_cambio(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, ticketId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        }
        return false;
    }
    
    // Obtener todos los cambios de un ticket (para mostrar historial)
    public void obtenerHistorialCompleto(String ticketId) throws DataLoadException {
        // Implementación similar usando SELECT * FROM historial_estados...
    }
}
