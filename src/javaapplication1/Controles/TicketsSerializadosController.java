package javaapplication1.Controles;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javaapplication1.Ticket;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javaapplication1.utils.TicketSerializationUtil;

public class TicketsSerializadosController implements Initializable {

    @FXML private TableView<Ticket> tablaTickets;
    @FXML private TableColumn<Ticket, String> colId;
    @FXML private TableColumn<Ticket, String> colTitulo;
    @FXML private TableColumn<Ticket, String> colEstado;
    @FXML private TableColumn<Ticket, String> colPrioridad;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("title"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colPrioridad.setCellValueFactory(new PropertyValueFactory<>("prioridad"));

        try {
            List<Ticket> lista = TicketSerializationUtil.cargarTickets("tickets_serializados");
            if (lista != null) {
                tablaTickets.setItems(FXCollections.observableArrayList(lista));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}