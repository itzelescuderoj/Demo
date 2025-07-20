package mx.edu.utez.demo3.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.edu.utez.demo3.dao.impl.CarreraDaoImpl;
import mx.edu.utez.demo3.model.Carrera;

import java.sql.SQLException;
import java.util.List;


public class CarreraFormController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnCancelar;

    private final CarreraDaoImpl carreraDao = new CarreraDaoImpl();

    @FXML
    private void onSubmit() {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();

        if (nombre.isEmpty() || descripcion.isEmpty()) {
            showAlert("Error", "Todos los campos son obligatorios");
            return;
        }

        Carrera carrera = new Carrera();
        carrera.setNombre(nombre);
        carrera.setDescrpcion(descripcion);

        try {
            carreraDao.create(carrera);
            showAlert("Exito", "Carrera creada correctamente");
            closeWindow();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se creo la carrera");
        }
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

