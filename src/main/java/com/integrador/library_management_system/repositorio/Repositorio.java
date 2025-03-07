/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.repositorio;

import com.integrador.library_management_system.modelo.CopiaLibro;
import com.integrador.library_management_system.modelo.Libro;
import com.integrador.library_management_system.modelo.Miembro;
import com.integrador.library_management_system.modelo.Prestamo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

/**
 *
 * @author exe
 */
public class Repositorio {

    private final EntityManager em;

    //public Repositorio(EntityManagerFactory emf) {
    public Repositorio() {
        var emf = Persistence.createEntityManagerFactory("com.integradorLMS_PU");
        this.em = emf.createEntityManager();
    }

    public void iniciarTransaccion() {
        em.getTransaction().begin();
    }

    public void confirmarTransaccion() {
        em.getTransaction().commit();
    }

    public void descartarTransaccion() {
        em.getTransaction().rollback();
    }

    public void insertar(Object o) {
        this.em.persist(o);
    }

    public void modificar(Object o) {
        this.em.merge(o);
    }

    public void eliminar(Object o) {
        this.em.remove(o);
    }

    public void refrescar(Object o) {
        this.em.refresh(o);
    }

    public void cerrar() {
        this.em.close();
    }

    // Metodo generico
    // Acepta cualquier tipo (T) que extienda de Object
    // Devuelve un objeto de tipo (T)    
    public <T extends Object> T buscar(Class<T> clase, Object id) {
        return (T) this.em.find(clase, id);
    }

    // Metodo generico
    // Acepta cualquier tipo (T) que extienda de Object
    // Devuelve una lista de ese tipo (T)
    public <T extends Object> java.util.List<T> buscarTodos(Class<T> clase) {
        // obtengo un CriteriaBuilder
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        // creo un CriteriaQuery
        CriteriaQuery<T> consulta = cb.createQuery(clase);
        // defino el FROM
        Root<T> origen = consulta.from(clase);
        // defino el select (opcional)
        consulta.select(origen);
        // ejecuto y obtengo el resultado
        return em.createQuery(consulta).getResultList();
    }

    // el parametro de orden a pasar se obtiene del metamodelo generado por EclipseLink
    public <T extends Object, P extends Object> List<T> buscarTodosOrdenadosPor(Class<T> clase, SingularAttribute<T, P> orden) {
        // obtengo un CriteriaBuilder
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        // creo un CriteriaQuery
        CriteriaQuery<T> consulta = cb.createQuery(clase);
        // Defino el FROM
        Root<T> origen = consulta.from(clase);
        // defino el select (opcional)
        consulta.select(origen);
        // ordenado de forma ascendente (cb.asc() ) 
        // el atributo de origen definido en orden
        consulta.orderBy(cb.asc(origen.get(orden)));
        // ejecuto y obtengo el resultado
        return em.createQuery(consulta).getResultList();
    }
    //creacion de select para login

    public void consultaSQL() {

        try {
            // Consulta JPQL para obtener todos los registros de la entidad Miembro
            List<Miembro> usuarios = em.createQuery("SELECT u FROM Usuario u", Miembro.class).getResultList();

            // Mostrar resultados
            for (Miembro u : usuarios) {
                System.out.println(u);
            }
        } finally {
            em.close();

        }
    }

    public void consultaSQL2(int id) {
        try {
            // Crear el constructor de CriteriaQuery
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Miembro> cq = cb.createQuery(Miembro.class);
            Root<Miembro> root = cq.from(Miembro.class);

            // Seleccionar todos los registros
            //cq.select(root);
            cq.select(root).where(cb.equal(root.get("id"), id));

            // Ejecutar la consulta
            List<Miembro> usuarios = em.createQuery(cq).getResultList();
            // Mostrar resultados
            usuarios.forEach(System.out::println);
        } finally {
            em.close();
            //emf.close();
        }
    }

    //el repositorio me va a dar los metodos para complementar el login 
    public List<Miembro> buscarUsuario(int id, String pass) {
        //Usuario usuario = null;

        try {
            // Crear el constructor de CriteriaQuery
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Miembro> cq = cb.createQuery(Miembro.class);
            Root<Miembro> root = cq.from(Miembro.class);

            // Seleccionar todos los registros
            //cq.select(root);
            Predicate condicionId = cb.equal(root.get("id"), id);
            Predicate condicionNombre = cb.equal(root.get("clave"), pass);

            cq.where(cb.and(condicionId, condicionNombre));

            // Ejecutar la consulta
            List<Miembro> usuarios = em.createQuery(cq).getResultList();
            // Mostrar resultados
            //    usuarios.forEach(System.out::println);
            return usuarios;
        } finally {
            em.close();
            //emf.close();
        }

    }

