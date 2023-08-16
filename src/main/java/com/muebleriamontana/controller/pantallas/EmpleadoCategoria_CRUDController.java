package com.muebleriamontana.controller.pantallas;

import com.muebleriamontana.Montana;
import com.muebleriamontana.controller.alertas.EliminarController;
import com.muebleriamontana.model.EmpleadoCategoria;
import com.muebleriamontana.modelDAO.EmpleadoCategoria_DAO;
import javafx.collections.FXCollections;
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

public class EmpleadoCategoria_CRUDController implements Initializable {

    @FXML
    private TextField txfBusqueda;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnActualizar;

    @FXML
    private TableView<EmpleadoCategoria> tblEmpleadoCategorias;

    @FXML
    private TableColumn<?, ?> tbcId;

    @FXML
    private TableColumn<?, ?> tbcNombre;

    @FXML
    private TableColumn<?, ?> tbcDescripcion;

    @FXML
    private TableColumn<EmpleadoCategoria, String> tbcAcciones;

    EmpleadoCategoria_DAO empleadoCategoriaDao = new EmpleadoCategoria_DAO();
    ObservableList<EmpleadoCategoria> empleadoCategoriaObservableList = FXCollections.observableArrayList();
    private final Image imgEditar = new Image(Montana.class.getResourceAsStream("img/sistema/editar.png"));
    private final Image imgEliminar = new Image(Montana.class.getResourceAsStream("img/sistema/eliminar.png"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        Callback<TableColumn<EmpleadoCategoria, String>, TableCell<EmpleadoCategoria, String>> accionesCell = param -> {
            final TableCell<EmpleadoCategoria, String> cell = new TableCell<EmpleadoCategoria, String>() {
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
                                EmpleadoCategoria empleadoCategoria = getTableView().getItems().get(getIndex());
                                FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/pantallas/empleadoCategoria_Plantilla.fxml"));
                                Parent root = loader.load();
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                Image image = new Image(Montana.class.getResourceAsStream("img/sistema/editar01.png"));
                                stage.setTitle("Editar Categoria Empleado "+empleadoCategoria.getId());
                                stage.getIcons().add(image);
                                stage.setScene(scene);
                                stage.show();
                                EmpleadoCategoria_PlantillaController empleadoCategoriaPlantillaController = loader.getController();
                                empleadoCategoriaPlantillaController.setData(empleadoCategoria,"editar");
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
                            EmpleadoCategoria empleadoCategoria = getTableView().getItems().get(getIndex());
                            try {
                                FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/alertas/eliminar.fxml"));
                                Parent root = loader.load();
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                Image image = new Image(Montana.class.getResourceAsStream("img/sistema/alerta01.png"));
                                stage.setTitle("Eliminar la Categoria Empleado "+empleadoCategoria.getNombre());
                                stage.getIcons().add(image);
                                stage.setScene(scene);
                                stage.initStyle(StageStyle.UNDECORATED);
                                stage.show();
                                EliminarController eliminarController = loader.getController();
                                eliminarController.setData(empleadoCategoria);
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
        listar(empleadoCategoriaDao.listarOL());
    }

    private void listar(ObservableList<EmpleadoCategoria> empleadoCategoriaObservableList) {
        this.empleadoCategoriaObservableList = empleadoCategoriaObservableList;
        tblEmpleadoCategorias.setItems(empleadoCategoriaObservableList);
    }

    @FXML
    void click_btnBuscar(ActionEvent event) {
        listar(empleadoCategoriaDao.busquedaGeneral(txfBusqueda.getText()));
    }

    @FXML
    void click_btnAgregar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/pantallas/empleadoCategoria_Plantilla.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        EmpleadoCategoria_PlantillaController empleadoCategoriaPlantillaController = loader.getController();
        empleadoCategoriaPlantillaController.setData(null,"agregar");
    }

    @FXML
    void click_btnActualizar(ActionEvent event) {
        txfBusqueda.setText("");
        listar(empleadoCategoriaDao.listarOL());
    }

    @FXML
    void click_btnExportar(ActionEvent event) {

    }

}
