/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.integrador.library_management_system.controlador;

import com.integrador.library_management_system.App;
import static com.integrador.library_management_system.App.FXML;
import static com.integrador.library_management_system.App.loadFXML;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.modelo.Miembro;
import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioLibro;
import com.integrador.library_management_system.util.GestorDatos;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ButtonType;
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
public class ViewLibroController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Libro> tableLibros;
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

    //navegacion
    @FXML
    private Button btnNuevoLibro;
    //panel
    @FXML
    private Button btnGestionarLibro;
    @FXML
    private Button btnGestionarUsuario;
    @FXML
    private Button btnGestionarPrestamo;
    @FXML
    private Button btnGestionarCopias;
    @FXML
    private Button btnGestionarRack;
    @FXML
    private Button btnGestionarMulta;

    @FXML
    private Button btnInicio;
    @FXML
    private Button btnShow;

    private Libro libroFila;

    /*GESTIONAR LIBRO*/
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;

    /*FILTROS*/
    @FXML
    private TextField txtFiltroTitulo;
    @FXML
    private TextField txtFiltroAutor;
    @FXML
    private TextField txtFiltroCategoria;

    /*NODOS DE VISTA*/
    @FXML
    private Label lbUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /*DATOS DE USUARIO*/
        Miembro miembro = (Miembro) GestorDatos.obtenerDato("miembroAuth");
        lbUser.setText(miembro.getNombre());

        /*TABLA*/
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutores.setCellValueFactory(new PropertyValueFactory<>("autores"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoriaTematica"));
        colIdioma.setCellValueFactory(new PropertyValueFactory<>("idioma"));

        // Cargar datos
        Repositorio r = new Repositorio();
        ServicioLibro sl = new ServicioLibro(r);
        listaLibros = FXCollections.observableArrayList(sl.obtenerTodos());

        tableLibros.setItems(listaLibros);

        // Listen for selection changes and show the person details when changed.
        tableLibros.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)
                -> this.libroFila = newValue
        );

        // System.out.println(fila);
        filtrar();
    }

    private void filtrar() {
        System.out.println("filtrando ...");

        FilteredList<Libro> filteredList = new FilteredList<>(listaLibros, u -> true);

        txtFiltroTitulo.textProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println(newValue);

            //si es vacio muestra todo
            if (newValue.isEmpty()) {
                filteredList.setPredicate(u -> true);
            } else {
                //filtro por el titulo. El filtro tiene encuentra si la cadena esta contenido dentro de los titulos

                filteredList.setPredicate(u -> u.getTitulo().contains(newValue.toUpperCase()));

            }
        });
        txtFiltroAutor.textProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println(newValue);

            //si es vacio muestra todo
            if (newValue.isEmpty()) {
                filteredList.setPredicate(u -> true);
            } else {
                //filtro por el titulo. El filtro tiene encuentra si la cadena esta contenido dentro de los titulos

                filteredList.setPredicate(u -> u.getAutores().contains(newValue.toUpperCase()));

            }
        });
        txtFiltroCategoria.textProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println(newValue);

            //si es vacio muestra todo
            if (newValue.isEmpty()) {
                filteredList.setPredicate(u -> true);
            } else {
                //filtro por el titulo. El filtro tiene encuentra si la cadena esta contenido dentro de los titulos

                filteredList.setPredicate(u -> u.getCategoriaTematica().contains(newValue.toUpperCase()));

            }
        });

        tableLibros.setItems(filteredList);

    }

    @FXML
    private void eventAction(ActionEvent event) throws IOException {

        try {
            // Cargo la vista
            Object evt = event.getSource();
            if (evt.equals(btnNuevoLibro)) {

                loadStage("ViewCreateLibro", event);
            } else if (evt.equals(btnGestionarLibro)) {
                loadStage("ViewIndexLibro", event);
            } else if (evt.equals(btnGestionarUsuario)) {
                loadStage("ViewIndexUsuario", event);
            } else if (evt.equals(btnGestionarPrestamo)) {
                loadStage("ViewIndexPrestamo", event);
            } else if (evt.equals(btnGestionarCopias)) {
                loadStage("ViewIndexCopias", event);
            } else if (evt.equals(btnGestionarRack)) {
                loadStage("ViewIndexRack", event);
            } else if (evt.equals(btnInicio)) {
                //loadStage("ViewIndexUsuario", event);
                loadStage("ViewPrincipal", event);

            } else if (evt.equals(btnGestionarMulta)) {
                loadStage("ViewIndexMulta", event);
            } else if (evt.equals(btnEditar)) {
                //Cargar la vista

                if (libroFila == null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Selección de Libro");
                    alert.setHeaderText("Por favor, seleccione un libro");
                    alert.setContentText("Para continuar, elija un libro de la lista disponible.");
                    alert.showAndWait();
                } else {
                    var fxml = "ViewEditLibro";

                    FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
                    Parent root = loader.load();

                    // Obtener el controlador y pasarle los datos
                    ViewEditLibroController detalleController = loader.getController();
                    detalleController.setData(libroFila);

                    //ocultar la escena anterior y generar una nueva
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(ViewLibroController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void eventShow(ActionEvent event) throws IOException {

        if (libroFila == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selección de Libro");
            alert.setHeaderText("Por favor, seleccione un libro");
            alert.setContentText("Para continuar, elija un libro de la lista disponible.");
            alert.showAndWait();
        } else {
            //Cargar la vista
            var fxml = "ViewShowLibro";

            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasarle los datos
            ViewShowLibroController detalleController = loader.getController();
            detalleController.setData(libroFila);

            //ocultar la escena anterior y generar una nueva
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    private void eventEliminar(ActionEvent event) throws IOException {
        Object evt = event.getSource();
        // Cargo la vista
        if (evt.equals(btnEliminar)) {

            //traer el libro seleccionado
            System.out.println(libroFila.getClass());
            //buscar ese libro en db
            Repositorio r = new Repositorio();
            ServicioLibro sl = new ServicioLibro(r);
            //sl.findLibro()
            // eliminarlo de la db
           
            if (libroFila != null) {

                //controlar la eliminacion
                //si tiene copias asociadas avisar al usuario
                // el usuario confirma la eliminacion
                Alert alertConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmacion.setTitle("Confirmación de Eliminación");
                alertConfirmacion.setHeaderText("Confirmación de eliminación de libro");
                alertConfirmacion.setContentText("Al eliminar este libro, todas sus copias también serán eliminadas. ¿Está seguro de que desea continuar?");
                alertConfirmacion.showAndWait();

                Optional<ButtonType> result = alertConfirmacion.showAndWait(); // Muestra la alerta y espera a que el usuario la cierre
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("Este es un mensaje informativo");
                if (result.isPresent() && result.get() == ButtonType.OK) {

                    System.out.println("libro eliminado con sus copias");

                    Libro libro = sl.buscarLibro(libroFila);
                    sl.eliminarLibro(libro);

                    //notificar al usuario 
                    System.out.println("Eliminado exitosamente");

                    alert.setContentText("LIBRO ELIMINADO EXITOSAMENTE!");

                    alert.showAndWait(); // Muestra la alerta y espera a que el usuario la cierre

                    //volver al index
                    listaLibros = FXCollections.observableArrayList(sl.obtenerTodos());

                    tableLibros.setItems(listaLibros);
                } else {

                    System.out.println("Se cancela la operacion");
                    alert.setContentText("OPERACION CANCELADA!!");

                    alert.showAndWait(); // Muestra la alerta y espera a que el usuario la cierre
                }

            }

        }
    }

    private void loadStage(String url, ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();

        Scene scene = new Scene(loadFXML(url), 1600, 900);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Library Manager System");
        stage.show();
    }

}
