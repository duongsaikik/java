/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DataHelper.MyData;
import Model.BlackList;
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
public class BlackListDAO {
     public List<BlackList> FindBlackList() throws Exception
    {
        String query="select * from READER_BLACKLIST";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            try(ResultSet re=r.executeQuery();)
            {
                List<BlackList> list=new ArrayList<>();
                while(re.next())
                {
                    BlackList s=new BlackList();
                    s.setId(re.getString("BLACKLIST_ID"));
                    s.setReaderID(re.getString("Reader_ID"));
                    s.setVTcateid(re.getString("VT_Cate_ID"));
                    list.add(s);
                }
                return list;
            }
            
        }
    }
     public Violation FinById(String code) throws Exception
    {
        String query="select * from VIOLATION_CATEGORY where VT_Cate_Name=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, code);
            try(ResultSet re=r.executeQuery();)
            {
                if(re.next())
                {
                    Violation b=new Violation();
                     b.setId(re.getString("VT_Cate_ID"));
                     b.setName(re.getString("VT_Cate_Name"));
                    return b;
                }
            }
            return null;
        }
    }
      public boolean insertBlack(BlackList s) throws Exception
    {
        
        String query="INSERT INTO [dbo].[READER_BLACKLIST]([Reader_ID],[VT_Cate_ID])"+" values(?,?)";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, s.getReaderID());
            r.setString(2, s.getVTcateid());
           return r.executeUpdate()>0;
        }
    }
}
