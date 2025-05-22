/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javaapplication1.Controles;

import javafx.fxml.FXML;
import javaapplication1.JavaApplication1;
import javaapplication1.Ticket;
import javaapplication1.database.TicketDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.sql.SQLException;
import javaapplication1.utils.SessionManager;




public class TicketDetailController {
    @FXML private TextField txtTitulo;
    @FXML private TextArea txtDescripcion;
    @FXML private Label lblEstado;
    @FXML private Label lblDepartamento;
    @FXML private Label lblUsuario;
    
    private Ticket ticket;
    
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
        cargarTicket();
    }
    
    @FXML
    private void handleBack() throws Exception {
        JavaApplication1.showTicketList();
    }
    
  @FXML
    private void handleDeshacerCambio() {
    try {
            ticket.setDescription(txtDescripcion.getText());
            
            // Corrección: Usar updateTicketStatus o el método correcto de TicketDAO
            new TicketDAO().updateTicketDescription(
                ticket.getId(), 
                ticket.getDescription()
            );
            
            this.showAlert("Éxito", "Ticket actualizado", AlertType.INFORMATION);
        } catch (SQLException e) {
            this.showAlert("Error", "No se pudo guardar: " + e.getMessage(), AlertType.ERROR);
        }
    }

   private void cargarTicket() {
        if (ticket != null) {
            txtTitulo.setText(ticket.getTitle());
            txtDescripcion.setText(ticket.getDescription());
            lblEstado.setText(ticket.getStatus());
            lblDepartamento.setText(ticket.getDepartamentoNombre());
            lblUsuario.setText(ticket.getUsuarioNombre());
        }
    }

    @FXML
    private void handleGuardarCambios() {
        try {
            ticket.setDescription(txtDescripcion.getText());
            new TicketDAO().updateTicketStatus(ticket.getId(), ticket.getStatus(), SessionManager.getCurrentUser().getId());
        } catch (SQLException e) {
            JavaApplication1.showErrorAlert("Error", "No se pudo guardar: " + e.getMessage());
        }
    }
 private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 