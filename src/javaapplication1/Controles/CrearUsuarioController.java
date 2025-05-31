package javaapplication1.Controles;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javaapplication1.JavaApplication1;
import javaapplication1.database.UsuarioDAO;
import javaapplication1.database.DepartamentoDAO;
import javaapplication1.models.Usuario;
import javaapplication1.models.Administrador;
import javaapplication1.models.Tecnico;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;

public class CrearUsuarioController implements Initializable {

    @FXML private TextField nombreField;
    @FXML private TextField emailField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private ComboBox<String> rolComboBox;
    @FXML private ComboBox<String> departamentoComboBox;
    @FXML private Label mensajeLabel;
    
    private Map<String, Integer> departamentosMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar opciones del ComboBox de roles
        rolComboBox.getItems().addAll("admin", "tecnico");
        rolComboBox.getSelectionModel().selectFirst();
        
        // Cargar departamentos
        try {
            DepartamentoDAO departamentoDAO = new DepartamentoDAO();
            departamentoDAO.getAllDepartamentos().forEach(dep -> {
                departamentosMap.put(dep.getNombre(), dep.getId());
                departamentoComboBox.getItems().add(dep.getNombre());
            });
            
            if (!departamentoComboBox.getItems().isEmpty()) {
                departamentoComboBox.getSelectionModel().selectFirst();
            }
        } catch (Exception e) {
            mostrarError("Error al cargar departamentos: " + e.getMessage());
        }
        
        // Listener para mostrar/ocultar departamento según el rol
        rolComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            departamentoComboBox.setDisable(!"tecnico".equals(newVal));
        });
    }
    
    @FXML
    private void handleGuardarUsuario() {
        try {
        // Validaciones básicas
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            mostrarError("Las contraseñas no coinciden");
            return;
        }
        
        if (nombreField.getText().isEmpty() || emailField.getText().isEmpty() || 
            usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            mostrarError("Todos los campos son obligatorios");
            return;
        }
        
        String rol = rolComboBox.getValue();
        Usuario usuario;
        
        if ("tecnico".equals(rol)) {
            String departamentoNombre = departamentoComboBox.getValue();
            if (departamentoNombre == null) {
                mostrarError("Debe seleccionar un departamento para técnicos");
                return;
            }
            
            // Obtener el ID del departamento seleccionado
            int departamentoId = departamentosMap.get(departamentoNombre);
            
            // Validar que el ID no sea 0
            if (departamentoId == 0) {
                mostrarError("ID de departamento inválido");
                return;
            }
            
                usuario = new Tecnico(
                    0, // ID se generará en la base de datos
                    nombreField.getText(),
                    emailField.getText(),
                    usernameField.getText(),
                    passwordField.getText(),
                    departamentoId
                );
        } else {
            usuario = new Administrador(
                0,
                nombreField.getText(),
                emailField.getText(),
                usernameField.getText(),
                passwordField.getText()
            );
        }
        
        // Guardar en la base de datos
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.addUser(usuario);
        
        JavaApplication1.showErrorAlert("Éxito", "Usuario creado correctamente");
        handleCancelar();
        
    } catch (SQLException e) {
        if (e.getMessage().contains("usuarios_departamentos_departamento_id_fkey")) {
            mostrarError("Error: El departamento seleccionado no existe en la base de datos");
        } else if (e.getMessage().contains("usuarios_username_key")) {
            mostrarError("El nombre de usuario ya está en uso");
        } else if (e.getMessage().contains("usuarios_email_key")) {
            mostrarError("El correo electrónico ya está registrado");
        } else {
            mostrarError("Error al guardar usuario: " + e.getMessage());
        }
        e.printStackTrace();
    } catch (Exception e) {
        mostrarError("Error inesperado: " + e.getMessage());
        e.printStackTrace();
     }
    }
    
    @FXML
private void handleCancelar() {
    try {
        // Cerrar la ventana actual en lugar de intentar abrir una nueva
        ((Stage) nombreField.getScene().getWindow()).close();
    } catch (Exception e) {
        mostrarError("Error al cerrar la ventana: " + e.getMessage());
        e.printStackTrace();
    }
}
    
    private void mostrarError(String mensaje) {
        mensajeLabel.setText(mensaje);
    }
}