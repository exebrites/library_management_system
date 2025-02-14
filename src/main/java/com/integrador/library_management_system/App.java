package com.integrador.library_management_system;

import com.integrador.library_management_system.modelo.Miembro;

import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioMiembro;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        //  EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.integradorLMS_PU");

        // Crear Repositorio
        //   su.consulta2(3);

        /*
          Repositorio repositorio = new Repositorio();
        Miembro u = new Miembro(1, "123", true, "exequiel", "brites");
        ServicioMiembro su = new ServicioMiembro(repositorio);
        su.agregarUsuario(u);
      
         */
        scene = new Scene(loadFXML("ViewLogin"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
