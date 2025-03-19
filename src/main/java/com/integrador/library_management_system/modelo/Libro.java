/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.modelo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author exe
 */
@Entity
@Table(name = "libros", uniqueConstraints = {
    @UniqueConstraint(columnNames = "titulo")})
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
    
   

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 255, message = "El título no puede superar los 255 caracteres")
    @Column(nullable = false, unique = true)
    private String titulo;

    //esto asegura la composicion
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CopiaLibro> copias = new ArrayList<>();

    public Libro() {
    }

    public Libro(String editorial, String autores, String categoriaTematica, String isbn, String idioma, String titulo) {

        if (titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo TITULO no puede estar vacío.");
        }

        if (isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo ISBN no puede estar vacío.");
        }
        //regla isbn hasta 13 caracteres mas guiones 4=> 17totla ejemplo 978-950-563-656-3
        if (isbn.length() >= 17) {
            throw new IllegalArgumentException("El campo ISBN no puede superar los 17 caracteres.");
        }

        if (idioma.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo IDIOMA no puede estar vacío.");
        }

        if (editorial.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo EDITOR no puede estar vacío.");
        }
        if (categoriaTematica.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo CATEGORIA tematica no puede estar vacío.");
        }

        if (autores.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo AUTORES no puede estar vacío.");
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
        if (titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo titulo no puede estar vacío.");
        }
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.editorial);
        hash = 97 * hash + Objects.hashCode(this.autores);
        hash = 97 * hash + Objects.hashCode(this.categoriaTematica);
        hash = 97 * hash + Objects.hashCode(this.isbn);
        hash = 97 * hash + Objects.hashCode(this.idioma);
        hash = 97 * hash + Objects.hashCode(this.titulo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Libro other = (Libro) obj;
        if (!Objects.equals(this.editorial, other.editorial)) {
            return false;
        }
        if (!Objects.equals(this.autores, other.autores)) {
            return false;
        }
        if (!Objects.equals(this.categoriaTematica, other.categoriaTematica)) {
            return false;
        }
        if (!Objects.equals(this.isbn, other.isbn)) {
            return false;
        }
        if (!Objects.equals(this.idioma, other.idioma)) {
            return false;
        }
        if (!Objects.equals(this.titulo, other.titulo)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

}
