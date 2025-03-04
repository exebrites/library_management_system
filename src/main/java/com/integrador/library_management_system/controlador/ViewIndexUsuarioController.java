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
import com.integrador.library_management_system.servicios.ServicioMiembro;
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
import javafx.scene.control.Button;
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
public class ViewIndexUsuarioController implements Initializable {

    @FXML
    private Button btnGestionarLibro;
    @FXML
    private Button btnGestionarUsuario;
    @FXML
    private Button btnGestionarPrestamo;
    @FXML
    private Button btnGestionarCopias;
    /*NAVEGACION*/

 /*GESTION MIEMBROS*/
    @FXML
    private Button btnNuevo;

    @FXML
    private Label lbUser;

    //declara tabla
    @FXML
    private TableView<Miembro> tablaMiembros;
    //declarar columnas
    @FXML
    private TableColumn<Miembro, String> colNombre;
    @FXML
    private TableColumn<Miembro, String> colApellido;
    @FXML
    private TableColumn colEstado;

    //declarar observerlist
    private ObservableList<Miembro> listaMiembros;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /*DATOS DE USUARIO*/
        Miembro miembro = (Miembro) GestorDatos.obtenerDato("miembroAuth");
        lbUser.setText(miembro.getNombre());

        //setear columnas
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoMiembro"));
        //obtener todos los miembros
        Repositorio r = new Repositorio();
        ServicioMiembro sm = new ServicioMiembro(r);
        var miembros = sm.obtenerTodos();
        //setear lista
        listaMiembros = FXCollections.observableArrayList(miembros);
        //setear tabla
        tablaMiembros.setItems(listaMiembros);
    }

    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        try {
            // Cargo la vista
            Object evt = event.getSource();
            if (evt.equals(btnGestionarLibro)) {
                loadStage("ViewIndexLibro", event);
            } else if (evt.equals(btnGestionarUsuario)) {
                loadStage("ViewIndexUsuario", event);
            } else if (evt.equals(btnGestionarPrestamo)) {
                loadStage("ViewIndexPrestamo", event);
            } else if (evt.equals(btnGestionarCopias)) {
                loadStage("ViewIndexCopias", event);
            } else if (evt.equals(btnNuevo)) {
                loadStage("ViewCreateMiembro", event);
            }

        } catch (IOException ex) {
            Logger.getLogger(ViewLibroController.class.getName()).log(Level.SEVERE, null, ex);
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
