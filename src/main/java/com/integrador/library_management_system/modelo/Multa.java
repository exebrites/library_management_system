/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;
/**
 *
 * @author exe
 */
@Entity
@Table
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private float costo;

    @OneToOne
    @JoinColumn(name = "prestamo_id", nullable = false)
    private Prestamo prestamo;

    public Multa() {
    }

    public Multa(float costo) {

        this.costo = costo;
    }

}
