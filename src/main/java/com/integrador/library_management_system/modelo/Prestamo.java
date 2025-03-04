/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author exe
 */
@Entity
@Table
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate fechaPrestamo; //indica la fecha de incio del prestamo
    @Column
    private LocalDate fechaVencimiento;

    @ManyToOne
    @JoinColumn(name = "miembro_id", nullable = false)
    private Miembro miembro;

    @ManyToOne
    @JoinColumn(name = "copia_id", nullable = false)
    private CopiaLibro copia;

    @OneToOne(mappedBy = "prestamo")
    private Multa multa;

    public Prestamo() {
    }

    public Prestamo(LocalDate fechaPrestamo, LocalDate fechaVencimiento) {

        if (fechaPrestamo == null) {
            throw new IllegalArgumentException("El campo fechaPrestamo no puede estar vacío.");
        }
        if (fechaVencimiento == null) {
            throw new IllegalArgumentException("El campo fechaPrestamo no puede estar vacío.");
        }
        if (fechaPrestamo.isAfter(fechaPrestamo)) {
            throw new IllegalArgumentException("El campo fechaPrestamo no puede ser mayor a FechaVencimiento.");
        }

        this.fechaPrestamo = fechaPrestamo;
        this.fechaVencimiento = fechaVencimiento;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    public CopiaLibro getCopia() {
        return copia;
    }

    public void setCopia(CopiaLibro copia) {
        this.copia = copia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        if (fechaPrestamo == null) {
            throw new IllegalArgumentException("El campo fechaPrestamo no puede estar vacío.");
        }
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        if (fechaVencimiento == null) {
            throw new IllegalArgumentException("El campo fechaPrestamo no puede estar vacío.");
        }
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String toString() {
        return "Prestamo{" + "id=" + id + ", fechaPrestamo=" + fechaPrestamo + ", fechaVencimiento=" + fechaVencimiento + '}';
    }

}
