package com.muebleriamontana.controller.pantallas;

import com.muebleriamontana.Montana;
import com.muebleriamontana.controller.alertas.ErrorController;
import com.muebleriamontana.controller.alertas.ExitoController;
import com.muebleriamontana.model.Empleado;
import com.muebleriamontana.model.EmpleadoCategoria;
import com.muebleriamontana.modelDAO.EmpleadoCategoria_DAO;
import com.muebleriamontana.modelDAO.Empleado_DAO;
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

public class Empleado_PlantillaController implements Initializable {

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
    private TextField txfPaterno;

    @FXML
    private TextField txfMaterno;

    @FXML
    private TextField txfCorreo;

    @FXML
    private TextField txfUsuario;

    @FXML
    private TextField txfContraseña;

    @FXML
    private ComboBox<EmpleadoCategoria> cbxCategoria;

    @FXML
    private Button btnAccion;
    FXMLLoader loader;
    Parent root = null;
    Scene scene = null;
    Stage stage = null;
    String accion = null;
    Empleado empleado = new Empleado();
    Empleado_DAO empleado_dao = new Empleado_DAO();
    EmpleadoCategoria_DAO empleadoCategoriaDao = new EmpleadoCategoria_DAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbxCategoria.setItems(empleadoCategoriaDao.listarOL());
    }

    @FXML
    void click_btnSelecionarImagen(ActionEvent event) {

    }

    @FXML
    void click_btnAccion(ActionEvent event) throws IOException {
        Empleado empleado1 = new Empleado();
        empleado1.setNombre(txfNombre.getText());
        empleado1.setPaterno(txfPaterno.getText());
        empleado1.setMaterno(txfMaterno.getText());
        empleado1.setCorreo(txfCorreo.getText());
        empleado1.setUsuario(txfUsuario.getText());
        empleado1.setContrasena(txfContraseña.getText());
        empleado1.setEmpleadoCategoria(cbxCategoria.getValue());
        if (accion.equalsIgnoreCase("agregar")){
            validarOperacion(event,empleado_dao.agregar(empleado1),"Empleado "+empleado1.getNombre(),"agregado","agregar");
        } else if (accion.equalsIgnoreCase("editar")) {
            empleado1.setId(Integer.parseInt(txfId.getText()));
            validarOperacion(event,empleado_dao.actualizar(empleado1),"Empleado "+empleado1.getNombre(),"editado","editar");
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
    public void setData(Empleado empleado, String accion){
        this.accion = accion;
        if (accion.equalsIgnoreCase("agregar")){
            lblAccionTitulo.setText("Agregar");
            lblId.setVisible(false);
            txfId.setVisible(false);
            btnAccion.setText("Agregar");
            Image image = new Image(Montana.class.getResourceAsStream("img/productos/agregarImagen01.png"));
            imgProducto.setImage(image);
        } else if (accion.equalsIgnoreCase("editar")) {
            this.empleado = empleado;
            lblAccionTitulo.setText("Editar");
            btnAccion.setText("Editar");
            txfId.setText(String.valueOf(empleado.getId()));
            txfNombre.setText(empleado.getNombre());
            txfPaterno.setText(empleado.getPaterno());
            txfMaterno.setText(empleado.getMaterno());
            txfCorreo.setText(empleado.getCorreo());
            txfUsuario.setText(empleado.getUsuario());
            txfContraseña.setText(empleado.getContrasena());
            cbxCategoria.setValue(empleado.getEmpleadoCategoria());
        }
    }
}
