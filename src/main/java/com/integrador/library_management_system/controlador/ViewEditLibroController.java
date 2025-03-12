/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.integrador.library_management_system.controlador;

import static com.integrador.library_management_system.App.loadFXML;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.modelo.Miembro;
import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioLibro;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author exe
 */
public class ViewEditLibroController implements Initializable {

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
    private Button btnGestionarRack;

    //navegacion
    @FXML
    private Button btnNuevoLibro;
//create

    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtIsbn;
    @FXML
    private TextField txtIdioma;
    @FXML
    private TextField txtEditorial;
    @FXML
    private TextField txtCategoria;
    @FXML
    private TextField txtAutores;

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    private Libro libro;

    @FXML
    private Label lbUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Miembro miembro = (Miembro) GestorDatos.obtenerDato("miembroAuth");
        lbUser.setText(miembro.getNombre());
    }

    public void setData(Object data) {
        libro = (Libro) data;
        txtTitulo.setText(libro.getTitulo());
        txtIsbn.setText(libro.getIsbn());
        txtIdioma.setText(libro.getIdioma());
        txtEditorial.setText(libro.getEditorial());
        txtCategoria.setText(libro.getCategoriaTematica());
        txtAutores.setText(libro.getAutores());
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
        } else if (evt.equals(btnGestionarRack)) {
            loadStage("ViewIndexRack", event);
        } else if (evt.equals(btnGuardar)) {
            System.out.println("guardando... ");

            var titulo = txtTitulo.getText().toUpperCase();

            var isbn = txtIsbn.getText().toUpperCase();
            var idioma = txtIdioma.getText().toUpperCase();
            var editorial = txtEditorial.getText().toUpperCase();
            var autores = txtAutores.getText().toUpperCase();
            var categoriaTematica = txtCategoria.getText().toUpperCase();

            try {
                // Cargar datos
                Repositorio r = new Repositorio();
                ServicioLibro sl = new ServicioLibro(r);
                var librodb = sl.buscarLibro(libro);
                librodb.setTitulo(titulo);
                librodb.setIsbn(isbn);
                librodb.setIdioma(idioma);
                librodb.setEditorial(editorial);
                librodb.setAutores(autores);
                librodb.setCategoriaTematica(categoriaTematica);

                var librosTitulos = sl.buscarTitulo(titulo);
                var librosISBN = sl.buscarISBN(isbn);

                var bandera = true;
                //verificar si existe algun otro libro con el mismo titulo
                if (!librosTitulos.isEmpty()) {
                    if (!librodb.equals(librosTitulos.get(0))) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Alta Libros");
                        alert.setHeaderText("Titulos duplicados");
                        alert.setContentText("Ya existe un LIBRO con ese titulo. POR FAVOR INGRESE UN NUEVO TITULO");
                        alert.showAndWait();
                        bandera = false;
                    }
                }
                //verificar si existe algun otro libro con el mismo titulo
                if (!librosISBN.isEmpty()) {
                    if (!librodb.equals(librosISBN.get(0))) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Alta Libros");
                        alert.setHeaderText("ISBN duplicados");
                        alert.setContentText("Ya existe un LIBRO con ese ISBN. POR FAVOR INGRESE UN NUEVO ISBN");
                        alert.showAndWait();
                        bandera = false;
                    }
                }
                //modificar el libro
                if (bandera) {
                    System.out.println("modificando libro...");
                    sl.editarLibro(librodb);
                    loadStage("ViewIndexLibro", event);
                }

            } catch (IllegalArgumentException ie) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Alta Libros");
                alert.setHeaderText("Datos ingresados");
                alert.setContentText(ie.getMessage());
                alert.showAndWait();
            } catch (IOException e) {
                System.out.println(e.getMessage() + "io");
            } catch (Exception e) {
                System.out.println(e.getMessage() + "general");

            }
        } else if (evt.equals(btnCancelar)) {
            loadStage("ViewIndexLibro", event);
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
