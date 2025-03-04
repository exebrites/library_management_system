/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.integrador.library_management_system.controlador;

import com.integrador.library_management_system.App;
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
    private Button btnShow;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;

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

    //MIEMBRO SELECCIONADO
    private Miembro miembro;

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

        colEstado.setCellFactory(column -> new TextFieldTableCell<Miembro, Boolean>() {
            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "No ACTIVO" : "ACTIVO");
                }
            }
        });
        //setear lista
        listaMiembros = FXCollections.observableArrayList(miembros);
        //setear tabla
        tablaMiembros.setItems(listaMiembros);

        tablaMiembros.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.miembro = newSelection;
            }
        });
        System.out.println(miembro);
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
            } else if (evt.equals(btnShow)) {
                System.out.println("show....");

                //Cargar la vista
                var fxml = "ViewShowMiembro";

                FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
                Parent root = loader.load();

                // Obtener el controlador y pasarle los datos
                ViewShowMiembroController detalleController = loader.getController();
                detalleController.setData(miembro);

                //ocultar la escena anterior y generar una nueva
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                
                
            } else if (evt.equals(btnEditar)) {
                System.out.println("editar....");
            } else if (evt.equals(btnEliminar)) {
                System.out.println("eliminar....");
                System.out.println(miembro);

                //buscar de la base de datos
                Repositorio r = new Repositorio();
                ServicioMiembro sm = new ServicioMiembro(r);
                var miembrodb = sm.buscarMiembro(miembro);
                System.out.println(miembrodb);
                //eliminarlo
                try {
                    sm.eliminarUsuario(miembrodb);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                var miembros = sm.obtenerTodos();
                listaMiembros = FXCollections.observableArrayList(miembros);
                //setear tabla
                tablaMiembros.setItems(listaMiembros);
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
