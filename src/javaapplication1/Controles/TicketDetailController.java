/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javaapplication1.Controles;

import javafx.fxml.FXML;
import javaapplication1.JavaApplication1;

public class TicketDetailController {
    @FXML
    private void handleBack() throws Exception {
        JavaApplication1.showTicketList();
    }
    
}