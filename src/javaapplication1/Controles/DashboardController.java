/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javaapplication1.Controles;

import java.util.Map;
import javafx.fxml.FXML;
import javaapplication1.JavaApplication1;
import javaapplication1.database.TicketDAO;
import java.util.ArrayList;
import java.util.HashMap;

public class DashboardController {
    
    
    @FXML
    private void handleViewTickets() throws Exception {
        JavaApplication1.showTicketList();
    }


    @FXML
    private void handleLogout() {
        try {
            JavaApplication1.showLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
private void handleViewReports() {
    try {
        Map<String, Object> reporte = new TicketDAO().generarReporteConsolidado();
        
     
        if (reporte == null) {
            reporte = new HashMap<>();
            reporte.put("ultimosTicketsResueltos", new ArrayList<>());
        }
        
      
        JavaApplication1.showReportsScreen();
        
    } catch (Exception e) {
        System.err.println("Error al cargar reportes: " + e.getMessage());
        JavaApplication1.showErrorAlert("Error", "No se pudieron cargar los reportes");
    }
}


}