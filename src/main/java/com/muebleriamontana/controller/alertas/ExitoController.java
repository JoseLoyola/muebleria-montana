package com.muebleriamontana.controller.alertas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ExitoController implements Initializable {

    @FXML
    private Button btnAceptar;

    @FXML
    private ImageView btnCerrar;

    @FXML
    private ImageView imgAlerta;

    @FXML
    private Label lblAccionTitulo;

    @FXML
    private Label lblAccionMensaje;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void click_btnAceptar(ActionEvent event) {
        Stage stage = (Stage) btnAceptar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void click_btnCerrar(MouseEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    public void setData(String accion){
        lblAccionTitulo.setText(accion);
        lblAccionMensaje.setText(accion);
    }

    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
