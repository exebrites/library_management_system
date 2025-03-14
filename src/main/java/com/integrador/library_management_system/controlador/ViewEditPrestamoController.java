/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.integrador.library_management_system.controlador;

import com.integrador.library_management_system.App;
import static com.integrador.library_management_system.App.loadFXML;
import com.integrador.library_management_system.modelo.CopiaLibro;
import com.integrador.library_management_system.modelo.EstadoCopiaLibro;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.modelo.Miembro;
import com.integrador.library_management_system.modelo.Multa;
import com.integrador.library_management_system.modelo.Prestamo;
import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioCopiaLibro;
import com.integrador.library_management_system.servicios.ServicioLibro;
import com.integrador.library_management_system.servicios.ServicioMiembro;
import com.integrador.library_management_system.servicios.ServicioMulta;
import com.integrador.library_management_system.servicios.ServicioPrestamo;
import com.integrador.library_management_system.util.GestorDatos;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import javafx.scene.control.DatePicker;
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
public class ViewEditPrestamoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    //navegacion
    @FXML
    private Button btnInicio;
    @FXML
    private Button btnGestionarRack;

//create
    /*
    @FXML
    private TableView<Miembro> tablaMiembros;
    @FXML
    private TableColumn<Miembro, Long> colIdentificador;
    @FXML
    private TableColumn<Miembro, String> colNombre;
    @FXML
    private TableColumn<Miembro, String> colApellido;
    @FXML
    private ObservableList<Miembro> listaMiembros;

    private Object fila;
    private CopiaLibro copiaLocal;
  
    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtFiltro;
    @FXML
    private TextField txtfiltroNombre;
    @FXML
    private TextField txtfiltroApellido;

     */
    //Campos de copia
    /*
      @FXML
    private TextField txtId;
    @FXML
    private TextField txtEstado;
    @FXML
    private TextField txtTipo;*/
    @FXML
    private Label lbUser;

    private Prestamo prestamo;

    @FXML
    private DatePicker dataInicio;
    @FXML
    private DatePicker dataVencimiento;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtEstado;

    @FXML
    private Button btnGuardar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //NOMBRE USER
        Miembro miembro = (Miembro) GestorDatos.obtenerDato("miembroAuth");
        lbUser.setText(miembro.getNombre());

    }

    public void setData(Object data) {
        /*
        copiaLocal = (CopiaLibro) data;
        txtId.setText(copiaLocal.getId().toString());

        txtEstado.setText(copiaLocal.getEstado().toString());

        txtTipo.setText(copiaLocal.getTipo().toString());
         */
        prestamo = (Prestamo) data;

        System.out.println(prestamo);
        txtId.setText(prestamo.getId().toString());
        dataInicio.setValue(prestamo.getFechaPrestamo());
        dataVencimiento.setValue(prestamo.getFechaVencimiento());
        var formato = prestamo.isEstado() ? "ACTIVO" : "NO ACTIVO";
        txtEstado.setText(formato);
    }

    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        Object evt = event.getSource();

        if (evt.equals(btnGuardar)) {

            Repositorio r = new Repositorio();
            ServicioPrestamo sr = new ServicioPrestamo(r);
            var prestamodb = sr.buscarPrestamo(prestamo);
            var estado = false; //Controlar la fecha de vencimiento y generar multa
            System.out.println("hoy a hace diez dias sacaste el libro (?");
            //System.out.println(estado);
            if (prestamodb.hanPasadoDiezDias()) {
                System.out.println("Generar multa");
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Generacion de multa");
                //generarr multa. REalizar en el servicio
                var costo = 1;
                Multa multa = new Multa(costo);
                multa.setPrestamo(prestamodb);
                ServicioMulta sm = new ServicioMulta(r);
                sm.agregarMulta(multa);
            }

            prestamodb.setEstado(estado);
            sr.editarPrestamo(prestamodb);
            loadStage("ViewIndexPrestamo", event);
        } else if (evt.equals(btnInicio)) {
            //loadStage("ViewIndexUsuario", event);
            loadStage("ViewPrincipal", event);

        } else if (evt.equals(btnGestionarRack)) {
            loadStage("ViewIndexRack", event);
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
