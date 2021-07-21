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
public class BookStatus {
    private String id,ISBN,writerID,shelfID,shelfNO,floorNO,borrowingCount,instockMount,bookStatus;

    public BookStatus() {
    }

    public BookStatus(String id, String ISBN, String writerID, String shelfID, String shelfNO, String floorNO, String borrowingCount, String instockMount, String bookStatus) {
        this.id = id;
        this.ISBN = ISBN;
        this.writerID = writerID;
        this.shelfID = shelfID;
        this.shelfNO = shelfNO;
        this.floorNO = floorNO;
        this.borrowingCount = borrowingCount;
        this.instockMount = instockMount;
        this.bookStatus = bookStatus;
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

    public String getWriterID() {
        return writerID;
    }

    public void setWriterID(String writerID) {
        this.writerID = writerID;
    }

    public String getShelfID() {
        return shelfID;
    }

    public void setShelfID(String shelfID) {
        this.shelfID = shelfID;
    }

    public String getShelfNO() {
        return shelfNO;
    }

    public void setShelfNO(String shelfNO) {
        this.shelfNO = shelfNO;
    }

    public String getFloorNO() {
        return floorNO;
    }

    public void setFloorNO(String floorNO) {
        this.floorNO = floorNO;
    }

    public String getBorrowingCount() {
        return borrowingCount;
    }

    public void setBorrowingCount(String borrowingCount) {
        this.borrowingCount = borrowingCount;
    }

    public String getInstockMount() {
        return instockMount;
    }

    public void setInstockMount(String instockMount) {
        this.instockMount = instockMount;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }
    
}
