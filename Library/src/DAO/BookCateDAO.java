/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DataHelper.MyData;
import Model.Book;
import Model.Book_cate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class BookCateDAO {
     public List<Book_cate> FindBookCate() throws Exception
    {
        String query="select * from BOOK_CATEGORY";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            try(ResultSet re=r.executeQuery();)
            {
                List<Book_cate> list=new ArrayList<>();
                while(re.next())
                {
                    Book_cate s=new Book_cate();
                    s.setId(re.getString("Book_Cate_ID"));
                    s.setCateName(re.getString("Book_Cate_Name"));
                    list.add(s);
                }
                return list;
            }
            
        }
    }
     public boolean insertBookCate(Book_cate s) throws Exception
    {
        
        String query="INSERT INTO [dbo].[BOOK_CATEGORY]([Book_Cate_Name])"+" values(?)";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, s.getCateName());
           return r.executeUpdate()>0;
        }
    }
}
