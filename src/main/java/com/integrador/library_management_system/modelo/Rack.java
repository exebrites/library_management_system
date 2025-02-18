/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.modelo;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author exe
 */
@Entity
@Table
public class Rack implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String descripcion;

    @OneToMany(mappedBy = "rack")
    private Set<CopiaLibro> copias;

    public Rack(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Rack() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<CopiaLibro> getCopias() {
        return copias;
    }

    public void setCopias(Set<CopiaLibro> copias) {
        this.copias = copias;
    }

    @Override
    public String toString() {
        return "Rack{" + "id=" + id + ", descripcion=" + descripcion + ", copias=" + copias + '}';
    }

}
