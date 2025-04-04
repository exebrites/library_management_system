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
import com.integrador.library_management_system.modelo.Prestamo;
import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioCopiaLibro;
import com.integrador.library_management_system.servicios.ServicioLibro;
import com.integrador.library_management_system.servicios.ServicioMiembro;
import com.integrador.library_management_system.servicios.ServicioPrestamo;
import com.integrador.library_management_system.util.GestorDatos;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
public class ViewCreatePrestamoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    //navegacion
    @FXML
    private Button btnInicio;

    @FXML
    private Button btnGestionarUsuario;//colocar secciones abajo
    @FXML
    private Button btnGestionarLibro;

    @FXML
    private Button btnGestionarPrestamo;

    @FXML
    private Button btnGestionarCopias;

    @FXML
    private Button btnGestionarRack;
    @FXML
    private Button btnGestionarMulta;

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
    private CopiaLibro copiaLocal;
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
    @FXML
    private TextField txtfiltroNombre;
    @FXML
    private TextField txtfiltroApellido;

    //Campos de copia
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtEstado;
    @FXML
    private TextField txtTipo;

    @FXML
    private Label lbUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //NOMBRE USER
        Miembro miembro = (Miembro) GestorDatos.obtenerDato("miembroAuth");
        lbUser.setText(miembro.getNombre());

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

    public void setData(Object data) {
        copiaLocal = (CopiaLibro) data;
        txtId.setText(copiaLocal.getId().toString());

        txtEstado.setText(copiaLocal.getEstado().toString());

        txtTipo.setText(copiaLocal.getTipo().toString());

    }

    private void filtrar() {
        System.out.println("filtrando ...");

        FilteredList<Miembro> filteredList = new FilteredList<>(listaMiembros, u -> true);

        //filtro excluyentes
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

        //filtro por nombre
        txtfiltroNombre.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredList.setPredicate(u -> u.getNombre().contains(newValue));

        });

        txtfiltroApellido.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredList.setPredicate(u -> u.getApellido().contains(newValue));

        });

        tablaMiembros.setItems(filteredList);
    }

    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        Object evt = event.getSource();

        if (evt.equals(btnGuardar)) {

            try {
                if (fila == null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Selección de Miembro");
                    alert.setHeaderText("Por favor, seleccione un miembro");
                    alert.setContentText("Para continuar, seleccione un miembro de la lista disponible.");
                    alert.showAndWait();

                } else {

                    //establecer regla 3 : adeuda un prestamo 
                    /*
                    SI el numero de prestamos activos vencidos >= 1 ENTONCES
                    notificar al usuario y denegar el prestamo
                    SINO 
                    almacenar el prestamo
                     */
                    Repositorio r = new Repositorio();
                    ServicioPrestamo sr = new ServicioPrestamo(r);
                    ServicioMiembro sm = new ServicioMiembro(r);
                    var miembro = (Miembro) fila;
                    if (sm.prestamosVencidos(miembro) == 0) {

                        sr.realizarPrestamo(miembro, (CopiaLibro) copiaLocal);

                        System.out.println(sr.contarPrestamosPorMiembro(miembro.getId()));

                        System.out.println("prestamo guardado con Exito!");
                        List<Prestamo> prestamos = sr.obtenerTodos();
                        var prestamodb = prestamos.get(prestamos.size() - 1);

                        //cargar controlador y pasar a la vista el prestamo
                        var fxml = "ViewShowPrestamo";

                        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
                        Parent root = loader.load();

                        // Obtener el controlador y pasarle los datos
                        ViewShowPrestamoController controller = loader.getController();
                        controller.setData(prestamodb);

                        //ocultar la escena anterior y generar una nueva
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } else {

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Solicitud de Préstamo");
                        alert.setHeaderText("No es posible procesar la solicitud.");
                        alert.setContentText("El miembro tiene un préstamo pendiente y no puede solicitar otro hasta que regularice su deuda. Para más información, consulte con el administrador.");
                        alert.showAndWait();
                    }
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                var mensaje = e.getMessage();
                String[] partes = mensaje.split("\\|");
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle(partes[0]);
                alert.setHeaderText(partes[1]);
                alert.setContentText(partes[2]);
                alert.showAndWait();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }
        } else if (evt.equals(btnCancelar)) {
            loadStage("ViewIndexLibro", event);
        } else if (evt.equals(btnInicio)) {
            //loadStage("ViewIndexUsuario", event);
            loadStage("ViewPrincipal", event);

        } else if (evt.equals(btnGestionarRack)) {
            loadStage("ViewIndexRack", event);
        } else if (evt.equals(btnGestionarMulta)) {
            loadStage("ViewIndexMulta", event);
        } else if (evt.equals(btnGestionarLibro)) {

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
