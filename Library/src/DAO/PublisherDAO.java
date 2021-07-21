/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DataHelper.MyData;
import Model.Publisher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class PublisherDAO {
     public List<Publisher> FindPublisher() throws Exception
    {
        String query="select * from PUBLISHER";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            try(ResultSet re=r.executeQuery();)
            {
                List<Publisher> list=new ArrayList<>();
                while(re.next())
                {
                    Publisher s=new Publisher();
                    s.setId(re.getString("Pub_ID"));
                    s.setPubname(re.getString("Pub_Name"));
                    list.add(s);
                }
                return list;
            }
            
        }
    }
      public boolean insertPublisher(Publisher s) throws Exception
    {
        
        String query="INSERT INTO [dbo].[PUBLISHER]([Pub_Name])"+" values(?)";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, s.getPubname());
           return r.executeUpdate()>0;
        }
    }
}
