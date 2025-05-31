package javaapplication1.Controles;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javaapplication1.JavaApplication1;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javaapplication1.Ticket;
import javaapplication1.database.TicketDAO;
import javaapplication1.utils.SessionManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TecnicoDashboardController {

    @FXML private TableView<Ticket> ticketsTable;
    @FXML private TableColumn<Ticket, String> idColumn, tituloColumn, statusColumn, priorityColumn;
    @FXML private ComboBox<String> estadoFilter, prioridadFilter;

    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Configurar las columnas
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            tituloColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
            priorityColumn.setCellValueFactory(new PropertyValueFactory<>("prioridad"));

            // Inicializar ComboBoxes de filtro
            estadoFilter.setItems(FXCollections.observableArrayList(
                "Pendiente", "En Proceso", "Resuelto", "Cerrado"));
            prioridadFilter.setItems(FXCollections.observableArrayList(
                "alta", "media", "baja"));

            // Cargar los tickets del departamento del técnico
            cargarTickets();

        } catch (SQLException e) {
            mostrarError("Error al inicializar: " + e.getMessage());
            e.printStackTrace();
        }
       
    }

    private void cargarTickets() throws SQLException {

        int deptoId = SessionManager.getCurrentUser().getDepartamentoId();
        System.out.println("Cargando tickets para departamento ID: " + deptoId); 
        
        // Obtener tickets filtrados por departamento
        List<Ticket> tickets = new TicketDAO().obtenerTicketsPorDepartamento(deptoId, null, null);
        System.out.println("Tickets encontrados: " + tickets.size()); // Log de depuración

        // Mostrar en la tabla
        ObservableList<Ticket> datos = FXCollections.observableArrayList(tickets);
        ticketsTable.setItems(datos);
    }

    @FXML
    private void handleFiltrar() {
        try {
            int deptoId = SessionManager.getCurrentUser().getDepartamentoId();
            String estado = estadoFilter.getValue();
            String prioridad = prioridadFilter.getValue();
            
            System.out.println("Filtrando - Depto: " + deptoId + 
                             ", Estado: " + estado + 
                             ", Prioridad: " + prioridad); // Log de depuración
            
            List<Ticket> ticketsFiltrados = new TicketDAO()
                .obtenerTicketsPorDepartamento(deptoId, estado, prioridad);
            
            System.out.println("Tickets después de filtrar: " + ticketsFiltrados.size()); // Log
            
            ticketsTable.setItems(FXCollections.observableArrayList(ticketsFiltrados));
        } catch (SQLException e) {
            mostrarError("Error al filtrar tickets: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTomarTicket() {
        Ticket ticketSeleccionado = ticketsTable.getSelectionModel().getSelectedItem();
        
        if (ticketSeleccionado == null) {
            mostrarError("Debes seleccionar un ticket.");
            return;
        }

        if (!"Pendiente".equals(ticketSeleccionado.getStatus())) {
            mostrarError("Solo puedes tomar tickets en estado 'Pendiente'.");
            return;
        }

        try {
            // Cambiar estado a "En Proceso"
            new TicketDAO().actualizarEstado(
                ticketSeleccionado.getId(), 
                "En Proceso"
            );

            // Refrescar tabla
            cargarTickets();
            
            mostrarMensaje("Ticket tomado", "El ticket ahora está en proceso");
        } catch (SQLException e) {
            mostrarError("Error al tomar ticket: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEnProceso() {
        try {
            Ticket ticketSeleccionado = ticketsTable.getSelectionModel().getSelectedItem();
            if (ticketSeleccionado != null) {
                new TicketDAO().actualizarEstado(
                    ticketSeleccionado.getId(), 
                    "En Proceso"
                );
                cargarTickets();
                mostrarMensaje("Estado actualizado", "Ticket marcado como 'En Proceso'");
            } else {
                mostrarError("Debe seleccionar un ticket primero.");
            }
        } catch (SQLException e) {
            mostrarError("Error al actualizar el estado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCerrarTicket() {
        mostrarError("Los técnicos no tienen permiso para cerrar tickets.");
    }

    @FXML
    private void handleCerrarSesion() {
        try {
            JavaApplication1.showLogin();
        } catch (Exception e) {
            mostrarError("Error al cerrar sesión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    @FXML
private void handleCargarSerializados() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javaapplication1/views/TicketsSerializadosView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Tickets Serializados");
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        mostrarError("No se pudo abrir la ventana: " + e.getMessage());
        e.printStackTrace();
    }
}
}