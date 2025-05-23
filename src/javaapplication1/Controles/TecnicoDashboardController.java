package javaapplication1.Controles;

import java.io.IOException;
import java.sql.SQLException;
import javaapplication1.JavaApplication1;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javaapplication1.Ticket;
import javaapplication1.database.TicketDAO;
import javaapplication1.utils.SessionManager;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TecnicoDashboardController {

    @FXML private TableView<Ticket> ticketsTable;
    @FXML private TableColumn<Ticket, String> idColumn, titleColumn, statusColumn, priorityColumn;
    @FXML private ComboBox<String> estadoFilter, prioridadFilter;

    @FXML
    public void initialize() {
        // Configurar columnas
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        priorityColumn.setCellValueFactory(cellData -> cellData.getValue().priorityProperty());

        // Cargar filtros
        estadoFilter.setItems(FXCollections.observableArrayList("Pendiente", "En progreso", "Resuelto", "Abierto"));
        prioridadFilter.setItems(FXCollections.observableArrayList("alta", "media", "baja"));

        // Cargar datos iniciales
        cargarTickets();
    }

    private void cargarTickets() {
        try {
            int deptoId = SessionManager.getCurrentUser().getDepartamentoId();
            ticketsTable.setItems(FXCollections.observableArrayList(
                new TicketDAO().obtenerTicketsPorDepartamento(deptoId, null, null)
            ));
        } catch (SQLException e) {
            mostrarError("Error al cargar tickets: " + e.getMessage());
        }
    }

    @FXML
    private void handleFiltrar() {
        try {
            int deptoId = SessionManager.getCurrentUser().getDepartamentoId();
            String estado = estadoFilter.getValue();
            String prioridad = prioridadFilter.getValue();
            
            ticketsTable.setItems(FXCollections.observableArrayList(
                new TicketDAO().obtenerTicketsPorDepartamento(deptoId, estado, prioridad)
            ));
        } catch (SQLException e) {
            mostrarError("Error al filtrar tickets");
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.show();
    }
    
    @FXML
private void handleTomarTicket() {
    Ticket ticketSeleccionado = ticketsTable.getSelectionModel().getSelectedItem();
    if (!"Pendiente".equals(ticketSeleccionado.getStatus())) {
        mostrarError("Debes seleccionar un ticket.");
        return;
    }

    if (!"Pendiente".equals(ticketSeleccionado.getStatus())) {
        mostrarError("Solo puedes tomar tickets en estado 'Pendiente'.");
        return;
    }

    try {
        // Cambiar estado a "En Proceso"
        ticketSeleccionado.setStatus("En Proceso");

        // Aquí podrías usar un método en TicketDAO para actualizarlo en la BD
        new TicketDAO().actualizarEstado(ticketSeleccionado.getId(), "En progreso");

        // Refrescar tabla
        cargarTickets();
    } catch (SQLException e) {
        mostrarError("Error al tomar ticket: " + e.getMessage());
    }
}
@FXML
private void handleEnProceso() {
    // Aquí el código para manejar cuando se active este evento, por ejemplo:
    System.out.println("Botón En Proceso presionado");
    
    // Lógica para actualizar el estado del ticket a "En Proceso"
    try {
        Ticket ticketSeleccionado = ticketsTable.getSelectionModel().getSelectedItem();
        if (ticketSeleccionado != null) {
            new TicketDAO().actualizarEstado(ticketSeleccionado.getId(), "En Proceso");
            cargarTickets();  // recargar la tabla para reflejar cambios
        } else {
            mostrarError("Debe seleccionar un ticket primero.");
        }
    } catch (SQLException e) {
        mostrarError("Error al actualizar el estado: " + e.getMessage());
    }
}

@FXML
    private void handleCerrarTicket() {
        Ticket ticketSeleccionado = ticketsTable.getSelectionModel().getSelectedItem();

        if (ticketSeleccionado == null) {
            mostrarError("Debe seleccionar un ticket para cerrar.");
            return;
        }

        // Técnicos NO pueden cerrar tickets, mostramos mensaje de error
        mostrarError("No tiene permiso para cerrar tickets.");

        // Si quieres, aquí podrías refrescar la tabla o realizar otras acciones
        // cargarTickets();  // método para recargar tickets en la tabla (debes tenerlo definido)
    }
    @FXML
private void handleCerrarSesion() {
 try {
            JavaApplication1.showLogin();
        } catch (Exception e) {
            
        }
    }
}

