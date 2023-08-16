package com.muebleriamontana.controller;

import com.muebleriamontana.Montana;
import com.muebleriamontana.conexionBD.Sesion;
import com.muebleriamontana.model.Empleado;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrincipalController implements Initializable {

    @FXML
    private HBox barTop;

    @FXML
    private ImageView btnMinimizar;

    @FXML
    private ImageView btnMaximizar;

    @FXML
    private ImageView btnCerrar;

    @FXML
    private VBox barside;

    @FXML
    private ImageView imgEmpleado;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblCargo;

    @FXML
    private ScrollPane scpMenu;

    @FXML
    private Accordion acdMenu;

    @FXML
    private BorderPane brpPantalla;

    @FXML
    private Button btnCerrarSesion;

    private Empleado empleado;
    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarPantalla("producto_Catalogo");
    }

    @FXML
    void dragg_barTop(MouseEvent event) {
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    void press_barTop(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    void click_btnMinimizar(MouseEvent event) {
        Stage stage = (Stage) btnMinimizar.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void btn_btnMaximizar(MouseEvent event) {
        Stage stage = (Stage) btnMinimizar.getScene().getWindow();
        if (stage.isMaximized()){
            stage.setMaximized(false);
            btnMaximizar.setImage(new Image(Montana.class.getResourceAsStream("img/sistema/maximizarFalse.png")));
        }else{
            stage.setMaximized(true);
            btnMaximizar.setImage(new Image(Montana.class.getResourceAsStream("img/sistema/maximizarTrue.png")));
        }
    }

    @FXML
    void click_btnCerrar(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void click_btnCerrarSesion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        Sesion.getInstance().setEmpleado(null);
    }

    public void init(Stage stage){
        setStage(stage);
        this.empleado = Sesion.getInstance().getEmpleado();
        cargarDatos();
        cargarMenu();
    }
    private void setStage(Stage stage) {
        this.stage = stage;
    }
    private void cargarDatos() {
        try {
            Image image = new Image(Montana.class.getResourceAsStream("img/usuarios/"+empleado.getId()+".jpg"));
            imgEmpleado.setImage(image);
        }catch (Exception exception){
            Image image = new Image(Montana.class.getResourceAsStream("img/usuarios/404_usuarios.jpg"));
            imgEmpleado.setImage(image);
        }
        lblNombre.setText(this.empleado.getNombre());
        lblCargo.setText(this.empleado.getEmpleadoCategoria().getNombre());
    }
    private void cargarMenu(){
        VBox vbxEmpleado = new VBox();
        TitledPane tlbEmpleado = new TitledPane("Empleados", vbxEmpleado);
        VBox vbxEmpleadoCategoria = new VBox();
        TitledPane tlbEmpleadoCategoria = new TitledPane("Empleado Categoria", vbxEmpleadoCategoria);
        VBox vbxEnvio = new VBox();
        TitledPane tlbEnvio = new TitledPane("Envio", vbxEnvio);
        VBox vbxEnvioDetalle = new VBox();
        TitledPane tlbEnvioDetalle = new TitledPane("Envio Detalle", vbxEnvioDetalle);
        VBox vbxEnvioEstado = new VBox();
        TitledPane tlbEnvioEstado = new TitledPane("Envio Estado", vbxEnvioEstado);
        VBox vbxInventario = new VBox();
        TitledPane tlbInventario = new TitledPane("Inventario", vbxInventario);
        VBox vbxProducto = new VBox();
        TitledPane tlbProducto = new TitledPane("Producto", vbxProducto);
        VBox vbxProductoCategoria = new VBox();
        TitledPane tlbProductoCategoria = new TitledPane("Producto Categoria", vbxProductoCategoria);
        VBox vbxSucursal = new VBox();
        TitledPane tlbSucursal = new TitledPane("Sucursal", vbxSucursal);
        switch (empleado.getEmpleadoCategoria().getId()){
            case 1:
                vbxEmpleado.getChildren().addAll(
                    crearButton("Lista de Empleado","empleado_CRUD")
                );
                vbxEmpleadoCategoria.getChildren().addAll(
                        crearButton("Lista de Empleado Categoria","empleadoCategoria_CRUD")
                );
                vbxEnvio.getChildren().addAll(
                    crearButton("Lista de Envios", "envio_CRUD")
                );
//                vbxEnvioDetalle.getChildren().addAll(
//                    crearButton("Lista de Envio Detalle ","_CRUD")
//                );
//                vbxEnvioEstado.getChildren().addAll(
//                    crearButton("Lista de Envio Estados ","_CRUD")
//                );
//                vbxInventario.getChildren().addAll(
//                    crearButton("Lista de Inventario ","_CRUD")
//                );
                vbxProducto.getChildren().addAll(
                    crearButton("Catalogo de Productos","producto_Catalogo"),
                    crearButton("Lista de Productos","producto_CRUD")
                );
                vbxProductoCategoria.getChildren().addAll(
                    crearButton("Lista de Producto Categoria","productoCategoria_CRUD")
                );
                vbxSucursal.getChildren().addAll(
                    crearButton("Listar Sucursales","sucursal_CRUD")
                );
//                acdMenu.getPanes().addAll(tlbEmpleado,tlbEmpleadoCategoria,tlbEnvio,tlbEnvioDetalle,tlbEnvioEstado,tlbProducto,tlbProductoCategoria,tlbSucursal);
                acdMenu.getPanes().addAll(tlbEmpleado,tlbEmpleadoCategoria,tlbEnvio,tlbProducto,tlbProductoCategoria,tlbSucursal);
                break;
            case 2:
                vbxEmpleado.getChildren().addAll(
                        crearButton("Lista de Empleado","empleado_CRUD")
                );
                vbxProducto.getChildren().addAll(
                        crearButton("Catalogo de Productos","producto_Catalogo"),
                        crearButton("Lista de Productos","producto_CRUD")
                );
                vbxEnvio.getChildren().addAll(
                        crearButton("Lista de Envios", "envio_CRUD")
                );
                acdMenu.getPanes().addAll(tlbEmpleado,tlbEnvio,tlbProducto);
                break;
            case 3:
                vbxProducto.getChildren().addAll(
                        crearButton("Catalogo de Productos","producto_Catalogo"),
                        crearButton("Lista de Productos","producto_CRUD")
                );
                vbxEnvio.getChildren().addAll(
                        crearButton("Lista de Envios", "envio_CRUD")
                );
                acdMenu.getPanes().addAll(tlbEnvio,tlbProducto);
                break;
            case 4:
                vbxProducto.getChildren().addAll(
                        crearButton("Catalogo de Productos","producto_Catalogo")
                );
                acdMenu.getPanes().addAll(tlbProducto);
                break;
        }

    }
    private Button crearButton(String titulo, String fxml){
        Button button = new Button(titulo);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cargarPantalla(fxml);
            }
        });
        return button;
    }
    private void cargarPantalla(String pantalla){
        Parent root = null;
        try {
            root = FXMLLoader.load(Montana.class.getResource("view/pantallas/"+pantalla+".fxml"));
        } catch (IOException exception) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, exception);
        }
        brpPantalla.setCenter(root);
    }

}
