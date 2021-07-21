/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DataHelper.MyData;
import Model.Violation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class ViolationDAO {
     public List<Violation> FindViolation() throws Exception
    {
        String query="select * from VIOLATION_CATEGORY";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            try(ResultSet re=r.executeQuery();)
            {
                List<Violation> list=new ArrayList<>();
                while(re.next())
                {
                    Violation s=new Violation();
                    s.setId(re.getString("VT_Cate_ID"));
                    s.setName(re.getString("VT_Cate_Name"));
                    list.add(s);
                }
                return list;
            }
            
        }
    }
    public List<Violation> getName() throws Exception
    {
        String query="select * from VIOLATION_CATEGORY where VT_Cate_Name";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            try(ResultSet re=r.executeQuery();)
            {
                List<Violation> list=new ArrayList<>();
                while(re.next())
                {
                    Violation s=new Violation();
//                    s.setId(re.getString("VT_Cate_ID"));
                    s.setName(re.getString("VT_Cate_Name"));
                    list.add(s);
                }
                return list;
            }
            
        }
    }
      public boolean insertViolation(Violation s) throws Exception
    {
        
        String query="INSERT INTO [dbo].[VIOLATION_CATEGORY]([VT_Cate_Name])"+" values(?)";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, s.getName());
           return r.executeUpdate()>0;
        }
    }
}
