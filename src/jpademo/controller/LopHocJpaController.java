/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpademo.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpademo.model.SinhVien;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import jpademo.model.LopHoc;
import jpademo.controller.exceptions.IllegalOrphanException;
import jpademo.controller.exceptions.NonexistentEntityException;

/**
 *
 * @author datpt
 */
public class LopHocJpaController implements Serializable {

    public LopHocJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LopHoc lopHoc) {
        if (lopHoc.getSinhVienCollection() == null) {
            lopHoc.setSinhVienCollection(new ArrayList<SinhVien>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<SinhVien> attachedSinhVienCollection = new ArrayList<SinhVien>();
            for (SinhVien sinhVienCollectionSinhVienToAttach : lopHoc.getSinhVienCollection()) {
                sinhVienCollectionSinhVienToAttach = em.getReference(sinhVienCollectionSinhVienToAttach.getClass(), sinhVienCollectionSinhVienToAttach.getMa());
                attachedSinhVienCollection.add(sinhVienCollectionSinhVienToAttach);
            }
            lopHoc.setSinhVienCollection(attachedSinhVienCollection);
            em.persist(lopHoc);
            for (SinhVien sinhVienCollectionSinhVien : lopHoc.getSinhVienCollection()) {
                LopHoc oldIDLopOfSinhVienCollectionSinhVien = sinhVienCollectionSinhVien.getIDLop();
                sinhVienCollectionSinhVien.setIDLop(lopHoc);
                sinhVienCollectionSinhVien = em.merge(sinhVienCollectionSinhVien);
                if (oldIDLopOfSinhVienCollectionSinhVien != null) {
                    oldIDLopOfSinhVienCollectionSinhVien.getSinhVienCollection().remove(sinhVienCollectionSinhVien);
                    oldIDLopOfSinhVienCollectionSinhVien = em.merge(oldIDLopOfSinhVienCollectionSinhVien);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LopHoc lopHoc) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LopHoc persistentLopHoc = em.find(LopHoc.class, lopHoc.getMa());
            Collection<SinhVien> sinhVienCollectionOld = persistentLopHoc.getSinhVienCollection();
            Collection<SinhVien> sinhVienCollectionNew = lopHoc.getSinhVienCollection();
            List<String> illegalOrphanMessages = null;
            for (SinhVien sinhVienCollectionOldSinhVien : sinhVienCollectionOld) {
                if (!sinhVienCollectionNew.contains(sinhVienCollectionOldSinhVien)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SinhVien " + sinhVienCollectionOldSinhVien + " since its IDLop field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<SinhVien> attachedSinhVienCollectionNew = new ArrayList<SinhVien>();
            for (SinhVien sinhVienCollectionNewSinhVienToAttach : sinhVienCollectionNew) {
                sinhVienCollectionNewSinhVienToAttach = em.getReference(sinhVienCollectionNewSinhVienToAttach.getClass(), sinhVienCollectionNewSinhVienToAttach.getMa());
                attachedSinhVienCollectionNew.add(sinhVienCollectionNewSinhVienToAttach);
            }
            sinhVienCollectionNew = attachedSinhVienCollectionNew;
            lopHoc.setSinhVienCollection(sinhVienCollectionNew);
            lopHoc = em.merge(lopHoc);
            for (SinhVien sinhVienCollectionNewSinhVien : sinhVienCollectionNew) {
                if (!sinhVienCollectionOld.contains(sinhVienCollectionNewSinhVien)) {
                    LopHoc oldIDLopOfSinhVienCollectionNewSinhVien = sinhVienCollectionNewSinhVien.getIDLop();
                    sinhVienCollectionNewSinhVien.setIDLop(lopHoc);
                    sinhVienCollectionNewSinhVien = em.merge(sinhVienCollectionNewSinhVien);
                    if (oldIDLopOfSinhVienCollectionNewSinhVien != null && !oldIDLopOfSinhVienCollectionNewSinhVien.equals(lopHoc)) {
                        oldIDLopOfSinhVienCollectionNewSinhVien.getSinhVienCollection().remove(sinhVienCollectionNewSinhVien);
                        oldIDLopOfSinhVienCollectionNewSinhVien = em.merge(oldIDLopOfSinhVienCollectionNewSinhVien);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = lopHoc.getMa();
                if (findLopHoc(id) == null) {
                    throw new NonexistentEntityException("The lopHoc with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LopHoc lopHoc;
            try {
                lopHoc = em.getReference(LopHoc.class, id);
                lopHoc.getMa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lopHoc with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<SinhVien> sinhVienCollectionOrphanCheck = lopHoc.getSinhVienCollection();
            for (SinhVien sinhVienCollectionOrphanCheckSinhVien : sinhVienCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This LopHoc (" + lopHoc + ") cannot be destroyed since the SinhVien " + sinhVienCollectionOrphanCheckSinhVien + " in its sinhVienCollection field has a non-nullable IDLop field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(lopHoc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LopHoc> findLopHocEntities() {
        return findLopHocEntities(true, -1, -1);
    }

    public List<LopHoc> findLopHocEntities(int maxResults, int firstResult) {
        return findLopHocEntities(false, maxResults, firstResult);
    }

    private List<LopHoc> findLopHocEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LopHoc.class));
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

    public LopHoc findLopHoc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LopHoc.class, id);
        } finally {
            em.close();
        }
    }

    public int getLopHocCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LopHoc> rt = cq.from(LopHoc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<LopHoc> getListLopHoc() {
        EntityManager em = getEntityManager();
        TypedQuery<LopHoc> createQuery = em.createNamedQuery("LopHoc.findAll", LopHoc.class);

        List<LopHoc> list = createQuery.getResultList();
        return list;
    }

}
