package com.integrador.library_management_system.modelo;

import com.integrador.library_management_system.modelo.Prestamo;
import com.integrador.library_management_system.modelo.TipoMiembro;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-03-26T12:00:24", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Miembro.class)
public class Miembro_ { 

    public static volatile SingularAttribute<Miembro, String> clave;
    public static volatile SingularAttribute<Miembro, Boolean> estadoMiembro;
    public static volatile SingularAttribute<Miembro, TipoMiembro> tipoMiembro;
    public static volatile SingularAttribute<Miembro, String> apellido;
    public static volatile SingularAttribute<Miembro, Long> id;
    public static volatile SingularAttribute<Miembro, String> nombre;
    public static volatile SetAttribute<Miembro, Prestamo> prestamos;

}