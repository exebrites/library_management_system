/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private int id;
    @Column
    private String clave;
    @Column
    private boolean estadoMiembro;

    public Miembro() {
    }

    public Miembro(int id, String clave, boolean estadoMiembro) {
        this.id = id;
        this.clave = clave;
        this.estadoMiembro = estadoMiembro;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Miembro{" + "id=" + id + ", clave=" + clave + ", estadoMiembro=" + estadoMiembro + '}';
    }

}
