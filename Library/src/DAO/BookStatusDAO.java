/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DataHelper.MyData;
import Model.Book;
import Model.BookStatus;
import Model.Writer;
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
public class BookStatusDAO {
     public List<BookStatus> FindBookStatus() throws Exception
    {
        String query="select * from BOOK_STATUS";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            try(ResultSet re=r.executeQuery();)
            {
                List<BookStatus> list=new ArrayList<>();
                while(re.next())
                {
                    BookStatus s=new BookStatus();
                    s.setId(re.getString("ID"));
                    s.setISBN(re.getString("ISBN"));
                    s.setWriterID(re.getString("Writer_ID"));
                    s.setShelfID(re.getString("Shelf_ID"));
                    s.setShelfNO(re.getString("Shelf_NO"));
                    s.setFloorNO(re.getString("Floor_NO"));
                    s.setBorrowingCount(re.getString("Borrowing_Count"));
                    s.setInstockMount(re.getString("InStock_Mount"));
                    s.setBookStatus(re.getString("Book_Status"));
                    list.add(s);
                }
                return list;
            }
            
        }
    }
     public Book FindBook(String w) throws Exception
    {
        String query="select * from BOOK where Book_Name=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, w);
            try(ResultSet re=r.executeQuery();)
            {
                if(re.next())
                {
                    Book b=new Book();
                     b.setId(re.getString("ID"));
                     b.setBookName(re.getString("Book_Name"));
                    return b;
                }
            }
            return null;
        }
    }
     public Writer FindIDcatewriter(String w) throws Exception
    {
        String query="select * from WRITER where Writer_Name=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, w);
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
//       public boolean insertBookStatus(BookStatus s) throws Exception
//    {
////        INSERT INTO [dbo].[BOOK_STATUS]([ISBN],[Writer_ID,[Shelf_ID],[Shelf_NO],[Floor_NO,[Borrowing_Count,[InStock_Mount],[Book_Status])
////        String query="INSERT INTO [dbo].[BOOK]([ISBN],[Book_Name],[Book_Cate_ID],[Writer_ID],[Pub_ID],[Publishing_Year],[Date_Added],[Book_Image])"+" values(?,?,?,?,?,?,?,?)";
////        try(Connection con=MyData.openConnection();//mở kết nối
////            PreparedStatement r=con.prepareStatement(query);)
////        {
////            //thiết lập giá trị của tham số
////            r.setString(1, s.getISBN());
////            r.setString(2, s.getBookName());
////            r.setString(3, s.getBookCateID());
////            r.setString(4, s.getWriterID());
////            r.setString(5, s.getPubID());
////            r.setString(6, s.getPublisherYear());
////            r.setString(7, s.getDateAdd());
////             if(s.getHinh()!=null)
////            {
////                
////            }
////            else{
////                Blob img=null;
////                r.setBlob(8, img);
////            }
////           return r.executeUpdate()>0;
////        }
//    }
}
