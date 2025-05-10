/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ag045
 */
package javaapplication1.models;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String username;
    private String password;
    private String rol;

    // Constructor
    public Usuario(int id, String nombre, String email, String username, String password, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    // Getters y Setters (métodos completos)
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    // ... (agrega los demás getters y setters)

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
}
