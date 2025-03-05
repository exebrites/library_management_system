/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.servicios;

import com.integrador.library_management_system.modelo.CopiaLibro;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.modelo.Rack;
import com.integrador.library_management_system.repositorio.Repositorio;
import java.util.List;

/**
 *
 * @author exe
 */
public class ServicioRack {

    private final Repositorio repositorio;

    public ServicioRack(Repositorio p) {
        this.repositorio = p;
    }

    public void agregarRack(Rack rack) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.insertar(rack);
        this.repositorio.confirmarTransaccion();
    }

    public void editarRack(Rack rack) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.modificar(rack);
        this.repositorio.confirmarTransaccion();
    }

    public void eliminarRack(Rack rack) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.eliminar(rack);
        this.repositorio.confirmarTransaccion();
    }

    public List<Rack> obtenerTodos() {
        return this.repositorio.buscarTodos(Rack.class);
    }

    public Rack buscarRack(Rack rack) {
        return this.repositorio.buscar(Rack.class, rack.getId());
    }

}
