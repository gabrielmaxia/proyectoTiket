/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javaapplication1.Controles;


import javaapplication1.database.UsuarioDAO;
import javaapplication1.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javaapplication1.JavaApplication1;



    abstract class AuthenticationHandler {
    public abstract boolean authenticate(String username, String password) throws AuthenticationException;
}


class DatabaseAuthenticationHandler extends AuthenticationHandler {
    @Override
    public boolean authenticate(String username, String password) throws AuthenticationException {
        if(username == null || password == null) {
            throw new AuthenticationException("Credenciales no pueden ser nulas");
        }
     
        return username.equals("admin") && password.equals("admin123");
    }
}


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
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario user = usuarioDAO.authenticate(usernameField.getText(), passwordField.getText());
            
            JavaApplication1.showDashboard();
            
        } catch (AuthenticationException e) {
            showError("Error: " + e.getMessage());
        } catch (Exception e) {
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