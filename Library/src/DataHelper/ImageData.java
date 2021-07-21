/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataHelper;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Acer
 */
public class ImageData {
    public static Image resize(Image defau,int with,int height)
    {
        Image result=defau.getScaledInstance(with, height, Image.SCALE_SMOOTH);
        return result;
    }
    //chuyển ảnh sang mảng byte
    public static byte[] Imagearray(Image img,String type) throws IOException
    {
        BufferedImage bm=new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D gr=bm.createGraphics();
        gr.drawImage(img, 0, 0,null);
        gr.dispose();
        ByteArrayOutputStream by=new ByteArrayOutputStream();
        ImageIO.write(bm, type, by);
        byte[] arrayImage=by.toByteArray();
        return arrayImage;
    }
    public static Image createImage(byte[] data,String type) throws IOException
    {
        ByteArrayInputStream in=new ByteArrayInputStream(data);
        BufferedImage bf=ImageIO.read(in);
        Image img=bf.getScaledInstance(bf.getWidth(), bf.getHeight(), Image.SCALE_SMOOTH);
        return img;
    }
}
