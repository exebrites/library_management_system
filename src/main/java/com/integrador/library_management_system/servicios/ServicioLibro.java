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
    
    public List<Libro> obtenerTodos() {
        return this.repositorio.buscarTodos(Libro.class);
    }
    
    public Libro findLibro(Long id) {
        this.repositorio.iniciarTransaccion();
        var libro = this.repositorio.buscarLibro(id);
        this.repositorio.cerrar();
        return libro;
    }

    // implementar servicio de filtros 
    public List<Libro> buscarTitulo(String titulo) {
        
        return this.repositorio.buscarFiltro(titulo, "titulo");
    }
    
    public List<Libro> buscarISBN(String titulo) {
        
        return this.repositorio.buscarFiltro(titulo, "isbn");
    }
    
    public Libro buscarLibro(Libro libro) {
        return this.repositorio.buscar(Libro.class, libro.getId());
    }
    
    public List<CopiaLibro> copiasAsociadas(Libro libro) {
        
        return this.repositorio.findCopiasByLibroId(libro.getId());
    }

    public List<Object[]> consultarMiembrosSegunLibro(Libro libro) {
        return this.repositorio.consultarMiembrosSegunLibro(libro.getId());
    }
}
