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
public class BlackList {
    private String id,readerID,VTcateid;

    public BlackList() {
    }

    public BlackList(String id, String readerID, String VTcateid) {
        this.id = id;
        this.readerID = readerID;
        this.VTcateid = VTcateid;
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

    public String getVTcateid() {
        return VTcateid;
    }

    public void setVTcateid(String VTcateid) {
        this.VTcateid = VTcateid;
    }
    
}
