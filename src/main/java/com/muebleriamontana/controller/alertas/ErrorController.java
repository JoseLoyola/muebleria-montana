package com.muebleriamontana.controller.alertas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ErrorController {

    @FXML
    private Button btnAceptar;

    @FXML
    private ImageView btnCerrar;

    @FXML
    private ImageView imgAlerta;

    @FXML
    private Label lblAccionMensaje;

    @FXML
    private Label lblAccionTitulo;

    private Stage stage;

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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setData(String accion) {
        lblAccionTitulo.setText(accion);
        lblAccionMensaje.setText(accion);
    }
}
