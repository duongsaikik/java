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
public class Borrwing {
    private String id,readerID,ISBN,ngaymuon,hsd,ngaytra,trehan,chiphi,status;

    public Borrwing() {
    }

    public Borrwing(String id, String readerID, String ISBN, String ngaymuon, String hsd, String ngaytra, String trehan, String chiphi, String status) {
        this.id = id;
        this.readerID = readerID;
        this.ISBN = ISBN;
        this.ngaymuon = ngaymuon;
        this.hsd = hsd;
        this.ngaytra = ngaytra;
        this.trehan = trehan;
        this.chiphi = chiphi;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReaderID() {
        return readerID;
    }

    public void setReaderID(String readerID) {
        this.readerID = readerID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getNgaymuon() {
        return ngaymuon;
    }

    public void setNgaymuon(String ngaymuon) {
        this.ngaymuon = ngaymuon;
    }

    public String getHsd() {
        return hsd;
    }

    public void setHsd(String hsd) {
        this.hsd = hsd;
    }

    public String getNgaytra() {
        return ngaytra;
    }

    public void setNgaytra(String ngaytra) {
        this.ngaytra = ngaytra;
    }

    public String getTrehan() {
        return trehan;
    }

    public void setTrehan(String trehan) {
        this.trehan = trehan;
    }

    public String getChiphi() {
        return chiphi;
    }

    public void setChiphi(String chiphi) {
        this.chiphi = chiphi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   

    
    
}
