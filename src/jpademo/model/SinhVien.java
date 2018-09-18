/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpademo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Scanner;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author datpt
 */
@Entity
@Table(name = "SinhVien")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SinhVien.findAll", query = "SELECT s FROM SinhVien s")
    , @NamedQuery(name = "SinhVien.findByMa", query = "SELECT s FROM SinhVien s WHERE s.ma = :ma")
    , @NamedQuery(name = "SinhVien.findByTen", query = "SELECT s FROM SinhVien s WHERE s.ten = :ten")
    , @NamedQuery(name = "SinhVien.findByNgaySinh", query = "SELECT s FROM SinhVien s WHERE s.ngaySinh = :ngaySinh")})
public class SinhVien implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Ma")
    private Integer ma;
    @Column(name = "Ten")
    private String ten;
    @Column(name = "NgaySinh")
    @Temporal(TemporalType.DATE)
    private Date ngaySinh;
    @JoinColumn(name = "IDLop", referencedColumnName = "Ma")
    @ManyToOne(optional = false)
    private LopHoc iDLop;

    public SinhVien() {
    }

    public SinhVien(Integer ma) {
        this.ma = ma;
    }

    public Integer getMa() {
        return ma;
    }

    public void setMa(Integer ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public LopHoc getIDLop() {
        return iDLop;
    }

    public void setIDLop(LopHoc iDLop) {
        this.iDLop = iDLop;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ma != null ? ma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SinhVien)) {
            return false;
        }
        SinhVien other = (SinhVien) object;
        if ((this.ma == null && other.ma != null) || (this.ma != null && !this.ma.equals(other.ma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpademo.SinhVien[ ma=" + ma + " ]";
    }

    public void nhapThongTin(LopHoc lh) {
        System.out.print("Ten : ");
        String ten = new Scanner(System.in).nextLine();
        setTen(ten);
        System.out.println("Ma lop: ");
        setIDLop(lh);
        setNgaySinh(new Date());
    }

    public void hienThongTin() {
        System.out.println("Ma Sv: " + getMa());
        System.out.println("Ten Sv: "+getTen());
        System.out.println("Ma Lop: " + getIDLop().getMa());
        System.out.println("Ngay sinh: " + getNgaySinh());
    }

}
