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
public class Administrador extends Usuario {
    public Administrador(int id, String nombre, String email, String username, String password) {
        super(id, nombre, email, username, password, "Administrador");
    }

    @Override
    public void cambiarEstadoTicket(Ticket ticket, String nuevoEstado) {
        ticket.setStatus(nuevoEstado); // Sin restricciones
    }
}