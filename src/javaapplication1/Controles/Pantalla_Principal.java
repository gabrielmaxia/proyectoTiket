/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javaapplication1.Controles;

import javafx.fxml.FXML;
import javaapplication1.JavaApplication1;

public class Pantalla_Principal {
    @FXML
    private void handleViewTickets() throws Exception {
        JavaApplication1.showTicketList();
    }

    @FXML
    private void handleViewReports() {
        
        System.out.println("Mostrar reportes");
    }

    @FXML
    private void handleLogout() {
        try {
            JavaApplication1.showLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}