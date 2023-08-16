package com.muebleriamontana.controller;


import com.muebleriamontana.Montana;
import com.muebleriamontana.conexionBD.Sesion;
import com.muebleriamontana.controller.alertas.ErrorController;
import com.muebleriamontana.model.Empleado;
import com.muebleriamontana.modelDAO.Empleado_DAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private ImageView btnCerrar;

    @FXML
    private ImageView btnMinimizar;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private Button btnIngresar;

    private Empleado empleado;
    private Empleado_DAO empleadoDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void click_btnIngresar(ActionEvent event) throws IOException {
        String usuario = txtUsuario.getText();
        String contrasena = txtContrasena.getText();

        empleadoDAO = new Empleado_DAO();
        empleado = empleadoDAO.validarSesion(usuario,contrasena);

        if (empleado.getId()>0){
            Sesion.getInstance().setEmpleado(empleado);
            FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/principal.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            PrincipalController principalController = loader.getController();
            principalController.init(stage);
        }else {
            FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/alertas/error.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            Image image = new Image(Montana.class.getResourceAsStream("img/sistema/error01.png"));
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            ErrorController errorController = loader.getController();
            errorController.setData("Iniciar Sesion. Credenciales incorrectas");
            errorController.setStage(stage);
        }
    }

    @FXML
    void click_btnMinimizar(MouseEvent event) {
        Stage stage = (Stage) btnMinimizar.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void click_btnCerrar(MouseEvent event) {
        Platform.exit();
    }

}
