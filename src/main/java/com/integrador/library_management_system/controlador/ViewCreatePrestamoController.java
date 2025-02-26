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
import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioLibro;
import com.integrador.library_management_system.servicios.ServicioMiembro;
import com.integrador.library_management_system.servicios.ServicioPrestamo;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
public class ViewCreatePrestamoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    //navegacion
//create
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
    private Object copiaLocal;
    @FXML
    private DatePicker dataInicio;
    @FXML
    private DatePicker dataVencimiento;
    
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    
    @FXML
    private TextField txtFiltro;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        colIdentificador.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        
        Repositorio r = new Repositorio();
        ServicioMiembro sm = new ServicioMiembro(r);
        List<Miembro> miembros = sm.obtenerTodos();
        
        listaMiembros = FXCollections.observableArrayList(miembros);
        tablaMiembros.setItems(listaMiembros);
        
        tablaMiembros.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)
                -> {
            this.fila = newValue;
            if (newValue != null) {
                System.out.println("Seleccionaste: " + newValue.getId());
            }
        }
        );
        
        filtrar();
        
    }
    
    public void setData(Object copia) {
        copiaLocal = copia;
    }
    
    private void filtrar() {
        System.out.println("filtrando ...");
        
        FilteredList<Miembro> filteredList = new FilteredList<>(listaMiembros, u -> true);
        
        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println(newValue);

            if (newValue.isEmpty()) {
                filteredList.setPredicate(u -> true);
            } else {
                try {
                    int idFiltrar = Integer.parseInt(newValue);
                    filteredList.setPredicate(u -> u.getId() == idFiltrar);
                } catch (NumberFormatException e) {
                    filteredList.setPredicate(u -> false); // Si no es número, no muestra nada
                }
            }
        });
        
        tablaMiembros.setItems(filteredList);
    }
    
    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        Object evt = event.getSource();
        
        if (evt.equals(btnGuardar)) {
            
            try {
                
                LocalDate inicio = dataInicio.getValue();
                LocalDate vencimiento = dataVencimiento.getValue();
                System.out.println("inicio" + inicio);
                System.out.println("venc" + vencimiento);
                System.out.println("miembro" + fila);
                System.out.println("copia" + copiaLocal.toString());

                //crear objeto prestamo
                Prestamo prestamo = new Prestamo();
                prestamo.setFechaPrestamo(inicio);
                prestamo.setFechaVencimiento(vencimiento);

                //asociar miembro a prestamo 
                prestamo.setCopia((CopiaLibro) copiaLocal);
                //asociar copia a prestamo
                prestamo.setMiembro((Miembro) fila);
                //instanciar servicioPrestamo
                Repositorio r = new Repositorio();
                ServicioPrestamo sr = new ServicioPrestamo(r);

                //almacenar prestamo
                sr.agregarPrestamo(prestamo);
                System.out.println("prestamo guardado con Exito!");
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (evt.equals(btnCancelar)) {
            loadStage("ViewIndexLibro", event);
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
