package javaapplication1.Controles;

import javafx.fxml.FXML;
import javaapplication1.JavaApplication1;
import javaapplication1.Ticket;
import javaapplication1.database.TicketDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import java.sql.SQLException;

public class TicketDetailController {
    @FXML private Label lblNumeroTicket;
    @FXML private Label lblTitulo;
    @FXML private Label lblEstado;
    @FXML private Label lblPrioridad;
    @FXML private Label lblDepartamento;
    @FXML private Label lblUsuario;
    @FXML private TextArea txtDescripcion;
    @FXML private VBox container;
    
    private Ticket ticket;
    
    public void setTicket(Ticket ticket) {
    if (ticket == null) {
        System.err.println("Error: Ticket es null");
        mostrarAlerta("Error", "Ticket no válido", Alert.AlertType.ERROR);
        return;
    }
    
    this.ticket = ticket;
    
    try {
        cargarTicket();
        aplicarEstilos();
        System.out.println("Ticket cargado correctamente: " + ticket.getId());
    } catch (Exception e) {
        System.err.println("Error al cargar ticket:");
        e.printStackTrace();
        mostrarAlerta("Error", "No se pudo mostrar el ticket", Alert.AlertType.ERROR);
    }
}
    
    private void cargarTicket() {
        if (ticket != null) {
            lblNumeroTicket.setText("Detalle del Ticket #" + ticket.getId());
            lblTitulo.setText("Título: " + ticket.getTitle());
            lblEstado.setText("Estado: " + ticket.getStatus());
            lblPrioridad.setText("Prioridad: " + ticket.getPriority());
            lblDepartamento.setText("Departamento: " + ticket.getDepartamentoNombre());
            lblUsuario.setText("Reportado por: " + ticket.getUsuarioNombre());
            txtDescripcion.setText(ticket.getDescription());
        }
    }
    
    private void aplicarEstilos() {
        // Estilos dinámicos basados en prioridad
        String colorPrioridad = "#3498db"; // Azul por defecto
        if ("Alta".equalsIgnoreCase(ticket.getPriority())) {
            colorPrioridad = "#e74c3c"; // Rojo
        } else if ("Media".equalsIgnoreCase(ticket.getPriority())) {
            colorPrioridad = "#f39c12"; // Naranja
        }
        
        lblPrioridad.setStyle("-fx-text-fill: " + colorPrioridad + "; -fx-font-weight: bold;");
        
        // Estilo para estado
        String colorEstado = "#2ecc71"; // Verde por defecto
        if ("Cerrado".equalsIgnoreCase(ticket.getStatus())) {
            colorEstado = "#95a5a6"; // Gris
        } else if ("En Proceso".equalsIgnoreCase(ticket.getStatus())) {
            colorEstado = "#3498db"; // Azul
        }
        
        lblEstado.setStyle("-fx-text-fill: " + colorEstado + "; -fx-font-weight: bold;");
    }
    
    @FXML
    private void handleBack() throws Exception {
        JavaApplication1.showTicketList();
    }
    
    @FXML
    private void handleGuardarCambios() {
        try {
            new TicketDAO().updateTicketDescription(ticket.getId(), txtDescripcion.getText());
            mostrarAlerta("Éxito", "Descripción actualizada correctamente", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo guardar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}