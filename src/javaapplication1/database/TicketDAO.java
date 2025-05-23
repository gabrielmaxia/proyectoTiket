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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javaapplication1.Ticket;
import static javaapplication1.database.DatabaseConnection.getConnection;
import javaapplication1.models.Usuario;
import javaapplication1.utils.SessionManager;

public class TicketDAO {
    // Método para cargar todos los tickets (usado en TicketListController)
   public List<Ticket> getAllTickets() throws SQLException {
    List<Ticket> tickets = new ArrayList<>();
    String sql = """
        SELECT 
            id, 
            titulo as title, 
            estado as status, 
            TO_CHAR(fecha_creacion, 'YYYY-MM-DD HH24:MI') as date,
            descripcion
        FROM tickets
        """;
    
    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        
        while (rs.next()) {
            Ticket ticket = new Ticket(
                rs.getString("id"),
                rs.getString("title"),
                rs.getString("status"),
                rs.getString("date"),
                rs.getString("descripcion")
            );
            tickets.add(ticket);
        }
    }
    return tickets;
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
    Ticket ticket = getTicketById(ticketId);
    String rol = new UsuarioDAO().getUserById(userId).getRol(); // Obtiene el rol del usuario
    
    // Usa el método polimórfico
    Usuario usuarioActual = SessionManager.getCurrentUser(); // Obtén el usuario logueado
ticket.aplicarCambioEstado(usuarioActual, newStatus); 
         
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
     public void updateTicketDescription(String ticketId, String newDescription) throws SQLException {
    String sql = "UPDATE tickets SET descripcion = ? WHERE id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, newDescription);
        stmt.setString(2, ticketId);
        stmt.executeUpdate();
    }
}
     
 
public Map<String, Object> generarReporteConsolidado() {
    Map<String, Object> reporte = new HashMap<>();
    
    try (Connection conn = DatabaseConnection.getConnection()) {
        // 1. Total tickets (siempre con valor por defecto)
        reporte.put("totalTickets", getTotalTickets(conn));
        
        // 2. Tickets resueltos (lista vacía si hay error)
        List<Ticket> tickets = getTicketsResueltos(conn, 7);
        reporte.put("ultimosTicketsResueltos", tickets != null ? tickets : new ArrayList<>());
        
    } catch (SQLException e) {
        System.err.println("Error en generarReporteConsolidado: " + e.getMessage());
        // Valores por defecto seguros
        reporte.put("totalTickets", 0);
        reporte.put("ultimosTicketsResueltos", new ArrayList<>());
    }
    return reporte;
}

// Método auxiliar para contar tickets (similar a getTotalTickets)
private int getTotalTickets(Connection conn) throws SQLException {
    String sql = "SELECT COUNT(*) as total FROM tickets";
    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        return rs.next() ? rs.getInt("total") : 0;
    }
}

// Mantén este método tal cual (se usa en la tabla)
private List<Ticket> getTicketsResueltos(Connection conn, int dias) throws SQLException {
    List<Ticket> tickets = new ArrayList<>();
    String sql = """
        SELECT t.id, t.titulo, t.fecha_creacion, d.nombre as departamento 
        FROM tickets t
        JOIN departamentos d ON t.departamento_id = d.id
        WHERE t.estado = 'Resuelto'
        ORDER BY t.fecha_creacion DESC
        LIMIT 5
        """;
    
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            Ticket ticket = new Ticket(
                rs.getString("id"),
                rs.getString("titulo"),
                "Resuelto",
                rs.getString("fecha_creacion"),
                ""
            );
            ticket.setDepartamentoNombre(rs.getString("departamento"));
            tickets.add(ticket);
        }
    }
    return tickets;
}
    // Métodos auxiliares privados
    private Map<String, Integer> getEstadisticas(Connection conn, String sql) throws SQLException {
        Map<String, Integer> stats = new HashMap<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                stats.put(rs.getString("clave"), rs.getInt("valor"));
            }
        }
        return stats;
    }

    
public int getTotalTickets() throws SQLException {
    String sql = "SELECT COUNT(*) as total FROM tickets";
    try (Connection conn = getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        return rs.next() ? rs.getInt("total") : 0;
    }
}



public List<Ticket> getUltimosTicketsResueltos(int limite) throws SQLException {
    List<Ticket> tickets = new ArrayList<>();
    String sql = """
        SELECT t.id, t.titulo, t.estado, t.fecha_creacion, 
               d.nombre as departamento_nombre
        FROM tickets t
        JOIN departamentos d ON t.departamento_id = d.id
        WHERE t.estado = 'Resuelto'
        ORDER BY t.fecha_creacion DESC 
        LIMIT ?
        """;
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, limite);
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Ticket ticket = new Ticket(
                    rs.getString("id"),
                    rs.getString("titulo"),
                    rs.getString("estado"),
                    rs.getString("fecha_creacion"),
                    ""
                );
                ticket.setDepartamentoNombre(rs.getString("departamento_nombre"));
                tickets.add(ticket);
            }
        }
    }
    return tickets;
}

