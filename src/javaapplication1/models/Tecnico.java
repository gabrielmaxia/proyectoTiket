/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1.models;

import javaapplication1.Ticket;

/**
 *
 * @author ag045
 */


public class Tecnico extends Usuario {
    private String departamento;

    public Tecnico(int id, String nombre, String email, String username, String password, String departamento) {
        super(id, nombre, email, username, password, "Tecnico");
        this.departamento = departamento;
    }

    @Override
    public void cambiarEstadoTicket(Ticket ticket, String nuevoEstado) {
        if ("Cerrado".equalsIgnoreCase(nuevoEstado)) {
            throw new IllegalArgumentException("Técnicos no pueden cerrar tickets");
        }
        ticket.setStatus(nuevoEstado);
    }

    // Getter/Setter
    public String getDepartamento() {
        return departamento;
    }
}