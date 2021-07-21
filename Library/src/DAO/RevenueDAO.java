/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DataHelper.MyData;
import Model.Revenue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class RevenueDAO {
     public List<Revenue> FindRevenue() throws Exception
    {
        String query="select * from REVENUE";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            try(ResultSet re=r.executeQuery();)
            {
                List<Revenue> list=new ArrayList<>();
                while(re.next())
                {
                    Revenue s=new Revenue();
                    s.setId(re.getString("Revenue_ID"));
                    s.setDate(re.getString("Revenue_Date"));
                    s.setGain(re.getDouble("Gain"));
                    list.add(s);
                }
                return list;
            }
            
        }
    }
}
