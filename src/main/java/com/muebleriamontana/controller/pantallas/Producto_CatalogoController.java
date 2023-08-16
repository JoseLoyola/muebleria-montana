package com.muebleriamontana.controller.pantallas;

import com.muebleriamontana.Montana;
import com.muebleriamontana.model.Producto;
import com.muebleriamontana.modelDAO.ProductoCategoria_DAO;
import com.muebleriamontana.modelDAO.Producto_DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Producto_CatalogoController implements Initializable {

    @FXML
    private TextField txfBusqueda;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private FlowPane grdProductos;

    Producto producto = new Producto();
    Producto_DAO productoDAO = new Producto_DAO();
    ProductoCategoria_DAO productoCategoriaDAO = new ProductoCategoria_DAO();
    private ObservableList<Producto> productoObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imrpimir(productoDAO.listarOL());
    }

    @FXML
    void click_btnBuscar(ActionEvent event) {
        imrpimir(productoDAO.busquedaGeneral(txfBusqueda.getText()));
    }

    @FXML
    void click_btnLimpiar(ActionEvent event) {
        txfBusqueda.setText("");
        imrpimir(productoDAO.listarOL());
    }
    private void imrpimir(ObservableList<Producto> productos) {
        productoObservableList.clear();
        productoObservableList.addAll(productos);
        grdProductos.getChildren().clear();
        try {
            for (int i = 0; i < productoObservableList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(Montana.class.getResource("view/pantallas/producto_Card.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                Producto_CardController itemController = fxmlLoader.getController();
                itemController.setData(productoObservableList.get(i));

                grdProductos.getChildren().add(anchorPane);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

