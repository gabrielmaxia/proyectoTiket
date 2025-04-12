/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javaapplication1.Controles;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javaapplication1.JavaApplication1;


//Implementación de POO: Creamos una clase base para manejo de autenticación
    abstract class AuthenticationHandler {
    public abstract boolean authenticate(String username, String password) throws AuthenticationException;
}

// Implementación de POO: Clase concreta para autenticación
class DatabaseAuthenticationHandler extends AuthenticationHandler {
    @Override
    public boolean authenticate(String username, String password) throws AuthenticationException {
        if(username == null || password == null) {
            throw new AuthenticationException("Credenciales no pueden ser nulas");
        }
        // Lógica de autenticación simulada (en producción conectaría a BD)
        return username.equals("admin") && password.equals("admin123");
    }
}

// Implementación de POO: Excepción personalizada
class AuthenticationException extends Exception {
    public AuthenticationException(String message) {
        super(message);
    }
}
public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    
    private AuthenticationHandler authHandler;
    public LoginController() {
        this.authHandler = new DatabaseAuthenticationHandler();
    }

    @FXML
    private void handleLogin() {
        try {
            // Implementación de POO: Validación de datos
            validateInputs();
            
            // Implementación de POO: Polimorfismo (llamada al método abstracto)
            if(authHandler.authenticate(usernameField.getText(), passwordField.getText())) {
                JavaApplication1.showDashboard();
            } else {
                showError("Credenciales incorrectas");
            }
        } catch (AuthenticationException e) {
            // Manejo de excepciones específicas
            showError("Error de autenticación: " + e.getMessage());
        } catch (Exception e) {
            // Manejo de excepciones genéricas
            showError("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void validateInputs() throws AuthenticationException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(username.isEmpty() || password.isEmpty()) {
            throw new AuthenticationException("Usuario y contraseña son requeridos");
        }
        
        if(username.length() < 4) {
            throw new AuthenticationException("Usuario debe tener al menos 4 caracteres");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
    }
}