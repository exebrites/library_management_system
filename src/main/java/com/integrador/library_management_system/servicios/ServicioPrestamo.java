/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.servicios;

import com.integrador.library_management_system.modelo.CopiaLibro;
import com.integrador.library_management_system.modelo.EstadoCopiaLibro;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.modelo.Miembro;
import com.integrador.library_management_system.modelo.Prestamo;
import com.integrador.library_management_system.repositorio.Repositorio;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author exe
 */
public class ServicioPrestamo {

    private final Repositorio repositorio;

    public ServicioPrestamo(Repositorio p) {
        this.repositorio = p;
    }

    public void agregarPrestamo(Prestamo prestamo) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.insertar(prestamo);
        this.repositorio.confirmarTransaccion();
    }

    public void editarPrestamo(Prestamo prestamo) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.modificar(prestamo);
        this.repositorio.confirmarTransaccion();
    }

    public void eliminarPrestamo(Prestamo prestamo) {
        this.repositorio.iniciarTransaccion();
        this.repositorio.eliminar(prestamo);
        this.repositorio.confirmarTransaccion();
    }

    public List<Prestamo> obtenerTodos() {
        return this.repositorio.buscarTodos(Prestamo.class);
    }

    public void prestamoMiembro(Long miembroId, Long copiaLibroId) {
        try {
            //  tx.begin();
            this.repositorio.iniciarTransaccion();

            Miembro miembro = this.repositorio.buscar(Miembro.class, miembroId);
            CopiaLibro copia = this.repositorio.buscar(CopiaLibro.class, copiaLibroId);

            if (miembro == null || copia == null) {
                System.out.println("miembro o copia no encontrado.");
                return;
            }
            /*
            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setEstudiante(estudiante);
            inscripcion.setCurso(curso);
            inscripcion.setCalificacion(calificacion);

             */

            Prestamo prestamo = new Prestamo();
            prestamo.setMiembro(miembro);
            prestamo.setCopia(copia);
            prestamo.setFechaPrestamo(LocalDate.now());
            prestamo.setFechaVencimiento(LocalDate.now());

            //    em.persist(inscripcion);
            this.repositorio.insertar(prestamo);
            this.repositorio.confirmarTransaccion();
            //   tx.commit();
            System.out.println("Prestamo hecho a miembro : ");
        } catch (Exception e) {
            /*
             if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
             */
        } finally {
            //  em.close();
            this.repositorio.cerrar();
        }
    }

    public Prestamo buscarPrestamo(Prestamo prestamo) {
        return this.repositorio.buscar(Prestamo.class, prestamo.getId());
    }

    public Long contarPrestamosPorMiembro(Long miembroId) {
        return this.repositorio.contarPrestamosPorMiembro(miembroId);
    }

    //realizar el prestamo para un miembro
    public void realizarPrestamo(Miembro miembro, CopiaLibro copia) {
        // datos que necesito 
        // 1. el miembro 
        //2. la copia

        //relgas que verificar 
        // 1. verificar que el miembro sea un miembro activo, de lo contrario cancelar el prestamo
        /*SI NO es activo ENTONCES exception*/
        if (miembro.isEstadoMiembro()) {
            throw new IllegalArgumentException("El miembro no esta activo");
        }
        //2. verificar que el numero de prestamo activos sea menor o igual a 5. De lo contrario cancelar prestamo
        if (this.contarPrestamosPorMiembro(miembro.getId()) >= 5) {
            throw new IllegalArgumentException("El miembro  tiene mas 5 copias prestadas");
        }
        // acciones
        // 1. instanciar un prestamo
        //2. setear fechas de inicio y de vencimiento 
        Prestamo prestamo = new Prestamo(LocalDate.now());

        //3. asociar miembro a prestamo 
        prestamo.setMiembro(miembro);

        //5. copia pasa a un estado Prestada
        copia.setEstado(EstadoCopiaLibro.PRESTADA); // llamar y modificar en db(?
        ServicioCopiaLibro scopia = new ServicioCopiaLibro(this.repositorio);
        scopia.editarCopiaLibro(copia);
        //4. asociar copia a prestamo
        prestamo.setCopia(copia);
        //6. almacenar el prestamo
        this.agregarPrestamo(prestamo);

    }
}
