package javaapplication1;

import java.io.IOException;
import java.net.URL;
import javaapplication1.Controles.TicketDetailController;
import javaapplication1.database.TicketDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class JavaApplication1 extends Application {
    private static Stage primaryStage;

    public static void showAdminDashboard() throws Exception {
        Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/Pantalla_Principal.fxml"));
        primaryStage.setTitle("Admin - Sistema de Tickets");
        primaryStage.setScene(new Scene(root, 800, 600));
   
    }
    public static void showUserDashboard() throws Exception {
        Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/TicketList.fxml"));
        primaryStage.setTitle("Mis Tickets");
        primaryStage.setScene(new Scene(root, 1000, 600));
    }

public static void showErrorAlert(String title, String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
}
    @Override
    public void start(Stage stage)  {
        try {
       
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
         
            Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/Login.fxml"));
            primaryStage.setScene(new Scene(root, 400, 700));
            primaryStage.setTitle("Iniciar Sesión");
        } catch (IOException e) {
            // Implementación de manejo de excepciones
            System.err.println("Error al cargar la pantalla de login:");
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
        Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/Pantalla_Principal.fxml"));
        primaryStage.setTitle("Dashboard - Sistema de Tickets");
        primaryStage.setScene(new Scene(root, 1000, 700));
    }

    public static void showTicketList() throws Exception {
        Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/TicketList.fxml"));
        primaryStage.setTitle("Gestión de Tickets");
        primaryStage.setScene(new Scene(root, 1000, 600));
    }

    public static void showTicketDetail(Ticket ticket) throws Exception {
    if (ticket == null) {
        throw new IllegalArgumentException("Ticket no puede ser nulo");
    }
    
    FXMLLoader loader = new FXMLLoader(
        JavaApplication1.class.getResource("/javaapplication1/views/TicketDetail.fxml"));
    Parent root = loader.load();
    
    TicketDetailController controller = loader.getController();
    controller.setTicket(ticket);
    
    // Usa la ventana principal existente o crea nueva según tu flujo
    primaryStage.setTitle("Detalle Ticket #" + ticket.getId());
    primaryStage.setScene(new Scene(root, 800, 600));
}
    
    public static void showNewTicket() throws Exception {
        Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/NewTicket.fxml"));
        primaryStage.setTitle("Nuevo Ticket");
        primaryStage.setScene(new Scene(root, 800, 400));
    }
    
    public static void showReportsScreen() throws Exception {
    Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/Reportes.fxml"));
    primaryStage.setTitle("Reportes del Sistema");
    primaryStage.setScene(new Scene(root, 1000, 700));
}
    
    public static void showTecnicoDashboard() throws Exception {
    
Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/TecnicoDashboard.fxml"));
    primaryStage.setTitle("Area del tecnico");
    primaryStage.setScene(new Scene(root, 1000, 700));
    
    
}
  public static void showCrearUsuarioScreen() throws Exception {
    // Cambiar la ruta a /javaapplication1/views/CrearUsuario.fxml
    FXMLLoader loader = new FXMLLoader(JavaApplication1.class.getResource("/javaapplication1/views/CrearUsuario.fxml"));
    Parent root = loader.load();
    
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.setTitle("Crear Nuevo Usuario");
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.showAndWait();
}

    public static void main(String[] args) {
        launch(args);
  
    }
    


    }
