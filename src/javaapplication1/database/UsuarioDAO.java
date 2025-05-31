/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javaapplication1.exceptions.DataLoadException;
import javaapplication1.models.Administrador;
import javaapplication1.models.Tecnico;
import javaapplication1.models.Usuario;
import javax.naming.AuthenticationException;

public class UsuarioDAO {
    // Método para autenticar un usuario (usado en LoginController)
    public Usuario authenticate(String username, String password) throws AuthenticationException {
   String sql = """
    SELECT u.*, d.nombre AS departamento
    FROM usuarios u
    LEFT JOIN usuarios_departamentos ud ON u.id = ud.usuario_id
    LEFT JOIN departamentos d ON ud.departamento_id = d.id
    WHERE u.username = ? AND u.password = ?
""";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, username.trim());
        stmt.setString(2, password.trim());
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String rol = rs.getString("rol").trim().toLowerCase();
                
                switch (rol) {
                    case "tecnico":
                        return new Tecnico(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            username,
                            password,
                            rs.getString("departamento")
                        );
                    case "admin": // Cambiado de "administrador" a "admin"
                        return new Administrador(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            username,
                            password
                        );
                    default:
                        throw new AuthenticationException("Rol no válido. Se recibió: " + rol);
                }
            }
        }
        throw new AuthenticationException("Usuario o contraseña incorrectos");
    } catch (SQLException e) {
        throw new AuthenticationException("Error de BD: " + e.getMessage());
    }
}


    // Métodos adicionales (registro, actualización, etc.)
    public void addUser(Usuario usuario) throws SQLException {
    Connection conn = null;
    PreparedStatement stmtUsuario = null;
    PreparedStatement stmtRelacion = null;
    
    try {
        conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false); // Iniciar transacción

        // 1. Insertar usuario SIN especificar el ID (dejando que la secuencia lo genere)
        String sqlUsuario = "INSERT INTO usuarios (nombre, email, username, password, rol, activo, fecha_creacion) " +
                          "VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        stmtUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
        
        stmtUsuario.setString(1, usuario.getNombre());
        stmtUsuario.setString(2, usuario.getEmail());
        stmtUsuario.setString(3, usuario.getUsername());
        stmtUsuario.setString(4, usuario.getPassword());
        stmtUsuario.setString(5, usuario.getRol());
        stmtUsuario.setBoolean(6, true);
        
        int affectedRows = stmtUsuario.executeUpdate();
        
        if (affectedRows == 0) {
            throw new SQLException("No se pudo crear el usuario, ninguna fila afectada");
        }

        // Obtener el ID generado automáticamente
        try (ResultSet rs = stmtUsuario.getGeneratedKeys()) {
            if (rs.next()) {
                usuario.setId(rs.getInt(1)); // Asignar el ID generado
            } else {
                throw new SQLException("No se pudo obtener el ID del usuario creado");
            }
        }

        // 2. Si es técnico, insertar la relación con departamento
        if (usuario instanceof Tecnico) {
            Tecnico tecnico = (Tecnico) usuario;
            String sqlRelacion = "INSERT INTO usuarios_departamentos (usuario_id, departamento_id, fecha_asignacion) " +
                               "VALUES (?, ?, CURRENT_TIMESTAMP)";
            
            stmtRelacion = conn.prepareStatement(sqlRelacion);
            stmtRelacion.setInt(1, usuario.getId());
            stmtRelacion.setInt(2, tecnico.getDepartamentoId());
            stmtRelacion.executeUpdate();
        }

        conn.commit(); // Confirmar transacción
    } catch (SQLException e) {
        if (conn != null) {
            conn.rollback(); // Revertir en caso de error
        }
        throw e;
    } finally {
        if (stmtRelacion != null) stmtRelacion.close();
        if (stmtUsuario != null) stmtUsuario.close();
        if (conn != null) {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
}

    public void updateUser(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, email = ?, username = ?, password = ?, rol = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getUsername());
            stmt.setString(4, usuario.getPassword());
            stmt.setString(5, usuario.getRol());
            stmt.setInt(6, usuario.getId());
            
            stmt.executeUpdate();
        }
    }
    public void deactivateUser(int id) throws SQLException {
        String sql = "UPDATE usuarios SET estado = FALSE WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    } 
    public List<Usuario> getAllUsers() throws DataLoadException {
    String sql = "SELECT * FROM usuarios WHERE estado = TRUE";
    List<Usuario> users = new ArrayList<>();
    
    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        
        while (rs.next()) {
            String rol = rs.getString("rol").toLowerCase();
            
            // Crear instancia según el rol (polimorfismo)
            if ("tecnico".equals(rol)) {
                users.add(new Tecnico(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("departamento") // Asegúrate que este campo existe
                ));
            } else {
                users.add(new Administrador(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("password")
                ));
            }
        }
        return users;
        
    } catch (SQLException e) {
        throw new DataLoadException("Error al cargar usuarios: " + e.getMessage());
    }
}
    public int obtenerDepartamentoId(int usuarioId) throws SQLException {
    String sql = "SELECT departamento_id FROM usuarios_departamentos WHERE usuario_id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, usuarioId);
        ResultSet rs = stmt.executeQuery();
        return rs.next() ? rs.getInt(1) : -1;
    }
}
}
