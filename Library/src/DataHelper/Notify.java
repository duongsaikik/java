/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataHelper;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author Acer
 */
public class Notify {
    public static void ChickenMessageDialog(Component parent,String content,String title)
    {
        JOptionPane.showMessageDialog(parent, content,title,JOptionPane.INFORMATION_MESSAGE);
    }
    public static void ChickenErrorMessageDialog(Component parent,String content,String title)
    {
        JOptionPane.showMessageDialog(parent, content,title,JOptionPane.ERROR_MESSAGE);
    }
     public static int ChickenConfirmDialog(Component parent,String content,String title)
    {
        int choose=JOptionPane.showConfirmDialog(parent, content,title,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        return choose;
    }
}
