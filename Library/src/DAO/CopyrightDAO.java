/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DataHelper.MyData;
import Model.Copyright;
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
public class CopyrightDAO {
    public List<Copyright> FindCopy() throws Exception
    {
        String query="select * from COPYRIGHT_FEE";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            try(ResultSet re=r.executeQuery();)
            {
                List<Copyright> list=new ArrayList<>();
                while(re.next())
                {
                    Copyright s=new Copyright();
                    s.setId(re.getString("CPR_Fee_ID"));
                    s.setDay(re.getString("CPR_Fee_Date"));
                    s.setIsbn(re.getString("ISBN"));
                    s.setWriter(re.getString("Writer_ID"));
                    s.setChiphi(re.getString("CPR_Fee"));
                    list.add(s);
                }
                return list;
            }
            
        }
    }
    public boolean insertCopy(Copyright s) throws Exception
    {
        
        String query="exec sp_InsertCOPYRIGHT_FEE ?,?,?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, s.getIsbn());
            r.setString(2, s.getWriter());
            r.setString(3, s.getChiphi());
           r.executeUpdate();
           return true;
        }
        
    }
     public Writer FinById(String code) throws Exception
    {
        String query="select * from WRITER where Writer_Name=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, code);
            try(ResultSet re=r.executeQuery();)
            {
                if(re.next())
                {
                    Writer b=new Writer();
                     b.setId(re.getString("Writer_ID"));
                     b.setName(re.getString("Writer_Name"));
                    return b;
                }
            }
            return null;
        }
    }
}
