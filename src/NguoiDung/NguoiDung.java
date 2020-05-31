/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NguoiDung;

/**
 *
 * @author ntxl1
 */
public class NguoiDung {
    private static int id;
    private static String taiKhoan;
    private static String matKhau;
    private static String ten;
    private static String email;
    private static int maCV;
    private static int sDT;
    private static String diaChi;
    private static String gioiTinh;
    private static int luong;
    private static Boolean ghiNhoDangNhap = false;
    
    public NguoiDung()
    {
    }
    
    public NguoiDung(int id, String taiKhoan, String matKhau, String ten, String email, int maCV, int sDT, String diaChi, String gioiTinh, int luong, boolean ghiNhoDangNhap)
    {
        this.id = id;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.ten = ten;
        this.email = email;
        this.maCV = maCV;
        this.sDT = sDT;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.luong = luong;
        this.ghiNhoDangNhap = ghiNhoDangNhap;
    }
    
    public static int getId() {
        return id;
    }
    
    public static String getTaiKhoan() {
        return taiKhoan;
    }
    
    public static void setMatKhau(String matKhauMoi) {
        matKhau = matKhauMoi;
    }
    
    public static String getTen() {
        return ten;
    }
    
    public static void setTen(String tenMoi) {
        ten = tenMoi;
    }
    
    public static String getEmail() {
        return email;
    }
    
    public static void setEmail(String emailMoi) {
        email = emailMoi;
    }
    
    public static int getMaCV() {
        return maCV;
    }
    
    public static void setMaCV(int maCVMoi) {
        maCV = maCVMoi;
    }
    
    public static int getSDT() {
        return sDT;
    }
    
    public static void setSDT(int SDTMoi) {
        sDT = SDTMoi;
    }
    
    public static String getDiaChi() {
        return diaChi;
    }
    
    public static void setDiaChi(String diaChiMoi) {
        diaChi = diaChiMoi;
    }
    
    public static String getGioiTinh() {
        return gioiTinh;
    }
    
    public static void setGioiTinh(String gioiTinhMoi) {
        gioiTinh = gioiTinhMoi;
    }
    
    public static int getLuong() {
        return luong;
    }
    
    public static void setLuong(int LuongMoi) {
        luong = LuongMoi;
    }
    
    public static Boolean getghiNhoDangNhap() {
        return ghiNhoDangNhap;
    }

    public static void setghiNhoDangNhap(boolean ghiNhoDangNhapMoi) {
        ghiNhoDangNhap = ghiNhoDangNhapMoi;
    }

}
