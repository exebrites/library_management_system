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
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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

    @FXML
    private Button btnInicio;
    @FXML
    private Button btnGestionarMulta;
    @FXML
    private Button btnGestionarRack;
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
    private TextField txtTituloLibro;
    @FXML
    private TextField txtFechaInicio;
    @FXML
    private TextField txtFechaVencimiento;
    @FXML
    private TextField txtTipoCopia;
    @FXML
    private TextField txtEstadoCopia;

    @FXML
    private TextField txtEstadoPrestamo;

    private Prestamo prestamo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //System.out.println(TipoCopiaLibro.values());

        Miembro miembro = (Miembro) GestorDatos.obtenerDato("miembroAuth");
        lbUser.setText(miembro.getNombre());

    }

    public void setData(Object[] data) {
        fila = data;

        System.out.println(Arrays.toString(fila));

        Repositorio r = new Repositorio();
        ServicioPrestamo sp = new ServicioPrestamo(r);
        ServicioCopiaLibro scopia = new ServicioCopiaLibro(r);
        ServicioLibro sl = new ServicioLibro(r);
        //PRESTAMO
        Long idPrestamo = (Long) fila[6];
        Prestamo p = new Prestamo();
        p.setId(idPrestamo);
        prestamo = sp.buscarPrestamo(p);

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
        txtNombreApellido.setText((String) fila[0] + " " + (String) fila[1]);
        txtIdPrestamo.setText(prestamo.getId().toString());
        txtIdCopia.setText(copia.getId().toString());
        txtTituloLibro.setText(libro.getTitulo());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtFechaInicio.setText(prestamo.getFechaPrestamo().format(formatter));
        txtFechaVencimiento.setText(prestamo.getFechaVencimiento().format(formatter));
        txtTipoCopia.setText(copia.getTipo().toString());
        txtEstadoCopia.setText(copia.getEstado().toString());
        var estado = prestamo.isEstado() ? "Activo" : "No activo";
        txtEstadoPrestamo.setText(estado);
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

        } else if (evt.equals(btnGestionarMulta)) {
            loadStage("ViewIndexMulta", event);
        } else if (evt.equals(btnGestionarRack)) {
            loadStage("ViewIndexRack", event);
        } else if (evt.equals(btnPrestamo)) {
            //loadStage("ViewIndexUsuario", event);
            System.out.println("prestamo...");
            System.out.println(prestamo);

            var fxml = "ViewEditPrestamo";

            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasarle los datos
            ViewEditPrestamoController detalleController = loader.getController();
            detalleController.setData(prestamo);

            //ocultar la escena anterior y generar una nueva
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

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
