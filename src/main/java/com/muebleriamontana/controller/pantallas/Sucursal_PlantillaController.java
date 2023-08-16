package com.muebleriamontana.controller.pantallas;

import com.muebleriamontana.Montana;
import com.muebleriamontana.controller.alertas.ErrorController;
import com.muebleriamontana.controller.alertas.ExitoController;
import com.muebleriamontana.model.Empleado;
import com.muebleriamontana.model.Sucursal;
import com.muebleriamontana.modelDAO.Empleado_DAO;
import com.muebleriamontana.modelDAO.Sucursal_DAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Sucursal_PlantillaController implements Initializable {

    @FXML
    private Label lblAccionTitulo;

    @FXML
    private ImageView imgProducto;

    @FXML
    private Button btnSelecionarImagen;

    @FXML
    private Label lblId;

    @FXML
    private TextField txfId;

    @FXML
    private TextField txfNombre;

    @FXML
    private TextField txfDireccion;

    @FXML
    private TextField txfDistrito;

    @FXML
    private ComboBox<Empleado> cbxEncargado;

    @FXML
    private Button btnAccion;
    FXMLLoader loader;
    Parent root = null;
    Scene scene = null;
    Stage stage = null;
    String accion = null;
    Sucursal sucursal = new Sucursal();
    Empleado_DAO empleado_dao = new Empleado_DAO();
    Sucursal_DAO sucursal_dao = new Sucursal_DAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxEncargado.setItems(empleado_dao.listarOLGerente());
    }

    @FXML
    void click_btnAccion(ActionEvent event) throws IOException {
        Sucursal sucursal1 = new Sucursal();
        sucursal1.setNombre(txfNombre.getText());
        sucursal1.setDireccion(txfDireccion.getText());
        sucursal1.setDistrito(txfDistrito.getText());
        sucursal1.setEncargado(cbxEncargado.getValue());
        if (accion.equalsIgnoreCase("agregar")){
            validarOperacion(event,sucursal_dao.agregar(sucursal1),"Empleado "+sucursal1.getNombre(),"agregado","agregar");
        } else if (accion.equalsIgnoreCase("editar")) {
            sucursal1.setId(Integer.parseInt(txfId.getText()));
            validarOperacion(event,sucursal_dao.actualizar(sucursal1),"Empleado "+sucursal1.getNombre(),"editado","editar");
        }
    }

    @FXML
    void click_btnSelecionarImagen(ActionEvent event) {

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

    public void setData(Sucursal sucursal, String accion) {
        this.accion = accion;
        if (accion.equalsIgnoreCase("agregar")){
            lblAccionTitulo.setText("Agregar");
            lblId.setVisible(false);
            txfId.setVisible(false);
            btnAccion.setText("Agregar");
            Image image = new Image(Montana.class.getResourceAsStream("img/productos/agregarImagen01.png"));
            imgProducto.setImage(image);
        } else if (accion.equalsIgnoreCase("editar")) {
            this.sucursal = sucursal;
            lblAccionTitulo.setText("Editar");
            btnAccion.setText("Editar");
            txfId.setText(String.valueOf(sucursal.getId()));
            txfNombre.setText(sucursal.getNombre());
            txfDireccion.setText(sucursal.getDireccion());
            txfDistrito.setText(sucursal.getDistrito());
            cbxEncargado.setValue(sucursal.getEncargado());
        }
    }

}
