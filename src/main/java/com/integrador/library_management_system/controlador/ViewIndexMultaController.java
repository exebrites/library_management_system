/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.integrador.library_management_system.controlador;

import com.integrador.library_management_system.App;
import static com.integrador.library_management_system.App.loadFXML;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.modelo.Miembro;
import com.integrador.library_management_system.modelo.Multa;
import com.integrador.library_management_system.modelo.Prestamo;
import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioLibro;
import com.integrador.library_management_system.servicios.ServicioMulta;
import com.integrador.library_management_system.servicios.ServicioPrestamo;
import com.integrador.library_management_system.util.GestorDatos;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
public class ViewIndexMultaController implements Initializable {

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
    private Label lbUser;

    /*Info User*/
 /*
    
    definir tabla prestamos
    
    definir columnas
    definir la listaprestamos 
    definir el prestamo fila
     */
    @FXML
    private TableView<Multa> tablaMultas;
    @FXML
    private TableColumn<Multa, Long> colId;
    @FXML
    private TableColumn<Multa, Float> colCosto;
    @FXML
    private TableColumn<Multa, String> colIdPrestamo;

    private ObservableList<Multa> listaMultas;

    private Multa multa;
    /*Gestionar prestamos*/
    @FXML
    private Button btnEditar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /*DATOS DE USUARIO*/
        Miembro miembro = (Miembro) GestorDatos.obtenerDato("miembroAuth");
        lbUser.setText(miembro.getNombre());

        /*PRESTAMOS*/
 /*
        
        settear columnas
                
        obtener prestamos
        inicializar listaprestamos
        settear tabla prestamos
                
        establacer el formato para estado prestamos ACTIVO - NO ACTIVO
        establecer el formato de fechas dd/mm/yyyy
                
        implementar el listener para seleccionar
         */
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        colIdPrestamo.setCellValueFactory(cellData -> {
            var id = cellData.getValue().getPrestamo().getId();

            return new javafx.beans.property.SimpleObjectProperty<>(id.toString());

        });
//        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        Repositorio r = new Repositorio();
        ServicioMulta sm = new ServicioMulta(r);
        var multas = sm.obtenerTodos();
        listaMultas = FXCollections.observableArrayList(multas);
        tablaMultas.setItems(listaMultas);
        tablaMultas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.multa = newSelection;
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
            } else if (evt.equals(btnEditar)) {
                System.out.println("editar");
                System.out.println(this.multa);
                //configurar vista y envio a vista;

                /*   var fxml = "ViewEditPrestamo";

                FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
                Parent root = loader.load();

                // Obtener el controlador y pasarle los datos
                ViewEditPrestamoController detalleController = loader.getController();
                detalleController.setData(prestamo);

                //ocultar la escena anterior y generar una nueva
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();*/
            }
            //settear el btn editar

        } catch (IOException ex) {
            Logger.getLogger(ViewPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
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
