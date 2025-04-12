/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javaapplication1.Controles;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javaapplication1.JavaApplication1;
import javaapplication1.Ticket;
import javaapplication1.exceptions.InvalidDataException;

public class NewTicketController {
    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private ComboBox<String> departmentField;
    @FXML private ComboBox<String> priorityField;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
  
        departmentField.getItems().addAll("Soporte Técnico", "Ventas", "RH");
        priorityField.getItems().addAll("Alta", "Media", "Baja");
    }

    @FXML
    private void handleSubmit() {
        try {
            validateInputs();
            Ticket newTicket = createTicket();
            saveTicket(newTicket);
            JavaApplication1.showTicketList();
        } catch (InvalidDataException e) {
            errorLabel.setText(e.getMessage());
        } catch (Exception e) {
            JavaApplication1.showErrorAlert("Error al guardar el ticket: " + e.getMessage());
        }
    }

    private void validateInputs() throws InvalidDataException {
        if (titleField.getText().isEmpty() || descriptionField.getText().isEmpty() 
            || departmentField.getValue() == null || priorityField.getValue() == null) {
            throw new InvalidDataException("Todos los campos marcados con * son obligatorios.");
        }
    }

    private Ticket createTicket() {
        return new Ticket(
            generateId(), 
            titleField.getText(), 
            "Pendiente", 
            java.time.LocalDate.now().toString(), 
            descriptionField.getText()
        );
    }

    private String generateId() {
        return "TKT-" + System.currentTimeMillis();
    }

    private void saveTicket(Ticket ticket) {
    
        System.out.println("Ticket guardado: " + ticket.getId());
    }
}
