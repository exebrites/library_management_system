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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    }

}
