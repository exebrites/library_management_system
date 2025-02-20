package com.integrador.library_management_system;

import com.integrador.library_management_system.modelo.CopiaLibro;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.modelo.Miembro;
import com.integrador.library_management_system.modelo.Prestamo;
import com.integrador.library_management_system.modelo.Rack;

import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioCopiaLibro;
import com.integrador.library_management_system.servicios.ServicioLibro;
import com.integrador.library_management_system.servicios.ServicioMiembro;
import com.integrador.library_management_system.servicios.ServicioPrestamo;
import com.integrador.library_management_system.servicios.ServicioRack;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        Repositorio repo = new Repositorio();
//-------------------------------------------------------------------------------------

        ServicioLibro sl = new ServicioLibro(repo);
        Libro libro = new Libro();
        libro.setId(Long.valueOf("1001"));

        var l1 = sl.buscarLibro(libro);
        //System.out.println(l1);
        var copias = sl.copiasAsociadas(l1);
        System.out.println(copias);
//-------------------------------------------------------------------------------------
        //  scene = new Scene(loadFXML("ViewLogin"), 1280, 800);
        //stage.setScene(scene);
        // stage.setTitle("Library Manager System");
        // stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Object FXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load(); // 🔥 Cargar el FXML antes de obtener el controlador
        return fxmlLoader.getController();
    }

    public static void main(String[] args) {
        launch();
    }

}
