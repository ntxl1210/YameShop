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
    private int id;
    private String taiKhoan;
    private String matKhau;
    private String ten;
    private String email;
    private int maCV;
    private int maCN;
    private int sDT;
    private String diaChi;
    private String gioiTinh;
    private int luong;
    private Boolean ghiNhoDangNhap = false;
    
    public NguoiDung()
    {

    }
    
    public NguoiDung(int id, String taiKhoan, String matKhau, String ten, String email, int maCV, int maCN, int sDT, String diaChi, String gioiTinh, int luong, boolean ghiNhoDangNhap)
    {
        this.id = id;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.ten = ten;
        this.email = email;
        this.maCV = maCV;
        this.maCN = maCN;
        this.sDT = sDT;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.luong = luong;
        this.ghiNhoDangNhap = ghiNhoDangNhap;
    }
    
    public int getId() {
        return id;
    }
    
    public String getTaiKhoan() {
        return taiKhoan;
    }
    
    public void setMatKhau(String matKhauMoi) {
        matKhau = matKhauMoi;
    }
    
    public String getTen() {
        return ten;
    }
    
    public void setTen(String tenMoi) {
        ten = tenMoi;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String emailMoi) {
        email = emailMoi;
    }
    
    public int getMaCV() {
        return maCV;
    }
    
    public void setMaCV(int maCVMoi) {
        maCV = maCVMoi;
    }
    
    public int getMaCN() {
        return maCN;
    }
    
    public void setMaCN(int maCNMoi) {
        maCN = maCNMoi;
    }
    
    public int getSDT() {
        return sDT;
    }
    
    public void setSDT(int SDTMoi) {
        sDT = SDTMoi;
    }
    
    public String getDiaChi() {
        return diaChi;
    }
    
    public void setDiaChi(String diaChiMoi) {
        diaChi = diaChiMoi;
    }
    
    public String getGioiTinh() {
        return gioiTinh;
    }
    
    public void setGioiTinh(String gioiTinhMoi) {
        gioiTinh = gioiTinhMoi;
    }
    
    public int getLuong() {
        return luong;
    }
    
    public void setLuong(int LuongMoi) {
        luong = LuongMoi;
    }
    
    public Boolean getghiNhoDangNhap() {
        return ghiNhoDangNhap;
    }

    public void setghiNhoDangNhap(boolean ghiNhoDangNhapMoi) {
        ghiNhoDangNhap = ghiNhoDangNhapMoi;
    }

}
