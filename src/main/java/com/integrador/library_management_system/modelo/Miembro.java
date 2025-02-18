/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author exe
 */
@Entity
@Table
public class Miembro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String clave;
    @Column
    private boolean estadoMiembro;
    @Column
    private String nombre;
    @Column
    private String apellido;

   
    @OneToMany(mappedBy = "miembro")
    private Set<Prestamo> prestamos;

    public Miembro() {
    }

    public Miembro(String nombre, String apellido, boolean estadoMiembro) {

        this.estadoMiembro = estadoMiembro;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean isEstadoMiembro() {
        return estadoMiembro;
    }

    public void setEstadoMiembro(boolean estadoMiembro) {
        this.estadoMiembro = estadoMiembro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFullName() {

        return nombre + " " + apellido;
    }

    @Override
    public String toString() {
        return "Miembro{" + "id=" + id + ", clave=" + clave + ", estadoMiembro=" + estadoMiembro + ", nombre=" + nombre + ", apellido=" + apellido + '}';
    }

}
