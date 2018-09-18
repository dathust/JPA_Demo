/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.LopHocCtrl;
import controller.SinhVienCtrl;
import java.util.Scanner;
import jpademo.model.LopHoc;
import jpademo.model.SinhVien;

/**
 *
 * @author datpt
 */
public class ViewMain {

    LopHocCtrl lhc = new LopHocCtrl();
    SinhVienCtrl svc = new SinhVienCtrl();

    public void menu() {
        while (true) {
            System.out.println("1. Them lop hoc");
            System.out.println("2. Danh sach lop hoc");
            System.out.println("3. Them sinh vien");
            System.out.println("4. Danh sach sinh vien");
            System.out.println("5. Thoat");
            int chon = new Scanner(System.in).nextInt();

            switch (chon) {
                case 1:

                    lhc.themLopHoc();
                    break;
                case 2:
                    lhc.getListLopHoc();
                    break;
                case 3:
                    LopHoc lh = lhc.chonLopHoc();
                    svc.themSinhVien(lh);
                    break;
                case 4:
                    svc.getListSV();
                    break;
                case 5:
                    System.exit(0);
            }
        }

    }

}
