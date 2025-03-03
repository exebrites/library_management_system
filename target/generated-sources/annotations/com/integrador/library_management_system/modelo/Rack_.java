package com.integrador.library_management_system.modelo;

import com.integrador.library_management_system.modelo.CopiaLibro;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-03-03T16:53:00", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Rack.class)
public class Rack_ { 

    public static volatile SingularAttribute<Rack, String> descripcion;
    public static volatile SetAttribute<Rack, CopiaLibro> copias;
    public static volatile SingularAttribute<Rack, Long> id;

}