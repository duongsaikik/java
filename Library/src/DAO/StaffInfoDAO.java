/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DataHelper.MyData;
import Model.Staff;
import Model.StaffInfo;
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
public class StaffInfoDAO {
      public List<StaffInfo> FindStaffInfo() throws Exception
    {
        String query="select * from STAFF_INFO";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            try(ResultSet re=r.executeQuery();)
            {
                List<StaffInfo> list=new ArrayList<>();
                while(re.next())
                {
                    StaffInfo s=new StaffInfo();
                    s.setId(re.getString("Staff_ID"));
                    s.setName(re.getString("Staff_Name"));
                    s.setSex(re.getString("Sex"));
                    s.setNgaysinh(re.getString("Date_Of_Birth"));
                    s.setSdt(re.getString("Phone_Number"));
                    s.setDiachi(re.getString("Staff_Address"));
                    s.setEmail(re.getString("Staff_Email"));
                    s.setNgaylam(re.getString("In_Date"));
                    s.setNgaynghi(re.getString("Out_Date"));
                    list.add(s);
                }
                return list;
            }
            
        }
    }
    public boolean insertStaffInfo(StaffInfo s) throws Exception
    {
        
        String query="INSERT INTO [dbo].[STAFF_INFO]([Staff_ID],[Staff_Name],[Sex],[Date_Of_Birth],[Phone_Number],[Staff_Address],[Staff_Email],[In_Date])"+" values(?,?,?,?,?,?,?,?)";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, s.getId());
            r.setString(2, s.getName());
            r.setString(3, s.getSex());
            r.setString(4, s.getNgaysinh());
            r.setString(5, s.getSdt());
            r.setString(6, s.getDiachi());
            r.setString(7, s.getEmail());
            r.setString(8, s.getNgaylam());
            
           return r.executeUpdate()>0;
        }
    }
    public boolean updateStaffInfo(StaffInfo s) throws Exception
    {
        String query="UPDATE [dbo].[STAFF_INFO]" +
"   SET [Staff_Name] = ?,[Sex] = ?,[Date_Of_Birth] = ?,[Phone_Number] = ?,[Staff_Address] = ?,[Staff_Email]=?,[In_Date] = ?,[Out_Date] = ?"+" Where [Staff_ID]=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(9, s.getId());
            r.setString(1, s.getName());
            r.setString(2, s.getSex());
            r.setString(3, s.getNgaysinh());
            r.setString(4,s.getSdt());
            r.setString(5,s.getDiachi());
            r.setString(6, s.getEmail());
            r.setString(7, s.getNgaylam());
            r.setString(8, s.getNgaynghi());
           return r.executeUpdate()>0;
        }
    }
    public boolean deleteStaffInfo(StaffInfo s) throws Exception
    {
        String query="delete STAFF_INFO where Staff_ID=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, s.getId());
           return r.executeUpdate()>0;
        }
    }
    public StaffInfo FinById(String code) throws Exception
    {
        String query="select * from STAFF_INFO where Staff_ID=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, code);
            try(ResultSet re=r.executeQuery();)
            {
                if(re.next())
                {
                    StaffInfo s=new StaffInfo();
                    s.setId(re.getString("Staff_ID"));
                    s.setName(re.getString("Staff_Name"));
                    s.setSex(re.getString("Sex"));
                    s.setNgaysinh(re.getString("Date_Of_Birth"));
                    s.setSdt(re.getString("Phone_Number"));
                    s.setDiachi(re.getString("Staff_Address"));
                    s.setEmail(re.getString("Staff_Email"));
                    s.setNgaylam(re.getString("In_Date"));
                    s.setNgaynghi(re.getString("Out_Date"));
                    return s;
                }
            }
            return null;
        }
    }
}
