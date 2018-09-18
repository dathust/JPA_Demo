/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpademo.controller.SinhVienJpaController;
import jpademo.model.LopHoc;
import jpademo.model.SinhVien;

/**
 *
 * @author datpt
 */
public class SinhVienCtrl {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPADemoPU");
    EntityManager em = emf.createEntityManager();
    SinhVienJpaController svctr = new SinhVienJpaController(emf);

    public void themSinhVien(LopHoc lh) {
        SinhVien sinhVien = new SinhVien();
        sinhVien.nhapThongTin(lh);
        try {
            svctr.create(sinhVien);

        } catch (Exception e) {
            System.out.println("Ma lop khong ton tai");
        }
    }

    public void getListSV() {
        List<SinhVien> list = new ArrayList<>();
        list = svctr.getListSV();
        for (SinhVien sinhVien : list) {
            sinhVien.hienThongTin();
        }
    }
}
