/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DataHelper.MyData;
import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Acer
 */
public class AccountDAO {
    public Account CheckLogin(String user,String pass) throws SQLException, Exception
    {
         String query="select * from STAFF where Staff_User=? and Staff_Pass=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, user);
            r.setString(2, pass);
            try(ResultSet re=r.executeQuery();)
            {
                if(re.next())//nếu có dữ liệu trả về
                {
                    Account ac=new Account();
                    ac.setUsername(user);
                    ac.setPassword(pass);
                    return ac;
                }
             }
        }
        return null;
    }
}
