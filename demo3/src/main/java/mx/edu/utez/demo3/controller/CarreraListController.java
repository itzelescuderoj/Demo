package mx.edu.utez.demo3.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private TableView<Carrera> tableCarrera;
    @FXML
    private TableColumn<Carrera, String> colNombre;
    @FXML
    private TableColumn<Carrera, String> colDescripcion;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNombre.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(String.valueOf(data.getValue().getNombre())));
        colDescripcion.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(String.valueOf(data.getValue().getDescrpcion()))); // cuidado con el nombre del método
        loadCarreras();
    }

    @FXML
    private void onCrearCarrera(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/edu/utez/demo3/view/Carrera_form.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onVolverADashboard(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/mx/edu/utez/demo3/view/Dashboard.fxml")
            );
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadCarreras (){
        try {
            List<Carrera> carreras = new CarreraDaoImpl().findAll();
            tableCarrera.setItems(FXCollections.observableList(carreras));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
