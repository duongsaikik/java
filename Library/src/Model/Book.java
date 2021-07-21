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
public class Book {
    private String id,ISBN,BookName,BookCateID,WriterID,PubID,PublisherYear,DateAdd;
    private String tu,ngan;
    private int soluong;
    private byte[] hinh;
    public Book() {
    }

    public Book(String id, String ISBN, String BookName, String BookCateID, String WriterID, String PubID, String PublisherYear, String DateAdd, String tu, String ngan, int soluong, byte[] hinh) {
        this.id = id;
        this.ISBN = ISBN;
        this.BookName = BookName;
        this.BookCateID = BookCateID;
        this.WriterID = WriterID;
        this.PubID = PubID;
        this.PublisherYear = PublisherYear;
        this.DateAdd = DateAdd;
        this.tu = tu;
        this.ngan = ngan;
        this.soluong = soluong;
        this.hinh = hinh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String BookName) {
        this.BookName = BookName;
    }

    public String getBookCateID() {
        return BookCateID;
    }

    public void setBookCateID(String BookCateID) {
        this.BookCateID = BookCateID;
    }

    public String getWriterID() {
        return WriterID;
    }

    public void setWriterID(String WriterID) {
        this.WriterID = WriterID;
    }

    public String getPubID() {
        return PubID;
    }

    public void setPubID(String PubID) {
        this.PubID = PubID;
    }

    public String getPublisherYear() {
        return PublisherYear;
    }

    public void setPublisherYear(String PublisherYear) {
        this.PublisherYear = PublisherYear;
    }

    public String getDateAdd() {
        return DateAdd;
    }

    public void setDateAdd(String DateAdd) {
        this.DateAdd = DateAdd;
    }

    public String getTu() {
        return tu;
    }

    public void setTu(String tu) {
        this.tu = tu;
    }

    public String getNgan() {
        return ngan;
    }

    public void setNgan(String ngan) {
        this.ngan = ngan;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }

    
    
    
}
