/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ag045
 */
package javaapplication1;

import com.sun.jdi.connect.spi.Connection;
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
           
            primaryStage = stage;
            showLogin();
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Error crítico al iniciar la aplicación:");
            e.printStackTrace();
            showErrorAlert("No se pudo iniciar la aplicación");
        }
    }

    public static void showNewTicket() throws Exception {
        Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/NewTicket.fxml"));
        primaryStage.setTitle("Nuevo Ticket");
        primaryStage.setScene(new Scene(root, 600, 400));
    }
/*
    public static void showReports() throws Exception {
        Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/Reports.fxml"));
        primaryStage.setTitle("Reportes");
        primaryStage.setScene(new Scene(root, 800, 600));
    }
    // === FIN CAMBIO ===
*/
   
    public static void showLogin() {
        try {
            Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/Login.fxml"));
            primaryStage.setScene(new Scene(root, 600, 550));
            primaryStage.setTitle("Iniciar Sesión");
        } catch (Exception e) {
            System.err.println("Error al cargar la pantalla de login:");
            e.printStackTrace();
            showErrorAlert("Error al cargar la interfaz");
        }
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

   
    public static void showErrorAlert(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public class DatabaseConnection {
    // === INICIO CAMBIO === (Credenciales de Render.com)
    private static final String URL = "jdbc:postgresql://dpg-d060g6idbo4c73e37ss0-a.oregon-postgres.render.com:5432/tiketbase";
    private static final String USER = "tiketbase_user";
    private static final String PASSWORD = "FQj0padJz85XDtfNvwz1CClROM1WxbGW";
    // === FIN CAMBIO ===
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ag045
 */
package javaapplication1;

import com.sun.jdi.connect.spi.Connection;
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
           
            primaryStage = stage;
            showLogin();
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Error crítico al iniciar la aplicación:");
            e.printStackTrace();
            showErrorAlert("No se pudo iniciar la aplicación");
        }
    }

    public static void showNewTicket() throws Exception {
        Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/NewTicket.fxml"));
        primaryStage.setTitle("Nuevo Ticket");
        primaryStage.setScene(new Scene(root, 600, 400));
    }
/*
    public static void showReports() throws Exception {
        Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/Reports.fxml"));
        primaryStage.setTitle("Reportes");
        primaryStage.setScene(new Scene(root, 800, 600));
    }
    // === FIN CAMBIO ===
*/
   
    public static void showLogin() {
        try {
            Parent root = FXMLLoader.load(JavaApplication1.class.getResource("/javaapplication1/views/Login.fxml"));
            primaryStage.setScene(new Scene(root, 600, 550));
            primaryStage.setTitle("Iniciar Sesión");
        } catch (Exception e) {
            System.err.println("Error al cargar la pantalla de login:");
            e.printStackTrace();
            showErrorAlert("Error al cargar la interfaz");
        }
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

   
    public static void showErrorAlert(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public class DatabaseConnection {
    // === INICIO CAMBIO === (Credenciales de Render.com)
    private static final String URL = "jdbc:postgresql://dpg-d060g6idbo4c73e37ss0-a.oregon-postgres.render.com:5432/tiketbase";
    private static final String USER = "tiketbase_user";
    private static final String PASSWORD = "FQj0padJz85XDtfNvwz1CClROM1WxbGW";
    // === FIN CAMBIO ===

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");  // Carga el driver
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error al cargar el driver de PostgreSQL", e);
        }
    }
}
    public static void main(String[] args) {
        launch(args);
    }
}
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");  // Carga el driver
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error al cargar el driver de PostgreSQL", e);
        }
    }
}
    
    public class TestConexion {
  
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("✅ ¡Conexión exitosa a PostgreSQL!");
            
            // Prueba crear un ticket
            Ticket ticket = new Ticket(0, "Prueba", "Descripción de prueba", "Pendiente");
            TicketDAO dao = new TicketDAO();
            dao.crearTicket(ticket);
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }
}
    public static void main(String[] args) {
        launch(args);
    }
}