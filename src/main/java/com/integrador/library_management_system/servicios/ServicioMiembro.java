/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.servicios;

import com.integrador.library_management_system.modelo.Miembro;
import com.integrador.library_management_system.repositorio.Repositorio;
import java.util.List;
import javax.persistence.NoResultException;

/**
 *
 * @author exe
 */
public class ServicioMiembro {

    private final Repositorio repositorio;

    public ServicioMiembro(Repositorio p) {
        this.repositorio = p;
    }

    public void agregarUsuario(Miembro usuario) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.insertar(usuario);
        this.repositorio.confirmarTransaccion();
    }

    public void editarUsuario(Miembro usuario) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.modificar(usuario);
        this.repositorio.confirmarTransaccion();
    }

    public void eliminarUsuario(Miembro usuario) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.eliminar(usuario);
        this.repositorio.confirmarTransaccion();
    }

    public List<Miembro> obtenerTodos() {
        return this.repositorio.buscarTodos(Miembro.class);
    }

    public void consulta() {
        this.repositorio.consultaSQL();
    }

    public void consulta2(int id) {
        this.repositorio.consultaSQL2(id);
    }

    //El servicio se encarga del login
    public List<Miembro> login(int id, String pass) {

        //buscarUsuario();
        return this.repositorio.buscarUsuario(id, pass);

    }
    //El servicio se encarga del logout

}
