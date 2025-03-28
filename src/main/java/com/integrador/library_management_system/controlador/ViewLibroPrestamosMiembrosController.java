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
import com.integrador.library_management_system.servicios.ServicioPrestamo;
import com.integrador.library_management_system.servicios.ServicioRack;
import com.integrador.library_management_system.util.GestorDatos;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
public class ViewLibroPrestamosMiembrosController implements Initializable {

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
    private Button btnInicio;
    @FXML
    private Button btnGestionarMulta;
    @FXML
    private Button btnGestionarRack;
    /*NAVEGACION LATERAL*/

    @FXML
    private Label lbUser;
    /*INFORMACION VISTA*/

    @FXML
    private Button btnShow;

    /*NAVEGACION VENTANAS*/
 /*COMPONENTES*/
    @FXML
    private TableView tablaMiembrosLibro;
    @FXML
    private TableColumn<Object[], String> colFullName;
    @FXML
    private TableColumn<Object[], Long> colIdPrestamo;
    @FXML
    private TableColumn<Object[], Long> colIdCopia;

    private ObservableList lista;

    private Object[] fila;

    @FXML
    private TextField txtLibro;

    private Libro libro;

    @FXML
    private TextField txtIsbn;
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtCategoria;

    @FXML
    private TextField txtFullName;

    @FXML
    private Button btn;

    @FXML
    private Button btnIrPrestamo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Miembro miembro = (Miembro) GestorDatos.obtenerDato("miembroAuth");
        lbUser.setText(miembro.getNombre());
        //setear columnas

        //listener seleccionar 
        if (fila != null) {
            filtrar();

        }

    }

    public void setData(Object data) {

        libro = (Libro) data;
        txtIsbn.setText(libro.getIsbn());
        txtTitulo.setText(libro.getTitulo());
        txtCategoria.setText(libro.getCategoriaTematica());
        Repositorio r = new Repositorio();
        ServicioLibro sl = new ServicioLibro(r);
        var miembrosLibro = sl.consultarMiembrosSegunLibro(libro);
        System.out.println(Arrays.toString(miembrosLibro.get(0)));
        inicializarTabla(miembrosLibro);
    }

    public void inicializarTabla(List<Object[]> miembrosLibro) {
        colFullName.setCellValueFactory(cellData -> {
            Object[] row = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty((String) row[3] + " " + (String) row[4]);
        });

        colIdPrestamo.setCellValueFactory(cellData -> {
            Object[] row = cellData.getValue();
            return new javafx.beans.property.SimpleLongProperty((Long) row[2]).asObject();
        });

        colIdCopia.setCellValueFactory(cellData -> {
            Object[] row = cellData.getValue();
            return new javafx.beans.property.SimpleLongProperty((Long) row[1]).asObject();
        });

        lista = FXCollections.observableArrayList(miembrosLibro);
        tablaMiembrosLibro.setItems(lista);
        tablaMiembrosLibro.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fila = (Object[]) newSelection;
            }
        });
    }

    private void filtrar() {

        /*
          System.out.println("filtrando ...");

        FilteredList<Object[]> filteredList = new FilteredList<>(lista, u -> true);

        txtFullName.textProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println(newValue);

            //si es vacio muestra todo
            if (newValue.isEmpty()) {
                filteredList.setPredicate(u -> true);
            } else {
                //filtro por el titulo. El filtro tiene encuentra si la cadena esta contenido dentro de los titulos

                //filteredList.setPredicate(u -> u.getTitulo().contains(newValue.toUpperCase()));
                //1. concatenar nombre y apellido
                filteredList.setPredicate(u -> {
                    var fullName = (String) u[3] + " " + (String) u[4];

                    return fullName.contains(newValue.toUpperCase());
                });
            }
        });
        tablaMiembrosLibro.setItems(filteredList);
         */
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
        } else if (evt.equals(btnInicio)) {
            //loadStage("ViewIndexUsuario", event);
            loadStage("ViewPrincipal", event);

        } else if (evt.equals(btnGestionarMulta)) {
            loadStage("ViewIndexMulta", event);
        } else if (evt.equals(btnGestionarRack)) {
            loadStage("ViewIndexRack", event);
        } else if (evt.equals(btnShow)) {
            /*
            
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
             */
        } else if (evt.equals(btn)) {
            System.out.println("asdadsas");
            System.out.println(Arrays.toString(fila));
        } else if (evt.equals(btnIrPrestamo)) {
            System.out.println(Arrays.toString(fila));
            var p = fila[2];

            System.out.println(p);
            Prestamo prestamo = new Prestamo();
            prestamo.setId((Long) p);
            Repositorio r = new Repositorio();
            ServicioPrestamo sp = new ServicioPrestamo(r);
            var prestamodb = sp.buscarPrestamo(prestamo);

            var fxml = "ViewEditPrestamo";

            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasarle los datos
            ViewEditPrestamoController detalleController = loader.getController();
            detalleController.setData(prestamodb);

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
