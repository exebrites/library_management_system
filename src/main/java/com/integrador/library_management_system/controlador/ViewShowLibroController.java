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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author exe
 */
public class ViewShowLibroController implements Initializable {

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
    private TextField txtTitulo;
    @FXML
    private TextField txtEditorial;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Repositorio r = new Repositorio();
        // ServicioMiembre s = new ServicioMiembro(r);

    }

    public void setData(Object nombre) {
        Libro libro = (Libro) nombre;

        /*
        var titulo = libro.getTitulo();
        var editorial = libro.getEditorial();
        txtTitulo.setText(titulo);
        txtEditorial.setText(editorial);
         */
        Repositorio r = new Repositorio();
        ServicioLibro sl = new ServicioLibro(r);
        /*
         var librodb = sl.findLibro(libro);

        var l = librodb.get(0);

        if (l.getId() instanceof Long) {
            System.out.println("El objeto es una instancia de la clase String");
            sl.obtenerTodos();
        }
         */
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

            } else if (evt.equals(btnPrestamo)) {
                //loadStage("ViewIndexUsuario", event);
                loadStage("ViewCreatePrestamo", event);

            }

        } catch (IOException ex) {
            Logger.getLogger(ViewShowLibroController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loadStage(String url, ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();

        Scene scene = new Scene(loadFXML(url), 1280, 800);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Library Manager System");
        stage.show();
    }

}
