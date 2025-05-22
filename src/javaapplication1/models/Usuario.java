/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ag045
 */
package javaapplication1.models;

import javaapplication1.Ticket;

public abstract class Usuario extends Persona{
    
    private String username;
    private String password;
    private String rol;

    // Constructor
    public Usuario(int id, String nombre, String email, String username, String password, String rol) {
        super(id, nombre, email); // Llama al constructor de Persona
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    // Getters y Setters (se mantienen igual)
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRol() { return rol; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRol(String rol) { this.rol = rol; }
    
    public abstract void cambiarEstadoTicket(Ticket ticket, String nuevoEstado);
}