    public Libro buscarLibro(Long id) {
        //Usuario usuario = null;

        try {
            // Crear el constructor de CriteriaQuery
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Libro> cq = cb.createQuery(Libro.class);
            Root<Libro> root = cq.from(Libro.class);

            // Seleccionar todos los registros
            //cq.select(root);
            cq.where(cb.equal(root.get("id"), id));

            // Ejecutar la consulta
            List<Libro> libros = em.createQuery(cq).getResultList();
            // Mostrar resultados
            //    usuarios.forEach(System.out::println);
            return libros.get(0);
        } finally {
            em.close();
            //emf.close();
        }

    }

    public List<CopiaLibro> obtenerCopiasPorLibroId(Long libroId) {
        try {
            if (libroId == null) {
                throw new IllegalArgumentException("El ID del libro no puede ser null");
            }

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<CopiaLibro> cq = cb.createQuery(CopiaLibro.class);
            Root<CopiaLibro> copiaRoot = cq.from(CopiaLibro.class);

            //cq.select(copiaRoot).where(cb.equal(copiaRoot.get("libro").get("id"), libroId));
            cq.select(copiaRoot).where(cb.equal(copiaRoot.get("libro_id"), libroId));

            return em.createQuery(cq).getResultList();
        } finally {
            em.close();
            //emf.close();
        }
    }

    // definir el metodo all trae todos los registro como php 
    public List<Libro> all() {
        try {
            //1. definir el EntityManager -> em 
            // 2. Con "em" obtener el CriteriaBuilder cb
            CriteriaBuilder cb = em.getCriteriaBuilder();
            // 3. Crear el CriteriaQuery apartir del "cb"
            CriteriaQuery<Libro> cq = cb.createQuery(Libro.class);
            // 4. definir la raiz 
            Root<Libro> root = cq.from(Libro.class);

            cq.select(root);

            List<Libro> resultados = em.createQuery(cq).getResultList();
            return resultados;
        } finally {
            em.close();
            //emf.close();
        }

    }

// definir el metodo find trae un registro especifco segun el id 
    /*
    public <T extends Object> T find(Class<T> clase, Long id) {

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(clase);
            Root<T> root = cq.from(clase);

            cq.select(root).where(cb.equal(root.get("id"), id));
            List<T> resultados = em.createQuery(cq).getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);
        } finally {
            em.close();
        }
    }*/
    public List<Libro> buscarFiltro(String param1, String f1) {
        var clase = Libro.class;
        //var f1 = "titulo";

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Libro> cq = cb.createQuery(clase);
        Root<Libro> root = cq.from(clase);

        Predicate predicate1 = cb.equal(root.get(f1), param1);
        //  Predicate predicate2 = cb.equal(root.get(f2), param2);

        cq.select(root).where(predicate1);

        return em.createQuery(cq).getResultList();

    }

    public List<CopiaLibro> findCopiasByLibroId(Long libroId) {
        // 1. Crear el CriteriaBuilder
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // 2. Crear la consulta y la raíz
        CriteriaQuery<CopiaLibro> query = cb.createQuery(CopiaLibro.class);
        Root<CopiaLibro> copiaRoot = query.from(CopiaLibro.class);

        // 3. Definir la relación con Libro
        Join<CopiaLibro, Libro> libroJoin = copiaRoot.join("libro");

        // 4. Agregar la condición WHERE (filtrar por ID del libro)
        query.select(copiaRoot).where(cb.equal(libroJoin.get("id"), libroId));

        // 5. Ejecutar la consulta
        return em.createQuery(query).getResultList();
    }

    //#Retorna el miembro asociado al prestamo
    //1. armar la consuta en base al prestamo
    //2. unir prestamo con miembro 
    /*
    
    public Miembro findMiembroByPrestamoId(Long prestamoId) {
        // 1. Crear el CriteriaBuilder
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // 2. Crear la consulta y la raíz
        CriteriaQuery<CopiaLibro> query = cb.createQuery(CopiaLibro.class);
        Root<CopiaLibro> copiaRoot = query.from(CopiaLibro.class);

        // 3. Definir la relación con Libro
        Join<CopiaLibro, Libro> libroJoin = copiaRoot.join("libro");

        // 4. Agregar la condición WHERE (filtrar por ID del libro)
        query.select(copiaRoot).where(cb.equal(libroJoin.get("id"), libroId));

        // 5. Ejecutar la consulta
        return em.createQuery(query).getResultList();
    }
     */
    public List<Object[]> consultaHistorialLibrosMiembro() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Miembro> miembro = query.from(Miembro.class);
        Join<Miembro, Prestamo> prestamo = miembro.join("prestamos");
        Join<Prestamo, CopiaLibro> copiaLibro = prestamo.join("copia");
        Join<CopiaLibro, Libro> libro = copiaLibro.join("libro");

// Seleccionar los campos necesarios
        query.multiselect(
                miembro.get("nombre"),
                miembro.get("apellido"),
                prestamo.get("fechaVencimiento"),
                copiaLibro.get("id"),
                libro.get("titulo"),
                miembro.get("id"),
                prestamo.get("id"),
                libro.get("id")
        );

// Ejecutar la consulta
        TypedQuery<Object[]> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
}
