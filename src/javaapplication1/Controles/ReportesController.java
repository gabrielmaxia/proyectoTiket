package javaapplication1.Controles;



import java.util.List;
import javaapplication1.JavaApplication1;
import javaapplication1.Ticket;
import javaapplication1.database.TicketDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;
import java.util.Map;

public class ReportesController {
    @FXML private Label lblTotalTickets;
    @FXML private TableView<Ticket> tblUltimosTickets;
    @FXML private TableColumn<Ticket, String> colId;
    @FXML private TableColumn<Ticket, String> colTitulo;
    @FXML private TableColumn<Ticket, String> colDepartamento;
    @FXML private TableColumn<Ticket, String> colFecha;

    @FXML
public void initialize() {
    try {
        // Verificación de componentes inyectados
        if (tblUltimosTickets == null || colId == null || colTitulo == null || 
            colDepartamento == null || colFecha == null) {
            throw new IllegalStateException("Componentes FXML no fueron inyectados correctamente");
        }
        
        configurarColumnasTabla();
        cargarDatosReportes();
        
        System.out.println("ReportesController inicializado correctamente");
    } catch (Exception e) {
        JavaApplication1.showErrorAlert("Error", "Error de configuración: " + e.getMessage());
        e.printStackTrace();
    }
}

    private void configurarColumnasTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDepartamento.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDepartamentoNombre()));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void cargarDatosReportes() {
    try {
        Map<String, Object> reporte = new TicketDAO().generarReporteConsolidado();
        
        // 1. Total tickets (con verificación)
        Object total = reporte.getOrDefault("totalTickets", 0);
        lblTotalTickets.setText(String.valueOf(total));
        
        // 2. Tickets resueltos (con verificación)
        List<Ticket> tickets = (List<Ticket>) reporte.getOrDefault("ultimosTicketsResueltos", new ArrayList<>());
        
        // Debug: Verificar datos
        System.out.println("Total tickets: " + total);
        System.out.println("Tickets encontrados: " + tickets.size());
        tickets.forEach(t -> System.out.println(t.getId() + " - " + t.getTitle()));
        
        tblUltimosTickets.setItems(FXCollections.observableArrayList(tickets));
        
    } catch (Exception e) {
        System.err.println("Error en cargarDatosReportes: " + e.getMessage());
        tblUltimosTickets.setItems(FXCollections.observableArrayList());
    }
}

    @FXML
    private void handleBackToDashboard() {
        try {
            JavaApplication1.showDashboard();
        } catch (Exception e) {
            JavaApplication1.showErrorAlert("Error", "No se pudo volver al dashboard");
        }
    }
}