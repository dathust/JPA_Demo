/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpademo.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpademo.controller.exceptions.NonexistentEntityException;
import jpademo.model.LopHoc;
import jpademo.model.SinhVien;

/**
 *
 * @author datpt
 */
public class SinhVienJpaController implements Serializable {

    public SinhVienJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SinhVien sinhVien) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LopHoc IDLop = sinhVien.getIDLop();
            if (IDLop != null) {
                IDLop = em.getReference(IDLop.getClass(), IDLop.getMa());
                sinhVien.setIDLop(IDLop);
            }
            em.persist(sinhVien);
            if (IDLop != null) {
                IDLop.getSinhVienCollection().add(sinhVien);
                IDLop = em.merge(IDLop);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SinhVien sinhVien) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SinhVien persistentSinhVien = em.find(SinhVien.class, sinhVien.getMa());
            LopHoc IDLopOld = persistentSinhVien.getIDLop();
            LopHoc IDLopNew = sinhVien.getIDLop();
            if (IDLopNew != null) {
                IDLopNew = em.getReference(IDLopNew.getClass(), IDLopNew.getMa());
                sinhVien.setIDLop(IDLopNew);
            }
            sinhVien = em.merge(sinhVien);
            if (IDLopOld != null && !IDLopOld.equals(IDLopNew)) {
                IDLopOld.getSinhVienCollection().remove(sinhVien);
                IDLopOld = em.merge(IDLopOld);
            }
            if (IDLopNew != null && !IDLopNew.equals(IDLopOld)) {
                IDLopNew.getSinhVienCollection().add(sinhVien);
                IDLopNew = em.merge(IDLopNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sinhVien.getMa();
                if (findSinhVien(id) == null) {
                    throw new NonexistentEntityException("The sinhVien with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SinhVien sinhVien;
            try {
                sinhVien = em.getReference(SinhVien.class, id);
                sinhVien.getMa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sinhVien with id " + id + " no longer exists.", enfe);
            }
            LopHoc IDLop = sinhVien.getIDLop();
            if (IDLop != null) {
                IDLop.getSinhVienCollection().remove(sinhVien);
                IDLop = em.merge(IDLop);
            }
            em.remove(sinhVien);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SinhVien> findSinhVienEntities() {
        return findSinhVienEntities(true, -1, -1);
    }

    public List<SinhVien> findSinhVienEntities(int maxResults, int firstResult) {
        return findSinhVienEntities(false, maxResults, firstResult);
    }

    private List<SinhVien> findSinhVienEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SinhVien.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public SinhVien findSinhVien(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SinhVien.class, id);
        } finally {
            em.close();
        }
    }

    public int getSinhVienCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SinhVien> rt = cq.from(SinhVien.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
       
    }
     public List<SinhVien> getListSV() {
        EntityManager em = getEntityManager();
        TypedQuery<SinhVien> createQuery = em.createNamedQuery("SinhVien.findAll", SinhVien.class);

        List<SinhVien> list = createQuery.getResultList();
        return list;
    }
    
}
