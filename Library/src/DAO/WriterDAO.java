/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DataHelper.MyData;
import Model.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class WriterDAO {
    public List<Writer> FindWriter() throws Exception
    {
        String query="select * from WRITER";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            try(ResultSet re=r.executeQuery();)
            {
                List<Writer> list=new ArrayList<>();
                while(re.next())
                {
                    Writer s=new Writer();
                    s.setId(re.getString("Writer_ID"));
                    s.setName(re.getString("Writer_Name"));
                    s.setSex(re.getString("Sex"));
                    s.setNgaysinh(re.getString("Date_Of_Birth"));
                    s.setPhone(re.getString("Phone_Number"));
                    s.setEmail(re.getString("Writer_Email"));
                    s.setAddress(re.getString("Writer_Address"));
                    list.add(s);
                }
                return list;
            }
            
        }
    }
    public boolean insertWriter(Writer s) throws Exception
    {
        
        String query="INSERT INTO [dbo].[WRITER]([Writer_Name],[Sex],[Date_Of_Birth],[Phone_Number],[Writer_Email],[Writer_Address])"+" values(?,?,?,?,?,?)";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, s.getName());
            r.setString(2, s.getSex());
            r.setString(3, s.getNgaysinh());
            r.setString(4, s.getPhone());
            r.setString(5, s.getEmail());
            r.setString(6, s.getAddress());
           return r.executeUpdate()>0;
        }
    }
    public boolean updateWriter(Writer s) throws Exception
    {
        String query="UPDATE [dbo].[WRITER]" +
"   SET [Writer_Name] = ?,[Sex] = ?,[Date_Of_Birth] = ?,[Phone_Number] = ?,[Writer_Email] = ?,[Writer_Address] = ?"+" Where [Writer_ID]=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(7, s.getId());
            r.setString(1, s.getName());
            r.setString(2, s.getSex());
            r.setString(3, s.getNgaysinh());
            r.setString(4,s.getPhone());
            r.setString(5, s.getEmail());
            r.setString(6, s.getAddress());
           return r.executeUpdate()>0;
        }
    }
    public boolean deleteWriter(Writer s) throws Exception
    {
        String query="delete WRITER where Writer_ID=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, s.getId());
           return r.executeUpdate()>0;
        }
    }
    public Writer FinById(String code) throws Exception
    {
        String query="select * from WRITER where Writer_ID=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, code);
            try(ResultSet re=r.executeQuery();)
            {
                if(re.next())
                {
                    Writer s=new Writer();
                    s.setId(re.getString("Writer_ID"));
                    s.setName(re.getString("Writer_Name"));
                    s.setSex(re.getString("Sex"));
                    s.setNgaysinh(re.getString("Date_Of_Birth"));
                    s.setPhone(re.getString("Phone_Number"));
                    s.setEmail(re.getString("Writer_Email"));
                    s.setAddress(re.getString("Writer_Address"));
                    return s;
                }
            }
            return null;
        }
    }
}
