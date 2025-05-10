/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javaapplication1.database.DatabaseConnection;
import javaapplication1.exceptions.DataLoadException;
import javaapplication1.models.Usuario;
import javax.naming.AuthenticationException;

public class UsuarioDAO {
    // Método para autenticar un usuario (usado en LoginController)
    public Usuario authenticate(String username, String password) throws AuthenticationException {
        
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username); // Asigna solo el username
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Verifica la contraseña (sin cifrado para pruebas)
                String dbPassword = rs.getString("password");
                if (dbPassword.equals(password)) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("username"),
                        dbPassword,
                        rs.getString("rol")
                    );
                } else {
                    throw new AuthenticationException("Contraseña incorrecta");
                }
            } else {
                throw new AuthenticationException("Usuario no encontrado");
            }
            
        } catch (SQLException e) {
            throw new AuthenticationException("Error de base de datos: " + e.getMessage());
        }
    }


    // Métodos adicionales (registro, actualización, etc.)
    public void addUser(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, email, username, password, rol) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getUsername());
            stmt.setString(4, usuario.getPassword()); // Debería estar cifrada
            stmt.setString(5, usuario.getRol());
            
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
        }
    }
}
    public Usuario getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("rol")
                    );
                }
            }
        }
        return null;
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
        String sql = "UPDATE usuarios SET activo = FALSE WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    } 
    public List<Usuario> getAllUsers() throws DataLoadException {
        String sql = "SELECT * FROM usuarios WHERE activo = TRUE";
        List<Usuario> users = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                users.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("rol")
                ));
            }
            return users;
            
        } catch (SQLException e) {
            throw new DataLoadException("Error al cargar usuarios: " + e.getMessage());
        }
    }
}
