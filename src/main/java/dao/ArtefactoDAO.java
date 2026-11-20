/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import model.Artefacto;
import util.JPAUtil;

public class ArtefactoDAO {

    public void insertArtefacto(Artefacto artefacto) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(artefacto);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Artefacto selectArtefacto(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Artefacto.class, id);
        } finally {
            em.close();
        }
    }

    public List<Artefacto> selectAllArtefactos() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT a FROM Artefacto a", Artefacto.class).getResultList();
        } finally {
            em.close();
        }
    }

    public boolean deleteArtefacto(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean success = false;
        try {
            tx.begin();
            Artefacto artefacto = em.find(Artefacto.class, id);
            if (artefacto != null) {
                em.remove(artefacto);
                tx.commit();
                success = true;
            } else {
                tx.rollback(); // no existe: cerramos la transacción para no dejar la conexión abierta
            }
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
        return success;
    }

    public boolean updateArtefacto(Artefacto artefacto) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean success = false;
        try {
            tx.begin();
            // merge() insertaría una fila nueva si el id no existe, así que primero comprobamos que existe
            if (em.find(Artefacto.class, artefacto.getId()) != null) {
                em.merge(artefacto);
                tx.commit();
                success = true;
            } else {
                tx.rollback();
            }
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
        return success;
    }
}