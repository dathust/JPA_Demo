/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpademo;

import java.util.Date;
import jpademo.model.LopHoc;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import jpademo.controller.LopHocJpaController;
import jpademo.controller.SinhVienJpaController;
import jpademo.model.SinhVien;
import view.ViewMain;

/**
 *
 * @author datpt
 */
public class JPADemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ViewMain main = new ViewMain();
        main.menu();
    }
    public static void main4(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPADemoPU");
        EntityManager em = emf.createEntityManager();
        //TypedQuery<LopHoc> createQuery = em.createQuery("Select l from LopHoc l", LopHoc.class);
        //TypedQuery<LopHoc> createQuery = em.createNamedQuery("LopHoc.findAll", LopHoc.class);
        TypedQuery<LopHoc> createQuery = em.createQuery("Select l from LopHoc l where l.ma = :ma", LopHoc.class);
        createQuery.setParameter("ma", 2);
        LopHoc lopHoc = createQuery.getSingleResult();
        SinhVienJpaController svjc = new SinhVienJpaController(emf);
        SinhVien sv = new SinhVien();
        sv.setIDLop(lopHoc);
        sv.setTen("Sv1");
        sv.setNgaySinh(new Date());
        svjc.create(sv);

        LopHocJpaController lhjc = new LopHocJpaController(emf);
        List<LopHoc> list = lhjc.getListLopHoc();
        for (LopHoc lopHoc1 : list) {
            System.out.println("Lop1: " + lopHoc1.getTenLop());
        }

        try {
            LopHoc findLopHoc = lhjc.findLopHoc(3);
            System.out.println("Lop: " + findLopHoc.getTenLop());

        } catch (Exception e) {
            System.out.println("Ko tim thay");
        }
    }

    public static void main1(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPADemoPU");
        EntityManager em = emf.createEntityManager();
        //TypedQuery<LopHoc> createQuery = em.createQuery("Select l from LopHoc l", LopHoc.class);
        //TypedQuery<LopHoc> createQuery = em.createNamedQuery("LopHoc.findAll", LopHoc.class);
        TypedQuery<LopHoc> createQuery = em.createQuery("Select l from LopHoc l where l.ma > :ma", LopHoc.class);
        createQuery.setParameter("ma", 2);
        List<LopHoc> list = createQuery.getResultList();
        for (LopHoc lopHoc : list) {
            System.out.println("Ten: " + lopHoc.getTenLop());
        }
        LopHocJpaController lhjc = new LopHocJpaController(emf);
        LopHoc hoc = new LopHoc();
        hoc.setTenLop("Lop 02");
        lhjc.create(hoc);

    }

    public static void main3(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPADemoPU");
        EntityManager em = emf.createEntityManager();
        LopHoc find = em.find(LopHoc.class, 1);
        System.out.println("ma: " + find.getMa());
        System.out.println("ten: " + find.getTenLop());
    }

    public static void main2(String[] args) {
        // TODO code application logic here
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPADemoPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        LopHoc lopHoc = new LopHoc();
        lopHoc.setTenLop("Lop 01");
        try {
            em.persist(lopHoc);
            em.persist(lopHoc);
            em.persist(lopHoc);
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPADemoPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

}
