package javaapplication1.Controles;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javaapplication1.JavaApplication1;
import javaapplication1.database.TicketDAO;
import java.sql.SQLException;

public class NewTicketController {
    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private ComboBox<String> departmentField;
    @FXML private ComboBox<String> priorityField;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        // Configurar opciones de los ComboBox
        departmentField.getItems().addAll("Soporte Técnico", "Ventas", "TI", "Recursos Humanos");
        priorityField.getItems().addAll("alta", "media", "baja");
    }

    @FXML
private void handleSubmit() {
    try {
        // Validar campos
        if (titleField.getText().trim().isEmpty() || descriptionField.getText().trim().isEmpty()) {
            errorLabel.setText("Título y descripción son obligatorios");
            return;
        }
        

        String departamento = departmentField.getValue();
        String prioridad = priorityField.getValue();
        
        if (departamento == null || prioridad == null) {
            errorLabel.setStyle("-fx-text-fill: red;");
            errorLabel.setText("Seleccione departamento y prioridad");
            return;
        }

        new TicketDAO().createSimpleTicket(
            titleField.getText(),
            descriptionField.getText(),
            departamento,
            prioridad
        );
        
        errorLabel.setStyle("-fx-text-fill: green;");
        errorLabel.setText("Ticket creado!");
        
    } catch (SQLException e) {
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setText("Error: " + e.getMessage());
        e.printStackTrace();
    }
}

    @FXML
    private void handleBack() {
        try {
            JavaApplication1.showDashboard();
        } catch (Exception e) {
            errorLabel.setText("Error al volver al dashboard: " + e.getMessage());
        }
    }
    
}