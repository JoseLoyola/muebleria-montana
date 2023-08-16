package com.muebleriamontana.controller.pantallas;

import com.muebleriamontana.Montana;
import com.muebleriamontana.model.Producto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class Producto_CardController implements Initializable {

    @FXML
    private ImageView imgProducto;

    @FXML
    private Label lblCodigo;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblCosto;

    @FXML
    private Label lblStock;

    @FXML
    private Label lblCategoria;

    private Producto producto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setData(Producto producto){
        this.producto = producto;
        lblCodigo.setText(String.valueOf(producto.getSku()));
        lblNombre.setText(producto.getNombre());
        lblCosto.setText(Montana.MONEDA + " " + producto.getCosto());
        lblStock.setText(String.valueOf(producto.getStock()));
        lblCategoria.setText(producto.getCategoriaProducto().getNombre());
        try {
            Image image = new Image(Montana.class.getResourceAsStream("img/productos/"+producto.getSku()+".jpg"));
            imgProducto.setImage(image);
        }catch (Exception exception){
            Image image = new Image(Montana.class.getResourceAsStream("img/productos/404_productos.jpg"));
            imgProducto.setImage(image);
        }

    }

}
