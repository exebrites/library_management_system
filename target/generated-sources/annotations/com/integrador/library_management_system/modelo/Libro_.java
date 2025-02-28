package com.integrador.library_management_system.modelo;

import com.integrador.library_management_system.modelo.CopiaLibro;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-02-28T16:13:18", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Libro.class)
public class Libro_ { 

    public static volatile SingularAttribute<Libro, String> editorial;
    public static volatile SingularAttribute<Libro, String> categoriaTematica;
    public static volatile SingularAttribute<Libro, String> isbn;
    public static volatile ListAttribute<Libro, CopiaLibro> copias;
    public static volatile SingularAttribute<Libro, String> titulo;
    public static volatile SingularAttribute<Libro, String> idioma;
    public static volatile SingularAttribute<Libro, Long> id;
    public static volatile SingularAttribute<Libro, String> autores;

}