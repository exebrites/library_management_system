package com.integrador.library_management_system.modelo;

import com.integrador.library_management_system.modelo.CopiaLibro;
import com.integrador.library_management_system.modelo.Miembro;
import com.integrador.library_management_system.modelo.Multa;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-03-17T17:36:16", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Prestamo.class)
public class Prestamo_ { 

    public static volatile SingularAttribute<Prestamo, Boolean> estado;
    public static volatile SingularAttribute<Prestamo, LocalDate> fechaPrestamo;
    public static volatile SingularAttribute<Prestamo, LocalDate> fechaVencimiento;
    public static volatile SingularAttribute<Prestamo, Multa> multa;
    public static volatile SingularAttribute<Prestamo, Long> id;
    public static volatile SingularAttribute<Prestamo, CopiaLibro> copia;
    public static volatile SingularAttribute<Prestamo, Miembro> miembro;

}