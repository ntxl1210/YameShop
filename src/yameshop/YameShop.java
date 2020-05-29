/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yameshop;

/**
 *
 * @author NeedNguyen
 */
import form.frmDangNhap;
import form.frmTrangChu;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class YameShop {

    public static  form.frmTrangChu frmTC=new frmTrangChu();
    public static database.clsConnectDB connection = new  database.clsConnectDB ();

    public static void main(String[] args) {
       form.frmDangNhap frmDN= new   form.frmDangNhap();
  
       frmDN.show();
    }
    
}
