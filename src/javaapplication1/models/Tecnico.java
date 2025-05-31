/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1.models;

import java.util.List;
import javaapplication1.Ticket;
import javaapplication1.database.TicketDAO;
import java.sql.SQLException;

/**
 *
 * @author ag045
 */


public class Tecnico extends Usuario {
    private String departamento;
    private int departamentoId;
private String departamentoNombre;

    public Tecnico(int id, String nombre, String email, String username, 
               String password, String departamentoNombre) {
    super(id, nombre, email, username, password, "tecnico");
    this.departamentoNombre = departamentoNombre;
    // this.departamentoId puede asignarse después si es necesario
}
    public Tecnico(int id, String nombre, String email, String username, 
               String password, int departamentoId) {
    super(id, nombre, email, username, password, "tecnico");
    this.departamentoId = departamentoId;
   
}

@Override
public void cambiarEstadoTicket(Ticket ticket, String nuevoEstado) {
    String estadoActual = ticket.getStatus();

    if ("Cerrado".equalsIgnoreCase(nuevoEstado)) {
        throw new IllegalArgumentException("Técnicos no pueden cerrar tickets.");
    }

    if (!"Pendiente".equalsIgnoreCase(estadoActual) || !"En proceso".equalsIgnoreCase(nuevoEstado)) {
        throw new IllegalArgumentException("Solo puedes cambiar de 'Pendiente' a 'En proceso'. Estado actual: " + estadoActual);
    }

    ticket.setStatus(nuevoEstado);
    ticket.agregarAlHistorial("Cambio de estado por técnico: " + estadoActual + " → " + nuevoEstado);
}

public List<Ticket> obtenerTicketsPorDepartamento(int departamentoId, String estadoFiltro, String prioridadFiltro) throws SQLException {
    return new TicketDAO().obtenerTicketsPorDepartamento(departamentoId, estadoFiltro, prioridadFiltro);
}

    // Getter/Setter
    public String getDepartamento() {
        return departamento;
    }
    
public int getDepartamentoId() {
    return this.departamentoId; // Usa el campo que ya tienes en la clase
}
}