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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
public class ViewEditMiembroController implements Initializable {

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

    //navegacion
    @FXML
    private Button btnNuevoLibro;

    //edit
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtPass;
    @FXML
    private TextField txtEstado;

    @FXML
    private CheckBox checkActivo;

    private Miembro miembro;
    //funciones
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    //user
    @FXML
    private Label lbUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Miembro miembro = (Miembro) GestorDatos.obtenerDato("miembroAuth");
        lbUser.setText(miembro.getNombre());

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
        } else if (evt.equals(btnGuardar)) {
            System.out.println("GUARDANDO...");
            System.out.println(txtNombre.getText());
            System.out.println(txtApellido.getText());
            System.out.println(txtPass.getText());
            System.out.println(checkActivo.isSelected());
            var nombre = txtNombre.getText();
            var apellido = txtApellido.getText();
            var pass = txtPass.getText();
            var activo = checkActivo.isSelected();
            //crear objeto
            //almacenar miembro
            Repositorio r = new Repositorio();
            ServicioMiembro sm = new ServicioMiembro(r);

            var miembrodb = sm.buscarMiembro(miembro);
            miembrodb.setNombre(nombre);
            miembrodb.setApellido(apellido);
            miembrodb.setClave(pass);
            miembrodb.setEstadoMiembro(activo);

            try {
                sm.editarUsuario(miembrodb);
                loadStage("ViewIndexUsuario", event);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else if (evt.equals(btnCancelar)) {
            loadStage("ViewIndexLibro", event);
        }
    }

    public void setData(Object data) {
        miembro = (Miembro) data;
        txtNombre.setText(miembro.getNombre());
        txtApellido.setText(miembro.getApellido());
        var estado = miembro.isEstadoMiembro() ? "No Activo " : "Activo";
        txtEstado.setText(estado);
        txtPass.setText(miembro.getClave());
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
