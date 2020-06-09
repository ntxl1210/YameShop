/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KhachHang;

/**
 *
 * @author ntxl1
 */
public class KhachHang {
    private int id;
    private String maKH;
    private String tenKH;
    private String email;
    private int sDT;
    private String diaChi;
    private double tongTien;
    
    public KhachHang()
    {

    }
    
    public KhachHang(int id, String maKH, String tenKH, String email, int sDT, String diaChi, double  tongTien)
    {
        this.id = id;
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.email = email;
        this.email = email;
        this.sDT = sDT;
        this.sDT = sDT;
        this.diaChi = diaChi;
        this.tongTien = tongTien;
    }
    
    public int getId() {
        return id;
    }
    
    public String getMaKH() {
        return maKH;
    }
    
    
    public String getTenKH() {
        return tenKH;
    }
    
    public void setTenKH(String tenMoi) {
        tenKH = tenMoi;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String emailMoi) {
        email = emailMoi;
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
    
    public double getTongTien() {
        return tongTien;
    }
}