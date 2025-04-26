/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1.database;

import java.sql.*;
import javaapplication1.Ticket;
import javaapplication1.exceptions.DataPersistException;

public class TicketDAO {
    // === INICIO CAMBIO === (Método para crear tickets)
    public void crearTicket(Ticket ticket) throws DataPersistException {
        String sql = "INSERT INTO tickets (titulo, descripcion, estado) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, ticket.getTitulo());
            stmt.setString(2, ticket.getDescripcion());
            stmt.setString(3, ticket.getEstado());
            
            stmt.executeUpdate();
            
            // Obtener ID generado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    ticket.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DataPersistException("Error al guardar ticket: " + e.getMessage());
        }
    }
    // === FIN CAMBIO ===
}
