/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.integrador.library_management_system.controlador;

import static com.integrador.library_management_system.App.loadFXML;
import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioUsuario;
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
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

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
        ServicioUsuario su = new ServicioUsuario(repositorio);

        Object evt = event.getSource();

        if (evt.equals(btnLogin)) {
            if (!txtIdentificador.getText().isEmpty() && !txtContrasenia.getText().isEmpty()) {
                var identificador = Integer.parseInt(txtIdentificador.getText());
                String contrasenia = txtContrasenia.getText();

                if (su.login(identificador, contrasenia)) {
                    System.out.print("usuario logueado exitosamente");
                    loadStage("ViewPrincipal", event);
                } else {
                    System.out.print("upss algo falló xd");

                }
                // desde aca se va a realizar el login al menos un true o false para continuar 
                //System.out.print(identificador);
            } else {
                //JOptionPane.showMessageDialog(null, "Error al iniciar sesion", JOptionPane.WARNING_MESSAGE);
            }

        }

        /**
         * Initializes the controller class.
         */
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void loadStage(String url, Event event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();

        Scene scene = new Scene(loadFXML(url), 640, 480);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
