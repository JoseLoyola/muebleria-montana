package com.muebleriamontana.controller.pantallas;

import com.itextpdf.text.Document;
import com.muebleriamontana.Montana;
import com.muebleriamontana.controller.alertas.ErrorController;
import com.muebleriamontana.controller.alertas.ExitoController;
import com.muebleriamontana.correo.Correo;
import com.muebleriamontana.model.*;
import com.muebleriamontana.modelDAO.EnvioDetalle_DAO;
import com.muebleriamontana.modelDAO.EnvioEstado_DAO;
import com.muebleriamontana.modelDAO.Envio_DAO;
import com.muebleriamontana.modelDAO.Sucursal_DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Envio_PlantillaController implements Initializable {

    @FXML
    private Label lblAccion;

    @FXML
    private Label lblId;

    @FXML
    private TextField txfId;

    @FXML
    private ComboBox<Sucursal> cbxEmisor;

    @FXML
    private ComboBox<Sucursal> cbxReceptor;

    @FXML
    private ComboBox<EnvioEstado> cbxEstado;

    @FXML
    private TextField txfFecha;

    @FXML
    private TextField txfHora;

    @FXML
    private TextArea txfDescripcion;

    @FXML
    private Button btnAccion;

    private Envio envio = new Envio();
    private Envio_DAO envio_dao = new Envio_DAO();
    private Sucursal_DAO sucursalDao = new Sucursal_DAO();
    private EnvioEstado_DAO envioEstado_dao = new EnvioEstado_DAO();
    private EnvioDetalle_DAO envioDetalleDAO = new EnvioDetalle_DAO();
    private FXMLLoader loader;
    private Parent root = null;
    private Scene scene = null;
    private Stage stage = null;
    private String accion=null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbxEmisor.setItems(sucursalDao.listarOL());
        cbxReceptor.setItems(sucursalDao.listarOL());
        cbxEstado.setItems(envioEstado_dao.listarOL());

    }

    @FXML
    void click_btnAccion(ActionEvent event) throws IOException, AddressException {
        envio.setEmisor(cbxEmisor.getValue());
        envio.setReceptor(cbxReceptor.getValue());
        envio.setEstado(cbxEstado.getValue());
        envio.setFecha(txfFecha.getText());
        envio.setHora(txfHora.getText());
        envio.setDescripcion(txfDescripcion.getText());
        if (accion.equalsIgnoreCase("agregar")){
            validarOperacion(event,envio_dao.agregar(envio),"Envio "+envio.getId()+"_"+envio.getEmisor()+"-"+envio.getReceptor(),"agregado","agregar");

            enviarCorreo();
        } else if (accion.equalsIgnoreCase("editar")) {
            envio.setId(Integer.parseInt(txfId.getText()));
            validarOperacion(event,envio_dao.actualizar(envio),"Envio "+envio.getId()+"_"+envio.getEmisor()+"-"+envio.getReceptor(),"editado","editar");
        }
    }

    private void enviarCorreo() throws AddressException {
        Address[] destinatarios = new InternetAddress[2];
        destinatarios[0] = new InternetAddress(cbxEmisor.getValue().getEncargado().getCorreo());
        destinatarios[1] = new InternetAddress(cbxReceptor.getValue().getEncargado().getCorreo());
        String asunto = "Envio "+envio.getId()+"_"+envio.getEmisor()+"-"+envio.getReceptor()+" agregado";
        StringBuilder contenido = new StringBuilder("Envio agregado");
        Correo correo = new Correo(destinatarios,asunto,contenido);
    }

    private void validarOperacion(ActionEvent event, Boolean accion, String objeto, String nombre, String nombre2) throws IOException{
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
    Document document = new Document();
    DateTimeFormatter fecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    public void setData(Envio envio, String accion) throws SQLException {
        this.accion = accion;
        if (accion.equalsIgnoreCase("agregar")){
            lblAccion.setText("Agregar");
            lblId.setVisible(false);
            txfId.setVisible(false);
            btnAccion.setText("Agregar");
            txfFecha.setText(fecha.format(now));
            txfHora.setText(hora.format(now));
            cbxEstado.setValue(envioEstado_dao.busquedaID(1));
        } else if (accion.equalsIgnoreCase("editar")) {
            this.envio = envio;
            lblAccion.setText("Editar");
            btnAccion.setText("Editar");
            txfId.setText(String.valueOf(envio.getId()));
            cbxEmisor.setValue(envio.getEmisor());
            cbxReceptor.setValue(envio.getReceptor());
            cbxEstado.setValue(envio.getEstado());
            txfFecha.setText(envio.getFecha());
            txfHora.setText(envio.getHora());
            txfDescripcion.setText(envio.getDescripcion());
        }
    }

}
