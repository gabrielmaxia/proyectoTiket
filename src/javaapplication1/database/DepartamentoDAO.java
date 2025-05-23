/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javaapplication1.models.Departamento;
import javaapplication1.exceptions.DataLoadException;

public class DepartamentoDAO {
    
    // Obtener todos los departamentos activos
    public List<Departamento> getAllDepartamentos() throws DataLoadException {
        String sql = "SELECT * FROM departamentos WHERE estado = TRUE";
        List<Departamento> departamentos = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                departamentos.add(new Departamento(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                ));
            }
            return departamentos;
            
        } catch (SQLException e) {
            throw new DataLoadException("Error al cargar departamentos: " + e.getMessage());
        }
    }
    
    public void asignarTecnico(int usuarioId, int departamentoId) throws SQLException {
        String sql = "INSERT INTO usuarios_departamentos (usuario_id, departamento_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, departamentoId);
            stmt.executeUpdate();
        }
    }
    
    public void addDepartamento(Departamento depto) throws SQLException {
        String sql = "INSERT INTO departamentos (nombre, descripcion) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
              PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, depto.getNombre());
            stmt.setString(2, depto.getDescripcion());
            stmt.executeUpdate();
            
            // Obtener el ID generado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    depto.setId(rs.getInt(1));
                }
            }
        }
    }

    public void updateDepartamento(Departamento depto) throws SQLException {
        String sql = "UPDATE departamentos SET nombre = ?, descripcion = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, depto.getNombre());
            stmt.setString(2, depto.getDescripcion());
            stmt.setInt(3, depto.getId());
            stmt.executeUpdate();
        }
    }

    public void deactivateDepartamento(int id) throws SQLException {
        String sql = "UPDATE departamentos SET estado = FALSE WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
