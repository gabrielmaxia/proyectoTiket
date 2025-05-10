/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javaapplication1.Controles;


import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javaapplication1.JavaApplication1;
import javaapplication1.Ticket;
import javaapplication1.database.TicketDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javaapplication1.interfaces.TicketManager;
import javaapplication1.exceptions.DataLoadException;



    public class TicketListController implements TicketManager {
    @FXML private TableView<Ticket> ticketsTable;
    @FXML private TableColumn<Ticket, String> idColumn;
    @FXML private TableColumn<Ticket, String> titleColumn;
    @FXML private TableColumn<Ticket, String> statusColumn;
    @FXML private TableColumn<Ticket, String> dateColumn;


    @FXML
    public void initialize() {
       try {
          
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

       
            loadTickets();
        } catch (DataLoadException e) {
            System.err.println("Error al cargar tickets: " + e.getMessage());
            JavaApplication1.showErrorAlert("No se pudieron cargar los tickets");
        }
       }
    @Override
    public void loadTickets() throws DataLoadException {
        try {
            
            TicketDAO ticketDAO = new TicketDAO();
            ObservableList<Ticket> tickets = FXCollections.observableArrayList(ticketDAO.getAllTickets());
            ticketsTable.setItems(tickets);
            
        } catch (DataLoadException e) {
            JavaApplication1.showErrorAlert("Error al cargar tickets: " + e.getMessage());
        }
    }

    @FXML
    private void handleBack() {
        try {
            JavaApplication1.showDashboard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNewTicket() {
       
        System.out.println("Crear nuevo ticket");
    }

}
