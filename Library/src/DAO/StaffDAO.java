/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DataHelper.MyData;
import Model.Staff;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class StaffDAO {
    public boolean insertStaff(Staff s) throws Exception
    {
        String query="INSERT INTO [dbo].[STAFF]([Staff_User],[Staff_Pass],[IsAdmin],[Designation])"+" values(?,?,?,?)";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, s.getUser());
            r.setString(2, s.getPass());
            r.setString(3, s.getAdmin());
            r.setString(4, s.getDesignation());
            
           return r.executeUpdate()>0;
        }
    }
    public boolean deleteStaff(Staff s) throws Exception
    {
        String query="delete STAFF where Staff_ID=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, s.getId());
           return r.executeUpdate()>0;
        }
    }
     public boolean updateStaff(Staff s) throws Exception
    {
        String query="UPDATE [dbo].[STAFF]" +
"   SET [Staff_User] = ?,[Staff_Pass] = ?,[IsAdmin] = ?,[Designation] = ?"+" Where [Staff_ID]=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(5, s.getId());
            r.setString(1, s.getUser());
            r.setString(2, s.getPass());
            r.setString(3, s.getAdmin());
            r.setString(4, s.getDesignation());
           
           return r.executeUpdate()>0;
        }
    }
     public Staff FinById(String code) throws Exception
    {
        String query="select * from STAFF where Staff_ID=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, code);
            try(ResultSet re=r.executeQuery();)
            {
                if(re.next())
                {
                    Staff s=CreateStaff(re);
                    return s;
                }
            }
            return null;
        }
    }
      public Staff FindId(String code) throws Exception
    {
        String query="select * from STAFF where Staff_User=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, code);
            try(ResultSet re=r.executeQuery();)
            {
                if(re.next())
                {
                    Staff s=CreateStaff(re);
                    return s;
                }
            }
            return null;
        }
    }
     public List<Staff> FindAll() throws Exception
    {
        String query="select * from STAFF";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            try(ResultSet re=r.executeQuery();)
            {
                List<Staff> list=new ArrayList<>();
                while(re.next())
                {
                    Staff s=CreateStaff(re);
                    list.add(s);
                }
                return list;
            }
            
        }
    }
   
    private Staff CreateStaff(final ResultSet re) throws SQLException {
        Staff s=new Staff();
        s.setId(re.getString("Staff_ID"));
        s.setUser(re.getString("Staff_User"));
        s.setPass(re.getString("Staff_Pass"));
        s.setAdmin(re.getString("IsAdmin"));
        s.setDesignation(re.getString("Designation"));
        return s;
    }
}
