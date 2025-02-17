/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.servicios;

 
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.repositorio.Repositorio;
import java.util.List;

/**
 *
 * @author exe
 */
public class ServicioCopiaLibro {
     private final Repositorio repositorio;

    public ServicioCopiaLibro(Repositorio p) {
        this.repositorio = p;
    }

    public void agregarLibro(Libro libro) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.insertar(libro);
        this.repositorio.confirmarTransaccion();
    }

    public void editarLibro(Libro libro) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.modificar(libro);
        this.repositorio.confirmarTransaccion();
    }

    public void eliminarLibro(Libro libro) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.eliminar(libro);
        this.repositorio.confirmarTransaccion();
    }

    public List<Libro> obtenerTodos() {
        return this.repositorio.buscarTodos(Libro.class);
    }
    
}
