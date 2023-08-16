package com.muebleriamontana.controller.pantallas;

import com.muebleriamontana.Montana;
import com.muebleriamontana.controller.alertas.ErrorController;
import com.muebleriamontana.controller.alertas.ExitoController;
import com.muebleriamontana.model.Empleado;
import com.muebleriamontana.model.ProductoCategoria;
import com.muebleriamontana.modelDAO.ProductoCategoria_DAO;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductoCategoria_PlantillaController implements Initializable {

    @FXML
    private Label lblAccionTitulo;

    @FXML
    private Label lblId;

    @FXML
    private TextField txfId;

    @FXML
    private TextField txfNombre;

    @FXML
    private TextArea txfDescripcion;

    @FXML
    private Button btnAccion;
    FXMLLoader loader;
    Parent root = null;
    Scene scene = null;
    Stage stage = null;
    String accion = null;
    ProductoCategoria productoCategoria = new ProductoCategoria();
    ProductoCategoria_DAO productoCategoria_dao = new ProductoCategoria_DAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void click_btnAccion(ActionEvent event) throws IOException {
        ProductoCategoria productoCategoria1 = new ProductoCategoria();
        productoCategoria1.setNombre(txfNombre.getText());
        productoCategoria1.setDescripcion(txfDescripcion.getText());
        if (accion.equalsIgnoreCase("agregar")){
            validarOperacion(event,productoCategoria_dao.agregar(productoCategoria1),"Empleado "+productoCategoria1.getNombre(),"agregado","agregar");
        } else if (accion.equalsIgnoreCase("editar")) {
            productoCategoria1.setId(Integer.parseInt(txfId.getText()));
            validarOperacion(event,productoCategoria_dao.actualizar(productoCategoria1),"Empleado "+productoCategoria1.getNombre(),"editado","editar");
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
    public void setData(ProductoCategoria productoCategoria, String accion){
        this.accion = accion;
        if (accion.equalsIgnoreCase("agregar")){
            lblAccionTitulo.setText("Agregar");
            lblId.setVisible(false);
            txfId.setVisible(false);
            btnAccion.setText("Agregar");
        } else if (accion.equalsIgnoreCase("editar")) {
            this.productoCategoria = productoCategoria;
            lblAccionTitulo.setText("Editar");
            btnAccion.setText("Editar");
            txfId.setText(String.valueOf(productoCategoria.getId()));
            txfNombre.setText(productoCategoria.getNombre());
            txfDescripcion.setText(productoCategoria.getDescripcion());
        }
    }
}
