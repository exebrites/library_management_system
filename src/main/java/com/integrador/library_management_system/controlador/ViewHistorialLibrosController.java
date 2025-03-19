/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.integrador.library_management_system.controlador;

import com.integrador.library_management_system.App;
import static com.integrador.library_management_system.App.loadFXML;
import com.integrador.library_management_system.modelo.CopiaLibro;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.modelo.Miembro;
import com.integrador.library_management_system.modelo.Prestamo;
import com.integrador.library_management_system.modelo.Rack;
import com.integrador.library_management_system.modelo.TipoCopiaLibro;
import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioCopiaLibro;
import com.integrador.library_management_system.servicios.ServicioLibro;
import com.integrador.library_management_system.servicios.ServicioMiembro;
import com.integrador.library_management_system.servicios.ServicioRack;
import com.integrador.library_management_system.util.GestorDatos;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author exe
 */
public class ViewHistorialLibrosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnGestionarLibro;
    @FXML
    private Button btnGestionarUsuario;
    @FXML
    private Button btnGestionarPrestamo;
    @FXML
    private Button btnGestionarCopias;
    @FXML
    private Button btnPrestamo;
    /*NAVEGACION LATERAL*/

    @FXML
    private Label lbUser;
    /*INFORMACION VISTA*/

    @FXML
    private Button btnShow;

    /*NAVEGACION VENTANAS*/
 /*COMPONENTES*/
    @FXML
    private TableView tabla;
    @FXML
    private TableColumn<Object[], String> colNombreApellido;
    @FXML
    private TableColumn<Object[], String> colFechaInicio;
    @FXML
    private TableColumn<Object[], String> colFechaVencimiento;
    @FXML
    private TableColumn<Object[], Long> colNroCopia;
    @FXML
    private TableColumn<Object[], String> colTituloLibro;

    private ObservableList lista;

    private Object[] fila;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Miembro miembro = (Miembro) GestorDatos.obtenerDato("miembroAuth");
        lbUser.setText(miembro.getNombre());
        //setear columnas

        colNombreApellido.setCellValueFactory(cellData -> {
            Object[] row = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty((String) row[0] + " " + (String) row[1]);
        });
        colFechaInicio.setCellValueFactory(cellData -> {
            Object[] row = cellData.getValue();
            var fecha = (LocalDate) row[8];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            var fechaFormateada = fecha.format(formatter);
            return new javafx.beans.property.SimpleObjectProperty<>(fechaFormateada);
        });
        colFechaVencimiento.setCellValueFactory(cellData -> {
            Object[] row = cellData.getValue();
            var fecha = (LocalDate) row[2];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            var fechaFormateada = fecha.format(formatter);
            return new javafx.beans.property.SimpleObjectProperty<>(fechaFormateada);
        });

        colNroCopia.setCellValueFactory(cellData -> {
            Object[] row = cellData.getValue();
            return new javafx.beans.property.SimpleLongProperty((Long) row[3]).asObject();
        });

        colTituloLibro.setCellValueFactory(cellData -> {
            Object[] row = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty((String) row[4]);
        });
        // colNroCopia.setCellValueFactory(new PropertyValueFactory<>(""));
        //obtneer datos
        Repositorio r = new Repositorio();
        ServicioMiembro sm = new ServicioMiembro(r);

        var resultados = sm.consultaHistorialLibrosMiembro();

        //setear lista
        lista = FXCollections.observableArrayList(resultados);

        //setear tabla
        tabla.setItems(lista);

        //listener seleccionar 
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fila = (Object[]) newSelection;
            }
        });
    }

    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        Object evt = event.getSource();

        if (evt.equals(btnGestionarLibro)) {

            loadStage("ViewIndexLibro", event);
        } else if (evt.equals(btnGestionarUsuario)) {
            loadStage("ViewIndexUsuario", event);
        } else if (evt.equals(btnGestionarPrestamo)) {
            //loadStage("ViewIndexUsuario", event);
            loadStage("ViewIndexPrestamo", event);
        } else if (evt.equals(btnGestionarCopias)) {
            //loadStage("ViewIndexUsuario", event);
            loadStage("ViewIndexCopias", event);
        } else if (evt.equals(btnShow)) {

            var fxml = "ViewDetalleHistorialLibros";

            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasarle los datos
            ViewDetalleHistorialLibrosController detalleController = loader.getController();
            detalleController.setData(fila);

            //ocultar la escena anterior y generar una nueva
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }

    }

    private void loadStage(String url, ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();

        Scene scene = new Scene(loadFXML(url));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Library Manager System");
        stage.show();
    }

}
