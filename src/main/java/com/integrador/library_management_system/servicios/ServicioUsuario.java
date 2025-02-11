/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.servicios;

import com.integrador.library_management_system.modelo.Usuario;
import com.integrador.library_management_system.repositorio.Repositorio;
import java.util.List;

/**
 *
 * @author exe
 */
public class ServicioUsuario {

    private final Repositorio repositorio;

    public ServicioUsuario(Repositorio p) {
        this.repositorio = p;
    }

    public void agregarUsuario(Usuario usuario) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.insertar(usuario);
        this.repositorio.confirmarTransaccion();
    }

    public void editarUsuario(Usuario usuario) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.modificar(usuario);
        this.repositorio.confirmarTransaccion();
    }

    public void eliminarUsuario(Usuario usuario) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.eliminar(usuario);
        this.repositorio.confirmarTransaccion();
    }

    public List<Usuario> obtenerTodos() {
        return this.repositorio.buscarTodos(Usuario.class);
    }
}
