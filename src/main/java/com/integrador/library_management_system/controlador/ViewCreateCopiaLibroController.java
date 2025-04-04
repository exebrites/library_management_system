/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.integrador.library_management_system.controlador;

import static com.integrador.library_management_system.App.loadFXML;
import com.integrador.library_management_system.modelo.CopiaLibro;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.modelo.Miembro;
import com.integrador.library_management_system.modelo.Rack;
import com.integrador.library_management_system.modelo.TipoCopiaLibro;
import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioCopiaLibro;
import com.integrador.library_management_system.servicios.ServicioLibro;
import com.integrador.library_management_system.servicios.ServicioRack;
import com.integrador.library_management_system.util.GestorDatos;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
public class ViewCreateCopiaLibroController implements Initializable {

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
    @FXML
    private Button btnInicio;
    @FXML
    private Button btnGestionarRack;
    @FXML
    private Button btnGestionarMulta;

    //navegacion
    @FXML
    private Button btnNuevaCopia;
//create

    @FXML
    private ComboBox<TipoCopiaLibro> cboxTipo;

    @FXML
    private CheckBox checkReferencia;

    @FXML
    private TextField txtCosto;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    @FXML
    private Label lbUser;

    //LISTADO DE LIBROS
    //tablaLibros
    @FXML
    private TableView<Libro> tablaLibros;
    @FXML
    private TableColumn<Libro, String> colIsbn;
    @FXML
    private TableColumn<Libro, String> colTitulo;
    @FXML
    private TableColumn<Libro, String> colAutores;
    @FXML
    private TableColumn<Libro, String> colCategoria;
    @FXML
    private TableColumn<Libro, String> colIdioma;

    private ObservableList<Libro> listaLibros;
    //libroFila
    private Libro libroFila;

    /*LISTA DE RACKS*/
    @FXML
    private TableView<Rack> tablaRacks;
    @FXML
    private TableColumn<Rack, Long> colId;
    @FXML
    private TableColumn<Rack, String> colDescripcion;
    private ObservableList<Rack> listaRacks;
    //libroFila
    private Rack rack;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //System.out.println(TipoCopiaLibro.values());
        cboxTipo.getItems().addAll(TipoCopiaLibro.values());

        Miembro miembro = (Miembro) GestorDatos.obtenerDato("miembroAuth");
        lbUser.setText(miembro.getNombre());

        //LISTADO DE LIBROS
        //asociaciones de columnas con propiedades
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutores.setCellValueFactory(new PropertyValueFactory<>("autores"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoriaTematica"));
        colIdioma.setCellValueFactory(new PropertyValueFactory<>("idioma"));
        //obtener todos los libros
        Repositorio r = new Repositorio();
        ServicioLibro sl = new ServicioLibro(r);
        var libros = sl.obtenerTodos();
        // asignar libros al ObList
        System.out.println(libros);
        listaLibros = FXCollections.observableArrayList(libros);
        //setear la tabla libros
        tablaLibros.setItems(listaLibros);

        //Implementar un Listener cuando el usuario cliquea un libro para asignarlo a la copia
        tablaLibros.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                libroFila = newSelection;
            }
        });

        //LISTADO DE RACKS
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        ServicioRack sr = new ServicioRack(r);
        var racks = sr.obtenerTodos();
        // asignar libros al ObList
        listaRacks = FXCollections.observableArrayList(racks);
        //setear la tabla libros
        tablaRacks.setItems(listaRacks);

        //Implementar un Listener cuando el usuario cliquea un libro para asignarlo a la copia
        tablaRacks.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                rack = newSelection;
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
        } else if (evt.equals(btnInicio)) {
            //loadStage("ViewIndexUsuario", event);
            loadStage("ViewPrincipal", event);

        } else if (evt.equals(btnGestionarRack)) {
            loadStage("ViewIndexRack", event);
        } else if (evt.equals(btnGestionarMulta)) {
            loadStage("ViewIndexMulta", event);
        } else if (evt.equals(btnGuardar)) {

            //controla el combobox
            var tipoCopia = cboxTipo.getValue();
            var referencia = checkReferencia.isSelected();
            var precio = Float.valueOf(txtCosto.getText());
            try {
                Repositorio r = new Repositorio();
                ServicioCopiaLibro scopia = new ServicioCopiaLibro(r);
                ServicioLibro sl = new ServicioLibro(r);
                ServicioRack sr = new ServicioRack(r);

                var librodb = sl.buscarLibro(libroFila);
                var rackdb = sr.buscarRack(rack);

                CopiaLibro copia = new CopiaLibro(tipoCopia, librodb,precio);
                copia.setReferenciaLibro(referencia);
                copia.setLibro(librodb);
                copia.setRack(rackdb);

                scopia.agregarCopiaLibro(copia);
                loadStage("ViewIndexCopias", event);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else if (evt.equals(btnCancelar)) {
            loadStage("ViewIndexCopias", event);
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
