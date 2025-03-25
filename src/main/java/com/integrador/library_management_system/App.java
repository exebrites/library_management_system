package com.integrador.library_management_system;

import com.integrador.library_management_system.modelo.CopiaLibro;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.modelo.Miembro;
import com.integrador.library_management_system.modelo.Prestamo;
import com.integrador.library_management_system.modelo.Rack;
import com.integrador.library_management_system.modelo.TipoMiembro;

import com.integrador.library_management_system.repositorio.Repositorio;
import com.integrador.library_management_system.servicios.ServicioCopiaLibro;
import com.integrador.library_management_system.servicios.ServicioLibro;
import com.integrador.library_management_system.servicios.ServicioMiembro;
import com.integrador.library_management_system.servicios.ServicioMulta;
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
import java.util.Date;
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

        /*
        
                // Crear Repositorio
        Repositorio r = new Repositorio();
        //crear un miembro
        ServicioMiembro sm = new ServicioMiembro(r);
        Miembro m = new Miembro("biblitecario", "1", true);
        m.setTipoMiembro(TipoMiembro.BIBLIOTECARIO);
        m.setClave("123");
        m.setId(Long.valueOf(1));
        // sm.agregarUsuario(m);
        var miembrob = sm.buscarMiembro(m);
        sm.prestamosVencidos(miembrob);
         */
//-------------------------------------------------------------------------------------
      
/*
 Repositorio r = new Repositorio();
        ServicioPrestamo sp = new ServicioPrestamo(r);
        ServicioCopiaLibro scopia = new ServicioCopiaLibro(r);
        ServicioMiembro sm = new ServicioMiembro(r);
        Miembro m = new Miembro();
        m.setId(Long.valueOf(1));
        var miembrodb = sm.buscarMiembro(m);
        CopiaLibro c = new CopiaLibro();
        c.setId(Long.valueOf(1));
        var copiadb = scopia.buscarCopia(c);
        LocalDate fechaDada = LocalDate.of(2025, 3, 1); // 1 de marzo de 2025
        Prestamo prestamo = new Prestamo(fechaDada);
        prestamo.setCopia(copiadb);
        prestamo.setMiembro(miembrodb);
        sp.agregarPrestamo(prestamo);

*/
         
//---------------------------------------------------------------------------   ----------
        scene = new Scene(loadFXML("ViewLogin"), 1600, 900);
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
