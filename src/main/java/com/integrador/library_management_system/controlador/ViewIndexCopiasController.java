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
import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioCopiaLibro;
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
public class ViewIndexCopiasController implements Initializable {

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
    private Button btnGestionarRack;
    @FXML
    private Button btnGestionarMulta;

    /*NAVEGACION*/
    @FXML
    private Button btnNuevaCopia;

    @FXML
    private Label lbUser;

    /*LISTADO DE COPIAS*/
    @FXML
    private TableView<CopiaLibro> tablaCopias;
    @FXML
    private TableColumn<CopiaLibro, Long> colId;
    @FXML
    private TableColumn colReferencia;
    @FXML
    private TableColumn<CopiaLibro, String> colTipo;
    @FXML
    private TableColumn<CopiaLibro, String> colEstado;

    private ObservableList<CopiaLibro> listaCopias;

    private CopiaLibro copia;

    /*GESTIONAR COPIAS*/
    @FXML
    private Button btnShow;
    @FXML
    private Button btnEditar;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /*DATOS DE USUARIO*/
        Miembro miembro = (Miembro) GestorDatos.obtenerDato("miembroAuth");
        lbUser.setText(miembro.getNombre());

        //LISTADO DE COPIAS
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colReferencia.setCellValueFactory(new PropertyValueFactory<>("referenciaLibro"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        //SETEAR LA COLUMNA BOOLEAN

        colReferencia.setCellFactory(column -> new TextFieldTableCell<CopiaLibro, Boolean>() {
            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "SI" : "NO");
                }
            }
        });

        Repositorio r = new Repositorio();
        ServicioCopiaLibro sc = new ServicioCopiaLibro(r);
        var copias = sc.obtenerTodos();
        listaCopias = FXCollections.observableArrayList(copias);
        tablaCopias.setItems(listaCopias);

        //LISTENER SELECCIONAR COPIA
        tablaCopias.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                copia = newSelection;
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
            } else if (evt.equals(btnNuevaCopia)) {
                loadStage("ViewCreateCopiaLibro", event);

            } else if (evt.equals(btnGestionarMulta)) {
                loadStage("ViewIndexMulta", event);
            } else if (evt.equals(btnShow)) {

                System.out.println("show..");
                System.out.println(copia.toString());
            } else if (evt.equals(btnEditar)) {
          
                  var fxml = "ViewEditCopiaLibro";

                FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
                Parent root = loader.load();

                // Obtener el controlador y pasarle los datos
                ViewEditCopiaLibroController detalleController = loader.getController();
                detalleController.setData(copia);

                //ocultar la escena anterior y generar una nueva
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
          
            }

        } catch (IOException ex) {
            Logger.getLogger(ViewPrincipalController.class
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
