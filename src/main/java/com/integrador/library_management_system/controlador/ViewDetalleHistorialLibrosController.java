/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.integrador.library_management_system.controlador;

import static com.integrador.library_management_system.App.loadFXML;
import com.integrador.library_management_system.modelo.CopiaLibro;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.modelo.Miembro;
import com.integrador.library_management_system.modelo.Prestamo;
import com.integrador.library_management_system.modelo.Rack;
import com.integrador.library_management_system.modelo.TipoCopiaLibro;
import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioCopiaLibro;
import com.integrador.library_management_system.servicios.ServicioLibro;
import com.integrador.library_management_system.servicios.ServicioPrestamo;
import com.integrador.library_management_system.servicios.ServicioRack;
import com.integrador.library_management_system.util.GestorDatos;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
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
import javafx.scene.control.ComboBox;
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
public class ViewDetalleHistorialLibrosController implements Initializable {

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
    //navegacion

    @FXML
    private Label lbUser;

    private Object[] fila;

    /*COMPONENTES*/
    @FXML
    private TextField txtNombreApellido;
    @FXML
    private TextField txtIdPrestamo;
    @FXML
    private TextField txtIdCopia;
    @FXML
    private TextField txtIdLibro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //System.out.println(TipoCopiaLibro.values());

        Miembro miembro = (Miembro) GestorDatos.obtenerDato("miembroAuth");

    }

    public void setData(Object[] data) {
        fila = data;

        System.out.println(Arrays.toString(fila));
        txtNombreApellido.setText((String) fila[0] + " " + (String) fila[1]);

        Repositorio r = new Repositorio();
        ServicioPrestamo sp = new ServicioPrestamo(r);
        ServicioCopiaLibro scopia = new ServicioCopiaLibro(r);
        ServicioLibro sl = new ServicioLibro(r);
        //PRESTAMO
        Long idPrestamo = (Long) fila[6];
        Prestamo p = new Prestamo();
        p.setId(idPrestamo);
        var prestamo = sp.buscarPrestamo(p);

        //COPIA
        Long idCopia = (Long) fila[3];
        var c = new CopiaLibro();
        c.setId(idCopia);
        var copia = scopia.buscarCopia(c);

        //LIBRO 
        Long idLibro = (Long) fila[7];
        var l = new Libro();
        l.setId(idLibro);
        var libro = sl.buscarLibro(l);

        //VISUALIZAR DATOS|
        txtIdPrestamo.setText(prestamo.getId().toString());
        txtIdCopia.setText(copia.getId().toString());
        txtIdLibro.setText(libro.getId().toString());

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
