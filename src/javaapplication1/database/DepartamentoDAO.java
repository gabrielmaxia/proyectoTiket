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
        String sql = "SELECT * FROM departamentos WHERE activo = TRUE";
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
    
    // Asignar técnico a departamento
    public void asignarTecnico(int usuarioId, int departamentoId) throws SQLException {
        String sql = "INSERT INTO usuarios_departamentos (usuario_id, departamento_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, departamentoId);
            stmt.executeUpdate();
        }
    }
}
