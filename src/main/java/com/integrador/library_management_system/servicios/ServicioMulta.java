/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.servicios;

import com.integrador.library_management_system.modelo.CopiaLibro;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.modelo.Multa;
import com.integrador.library_management_system.modelo.Prestamo;
import com.integrador.library_management_system.modelo.Rack;
import com.integrador.library_management_system.repositorio.Repositorio;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    public void generarMulta(Prestamo prestamo) {
        //datos 
        //1. prestamo
        //2. copia
        var copia = prestamo.getCopia();
        //acciones
        //1. establecer el costo a cobrar
        //12. formula cant. dias despues de la fecha de vencimiento X precio estimado de la copia
        long diferencia = ChronoUnit.DAYS.between(LocalDate.now(), prestamo.getFechaVencimiento());
        var costo = diferencia * copia.getPrecioEstimado();
        costo = Math.abs(costo);
        
        //2. instanciar la multa
        Multa multa = new Multa(costo);
        multa.setPrestamo(prestamo);

        //3. guarda en db
        this.agregarMulta(multa);
    }

}
