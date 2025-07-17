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
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.edu.utez.demo3.dao.impl.AlumnoDaoImpl;
import mx.edu.utez.demo3.model.Alumno;
import mx.edu.utez.demo3.model.Asignatura;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class AlumnoListController implements Initializable {

    @FXML
    private TableView<Alumno> tableAlumnos;

    @FXML private TableColumn<Alumno, String> colId;
    @FXML private TableColumn<Alumno, String> colNombres;
    @FXML private TableColumn<Alumno, String> colApellidos;
    @FXML private TableColumn<Alumno, String> colFecha;
    @FXML private TableColumn<Alumno, String> colCorreo;
    @FXML private TableColumn<Alumno, String> colCarrera;
    @FXML private TableColumn<Alumno, String> colAsignaturas;
    @FXML private TableColumn<Alumno, Void> colAcciones;

    private final AlumnoDaoImpl alumnoDao = new AlumnoDaoImpl();
    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Asigna factories a las columnas estándar
        colId.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(String.valueOf(data.getValue().getId()))
        );
        colNombres.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().getNombre())
        );
        colApellidos.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().getApellidos())
        );
        colFecha.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue()
                        .getFechaNacimiento().format(df))
        );
        colCorreo.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().getCorreo())
        );
        colCarrera.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().getIdCarrera()+"")
        );

        // Columna de asignaturas: junta nombres con comas
        colAsignaturas.setCellValueFactory(data -> {
            List<Asignatura> lista = data.getValue().getAsignaturas();
            String texto = String.join(", ",
                    lista.stream().map(Asignatura::getNombre).toList()
            );
            return new ReadOnlyStringWrapper(texto);
        });

        // Column “Acciones”
        colAcciones.setCellFactory(col -> new TableCell<>() {
            private final Button btnEditar    = new Button("Editar");
            private final Button btnInscribir = new Button("Inscribir");
            private final Button btnEliminar  = new Button("Eliminar");
            private final HBox pane = new HBox(5, btnEditar, btnInscribir, btnEliminar);
            {
                pane.setAlignment(Pos.CENTER);
                pane.getChildren().forEach(b -> b.getStyleClass().add("action-btn"));
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });

        // Carga los datos
        loadAlumnos();

    }
    private void loadAlumnos() {
        try {
            List<Alumno> alumnos= alumnoDao.findAll();
            tableAlumnos.setItems(FXCollections.observableList(alumnos));
            System.out.println("Lista: " + alumnos.size());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void onCrearAlumnos(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/mx/edu/utez/demo3/view/Alumno_form.fxml")
        );
        try {
            Parent root=loader.load();
            Stage dialog= new Stage();
            dialog.initOwner( ((Node) event.getSource() ).getScene().getWindow() );
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Form Alumno");
            dialog.setScene(new Scene(root));
            dialog.showAndWait();
            loadAlumnos();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
