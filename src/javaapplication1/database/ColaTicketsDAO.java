/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1.database;

import java.sql.*;
import javaapplication1.Ticket;

public class ColaTicketsDAO {
    
    // Obtener el próximo ticket para un departamento
    public Ticket obtenerProximoTicket(int departamentoId) throws SQLException {
        String sql = "SELECT * FROM obtener_proximo_ticket(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, departamentoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String ticketId = rs.getString(1);
                    // Ahora obtenemos los detalles completos del ticket
                    TicketDAO ticketDAO = new TicketDAO();
                    return ticketDAO.getTicketById(ticketId);
                }
            }
        }
        return null;
    }
    
    // Agregar ticket a la cola 
    public void agregarACola(String ticketId, int departamentoId, String prioridad) throws SQLException {
        String sql = "INSERT INTO cola_tickets (departamento_id, ticket_id, prioridad) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, departamentoId);
            stmt.setString(2, ticketId);
            stmt.setString(3, prioridad);
            stmt.executeUpdate();
        }
    }
}