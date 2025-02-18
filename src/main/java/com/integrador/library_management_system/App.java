package com.integrador.library_management_system;

import com.integrador.library_management_system.modelo.CopiaLibro;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.modelo.Miembro;
import com.integrador.library_management_system.modelo.Prestamo;

import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioCopiaLibro;
import com.integrador.library_management_system.servicios.ServicioLibro;
import com.integrador.library_management_system.servicios.ServicioMiembro;
import com.integrador.library_management_system.servicios.ServicioPrestamo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
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
        //crear miembro y la copia
        Miembro miembro1 = new Miembro("exe", "brites", true);
        CopiaLibro copia1 = new CopiaLibro();

        //persistir en base de datos ambos
        ServicioMiembro sm = new ServicioMiembro(repo);
        sm.agregarUsuario(miembro1);
        ServicioCopiaLibro scl = new ServicioCopiaLibro(repo);
        scl.agregarCopiaLibro(copia1);

        //Llamar al servicio de prestamo para asociar miembro con copias
        ServicioPrestamo sp = new ServicioPrestamo(repo);
        sp.prestamoMiembro(miembro1.getId(), copia1.getId());
//-------------------------------------------------------------------------------------
        scene = new Scene(loadFXML("ViewLogin"), 1280, 800);
        stage.setScene(scene);
        stage.setTitle("Library Manager System");
        stage.show();
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
