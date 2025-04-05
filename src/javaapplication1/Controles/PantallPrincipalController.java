package javaapplication1.Controles;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author ag045

**/
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PantallPrincipalController {

    @FXML
    private Button viewTicketsButton;

    @FXML
    private Button logoutButton;

    @FXML
    private void handleViewTickets() {
        // Navegar a la pantalla de lista de tickets (por ahora solo un mensaje)
        System.out.println("Navegando a la lista de tickets...");
    }

    @FXML
    private void handleLogout() {
        // Navegar de vuelta a la pantalla de login (por ahora solo un mensaje)
        System.out.println("Cerrando sesión...");
    }
}

