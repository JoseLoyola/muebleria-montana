package com.muebleriamontana.controller.pantallas;

import com.muebleriamontana.Montana;
import com.muebleriamontana.controller.alertas.ErrorController;
import com.muebleriamontana.controller.alertas.ExitoController;
import com.muebleriamontana.model.Producto;
import com.muebleriamontana.model.ProductoCategoria;
import com.muebleriamontana.modelDAO.ProductoCategoria_DAO;
import com.muebleriamontana.modelDAO.Producto_DAO;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class Producto_PlantillaController implements Initializable {

    @FXML
    private Button btnAccion;

    @FXML
    private Button btnSelecionarImagen;

    @FXML
    private ComboBox<ProductoCategoria> cbxCategoria;

    @FXML
    private ImageView imgProducto;

    @FXML
    private Label lblAccionTitulo;

    @FXML
    private Label lblSku;

    @FXML
    private TextField txfCosto;

    @FXML
    private TextArea txfDescripcion;

    @FXML
    private TextField txfNombre;

    @FXML
    private TextField txfSKU;

    @FXML
    private TextField txfStock;

    FXMLLoader loader;
    Parent root = null;
    Scene scene = null;
    Stage stage = null;
    String accion = null;
    Producto producto = new Producto();
    Producto_DAO producto_dao = new Producto_DAO();
    ProductoCategoria_DAO productoCategoria_dao = new ProductoCategoria_DAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxCategoria.setItems(productoCategoria_dao.listarOL());
    }

    @FXML
    void clck_btnAccion(ActionEvent event) throws IOException {
        Producto producto1 = new Producto();
        producto1.setNombre(txfNombre.getText());
        producto1.setCosto(Double.valueOf(txfCosto.getText()));
        producto1.setStock(Integer.parseInt(txfStock.getText()));
        producto1.setCategoriaProducto(cbxCategoria.getValue());
        producto1.setDescripcion(txfDescripcion.getText());
        if (accion.equalsIgnoreCase("agregar")){
            validarOperacion(event,producto_dao.agregar(producto1),"Producto "+producto1.getNombre(),"agregado","agregar");
        } else if (accion.equalsIgnoreCase("editar")) {
            producto1.setSku(Integer.parseInt(txfSKU.getText()));
            validarOperacion(event,producto_dao.actualizar(producto1),"Empleado "+producto1.getNombre(),"editado","editar");
        }
        if (selectedFile != null) {

        }
    }

    FileChooser fileChooser = new FileChooser();
    Path desktopPath = FileSystems.getDefault().getPath(System.getProperty("user.home"), "Desktop");
    File selectedFile;
    Image imageProducto;

    @FXML
    void click_btnSelecionarImagen(ActionEvent event) throws IOException {
        fileChooser.setTitle("Seleciona una imagen");
        fileChooser.setInitialDirectory(desktopPath.toFile());
        FileChooser.ExtensionFilter imageFilter =
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(imageFilter);
        selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            imageProducto = new Image(String.valueOf(selectedFile));
            imgProducto.setImage(imageProducto);

            String fileName = selectedFile.getName();
//            String destinationPath = "img/" + fileName;
//            String destinationPath = Montana.class.getResource("img") + fileName;
            String destinationPath = "C:/Users/Toshiba/IdeaProjects/muebleriaMontana/target/classes/com/muebleriamontana/img/" + fileName;
            File destinationFile = new File(destinationPath);
            Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
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
    public void setData(Producto producto, String accion){
        this.accion = accion;
        if (accion.equalsIgnoreCase("agregar")){
//            lblAccion.setText("Agregar");
            txfSKU.setVisible(false);
            btnAccion.setText("Agregar");
            Image image = new Image(Montana.class.getResourceAsStream("img/productos/agregarImagen01.png"));
            imgProducto.setImage(image);
        } else if (accion.equalsIgnoreCase("editar")) {
            this.producto = producto;
//            lblAccion.setText("Editar");
            btnAccion.setText("Editar");
            txfSKU.setText(String.valueOf(producto.getSku()));
            txfNombre.setText(producto.getNombre());
            txfCosto.setText(String.valueOf(producto.getCosto()));
            txfStock.setText(String.valueOf(producto.getStock()));
            cbxCategoria.setValue(producto.getCategoriaProducto());
            try {
                Image image = new Image(Montana.class.getResourceAsStream("img/productos/"+producto.getSku()+".jpg"));
                imgProducto.setImage(image);
            }catch (Exception exception){
                Image image = new Image(Montana.class.getResourceAsStream("img/productos/404_productos.jpg"));
                imgProducto.setImage(image);
            }
        }
    }
}
