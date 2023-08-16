package com.muebleriamontana.controller.alertas;

import com.muebleriamontana.Montana;
import com.muebleriamontana.model.*;
import com.muebleriamontana.modelDAO.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EliminarController implements Initializable {

    @FXML
    private Button btnCancelar;

    @FXML
    private ImageView btnCerrar;

    @FXML
    private Button btnEliminar;

    @FXML
    private ImageView imgAlerta;

    @FXML
    private Label lblObjetoMensaje;

    @FXML
    private Label lblObjetoTitulo;

    @FXML
    private Pane pnCentro;

    Object objeto;
    Object objetoDAO;
    FXMLLoader loader;
    Parent root = null;
    Scene scene = null;
    Stage stage = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void click_btnCerrar(MouseEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
    @FXML
    void click_btnEliminar(ActionEvent event) throws IOException {
        if (objeto instanceof Producto){
            Producto_DAO productoDao = new Producto_DAO();
            validarOperacion(event, productoDao.eliminar(((Producto) objeto).getSku()),
                    "Producto", String.valueOf(((Producto) objeto).getSku()));
        } else if (objeto instanceof Empleado) {
            Empleado_DAO empleadoDao = new Empleado_DAO();
            validarOperacion(event, empleadoDao.eliminar(((Empleado) objeto).getId()),
                    "Empleado",((Empleado) objeto).getNombre());
        } else if (objeto instanceof EmpleadoCategoria) {
            EmpleadoCategoria_DAO empleadoCategoriaDao = new EmpleadoCategoria_DAO();
            validarOperacion(event, empleadoCategoriaDao.eliminar(((EmpleadoCategoria) objeto).getId()),
                    "Categoria de Empleado",((EmpleadoCategoria) objeto).getNombre());
        } else if (objeto instanceof Envio) {
            Envio_DAO envioDao = new Envio_DAO();
            validarOperacion(event, envioDao.eliminar(((Envio) objeto).getId()),
                    "Envio", String.valueOf(((Envio) objeto).getId()));
        } else if (objeto instanceof EnvioDetalle) {
            EnvioDetalle_DAO envioDetalleDao = new EnvioDetalle_DAO();
            validarOperacion(event, envioDetalleDao.eliminar(((EnvioDetalle) objeto).getEnvio().getId()),
                    "Detalle del Envio", String.valueOf(((EnvioDetalle) objeto).getEnvio().getId()));
        } else if (objeto instanceof EnvioEstado) {
            EnvioEstado_DAO envioEstadoDao = new EnvioEstado_DAO();
            validarOperacion(event, envioEstadoDao.eliminar(((EnvioEstado) objeto).getId()),
                    "Estado de envio",((EnvioEstado) objeto).getNombre());
//        } else if (objeto instanceof Inventario) {
//            Inventario_DAO inventarioDao = new Inventario_DAO();
//            validarOperacion(event, inventarioDao.eliminar(((Inventario) objeto).getId()),
//                    "Empleado",((Inventario) objeto).get());
        } else if (objeto instanceof ProductoCategoria) {
            ProductoCategoria_DAO productoCategoriaDao = new ProductoCategoria_DAO();
            validarOperacion(event, productoCategoriaDao.eliminar(((ProductoCategoria) objeto).getId()),
                    "Categoria de Producto",((ProductoCategoria) objeto).getNombre());
        } else if (objeto instanceof Sucursal) {
            Sucursal_DAO sucursalDao = new Sucursal_DAO();
            validarOperacion(event, sucursalDao.eliminar(((Sucursal) objeto).getId()),
                    "Sucursal",((Sucursal) objeto).getNombre());
        }
    }
    private void validarOperacion(ActionEvent event, Boolean eliminado, String objeto, String nombre) throws IOException {
        if (eliminado){
            cargarPantalla(event,true, objeto+" "+nombre+" eliminado");
        }else {
            cargarPantalla(event,false, "eliminar "+objeto+" "+nombre);
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

    @FXML
    void click_btnCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    public void setData(Object objeto){
        if (objeto instanceof Producto){
            this.objeto =objeto;
            lblObjetoMensaje.setText("el producto SKU: "+ ((Producto) objeto).getSku());
            lblObjetoTitulo.setText("Producto");
        } else if (objeto instanceof EmpleadoCategoria){
            this.objeto =objeto;
            lblObjetoMensaje.setText("la categoria: "+ ((EmpleadoCategoria) objeto).getNombre());
            lblObjetoTitulo.setText("Categoria Empleado");
        }else if (objeto instanceof Empleado){
            this.objeto =objeto;
            lblObjetoMensaje.setText("el empleado: "+ ((Empleado) objeto).getNombre());
            lblObjetoTitulo.setText("Empleado");
        }else if (objeto instanceof Envio){
            this.objeto =objeto;
            lblObjetoMensaje.setText("el envio: "+ ((Envio) objeto).getId()+"_"+((Envio) objeto).getEmisor()+"-"+((Envio) objeto).getReceptor());
            lblObjetoTitulo.setText("Envio");
        }else if (objeto instanceof EnvioDetalle){
            this.objeto =objeto;
            lblObjetoMensaje.setText("el detalle del envio: "+ ((EnvioDetalle) objeto).getProducto());
            lblObjetoTitulo.setText("Detalle el envio");
        }else if (objeto instanceof EnvioEstado){
            this.objeto =objeto;
            lblObjetoMensaje.setText("el estado del envio: "+ ((EnvioEstado) objeto).getNombre());
            lblObjetoTitulo.setText("Estado del envio");
        }else if (objeto instanceof ProductoCategoria){
            this.objeto =objeto;
            lblObjetoMensaje.setText("la categoria del producto: "+ ((ProductoCategoria) objeto).getNombre());
            lblObjetoTitulo.setText("Categoria del Producto");
        }else if (objeto instanceof Sucursal){
            this.objeto =objeto;
            lblObjetoMensaje.setText("la sucursal: "+ ((Sucursal) objeto).getNombre());
            lblObjetoTitulo.setText("Sucursal");
        }

    }
}
