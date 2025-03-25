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
import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioLibro;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
    private Button btnInicio;
    @FXML
    private Button btnGestionarRack;
    @FXML
    private Button btnGestionarMulta;

    /*NAVEGACION*/
    @FXML
    private Button btnPrestamo;

    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtEditorial;
    @FXML
    private TextField txtAutores;
    @FXML
    private TextField txtCategoria;
    @FXML
    private TextField txtIsbn;
    @FXML
    private TextField txtIdioma;

    //TABLA 
    //tablaCopias
    @FXML
    private TableView<CopiaLibro> tablaCopias;
    //colId
    //colTipo
    //colEstado
    //colReferencia

    @FXML
    private TableColumn<CopiaLibro, Long> colId;
    @FXML
    private TableColumn<CopiaLibro, String> colTipo;
    @FXML
    private TableColumn<CopiaLibro, String> colEstado;
    @FXML
    private TableColumn colReferencia;

    @FXML
    private ObservableList<CopiaLibro> listaCopias;

    private Libro libro1;
    private CopiaLibro copia;

    //NOMBRE USER
    @FXML
    private Label lbUser;

    @FXML
    private Button btnPrestado;

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

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colReferencia.setCellValueFactory(new PropertyValueFactory<>("referenciaLibro"));

        colReferencia.setCellFactory(column -> new TextFieldTableCell<Libro, Boolean>() {
            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "SI" : "NO");
                }
            }
        });
        //pasa la lista de copias en base a un libro
        Repositorio r = new Repositorio();
        ServicioLibro sl = new ServicioLibro(r);

        var copias = sl.copiasAsociadas(libro1);
        listaCopias = FXCollections.observableArrayList(copias);
        tablaCopias.setItems(listaCopias);
        tablaCopias.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)
                -> {
            this.copia = newValue;
            if (newValue != null) {
                System.out.println("Seleccionaste: " + newValue.getId());
            }
        }
        );

//     
    }

    public void setData(Object data) {
        Libro libro = (Libro) data;
        libro1 = libro;

        txtTitulo.setText(libro.getTitulo());
        txtEditorial.setText(libro.getEditorial());
        txtAutores.setText(libro.getAutores());
        txtCategoria.setText(libro.getCategoriaTematica());
        txtIsbn.setText(libro.getIsbn());
        txtIdioma.setText(libro.getIdioma());
        inicializarTabla();

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

            } else if (evt.equals(btnGestionarRack)) {
                loadStage("ViewIndexRack", event);
            } else if (evt.equals(btnInicio)) {
                //loadStage("ViewIndexUsuario", event);
                loadStage("ViewPrincipal", event);

            } else if (evt.equals(btnGestionarMulta)) {
                loadStage("ViewIndexMulta", event);
            } else if (evt.equals(btnPrestamo)) {
                //loadStage("ViewIndexUsuario", event);
                //Cargar la vista

                if (copia == null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Selección de Copia");
                    alert.setHeaderText("Es necesario seleccionar una copia para continuar.");
                    alert.setContentText("Por favor, elija una copia de la lista para proceder con la acción.");
                    alert.showAndWait();

                } else if (copia.isReferenciaLibro()) {
                    Alert alert2 = new Alert(Alert.AlertType.WARNING);
                    alert2.setTitle("Selección Incorrecta");
                    alert2.setHeaderText("Copia de Referencia Seleccionada");
                    alert2.setContentText("La copia seleccionada es una copia de referencia y no puede ser prestada. Por favor, seleccione otra copia para continuar.");
                    alert2.showAndWait();

                } else if (copia.getEstado().equals(EstadoCopiaLibro.PRESTADA)) {
                    Alert alert2 = new Alert(Alert.AlertType.WARNING);
                    alert2.setTitle("Copia en Préstamo");
                    alert2.setHeaderText("Copia ya prestada");
                    alert2.setContentText("La copia seleccionada ya está prestada. Por favor, elija otra copia disponible para continuar.");
                    alert2.showAndWait();

                } else {
                    var fxml = "ViewCreatePrestamo";

                    FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
                    Parent root = loader.load();

                    // Obtener el controlador y pasarle los datos
                    ViewCreatePrestamoController controller = loader.getController();
                    controller.setData(copia);

                    //ocultar la escena anterior y generar una nueva
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            } else if (evt.equals(btnPrestado)) {

                var fxml = "ViewLibroPrestamosMiembro";

                FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
                Parent root = loader.load();

                // Obtener el controlador y pasarle los datos
                ViewLibroPrestamosMiembrosController controller = loader.getController();
                controller.setData(libro1);

                //ocultar la escena anterior y generar una nueva
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

            }

        } catch (IOException ex) {
            Logger.getLogger(ViewShowLibroController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
