/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpademo.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Scanner;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author datpt
 */
@Entity
@Table(name = "LopHoc")
@XmlRootElement//convert to XML
@NamedQueries({
    @NamedQuery(name = "LopHoc.findAll", query = "SELECT l FROM LopHoc l")//dinh nghia name cho cau query
                                                                          // thay = viec viet query thi chi can goi name      
    , @NamedQuery(name = "LopHoc.findByMa", query = "SELECT l FROM LopHoc l WHERE l.ma = :ma")
    , @NamedQuery(name = "LopHoc.findByTenLop", query = "SELECT l FROM LopHoc l WHERE l.tenLop = :tenLop")})
public class LopHoc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//tu tang
    @Basic(optional = false)
    @Column(name = "Ma")
    private Integer ma;
    
    @Column(name = "TenLop")
    private String tenLop;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDLop")
    private Collection<SinhVien> sinhVienCollection;

    public LopHoc() {
    }

    public LopHoc(Integer ma) {
        this.ma = ma;
    }

    public Integer getMa() {
        return ma;
    }

    public void setMa(Integer ma) {
        this.ma = ma;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    @XmlTransient
    public Collection<SinhVien> getSinhVienCollection() {
        return sinhVienCollection;
    }

    public void setSinhVienCollection(Collection<SinhVien> sinhVienCollection) {
        this.sinhVienCollection = sinhVienCollection;
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
        if (!(object instanceof LopHoc)) {
            return false;
        }
        LopHoc other = (LopHoc) object;
        if ((this.ma == null && other.ma != null) || (this.ma != null && !this.ma.equals(other.ma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpademo.LopHoc[ ma=" + ma + " ]";
    }
    
    public void nhapThongTin(){
        System.out.print("Ten Lop: ");
        String tenLop = new Scanner(System.in).nextLine();
        setTenLop(tenLop);
    }
    
    public void hienThongTin() {
        System.out.println("Ma Lop: " + getMa());
        System.out.println("Ten Lop: " + getTenLop());
    }
}
