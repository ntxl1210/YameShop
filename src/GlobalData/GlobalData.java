/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GlobalData;


import NguoiDung.NguoiDung;
import KhachHang.KhachHang;
/**
 *
 * @author ntxl1
 */
public class GlobalData {
    private static NguoiDung nguoiDung;
    private static KhachHang khachHang;
    

    public static NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public static void setNguoiDung(NguoiDung nguoiDungMoi) {
        nguoiDung = nguoiDungMoi;
    }
    
    public static void xoaNguoiDung() {
        nguoiDung = null;
    }
    
    public static KhachHang getKhachHang() {
        return khachHang;
    }

    public static void setKhachHang(KhachHang khachHangMoi) {
        khachHang = khachHangMoi;
    }
    
    public static void xoaKhachHang() {
        khachHang = null;
    }
}
