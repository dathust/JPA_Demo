/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpademo.controller.LopHocJpaController;
import jpademo.model.LopHoc;

/**
 *
 * @author datpt
 */
public class LopHocCtrl {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPADemoPU");
    EntityManager em = emf.createEntityManager();
    LopHocJpaController lhjc = new LopHocJpaController(emf);

    public void themLopHoc() {
        LopHoc lh = new LopHoc();
        lh.nhapThongTin();
        lhjc.create(lh);
    }

    public void getListLopHoc() {
        List<LopHoc> list = new ArrayList<>();
        list = lhjc.getListLopHoc();
        for (LopHoc lopHoc : list) {
            lopHoc.hienThongTin();
        }
    }

    public LopHoc chonLopHoc() {

        System.out.println("Chon ma lop hoc: ");
        getListLopHoc();
        int maLop = new Scanner(System.in).nextInt();
        LopHoc findLopHoc = lhjc.findLopHoc(3);
        return findLopHoc;
    }
}
