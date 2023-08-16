package com.muebleriamontana.controller.pantallas;

import com.muebleriamontana.correo.Correo;
import com.muebleriamontana.model.Empleado;
import com.muebleriamontana.model.Envio;
import com.muebleriamontana.model.EnvioDetalle;
import com.muebleriamontana.modelDAO.EnvioDetalle_DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Envio_ListarDetallesController implements Initializable {

    @FXML
    private Label lblTitulo;

    @FXML
    private Button btnSend;

    @FXML
    private Label lblId;

    @FXML
    private Label lblEmisor;

    @FXML
    private Label lblReceptor;

    @FXML
    private Label lblEstado;

    @FXML
    private Label lblFecha;

    @FXML
    private Label lblHora;

    @FXML
    private Label lblDescripcion;

    @FXML
    private TableView<EnvioDetalle> tbvEnvioDetalles;

    @FXML
    private TableColumn<?, ?> tbcNro;

    @FXML
    private TableColumn<?, ?> tbcProducto;

    @FXML
    private TableColumn<?, ?> tbcCantidad;

    @FXML
    private TableColumn<?, ?> tbcDescripcion;
    Envio envio = new Envio();
    private EnvioDetalle_DAO envioDetalleDAO = new EnvioDetalle_DAO();
    private ObservableList<EnvioDetalle> envioDetalleObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbcNro.setCellValueFactory(new PropertyValueFactory<>("nro"));
        tbcProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        tbcCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        tbcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    }

    public void setData(Envio envio) throws SQLException {
        this.envio=envio;
        lblId.setText(String.valueOf(envio.getId()));
        lblEmisor.setText(String.valueOf(envio.getEmisor()));
        lblReceptor.setText(String.valueOf(envio.getReceptor()));
        lblEstado.setText(String.valueOf(envio.getEstado()));
        lblFecha.setText(envio.getFecha());
        lblHora.setText(envio.getHora());
        lblDescripcion.setText(envio.getDescripcion());
        listar(envioDetalleDAO.listarOL_Detalle(envio.getId()));
    }

    private void listar(ObservableList<EnvioDetalle> envioDetalleObservableList) {
        this.envioDetalleObservableList = envioDetalleObservableList;
        tbvEnvioDetalles.setItems(envioDetalleObservableList);
    }

    @FXML
    void click_btnSend(ActionEvent event) throws AddressException {
        Address[] destinatarios = new InternetAddress[2];
        destinatarios[0] = new InternetAddress("prueba.jl.001@gmail.com");
        destinatarios[1] = new InternetAddress("prueba.jl.002@gmail.com");
        String asunto = "Envio "+envio.getId()+" - Muebleria Montana";
        StringBuilder contenido = new StringBuilder();
        contenido.append("Lista de Detalles </br>")
            .append("<table>")
                .append("<tr>")
                    .append("<th>Nro</th>")
                    .append("<th>Producto</th>")
                    .append("<th>Cantidad</th>")
                .append("</tr>");
        for (EnvioDetalle envioDetalle : tbvEnvioDetalles.getItems()) {
            contenido
                .append("<tr>")
                    .append("<td>"+envioDetalle.getNro()+"</td>")
                    .append("<td>"+envioDetalle.getProducto().getNombre()+"</td>")
                    .append("<td>"+envioDetalle.getCantidad()+"</td>")
                .append("</tr>");
        }
            contenido.append("</table>");
        Correo correo = new Correo(destinatarios, asunto, contenido);
    }

}