public void createSimpleTicket(String titulo, String descripcion, String departamento, String prioridad) 
    throws SQLException {
    
    // 1. Validar datos obligatorios
    if (titulo == null || descripcion == null || departamento == null || prioridad == null) {
        throw new SQLException("Todos los campos son obligatorios");
    }

    // 2. Obtener departamento_id (seguro)
    Integer departamentoId = null;
    String sqlSelect = "SELECT id FROM departamentos WHERE nombre = ?";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sqlSelect)) {
         
        stmt.setString(1, departamento);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            departamentoId = rs.getInt("id");
        } else {
            throw new SQLException("Departamento no encontrado: " + departamento);
        }
    }
    
    // 3. Obtener usuario_id desde la sesión (ejemplo)
    int usuarioId = SessionManager.getCurrentUser().getId(); // Ajusta según tu SessionManager
    String ticketId = generarNuevoTicketId();

    // 4. Insertar ticket
    String sqlInsert = """
        INSERT INTO tickets (id, titulo, descripcion, departamento_id, prioridad, estado, fecha_creacion, usuario_id)
        VALUES (?, ?, ?, ?, ?, 'Abierto', NOW(), ?)
        """;
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {
        
        
        stmt.setString(1, ticketId);
        stmt.setString(2, titulo);
        stmt.setString(3, descripcion);
        stmt.setInt(4, departamentoId);
        stmt.setString(5, prioridad);
        stmt.setInt(6, usuarioId); // <- Añadido usuario_id
        
        int affectedRows = stmt.executeUpdate();
        
        if (affectedRows == 0) {
            throw new SQLException("No se pudo crear el ticket");
        }
    }
}

public boolean cambiarEstado(int ticketId, String nuevoEstado, String estadoActual) throws SQLException {
    String sql = "SELECT 1 FROM flujos_trabajo WHERE estado_actual = ? AND estado_siguiente = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, estadoActual);
        stmt.setString(2, nuevoEstado);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return false; // Transición no permitida
        }
    }
    
    // Si pasa la validación, actualiza el estado
    sql = "UPDATE tickets SET estado = ? WHERE id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, nuevoEstado);
        stmt.setInt(2, ticketId);
        return stmt.executeUpdate() > 0;
    }
    
    
}
     
public List<Ticket> obtenerTicketsPorDepartamento(int departamentoId, String estadoFiltro, String prioridadFiltro) throws SQLException {
    List<Ticket> tickets = new ArrayList<>();
    StringBuilder sql = new StringBuilder("""
    SELECT t.*, d.nombre AS nombre_departamento
    FROM tickets t
    JOIN departamentos d ON t.departamento_id = d.id
    WHERE t.departamento_id = ?
    """);

    if (estadoFiltro != null && !estadoFiltro.isBlank()) {
        sql.append(" AND t.estado = ?");
    }

    if (prioridadFiltro != null && !prioridadFiltro.isBlank()) {
        sql.append(" AND t.prioridad = ?");
    }

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

        int paramIndex = 1;
        stmt.setInt(paramIndex++, departamentoId);

        if (estadoFiltro != null && !estadoFiltro.isBlank()) {
            stmt.setString(paramIndex++, estadoFiltro);
        }

        if (prioridadFiltro != null && !prioridadFiltro.isBlank()) {
            stmt.setString(paramIndex++, prioridadFiltro);
        }

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Ticket ticket = new Ticket(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descripcion"),
                    rs.getString("estado"),
                    rs.getString("prioridad"),
                    rs.getInt("usuario_id"),
                    rs.getInt("departamento_id")
                );
                ticket.setDepartamentoNombre(rs.getString("nombre_departamento"));
                ticket.setDate(rs.getString("fecha_creacion"));
                ticket.setFechaCierre(rs.getString("fecha_cierre"));
                tickets.add(ticket);
            }
        }
    }
    return tickets;
}


public void actualizarEstado(String ticketId, String nuevoEstado) throws SQLException {
    String sql = "UPDATE tickets SET estado = ? WHERE id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, nuevoEstado);
        stmt.setString(2, ticketId);
        stmt.executeUpdate();
    }
}
public String generarNuevoTicketId() throws SQLException {
    String sql = "SELECT id FROM tickets WHERE id LIKE 'TICK-%' ORDER BY id DESC LIMIT 1";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            String ultimoId = rs.getString("id"); // ej. "TICK-003"
            int numero = Integer.parseInt(ultimoId.substring(5)); // extrae el número
            return String.format("TICK-%03d", numero + 1);
        } else {
            return "TICK-001";
        }
    }
}


}
