/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DataHelper.MyData;
import Model.Statistic;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class StatisticDAO {
    public List<Statistic> FindSta() throws Exception
    {
        String query="select * from STATISTIC";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            try(ResultSet re=r.executeQuery();)
            {
                List<Statistic> list=new ArrayList<>();
                while(re.next())
                {
                    Statistic s=new Statistic();
                    s.setId(re.getString("ID"));
                    s.setNgay(re.getString("Stat_Code"));
                    s.setDoanhthu(re.getString("Revenue"));
                    s.setPhibanquyen(re.getString("CPR_Fee"));
                    s.setLoinhuan(re.getString("Profit"));
                    s.setTangtruong(re.getString("Growth"));
                    list.add(s);
                }
                return list;
            }
            
        }
    }
}
