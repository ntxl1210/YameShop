/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yameshop;

import GlobalData.GlobalData;
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

//    public static  form.frmTrangChu frmTC=new frmTrangChu();
    public static database.clsConnectDB connection = new  database.clsConnectDB ();

    public static void main(String[] args) {
       if(GlobalData.getNguoiDung() != null)
       {
            frmTrangChu frmCT = new frmTrangChu();
            frmCT.pack();
            frmCT.show();
       }
       else
       {
            frmDangNhap frmDN = new frmDangNhap();
            frmDN.pack();
            frmDN.show();
       }
       
    }
    
}
