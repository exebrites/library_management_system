package com.integrador.library_management_system.modelo;

import com.integrador.library_management_system.modelo.EstadoCopiaLibro;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.modelo.Prestamo;
import com.integrador.library_management_system.modelo.Rack;
import com.integrador.library_management_system.modelo.TipoCopiaLibro;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-03-04T16:20:56", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(CopiaLibro.class)
public class CopiaLibro_ { 

    public static volatile SingularAttribute<CopiaLibro, Libro> libro;
    public static volatile SingularAttribute<CopiaLibro, Boolean> referenciaLibro;
    public static volatile SingularAttribute<CopiaLibro, TipoCopiaLibro> tipo;
    public static volatile SingularAttribute<CopiaLibro, EstadoCopiaLibro> estado;
    public static volatile SingularAttribute<CopiaLibro, Rack> rack;
    public static volatile SingularAttribute<CopiaLibro, Long> id;
    public static volatile SetAttribute<CopiaLibro, Prestamo> prestamos;

}