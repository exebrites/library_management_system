/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
public class Prestamo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate fechaPrestamo; //indica la fecha de incio del prestamo
    @Column
    private LocalDate fechaVencimiento;
    @Column
    private boolean estado = true;
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

    public Prestamo(LocalDate fechaPrestamo) {

        if (fechaPrestamo == null) {
            throw new IllegalArgumentException("El campo fechaPrestamo no puede estar vacío.");
        }

        this.fechaPrestamo = fechaPrestamo;

        this.fechaVencimiento = fechaPrestamo.plusDays(10);
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Multa getMulta() {
        return multa;
    }

    public void setMulta(Multa multa) {
        this.multa = multa;
    }

    @Override
    public String toString() {
        return "Prestamo{" + "id=" + id + ", fechaPrestamo=" + fechaPrestamo + ", fechaVencimiento=" + fechaVencimiento + ", estado=" + estado + ", miembro=" + miembro + ", copia=" + copia + ", multa=" + multa + '}';
    }

    public boolean hanPasadoDiezDias() {
        return ChronoUnit.DAYS.between(fechaPrestamo, LocalDate.now()) >= 10;
    }

}
