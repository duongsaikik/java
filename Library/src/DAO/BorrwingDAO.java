/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DataHelper.MyData;
import Model.Borrwing;
import Model.Memo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class BorrwingDAO {
     public List<Borrwing> FindBorrowing() throws Exception
    {
        String query="select * from BOOK_BORROWING";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            try(ResultSet re=r.executeQuery();)
            {
                List<Borrwing> list=new ArrayList<>();
                while(re.next())
                {
                    Borrwing s=new Borrwing();
                    s.setId(re.getString("BKG_ID"));
                    s.setReaderID(re.getString("Reader_ID"));
                    s.setISBN(re.getString("ISBN"));
                    s.setNgaymuon(re.getString("Borrowed_From_Date"));
                    s.setHsd(re.getString("Borrowed_To_Date"));
                    s.setNgaytra(re.getString("Return_Date"));
                    s.setTrehan(re.getString("Deferred_Payment"));
                    s.setChiphi(re.getString("Cost_Payment"));
                    s.setStatus(re.getString("Borrowing_Status"));
                    list.add(s);
                }
                return list;
            }
            
        }
    }
    public boolean insertMuon(Borrwing s) throws Exception
    {
        String query="EXECUTE sp_InsertBOOK_BORROWING ?,?,?";
       try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query); ){          
//            //thiết lập giá trị của tham số
            r.setString(1, s.getReaderID());
            r.setString(2, s.getISBN());
            r.setString(3, s.getHsd());
            
           r.executeUpdate();
           
           return true;
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
         return false;
    }
    public boolean payment(Borrwing s) throws Exception
    {
        String query="EXECUTE sp_UpdateCost ?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, s.getId());
           r.executeUpdate();
           return true;
        }
    }
}
