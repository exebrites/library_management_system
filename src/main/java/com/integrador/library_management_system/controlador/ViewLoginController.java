/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.integrador.library_management_system.controlador;

import static com.integrador.library_management_system.App.loadFXML;
import com.integrador.library_management_system.modelo.TipoMiembro;
import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioMiembro;
import com.integrador.library_management_system.util.GestorDatos;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author exe
 */
public class ViewLoginController implements Initializable {

    @FXML
    private TextField txtIdentificador;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private Button btnLogin;

    @FXML
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();
        if (evt.equals(txtIdentificador)) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }

        } else if (evt.equals(txtContrasenia)) {

            if (event.getCharacter().equals(" ")) {
                event.consume();
            }

        }

    }

    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        Repositorio repositorio = new Repositorio();
        // Usuario u = new Usuario(3, "exequiel");
        ServicioMiembro su = new ServicioMiembro(repositorio);

        Object evt = event.getSource();

        if (evt.equals(btnLogin)) {
            if (!txtIdentificador.getText().isEmpty() && !txtContrasenia.getText().isEmpty()) {
                var identificador = Integer.parseInt(txtIdentificador.getText());
                String contrasenia = txtContrasenia.getText();
                var miembro = su.login(identificador, contrasenia);
                if (!miembro.isEmpty()) {
                    System.out.print("usuario logueado exitosamente");

                    var miembroAuth = miembro.get(0);
                    if (!miembroAuth.isEstadoMiembro()) {// tomarlo como bajamiembro
                        System.out.println("estado del user: " + miembroAuth.isEstadoMiembro());
                        GestorDatos.guardarDato("miembroAuth", miembroAuth);

                        /*
                    SI miembro es bibliotecario ENTONCES viewprincipal
                    SINO ENTONCES viewUsuario
                    
                    
                         */
                        if (miembroAuth.getTipoMiembro().equals(TipoMiembro.BIBLIOTECARIO)) {
                            loadStage("ViewPrincipal", event);
                        } else if (miembroAuth.getTipoMiembro().equals(TipoMiembro.USUARIO)) {

                            System.out.println("USUARIO");
                            loadStage("ViewUsuario", event);
                        }
                    } else {

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Cuenta Inactiva");
                        alert.setHeaderText("Acceso no permitido");
                        alert.setContentText("La cuenta con la que intenta ingresar ha sido dada de baja. Por favor, contacte al administrador para más información.");
                        alert.showAndWait();

                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error de Autenticación");
                    alert.setHeaderText("Credenciales incorrectas");
                    alert.setContentText("El identificador o la contraseña ingresados no coinciden con ningún usuario registrado. Por favor, verifique sus datos e inténtelo nuevamente.");
                    alert.showAndWait();

                }
                // desde aca se va a realizar el login al menos un true o false para continuar 
                //System.out.print(identificador);
            } else {
                //JOptionPane.showMessageDialog(null, "Error al iniciar sesion", JOptionPane.WARNING_MESSAGE);
            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void loadStage(String url, Event event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();

        Scene scene = new Scene(loadFXML(url), 1600, 900);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Library Manager System");
        stage.show();
    }
}
