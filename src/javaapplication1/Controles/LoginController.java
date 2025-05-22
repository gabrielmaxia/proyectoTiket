package javaapplication1.Controles;

import javaapplication1.JavaApplication1;
import javaapplication1.database.UsuarioDAO;
import javaapplication1.models.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javaapplication1.utils.SessionManager;
import javax.naming.AuthenticationException;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    
   @FXML
private void handleLogin() {
    try {
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            throw new AuthenticationException("Usuario y contraseña son requeridos");
        }

        Usuario user = new UsuarioDAO().authenticate(
            usernameField.getText(), 
            passwordField.getText()
        );
        
        SessionManager.login(user);
        
        if (user.getRol().equals("admin")) { // Ahora coincide exactamente con tu BD
            JavaApplication1.showAdminDashboard();
        } else {
            JavaApplication1.showDashboard();
        }
        
    } catch (AuthenticationException e) {
        errorLabel.setText("Error: " + e.getMessage());
    } catch (Exception e) {
        errorLabel.setText("Error inesperado. Intente nuevamente.");
        e.printStackTrace();
    }
}
}                                               