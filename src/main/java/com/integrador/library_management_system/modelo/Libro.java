/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
public class Libro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String editorial;
    @Column
    private String autores;
    @Column
    private String categoriaTematica;
    @Column
    private String isbn;
    @Column
    private String idioma;
    @Column
    private String titulo;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CopiaLibro> copias = new ArrayList<>();

    public Libro() {
    }

    public Libro(String editorial, String autores, String categoriaTematica, String isbn, String idioma, String titulo) {

        if (editorial.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo editor no puede estar vacío.");
        }

        if (autores.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo autores no puede estar vacío.");
        }

        if (categoriaTematica.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo categoria tematica no puede estar vacío.");
        }

        if (isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo isbn no puede estar vacío.");
        }

        if (idioma.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo idioma no puede estar vacío.");
        }

        if (titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo titulo no puede estar vacío.");
        }
        this.editorial = editorial.toUpperCase();
        this.autores = autores.toUpperCase();
        this.categoriaTematica = categoriaTematica.toUpperCase();
        this.isbn = isbn.toUpperCase();
        this.idioma = idioma.toUpperCase();
        this.titulo = titulo.toUpperCase();
    }

    public Long getId() {
        return id;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public String getCategoriaTematica() {
        return categoriaTematica;
    }

    public void setCategoriaTematica(String categoriaTematica) {
        this.categoriaTematica = categoriaTematica;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "Libro{" + "id=" + id + ", editorial=" + editorial + ", autores=" + autores + ", categoriaTematica=" + categoriaTematica + ", isbn=" + isbn + ", idioma=" + idioma + ", titulo=" + titulo + '}';
    }

    public void addCopia(CopiaLibro copia) {
        copia.setLibro(this);  // Establece la relación en la copia
        copias.add(copia);
    }

    public void removeCopia(CopiaLibro copia) {
        copias.remove(copia);
        copia.setLibro(null);  // Rompe la relación
    }

    public List<CopiaLibro> getCopias() {
        return copias;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
