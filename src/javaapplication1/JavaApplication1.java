package javaapplication1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaApplication1 extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage)  {
        try {
            // Código original
            primaryStage = stage;
            showLogin();
            primaryStage.show();
        } catch (Exception e) {
            // Implementación de manejo de excepciones
            System.err.println("Error crítico al iniciar la aplicación:");
            e.printStackTrace();
            showErrorAlert("No se pudo iniciar la aplicación");
        }
    }

    

    public static void showLogin() {
        try {
            // Código original
            Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/Login.fxml"));
            primaryStage.setScene(new Scene(root, 600, 550));
            primaryStage.setTitle("Iniciar Sesión");
        } catch (Exception e) {
            // Implementación de manejo de excepciones
            System.err.println("Error al cargar la pantalla de login:");
            e.printStackTrace();
            showErrorAlert("Error al cargar la interfaz");
        }
    }
    
    
    public static void showErrorAlert(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static void showDashboard() throws Exception {
        Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/Dashboard.fxml"));
        primaryStage.setTitle("Dashboard - Sistema de Tickets");
        primaryStage.setScene(new Scene(root, 1000, 700));
    }

    public static void showTicketList() throws Exception {
        Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/TicketList.fxml"));
        primaryStage.setTitle("Gestión de Tickets");
        primaryStage.setScene(new Scene(root, 1200, 800));
    }

    public static void showTicketDetail() throws Exception {
        Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/TicketDetail.fxml"));
        primaryStage.setTitle("Detalle de Ticket");
        primaryStage.setScene(new Scene(root, 1000, 700));
    }
    
    public static void showNewTicket() throws Exception {
        Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/NewTicket.fxml"));
        primaryStage.setTitle("Nuevo Ticket");
        primaryStage.setScene(new Scene(root, 800, 600));
    }

    public static void main(String[] args) {
        launch(args);
  
    }
    private static void testCRUDOperations() {
       System.out.println("Pruebas CRUD desactivadas temporalmente");
}

    }
