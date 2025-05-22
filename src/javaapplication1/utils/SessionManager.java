/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1.utils;

/**
 *
 * @author ag045
 */
import javaapplication1.models.Usuario;

public class SessionManager {
    private static Usuario currentUser; // Usuario logueado actualmente

    // Método para iniciar sesión
    public static void login(Usuario user) {
        currentUser = user;
    }

    // Método para cerrar sesión
    public static void logout() {
        currentUser = null;
    }

    // Obtener el usuario actual
    public static Usuario getCurrentUser() {
        return currentUser;
    }

    // Verificar si hay sesión activa
    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    // Verificar si el usuario es admin
    public static boolean isAdmin() {
        return currentUser != null && currentUser.getRol().equals("admin");
    }

    // Verificar si el usuario es técnico
    public static boolean isTecnico() {
        return currentUser != null && currentUser.getRol().equals("tecnico");
    }
}
