package javaapplication1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaApplication1 extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showLogin();
    }

    public void showLogin() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Iniciar Sesión");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public void showDashboard() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PantallPrincipal.fxml"));
        primaryStage.setTitle("Sesion iniciada");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public void showTicketList() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ListaTikets"));
        primaryStage.setTitle("Lista de Tickets");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public void showTicketDetail() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("DetallesTikets"));
        primaryStage.setTitle("Detalles del Ticket");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}