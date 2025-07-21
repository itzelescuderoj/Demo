package mx.edu.utez.demo3.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.edu.utez.demo3.dao.impl.CarreraDaoImpl;
import mx.edu.utez.demo3.model.Carrera;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class CarreraFormController {

    @FXML
    private TextField txtNombreCarrera;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnCancelar;

    private final CarreraDaoImpl carreraDao = new CarreraDaoImpl();

    @FXML
    private void onSubmit() {
        String nombre = txtNombreCarrera.getText();
        String descripcion = txtDescripcion.getText();

        if (nombre.isEmpty() || descripcion.isEmpty()) {
            showAlert("Error", "Todos los campos son requeridos");
            return;
        }

        Carrera carrera = new Carrera();
        carrera.setNombre(nombre);
        carrera.setDescrpcion(descripcion);

        try {
            carreraDao.create(carrera);
            showAlert("Éxito", "Carrera creada correctamente");
            closeWindow();
        } catch (Exception e) {
            showAlert("Error", "No se pudo crear la carrera");
            e.printStackTrace();
        }
    }


    @FXML
    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/edu/utez/demo3/view/Carrera_list.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) btnCrear.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

