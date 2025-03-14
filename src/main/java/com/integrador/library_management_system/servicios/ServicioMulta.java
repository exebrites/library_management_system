/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.servicios;

import com.integrador.library_management_system.modelo.CopiaLibro;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.modelo.Multa;
import com.integrador.library_management_system.modelo.Rack;
import com.integrador.library_management_system.repositorio.Repositorio;
import java.util.List;

/**
 *
 * @author exe
 */
public class ServicioMulta {

    private final Repositorio repositorio;

    public ServicioMulta(Repositorio p) {
        this.repositorio = p;
    }

    public void agregarMulta(Multa multa) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.insertar(multa);
        this.repositorio.confirmarTransaccion();
    }

    public void editarMulta(Multa multa) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.modificar(multa);
        this.repositorio.confirmarTransaccion();
    }

    public void eliminarMulta(Multa multa) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.eliminar(multa);
        this.repositorio.confirmarTransaccion();
    }

    public List<Multa> obtenerTodos() {
        return this.repositorio.buscarTodos(Multa.class);
    }

    public Multa buscarMulta(Multa multa) {
        return this.repositorio.buscar(Multa.class, multa.getId());
    }

}
