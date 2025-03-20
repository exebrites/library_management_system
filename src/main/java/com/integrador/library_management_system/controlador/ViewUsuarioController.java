/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.integrador.library_management_system.controlador;

import static com.integrador.library_management_system.App.loadFXML;
import com.integrador.library_management_system.modelo.Miembro;
import com.integrador.library_management_system.util.GestorDatos;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author exe
 */
public class ViewUsuarioController implements Initializable {

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
    private Button btnInicio;

    @FXML
    private Button btnGestionarMulta;

    @FXML
    private Button btnLogout;
    /*NAVEGACION*/
    @FXML
    private Label lbBienvenida;
    @FXML
    private Label lbUser;

    /*DASHBOARD*/
    @FXML
    private Button btnHistorialLibro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Repositorio r = new Repositorio();
        // ServicioMiembre s = new ServicioMiembro(r);

        Miembro miembro = (Miembro) GestorDatos.obtenerDato("miembroAuth");
        lbUser.setText(miembro.getNombre());
        lbBienvenida.setText(miembro.getFullName());
    }

    @FXML
    private void eventAction(ActionEvent event) throws IOException {

        try {
            // Cargo la vista
            Object evt = event.getSource();

            if (evt.equals(btnGestionarLibro)) {

                loadStage("ViewUsuarioLibro", event);
            } else if (evt.equals(btnInicio)) {
                //loadStage("ViewIndexUsuario", event);
                loadStage("ViewUsuario", event);

            } else if (evt.equals(btnLogout)) {
                loadStage("ViewLogin", event);
            }

        } catch (IOException ex) {
            Logger.getLogger(ViewUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void eventDash(ActionEvent event) throws IOException {
        /*
        try {
            Object evt = event.getSource();
            if (evt.equals(btnHistorialLibro)) {

                System.out.println("ver...");
                loadStage("ViewHistorialLibros", event);

            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + "io");
        } catch (Exception e) {
            System.out.println(e.getMessage() + "e");
        }
         */

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
