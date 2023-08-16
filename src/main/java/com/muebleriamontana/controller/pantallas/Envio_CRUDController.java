package com.muebleriamontana.controller.pantallas;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.muebleriamontana.Montana;
import com.muebleriamontana.controller.PrincipalController;
import com.muebleriamontana.controller.alertas.EliminarController;
import com.muebleriamontana.model.Empleado;
import com.muebleriamontana.model.Envio;
import com.muebleriamontana.modelDAO.Envio_DAO;
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

public class Envio_CRUDController implements Initializable {

    @FXML
    private TextField txfBusqueda;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnActualizar;

    @FXML
    private TableView<Envio> tblEnvios;

    @FXML
    private TableColumn<Envio, ?> tbcId;

    @FXML
    private TableColumn<Envio, ?> tbcEmisor;

    @FXML
    private TableColumn<Envio, ?> tbcReceptor;

    @FXML
    private TableColumn<Envio, ?> tbcEstado;

    @FXML
    private TableColumn<Envio, ?> tbcFecha;

    @FXML
    private TableColumn<Envio, ?> tbcHora;

    @FXML
    private TableColumn<Envio, String> tbcAcciones;

    private final Image imgDetalles = new Image(Montana.class.getResourceAsStream("img/sistema/detalles01.png"));
    private final Image imgEditar = new Image(Montana.class.getResourceAsStream("img/sistema/editar.png"));
    private final Image imgEliminar = new Image(Montana.class.getResourceAsStream("img/sistema/eliminar.png"));

    Envio_DAO envio_dao = new Envio_DAO();

    ObservableList<Envio> envioObservableList = FXCollections.observableArrayList();
    public Empleado empleado;
    public void setEmpleado(Empleado empleado){
        this.empleado = empleado;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcEmisor.setCellValueFactory(new PropertyValueFactory<>("emisor"));
        tbcReceptor.setCellValueFactory(new PropertyValueFactory<>("receptor"));
        tbcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tbcHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        Callback<TableColumn<Envio, String>, TableCell<Envio, String>> accionesCell = param -> {
            final TableCell<Envio, String> cell = new TableCell<Envio, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty){
                    setGraphic(null);
                    setText(null);
                }else {
                    ImageView imvDetalles = new ImageView(imgDetalles);
                    imvDetalles.setFitWidth(20);
                    imvDetalles.setFitHeight(20);
                    final Button btnDetalles = new Button();
                    btnDetalles.setGraphic(imvDetalles);
                    btnDetalles.setOnMouseClicked(mouseEvent -> {
                        try {
                            Envio envio = getTableView().getItems().get(getIndex());
                            FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/pantallas/envio_ListarDetalles.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            Image image = new Image(Montana.class.getResourceAsStream("img/sistema/detalles01.png"));
                            stage.setTitle("Ver datalles del envio Id:"+envio.getId());
                            stage.getIcons().add(image);
                            stage.setScene(scene);
                            stage.show();
                            Envio_ListarDetallesController envioPlantillaController = loader.getController();
                            envioPlantillaController.setData(envio);
                        } catch (IOException | SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    ImageView imvEditar = new ImageView(imgEditar);
                    imvEditar.setFitWidth(20);
                    imvEditar.setFitHeight(20);
                    final Button btnEditar = new Button();
                    btnEditar.setGraphic(imvEditar);
                    btnEditar.setOnMouseClicked(mouseEvent -> {
                        try {
                            Envio envio = getTableView().getItems().get(getIndex());
                            FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/pantallas/envio_Plantilla.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            Image image = new Image(Montana.class.getResourceAsStream("img/sistema/editar01.png"));
                            stage.setTitle("Editar producto "+envio.getId());
                            stage.getIcons().add(image);
                            stage.setScene(scene);
                            stage.show();
                            Envio_PlantillaController envioPlantillaController = loader.getController();
                            envioPlantillaController.setData(envio,"editar");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    ImageView imvEliminar = new ImageView(imgEliminar);
                    imvEliminar.setFitWidth(20);
                    imvEliminar.setFitHeight(20);
                    final Button btnEliminar = new Button();
                    btnEliminar.setGraphic(imvEliminar);
                    btnEliminar.setOnMouseClicked(mouseEvent -> {
                        Envio envio = getTableView().getItems().get(getIndex());
                        try {
                            FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/alertas/eliminar.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            Image image = new Image(Montana.class.getResourceAsStream("img/sistema/alerta01.png"));
                            stage.setTitle("Eliminar envio "+envio.getId());
                            stage.getIcons().add(image);
                            stage.setScene(scene);
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.show();
                            EliminarController eliminarController = loader.getController();
                            eliminarController.setData(envio);
                        } catch (IOException ex) {
                            Logger.getLogger(Producto_CRUDController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                    HBox hbxAcciones = new HBox(btnDetalles, btnEditar, btnEliminar);
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
        listar(envio_dao.listarOL());
    }

    @FXML
    void click_btnActualizar(ActionEvent event) {
        listar(envio_dao.listarOL());
    }
    private void listar(ObservableList<Envio> envioObservableList){
        this.envioObservableList=envioObservableList;
        tblEnvios.setItems(envioObservableList);
    }

    @FXML
    void click_btnAgregar(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/pantallas/envio_Plantilla.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        Envio_PlantillaController empleado_plantillaController = loader.getController();
        empleado_plantillaController.setData(null,"agregar");
    }

    @FXML
    void click_btnBuscar(ActionEvent event) {
        listar(envio_dao.busquedaGeneral(txfBusqueda.getText()));
    }

}
