package mx.edu.utez.demo3.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;



public class DashboardController {

    @FXML
    private void onAlumnos(ActionEvent e){
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/mx/edu/utez/demo3/view")
        );
    }
}
