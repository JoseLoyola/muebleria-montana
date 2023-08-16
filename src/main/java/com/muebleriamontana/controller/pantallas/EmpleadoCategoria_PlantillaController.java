package com.muebleriamontana.controller.pantallas;

import com.muebleriamontana.Montana;
import com.muebleriamontana.controller.alertas.ErrorController;
import com.muebleriamontana.controller.alertas.ExitoController;
import com.muebleriamontana.model.EmpleadoCategoria;
import com.muebleriamontana.modelDAO.EmpleadoCategoria_DAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmpleadoCategoria_PlantillaController implements Initializable {
    @FXML
    private Button btnAccion;

    @FXML
    private Label lblAccionTitulo;

    @FXML
    private Label lblId;

    @FXML
    private TextArea txfDescripcion;

    @FXML
    private TextField txfId;

    @FXML
    private TextField txfNombre;

    FXMLLoader loader;
    Parent root = null;
    Scene scene = null;
    Stage stage = null;
    private EmpleadoCategoria empleadoCategoria = new EmpleadoCategoria();
    private EmpleadoCategoria_DAO empleadoCategoria_dao=new EmpleadoCategoria_DAO();
    private String accion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void click_btnAccion(ActionEvent event) throws IOException {
//        EmpleadoCategoria empleadoCategoria = new EmpleadoCategoria();
        empleadoCategoria.setNombre(txfNombre.getText());
        empleadoCategoria.setDescripcion(txfDescripcion.getText());
        if (accion.equalsIgnoreCase("agregar")){
            validarOperacion(event,empleadoCategoria_dao.agregar(empleadoCategoria),"Categoria de Empleado "+empleadoCategoria.getNombre(),"agregado","agregar");
        } else if (accion.equalsIgnoreCase("editar")) {
            empleadoCategoria.setId(Integer.parseInt(txfId.getText()));
            validarOperacion(event,empleadoCategoria_dao.actualizar(empleadoCategoria),"Categoria de Empleado "+empleadoCategoria.getNombre(),"editado","editar");
        }
    }
    private void validarOperacion(ActionEvent event, Boolean accion, String objeto, String nombre, String nombre2) throws IOException {
        if (accion){
            cargarPantalla(event,true, objeto+" "+nombre);
        }else {
            cargarPantalla(event,false, nombre2+" "+objeto);
        }
    }
    private void cargarPantalla(ActionEvent event, Boolean eliminado, String accion) throws IOException {
        Image image;
        if (eliminado){
            loader = new FXMLLoader(Montana.class.getResource("view/alertas/exito.fxml"));
            image = new Image(Montana.class.getResourceAsStream("img/sistema/exito01.png"));
        }else {
            loader = new FXMLLoader(Montana.class.getResource("view/alertas/error.fxml"));
            image = new Image(Montana.class.getResourceAsStream("img/sistema/error01.png"));
        }
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
        if (eliminado){
            ExitoController exitoController = loader.getController();
            exitoController.setData(accion);
            exitoController.setStage(stage);
        }else {
            ErrorController errorController = loader.getController();
            errorController.setData(accion);
            errorController.setStage(stage);
        }
    }

    public void setData(EmpleadoCategoria empleadoCategoria, String accion) {
        this.accion = accion;
        if (accion.equalsIgnoreCase("agregar")){
            lblAccionTitulo.setText("Agregar");
            btnAccion.setText("Agregar");
            lblId.setVisible(false);
            txfId.setVisible(false);
        } else if (accion.equalsIgnoreCase("editar")) {
            this.empleadoCategoria=empleadoCategoria;
            lblAccionTitulo.setText("Editar");
            btnAccion.setText("Editar");
            txfId.setText(String.valueOf(empleadoCategoria.getId()));
            txfNombre.setText(empleadoCategoria.getNombre());
            txfDescripcion.setText(empleadoCategoria.getDescripcion());
        }
    }

}



