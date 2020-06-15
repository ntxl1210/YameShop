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
   
    public int slc = 1;
    public int giac = 2;
   
    public int slspbhc = 1;
    public int ggbh = 2;
   
    public int gghdc = 1;
    public int tthdc = 2;
   
    
    public boolean checkTTKH(String text,int type)
    {
        
        if(type == this.sdtc)
        {
            this.Mess = "Số điện thoại phải bao gồm 10 hoặc 11 chữ số!";    
            Pattern pattern = Pattern.compile("\\d{10,11}");
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
    
    public boolean checkTTSP(String text,int type)
    {
        
        if(type == this.slc)
        {
            this.Mess = "Số lượng phải lớn hơn 0 và nhỏ hơn 1001 và không chứa ký tự chữ!";    
            Pattern pattern = Pattern.compile("^([1-9][0-9]{0,2}|1000)$");
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        }
        if(type == this.giac)
        {
            this.Mess = "Giá phải lớn hơn 0 và không chứa ký tự chữ!"; 
            Pattern pattern = Pattern.compile("^([0-9]+([.][0-9]*)?|[.][0-9]+)$");
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        }
        return false;
        
    }
    
    public boolean checkTTSPBH(String text,int type)
    {
        if(type == this.slspbhc)
        {
            this.Mess = "Số lượng chỉ có thể là số!"; 
            Pattern pattern = Pattern.compile("^[0-9]*$");
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        }
        
        if(type == this.ggbh)
        {
            this.Mess = "Giảm giá chỉ có thể từ 0 đến 100!"; 
            Pattern pattern = Pattern.compile("^([0-9][0-9]{0,1}|100)$");
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        }
        return false;
        
    }
    
    public boolean checkTTHD(String text,int type)
    {
        if(type == this.gghdc)
        {
            this.Mess = "Giảm giá chỉ có thể từ 0 đến 100!"; 
            Pattern pattern = Pattern.compile("^(([0-9]{0,2}){1}(\\.[0-9]{0,2})|100)$");
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        }
        
        if(type == this.tthdc)
        {
            this.Mess = "Tổng tiền chỉ có thể là số!"; 
            Pattern pattern = Pattern.compile("^([0-9]*[.])?[0-9]+$");
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        }
        return false;
        
    }
    
    public boolean checkTTCTHD(String text,int type)
    {
        if(type == this.gghdc)
        {
            this.Mess = "Giảm giá chỉ có thể từ 0 đến 100!"; 
            Pattern pattern = Pattern.compile("^(([0-9]{0,2}){1}(\\.[0-9]{0,2})|100)$");
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        }
        
        if(type == this.tthdc)
        {
            this.Mess = "Tổng tiền chỉ có thể là số!"; 
            Pattern pattern = Pattern.compile("^([0-9]*[.])?[0-9]+$");
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        }
        return false;
        
    }
}
