package com.muebleriamontana.controller.pantallas;

import com.muebleriamontana.Montana;
import com.muebleriamontana.controller.alertas.EliminarController;
import com.muebleriamontana.model.Sucursal;
import com.muebleriamontana.modelDAO.Sucursal_DAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sucursal_CRUDController implements Initializable {

    @FXML
    private TextField txfBusqueda;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnActualizar;

    @FXML
    private TableView<Sucursal> tbvSucursales;

    @FXML
    private TableColumn<?, ?> tbcId;

    @FXML
    private TableColumn<?, ?> tbcNombre;

    @FXML
    private TableColumn<?, ?> tbcDireccion;

    @FXML
    private TableColumn<?, ?> tbcDistrito;

    @FXML
    private TableColumn<?, ?> tbcEncargado;

    @FXML
    private TableColumn<Sucursal, String> tbcAcciones;

    Sucursal_DAO sucursalDao = new Sucursal_DAO();
    private final Image imgEditar = new Image(Montana.class.getResourceAsStream("img/sistema/editar.png"));
    private final Image imgEliminar = new Image(Montana.class.getResourceAsStream("img/sistema/eliminar.png"));
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tbcDistrito.setCellValueFactory(new PropertyValueFactory<>("distrito"));
        tbcEncargado.setCellValueFactory(new PropertyValueFactory<>("encargado"));
        Callback<TableColumn<Sucursal, String>, TableCell<Sucursal, String>> accionesCell = param -> {
            final TableCell<Sucursal, String> cell = new TableCell<Sucursal, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty){
                        setGraphic(null);
                        setText(null);
                    }else {
                        ImageView imvEditar = new ImageView(imgEditar);
                        imvEditar.setFitWidth(20);
                        imvEditar.setFitHeight(20);
                        final Button btnEditar = new Button();
                        btnEditar.setGraphic(imvEditar);
                        btnEditar.setOnMouseClicked(event -> {
                            try {
                                Sucursal sucursal = getTableView().getItems().get(getIndex());
                                FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/pantallas/sucursal_Plantilla.fxml"));
                                Parent root = loader.load();
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                Image image = new Image(Montana.class.getResourceAsStream("img/sistema/editar01.png"));
                                stage.setTitle("Editar al empleado "+sucursal.getId());
                                stage.getIcons().add(image);
                                stage.setScene(scene);
                                stage.show();
                                Sucursal_PlantillaController sucursalPlantillaController = loader.getController();
                                sucursalPlantillaController.setData(sucursal,"editar");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        ImageView imvEliminar = new ImageView(imgEliminar);
                        imvEliminar.setFitWidth(20);
                        imvEliminar.setFitHeight(20);
                        final Button btnEliminar = new Button();
                        btnEliminar.setGraphic(imvEliminar);
                        btnEliminar.setOnMouseClicked(event -> {
                            Sucursal sucursal = getTableView().getItems().get(getIndex());
                            try {
                                FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/alertas/eliminar.fxml"));
                                Parent root = loader.load();
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                Image image = new Image(Montana.class.getResourceAsStream("img/sistema/alerta01.png"));
                                stage.setTitle("Eliminar la sucursal "+sucursal.getNombre());
                                stage.getIcons().add(image);
                                stage.setScene(scene);
                                stage.initStyle(StageStyle.UNDECORATED);
                                stage.show();
                                EliminarController eliminarController = loader.getController();
                                eliminarController.setData(sucursal);
                            } catch (IOException ex) {
                                Logger.getLogger(Producto_CRUDController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        HBox hbxAcciones = new HBox(btnEditar, btnEliminar);
                        HBox.setMargin(btnEditar, new Insets(2,2,0,3));
                        HBox.setMargin(btnEliminar, new Insets(2, 3, 0, 2));
                        setGraphic(hbxAcciones);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        tbcAcciones.setCellFactory(accionesCell);
        listar(sucursalDao.listarOL());
    }

    private void listar(ObservableList<Sucursal> sucursals) {
        tbvSucursales.setItems(sucursals);
    }

    @FXML
    void click_btnBuscar(ActionEvent event) {
        listar(sucursalDao.busquedaGeneral(txfBusqueda.getText()));
    }

    @FXML
    void click_btnAgregar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/pantallas/sucursal_Plantilla.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        Sucursal_PlantillaController sucursal_plantillaController = loader.getController();
        sucursal_plantillaController.setData(null,"agregar");
    }

    @FXML
    void click_btnActualizar(ActionEvent event) {
        listar(sucursalDao.listarOL());
    }

}
