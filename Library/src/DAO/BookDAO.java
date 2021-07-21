/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DataHelper.MyData;
import Model.Book;
import Model.Book_cate;
import Model.Publisher;

import Model.Writer;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.serial.SerialBlob;

/**
 *
 * @author Acer
 */
public class BookDAO {
     public List<Book> FindBook() throws Exception
    {
        
        String query="Select * From BOOK";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            try(ResultSet re=r.executeQuery();)
            {
                List<Book> list=new ArrayList<>();
                while(re.next())
                {
                    Book s=new Book();
                    s.setId(re.getString("ID"));
                    s.setISBN(re.getString("ISBN"));
                    s.setBookName(re.getString("Book_Name"));
                    s.setBookCateID(re.getString("Book_Cate_ID"));
                    s.setWriterID(re.getString("Writer_ID"));
                    s.setPubID(re.getString("Pub_ID"));
                    s.setPublisherYear(re.getString("Publishing_Year"));
                    s.setDateAdd(re.getString("Date_Added"));
                    Blob bl=re.getBlob("Book_Image");
                    if(bl!=null)
                    {
                        s.setHinh(bl.getBytes(1, (int)bl.length()));
                    }
                    list.add(s);
                 }
                return list;
            }
            
        }
    }
      public boolean insertBook(Book s) throws Exception
    {
        String query="EXECUTE sp_InsertBOOK ?,?,?,?,?,?,?,?,?,?,?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
//            //thiết lập giá trị của tham số
            r.setString(1, s.getISBN());
            r.setString(2, s.getBookName());
            r.setString(3, s.getBookCateID());
            r.setString(4, s.getWriterID());
            r.setString(5, s.getPubID());
            r.setString(6, s.getPublisherYear());
            r.setString(7, s.getDateAdd());
             if(s.getHinh()!=null)
            {
                Blob hinh=new SerialBlob(s.getHinh());
                r.setBlob(8, hinh);
            }
            else{
                Blob img=null;
                r.setBlob(8, img);
            }
            r.setString(9, s.getTu());
            r.setString(10, s.getNgan());
            r.setInt(11, s.getSoluong());
           return r.executeUpdate()>0;
        }
    }
    public boolean updateBook(Book s) throws Exception
    {
        String query="UPDATE [dbo].[BOOK]" +
"   SET [ISBN] = ?,[Book_Name] = ?,[Book_Cate_ID] = ?,[Writer_ID] = ?,[Pub_ID] = ?,[Publishing_Year] = ?,[Date_Added]=?,[Book_Image]=?"+" Where [ID]=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(9, s.getId());
             r.setString(1, s.getISBN());
            r.setString(2, s.getBookName());
            r.setString(3, s.getBookCateID());
            r.setString(4, s.getWriterID());
            r.setString(5, s.getPubID());
            r.setString(6, s.getPublisherYear());
            r.setString(7, s.getDateAdd());
            if(s.getHinh()!=null)
            {
                 Blob hinh=new SerialBlob(s.getHinh());
                r.setBlob(8, hinh);
            }
            else{
                Blob img=null;
                r.setBlob(8, img);
            }
           return r.executeUpdate()>0;
        }
    }
    public boolean deleteBook(Book s) throws Exception
    {
        String query="delete BOOK where ID=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, s.getId());
           return r.executeUpdate()>0;
        }
    }
    public Book_cate FindIDcatebook(String cate) throws Exception
    {
        String query="select * from BOOK_CATEGORY where Book_Cate_Name=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, cate);
            try(ResultSet re=r.executeQuery();)
            {
                if(re.next())
                {
                    Book_cate b=new Book_cate();
                     b.setId(re.getString("Book_Cate_ID"));
                     b.setCateName(re.getString("Book_Cate_Name"));
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
    public Publisher FindIDcatepublisher(String p) throws Exception
    {
        String query="select * from PUBLISHER where Pub_Name=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, p);
            try(ResultSet re=r.executeQuery();)
            {
                if(re.next())
                {
                    Publisher b=new Publisher();
                     b.setId(re.getString("Pub_ID"));
                     b.setPubname(re.getString("Pub_Name"));
                    return b;
                }
            }
            return null;
        }
    }
    //hàm lấy name
    public Book_cate getnamebook(String cate) throws Exception
    {
        String query="select * from BOOK_CATEGORY where Book_Cate_ID=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, cate);
            try(ResultSet re=r.executeQuery();)
            {
                if(re.next())
                {
                    Book_cate b=new Book_cate();
                     b.setId(re.getString("Book_Cate_ID"));
                     b.setCateName(re.getString("Book_Cate_Name"));
                    return b;
                }
            }
            return null;
        }
    }
    public Writer getnamewriter(String w) throws Exception
    {
        String query="select * from WRITER where Writer_ID=?";
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
    public Publisher getnamepublisher(String p) throws Exception
    {
        String query="select * from PUBLISHER where Pub_ID=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, p);
            try(ResultSet re=r.executeQuery();)
            {
                if(re.next())
                {
                    Publisher b=new Publisher();
                     b.setId(re.getString("Pub_ID"));
                     b.setPubname(re.getString("Pub_Name"));
                    return b;
                }
            }
            return null;
        }
    }
    public Book FinById(String code) throws Exception
    {
        String query="select * from BOOK where ID=?";
        try(Connection con=MyData.openConnection();//mở kết nối
            PreparedStatement r=con.prepareStatement(query);)
        {
            //thiết lập giá trị của tham số
            r.setString(1, code);
            try(ResultSet re=r.executeQuery();)
            {
                if(re.next())
                {
                    Book s=new Book();
                    s.setId(re.getString("ID"));
                    s.setISBN(re.getString("ISBN"));
                    s.setBookName(re.getString("Book_Name"));
                    s.setDateAdd(re.getString("Date_Added"));
                     Blob bl=re.getBlob("Book_Image");
                        if(bl!=null)
                        {
                            s.setHinh(bl.getBytes(1, (int)bl.length()));
                        }
                    return s;
                }
            }
            return null;
        }
    }
    
    
}
