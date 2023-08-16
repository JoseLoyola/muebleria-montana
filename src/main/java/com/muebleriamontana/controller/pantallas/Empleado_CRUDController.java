package com.muebleriamontana.controller.pantallas;

import com.muebleriamontana.Montana;
import com.muebleriamontana.controller.PrincipalController;
import com.muebleriamontana.controller.alertas.EliminarController;
import com.muebleriamontana.model.Empleado;
import com.muebleriamontana.modelDAO.Empleado_DAO;
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

public class Empleado_CRUDController implements Initializable {

    @FXML
    private TextField txfBusqueda;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnActualizar;

    @FXML
    private TableView<Empleado> tblEmpleados;

    @FXML
    private TableColumn<?, ?> tbcId;

    @FXML
    private TableColumn<?, ?> tbcNombre;

    @FXML
    private TableColumn<?, ?> tbcPaterno;

    @FXML
    private TableColumn<?, ?> tbcMaterno;

    @FXML
    private TableColumn<?, ?> tbcCorreo;

    @FXML
    private TableColumn<?, ?> tbcCategoria;

    @FXML
    private TableColumn<Empleado, String> tbcAcciones;
    private Empleado_DAO empleadoDao = new Empleado_DAO();
    private ObservableList<Empleado> empleadoObservableList = FXCollections.observableArrayList();
    private final Image imgEditar = new Image(Montana.class.getResourceAsStream("img/sistema/editar.png"));
    private final Image imgEliminar = new Image(Montana.class.getResourceAsStream("img/sistema/eliminar.png"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbcPaterno.setCellValueFactory(new PropertyValueFactory<>("paterno"));
        tbcMaterno.setCellValueFactory(new PropertyValueFactory<>("materno"));
        tbcCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        tbcCategoria.setCellValueFactory(new PropertyValueFactory<>("empleadoCategoria"));
        Callback<TableColumn<Empleado, String>, TableCell<Empleado, String>> accionesCell = param -> {
            final TableCell<Empleado, String> cell = new TableCell<Empleado, String>() {
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
                                Empleado empleado = getTableView().getItems().get(getIndex());
                                FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/pantallas/empleado_Plantilla.fxml"));
                                Parent root = loader.load();
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                Image image = new Image(Montana.class.getResourceAsStream("img/sistema/editar01.png"));
                                stage.setTitle("Editar al empleado "+empleado.getId());
                                stage.getIcons().add(image);
                                stage.setScene(scene);
                                stage.show();
                                Empleado_PlantillaController empleado_plantillaController = loader.getController();
                                empleado_plantillaController.setData(empleado,"editar");
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
                            Empleado empleado = getTableView().getItems().get(getIndex());
                            try {
                                FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/alertas/eliminar.fxml"));
                                Parent root = loader.load();
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                Image image = new Image(Montana.class.getResourceAsStream("img/sistema/alerta01.png"));
                                stage.setTitle("Eliminar al empleado "+empleado.getNombre());
                                stage.getIcons().add(image);
                                stage.setScene(scene);
                                stage.initStyle(StageStyle.UNDECORATED);
                                stage.show();
                                EliminarController eliminarController = loader.getController();
                                eliminarController.setData(empleado);
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
        listar(empleadoDao.listarOL());
    }

    private void listar(ObservableList<Empleado> empleadoObservableList) {
        this.empleadoObservableList=empleadoObservableList;
        tblEmpleados.setItems(empleadoObservableList);
    }


    @FXML
    void click_btnBuscar(ActionEvent event) {
        listar(empleadoDao.busquedaGeneral(txfBusqueda.getText()));
    }

    @FXML
    void click_btnAgregar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/pantallas/empleado_Plantilla.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        Empleado_PlantillaController empleado_plantillaController = loader.getController();
        empleado_plantillaController.setData(null,"agregar");
    }

    @FXML
    void click_btnActualizar(ActionEvent event) {
        txfBusqueda.setText("");
        listar(empleadoDao.listarOL());
    }

}
