/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Acer
 */
public class Statistic {
    private String id,ngay,doanhthu,phibanquyen,loinhuan,tangtruong;

    public Statistic() {
    }

    public Statistic(String id, String ngay, String doanhthu, String phibanquyen, String loinhuan, String tangtruong) {
        this.id = id;
        this.ngay = ngay;
        this.doanhthu = doanhthu;
        this.phibanquyen = phibanquyen;
        this.loinhuan = loinhuan;
        this.tangtruong = tangtruong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getDoanhthu() {
        return doanhthu;
    }

    public void setDoanhthu(String doanhthu) {
        this.doanhthu = doanhthu;
    }

    public String getPhibanquyen() {
        return phibanquyen;
    }

    public void setPhibanquyen(String phibanquyen) {
        this.phibanquyen = phibanquyen;
    }

    public String getLoinhuan() {
        return loinhuan;
    }

    public void setLoinhuan(String loinhuan) {
        this.loinhuan = loinhuan;
    }

    public String getTangtruong() {
        return tangtruong;
    }

    public void setTangtruong(String tangtruong) {
        this.tangtruong = tangtruong;
    }
    
}
