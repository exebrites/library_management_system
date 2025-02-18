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
public class ServicioCopiaLibro {

    private final Repositorio repositorio;

    public ServicioCopiaLibro(Repositorio p) {
        this.repositorio = p;
    }

    public void agregarCopiaLibro(CopiaLibro copia) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.insertar(copia);
        this.repositorio.confirmarTransaccion();
    }

    public void editarCopiaLibro(CopiaLibro copia) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.modificar(copia);
        this.repositorio.confirmarTransaccion();
    }

    public void eliminarCopiaLibro(CopiaLibro copia) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.eliminar(copia);
        this.repositorio.confirmarTransaccion();
    }

    public List<CopiaLibro> obtenerTodos() {
        return this.repositorio.buscarTodos(CopiaLibro.class);
    }

}
