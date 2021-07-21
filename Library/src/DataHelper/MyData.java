/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataHelper;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Acer
 */
public class MyData {
    public static Connection openConnection() throws Exception
    {
       String url = "jdbc:sqlserver://localhost:1433;databaseName=Library_Management_Biden;user=sa;password=saiki123";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection con = DriverManager.getConnection(url);
        return con;
  
    }
}
