/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author exe
 */
@Entity
@Table
public class CopiaLibro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean referenciaLibro = false;

    @Enumerated(EnumType.STRING)  // Almacena como texto en la BD
    private TipoCopiaLibro tipo;

    @Enumerated(EnumType.STRING)  // Almacena como texto en la BD
    private EstadoCopiaLibro estado = EstadoCopiaLibro.DISPONIBLE;

    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false) // Clave foránea
    private Libro libro;

    public CopiaLibro() {
    }

    public CopiaLibro(Long id, Libro libro, TipoCopiaLibro tipo) {
        this.id = id;
        this.tipo = tipo;
        this.libro = libro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoCopiaLibro getTipo() {
        return tipo;
    }

    public void setTipo(TipoCopiaLibro tipo) {
        this.tipo = tipo;
    }

    public EstadoCopiaLibro getEstado() {
        return estado;
    }

    public void setEstado(EstadoCopiaLibro estado) {
        this.estado = estado;
    }

    public boolean isReferenciaLibro() {
        return referenciaLibro;
    }

    public void setReferenciaLibro(boolean referenciaLibro) {
        this.referenciaLibro = referenciaLibro;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "CopiaLibro{" + "id=" + id + ", referenciaLibro=" + referenciaLibro + ", tipo=" + tipo + ", estado=" + estado + ", libro=" + libro + '}';
    }

}
