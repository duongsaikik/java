/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataHelper;

import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Acer
 */
public class Validator {
    public static void CheckText(JTextField JT,StringBuilder sb,String Error)
    {
        if(JT.getText().equals(""))
        {
            sb.append(Error).append("\n");
            JT.setBackground(Color.red);
            JT.requestFocus();
        }
        else{
            JT.setBackground(Color.WHITE);
        }
    }
    public static void CheckText(JPasswordField JP,StringBuilder sb,String Error)
    {
         String s=String.valueOf(JP.getPassword());
        if(s.equals(""))
        {
            sb.append(Error).append("\n");
            JP.setBackground(Color.red);
            JP.requestFocus();
        }
        else{
            JP.setBackground(Color.WHITE);
        }
    }
    public static void CheckText(JRadioButton JP,StringBuilder sb,String Error)
    {
         String s=String.valueOf(JP.getSelectedIcon());
        if(s.equals(""))
        {
            sb.append(Error).append("\n");
            JP.setBackground(Color.red);
            JP.requestFocus();
        }
        else{
            JP.setBackground(Color.WHITE);
        }
    }
}
