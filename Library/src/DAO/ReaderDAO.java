/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DataHelper.MyData;
import Model.Reader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class ReaderDAO {
      public List<Reader> FindReader() throws Exception
    {
        String query="select * from READER";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            try(ResultSet re=r.executeQuery();)
            {
                List<Reader> list=new ArrayList<>();
                while(re.next())
                {
                    Reader s=new Reader();
                    s.setId(re.getString("Reader_ID"));
                    s.setName(re.getString("Reader_Name"));
                    s.setSex(re.getString("Sex"));
                    s.setNgaysinh(re.getString("Date_Of_Birth"));
                    s.setPhone(re.getString("Phone_Number"));
                    s.setEmail(re.getString("Reader_Email"));
                    s.setDiachi(re.getString("Reader_Address"));
                    s.setNgaydangky(re.getString("Register_Date"));
                    list.add(s);
                }
                return list;
            }
            
        }
    }
    public boolean insertReader(Reader s) throws Exception
    {
        String query="INSERT INTO [dbo].[READER]([Reader_Name],[Sex],[Date_Of_Birth],[Phone_Number],[Reader_Email],[Reader_Address],[Register_Date])"+" values(?,?,?,?,?,?,?)";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            r.setString(1, s.getName());
            r.setString(2, s.getSex());
            r.setString(3, s.getNgaysinh());
            r.setString(4, s.getPhone());
            r.setString(5, s.getEmail());
            r.setString(6, s.getDiachi());
            r.setString(7, s.getNgaydangky());
           return r.executeUpdate()>0;
        }
    }
     public Reader FinById(String code) throws Exception
    {
        String query="select * from READER where Reader_ID=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, code);
            try(ResultSet re=r.executeQuery();)
            {
                if(re.next())
                {
                    Reader s=new Reader();
                    s.setId(re.getString("Reader_ID"));
                    s.setName(re.getString("Reader_Name"));
                    s.setSex(re.getString("Sex"));
                    s.setDiachi(re.getString("Reader_Address"));
                    s.setEmail(re.getString("Reader_Email"));
                    s.setNgaydangky(re.getString("Register_Date"));
                    s.setNgaysinh(re.getString("Date_Of_Birth"));
                    s.setPhone(re.getString("Phone_Number"));
                    return s;
                }
            }
            return null;
        }
    }
    public boolean deleteReader(Reader s) throws Exception
    {
        String query="delete READER where Reader_ID=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            r.setString(1, s.getId());
           return r.executeUpdate()>0;
        }
    }
    public boolean updateReader(Reader s) throws Exception
    {
        String query="UPDATE [dbo].[READER]" +
"   SET [Reader_Name] = ?,[Sex] = ?,[Date_Of_Birth] = ?,[Phone_Number] = ?,[Reader_Email] = ?,[Reader_Address]=?,[Register_Date] = ?"+" Where [Reader_ID]=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(8, s.getId());
            r.setString(1, s.getName());
            r.setString(2, s.getSex());
            r.setString(3, s.getNgaysinh());
            r.setString(4,s.getPhone());
            r.setString(5,s.getEmail());
            r.setString(6, s.getDiachi());
            r.setString(7, s.getNgaydangky());
           return r.executeUpdate()>0;
        }
    }
}
