/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1.database;

/**
 *
 * @author ag045
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javaapplication1.Ticket;
import javaapplication1.exceptions.DataLoadException;

public class TicketDAO {
    // Método para cargar todos los tickets (usado en TicketListController)
    public List<Ticket> getAllTickets() throws DataLoadException {
        String sql = "SELECT * FROM tickets";
        List<Ticket> tickets = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                tickets.add(new Ticket(
                    rs.getString("id"),
                    rs.getString("titulo"),
                    rs.getString("estado"),
                    rs.getString("fecha_creacion"),
                    rs.getString("descripcion")
                ));
            }
            
            return tickets;
            
        } catch (SQLException e) {
            throw new DataLoadException("Error al cargar tickets: " + e.getMessage());
        }
    }

    // Método para crear un ticket (usado en NewTicketController)
    public void createTicket(Ticket ticket, int usuarioId) throws SQLException {
        String sql = "INSERT INTO tickets (id, titulo, descripcion, usuario_id, departamento, prioridad) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, ticket.getId());
            stmt.setString(2, ticket.getTitle());
            stmt.setString(3, ticket.getDescription());
            stmt.setInt(4, usuarioId); // ID del usuario logueado
            stmt.setString(5, "Soporte Técnico"); // Ejemplo, ajusta según tu ComboBox
            stmt.setString(6, "Alta"); // Ejemplo
            
            stmt.executeUpdate();
        }
    }
     public Ticket getTicketById(String id) throws SQLException {
        String sql = """
            SELECT t.*, u.nombre AS usuario_nombre, d.nombre AS departamento_nombre
            FROM tickets t
            JOIN usuarios u ON t.usuario_id = u.id
            JOIN departamentos d ON t.departamento_id = d.id
            WHERE t.id = ?
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Ticket ticket = new Ticket(
                        rs.getString("id"),
                        rs.getString("titulo"),
                        rs.getString("estado"),
                        rs.getString("fecha_creacion"),
                        rs.getString("descripcion")
                    );
                    ticket.setUsuarioNombre(rs.getString("usuario_nombre"));
                    ticket.setDepartamentoNombre(rs.getString("departamento_nombre"));
                    return ticket;
                }
            }
        }
        return null;
    }
     
     public void updateTicketStatus(String ticketId, String newStatus, int userId) throws SQLException {
        String sqlUpdate = "UPDATE tickets SET estado = ? WHERE id = ?";
        String sqlHistory = """
            INSERT INTO historial_estados (ticket_id, estado_anterior, estado_nuevo, usuario_id)
            SELECT id, estado, ?, ? FROM tickets WHERE id = ?
            """;
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate);
                 PreparedStatement stmtHistory = conn.prepareStatement(sqlHistory)) {
                
                // 1. Registrar en historial
                stmtHistory.setString(1, newStatus);
                stmtHistory.setInt(2, userId);
                stmtHistory.setString(3, ticketId);
                stmtHistory.executeUpdate();
                
                // 2. Actualizar ticket
                stmtUpdate.setString(1, newStatus);
                stmtUpdate.setString(2, ticketId);
                stmtUpdate.executeUpdate();
                
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
     
}
