/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javaapplication1.Controles;


import javafx.fxml.FXML;
import javaapplication1.JavaApplication1;
import javaapplication1.Ticket;
import javaapplication1.database.ColaTicketsDAO;
import javaapplication1.database.TicketDAO;
import javaapplication1.database.UsuarioDAO;
import javaapplication1.utils.SessionManager;
import javafx.scene.control.Alert;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class TicketListController {
    
    @FXML private TableView<Ticket> ticketsTable;
    @FXML private TableColumn<Ticket, String> idColumn;
    @FXML private TableColumn<Ticket, String> titleColumn;
    @FXML private TableColumn<Ticket, String> statusColumn;
    @FXML private TableColumn<Ticket, String> dateColumn;

    private ObservableList<Ticket> ticketsList = FXCollections.observableArrayList();
    
    
    private void setupTableColumns() {
    // Ajusta los PropertyValueFactory para que coincidan con tus getters
    idColumn.setCellValueFactory(new PropertyValueFactory<>("id")); 
    titleColumn.setCellValueFactory(new PropertyValueFactory<>("title")); // Usa getTitle()
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status")); // Usa getStatus()
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("date")); // Usa getDate()
}
   @FXML
    public void initialize() {
        setupTableColumns();
        loadTicketData();
        setupRowStyling();
        setupTableDoubleClick();
    }

    
    
   private void loadTicketData() {
        try {
            List<Ticket> tickets = new TicketDAO().getAllTickets();
            ticketsList.setAll(tickets); // Reemplaza el contenido
            ticketsTable.setItems(ticketsList); // 👈 Esta línea es esencial

        } catch (SQLException e) {
            System.err.println("Error al cargar tickets: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    private void setupRowStyling() {
        ticketsTable.setRowFactory(tv -> new TableRow<Ticket>() {
            @Override
            protected void updateItem(Ticket ticket, boolean empty) {
                super.updateItem(ticket, empty);
                if (ticket == null || empty) {
                    setStyle("");
                } else {
                    setStyle(getIndex() % 2 == 0 ? 
                            "-fx-background-color: #ffffff;" : 
                            "-fx-background-color: #f2f2f2;");
                }
            }
        });
    }
    
    private void setupTableDoubleClick() {
        ticketsTable.setRowFactory(tv -> {
            TableRow<Ticket> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Ticket selectedTicket = row.getItem();
                    mostrarDetalleTicket(selectedTicket);
                }
            });
            return row;
        });
    }
    
    private void mostrarDetalleTicket(Ticket ticket) {
        try {
            JavaApplication1.showTicketDetail(ticket.getId());
        } catch (Exception e) {
            showAlert("Error", "No se pudo abrir el ticket: " + e.getMessage());
        }
    }
    
@FXML
private void handleBack() throws Exception {
    JavaApplication1.showDashboard();
    
}
    
     @FXML
    private void handleTomarProximoTicket() throws Exception {
        try {
            if (!SessionManager.isTecnico()) {
                showAlert("Error", "Solo los técnicos pueden tomar tickets");
                return;
            }
            
            int deptoId = new UsuarioDAO().obtenerDepartamentoId(
                SessionManager.getCurrentUser().getId()
            );
            
            if (deptoId == -1) {
                showAlert("Error", "No tienes departamento asignado");
                return;
            }
            
            Ticket proximoTicket = new ColaTicketsDAO().obtenerProximoTicket(deptoId);
            
            if (proximoTicket != null) {
                new TicketDAO().updateTicketStatus(
                    proximoTicket.getId(), 
                    "en_proceso",
                    SessionManager.getCurrentUser().getId()
                );
                loadTicketData(); // Refrescar la tabla
                JavaApplication1.showTicketDetail(proximoTicket.getId());
            } else {
                showAlert("Info", "No hay tickets disponibles en tu departamento");
            }
            
        } catch (SQLException e) {
            showAlert("Error", "Error de base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleNewTicket() throws Exception {
        JavaApplication1.showNewTicket();
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
   
    }


   
    
    


