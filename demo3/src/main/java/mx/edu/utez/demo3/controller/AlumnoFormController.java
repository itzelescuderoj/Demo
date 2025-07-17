package mx.edu.utez.demo3.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mx.edu.utez.demo3.dao.impl.AlumnoDaoImpl;
import mx.edu.utez.demo3.dao.impl.CarreraDaoImpl;
import mx.edu.utez.demo3.model.Alumno;
import mx.edu.utez.demo3.model.Carrera;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AlumnoFormController {

    @FXML
    private TextField txtNombres;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtCorreo;
    @FXML
    private DatePicker dpFechaNacimiento;
    @FXML
    private ComboBox<Carrera> cbCarrera;
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnCancelar;

    AlumnoDaoImpl daoAlumno = new AlumnoDaoImpl();
    CarreraDaoImpl daoCarrera = new CarreraDaoImpl();

    @FXML
    private void initialize(){
        try {
            List<Carrera> carreras = daoCarrera.findAll();
            cbCarrera.setItems(FXCollections.observableList(carreras));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void onSubmit(){
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String correo = txtCorreo.getText().trim();
        LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
        Carrera carrera = cbCarrera.getSelectionModel().getSelectedItem();
        if(nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || fechaNacimiento == null || carrera == null){
        showAlert("Error", "TODOS LOS CAMPOS SON REQUERIDOS");
            System.out.println("Todos los campos son obligatorios");
        return;
        }
        Alumno alumno = new Alumno();
        alumno.setNombre(nombres);
        alumno.setApellidos(apellidos);
        alumno.setCorreo(correo);
        alumno.setFechaNacimiento(fechaNacimiento);
        alumno.setIdCarrera(carrera.getId());
        try {
            daoAlumno.create(alumno);
            showAlert("Exito", "Se creo el Alumno con exito");
            closeWindow();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public void closeWindow(){
        Stage stage =(Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void onCancel(){
        closeWindow();
    }
    public void showAlert(String title, String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
