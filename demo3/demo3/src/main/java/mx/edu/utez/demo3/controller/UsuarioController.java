package mx.edu.utez.demo3.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mx.edu.utez.demo3.dao.impl.UsuarioDaoImpl;

public class UsuarioController {

    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField txtPass;

    @FXML
    private void onLogin(){
        String correo= txtCorreo.getText().trim();
        String pass = txtPass.getText().trim();
        UsuarioDaoImpl dao= new UsuarioDaoImpl();
        try {
            if(dao.login(correo,pass)){
                System.out.println("Usuario validado");
            }else{
                showAlert("Error", "Credenciales incorrectas");
                System.out.println("Credenciales Incorrectas");
            }

        } catch (Exception e) {
            showAlert("Error", "Error al intentar logear");
            System.out.println("Error");
        }
    }

    public void showAlert(String title, String msg){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
