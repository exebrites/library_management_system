package com.integrador.library_management_system.modelo;

import com.integrador.library_management_system.modelo.Prestamo;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-02-26T15:14:52", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Miembro.class)
public class Miembro_ { 

    public static volatile SingularAttribute<Miembro, String> clave;
    public static volatile SingularAttribute<Miembro, Boolean> estadoMiembro;
    public static volatile SingularAttribute<Miembro, String> apellido;
    public static volatile SingularAttribute<Miembro, Long> id;
    public static volatile SingularAttribute<Miembro, String> nombre;
    public static volatile SetAttribute<Miembro, Prestamo> prestamos;

}