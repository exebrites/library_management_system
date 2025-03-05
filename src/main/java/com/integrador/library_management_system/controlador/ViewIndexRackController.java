/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.integrador.library_management_system.controlador;

import com.integrador.library_management_system.App;
import static com.integrador.library_management_system.App.loadFXML;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.modelo.Miembro;
import com.integrador.library_management_system.modelo.Rack;
import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioLibro;
import com.integrador.library_management_system.servicios.ServicioMiembro;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author exe
 */
public class ViewIndexRackController implements Initializable {

    @FXML
    private Button btnGestionarLibro;
    @FXML
    private Button btnGestionarUsuario;
    @FXML
    private Button btnGestionarPrestamo;
    @FXML
    private Button btnGestionarCopias;
    /*NAVEGACION*/

 /*GESTION rack*/
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnShow;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;

    @FXML
    private Label lbUser;

    //TABLA RACKS
    @FXML
    private TableView<Rack> tablaRacks;
    //declarar columnas
    @FXML
    private TableColumn<Rack, Long> colId;
    @FXML
    private TableColumn<Rack, String> colDescripcion;

    //declarar observerlist
    private ObservableList<Rack> listaRacks;

    //MIEMBRO SELECCIONADO
    private Rack rack;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /*DATOS DE USUARIO*/
        Miembro miembro = (Miembro) GestorDatos.obtenerDato("miembroAuth");
        lbUser.setText(miembro.getNombre());

        //setear columnas
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        //obtener todos los racks
        Repositorio r = new Repositorio();
        ServicioRack sr = new ServicioRack(r);
        var racks = sr.obtenerTodos();
        //setear lista
        listaRacks = FXCollections.observableArrayList(racks);
        //setear tabla
        tablaRacks.setItems(listaRacks);

        tablaRacks.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.rack = newSelection;
            }
        });

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
                loadStage("ViewCreateRack", event);
            } else if (evt.equals(btnShow)) {
                System.out.println("show....");
//Cargar la vista

                var fxml = "ViewShowRack";

                FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
                Parent root = loader.load();

                // Obtener el controlador y pasarle los datos
                ViewShowRackController detalleController = loader.getController();
                detalleController.setData(rack);

                //ocultar la escena anterior y generar una nueva
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                  
            } else if (evt.equals(btnEditar)) {
                System.out.println("editar....");
                //Cargar la vista
                var fxml = "ViewEditRack";

                FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
                Parent root = loader.load();

                // Obtener el controlador y pasarle los datos
                ViewEditRackController detalleController = loader.getController();
                detalleController.setData(rack);

                //ocultar la escena anterior y generar una nueva
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } else if (evt.equals(btnEliminar)) {
                System.out.println("eliminar....");

                System.out.println(rack);
//buscar de la base de datos
                Repositorio r = new Repositorio();
                ServicioRack sr = new ServicioRack(r);
                var rackdb = sr.buscarRack(rack);
                System.out.println(rackdb);
                //eliminarlo
                try {
                    sr.eliminarRack(rackdb);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                var racks = sr.obtenerTodos();
                listaRacks = FXCollections.observableArrayList(racks);
                //setear tabla
                tablaRacks.setItems(listaRacks);
                //notificar si esta asociado a algun prestamo

            }

        } catch (IOException ex) {
            Logger.getLogger(ViewLibroController.class
                    .getName()).log(Level.SEVERE, null, ex);
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
