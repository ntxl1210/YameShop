/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author HP
 */
public class Regex {
   public String Mess = "";
   public int sdtc = 1;
   public int emailc = 2;
   public int namec = 3;
    
    public boolean check(String text,int type)
    {
        
        if(type == this.sdtc)
        {
            this.Mess = "Số điện thoại phải bao gồm 11 chữ số!";    
            Pattern pattern = Pattern.compile("\\d{11}");
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        }
        if(type == this.emailc)
        {
            this.Mess = "Không đúng định dạng email!"; 
            Pattern pattern = Pattern.compile("^(.+)@(.+)$");
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        }
        if(type == this.namec)
        {
            this.Mess = "Tên không được bao gồm số, khoảng trắng cuối dòng!"; 
            Pattern pattern = Pattern.compile("^\\p{L}+(?: \\p{L}+)*$");
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        }
        return false;
        
    }
}
