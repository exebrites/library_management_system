/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.servicios;

import com.integrador.library_management_system.modelo.Miembro;
import com.integrador.library_management_system.modelo.Prestamo;
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

    /* public void consulta() {
        this.repositorio.consultaSQL();
    }

    public void consulta2(int id) {
        this.repositorio.consultaSQL2(id);
    }*/
    public Miembro buscarMiembro(Miembro miembro) {
        return this.repositorio.buscar(Miembro.class, miembro.getId());
    }

    //El servicio se encarga del login
    public List<Miembro> login(int id, String pass) {

        //buscarUsuario();
        return this.repositorio.buscarUsuario(id, pass);

    }

    //El servicio se encarga del logout
    public List<Object[]> consultaHistorialLibrosMiembro() {
        return this.repositorio.consultaHistorialLibrosMiembro();
    }

    //regla 3 
    /*
        obtener al menos un prestamo activo cuyo vencimiento ha sido superado. 
     */
    public int prestamosVencidos(Miembro miembro) {
        var prestamos = miembro.getPrestamos();
        int sumar = 0;
        for (Prestamo prestamo : prestamos) {
            if (prestamo.isEstado() && prestamo.hanPasadoDiezDias()) {
                System.out.println(prestamo);
                sumar++;
            }
        }
        return sumar;
    }
}
