package com.muebleriamontana.controller.pantallas;

import com.muebleriamontana.Montana;
import com.muebleriamontana.controller.alertas.EliminarController;
import com.muebleriamontana.model.Producto;
import com.muebleriamontana.modelDAO.Producto_DAO;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producto_CRUDController implements Initializable {

    @FXML
    private TextField txfBusqueda;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnExportar;

    @FXML
    private TableView<Producto> tblProductos;

    @FXML
    private TableColumn<Producto, Integer> tbcId;

    @FXML
    private TableColumn<Producto, String> tbcNombre;

    @FXML
    private TableColumn<Producto, String> tbcCosto;

    @FXML
    private TableColumn<Producto, String> tbcStock;

    @FXML
    private TableColumn<Producto, String> tbcCategoria;

    @FXML
    private TableColumn<Producto, String> tbcAcciones;

    private Producto_DAO productoDAO = new Producto_DAO();
    private ObservableList<Producto> productoObservableList = FXCollections.observableArrayList();
    private final Image imgEditar = new Image(Montana.class.getResourceAsStream("img/sistema/editar.png"));
    private final Image imgEliminar = new Image(Montana.class.getResourceAsStream("img/sistema/eliminar.png"));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbcId.setCellValueFactory(new  PropertyValueFactory<>("sku"));
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        Callback<TableColumn<Producto, String>, TableCell<Producto, String>> costoCol = param -> {
            final TableCell<Producto, String> cell = new TableCell<Producto, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty){
                        setGraphic(null);
                        setText(null);
                    }else {
                        Producto producto = getTableView().getItems().get(getIndex());
                        setText(Montana.MONEDA+" "+producto.getCosto());
                    }
                }
            };
            return cell;
        };
        tbcCosto.setCellFactory(costoCol);
        tbcStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tbcCategoria.setCellValueFactory(new PropertyValueFactory<>("categoriaProducto"));
        Callback<TableColumn<Producto, String>, TableCell<Producto, String>> accionesCell =param -> {
            final TableCell<Producto, String> cell = new TableCell<Producto, String>(){
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
                            Producto producto = getTableView().getItems().get(getIndex());
                            FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/pantallas/producto_Plantilla.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            Image image = new Image(Montana.class.getResourceAsStream("img/sistema/editar01.png"));
                            stage.setTitle("Editar producto "+producto.getSku());
                            stage.getIcons().add(image);
                            stage.setScene(scene);
                            stage.show();
                            Producto_PlantillaController producto_plantillaController = loader.getController();
                            producto_plantillaController.setData(producto,"editar");
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
                        Producto producto = getTableView().getItems().get(getIndex());
                        try {
                            FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/alertas/eliminar.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            Image image = new Image(Montana.class.getResourceAsStream("img/sistema/alerta01.png"));
                            stage.setTitle("Eliminar producto "+producto.getSku());
                            stage.getIcons().add(image);
                            stage.setScene(scene);
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.show();
                            EliminarController eliminarController = loader.getController();
                            eliminarController.setData(producto);
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
        listar(productoDAO.listarOL());
    }

    @FXML
    void click_btnBuscar(ActionEvent  event) {
        listar(productoDAO.busquedaGeneral(txfBusqueda.getText()));
    }

    @FXML
    void click_btnAgregar(ActionEvent  event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Montana.class.getResource("view/pantallas/producto_Plantilla.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        Producto_PlantillaController producto_plantillaController = loader.getController();
        producto_plantillaController.setData(null,"agregar");
    }

    @FXML
    void click_btnActualizar(ActionEvent event) {
        txfBusqueda.setText("");
        listar(productoDAO.listarOL());
    }

    private void listar(ObservableList<Producto> productoObservableList) {
        this.productoObservableList=productoObservableList;
        tblProductos.setItems(productoObservableList);
    }

    @FXML
    void click_btnExportar(ActionEvent  event) {
        exportTableToPDF(tblProductos);
    }

    public static void exportTableToPDF(TableView<Producto> tableView) {
        Document document = new Document();
        DateTimeFormatter fecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            FileChooser fileChooser = new FileChooser();
            Path desktopPath = FileSystems.getDefault().getPath(System.getProperty("user.home"), "Desktop");
            fileChooser.setInitialDirectory(desktopPath.toFile());
            fileChooser.setInitialFileName("ReporteInventario_"+fecha.format(now)+"_"+hora.format(now)+".pdf");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();

                float[] twcTop = {70f,30f};
                PdfPTable tableTop = new PdfPTable(twcTop);
                tableTop.setWidthPercentage(100);
                PdfPCell empresaLogoCell = new PdfPCell();
                empresaLogoCell.setBorder(0);
                Paragraph empresa = new Paragraph("Muebleria Montana\n",FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD));
                empresa.add("Reporte de Inventario");
                empresaLogoCell.addElement(empresa);
                tableTop.addCell(empresaLogoCell);
                PdfPCell fechaHoraCell = new PdfPCell();
                fechaHoraCell.setBorder(0);
                Paragraph fechaHora = new Paragraph();
                fechaHora.add("Fecha: "+fecha.format(now)+"\n");
                fechaHora.add("Hora: "+hora.format(now));
                fechaHoraCell.addElement(fechaHora);
                tableTop.addCell(fechaHoraCell);
                document.add(tableTop);

                //Tabla productos
                float[] twcProductos = {15,42,13,10,20};
                PdfPTable pdfTblProducto = new PdfPTable(twcProductos);
                pdfTblProducto.setHeaderRows(5);
                pdfTblProducto.setWidthPercentage(100);

                pdfTblProducto.addCell("SKU");
                pdfTblProducto.addCell("Nombre");
                pdfTblProducto.addCell("Costo");
                pdfTblProducto.addCell("Stock");
                pdfTblProducto.addCell("Categoria");
                ObservableList<Producto> items = tableView.getItems();
                for (Producto producto : items) {
                    pdfTblProducto.addCell(String.valueOf(producto.getSku()));
                    pdfTblProducto.addCell(producto.getNombre());
                    pdfTblProducto.addCell(Montana.MONEDA+" "+producto.getCosto());
                    pdfTblProducto.addCell(String.valueOf(producto.getStock()));
                    pdfTblProducto.addCell(producto.getCategoriaProducto().getNombre());
                }
                document.add(pdfTblProducto);

                document.close();
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

}
