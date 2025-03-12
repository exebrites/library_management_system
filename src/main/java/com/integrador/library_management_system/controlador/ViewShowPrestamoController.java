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
import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioLibro;
import com.integrador.library_management_system.servicios.ServicioPrestamo;
import com.integrador.library_management_system.util.GestorDatos;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author exe
 */
public class ViewShowPrestamoController implements Initializable {

    /*NAVEGACION*/
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

    /*NAVEGACION*/
    @FXML
    private Button btnNuevoPrestamo;

    private Libro libro1;
    private Object fila;

    //PRESTAMO
    private Prestamo prestamoLocal;

    @FXML
    private TextField txtIdPrestamo;

    @FXML
    private TextField txtFechaInicio;

    @FXML
    private TextField txtFechaVencimiento;

    //MIEMBRO
    @FXML
    private TextField txtNombreMiembro;

    @FXML
    private TextField txtApellidoMiembro;

    //COPIA
    @FXML
    private TextField txtIdCopia;
    @FXML
    private TextField txtEstadoCopia;
    @FXML
    //LIBRO
    private TextField txtTituloLibro;

    //USER
    @FXML
    private Label lbUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Miembro miembro = (Miembro) GestorDatos.obtenerDato("miembroAuth");
        lbUser.setText(miembro.getNombre());
        // Listen for selection changes and show the person details when changed.
        /*
         tablaCopias.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)
                -> this.fila = newValue
        );
         */
    }

    private void inicializarTabla() {

        /*
                colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colReferencia.setCellValueFactory(new PropertyValueFactory<>("referenciaLibro"));

        //pasa la lista de copias en base a un libro
        Repositorio r = new Repositorio();
        ServicioLibro sl = new ServicioLibro(r);

        var copias = sl.copiasAsociadas(libro1);
        listaCopias = FXCollections.observableArrayList(copias);
        tablaCopias.setItems(listaCopias);
        tablaCopias.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)
                -> {
            this.fila = newValue;
            if (newValue != null) {
                System.out.println("Seleccionaste: " + newValue.getId());
            }
        }
        );

         */
//     
    }

    public void setData(Object prestamo) {
        prestamoLocal = (Prestamo) prestamo;

        System.out.println("estoy en show prestamo");
        System.out.println(prestamoLocal);

        txtIdPrestamo.setText(prestamoLocal.getId().toString());
        txtFechaInicio.setText(prestamoLocal.getFechaPrestamo().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        txtFechaVencimiento.setText(prestamoLocal.getFechaVencimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        Repositorio r = new Repositorio();
        ServicioPrestamo sp = new ServicioPrestamo(r);
        var prestamodb = sp.buscarPrestamo(prestamoLocal);
        System.out.println(prestamodb.getMiembro());
        System.out.println("datos del miembro");
        //BUSCAR DATOS DEL MIEMBRO ENBASE AL PRESTAMO
        var miembro = prestamodb.getMiembro();
        txtNombreMiembro.setText(miembro.getNombre());
        txtApellidoMiembro.setText(miembro.getApellido());
        System.out.println("datos de la copia");
        var copia = prestamodb.getCopia();
        txtIdCopia.setText(copia.getId().toString());
        txtEstadoCopia.setText(copia.getEstado().toString());
        //libro
        System.out.println(copia.getLibro());
        var libro = copia.getLibro();
        txtTituloLibro.setText(libro.getTitulo());
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
            } else if (evt.equals(btnNuevoPrestamo)) {
                loadStage("ViewCreatePrestamo", event);
            }
            //Cargar la vista
            /*
                 var fxml = "ViewCreatePrestamo";

                FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
                Parent root = loader.load();

                // Obtener el controlador y pasarle los datos
                ViewCreatePrestamoController controller = loader.getController();
                controller.setData(fila);

                //ocultar la escena anterior y generar una nueva
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                
             */

            //}
        } catch (IOException ex) {
            Logger.getLogger(ViewShowPrestamoController.class.getName()).log(Level.SEVERE, null, ex);
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
