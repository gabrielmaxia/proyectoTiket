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

   @FXML
    public void initialize() {
        setupTableColumns();
        loadTicketData();
        setupRowStyling();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void configurarColumnas() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void cargarTickets() {
        try {
            ticketsList.clear();
            ticketsList.addAll(new TicketDAO().getAllTickets());
            ticketsTable.setItems(ticketsList);
        } catch (Exception e) {
            System.err.println("Error al cargar tickets: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configurarFilasAlternas() {
        ticketsTable.setRowFactory(tv -> new TableRow<Ticket>() {
            @Override
            protected void updateItem(Ticket ticket, boolean empty) {
                super.updateItem(ticket, empty);
                if (empty || ticket == null) {
                    setStyle("");
                } else {
                    if (getIndex() % 2 == 0) {
                        setStyle("-fx-background-color: #ffffff;");
                    } else {
                        setStyle("-fx-background-color: #f2f2f2;");
                    }
                }
            }
        });
    }

    private void loadTicketData() {
        try {
            ticketsList.setAll(new TicketDAO().getAllTickets());
            ticketsTable.setItems(ticketsList);
            
            // Debug: Verifica cuántos tickets se cargaron
            System.out.println("Tickets cargados: " + ticketsList.size()); 
        } catch (SQLException e) {
            showAlert("Error", "No se pudieron cargar los tickets: " + e.getMessage());
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


    
    @FXML
private void handleBack() throws Exception {
    JavaApplication1.showDashboard();// O tu método de navegación
}

    // 2. Solución para mostrarTicket()
    private void mostrarDetalleTicket(Ticket ticket) {
        try {
        JavaApplication1.showTicketDetail(ticket.getId());
    } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Error al mostrar ticket: " + e.getMessage());
        alert.showAndWait();
    }
    }

    @FXML
    private void handleTomarProximoTicket() throws Exception {
         try {
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
                JavaApplication1.showTicketDetail(proximoTicket.getId());
            } else {
                showAlert("Info", "No hay tickets disponibles en tu departamento");
            }
            
        } catch (SQLException e) {
            showAlert("Error", "Error de base de datos: " + e.getMessage());
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    private void handleNewTicket() throws Exception {
        JavaApplication1.showNewTicket();
    }
}

