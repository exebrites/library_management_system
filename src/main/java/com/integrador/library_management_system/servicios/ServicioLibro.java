/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.servicios;

import com.integrador.library_management_system.modelo.CopiaLibro;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.repositorio.Repositorio;
import java.util.List;

/**
 *
 * @author exe
 */
public class ServicioLibro {

    private final Repositorio repositorio;

    public ServicioLibro(Repositorio p) {
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

    public List<CopiaLibro> obtenerTodos() {
        return this.repositorio.buscarTodos(CopiaLibro.class);
    }

    public List<Libro> findLibro(Libro libro) {

        return this.repositorio.buscarLibro(libro.getId());
    }

    public void copiasLibro(Long id) {
        System.out.println(this.repositorio.obtenerCopiasPorLibroId(id));
    }
}
