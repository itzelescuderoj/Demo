package mx.edu.utez.demo3.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.edu.utez.demo3.dao.impl.CarreraDaoImpl;
import mx.edu.utez.demo3.model.Carrera;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CarreraListController implements Initializable {

    @FXML
    private TableView<Carrera> tblCarreras;

    @FXML
    private TableColumn<Carrera, Integer> colId;

    @FXML
    private TableColumn<Carrera, String> colNombre;

    @FXML
    private TableColumn<Carrera, String> colDescripcion;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnDashboard;

    private final CarreraDaoImpl carreraDao = new CarreraDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        onCrearCarrera();

        btnAgregar.setOnAction(event -> mostrarFormulario(event));
        btnDashboard.setOnAction(event -> iraDashboar(event));
    }

    private void onCrearCarrera() {
        try {
            List<Carrera> carreras = carreraDao.findAll();
            tblCarreras.setItems(FXCollections.observableArrayList(carreras));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarFormulario(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/mx/edu/utez/demo3/view/CarreraForm.fxml")
            );
            Parent root = loader.load();

            Stage dialog = new Stage();
            dialog.setTitle("Nueva Carrera");
            dialog.setScene(new Scene(root));
            dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
            onCrearCarrera();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void iraDashboar(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/mx/edu/utez/demo3/view/Dashboard.fxml")
            );
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
