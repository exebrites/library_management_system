/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.integrador.library_management_system.controlador;

import static com.integrador.library_management_system.App.loadFXML;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioLibro;
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
import javafx.scene.control.Button;
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
public class ViewCreateLibroController implements Initializable {

    /**
     * Initializes the controller class.
     */
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        Object evt = event.getSource();

        if (evt.equals(btnGuardar)) {
            var titulo = txtTitulo.getText();
            var isbn = txtIsbn.getText();
            var idioma = txtIdioma.getText();
            var editorial = txtEditorial.getText();
            var autores = txtAutores.getText();
            var categoriaTematica = txtCategoria.getText();

            try {

                Libro libro = new Libro(editorial, autores, categoriaTematica, isbn, idioma, titulo);
                // Cargar datos
                Repositorio r = new Repositorio();
                ServicioLibro sl = new ServicioLibro(r);
                sl.agregarLibro(libro);
                loadStage("ViewIndexLibro", event);
            } catch (Exception e) {
                System.out.println(e);
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
        stage.show();
    }

}
