/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DataHelper.MyData;
import Model.Shelf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class ShelfDAO {
      public List<Shelf> FindShelf() throws Exception
    {
        String query="select * from SHELF_BOOK";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            try(ResultSet re=r.executeQuery();)
            {
                List<Shelf> list=new ArrayList<>();
                while(re.next())
                {
                    Shelf s=new Shelf();
                    s.setId(re.getString("Shelf_ID"));
                    s.setTang(re.getString("Floor_NO"));
                    list.add(s);
                }
                return list;
            }
            
        }
    }
      public boolean insertShelf(Shelf s) throws Exception
    {
        
        String query="INSERT INTO [dbo].[SHELF_BOOK]([Floor_NO])"+" values(?)";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, s.getTang());
           return r.executeUpdate()>0;
        }
    }
}
