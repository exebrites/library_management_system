/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.modelo;

import com.integrador.library_management_system.modelo.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;

/**
 *
 * @author exe
 */
public class UsuarioJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public UsuarioJpaController() {
        emf = Persistence.createEntityManagerFactory("com.integradorLMS_PU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("puto", enfe);
            }
            em.remove(usuario);
            em.getTransaction().commit();

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException {

        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            usuario = em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = usuario.getId();
                //if(findAlumno(id)==null){
                //   throw new NonexistentEntityException("puto");
                //}
                throw ex;
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Usuario findAlumno(int id) throws NonexistentEntityException {

        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("puto", enfe);
            }
            //  em.remove(alumno);
            em.getTransaction().commit();
            return usuario;
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    public List<Usuario> findAlumnoEntities() {
        //findAlumnoEntities   throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return findAlumnoEntities(true, -1, -1);
    }

    private List<Usuario> findAlumnoEntities(boolean b, int i, int i0) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        // creo un CriteriaQuery
        var consulta = cb.createQuery(Usuario.class);
        // defino el FROM
        var origen = consulta.from(Usuario.class);
        // defino el select (opcional)
        consulta.select(origen);
        // ejecuto y obtengo el resultado
        return em.createQuery(consulta).getResultList();
    }  
    
}
