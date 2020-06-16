/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import GlobalData.GlobalData;
import NguoiDung.NguoiDung;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import database.clsConnectDB;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import Regex.Regex;
import java.awt.event.KeyEvent;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import KhachHang.KhachHang;
import SHAHashing.SHAHashing;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author NeedNguyen
 */
public class frmTrangChu extends javax.swing.JFrame {
    clsConnectDB cls = new clsConnectDB();
    Regex regex = new Regex();
    NguoiDung nguoiDung = new NguoiDung();
    KhachHang khachHang = new KhachHang();
    List<Integer> cbxCCChucvu = new ArrayList<Integer>();
    List<Integer> cbxCCCN = new ArrayList<Integer>();
    List<Integer> listDMSPID = new ArrayList<Integer>();
    List<Integer> listNVID = new ArrayList<Integer>();
    List<Integer> listKHID = new ArrayList<Integer>();
    List<Integer> listSPID = new ArrayList<Integer>();
    List<Integer> listHDID = new ArrayList<Integer>();
    List<Integer> listPNID = new ArrayList<Integer>();
    List<Integer> listNCCID = new ArrayList<Integer>();
    File file, newFile;
    BufferedImage bi;
    int tonKho = 0;
    int soLuong = 0;
  
    /**
     * Creates new form frmTrangChu
     */
    public frmTrangChu() {
        
        initComponents();
        
        try 
        {
            
            nguoiDung = GlobalData.getNguoiDung();
        } 
        catch (NullPointerException e) 
        {
            nguoiDung = null;
        }
        try 
        {
            khachHang = GlobalData.getKhachHang();
        }
        catch (NullPointerException e) 
        {
            khachHang = null;
        }
        
        if(nguoiDung == null)
        {
            form.frmDangNhap frmDN = new   form.frmDangNhap();
            this.hide();
            frmDN.show();
        }
        else
        {
            if(nguoiDung.getMaCV() != 1)
            {
                if(nguoiDung.getMaCV() == 2)
                {
                    for(int i = 1; i < jTabbedPane1.getTabCount()-1; i++)
                        jTabbedPane1.setEnabledAt(i, false);
                }
                else if(nguoiDung.getMaCV() == 3)
                {
                    for(int i = 1; i < jTabbedPane1.getTabCount(); i++)
                        if(i != jTabbedPane1.getTabCount() - 2 & i != jTabbedPane1.getTabCount() - 4)
                            jTabbedPane1.setEnabledAt(i, false);
                }
                else if(nguoiDung.getMaCV() == 4)
                {
                    for(int i = 1; i < jTabbedPane1.getTabCount(); i++)
                        if(i != jTabbedPane1.getTabCount() - 5)
                            jTabbedPane1.setEnabledAt(i, false);
                }
            }
            
            readOnly();
            
            txtNgayBan.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            txtTenNV.setText(nguoiDung.getTen());
            cbGioiTinh.removeAllItems();
            cbGioiTinh.addItem("Nam");
            cbGioiTinh.addItem("Nữ");
            
            String header[] = {"Mã sản phẩm", "Tên sản phẩm", "Kích thước","Số lượng","Giá bán", "Thành tiền"};
            DefaultTableModel tbModel = new DefaultTableModel(header,0);
            tblHoaDonBan.setModel(tbModel);
            
            
            loadDM();
            loadSP();
            loadSPBH();
            LoadCNBC();
            LoadChiNhanh();
            loadCCCV();
            LoadUserTable();
            LoadNhaCC();
            loadKhachHang();
            LoadHoaDon();
            LoadPhieuNhap();
        }
    }
    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
    
    private void LoadNhaCC()
    {
         String  sql = "SELECT * FROM nha_cung_cap";
        
        try {
            String header[] = {"Id", "Mã nhà cung cấp", "Tên nhà cung cấp","Địa chỉ","Số điện thoại","Email","Tổng tiền"};
            DefaultTableModel tblModel = new DefaultTableModel(header,0);
            DefaultComboBoxModel cbModel = new DefaultComboBoxModel();
            Vector data = null;
            tblModel.setRowCount(0);
            ResultSet rs = cls.excuteQueryGetTable(sql);
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getInt("id"));
                data.add(rs.getString("ma_ncc"));
                data.add(rs.getString("ten_ncc"));
                data.add(rs.getString("dia_chi"));
                data.add(rs.getString("sdt"));
                data.add(rs.getString("email"));
                data.add(rs.getDouble("tong_tien_nhap"));
                
                // Thêm một dòng vào table model
                tblModel.addRow(data);
                cbModel.addElement(rs.getString("ten_ncc"));
                listNCCID.add(rs.getInt("id"));
                }
            tblNhaCC.setModel(tblModel);
            cbNCCNH.setModel(cbModel);
        } catch (SQLException ex) {
            System.err.println("Cannot connect database, " + ex);
        }
    }
    
    private void LoadHoaDon()
    {
         String  sql = "SELECT h.id, h.ma_hd, c.ten_cn, n.ten, h.ten_kh, h.ngay_tao, h.giam_gia, h.tong_tien "
                + "FROM hoa_don h INNER JOIN chi_nhanh c ON h.ma_cn=c.id INNER JOIN nguoi_dung n ON h.ma_nv=n.id";
        
        try {
            String header[] = {"Id", "Mã hóa đơn", "Chi nhánh", "Nhân viên", "Khách hàng", "Ngày tạo", "Giảm giá", "Tổng tiền"};
            DefaultTableModel tblModel = new DefaultTableModel(header,0);
            DefaultComboBoxModel cbModel = new DefaultComboBoxModel();
            Vector data = null;
            tblModel.setRowCount(0);
            ResultSet rs = cls.excuteQueryGetTable(sql);
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getInt("id"));
                data.add(rs.getString("ma_hd"));
                data.add(rs.getString("ten_cn"));
                data.add(rs.getString("ten"));
                data.add(rs.getString("ten_kh"));
                data.add(rs.getDate("ngay_tao"));
                data.add(rs.getFloat("giam_gia"));
                data.add(rs.getDouble("tong_tien"));
                
                // Thêm một dòng vào table model
                tblModel.addRow(data);
                cbModel.addElement(rs.getString("ma_hd"));
                listHDID.add(rs.getInt("id"));
            }
            tblHoaDon.setModel(tblModel);
            cbMaHoaDon.setModel(cbModel);
        } catch (SQLException ex) {
            System.err.println("Cannot connect database, " + ex);
        }
    }
    
    public void loadCTHD(int ma_hd)
    {
        String sql = "SELECT h.ma_hd, s.ten_sp, c.so_luong, c.don_gia, c.tong_tien "
                + "FROM ct_hoa_don c INNER JOIN hoa_don h ON c.ma_hd=h.id INNER JOIN san_pham s ON c.ma_sp=s.id "
                + "WHERE c.ma_hd="+ma_hd;
        try {
            String header[] = {"Mã hóa đơn", "Sản phẩm", "Số lượng", "Đơn bán", "Thành tiền"};
            DefaultTableModel tblModel = new DefaultTableModel(header,0);
            Vector data = null;
            tblModel.setRowCount(0);
            ResultSet rs = cls.excuteQueryGetTable(sql);
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getString("ma_hd"));
                data.add(rs.getString("ten_sp"));
                data.add(rs.getInt("so_luong"));
                data.add(rs.getFloat("don_gia"));
                data.add(rs.getDouble("tong_tien"));
                // Thêm một dòng vào table model
                tblModel.addRow(data);
                }
            tblCTHD.setModel(tblModel);
        } 
        catch (SQLException ex) {
            System.err.println("Cannot connect database, " + ex);
        }
    }
    
    public void loadCTHD()
    {
        String sql = "SELECT h.ma_hd, s.ten_sp, c.so_luong, c.don_gia, c.tong_tien "
                + "FROM ct_hoa_don c INNER JOIN hoa_don h ON c.ma_hd=h.id INNER JOIN san_pham s ON c.ma_sp=s.id ";
        try {
            String header[] = {"Mã hóa đơn", "Sản phẩm", "Số lượng", "Đơn bán", "Thành tiền"};
            DefaultTableModel tblModel = new DefaultTableModel(header,0);
            Vector data = null;
            tblModel.setRowCount(0);
            ResultSet rs = cls.excuteQueryGetTable(sql);
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getString("ma_hd"));
                data.add(rs.getString("ten_sp"));
                data.add(rs.getInt("so_luong"));
                data.add(rs.getFloat("don_gia"));
                data.add(rs.getDouble("tong_tien"));
                // Thêm một dòng vào table model
                tblModel.addRow(data);
                }
            tblCTHD.setModel(tblModel);
        } 
        catch (SQLException ex) {
            System.err.println("Cannot connect database, " + ex);
        }
    }
    
    private void LoadPhieuNhap()
    {
        String  sql = "SELECT p.id, p.ma_pn, c.ten_cn, n.ten, ncc.ten_ncc, p.ngay_nhap, p.tong_tien "
                + "FROM phieu_nhap p INNER JOIN chi_nhanh c ON p.ma_cn=c.id INNER JOIN nguoi_dung n ON p.ma_nv=n.id "
                + "INNER JOIN nha_cung_cap ncc ON p.ma_ncc=ncc.id";
        
        try {
            String header[] = {"Id", "Mã phiếu nhập", "Chi nhánh", "Nhân viên", "Nhà cung cấp", "Ngày nhập", "Tổng tiền"};
            DefaultTableModel tblModel = new DefaultTableModel(header,0);
            DefaultComboBoxModel cbModel = new DefaultComboBoxModel();
            Vector data = null;
            tblModel.setRowCount(0);
            ResultSet rs = cls.excuteQueryGetTable(sql);
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getInt("id"));
                data.add(rs.getString("ma_pn"));
                data.add(rs.getString("ten_cn"));
                data.add(rs.getString("ten"));
                data.add(rs.getString("ten_ncc"));
                data.add(rs.getDate("ngay_nhap"));
                data.add(rs.getDouble("tong_tien"));
                
                // Thêm một dòng vào table model
                tblModel.addRow(data);
                cbModel.addElement(rs.getString("ma_pn"));
                listPNID.add(rs.getInt("id"));
            }
            tblHoaDonNH.setModel(tblModel);
            cbMAPN.setModel(cbModel);
        } catch (SQLException ex) {
            System.err.println("Cannot connect database, " + ex);
        }
    }
    
    public void loadCTPN(int ma_pn)
    {
        String sql = "SELECT p.ma_pn, s.ten_sp, c.so_luong_nhap, c.don_gia, c.tong_tien "
                + "FROM ct_phieu_nhap c INNER JOIN phieu_nhap p ON c.ma_pn=p.id INNER JOIN san_pham s ON c.ma_sp=s.id "
                + "WHERE c.ma_pn="+ma_pn;
        try {
            String header[] = {"Mã phiếu nhập", "Sản phẩm", "Số lượng", "Đơn bán", "Thành tiền"};
            DefaultTableModel tblModel = new DefaultTableModel(header,0);
            Vector data = null;
            tblModel.setRowCount(0);
            ResultSet rs = cls.excuteQueryGetTable(sql);
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getString("ma_pn"));
                data.add(rs.getString("ten_sp"));
                data.add(rs.getInt("so_luong_nhap"));
                data.add(rs.getFloat("don_gia"));
                data.add(rs.getDouble("tong_tien"));
                // Thêm một dòng vào table model
                tblModel.addRow(data);
                }
            tblCTHDNH.setModel(tblModel);
        } 
        catch (SQLException ex) {
            System.err.println("Cannot connect database, " + ex);
        }
    }
    
    public void loadCTPN()
    {
        String sql = "SELECT p.ma_pn, s.ten_sp, c.so_luong_nhap, c.don_gia, c.tong_tien "
                + "FROM ct_phieu_nhap c INNER JOIN phieu_nhap p ON c.ma_pn=p.id INNER JOIN san_pham s ON c.ma_sp=s.id ";
        try {
            String header[] = {"Mã phiếu nhập", "Sản phẩm", "Số lượng", "Đơn bán", "Thành tiền"};
            DefaultTableModel tblModel = new DefaultTableModel(header,0);
            Vector data = null;
            tblModel.setRowCount(0);
            ResultSet rs = cls.excuteQueryGetTable(sql);
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getString("ma_pn"));
                data.add(rs.getString("ten_sp"));
                data.add(rs.getInt("so_luong_nhap"));
                data.add(rs.getFloat("don_gia"));
                data.add(rs.getDouble("tong_tien"));
                // Thêm một dòng vào table model
                tblModel.addRow(data);
                }
            tblCTHDNH.setModel(tblModel);
        } 
        catch (SQLException ex) {
            System.err.println("Cannot connect database, " + ex);
        }
    }
    
    public void bindingValuesHD(int row)
    {
        txtMaHoaDon.setText(tblHoaDon.getModel().getValueAt(row, 1).toString());
        cbChiNhanh.setSelectedItem(tblHoaDon.getModel().getValueAt(row, 2).toString());
        cbNhanVien.setSelectedItem(tblHoaDon.getModel().getValueAt(row, 3).toString());
        cbKhachHang.setSelectedItem(tblHoaDon.getModel().getValueAt(row, 4).toString());
        txtNgayLap.setText(tblHoaDon.getModel().getValueAt(row, 5).toString());
        txtGiamGiaHD.setText(tblHoaDon.getModel().getValueAt(row, 6).toString());
        txtTongTienHD.setText(tblHoaDon.getModel().getValueAt(row, 7).toString());
    }
    
    public void bindingValuesCTHD(int row)
    {
        cbMaHoaDon.setSelectedItem(tblCTHD.getModel().getValueAt(row, 0).toString());
        cbSanPham.setSelectedItem(tblCTHD.getModel().getValueAt(row, 1).toString());
        txtSoLuongCTHD.setText(tblCTHD.getModel().getValueAt(row, 2).toString());
        txtDonGiaCTHD.setText(tblCTHD.getModel().getValueAt(row, 3).toString());
    }
    
    public void bindingValuesPN(int row)
    {
        txtMaPhieuNHap.setText(tblHoaDonNH.getModel().getValueAt(row, 1).toString());
        cbChiNhanhNH.setSelectedItem(tblHoaDonNH.getModel().getValueAt(row, 2).toString());
        cbNhanVienNH.setSelectedItem(tblHoaDonNH.getModel().getValueAt(row, 3).toString());
        cbNCCNH.setSelectedItem(tblHoaDonNH.getModel().getValueAt(row, 4).toString());
        txtNgayLap.setText(tblHoaDonNH.getModel().getValueAt(row, 5).toString());
        txtTongTienHD2.setText(tblHoaDonNH.getModel().getValueAt(row, 6).toString());
    }
    
    public void bindingValuesCTPN(int row)
    {
        cbMAPN.setSelectedItem(tblCTHDNH.getModel().getValueAt(row, 0).toString());
        cbSanPham.setSelectedItem(tblCTHDNH.getModel().getValueAt(row, 1).toString());
        txtSoLuongCTHD2.setText(tblCTHDNH.getModel().getValueAt(row, 2).toString());
        txtDonGiaCTHD2.setText(tblCTHDNH.getModel().getValueAt(row, 3).toString());
    }
    
    private void loadKhachHang()
    {
         String  sql = "SELECT * FROM khach_hang";
        
        try {
            String header[] = {"Id", "Mã khách hàng", "Tên khách hàng","Địa chỉ","Số điện thoại","Email","Tổng tiền mua"};
            DefaultTableModel tblModel = new DefaultTableModel(header,0);
            DefaultComboBoxModel cbModel = new DefaultComboBoxModel();
            DefaultComboBoxModel cbModel1 = new DefaultComboBoxModel();
            Vector data = null;
            tblModel.setRowCount(0);
            ResultSet rs = cls.excuteQueryGetTable(sql);
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getInt("id"));
                data.add(rs.getString("ma_kh"));
                data.add(rs.getString("ten_kh"));
                data.add(rs.getString("dia_chi"));
                data.add(rs.getString("sdt"));
                data.add(rs.getString("email"));
                data.add(rs.getDouble("tong_tien"));
                
                // Thêm một dòng vào table model
                tblModel.addRow(data);
                cbModel.addElement(rs.getString("ten_kh"));
                cbModel1.addElement(rs.getString("ten_kh")+" | " + rs.getString("sdt"));
                listKHID.add(rs.getInt("id"));
                }
            tblKhachHang.setModel(tblModel);
            cbKhachHang.setModel(cbModel);
            cbKhachHangBH.setModel(cbModel1);
        } catch (SQLException ex) {
            System.err.println("Cannot connect database, " + ex);
        }
    }
    private void LoadCNBC()
    {
         String  sql = "SELECT * FROM chi_nhanh";
        
        try {
            String header[] = {"Id", "Mã danh mục", "Tên danh mục"};
            ResultSet rs = cls.excuteQueryGetTable(sql);
            Vector data = new Vector();
            while (rs.next()) {
                cbxCCCN.add(rs.getInt("id"));
                data.add(rs.getString("ten_cn"));
            }
           DefaultComboBoxModel cmbModel = new DefaultComboBoxModel(data);
            cbxChiNanhBC.setModel(cmbModel);
            cbxChiNanh.setModel(cmbModel);
            cbChiNhanhNH.setModel(cmbModel);
        } catch (SQLException ex) {
            System.err.println("Cannot connect database, " + ex);
        }
    }
    private void loadCCCV()
    {
        String  sql = "SELECT * FROM chuc_vu";
        
        try {
            
            DefaultComboBoxModel cbModel = new DefaultComboBoxModel();
            Vector data = null;
            
            ResultSet rs = cls.excuteQueryGetTable(sql);
            while (rs.next()) {
                cbxCCChucvu.add(rs.getInt("id"));
                cbModel.addElement(rs.getString("ten_cv"));
            }
            
            cbxChucVu.setModel(cbModel);
    
        } catch (SQLException ex) {
            System.err.println("Cannot connect database, " + ex);
        }
    }
    private void LoadUserTable()
    {
        String  sql = "SELECT nd.*,cv.ten_cv FROM nguoi_dung as nd,chuc_vu as cv where cv.id = nd.ma_cv";
        
        try {
            String header[] = {"Id", "Tài khoản", "Tên","Email","Chức vụ","Số Điện thoại","Dịa chỉ","giới tính","Lương"};
            DefaultTableModel tblModel = new DefaultTableModel(header,0);
            DefaultComboBoxModel cbModel = new DefaultComboBoxModel();
            Vector data = null;
            tblModel.setRowCount(0);
            ResultSet rs = cls.excuteQueryGetTable(sql);
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getInt("id"));
                data.add(rs.getString("tai_khoan"));
                data.add(rs.getString("ten"));
                data.add(rs.getString("email"));
                data.add(rs.getString("ten_cv"));
                data.add(rs.getString("sdt"));
                data.add(rs.getString("dia_chi"));
                data.add(rs.getString("gioi_tinh"));
                data.add(rs.getDouble("luong"));
                // Thêm một dòng vào table model
                tblModel.addRow(data);
                cbModel.addElement(rs.getString("ten"));
                listNVID.add(rs.getInt("id"));
                }
            tblNguoiDung.setModel(tblModel);
            cbNhanVien.setModel(cbModel);
            cbNhanVienNH.setModel(cbModel);
        }
        catch (SQLException ex) {
            System.err.println("Cannot connect database, " + ex);
        }    
    }
    
    private void readOnly()
    {
        tblDanhMuc.setDefaultEditor(Object.class, null);
        tblSanPham.setDefaultEditor(Object.class, null);
        tblSanPhamBH.setDefaultEditor(Object.class, null);
        tblBaoCaoBH.setDefaultEditor(Object.class, null);
        tblCTHD.setDefaultEditor(Object.class, null);
        tblCTHDNH.setDefaultEditor(Object.class, null);
        tblChiNhanh.setDefaultEditor(Object.class, null);
        tblHoaDon.setDefaultEditor(Object.class, null);
        tblHoaDonNH.setDefaultEditor(Object.class, null);
        tblKhachHang.setDefaultEditor(Object.class, null);
        tblNguoiDung.setDefaultEditor(Object.class, null);
        tblNhaCC.setDefaultEditor(Object.class, null);
        tblHoaDonBan.setDefaultEditor(Object.class, null);
        
        txtTenNV.setEditable(false);
        txtNgayBan.setEditable(false);
        txtMaSP.setEditable(false);
        txtMaSP3.setEditable(false);
        txtMaSP4.setEditable(false);
        txtDonGia.setEditable(false);
        txtThanhTien.setEditable(false);
        txtTongTien.setEditable(false);
        txtDonGiaCTHD.setEditable(false);
        txtTongTienHD.setEditable(false);
        txtTongTienHD2.setEditable(false);
    }
    
    private void loadDM()
    {
        String  sql = "SELECT * FROM danh_muc_sp";
        
        try
        {
            String header[] = {"Id", "Mã danh mục", "Tên danh mục"};
            DefaultTableModel tblModel = new DefaultTableModel(header,0);
            DefaultComboBoxModel cbModel = new DefaultComboBoxModel();
            Vector data = null;
            tblModel.setRowCount(0);
            ResultSet rs = cls.excuteQueryGetTable(sql);
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getInt("id"));
                data.add(rs.getString("ma_dm"));
                data.add(rs.getString("ten_dm"));
                // Thêm một dòng vào table model
                tblModel.addRow(data);
                cbModel.addElement(rs.getString("ten_dm"));
                listDMSPID.add(rs.getInt("id"));
                }
            tblDanhMuc.setModel(tblModel);
            cbxDanhMuc.setModel(cbModel);
        }
        catch(SQLException ex)
        {
            System.err.println("Cannot connect database, " + ex);
        }
    
        
    }
    private void LoadChiNhanh()
    {
        String  sql = "SELECT * FROM chi_nhanh";
        
        try {
            String header[] = {"id","Mã chi nhánh", "Tên chi nhánh","Số điện thoại","Địa chỉ"};
            DefaultTableModel tblModel = new DefaultTableModel(header,0);
            DefaultComboBoxModel cbModel = new DefaultComboBoxModel();
            Vector data = null;
            tblModel.setRowCount(0);
            ResultSet rs = cls.excuteQueryGetTable(sql);
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getInt("id"));
                data.add(rs.getString("ma_cn"));
                data.add(rs.getString("ten_cn"));
                data.add(rs.getString("sdt"));
                data.add(rs.getString("dia_chi"));
                // Thêm một dòng vào table model
                tblModel.addRow(data);
                cbModel.addElement(rs.getString("ten_cn"));
                }
            tblChiNhanh.setModel(tblModel);
            cbChiNhanh.setModel(cbModel);
    
        } catch (SQLException ex) {
            System.err.println("Cannot connect database, " + ex);
        }
    }
    
    private void loadSP()
    {
        String  sql = "SELECT * FROM san_pham INNER JOIN danh_muc_sp ON san_pham.ma_dm = danh_muc_sp.id";
        
        try {
            String header[] = {"Id", "Mã sản phẩm", "Tên sản phẩm", "Kích thước", "Số lượng", "Tồn kho", "Mô tả", "Hình ảnh", "Giá nhập", "Giá bán", "Ngày nhập", "Loại sản phẩm"};
            DefaultTableModel tblModel = new DefaultTableModel(header,0);
            DefaultComboBoxModel cbModel = new DefaultComboBoxModel();
            Vector data = null;
            tblModel.setRowCount(0);
            ResultSet rs = cls.excuteQueryGetTable(sql);
            
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getInt("id"));
                data.add(rs.getString("ma_sp"));
                data.add(rs.getString("ten_sp"));
                data.add(rs.getString("kich_thuoc"));
                data.add(rs.getInt("so_luong"));
                data.add(rs.getInt("ton_kho"));
                data.add(rs.getString("mo_ta"));
                data.add(rs.getString("hinh_anh"));
                data.add(rs.getFloat("gia_nhap"));
                data.add(rs.getFloat("gia_ban"));
                data.add(rs.getDate("ngay_nhap"));
                data.add(rs.getString("ten_dm"));
                // Thêm một dòng vào table model
                tblModel.addRow(data);
                cbModel.addElement(rs.getString("ten_sp"));
                listSPID.add(rs.getInt("id"));
                }
            cbSanPham.setModel(cbModel);
            cbSanPhamNH.setModel(cbModel);
            tblSanPham.setModel(tblModel);
    
        } catch (SQLException ex) {
            System.err.println("Lỗi câu lệnh database, lỗi: " + ex);
        }
    }
    
    private void loadSPBH()
    {
        String  sql = "SELECT gia_ban, ma_sp, ton_kho, ten_sp FROM san_pham where ton_kho > 0";
        
        try {
            String header[] = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng","Giá bán"};
            DefaultTableModel tblModel = new DefaultTableModel(header,0);
            Vector data = null;
            tblModel.setRowCount(0);
            ResultSet rs = cls.excuteQueryGetTable(sql);
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getString("ma_sp"));
                data.add(rs.getString("ten_sp"));
                data.add(rs.getInt("ton_kho"));
                data.add(rs.getString("gia_ban"));
                // Thêm một dòng vào table model
                tblModel.addRow(data);
                }
            tblSanPhamBH.setModel(tblModel);
    
        } catch (SQLException ex) {
            System.err.println("Cannot connect database, " + ex);
        }
    }
    
    private void dangXuat()
    {
        GlobalData.xoaNguoiDung();
        
        form.frmTrangChu frmTC = new   form.frmTrangChu();
        this.hide();
    }
    
    private String getToDay()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date ngayNhap = new Date();
        return sdf.format(ngayNhap);
    }
    
    private int getDMID(int index)
    {
        return listDMSPID.get(index);
    }
    
    private void findSP()
    {
        String  sql = "SELECT * FROM san_pham INNER JOIN danh_muc_sp ON san_pham.ma_dm = danh_muc_sp.id";
        
        if (txtTimKiemSP.getText().length() > 0) {
            sql = sql + " WHERE ten_sp like N'%" + txtTimKiemSP.getText() + "%' OR ma_sp like N'%" + txtTimKiemSP.getText() + "%' ";
        }
        try {
            String header[] = {"Id", "Mã sản phẩm", "Tên sản phẩm", "Kích thước", "Số lượng", "Tồn kho", "Mô tả", "Hình ảnh", "Giá nhập", "Giá bán", "Ngày nhập", "Loại sản phẩm"};
            DefaultTableModel tblModel = new DefaultTableModel(header,0);
            Vector data = null;
            tblModel.setRowCount(0);
            ResultSet rs = cls.excuteQueryGetTable(sql);
            
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getInt("id"));
                data.add(rs.getString("ma_sp"));
                data.add(rs.getString("ten_sp"));
                data.add(rs.getString("kich_thuoc"));
                data.add(rs.getInt("so_luong"));
                data.add(rs.getInt("ton_kho"));
                data.add(rs.getString("mo_ta"));
                data.add(rs.getString("hinh_anh"));
                data.add(rs.getFloat("gia_nhap"));
                data.add(rs.getFloat("gia_ban"));
                data.add(rs.getDate("ngay_nhap"));
                data.add(rs.getString("ten_dm"));
                // Thêm một dòng vào table model
                tblModel.addRow(data);
                }
            tblSanPham.setModel(tblModel);
    
        } catch (SQLException ex) {
            System.err.println("Lỗi câu lệnh database, lỗi: " + ex);
        }
    }
    
    private void findSPBH()
    {
        String  sql = "SELECT gia_ban, ma_sp, ton_kho, ten_sp FROM san_pham";
        if (txtTimSP.getText().length() > 0) {
            sql = sql + " where ten_sp like N'%" + txtTimSP.getText() + "%' OR ma_sp like N'%" + txtTimSP.getText() + "%'  and ton_kho > 0";
        }
        try {
            String header[] = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng","Giá bán"};
            DefaultTableModel tblModel = new DefaultTableModel(header,0);
            Vector data = null;
            tblModel.setRowCount(0);
            ResultSet rs = cls.excuteQueryGetTable(sql);
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getString("ma_sp"));
                data.add(rs.getString("ten_sp"));
                data.add(rs.getInt("ton_kho"));
                data.add(rs.getString("gia_ban"));
                // Thêm một dòng vào table model
                tblModel.addRow(data);
                }
            tblSanPhamBH.setModel(tblModel);
    
        } catch (SQLException ex) {
            System.err.println("Cannot connect database, " + ex);
        }
    }
    
    public int existsInTable(JTable table, String ma_sp) 
    {

        int rowCount = table.getRowCount();
        
        if(rowCount == 0)
            return -1;

        for (int i = 0; i < rowCount; i++) {
            if(table.getValueAt(i, 0).equals(ma_sp))
                return i;
        }
        return -1;
    }
    
    public boolean existsInDatabaseTable(String table, String field, String value, int type) 
    {
        String sql = "";
        if(type == 1)
            sql = "select * from "+table+" where "+field+"=N'"+value+"'";
        else
            sql = "select * from "+table+" where "+field+"='"+value+"'";
        try
        {
            ResultSet rs = cls.excuteQueryGetTable(sql);

            if(rs.next())
                return true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean existsInDatabaseTable2(String table, String field1, String field2, String value1, String value2, int type1, int type2) 
    {
        String sql = "select * from "+table+"";
        if(type1 == 1)
            sql += " where "+field1+"=N'"+value1+"'";
        else
            sql += " where "+field1+"='"+value1+"'";
        if(type2 == 1)
            sql += " and "+field2+"='N"+value2+"'";
        else
            sql += " and "+field2+"='"+value2+"'";
        try
        {
            ResultSet rs = cls.excuteQueryGetTable(sql);

            if(rs.next())
                return true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    
    public double calculatePrice(int soLuong, float donGia)
    {
        return (double)soLuong * donGia;
    }
    
    public void calculateTotalPrice(int countrow)
    {
        double totalPrice = 0;
        for(int i = 0; i < countrow; i++)
        {
            double price = (double)tblHoaDonBan.getValueAt(i, 5);
            totalPrice += price;
        }
        txtTongTien.setText(String.valueOf(totalPrice));
    }
    
     public void updateTotalPriceHD(int ma_hd)
    {
        String sql = "select sum(tong_tien) as tong_tien from ct_hoa_don where ma_hd="+ma_hd+"";
        Float tongTien = 0.f;
        try
        {
            ResultSet rs = cls.excuteQueryGetTable(sql);
            if(rs.next())
                tongTien = rs.getFloat("tong_tien");
            sql = "update hoa_don set tong_tien='"+tongTien+"' where id="+ma_hd+"";
            cls.excuteQueryUpdateDB(sql);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
     
     public void updateTotalPriceKH(int ma_kh, double tong_tien)
    {
        double tongTien = khachHang.getTongTien() + tong_tien;
        
        String sql = "update khach_hang set tong_tien='"+tongTien+"' where id="+ma_kh+"";
        cls.excuteQueryUpdateDB(sql);
    }
     
    public void updateTotalPricePN(int ma_pn)
    {
        String sql = "select sum(tong_tien) as tong_tien from ct_phieu_nhap where ma_pn="+ma_pn+"";
        Float tongTien = 0.f;
        try
        {
            ResultSet rs = cls.excuteQueryGetTable(sql);
            if(rs.next())
                tongTien = rs.getFloat("tong_tien");
            
            sql = "update phieu_nhap set tong_tien='"+tongTien+"' where id="+ma_pn+"";
            cls.excuteQueryUpdateDB(sql);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void updateSP(int ma_sp, int ton_kho, int so_luong, float gia_nhap)
    {
        String sql = "update san_pham set ton_kho="+ton_kho+", so_luong="+so_luong+", gia_nhap="+gia_nhap+" where id="+ma_sp;
        cls.excuteQueryUpdateDB(sql);
    }
    
    public void updateSP1(int ma_sp, int ton_kho, int so_luong)
    {
        String sql = "update san_pham set ton_kho="+ton_kho+", so_luong="+so_luong+" where id="+ma_sp;
        cls.excuteQueryUpdateDB(sql);
    }
     
    public void updateRemainingQuantity(int ma_sp, int ton_kho)
    {
        String sql = "update san_pham set ton_kho="+ton_kho+" where id="+ma_sp;
        cls.excuteQueryUpdateDB(sql);
    }
    
    
    public void bindingValuesBH(int row)
    {
        txtMaSP.setText(tblHoaDonBan.getModel().getValueAt(row, 0).toString());
        txtMaSP3.setText(tblHoaDonBan.getModel().getValueAt(row, 1).toString());
        txtMaSP2.setText(tblHoaDonBan.getModel().getValueAt(row, 3).toString());
        txtMaSP4.setText(tblHoaDonBan.getModel().getValueAt(row, 2).toString());
        txtDonGia.setText(tblHoaDonBan.getModel().getValueAt(row, 4).toString());
        txtThanhTien.setText(tblHoaDonBan.getModel().getValueAt(row, 5).toString());
    }
    
    public void setNullBH()
    {
        txtMaSP.setText("");
        txtMaSP3.setText("");
        txtMaSP2.setText("");
        txtMaSP4.setText("");
        txtDonGia.setText("");
        txtThanhTien.setText("");
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jTabbedPaneTrangChu = new javax.swing.JTabbedPane();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jTabbedPaneQLNguoiDung = new javax.swing.JTabbedPane();
        jPanelNguoiDung = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtTaiKhoan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        txtSoDienThoai = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbxChiNanh = new javax.swing.JComboBox<>();
        cbxChucVu = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        txtEmailND = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        cbGioiTinh = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNguoiDung = new javax.swing.JTable();
        btnThemNguoiDung = new javax.swing.JButton();
        btnSuaNguoiDung = new javax.swing.JButton();
        btnXoaNguoiDung = new javax.swing.JButton();
        btnXuatNguoiDung = new javax.swing.JButton();
        jPanelChiNhanh = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaChiNhanh = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTenChiNhanh = new javax.swing.JTextField();
        txtDiaChiChiNhanh = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSdtChiNhanh = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblChiNhanh = new javax.swing.JTable();
        btnSuaChiNhanh = new javax.swing.JButton();
        btnXoaChiNhanh = new javax.swing.JButton();
        btnXuatChiNhanh = new javax.swing.JButton();
        btnThemChiNhanh3 = new javax.swing.JButton();
        jTabbedPaneQLSanPham = new javax.swing.JTabbedPane();
        jPanelDMSanPham = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDanhMuc = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtMaDanhMuc = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtTenDanhMuc = new javax.swing.JTextField();
        btnThemDanhMuc = new javax.swing.JButton();
        btnSuaDanhMuc = new javax.swing.JButton();
        btnXoaDanhMuc = new javax.swing.JButton();
        btnXuatDanhMuc = new javax.swing.JButton();
        jPanelDSSanPham = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        txtGiaBan = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtKichThuoc = new javax.swing.JTextField();
        btnDoiHinh = new javax.swing.JButton();
        btnThemSanPham = new javax.swing.JButton();
        btnXoaSanPham = new javax.swing.JButton();
        btnSuaSanPham = new javax.swing.JButton();
        txtTenSanPham = new javax.swing.JTextField();
        cbxDanhMuc = new javax.swing.JComboBox<>();
        jLabel47 = new javax.swing.JLabel();
        textAreaMoTa = new java.awt.TextArea();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        txtTonKho = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        txtNgayNhap = new javax.swing.JTextField();
        btnXuatFile = new javax.swing.JButton();
        lbHinhAnh = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        btnTimSanPham = new javax.swing.JButton();
        txtTimKiemSP = new javax.swing.JTextField();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel17 = new javax.swing.JPanel();
        jPanelBanHang2 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        btnSuaCTHDNH = new javax.swing.JButton();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        txtSoLuongCTHD2 = new javax.swing.JTextField();
        jLabel92 = new javax.swing.JLabel();
        txtDonGiaCTHD2 = new javax.swing.JTextField();
        btnThemCTHDNH = new javax.swing.JButton();
        btnXuatCTHDNH = new javax.swing.JButton();
        btnXoaCTHDNH = new javax.swing.JButton();
        cbMAPN = new javax.swing.JComboBox<>();
        cbSanPhamNH = new javax.swing.JComboBox<>();
        jPanel19 = new javax.swing.JPanel();
        btnSuaHDNH = new javax.swing.JButton();
        jLabel94 = new javax.swing.JLabel();
        txtMaPhieuNHap = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        txtNgayLapNH = new javax.swing.JTextField();
        jLabel101 = new javax.swing.JLabel();
        txtTongTienHD2 = new javax.swing.JTextField();
        btnXoaHDNH = new javax.swing.JButton();
        btnXuatHDNH = new javax.swing.JButton();
        btnThemHDNH = new javax.swing.JButton();
        cbNhanVienNH = new javax.swing.JComboBox<>();
        cbChiNhanhNH = new javax.swing.JComboBox<>();
        cbNCCNH = new javax.swing.JComboBox<>();
        jScrollPane14 = new javax.swing.JScrollPane();
        tblHoaDonNH = new javax.swing.JTable();
        jScrollPane15 = new javax.swing.JScrollPane();
        tblCTHDNH = new javax.swing.JTable();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanelBanHang = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        btnSuaCTHD = new javax.swing.JButton();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        txtSoLuongCTHD = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        txtDonGiaCTHD = new javax.swing.JTextField();
        btnThemCTHD = new javax.swing.JButton();
        btnXuatCTHD = new javax.swing.JButton();
        btnXoaCTHD = new javax.swing.JButton();
        cbMaHoaDon = new javax.swing.JComboBox<>();
        cbSanPham = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        btnSuaHD = new javax.swing.JButton();
        jLabel51 = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        txtGiamGiaHD = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        txtNgayLap = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        txtTongTienHD = new javax.swing.JTextField();
        btnXoaHD = new javax.swing.JButton();
        btnXuatHD = new javax.swing.JButton();
        btnThemHD = new javax.swing.JButton();
        cbNhanVien = new javax.swing.JComboBox<>();
        cbKhachHang = new javax.swing.JComboBox<>();
        cbChiNhanh = new javax.swing.JComboBox<>();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblCTHD = new javax.swing.JTable();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanelNhaCungCap = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblNhaCC = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtNhaCC = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtTenNhaCC = new javax.swing.JTextField();
        txtDiaChiNCC = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtSdtNCC = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtTongTienNhap = new javax.swing.JTextField();
        btnThemNCC = new javax.swing.JButton();
        btnSuaNCC = new javax.swing.JButton();
        btnXoaNCC = new javax.swing.JButton();
        btnXuatNCC = new javax.swing.JButton();
        jPanelKhachHang = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        txtDiaChiKH = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtSdtKH = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtEmailKH = new javax.swing.JTextField();
        txtTongTienMua = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        btnThemKH = new javax.swing.JButton();
        btnSuaKh = new javax.swing.JButton();
        btnXoaKH = new javax.swing.JButton();
        btnXuatKH = new javax.swing.JButton();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jPanelBCBanHang = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        txtTuNgayBCBH = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        txtDenNgayBCBH = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        btnLocBaoCaoBH = new javax.swing.JButton();
        cbxChiNanhBC = new javax.swing.JComboBox<>();
        btnInBaoCaoBH = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblBaoCaoBH = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jPanelBCXuatNhap = new javax.swing.JPanel();
        jPanelBCBanHang1 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        txtTuNgayBCNH = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        txtDenNgayBCNH = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        btnLocBaoCaoNH = new javax.swing.JButton();
        cbxChiNanhBC1 = new javax.swing.JComboBox<>();
        btnInBaoCaoNH = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblBaoCaoNH = new javax.swing.JTable();
        jPanel23 = new javax.swing.JPanel();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        btnLuuHD = new javax.swing.JButton();
        btnThemKhachHang = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        txtNgayBan = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtMaSP2 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        txtMaSP3 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtMaSP4 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        txtThanhTien = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        txtGiamGia = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        btnHuy = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblHoaDonBan = new javax.swing.JTable();
        cbKhachHangBH = new javax.swing.JComboBox<>();
        jScrollPaneBanHang = new javax.swing.JScrollPane();
        tblSanPhamBH = new javax.swing.JTable();
        btnTimSP = new javax.swing.JButton();
        txtTimSP = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnDangXuat = new javax.swing.JButton();
        btnLayMatKhau = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TRANG CHỦ");
        setName("TRANG CHỦ"); // NOI18N

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane1.setForeground(new java.awt.Color(51, 102, 255));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(1335, 200));

        jTabbedPaneTrangChu.setDoubleBuffered(true);
        jTabbedPaneTrangChu.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jPanel15.setLayout(null);

        jPanel16.setBackground(new java.awt.Color(153, 153, 153));
        jPanel16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 255));
        jLabel14.setText("CỬA HÀNG YAME SHOP - UY TÍN - CHẤT LƯỢNG - GIÁ TỐT");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1316, Short.MAX_VALUE)
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel16Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel14)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel16Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel14)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel15.add(jPanel16);
        jPanel16.setBounds(10, 10, 1320, 40);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 652, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel15.add(jPanel20);
        jPanel20.setBounds(20, 21, 652, 30);

        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bgmall.png"))); // NOI18N
        jLabel68.setText("jLabel68");
        jPanel15.add(jLabel68);
        jLabel68.setBounds(15, 55, 890, 440);

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin liên hệ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(0, 102, 204))); // NOI18N

        jLabel55.setBackground(new java.awt.Color(0, 102, 204));
        jLabel55.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 0, 51));
        jLabel55.setText("Môn: Lập trình Java");

        jLabel63.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(0, 102, 204));
        jLabel63.setText("Lớp: 16DTHA3");

        jLabel71.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(0, 102, 204));
        jLabel71.setText("Nhóm thực hiện: 8");

        jLabel72.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(0, 102, 204));
        jLabel72.setText("GVHD: Ths. Nguyễn Thị Anh Thư");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel55)
                    .addComponent(jLabel71)
                    .addComponent(jLabel63)
                    .addComponent(jLabel72))
                .addContainerGap(121, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel55)
                .addGap(18, 18, 18)
                .addComponent(jLabel72)
                .addGap(18, 18, 18)
                .addComponent(jLabel71)
                .addGap(18, 18, 18)
                .addComponent(jLabel63)
                .addContainerGap(235, Short.MAX_VALUE))
        );

        jPanel15.add(jPanel21);
        jPanel21.setBounds(920, 60, 410, 440);

        jTabbedPaneTrangChu.addTab("Trang chủ", jPanel15);

        jTabbedPane1.addTab("Hệ thống", jTabbedPaneTrangChu);

        jTabbedPaneQLNguoiDung.setMinimumSize(new java.awt.Dimension(1188, 77));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin người dùng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(51, 102, 255))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Tài khoản");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setText("Họ tên");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setText("Điện thoại");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setText("Chi nhánh");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel10.setText("Chức vụ");

        cbxChiNanh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel33.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel33.setText("Email");

        jLabel34.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel34.setText("Địa chỉ");

        jLabel37.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel37.setText("Giới tính");

        cbGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel33))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel34)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTen, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                            .addComponent(txtTaiKhoan)
                            .addComponent(txtSoDienThoai)
                            .addComponent(txtEmailND)
                            .addComponent(txtDiaChi)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxChiNanh, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel37)
                        .addGap(18, 18, 18)
                        .addComponent(cbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmailND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxChiNanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách người dùng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 102, 204))); // NOI18N
        jScrollPane2.setForeground(new java.awt.Color(0, 102, 204));

        tblNguoiDung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblNguoiDung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguoiDungMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNguoiDung);

        btnThemNguoiDung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnThemNguoiDung.setText("Thêm");
        btnThemNguoiDung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemNguoiDungMouseClicked(evt);
            }
        });

        btnSuaNguoiDung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/register.png"))); // NOI18N
        btnSuaNguoiDung.setText("Sửa");
        btnSuaNguoiDung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaNguoiDungMouseClicked(evt);
            }
        });

        btnXoaNguoiDung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnXoaNguoiDung.setText("Xóa");
        btnXoaNguoiDung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaNguoiDungMouseClicked(evt);
            }
        });

        btnXuatNguoiDung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/export.png"))); // NOI18N
        btnXuatNguoiDung.setText("Xuất file");
        btnXuatNguoiDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatNguoiDungActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelNguoiDungLayout = new javax.swing.GroupLayout(jPanelNguoiDung);
        jPanelNguoiDung.setLayout(jPanelNguoiDungLayout);
        jPanelNguoiDungLayout.setHorizontalGroup(
            jPanelNguoiDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelNguoiDungLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 923, Short.MAX_VALUE)
                .addGroup(jPanelNguoiDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelNguoiDungLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelNguoiDungLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThemNguoiDung)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSuaNguoiDung)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXoaNguoiDung)
                        .addGap(14, 14, 14)
                        .addComponent(btnXuatNguoiDung)
                        .addGap(33, 33, 33))))
        );
        jPanelNguoiDungLayout.setVerticalGroup(
            jPanelNguoiDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNguoiDungLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanelNguoiDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelNguoiDungLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelNguoiDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThemNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSuaNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoaNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXuatNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPaneQLNguoiDung.addTab("Quản lý người dùng", jPanelNguoiDung);

        jPanelChiNhanh.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin chi nhánh", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(51, 102, 255))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Mã chi nhánh");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Tên chi nhánh");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Địa chỉ");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("Số điện thoại");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenChiNhanh)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDiaChiChiNhanh, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                            .addComponent(txtMaChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSdtChiNhanh))
                        .addGap(0, 29, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTenChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDiaChiChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSdtChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách chi nhánh", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 102, 204))); // NOI18N

        tblChiNhanh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblChiNhanh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiNhanhMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblChiNhanh);

        btnSuaChiNhanh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/register.png"))); // NOI18N
        btnSuaChiNhanh.setText("Sửa");
        btnSuaChiNhanh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaChiNhanhMouseClicked(evt);
            }
        });

        btnXoaChiNhanh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnXoaChiNhanh.setText("Xóa");
        btnXoaChiNhanh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaChiNhanhMouseClicked(evt);
            }
        });
        btnXoaChiNhanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaChiNhanhActionPerformed(evt);
            }
        });

        btnXuatChiNhanh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/export.png"))); // NOI18N
        btnXuatChiNhanh.setText("Xuất file");
        btnXuatChiNhanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatChiNhanhActionPerformed(evt);
            }
        });

        btnThemChiNhanh3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnThemChiNhanh3.setText("Thêm");
        btnThemChiNhanh3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemChiNhanh3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelChiNhanhLayout = new javax.swing.GroupLayout(jPanelChiNhanh);
        jPanelChiNhanh.setLayout(jPanelChiNhanhLayout);
        jPanelChiNhanhLayout.setHorizontalGroup(
            jPanelChiNhanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelChiNhanhLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 861, Short.MAX_VALUE)
                .addGroup(jPanelChiNhanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelChiNhanhLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(btnThemChiNhanh3)
                        .addGap(18, 18, 18)
                        .addComponent(btnSuaChiNhanh)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaChiNhanh)
                        .addGap(18, 18, 18)
                        .addComponent(btnXuatChiNhanh))
                    .addGroup(jPanelChiNhanhLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        jPanelChiNhanhLayout.setVerticalGroup(
            jPanelChiNhanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelChiNhanhLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanelChiNhanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelChiNhanhLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanelChiNhanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoaChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSuaChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemChiNhanh3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXuatChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPaneQLNguoiDung.addTab("Quản lý chi nhánh", jPanelChiNhanh);

        jTabbedPane1.addTab("Cơ cấu", jTabbedPaneQLNguoiDung);

        jTabbedPaneQLSanPham.setMinimumSize(new java.awt.Dimension(50, 50));
        jTabbedPaneQLSanPham.setPreferredSize(new java.awt.Dimension(50, 50));

        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh mục sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(51, 153, 255))); // NOI18N

        tblDanhMuc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Mã danh mục", "Tên danh mục"
            }
        ));
        tblDanhMuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhMucMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblDanhMuc);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin danh mục", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(51, 102, 255))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel19.setText("Mã danh mục");

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel20.setText("Tên danh mục");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19))
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenDanhMuc)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtMaDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 103, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtMaDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtTenDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        btnThemDanhMuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnThemDanhMuc.setText("Thêm");
        btnThemDanhMuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemDanhMucMouseClicked(evt);
            }
        });

        btnSuaDanhMuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/register.png"))); // NOI18N
        btnSuaDanhMuc.setText("Sửa");
        btnSuaDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaDanhMucActionPerformed(evt);
            }
        });

        btnXoaDanhMuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnXoaDanhMuc.setText("Xóa");
        btnXoaDanhMuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaDanhMucMouseClicked(evt);
            }
        });
        btnXoaDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaDanhMucActionPerformed(evt);
            }
        });

        btnXuatDanhMuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/export.png"))); // NOI18N
        btnXuatDanhMuc.setText("Xuất file");
        btnXuatDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatDanhMucActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelDMSanPhamLayout = new javax.swing.GroupLayout(jPanelDMSanPham);
        jPanelDMSanPham.setLayout(jPanelDMSanPhamLayout);
        jPanelDMSanPhamLayout.setHorizontalGroup(
            jPanelDMSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDMSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 884, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanelDMSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDMSanPhamLayout.createSequentialGroup()
                        .addComponent(btnThemDanhMuc)
                        .addGap(18, 18, 18)
                        .addComponent(btnSuaDanhMuc)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaDanhMuc)
                        .addGap(18, 18, 18)
                        .addComponent(btnXuatDanhMuc))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );
        jPanelDMSanPhamLayout.setVerticalGroup(
            jPanelDMSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDMSanPhamLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanelDMSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDMSanPhamLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanelDMSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThemDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSuaDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoaDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXuatDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPaneQLSanPham.addTab("Quản lý danh mục sản phẩm", jPanelDMSanPham);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(51, 102, 255))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel11.setText("Mã sản phẩm");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel12.setText("Tên sản phẩm");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel13.setText("Danh mục");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel15.setText("Số lượng");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel16.setText("Giá bán");

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel17.setText("Giá nhập");

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel18.setText("Kích thước");

        btnDoiHinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnDoiHinh.setText("Đổi hình");
        btnDoiHinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiHinhActionPerformed(evt);
            }
        });

        btnThemSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnThemSanPham.setText("Thêm");
        btnThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamActionPerformed(evt);
            }
        });

        btnXoaSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnXoaSanPham.setText("Xóa");
        btnXoaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSanPhamActionPerformed(evt);
            }
        });

        btnSuaSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/register.png"))); // NOI18N
        btnSuaSanPham.setText("Sửa");
        btnSuaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSanPhamActionPerformed(evt);
            }
        });

        cbxDanhMuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel47.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel47.setText("Hình ảnh");

        jLabel48.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel48.setText("Mô tả");

        jLabel49.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel49.setText("Tồn kho");

        jLabel50.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel50.setText("Ngày nhập");

        btnXuatFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/export.png"))); // NOI18N
        btnXuatFile.setText("Xuất file");
        btnXuatFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13)
                            .addComponent(jLabel15)
                            .addComponent(jLabel49)
                            .addComponent(jLabel50)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThemSanPham, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(200, 200, 200)
                                        .addComponent(jLabel48))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel47))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(64, 64, 64)
                                        .addComponent(btnSuaSanPham)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnXoaSanPham)))
                                .addGap(12, 12, 12))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(textAreaMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnXuatFile, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 40, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTonKho, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(34, 34, 34))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnDoiHinh)
                .addGap(66, 66, 66))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel48))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTonKho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel49)))
                    .addComponent(textAreaMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel50)
                            .addComponent(jLabel47)
                            .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)))
                    .addComponent(lbHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnDoiHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã sản phẩm", "Tên sản phẩm", "Kích thước", "Số lượng", "Tồn kho", "Mô tả", "Hình ảnh", "Giá nhập", "Giá bán", "Ngày nhập", "Loại sản phẩm"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSanPham);

        btnTimSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnTimSanPham.setText("Tìm kiếm");
        btnTimSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimSanPhamMouseClicked(evt);
            }
        });

        txtTimKiemSP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemSPKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanelDSSanPhamLayout = new javax.swing.GroupLayout(jPanelDSSanPham);
        jPanelDSSanPham.setLayout(jPanelDSSanPhamLayout);
        jPanelDSSanPhamLayout.setHorizontalGroup(
            jPanelDSSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDSSanPhamLayout.createSequentialGroup()
                .addGroup(jPanelDSSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDSSanPhamLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 676, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDSSanPhamLayout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(txtTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(btnTimSanPham)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelDSSanPhamLayout.setVerticalGroup(
            jPanelDSSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDSSanPhamLayout.createSequentialGroup()
                .addGroup(jPanelDSSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDSSanPhamLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanelDSSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTimSanPham)
                            .addComponent(txtTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPaneQLSanPham.addTab("Quản lý danh sách sản phẩm", jPanelDSSanPham);

        jTabbedPane1.addTab("Sản phẩm", jTabbedPaneQLSanPham);

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin chi tiết nhập hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 102, 204))); // NOI18N

        btnSuaCTHDNH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/register.png"))); // NOI18N
        btnSuaCTHDNH.setText("Sửa");
        btnSuaCTHDNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaCTHDNHActionPerformed(evt);
            }
        });

        jLabel89.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel89.setText("Mã phiếu nhập");

        jLabel90.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel90.setText("Sản phẩm");

        jLabel91.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel91.setText("Số lượng nhập");

        jLabel92.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel92.setText("Đơn giá");

        btnThemCTHDNH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnThemCTHDNH.setText("Thêm");
        btnThemCTHDNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemCTHDNHActionPerformed(evt);
            }
        });

        btnXuatCTHDNH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/export.png"))); // NOI18N
        btnXuatCTHDNH.setText("Xuất file");

        btnXoaCTHDNH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnXoaCTHDNH.setText("Xóa");
        btnXoaCTHDNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaCTHDNHActionPerformed(evt);
            }
        });

        cbMAPN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbSanPhamNH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbSanPhamNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSanPhamNHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel89)
                            .addComponent(jLabel91))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSoLuongCTHD2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbMAPN, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(btnThemCTHDNH)
                        .addGap(54, 54, 54)
                        .addComponent(btnSuaCTHDNH)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel92)
                    .addComponent(jLabel90)
                    .addComponent(btnXoaCTHDNH))
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbSanPhamNH, 0, 126, Short.MAX_VALUE)
                            .addComponent(txtDonGiaCTHD2)))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(btnXuatCTHDNH)))
                .addGap(23, 23, 23))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel89)
                    .addComponent(jLabel90)
                    .addComponent(cbSanPhamNH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbMAPN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel91)
                    .addComponent(txtSoLuongCTHD2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel92)
                    .addComponent(txtDonGiaCTHD2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemCTHDNH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaCTHDNH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaCTHDNH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatCTHDNH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin nhập hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 102, 204))); // NOI18N

        btnSuaHDNH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/register.png"))); // NOI18N
        btnSuaHDNH.setText("Sửa");
        btnSuaHDNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaHDNHActionPerformed(evt);
            }
        });

        jLabel94.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel94.setText("Mã phiếu nhập");

        jLabel95.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel95.setText("Nhà cung cấp");

        jLabel96.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel96.setText("Nhân viên");

        jLabel97.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel97.setText("Chi nhánh");

        jLabel100.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel100.setText("Ngày nhập");

        jLabel101.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel101.setText("Tổng tiền");

        txtTongTienHD2.setText("0");

        btnXoaHDNH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnXoaHDNH.setText("Xóa");
        btnXoaHDNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHDNHActionPerformed(evt);
            }
        });

        btnXuatHDNH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/export.png"))); // NOI18N
        btnXuatHDNH.setText("Xuất file");

        btnThemHDNH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnThemHDNH.setText("Thêm");
        btnThemHDNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHDNHActionPerformed(evt);
            }
        });

        cbNhanVienNH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbChiNhanhNH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbNCCNH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel94)
                            .addComponent(jLabel96)
                            .addComponent(jLabel100)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnThemHDNH)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(txtMaPhieuNHap, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel95)
                        .addGap(9, 9, 9)
                        .addComponent(cbNCCNH, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cbNhanVienNH, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNgayLapNH, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(btnSuaHDNH)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel97, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel101, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnXoaHDNH, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtTongTienHD2)
                                .addComponent(cbChiNhanhNH, 0, 135, Short.MAX_VALUE))
                            .addComponent(btnXuatHDNH, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(18, 18, 18))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel94)
                    .addComponent(txtMaPhieuNHap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel95)
                    .addComponent(cbNCCNH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel96)
                    .addComponent(jLabel97)
                    .addComponent(cbNhanVienNH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbChiNhanhNH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel100)
                    .addComponent(txtNgayLapNH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTongTienHD2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel101))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSuaHDNH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatHDNH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemHDNH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaHDNH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jScrollPane14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách nhập hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 102, 204))); // NOI18N

        tblHoaDonNH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblHoaDonNH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonNHMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(tblHoaDonNH);

        jScrollPane15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách chi tiết nhập hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 102, 204))); // NOI18N

        tblCTHDNH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCTHDNH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCTHDNHMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(tblCTHDNH);

        javax.swing.GroupLayout jPanelBanHang2Layout = new javax.swing.GroupLayout(jPanelBanHang2);
        jPanelBanHang2.setLayout(jPanelBanHang2Layout);
        jPanelBanHang2Layout.setHorizontalGroup(
            jPanelBanHang2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBanHang2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBanHang2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 757, Short.MAX_VALUE)
                    .addComponent(jScrollPane15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelBanHang2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelBanHang2Layout.setVerticalGroup(
            jPanelBanHang2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBanHang2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelBanHang2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelBanHang2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(211, 211, 211))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1347, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel17Layout.createSequentialGroup()
                    .addGap(0, 12, Short.MAX_VALUE)
                    .addComponent(jPanelBanHang2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 11, Short.MAX_VALUE)))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 725, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel17Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanelBanHang2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("Quản lý nhập hàng", jPanel17);

        jTabbedPane1.addTab("Giao dịch", jTabbedPane2);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin chi tiết hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 102, 204))); // NOI18N

        btnSuaCTHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/register.png"))); // NOI18N
        btnSuaCTHD.setText("Sửa");
        btnSuaCTHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaCTHDActionPerformed(evt);
            }
        });

        jLabel59.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel59.setText("Mã hóa đơn");

        jLabel60.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel60.setText("Sản phẩm");

        jLabel61.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel61.setText("Số lượng");

        jLabel62.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel62.setText("Đơn giá");

        btnThemCTHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnThemCTHD.setText("Thêm");
        btnThemCTHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemCTHDActionPerformed(evt);
            }
        });

        btnXuatCTHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/export.png"))); // NOI18N
        btnXuatCTHD.setText("Xuất file");

        btnXoaCTHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnXoaCTHD.setText("Xóa");
        btnXoaCTHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaCTHDActionPerformed(evt);
            }
        });

        cbMaHoaDon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbSanPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSanPhamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel61)
                            .addComponent(jLabel59))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtSoLuongCTHD, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbMaHoaDon, javax.swing.GroupLayout.Alignment.LEADING, 0, 90, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel60)
                            .addComponent(jLabel62))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbSanPham, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDonGiaCTHD)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnThemCTHD)
                        .addGap(41, 41, 41)
                        .addComponent(btnSuaCTHD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXoaCTHD)
                        .addGap(47, 47, 47)
                        .addComponent(btnXuatCTHD)))
                .addGap(39, 39, 39))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59)
                    .addComponent(jLabel60)
                    .addComponent(cbSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(txtSoLuongCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62)
                    .addComponent(txtDonGiaCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 102, 204))); // NOI18N

        btnSuaHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/register.png"))); // NOI18N
        btnSuaHD.setText("Sửa");
        btnSuaHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaHDActionPerformed(evt);
            }
        });

        jLabel51.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel51.setText("Mã hóa đơn");

        jLabel52.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel52.setText("Khách hàng");

        jLabel53.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel53.setText("Nhân viên");

        jLabel54.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel54.setText("Chi nhánh");

        jLabel56.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel56.setText("Giảm giá");

        txtGiamGiaHD.setText("0");

        jLabel57.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel57.setText("Ngày lập");

        jLabel58.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel58.setText("Tổng tiền");

        txtTongTienHD.setText("0");

        btnXoaHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnXoaHD.setText("Xóa");
        btnXoaHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHDActionPerformed(evt);
            }
        });

        btnXuatHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/export.png"))); // NOI18N
        btnXuatHD.setText("Xuất file");

        btnThemHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnThemHD.setText("Thêm");
        btnThemHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHDActionPerformed(evt);
            }
        });

        cbNhanVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbKhachHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbChiNhanh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel51)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(btnThemHD)
                        .addGap(35, 35, 35)
                        .addComponent(btnSuaHD)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(129, 129, 129)
                                .addComponent(txtMaHoaDon))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel53)
                                    .addComponent(jLabel52)
                                    .addComponent(jLabel57))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNgayLap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(cbKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(cbNhanVien, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel54)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel58)
                                    .addComponent(jLabel56)
                                    .addComponent(btnXoaHD))
                                .addGap(3, 3, 3)))))
                .addGap(21, 21, 21)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtGiamGiaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTongTienHD, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnXuatHD, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(46, 46, 46))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cbChiNhanh, txtGiamGiaHD, txtTongTienHD});

        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54)
                    .addComponent(cbChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(cbNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56)
                    .addComponent(txtGiamGiaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52)
                    .addComponent(jLabel58)
                    .addComponent(txtTongTienHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(txtNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemHD, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatHD, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 26, Short.MAX_VALUE))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbKhachHang, cbNhanVien});

        jScrollPane8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 102, 204))); // NOI18N

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblHoaDon);

        jScrollPane9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách chi tiết hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 102, 204))); // NOI18N

        tblCTHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ));
        tblCTHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCTHDMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tblCTHD);

        javax.swing.GroupLayout jPanelBanHangLayout = new javax.swing.GroupLayout(jPanelBanHang);
        jPanelBanHang.setLayout(jPanelBanHangLayout);
        jPanelBanHangLayout.setHorizontalGroup(
            jPanelBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBanHangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
                    .addComponent(jScrollPane9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelBanHangLayout.setVerticalGroup(
            jPanelBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBanHangLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelBanHangLayout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelBanHangLayout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(211, 211, 211))
        );

        jTabbedPane3.addTab("Quản lý hóa đơn bán hàng", jPanelBanHang);

        jTabbedPane1.addTab("Hóa đơn", jTabbedPane3);

        jScrollPane5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách nhà cung cấp", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 102, 204))); // NOI18N

        tblNhaCC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ", "Số điện thoại", "Email", "Tổng tiền nhập"
            }
        ));
        tblNhaCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhaCCMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblNhaCC);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin nhà cung cấp", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 102, 204))); // NOI18N

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel21.setText("Mã nhà cung cấp");

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel22.setText("Tên nhà cung cấp");

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel23.setText("Địa chỉ");

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel24.setText("Số điện thoại");

        jLabel29.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel29.setText("Email");

        jLabel31.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel31.setText("Tổng tiền nhập");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(28, 28, 28)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenNhaCC)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDiaChiNCC, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                            .addComponent(txtNhaCC, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSdtNCC)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTongTienNhap))
                        .addGap(0, 29, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtNhaCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtTenNhaCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtDiaChiNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtSdtNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTongTienNhap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btnThemNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnThemNCC.setText("Thêm");
        btnThemNCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemNCCMouseClicked(evt);
            }
        });

        btnSuaNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/register.png"))); // NOI18N
        btnSuaNCC.setText("Sửa");
        btnSuaNCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaNCCMouseClicked(evt);
            }
        });

        btnXoaNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnXoaNCC.setText("Xóa");
        btnXoaNCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaNCCMouseClicked(evt);
            }
        });
        btnXoaNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaNCCActionPerformed(evt);
            }
        });

        btnXuatNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/export.png"))); // NOI18N
        btnXuatNCC.setText("Xuất file");
        btnXuatNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatNCCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelNhaCungCapLayout = new javax.swing.GroupLayout(jPanelNhaCungCap);
        jPanelNhaCungCap.setLayout(jPanelNhaCungCapLayout);
        jPanelNhaCungCapLayout.setHorizontalGroup(
            jPanelNhaCungCapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelNhaCungCapLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE)
                .addGroup(jPanelNhaCungCapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelNhaCungCapLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(btnThemNCC)
                        .addGap(18, 18, 18)
                        .addComponent(btnSuaNCC)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaNCC)
                        .addGap(18, 18, 18)
                        .addComponent(btnXuatNCC))
                    .addGroup(jPanelNhaCungCapLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        jPanelNhaCungCapLayout.setVerticalGroup(
            jPanelNhaCungCapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNhaCungCapLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanelNhaCungCapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelNhaCungCapLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelNhaCungCapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThemNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSuaNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoaNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXuatNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane4.addTab("Quản lý nhà cung cấp", jPanelNhaCungCap);

        jScrollPane6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 102, 204))); // NOI18N

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã khách hàng", "Tên khách hàng", "Điạ chỉ", "Số điện thoại", "Email", "Tổng tiền mua"
            }
        ));
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblKhachHang);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 102, 204))); // NOI18N

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel25.setText("Mã khách hàng");

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel26.setText("Tên khách hàng");

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel27.setText("Địa chỉ");

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel28.setText("Số điện thoại");

        jLabel30.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel30.setText("Email");

        jLabel32.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel32.setText("Tổng tiền mua");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26)
                    .addComponent(jLabel30)
                    .addComponent(jLabel28)
                    .addComponent(jLabel27)
                    .addComponent(jLabel25)
                    .addComponent(jLabel32))
                .addGap(23, 23, 23)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenKH)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDiaChiKH, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSdtKH, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                            .addComponent(txtEmailKH)
                            .addComponent(txtTongTienMua))
                        .addGap(0, 29, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtDiaChiKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtSdtKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txtEmailKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtTongTienMua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnThemKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnThemKH.setText("Thêm");
        btnThemKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemKHMouseClicked(evt);
            }
        });

        btnSuaKh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnSuaKh.setText("Sửa");
        btnSuaKh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaKhMouseClicked(evt);
            }
        });

        btnXoaKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnXoaKH.setText("Xóa");
        btnXoaKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaKHMouseClicked(evt);
            }
        });
        btnXoaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKHActionPerformed(evt);
            }
        });

        btnXuatKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnXuatKH.setText("Xuất file");
        btnXuatKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelKhachHangLayout = new javax.swing.GroupLayout(jPanelKhachHang);
        jPanelKhachHang.setLayout(jPanelKhachHangLayout);
        jPanelKhachHangLayout.setHorizontalGroup(
            jPanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 878, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelKhachHangLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnThemKH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSuaKh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXoaKH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXuatKH))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );
        jPanelKhachHangLayout.setVerticalGroup(
            jPanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelKhachHangLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelKhachHangLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSuaKh, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXuatKH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(85, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Khách hàng", jPanelKhachHang);

        jTabbedPane1.addTab("Đối tác", jTabbedPane4);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm thông tin bán hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 102, 255))); // NOI18N

        jLabel64.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel64.setText("Từ ngày");

        jLabel65.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel65.setText("Đến ngày");

        jLabel66.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel66.setText("Chi nhánh");

        btnLocBaoCaoBH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/filter.png"))); // NOI18N
        btnLocBaoCaoBH.setText("Lọc");
        btnLocBaoCaoBH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLocBaoCaoBHMouseClicked(evt);
            }
        });

        cbxChiNanhBC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnInBaoCaoBH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print.png"))); // NOI18N
        btnInBaoCaoBH.setText("In");
        btnInBaoCaoBH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInBaoCaoBHMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel64)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTuNgayBCBH, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel65)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDenNgayBCBH, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jLabel66)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxChiNanhBC, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnLocBaoCaoBH)
                .addGap(37, 37, 37)
                .addComponent(btnInBaoCaoBH)
                .addContainerGap(425, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(txtDenNgayBCBH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel66)
                    .addComponent(btnLocBaoCaoBH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxChiNanhBC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInBaoCaoBH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTuNgayBCBH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        tblBaoCaoBH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane10.setViewportView(tblBaoCaoBH);

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1288, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 66, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelBCBanHangLayout = new javax.swing.GroupLayout(jPanelBCBanHang);
        jPanelBCBanHang.setLayout(jPanelBCBanHangLayout);
        jPanelBCBanHangLayout.setHorizontalGroup(
            jPanelBCBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBCBanHangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBCBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane10)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelBCBanHangLayout.setVerticalGroup(
            jPanelBCBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBCBanHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane6.addTab("Báo cáo bán hàng", jPanelBCBanHang);

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm thông tin nhập hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 102, 204))); // NOI18N

        jLabel67.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel67.setText("Từ ngày");

        jLabel69.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel69.setText("Đến ngày");

        jLabel70.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel70.setText("Chi nhánh");

        btnLocBaoCaoNH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/filter.png"))); // NOI18N
        btnLocBaoCaoNH.setText("Lọc");
        btnLocBaoCaoNH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLocBaoCaoNHMouseClicked(evt);
            }
        });

        cbxChiNanhBC1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnInBaoCaoNH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print.png"))); // NOI18N
        btnInBaoCaoNH.setText("In");
        btnInBaoCaoNH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInBaoCaoNHMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel67)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTuNgayBCNH, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel69)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDenNgayBCNH, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jLabel70)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxChiNanhBC1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(btnLocBaoCaoNH)
                .addGap(30, 30, 30)
                .addComponent(btnInBaoCaoNH)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69)
                    .addComponent(txtDenNgayBCNH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70)
                    .addComponent(btnLocBaoCaoNH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxChiNanhBC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInBaoCaoNH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTuNgayBCNH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        tblBaoCaoNH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane11.setViewportView(tblBaoCaoNH);

        jPanel23.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1288, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 66, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelBCBanHang1Layout = new javax.swing.GroupLayout(jPanelBCBanHang1);
        jPanelBCBanHang1.setLayout(jPanelBCBanHang1Layout);
        jPanelBCBanHang1Layout.setHorizontalGroup(
            jPanelBCBanHang1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBCBanHang1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBCBanHang1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane11)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelBCBanHang1Layout.setVerticalGroup(
            jPanelBCBanHang1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBCBanHang1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelBCXuatNhapLayout = new javax.swing.GroupLayout(jPanelBCXuatNhap);
        jPanelBCXuatNhap.setLayout(jPanelBCXuatNhapLayout);
        jPanelBCXuatNhapLayout.setHorizontalGroup(
            jPanelBCXuatNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1316, Short.MAX_VALUE)
            .addGroup(jPanelBCXuatNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelBCXuatNhapLayout.createSequentialGroup()
                    .addGap(0, 4, Short.MAX_VALUE)
                    .addComponent(jPanelBCBanHang1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 5, Short.MAX_VALUE)))
        );
        jPanelBCXuatNhapLayout.setVerticalGroup(
            jPanelBCXuatNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 507, Short.MAX_VALUE)
            .addGroup(jPanelBCXuatNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelBCXuatNhapLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanelBCBanHang1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane6.addTab("Báo cáo xuất nhập tồn", jPanelBCXuatNhap);

        jTabbedPane1.addTab("Báo cáo", jTabbedPane6);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bán hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 102, 204))); // NOI18N

        btnLuuHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print.png"))); // NOI18N
        btnLuuHD.setText("Lưu hóa đơn và in");
        btnLuuHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuHDActionPerformed(evt);
            }
        });

        btnThemKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnThemKhachHang.setText("Thêm khách hàng");
        btnThemKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemKhachHangMouseClicked(evt);
            }
        });
        btnThemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKhachHangActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel35.setText("Ngày bán");

        jLabel36.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel36.setText("Tên khách hàng");

        jLabel38.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel38.setText("Tên nhân viên");

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin mặt hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 102, 204))); // NOI18N

        jLabel39.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel39.setText("Mã sản phẩm");

        jLabel40.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel40.setText("Số lượng");

        txtMaSP2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMaSP2KeyPressed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel41.setText("Tên sản phẩm");

        jLabel42.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel42.setText("Kích thước");

        jLabel43.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel43.setText("Đơn giá");

        jLabel44.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel44.setText("Thành tiền");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMaSP2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaSP3)
                    .addComponent(txtMaSP4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44)
                    .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDonGia)
                    .addComponent(txtThanhTien))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel43)
                        .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel41)
                        .addComponent(txtMaSP3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel39)
                        .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel44)
                        .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel42)
                        .addComponent(txtMaSP4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel40)
                        .addComponent(txtMaSP2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23))
        );

        jLabel45.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel45.setText("Giảm giá");

        txtGiamGia.setText("0");

        jLabel46.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel46.setText("Tổng tiền");

        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnHuy.setText("Hủy");
        btnHuy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHuyMouseClicked(evt);
            }
        });

        tblHoaDonBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Kích thước", "Số lượng", "Giá bán", "Thành tiền"
            }
        ));
        tblHoaDonBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonBanMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblHoaDonBan);

        cbKhachHangBH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbKhachHangBH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKhachHangBHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel38))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel35)))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtNgayBan, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(txtTenNV))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel36)
                .addGap(18, 18, 18)
                .addComponent(cbKhachHangBH, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 781, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(btnLuuHD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnHuy)
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThemKhachHang)
                .addGap(47, 47, 47))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(btnThemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel38))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel36)
                        .addComponent(cbKhachHangBH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(txtNgayBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46)
                    .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLuuHD, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );

        tblSanPhamBH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPhamBH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamBHMouseClicked(evt);
            }
        });
        jScrollPaneBanHang.setViewportView(tblSanPhamBH);

        btnTimSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnTimSP.setText("Tìm sản phẩm");
        btnTimSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimSPMouseClicked(evt);
            }
        });

        txtTimSP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimSPKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPaneBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(btnTimSP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimSP, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTimSP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTimSP))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPaneBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane7.addTab("Quản lý bán hàng", jPanel8);

        jTabbedPane1.addTab("Bán hàng", jTabbedPane7);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 102, 255));
        jLabel1.setText("QUẢN LÝ CỬA HÀNG YAME SHOP");

        btnDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnDangXuat.setText("Đăng xuất");
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });

        btnLayMatKhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/register.png"))); // NOI18N
        btnLayMatKhau.setText("Thay đổi mật khẩu");
        btnLayMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLayMatKhauActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLayMatKhau)
                .addGap(18, 18, 18)
                .addComponent(btnDangXuat)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDangXuat)
                            .addComponent(btnLayMatKhau))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnXuatKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXuatKHActionPerformed

    private void btnXoaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaKHActionPerformed

    private void btnXuatNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatNCCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXuatNCCActionPerformed

    private void btnXoaNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaNCCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaNCCActionPerformed

    private void btnSuaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSanPhamActionPerformed
        // TODO add your handling code here:
        try
        {
            int row = tblSanPham.getSelectedRow();
            int index = cbxDanhMuc.getSelectedIndex();
            DefaultTableModel models = (DefaultTableModel)tblSanPham.getModel();
            int id = (int)models.getValueAt(row, 0);
            String duongDan = "src/images/SanPham/" + txtMaSanPham.getText();
            if(regex.checkTTSP(txtSoLuong.getText(), regex.slc) && regex.checkTTSP(txtGiaNhap.getText(), regex.giac) && regex.checkTTSP(txtGiaBan.getText(), regex.giac))
            {
                String sql = "UPDATE san_pham "
                        + "SET ma_sp = '"+txtMaSanPham.getText()+"', ten_sp = N'"+txtTenSanPham.getText()+"', kich_thuoc = N'"+txtKichThuoc.getText()+"',"
                        + " so_luong =  '"+txtSoLuong.getText()+"', ton_kho = '"+txtTonKho.getText()+"', mo_ta = N'"+textAreaMoTa.getText()+"',"
                        + " hinh_anh = N'"+duongDan+"', gia_nhap = '"+txtGiaNhap.getText()+"', gia_ban = '"+txtGiaBan.getText()+"', ma_dm = '"+getDMID(index)+"' "
                        + "WHERE id = '"+id+"'";
                try
                {
                    File saveFile = new File(duongDan);
                    if(!saveFile.exists())
                    {
                        saveFile.mkdirs();
                        saveFile.createNewFile();
                        ImageIO.write(bi, "png", saveFile);
                    }
                    else
                    {
                        saveFile.delete();
                        saveFile.mkdirs();
                        saveFile.createNewFile();
                        ImageIO.write(bi, "png", saveFile);
                    }
                    cls.excuteQueryUpdateDB(sql);
                    JOptionPane.showMessageDialog(this, "Sửa sản phẩm "+txtMaSanPham.getText()+" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
                    loadSP();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
               JOptionPane.showMessageDialog(this, regex.Mess, "Thông báo", JOptionPane.ERROR_MESSAGE); 
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSuaSanPhamActionPerformed

    private void btnXoaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSanPhamActionPerformed
        // TODO add your handling code here:
        try
        {
            int row = tblSanPham.getSelectedRow();

            DefaultTableModel models = (DefaultTableModel)tblSanPham.getModel(); 

            String duongDan = models.getValueAt(row, 7).toString();
            File file = new File(duongDan);
            if(file.exists())
                file.delete();

            String sql = "DELETE FROM san_pham WHERE id = "+(int)models.getValueAt(row, 0)+"";
            cls.excuteQueryUpdateDB(sql);
            JOptionPane.showMessageDialog(this, "Xóa sản phẩm "+models.getValueAt(row, 1)+" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
            loadSP();
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnXoaSanPhamActionPerformed

    private void btnThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSanPhamActionPerformed
        // TODO add your handling code here:
        int index = cbxDanhMuc.getSelectedIndex();
        String duongDan = "src/images/SanPham/"+txtMaSanPham.getText();
        
        if(existsInDatabaseTable("san_pham", "ma_sp", txtMaSanPham.getText(), 1))
            JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại", "Thông báo", JOptionPane.ERROR_MESSAGE); 
        else
        {
            if(regex.checkTTSP(txtSoLuong.getText(), regex.slc) && regex.checkTTSP(txtGiaNhap.getText(), regex.giac) && regex.checkTTSP(txtGiaBan.getText(), regex.giac))
            {
                String sql1 = "INSERT  INTO  san_pham ([ma_sp], [ten_sp], [kich_thuoc], [so_luong], [ton_kho], [mo_ta], [hinh_anh], [gia_nhap], [gia_ban], [ngay_nhap], [ma_dm]) "
                    + "VALUES ('"+txtMaSanPham.getText()+"', N'"+txtTenSanPham.getText()+"', N'"+txtKichThuoc.getText()+"', '"+txtSoLuong.getText()+"', '"+txtSoLuong.getText()+"',"
                    +  "N'"+textAreaMoTa.getText()+"', N'"+duongDan+"', '"+txtGiaNhap.getText()+"', '"+txtGiaBan.getText()+"', "
                    + "'"+getToDay()+"', '"+listDMSPID.get(index)+"')";

                try
                {
                    File saveFile = new File(duongDan);
                    if(!saveFile.exists())
                    {
                        saveFile.mkdirs();
                        saveFile.createNewFile();
                        ImageIO.write(bi, "png", saveFile);
                    }
                    cls.excuteQueryUpdateDB(sql1);
                    JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
                    loadSP();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }

            }
            else
            {
               JOptionPane.showMessageDialog(this, regex.Mess, "Thông báo", JOptionPane.ERROR_MESSAGE); 
            }
        }
    }//GEN-LAST:event_btnThemSanPhamActionPerformed

    private void btnXuatDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatDanhMucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXuatDanhMucActionPerformed

    private void btnXoaDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaDanhMucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaDanhMucActionPerformed

    private void btnXuatChiNhanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatChiNhanhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXuatChiNhanhActionPerformed

    private void btnXoaChiNhanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaChiNhanhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaChiNhanhActionPerformed

    private void btnXuatNguoiDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatNguoiDungActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXuatNguoiDungActionPerformed

    private void btnLuuHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuHDActionPerformed
        // TODO add your handling code here:
        int rowCount = tblHoaDonBan.getModel().getRowCount();
        int giamGia = 0;
        if(regex.checkTTSPBH(txtGiamGia.getText(), regex.ggbh))
            giamGia = Integer.parseInt(txtGiamGia.getText());
        else
        {
            JOptionPane.showMessageDialog(this, regex.Mess, "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
        }
        
        try
       {
            String sql = "SELECT TOP 1 * FROM hoa_don ORDER BY id DESC";
            ResultSet rs = cls.excuteQueryGetTable(sql);
            int id = 1;
            String maHD = "HD00" + id;
            if(rs.next())
            {
                id = rs.getInt("id") + 1;
                maHD = "HD00" + id;
            }

            double tongTien = 0;
            if(txtTongTien.getText() != "")
                tongTien = Double.parseDouble(txtTongTien.getText());

            sql = "INSERT  INTO  hoa_don ([ma_hd], [ma_cn], [ma_nv], [ma_kh], [ten_kh], [ngay_tao], [giam_gia], [tong_tien]) VALUES ('"+maHD+"','"
                    +nguoiDung.getMaCN()+"','"+nguoiDung.getId()+"','"+khachHang.getId()+"',N'"+khachHang.getTenKH()+"','"+getToDay()+"',"+giamGia+","+tongTien+")";
            cls.excuteQueryUpdateDB(sql);
            updateTotalPriceKH(khachHang.getId(), tongTien);

            for(int i = 0; i < rowCount; i++)
            {
                String maSP = tblHoaDonBan.getModel().getValueAt(i, 0).toString();
                sql = "SELECT id, ton_kho FROM san_pham WHERE ma_sp=N'"+maSP+"'";
                rs = cls.excuteQueryGetTable(sql);
                rs.next();
                int SPID = rs.getInt("id");
                int tonKho = rs.getInt("ton_kho");
                int newTonKho = tonKho - (int)tblHoaDonBan.getModel().getValueAt(i, 3);
                updateRemainingQuantity(SPID, newTonKho);
                int soLuong = (int)tblHoaDonBan.getModel().getValueAt(i, 3);
                float donGia = (float)tblHoaDonBan.getModel().getValueAt(i, 4);
                double thanhTien = (double)tblHoaDonBan.getModel().getValueAt(i, 5);
                sql = "INSERT  INTO  ct_hoa_don ([ma_hd], [ma_sp], [so_luong], [don_gia], [tong_tien]) VALUES ("+id+","
                    +SPID+","+soLuong+","+donGia+","+thanhTien+")";
                System.out.println(sql);
                cls.excuteQueryUpdateDB(sql);
                LoadHoaDon();
                loadKhachHang();
            }

            JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
            ((DefaultTableModel)tblHoaDonBan.getModel()).setRowCount(0);
            setNullBH();
            rowCount = tblHoaDonBan.getRowCount();
            calculateTotalPrice(rowCount);
            loadSPBH();
       }
        catch (SQLException ex) {
            System.err.println("Cannot connect database, " + ex);
        }
        catch (NullPointerException e)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng trước khi lưu","Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnLuuHDActionPerformed

    private void btnThemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKhachHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemKhachHangActionPerformed

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXuatFileActionPerformed

    private void btnTimSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimSPMouseClicked
        findSPBH();
    }//GEN-LAST:event_btnTimSPMouseClicked

    private void tblSanPhamBHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamBHMouseClicked
        int row = tblSanPhamBH.rowAtPoint(evt.getPoint());
        String value = (String)tblSanPhamBH.getModel().getValueAt(row, 0);
        int tonKho = (int)tblSanPhamBH.getModel().getValueAt(row, 2);
        int result = existsInTable(tblHoaDonBan, value);

        if(result == -1)
        {
            String  sql = "SELECT gia_ban, ma_sp, ton_kho, ten_sp,kich_thuoc FROM san_pham where ma_sp = '"+tblSanPhamBH.getModel().getValueAt(row, 0)+"' and ton_kho > 0";
            ResultSet rs = cls.excuteQueryGetTable(sql);
            try {
                rs.next();
                Vector data = new Vector();
                data.add(rs.getString("ma_sp"));
                data.add(rs.getString("ten_sp"));
                data.add(rs.getString("kich_thuoc"));
                data.add(1);
                data.add(rs.getFloat("gia_ban"));
                data.add(calculatePrice(1, rs.getFloat("gia_ban")));
               DefaultTableModel models = (DefaultTableModel)tblHoaDonBan.getModel();
               models.addRow(data);
               calculateTotalPrice(tblHoaDonBan.getRowCount());

            } catch (SQLException ex) {
               Logger.getLogger(frmTrangChu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            float donGia = Float.parseFloat((String)tblSanPhamBH.getValueAt(row, 3));
            int soLuong = (int)tblHoaDonBan.getValueAt(result, 3);
            if(soLuong < tonKho)
            {
                soLuong++;
                tblHoaDonBan.setValueAt(soLuong, result, 3);
                tblHoaDonBan.setValueAt(calculatePrice(soLuong, donGia), result, 5);
                calculateTotalPrice(tblHoaDonBan.getRowCount());
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Số lượng sản phẩm không thể lớn hơn "+tonKho, "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
            }
        }
        bindingValuesBH((result != -1) ? result : (tblHoaDonBan.getRowCount()-1));
    }//GEN-LAST:event_tblSanPhamBHMouseClicked

    private void btnThemKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemKhachHangMouseClicked
        frmDangKyKH frm = new frmDangKyKH();
        frm.show();
    }//GEN-LAST:event_btnThemKhachHangMouseClicked

    private void tblDanhMucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhMucMouseClicked
        int row = tblDanhMuc.rowAtPoint(evt.getPoint());
        DefaultTableModel models = (DefaultTableModel)tblDanhMuc.getModel();
        txtMaDanhMuc.setText(models.getValueAt(row, 1).toString());
        txtTenDanhMuc.setText(models.getValueAt(row, 2).toString());
    }//GEN-LAST:event_tblDanhMucMouseClicked

    private void btnThemDanhMucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemDanhMucMouseClicked
           
            String sql = "INSERT  INTO  danh_muc_sp ([ma_dm], [ten_dm]) VALUES ('"+txtMaDanhMuc.getText()+"',N'"+txtTenDanhMuc.getText()+"')";
            cls.excuteQueryUpdateDB(sql);
            JOptionPane.showMessageDialog(this, "Thêm danh muc thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
            loadDM();
    }//GEN-LAST:event_btnThemDanhMucMouseClicked

    private void btnSuaDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaDanhMucActionPerformed
        try {
            int row = tblDanhMuc.getSelectedRow();
        
            DefaultTableModel models = (DefaultTableModel)tblDanhMuc.getModel();
            int id = (int)models.getValueAt(row, 0);
            String sql = "UPDATE danh_muc_sp SET ma_dm = '"+txtMaDanhMuc.getText()+"', ten_dm = N'"+txtTenDanhMuc.getText()+"'  WHERE id = '"+id+"'";
            cls.excuteQueryUpdateDB(sql);
            JOptionPane.showMessageDialog(this, "Sửa danh muc "+txtMaDanhMuc.getText()+" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
            loadDM();
        } catch(ArrayIndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục cần sửa", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSuaDanhMucActionPerformed

    private void btnXoaDanhMucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaDanhMucMouseClicked
        try
        {
            int row = tblDanhMuc.getSelectedRow();
        
            DefaultTableModel models = (DefaultTableModel)tblDanhMuc.getModel(); 
            String sql = "DELETE FROM danh_muc_sp WHERE id = "+(int)models.getValueAt(row, 0)+"";
            cls.excuteQueryUpdateDB(sql);
            JOptionPane.showMessageDialog(this, "Xóa danh muc "+models.getValueAt(row, 1)+" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
            loadDM();
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục cần xóa", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnXoaDanhMucMouseClicked

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        // TODO add your handling code here:
        String message = "Bạn muốn đăng xuất?";
        String tittle = "Đăng xuất";
        int result = JOptionPane.showConfirmDialog(this, message, tittle, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            dangXuat();
        }
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        int row = tblSanPham.rowAtPoint(evt.getPoint());
        
        
        DefaultTableModel models = (DefaultTableModel)tblSanPham.getModel();
        txtMaSanPham.setText(models.getValueAt(row, 1).toString());
        txtTenSanPham.setText(models.getValueAt(row, 2).toString());
        txtKichThuoc.setText(models.getValueAt(row, 3).toString());
        txtSoLuong.setText(models.getValueAt(row, 4).toString());
        txtTonKho.setText(models.getValueAt(row, 5).toString());
        textAreaMoTa.setText(models.getValueAt(row, 6).toString());
        txtGiaNhap.setText(models.getValueAt(row, 8).toString());
        txtGiaBan.setText(models.getValueAt(row, 9).toString());
        txtNgayNhap.setText(models.getValueAt(row, 10).toString());
        cbxDanhMuc.setSelectedItem(models.getValueAt(row, 11).toString());
        
        String duongDan = models.getValueAt(row, 7).toString();
        try {
            file = new File(duongDan);
            bi = ImageIO.read(file);
          
            lbHinhAnh.setIcon(new ImageIcon(bi.getScaledInstance(lbHinhAnh.getWidth(), lbHinhAnh.getHeight(), Image.SCALE_SMOOTH)));
        } catch (NullPointerException e) {
            System.out.println("Đường dẫn lỗi: " + duongDan);
            System.out.println("Đường dẫn không đúng");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnTimSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimSanPhamMouseClicked
        // TODO add your handling code here:
        findSP();
    }//GEN-LAST:event_btnTimSanPhamMouseClicked

    private void txtTimKiemSPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemSPKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            findSP();
        }
    }//GEN-LAST:event_txtTimKiemSPKeyPressed

    private void txtTimSPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimSPKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            findSPBH();
        }
    }//GEN-LAST:event_txtTimSPKeyPressed

    private void txtMaSP2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaSP2KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            String maSP = txtMaSP.getText().toString();
            int rowCount = tblHoaDonBan.getRowCount();
            int tonKho = 0;
            String  sql = "SELECT ton_kho FROM san_pham where ma_sp = '"+ maSP +"' and ton_kho > 0";
            ResultSet rs = cls.excuteQueryGetTable(sql);
            
            try {
                rs.next();
                tonKho = rs.getInt("ton_kho");
            } catch (SQLException e) {
                System.err.println("Lỗi truy xuất database, lỗi: " + e);
            }
            
            
            
            if(tonKho != 0)
            {
                if(regex.checkTTSPBH(txtMaSP2.getText(), regex.slspbhc))
                {
                    int soLuong = Integer.parseInt(txtMaSP2.getText());
                 
                    if(soLuong > tonKho)
                    {
                        String message = "Số lượng không thể lớn hơn " + tonKho;
                        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(soLuong == 0)
                    {
                        for(int i = rowCount - 1; i >= 0; i--)
                        {
                            if(tblHoaDonBan.getValueAt(i, 0).equals(maSP))
                            {
                               ((DefaultTableModel)tblHoaDonBan.getModel()).removeRow(i);
                                break;
                            }
                        }
                        setNullBH();
                        rowCount = tblHoaDonBan.getRowCount();
                        calculateTotalPrice(rowCount);
                    }
                    else
                    {
                        for(int i = 0; i < rowCount; i++)
                        {
                            if(tblHoaDonBan.getValueAt(i, 0).equals(maSP))
                            {
                                float donGia = Float.parseFloat((String)tblSanPhamBH.getValueAt(i, 3));
                                double thanhTien = calculatePrice(soLuong, donGia);
                                tblHoaDonBan.setValueAt(soLuong, i, 3);
                                tblHoaDonBan.setValueAt(thanhTien, i, 5);
                                txtThanhTien.setText(String.valueOf(thanhTien));
                                break;
                            }
                        }
                        calculateTotalPrice(rowCount);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(this, regex.Mess, "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                String message = "Sản phẩm đã bán hết!";
                JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_txtMaSP2KeyPressed

    private void btnHuyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHuyMouseClicked
        ((DefaultTableModel)tblHoaDonBan.getModel()).setRowCount(0);
        setNullBH();
        int rowCount = tblHoaDonBan.getRowCount();
        calculateTotalPrice(rowCount);
    }//GEN-LAST:event_btnHuyMouseClicked

    private void btnLocBaoCaoBHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLocBaoCaoBHMouseClicked
         System.out.println(isValidDate("20/01/2014"));
         String sql = "SELECT sp.ma_sp,sp.ten_sp,ct.so_luong,ct.tong_tien, hd.ten_kh, nv.ten FROM hoa_don As hd, ct_hoa_don AS ct, san_pham as sp, nguoi_dung as nv WHERE hd.id=ct.ma_hd and ct.ma_sp = sp.id and nv.id = hd.ma_nv";
         if(isValidDate(txtTuNgayBCBH.getText().trim())  && isValidDate(txtDenNgayBCBH.getText().trim()))
         {
             int ma_cn = 0;
                ma_cn = cbxCCCN.get(cbxChiNanhBC.getSelectedIndex());
             
             
            sql += " and hd.ngay_tao >= '"+txtTuNgayBCBH.getText().trim()+"' AND  hd.ngay_tao <= '"+txtDenNgayBCBH.getText().trim()+"' and hd.ma_cn = "+ma_cn+"";
            ResultSet rshoadon = cls.excuteQueryGetTable(sql);
            
             try {
                  String header[] = {"STT","Mã sản phẩm", "Tên sản phẩm", "Số lượng","Tổng tiền","Tên kh","Tên nv"};
                    DefaultTableModel tblModel = new DefaultTableModel(header,0);
                    
                    tblModel.setRowCount(0);
                    int i = 1;
                 while (rshoadon.next()) {
                     Vector data = new Vector();
                    
                     data.add(i);
                     data.add(rshoadon.getString("ma_sp"));
                     data.add(rshoadon.getString("ten_sp"));
                     data.add(rshoadon.getInt("so_luong"));
                     data.add(rshoadon.getDouble("tong_tien"));
                     data.add(rshoadon.getString("ten_kh"));
                     data.add(rshoadon.getString("ten"));
                     i++;
                     tblModel.addRow(data);
                
            
                  }
                 tblBaoCaoBH.setModel(tblModel);
             } 
             catch (SQLException ex) {
                 Logger.getLogger(frmTrangChu.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
         else
         {
         JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng ngày dd/mm/yyyy", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
         }
    }//GEN-LAST:event_btnLocBaoCaoBHMouseClicked

    private void btnInBaoCaoBHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInBaoCaoBHMouseClicked
             DefaultTableModel model = (DefaultTableModel) tblBaoCaoBH.getModel();
            JFileChooser excelfilechooser = new JFileChooser("E:\\");
            excelfilechooser.setDialogTitle("Save as");
            FileNameExtensionFilter fnef = new FileNameExtensionFilter("Excel files","xls","xlsx","xlsm");
            excelfilechooser.setFileFilter(fnef);
           int excelchooser =  excelfilechooser.showSaveDialog(null);
            if(excelchooser == JFileChooser.APPROVE_OPTION)
            {
                XSSFWorkbook excelJtableExporter = new XSSFWorkbook();
                XSSFSheet  excelSheet = excelJtableExporter.createSheet("Bao cao");
               for(int i = 0 ;i<model.getRowCount();i++)
               {
                   XSSFRow excelRow = excelSheet.createRow(i);
                   for(int j=0;j<model.getColumnCount();j++)
                   {
                       XSSFCell excelCell = excelRow.createCell(j);
                       excelCell.setCellValue("a");
                   }
               }
                 try {
                     FileOutputStream excelFOP = new FileOutputStream(excelfilechooser.getSelectedFile() + ".xlsx");
                 BufferedOutputStream excelBOP = new BufferedOutputStream(excelFOP);
                 excelJtableExporter.write(excelBOP);
                 } catch (FileNotFoundException ex) {
                     Logger.getLogger(frmTrangChu.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (IOException ex) {
                     Logger.getLogger(frmTrangChu.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }   
            
            
            
            
    }//GEN-LAST:event_btnInBaoCaoBHMouseClicked

    private void tblChiNhanhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiNhanhMouseClicked
        int row = tblChiNhanh.rowAtPoint(evt.getPoint());
        
        
        DefaultTableModel models = (DefaultTableModel)tblChiNhanh.getModel();
        txtMaChiNhanh.setText(models.getValueAt(row, 1).toString());
        txtTenChiNhanh.setText(models.getValueAt(row, 2).toString());
        txtSdtChiNhanh.setText(models.getValueAt(row, 3).toString());
        txtDiaChiChiNhanh.setText(models.getValueAt(row, 4).toString());
    }//GEN-LAST:event_tblChiNhanhMouseClicked

    private void btnThemChiNhanh3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemChiNhanh3MouseClicked
        String sql = "INSERT  INTO  chi_nhanh ([ma_cn], [ten_cn],[sdt],[dia_chi]) VALUES ('"+txtMaChiNhanh.getText()+"',N'"+txtTenChiNhanh.getText()+"','"+txtSdtChiNhanh.getText()+"',N'"+txtDiaChiChiNhanh.getText()+"')";
            cls.excuteQueryUpdateDB(sql);
            JOptionPane.showMessageDialog(this, "Thêm danh muc thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
           LoadChiNhanh();
    }//GEN-LAST:event_btnThemChiNhanh3MouseClicked

    private void btnSuaChiNhanhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaChiNhanhMouseClicked
        try {
            int row = tblChiNhanh.getSelectedRow();
        
            DefaultTableModel models = (DefaultTableModel)tblChiNhanh.getModel();
            int id = (int)models.getValueAt(row, 0);
            String sql = "UPDATE chi_nhanh SET ma_cn = '"+txtMaChiNhanh.getText()+"', ten_cn = N'"+txtTenChiNhanh.getText()+"', sdt = N'"+txtSdtChiNhanh.getText()+"',dia_chi = N'"+txtDiaChiChiNhanh.getText()+"'  WHERE id = '"+id+"'";
            cls.excuteQueryUpdateDB(sql);
            JOptionPane.showMessageDialog(this, "Sửa chi nhánh "+txtMaChiNhanh.getText()+" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
            LoadChiNhanh();
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chi nhánh cần sửa", "Thông báo", JOptionPane.ERROR_MESSAGE); 
        }
    }//GEN-LAST:event_btnSuaChiNhanhMouseClicked

    private void btnXoaChiNhanhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaChiNhanhMouseClicked
        try {
             int row = tblChiNhanh.getSelectedRow();
        
            DefaultTableModel models = (DefaultTableModel)tblChiNhanh.getModel(); 
            String sql = "DELETE FROM chi_nhanh WHERE id = "+(int)models.getValueAt(row, 0)+"";
            cls.excuteQueryUpdateDB(sql);
            JOptionPane.showMessageDialog(this, "Xóa chi nhánh "+models.getValueAt(row, 1)+" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
            LoadChiNhanh();
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chi nhánh cần xóa", "Thông báo", JOptionPane.ERROR_MESSAGE); 
        }
    }//GEN-LAST:event_btnXoaChiNhanhMouseClicked

    private void tblHoaDonBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonBanMouseClicked
        // TODO add your handling code here:
        int row = tblChiNhanh.rowAtPoint(evt.getPoint());
        bindingValuesBH(row);
    }//GEN-LAST:event_tblHoaDonBanMouseClicked

    private void tblNhaCCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhaCCMouseClicked
        int row = tblNhaCC.rowAtPoint(evt.getPoint());
        
        
        DefaultTableModel models = (DefaultTableModel)tblNhaCC.getModel();
        txtNhaCC.setText(models.getValueAt(row, 1).toString());
        txtTenNhaCC.setText(models.getValueAt(row, 2).toString());
        txtDiaChiNCC.setText(models.getValueAt(row, 3).toString());
        txtSdtNCC.setText(models.getValueAt(row, 4).toString());
        txtEmail.setText(models.getValueAt(row, 5).toString());
        txtTongTienNhap.setText(models.getValueAt(row, 6).toString());
    }//GEN-LAST:event_tblNhaCCMouseClicked

    private void btnThemNCCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemNCCMouseClicked
        String sql = "INSERT  INTO  nha_cung_cap ([ma_ncc], [ten_ncc],[sdt],[dia_chi],[email],[tong_tien_nhap]) VALUES ('"+txtNhaCC.getText()+"',N'"+txtTenNhaCC.getText()+"','"+txtSdtNCC.getText()+"',N'"+txtDiaChiNCC.getText()+"',N'"+txtEmail.getText()+"',"+txtTongTienNhap.getText()+")";
            cls.excuteQueryUpdateDB(sql);
            JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
           LoadNhaCC();
    }//GEN-LAST:event_btnThemNCCMouseClicked

    private void btnSuaNCCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaNCCMouseClicked
        try{
            int row = tblNhaCC.getSelectedRow();
        
            DefaultTableModel models = (DefaultTableModel)tblNhaCC.getModel();
            int id = (int)models.getValueAt(row, 0);
            String sql = "UPDATE nha_cung_cap SET ma_ncc = '"+txtNhaCC.getText()+"', ten_ncc = N'"+txtTenNhaCC.getText()+"', sdt = N'"+txtSdtNCC.getText()+"',dia_chi = N'"+txtDiaChiNCC.getText()+"',email = N'"+txtEmail.getText()+"',tong_tien_nhap = "+txtTongTienNhap.getText()+"  WHERE id = '"+id+"'";
            cls.excuteQueryUpdateDB(sql);
            JOptionPane.showMessageDialog(this, "Sửa nhà cung cấp "+models.getValueAt(row, 1)+" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
            LoadNhaCC();
        } catch(ArrayIndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần sửa", "Thông báo", JOptionPane.ERROR_MESSAGE); 
        }
    }//GEN-LAST:event_btnSuaNCCMouseClicked

    private void btnXoaNCCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaNCCMouseClicked
        try{
            int row = tblNhaCC.getSelectedRow();
        
            DefaultTableModel models = (DefaultTableModel)tblNhaCC.getModel(); 
            String sql = "DELETE FROM nha_cung_cap WHERE id = "+(int)models.getValueAt(row, 0)+"";
            cls.excuteQueryUpdateDB(sql);
            JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp "+models.getValueAt(row, 1)+" thành công", "Thông báo", JOptionPane.ERROR_MESSAGE); 
            LoadNhaCC();
        } catch(ArrayIndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần xóa", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
        }
    }//GEN-LAST:event_btnXoaNCCMouseClicked

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        int row = tblKhachHang.rowAtPoint(evt.getPoint());
        
        DefaultTableModel models = (DefaultTableModel)tblKhachHang.getModel();
        txtMaKH.setText(models.getValueAt(row, 1).toString());
        txtTenKH.setText(models.getValueAt(row, 2).toString());
        txtDiaChiKH.setText(models.getValueAt(row, 3).toString());
        txtSdtKH.setText(models.getValueAt(row, 4).toString());
        txtEmailKH.setText(models.getValueAt(row, 5).toString());
        txtTongTienMua.setText(models.getValueAt(row, 6).toString());
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void btnThemKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemKHMouseClicked
         String sql = "INSERT  INTO  khach_hang ([ma_kh], [ten_kh],[sdt],[dia_chi],[email],[tong_tien]) VALUES ('"+txtMaKH.getText()+"',N'"+txtTenKH.getText()+"','"+txtSdtKH.getText()+"',N'"+txtDiaChiKH.getText()+"',N'"+txtEmailKH.getText()+"',"+txtTongTienMua.getText()+")";
            cls.excuteQueryUpdateDB(sql);
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
           loadKhachHang();
    }//GEN-LAST:event_btnThemKHMouseClicked

    private void btnSuaKhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaKhMouseClicked
        try
        {
            int row = tblKhachHang.getSelectedRow();
        
            DefaultTableModel models = (DefaultTableModel)tblKhachHang.getModel();
            int id = (int)models.getValueAt(row, 0);
            String sql = "UPDATE khach_hang SET ma_kh = '"+txtMaKH.getText()+"', ten_kh = N'"+txtTenKH.getText()+"', sdt = N'"+txtSdtKH.getText()+"',dia_chi = N'"+txtDiaChiKH.getText()+"',email = N'"+txtEmailKH.getText()+"',tong_tien = "+txtTongTienMua.getText()+"  WHERE id = '"+id+"'";
            cls.excuteQueryUpdateDB(sql);
            JOptionPane.showMessageDialog(this, "Sửa danh muc "+txtMaChiNhanh.getText()+" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
            loadKhachHang();
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần sửa", "Thông báo", JOptionPane.ERROR_MESSAGE); 
        }
        
    }//GEN-LAST:event_btnSuaKhMouseClicked

    private void btnXoaKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaKHMouseClicked
        try
        {
            int row = tblKhachHang.getSelectedRow();
        
            DefaultTableModel models = (DefaultTableModel)tblKhachHang.getModel(); 
            String sql = "DELETE FROM khach_hang WHERE id = "+(int)models.getValueAt(row, 0)+"";
            cls.excuteQueryUpdateDB(sql);
            JOptionPane.showMessageDialog(this, "Xóa khách hàng "+models.getValueAt(row, 1)+" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
            loadKhachHang();
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa", "Thông báo", JOptionPane.ERROR_MESSAGE); 
        }
        
    }//GEN-LAST:event_btnXoaKHMouseClicked

    private void btnLocBaoCaoNHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLocBaoCaoNHMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLocBaoCaoNHMouseClicked

    private void btnInBaoCaoNHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInBaoCaoNHMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnInBaoCaoNHMouseClicked

    private void tblNguoiDungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguoiDungMouseClicked
       int row = tblNguoiDung.getSelectedRow();
        
        DefaultTableModel models = (DefaultTableModel)tblNguoiDung.getModel(); 
        String sql = "select * FROM nguoi_dung WHERE id = "+(int)models.getValueAt(row, 0)+"";
        ResultSet rs = cls.excuteQueryGetTable(sql);
        try {
            rs.next();
            txtTaiKhoan.setText(rs.getString("tai_khoan"));
            txtTen.setText(rs.getString("ten"));
            txtSoDienThoai.setText(rs.getString("sdt"));
            int cvindex = cbxCCChucvu.indexOf(rs.getInt("ma_cv"));
            int cnindex = cbxCCCN.indexOf(rs.getInt("ma_cn"));
            txtEmailND.setText(rs.getString("email"));
            txtDiaChi.setText(rs.getString("dia_chi"));
            cbGioiTinh.setSelectedItem(rs.getString("gioi_tinh"));
            cbxChiNanh.setSelectedIndex(cnindex);
            cbxChucVu.setSelectedIndex(cvindex);
        } catch (SQLException ex) {
            Logger.getLogger(frmTrangChu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_tblNguoiDungMouseClicked

    private void btnThemNguoiDungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemNguoiDungMouseClicked
         int  ma_cn = cbxCCCN.get(cbxChiNanh.getSelectedIndex());
         int  ma_cv = cbxCCChucvu.get(cbxChucVu.getSelectedIndex());
         String matKhauDaMaHoa = SHAHashing.getSHAHash("1");
        String sql = "INSERT  INTO  nguoi_dung ([tai_khoan], [mat_khau],[ten],[sdt],[email],[dia_chi],[gioi_tinh],[ma_cv],[ma_cn],[luong]) "
                + "VALUES ('"+txtTaiKhoan.getText()+"','"+matKhauDaMaHoa+"',N'"+txtTen.getText()+"','"+txtSoDienThoai.getText()+"','"+txtEmailND.getText()
                +"',N'"+txtDiaChi.getText()+"',N'"+cbGioiTinh.getSelectedItem()+"',N'"+ma_cv+"',N'"+ma_cn+"',40000)";
            cls.excuteQueryUpdateDB(sql);
            JOptionPane.showMessageDialog(this, "Thêm người dùng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
           LoadUserTable();
    }//GEN-LAST:event_btnThemNguoiDungMouseClicked

    private void btnSuaNguoiDungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaNguoiDungMouseClicked
        try {
            int row = tblNguoiDung.getSelectedRow();
        
            DefaultTableModel models = (DefaultTableModel)tblNguoiDung.getModel();
            int id = (int)models.getValueAt(row, 0);
            int  ma_cn = cbxCCCN.get(cbxChiNanh.getSelectedIndex());
          int  ma_cv = cbxCCChucvu.get(cbxChucVu.getSelectedIndex());
          String sql = "UPDATE nguoi_dung SET tai_khoan = '"+txtTaiKhoan.getText()+"', ten = N'"+txtTen.getText()+"',sdt = N'"+txtSoDienThoai.getText()
                  +"', email='"+txtEmailND.getText()+"', dia_chi=N'"+txtDiaChi.getText()+"',gioi_tinh=N'"+cbGioiTinh.getSelectedItem()
                  +"',ma_cv = "+ma_cv+",ma_cn = "+ma_cn+"  WHERE id = '"+id+"'";
                cls.excuteQueryUpdateDB(sql);
                JOptionPane.showMessageDialog(this, "Chỉnh sửa người dùng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
               LoadUserTable();
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người dùng cần sửa", "Thông báo", JOptionPane.ERROR_MESSAGE); 
        }
            
    }//GEN-LAST:event_btnSuaNguoiDungMouseClicked

    private void btnXoaNguoiDungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaNguoiDungMouseClicked
        try
        {
            int row = tblNguoiDung.getSelectedRow();
        
            DefaultTableModel models = (DefaultTableModel)tblNguoiDung.getModel(); 
            String sql = "DELETE FROM nguoi_dung WHERE id = "+(int)models.getValueAt(row, 0)+"";
            cls.excuteQueryUpdateDB(sql);
            JOptionPane.showMessageDialog(this, "Xóa người dùng "+models.getValueAt(row, 1)+" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
            LoadUserTable();
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người dùng cần xóa", "Thông báo", JOptionPane.ERROR_MESSAGE); 
        }
    }//GEN-LAST:event_btnXoaNguoiDungMouseClicked

    private void btnLayMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLayMatKhauActionPerformed
        // TODO add your handling code here:
        frmDoiMatKhau frmDMK = new frmDoiMatKhau();
        this.hide();
        frmDMK.show();
    }//GEN-LAST:event_btnLayMatKhauActionPerformed

    private void btnThemHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHDActionPerformed
        // TODO add your handling code here:
        int cnIndex = cbChiNhanh.getSelectedIndex();
        int nvIndex = cbNhanVien.getSelectedIndex();
        int khIndex = cbKhachHang.getSelectedIndex();
        String tenKH = (String)cbKhachHang.getSelectedItem();
        if(existsInDatabaseTable("hoa_don", "ma_hd", txtMaHoaDon.getText(), 1))
        {
            JOptionPane.showMessageDialog(this, "Mã hóa đơn đã tồn tại", "Thông báo", JOptionPane.ERROR_MESSAGE); 
        }
        else
        {
            if(regex.checkTTHD(txtGiamGiaHD.getText(),regex.gghdc) && regex.checkTTHD(txtTongTienHD.getText(),regex.tthdc))
            {
                String sql = "INSERT  INTO  hoa_don ([ma_hd], [ma_cn],[ma_nv],[ma_kh],[ten_kh],[ngay_tao], [giam_gia], [tong_tien]) VALUES "
                + "('"+txtMaHoaDon.getText()+"','"+cbxCCCN.get(cnIndex)+"','"+listNVID.get(nvIndex)+"','"
                +listKHID.get(khIndex)+"',N'"+tenKH+"','"+getToDay()+"','"+txtGiamGiaHD.getText()+"','"+txtTongTienHD.getText()+"')";
                cls.excuteQueryUpdateDB(sql);
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
                LoadHoaDon();
            }
            else
            {
                JOptionPane.showMessageDialog(this, regex.Mess, "Thông báo", JOptionPane.ERROR_MESSAGE); 
            }
        }
    }//GEN-LAST:event_btnThemHDActionPerformed

    private void btnXoaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHDActionPerformed
        // TODO add your handling code here:
        try {
            int row = tblHoaDon.getSelectedRow();
            DefaultTableModel models = (DefaultTableModel)tblHoaDon.getModel(); 
            String message = "Bạn có chắc muốn xóa hóa dơn này, điều này sẽ đồng thời xóa chi tiết hóa đơn liên quan đến hóa đơn này?";
            String tittle = "Xóa hóa đơn";
            int result = JOptionPane.showConfirmDialog(this, message, tittle, JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String sql = "SELECT * From ct_hoa_don WHERE ma_hd="+(int)models.getValueAt(row, 0);
                try {
                    ResultSet rs = cls.excuteQueryGetTable(sql);
                    while(rs.next())
                    {
                        int maSP = rs.getInt("ma_sp");
                        int soLuong = rs.getInt("so_luong");
                        sql = "select ton_kho from san_pham where id="+maSP;
                        rs = cls.excuteQueryGetTable(sql);
                        int tonKho = 0;
                        if(rs.next())
                            tonKho = rs.getInt("ton_kho");
                        int newTonKho = tonKho + soLuong;
                        updateRemainingQuantity(maSP, newTonKho);
                        sql = "DELETE FROM ct_hoa_don WHERE ma_hd = "+(int)models.getValueAt(row, 0)+"";
                        cls.excuteQueryUpdateDB(sql);       
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                sql = "DELETE FROM hoa_don WHERE id = "+(int)models.getValueAt(row, 0)+"";
                cls.excuteQueryUpdateDB(sql);
                JOptionPane.showMessageDialog(this, "Xóa hóa đơn "+models.getValueAt(row, 1)+" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
                loadSP();
                loadCTHD();
                LoadHoaDon();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần xóa", "Thông báo", JOptionPane.ERROR_MESSAGE); 
        }
        
    }//GEN-LAST:event_btnXoaHDActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        int row = tblHoaDon.rowAtPoint(evt.getPoint());
        int ma_hd = (int)tblHoaDon.getModel().getValueAt(row, 0);
        loadCTHD(ma_hd);
        bindingValuesHD(row);
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void tblCTHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCTHDMouseClicked
        // TODO add your handling code here:
        int row = tblCTHD.rowAtPoint(evt.getPoint());
        bindingValuesCTHD(row);
    }//GEN-LAST:event_tblCTHDMouseClicked

    private void btnDoiHinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiHinhActionPerformed
        // TODO add your handling code here:
        FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        JFileChooser filechooser = new JFileChooser();
        filechooser.setDialogTitle("Chọn hình ảnh");
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        filechooser.setFileFilter(imageFilter);
        
        int returnval = filechooser.showOpenDialog(this);
        if (returnval == JFileChooser.APPROVE_OPTION)
        {
            newFile = filechooser.getSelectedFile();
            try {
                // display the image in a Jlabel
                bi = ImageIO.read(newFile);
                System.out.println("Tên file: "+ newFile.getName());
                lbHinhAnh.setIcon(new ImageIcon(bi.getScaledInstance(lbHinhAnh.getWidth(), lbHinhAnh.getHeight(), Image.SCALE_SMOOTH)));
            } catch(IOException e) {
               e.printStackTrace();
            }
            this.pack();
        }
    }//GEN-LAST:event_btnDoiHinhActionPerformed

    private void btnSuaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaHDActionPerformed
        // TODO add your handling code here:
        int cnIndex = cbChiNhanh.getSelectedIndex();
        int nvIndex = cbNhanVien.getSelectedIndex();
        int khIndex = cbKhachHang.getSelectedIndex();
        String tenKH = (String)cbKhachHang.getSelectedItem();
        try {
            int row = tblHoaDon.getSelectedRow();
            DefaultTableModel models = (DefaultTableModel)tblHoaDon.getModel();
            int id = (int)models.getValueAt(row, 0);

            if(regex.checkTTHD(txtGiamGiaHD.getText(),regex.gghdc) && regex.checkTTHD(txtTongTienHD.getText(),regex.tthdc))
            {
                String sql = "UPDATE hoa_don "
                        + "SET ma_hd = '"+txtMaHoaDon.getText()+"', ma_cn = '"+cbxCCCN.get(cnIndex)+"', ma_nv = '"+listNVID.get(nvIndex)+"',"
                        + " ma_kh =  '"+listKHID.get(khIndex)+"', ten_kh = N'"+tenKH+"',"
                        + " giam_gia = '"+txtGiamGiaHD.getText()+"', tong_tien = '"+txtTongTienHD.getText()+"' "
                        + "WHERE id = '"+id+"'";

                cls.excuteQueryUpdateDB(sql);
                JOptionPane.showMessageDialog(this, "Sửa hóa đơn "+txtMaHoaDon.getText()+" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
                LoadHoaDon();
            }
            else
            {
               JOptionPane.showMessageDialog(this, regex.Mess, "Thông báo", JOptionPane.ERROR_MESSAGE); 
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần sửa", "Thông báo", JOptionPane.ERROR_MESSAGE); 
        }
    }//GEN-LAST:event_btnSuaHDActionPerformed

    private void cbSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSanPhamActionPerformed
        // TODO add your handling code here:
        String sql = "select gia_ban, ton_kho from san_pham where id="+listSPID.get(cbSanPham.getSelectedIndex())+"";
        ResultSet rs = cls.excuteQueryGetTable(sql);
        try {
            if(rs.next())
            {
                txtDonGiaCTHD.setText(String.valueOf(rs.getFloat("gia_ban")));
                tonKho = rs.getInt("ton_kho");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_cbSanPhamActionPerformed

    private void btnThemCTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemCTHDActionPerformed
        // TODO add your handling code here:
        String maHD = String.valueOf(listHDID.get(cbMaHoaDon.getSelectedIndex()));
        String maSP = String.valueOf(listSPID.get(cbSanPham.getSelectedIndex()));
        if(existsInDatabaseTable2("ct_hoa_don", "ma_hd", "ma_sp", maHD, maSP, 2, 2))
        {
            JOptionPane.showMessageDialog(this, "Hóa đơn "+cbMaHoaDon.getSelectedItem()+" đã tồn tại sản phẩm "+cbSanPham.getSelectedItem(), "Thông báo", JOptionPane.ERROR_MESSAGE); 
        }
        else
        {
            try {
                if(regex.checkTTSP(txtSoLuongCTHD.getText(),regex.slc) && regex.checkTTSP(txtDonGiaCTHD.getText(),regex.giac))
                {
                    int tonKho = 0;
                    String sql = "select ton_kho from san_pham where id="+listSPID.get(cbSanPham.getSelectedIndex());
                    ResultSet rs = cls.excuteQueryGetTable(sql);
                    if(rs.next())
                    {
                        tonKho = rs.getInt("ton_kho");
                    }
                    if(tonKho !=0)
                    {
                        if(Integer.parseInt(txtSoLuongCTHD.getText()) > tonKho)
                        {
                            JOptionPane.showMessageDialog(this, "Số lượng phải nhỏ hơn "+tonKho, "Thông báo", JOptionPane.ERROR_MESSAGE); 
                        }
                        else
                        {
                            double thanhTien = Integer.parseInt(txtSoLuongCTHD.getText()) * Float.parseFloat(txtDonGiaCTHD.getText());
                            sql = "INSERT  INTO  ct_hoa_don ([ma_hd], [ma_sp], [so_luong], [don_gia], [tong_tien]) VALUES "
                            + "('"+listHDID.get(cbMaHoaDon.getSelectedIndex())+"','"+listSPID.get(cbSanPham.getSelectedIndex())+"','"+txtSoLuongCTHD.getText()+"','"
                            +txtDonGiaCTHD.getText()+"','"+thanhTien+"')";
                            cls.excuteQueryUpdateDB(sql);
                            updateTotalPriceHD(listHDID.get(cbMaHoaDon.getSelectedIndex()));
                            int newTonKho = tonKho - Integer.parseInt(txtSoLuongCTHD.getText());
                            updateRemainingQuantity(listSPID.get(cbSanPham.getSelectedIndex()), newTonKho);
                            JOptionPane.showMessageDialog(this, "Thêm chi tiết hóa đơn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
                            loadCTHD(listHDID.get(cbMaHoaDon.getSelectedIndex()));
                            LoadHoaDon();
                            loadSP();
                        }
                    }
                    else
                    {
                        String message = "Sản phẩm đã bán hết!";
                        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(this, regex.Mess, "Thông báo", JOptionPane.ERROR_MESSAGE); 
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnThemCTHDActionPerformed

    private void btnXoaCTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaCTHDActionPerformed
        // TODO add your handling code here:
        String message = "Bạn có chắc muốn xóa chi tiết hóa dơn này?";
        String tittle = "Xóa chi tiết hóa đơn";
        int result = JOptionPane.showConfirmDialog(this, message, tittle, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try
            {
                int row = tblCTHD.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) tblCTHD.getModel();
                int tonKho = 0;
                int SPID = 0;
                int HDID = 0;
                String sql = "select id, ton_kho from san_pham where ten_sp=N'"+model.getValueAt(row, 1)+"'";
                try
                {
                    ResultSet rs = cls.excuteQueryGetTable(sql);
                    if(rs.next())
                    {
                        tonKho = rs.getInt("ton_kho");
                        SPID = rs.getInt("id");
                    }

                    sql = "select id from hoa_don where ma_hd='"+model.getValueAt(row, 0)+"'";
                    rs = cls.excuteQueryGetTable(sql);
                    if(rs.next())
                    {
                        HDID = rs.getInt("id");
                    }
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }

                sql = "DELETE FROM ct_hoa_don WHERE ma_hd = "+HDID+" AND ma_sp = "+SPID;
                cls.excuteQueryUpdateDB(sql);
                updateTotalPriceHD(HDID);
                int newTonKho = tonKho + Integer.parseInt(txtSoLuongCTHD.getText());
                updateRemainingQuantity(SPID, newTonKho);
                JOptionPane.showMessageDialog(this, "Xóa chi tiết hóa đơn "+cbMaHoaDon.getSelectedItem()+" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
                loadCTHD(HDID);
                LoadHoaDon();
                loadSP();
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn chi tiết hóa đơn cần xóa", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
            }
            
        }
    }//GEN-LAST:event_btnXoaCTHDActionPerformed

    private void btnSuaCTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaCTHDActionPerformed
        // TODO add your handling code here:
        int row = tblCTHD.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tblCTHD.getModel();
        String maHD = String.valueOf(listHDID.get(cbMaHoaDon.getSelectedIndex()));
        String maSP = String.valueOf(listSPID.get(cbSanPham.getSelectedIndex()));
        if(cbMAPN.getSelectedItem().equals(model.getValueAt(row, 0)))
        {
            if(cbSanPhamNH.getSelectedItem().equals(model.getValueAt(row, 1)))
            {
                try {
                    if(regex.checkTTSP(txtSoLuongCTHD.getText(),regex.slc) && regex.checkTTSP(txtDonGiaCTHD.getText(),regex.giac))
                    {
                        int tonKho1 = 0;
                        int SPID = 0;
                        int HDID = 0;
                        String sql = "select id, ton_kho from san_pham where ten_sp=N'"+model.getValueAt(row, 1)+"'";
                        ResultSet rs = cls.excuteQueryGetTable(sql);
                        if(rs.next())
                        {
                            tonKho1 = rs.getInt("ton_kho");
                            SPID = rs.getInt("id");
                        }
                        sql = "select id from hoa_don where ma_hd='"+model.getValueAt(row, 0)+"'";
                        rs = cls.excuteQueryGetTable(sql);
                        if(rs.next())
                        {
                            HDID = rs.getInt("id");
                        }
                        if(tonKho1 !=0 && tonKho !=0)
                        {
                            if(Integer.parseInt(txtSoLuongCTHD.getText()) > tonKho)
                            {
                                JOptionPane.showMessageDialog(this, "Số lượng phải nhỏ hơn "+tonKho, "Thông báo", JOptionPane.ERROR_MESSAGE); 
                            }
                            else
                            {
                                double thanhTien = Integer.parseInt(txtSoLuongCTHD.getText()) * Float.parseFloat(txtDonGiaCTHD.getText());
                                sql = "UPDATE ct_hoa_don SET ma_hd="+listHDID.get(cbMaHoaDon.getSelectedIndex())+", "
                                        + "ma_sp="+listSPID.get(cbSanPham.getSelectedIndex())+", so_luong="+txtSoLuongCTHD.getText()+", "
                                        + "don_gia="+txtDonGiaCTHD.getText()+", tong_tien="+thanhTien+" "
                                        + "WHERE ma_hd="+HDID+" AND ma_sp="+SPID;
                                cls.excuteQueryUpdateDB(sql);
                                updateTotalPriceHD(HDID);
                                updateTotalPriceHD(listHDID.get(cbMaHoaDon.getSelectedIndex()));
                                int newTonKho1 = tonKho1 + (int)model.getValueAt(row, 2);
                                int newTonKho2 = tonKho - Integer.parseInt(txtSoLuongCTHD.getText());
                                updateRemainingQuantity(SPID, newTonKho1);
                                updateRemainingQuantity(listSPID.get(cbSanPham.getSelectedIndex()), newTonKho1);
                                JOptionPane.showMessageDialog(this, "Sửa chi tiết hóa đơn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
                                loadCTHD(listHDID.get(cbMaHoaDon.getSelectedIndex()));
                                LoadHoaDon();
                                loadSP();
                            }
                        }
                        else
                        {
                            String message = "Sản phẩm đã bán hết!";
                            JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }

                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, regex.Mess, "Thông báo", JOptionPane.ERROR_MESSAGE); 
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException e) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn chi tiết hóa dơn cần sửa", "Thông báo", JOptionPane.ERROR_MESSAGE); 
                }
            }
            else
            {
                if(existsInDatabaseTable2("ct_hoa_don", "ma_hd", "ma_sp", maHD, maSP, 2, 2))
                {
                    JOptionPane.showMessageDialog(this, "Hóa đơn "+cbMaHoaDon.getSelectedItem()+" đã tồn tại sản phẩm "+cbSanPham.getSelectedItem(), "Thông báo", JOptionPane.ERROR_MESSAGE); 
                }
                else
                {
                    try {
                        if(regex.checkTTSP(txtSoLuongCTHD.getText(),regex.slc) && regex.checkTTSP(txtDonGiaCTHD.getText(),regex.giac))
                        {
                            int tonKho1 = 0;
                            int SPID = 0;
                            int HDID = 0;
                            String sql = "select id, ton_kho from san_pham where ten_sp=N'"+model.getValueAt(row, 1)+"'";
                            ResultSet rs = cls.excuteQueryGetTable(sql);
                            if(rs.next())
                            {
                                tonKho1 = rs.getInt("ton_kho");
                                SPID = rs.getInt("id");
                            }
                            sql = "select id from hoa_don where ma_hd='"+model.getValueAt(row, 0)+"'";
                            rs = cls.excuteQueryGetTable(sql);
                            if(rs.next())
                            {
                                HDID = rs.getInt("id");
                            }
                            if(tonKho1 !=0 && tonKho !=0)
                            {
                                if(Integer.parseInt(txtSoLuongCTHD.getText()) > tonKho)
                                {
                                    JOptionPane.showMessageDialog(this, "Số lượng phải nhỏ hơn "+tonKho, "Thông báo", JOptionPane.ERROR_MESSAGE); 
                                }
                                else
                                {
                                    double thanhTien = Integer.parseInt(txtSoLuongCTHD.getText()) * Float.parseFloat(txtDonGiaCTHD.getText());
                                    sql = "UPDATE ct_hoa_don SET ma_hd="+listHDID.get(cbMaHoaDon.getSelectedIndex())+", "
                                            + "ma_sp="+listSPID.get(cbSanPham.getSelectedIndex())+", so_luong="+txtSoLuongCTHD.getText()+", "
                                            + "don_gia="+txtDonGiaCTHD.getText()+", tong_tien="+thanhTien+" "
                                            + "WHERE ma_hd="+HDID+" AND ma_sp="+SPID;
                                    cls.excuteQueryUpdateDB(sql);
                                    updateTotalPriceHD(HDID);
                                    updateTotalPriceHD(listHDID.get(cbMaHoaDon.getSelectedIndex()));
                                    int newTonKho1 = tonKho1 + (int)model.getValueAt(row, 2);
                                    int newTonKho2 = tonKho - Integer.parseInt(txtSoLuongCTHD.getText());
                                    updateRemainingQuantity(SPID, newTonKho1);
                                    updateRemainingQuantity(listSPID.get(cbSanPham.getSelectedIndex()), newTonKho1);
                                    JOptionPane.showMessageDialog(this, "Sửa chi tiết hóa đơn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
                                    loadCTHD(listHDID.get(cbMaHoaDon.getSelectedIndex()));
                                    LoadHoaDon();
                                    loadSP();
                                }
                            }
                            else
                            {
                                String message = "Sản phẩm đã bán hết!";
                                JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            }

                        }
                        else
                        {
                            JOptionPane.showMessageDialog(this, regex.Mess, "Thông báo", JOptionPane.ERROR_MESSAGE); 
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn chi tiết hóa dơn cần sửa", "Thông báo", JOptionPane.ERROR_MESSAGE); 
                    }
                }
            }
        }
        else
        {
            if(existsInDatabaseTable2("ct_hoa_don", "ma_hd", "ma_sp", maHD, maSP, 2, 2))
            {
                JOptionPane.showMessageDialog(this, "Hóa đơn "+cbMaHoaDon.getSelectedItem()+" đã tồn tại sản phẩm "+cbSanPham.getSelectedItem(), "Thông báo", JOptionPane.ERROR_MESSAGE); 
            }
            else
            {
                try {
                    if(regex.checkTTSP(txtSoLuongCTHD.getText(),regex.slc) && regex.checkTTSP(txtDonGiaCTHD.getText(),regex.giac))
                    {
                        int tonKho1 = 0;
                        int SPID = 0;
                        int HDID = 0;
                        String sql = "select id, ton_kho from san_pham where ten_sp=N'"+model.getValueAt(row, 1)+"'";
                        ResultSet rs = cls.excuteQueryGetTable(sql);
                        if(rs.next())
                        {
                            tonKho1 = rs.getInt("ton_kho");
                            SPID = rs.getInt("id");
                        }
                        sql = "select id from hoa_don where ma_hd='"+model.getValueAt(row, 0)+"'";
                        rs = cls.excuteQueryGetTable(sql);
                        if(rs.next())
                        {
                            HDID = rs.getInt("id");
                        }
                        if(tonKho1 !=0 && tonKho !=0)
                        {
                            if(Integer.parseInt(txtSoLuongCTHD.getText()) > tonKho)
                            {
                                JOptionPane.showMessageDialog(this, "Số lượng phải nhỏ hơn "+tonKho, "Thông báo", JOptionPane.ERROR_MESSAGE); 
                            }
                            else
                            {
                                double thanhTien = Integer.parseInt(txtSoLuongCTHD.getText()) * Float.parseFloat(txtDonGiaCTHD.getText());
                                sql = "UPDATE ct_hoa_don SET ma_hd="+listHDID.get(cbMaHoaDon.getSelectedIndex())+", "
                                        + "ma_sp="+listSPID.get(cbSanPham.getSelectedIndex())+", so_luong="+txtSoLuongCTHD.getText()+", "
                                        + "don_gia="+txtDonGiaCTHD.getText()+", tong_tien="+thanhTien+" "
                                        + "WHERE ma_hd="+HDID+" AND ma_sp="+SPID;
                                cls.excuteQueryUpdateDB(sql);
                                updateTotalPriceHD(HDID);
                                updateTotalPriceHD(listHDID.get(cbMaHoaDon.getSelectedIndex()));
                                int newTonKho1 = tonKho1 + (int)model.getValueAt(row, 2);
                                int newTonKho2 = tonKho - Integer.parseInt(txtSoLuongCTHD.getText());
                                updateRemainingQuantity(SPID, newTonKho1);
                                updateRemainingQuantity(listSPID.get(cbSanPham.getSelectedIndex()), newTonKho1);
                                JOptionPane.showMessageDialog(this, "Sửa chi tiết hóa đơn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
                                loadCTHD(listHDID.get(cbMaHoaDon.getSelectedIndex()));
                                LoadHoaDon();
                                loadSP();
                            }
                        }
                        else
                        {
                            String message = "Sản phẩm đã bán hết!";
                            JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }

                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, regex.Mess, "Thông báo", JOptionPane.ERROR_MESSAGE); 
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException e) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn chi tiết hóa dơn cần sửa", "Thông báo", JOptionPane.ERROR_MESSAGE); 
                }
            }
        }
    }//GEN-LAST:event_btnSuaCTHDActionPerformed

    private void btnThemHDNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHDNHActionPerformed
        // TODO add your handling code here:
        int cnIndex = cbChiNhanhNH.getSelectedIndex();
        int nvIndex = cbNhanVienNH.getSelectedIndex();
        int nccIndex = cbNCCNH.getSelectedIndex();
        
        if(existsInDatabaseTable("phieu_nhap", "ma_pn", txtMaPhieuNHap.getText(), 1))
        {
            JOptionPane.showMessageDialog(this, "Mã phiếu nhập đã tồn tại", "Thông báo", JOptionPane.ERROR_MESSAGE); 
        }
        else
        {
            if(regex.checkTTHD(txtTongTienHD2.getText(),regex.tthdc))
            {
                String sql = "INSERT  INTO  phieu_nhap ([ma_pn],[ma_cn],[ma_nv],[ma_ncc],[ngay_nhap], [tong_tien]) VALUES "
                + "('"+txtMaPhieuNHap.getText()+"','"+cbxCCCN.get(cnIndex)+"','"+listNVID.get(nvIndex)+"','"
                +listNCCID.get(nccIndex)+"','"+getToDay()+"','"+txtTongTienHD.getText()+"')";
                cls.excuteQueryUpdateDB(sql);
                JOptionPane.showMessageDialog(this, "Thêm phiếu nhập thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
                LoadPhieuNhap();
            }
            else
            {
                JOptionPane.showMessageDialog(this, regex.Mess, "Thông báo", JOptionPane.ERROR_MESSAGE); 
            }
        }
    }//GEN-LAST:event_btnThemHDNHActionPerformed

    private void tblHoaDonNHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonNHMouseClicked
        // TODO add your handling code here:
        int row = tblHoaDonNH.rowAtPoint(evt.getPoint());
        int ma_pn = (int)tblHoaDonNH.getModel().getValueAt(row, 0);
        loadCTPN(ma_pn);
        bindingValuesPN(row);
    }//GEN-LAST:event_tblHoaDonNHMouseClicked

    private void btnXoaHDNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHDNHActionPerformed
        // TODO add your handling code here:
        try
        {
            int row = tblHoaDonNH.getSelectedRow();
            DefaultTableModel models = (DefaultTableModel)tblHoaDonNH.getModel(); 
            String message = "Bạn có chắc muốn xóa phiếu nhập này, điều này sẽ đồng thời xóa chi tiết phiếu nhập liên quan đến phiếu nhập này?";
            String tittle = "Xóa phiếu nhập";
            int result = JOptionPane.showConfirmDialog(this, message, tittle, JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String sql = "SELECT * From ct_phieu_nhap WHERE ma_pn="+(int)models.getValueAt(row, 0);
                try {
                    ResultSet rs = cls.excuteQueryGetTable(sql);
                    while(rs.next())
                    {
                        int maSP = rs.getInt("ma_sp");
                        int soLuong = rs.getInt("so_luong_nhap");
                        sql = "select ton_kho from san_pham where id="+maSP;
                        rs = cls.excuteQueryGetTable(sql);
                        int tonKho = 0;
                        if(rs.next())
                            tonKho = rs.getInt("ton_kho");
                        int newTonKho = tonKho - soLuong;
                        updateRemainingQuantity(maSP, newTonKho);
                        sql = "DELETE FROM ct_phieu_nhap WHERE ma_pn = "+(int)models.getValueAt(row, 0)+"";
                        cls.excuteQueryUpdateDB(sql);       
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                sql = "DELETE FROM phieu_nhap WHERE id = "+(int)models.getValueAt(row, 0)+"";
                cls.excuteQueryUpdateDB(sql);
                JOptionPane.showMessageDialog(this, "Xóa phiếu nhập "+models.getValueAt(row, 1)+" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
                loadSP();
                loadCTPN();
                LoadPhieuNhap();
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần xóa", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnXoaHDNHActionPerformed

    private void btnSuaHDNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaHDNHActionPerformed
        // TODO add your handling code here:
        int cnIndex = cbChiNhanhNH.getSelectedIndex();
        int nvIndex = cbNhanVienNH.getSelectedIndex();
        int nccIndex = cbNCCNH.getSelectedIndex();
        
        try {
            int row = tblHoaDonNH.getSelectedRow();
            DefaultTableModel models = (DefaultTableModel)tblHoaDonNH.getModel();
            int id = (int)models.getValueAt(row, 0);

            if(regex.checkTTHD(txtTongTienHD2.getText(),regex.tthdc))
            {
                String sql = "UPDATE phieu_nhap "
                        + "SET ma_pn = '"+txtMaPhieuNHap.getText()+"', ma_cn = '"+cbxCCCN.get(cnIndex)+"', ma_nv = '"+listNVID.get(nvIndex)+"',"
                        + " ma_ncc =  '"+listNCCID.get(nccIndex)+"',"
                        + " tong_tien = '"+txtTongTienHD2.getText()+"' "
                        + "WHERE id = '"+id+"'";

                cls.excuteQueryUpdateDB(sql);
                JOptionPane.showMessageDialog(this, "Sửa phiếu nhập "+txtMaPhieuNHap.getText()+" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
                LoadPhieuNhap();
            }
            else
            {
               JOptionPane.showMessageDialog(this, regex.Mess, "Thông báo", JOptionPane.ERROR_MESSAGE); 
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần sửa", "Thông báo", JOptionPane.ERROR_MESSAGE); 
        }
    }//GEN-LAST:event_btnSuaHDNHActionPerformed

    private void btnThemCTHDNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemCTHDNHActionPerformed
        // TODO add your handling code here:
        String maPN = String.valueOf(listPNID.get(cbMAPN.getSelectedIndex()));
        String maSP = String.valueOf(listSPID.get(cbSanPhamNH.getSelectedIndex()));
        if(existsInDatabaseTable2("ct_phieu_nhap", "ma_pn", "ma_sp", maPN, maSP, 2, 2))
        {
            JOptionPane.showMessageDialog(this, "Phiếu nhập "+cbMAPN.getSelectedItem()+" đã tồn tại sản phẩm "+cbSanPhamNH.getSelectedItem(), "Thông báo", JOptionPane.ERROR_MESSAGE); 
        }
        else
        {
            try {
                if(regex.checkTTSP(txtSoLuongCTHD2.getText(),regex.slc) && regex.checkTTSP(txtDonGiaCTHD2.getText(),regex.giac))
                {
                    int tonKho = 0;
                    int soLuong = 0;
                    String sql = "select ton_kho, so_luong from san_pham where id="+listSPID.get(cbSanPham.getSelectedIndex());
                    ResultSet rs = cls.excuteQueryGetTable(sql);
                    if(rs.next())
                    {
                        tonKho = rs.getInt("ton_kho");
                        soLuong = rs.getInt("so_luong");
                    }
                    
                    double thanhTien = Integer.parseInt(txtSoLuongCTHD2.getText()) * Float.parseFloat(txtDonGiaCTHD2.getText());
                    sql = "INSERT  INTO  ct_phieu_nhap ([ma_pn], [ma_sp], [so_luong_nhap], [don_gia], [tong_tien]) VALUES "
                    + "('"+listPNID.get(cbMAPN.getSelectedIndex())+"','"+listSPID.get(cbSanPhamNH.getSelectedIndex())+"','"+txtSoLuongCTHD2.getText()+"','"
                    +txtDonGiaCTHD2.getText()+"','"+thanhTien+"')";
                    cls.excuteQueryUpdateDB(sql);
                    updateTotalPricePN(listPNID.get(cbMAPN.getSelectedIndex()));
                    int newTonKho = tonKho + Integer.parseInt(txtSoLuongCTHD2.getText());
                    int newSoLuong = soLuong + Integer.parseInt(txtSoLuongCTHD2.getText());
                    updateSP(listSPID.get(cbSanPhamNH.getSelectedIndex()), newTonKho, newSoLuong, Float.parseFloat(txtDonGiaCTHD2.getText()));
                    JOptionPane.showMessageDialog(this, "Thêm chi tiết phiếu nhập thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
                    loadCTPN(listPNID.get(cbMAPN.getSelectedIndex()));
                    LoadPhieuNhap();
                    loadSP();
                }
                else
                {
                    JOptionPane.showMessageDialog(this, regex.Mess, "Thông báo", JOptionPane.ERROR_MESSAGE); 
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnThemCTHDNHActionPerformed

    private void btnXoaCTHDNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaCTHDNHActionPerformed
        // TODO add your handling code here:
        String message = "Bạn có chắc muốn xóa chi tiết phiếu nhập này?";
        String tittle = "Xóa chi tiết phiếu nhập";
        int result = JOptionPane.showConfirmDialog(this, message, tittle, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try
            {
                int row = tblCTHDNH.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) tblCTHDNH.getModel();
                int tonKho = 0;
                int soLuong = 0;
                int SPID = 0;
                int PNID = 0;
                String sql = "select id, ton_kho, so_luong from san_pham where ten_sp=N'"+model.getValueAt(row, 1)+"'";
                ResultSet rs = cls.excuteQueryGetTable(sql);
                try
                {
                    if(rs.next())
                    {
                        tonKho = rs.getInt("ton_kho");
                        SPID = rs.getInt("id");
                        soLuong = rs.getInt("so_luong");
                    }

                    sql = "select id from phieu_nhap where ma_pn='"+model.getValueAt(row, 0)+"'";
                    rs = cls.excuteQueryGetTable(sql);
                    if(rs.next())
                    {
                        PNID = rs.getInt("id");
                    }
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }

                sql = "DELETE FROM ct_phieu_nhap WHERE ma_pn = "+PNID+" AND ma_sp = "+SPID;
                cls.excuteQueryUpdateDB(sql);
                updateTotalPricePN(PNID);
                int newTonKho = tonKho - Integer.parseInt(txtSoLuongCTHD2.getText());
                int newSoLuong = soLuong - Integer.parseInt(txtSoLuongCTHD2.getText());
                updateSP(SPID, newTonKho, newSoLuong, Float.parseFloat(txtDonGiaCTHD2.getText()));
                JOptionPane.showMessageDialog(this, "Xóa chi tiết phiếu nhập "+cbMAPN.getSelectedItem()+" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
                loadCTPN(PNID);
                LoadPhieuNhap();
                loadSP();
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn chi tiết phiếu nhập cần xóa", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
            }
        }
    }//GEN-LAST:event_btnXoaCTHDNHActionPerformed

    private void tblCTHDNHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCTHDNHMouseClicked
        // TODO add your handling code here:
        int row = tblCTHDNH.rowAtPoint(evt.getPoint());
        bindingValuesCTPN(row);
    }//GEN-LAST:event_tblCTHDNHMouseClicked

    private void btnSuaCTHDNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaCTHDNHActionPerformed
        // TODO add your handling code here:
        int row = tblCTHDNH.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tblCTHDNH.getModel();
        String maPN = String.valueOf(listPNID.get(cbMAPN.getSelectedIndex()));
        String maSP = String.valueOf(listSPID.get(cbSanPhamNH.getSelectedIndex()));
        if(cbMAPN.getSelectedItem().equals(model.getValueAt(row, 0)))
        {
            if(cbSanPhamNH.getSelectedItem().equals(model.getValueAt(row, 1)))
            {
                try {
                    if(regex.checkTTSP(txtSoLuongCTHD2.getText(),regex.slc) && regex.checkTTSP(txtDonGiaCTHD2.getText(),regex.giac))
                    {
                        int tonKho1 = 0;
                        int soLuong1 = 0;
                        int SPID = 0;
                        int PNID = 0;
                        String sql = "select id, ton_kho, so_luong from san_pham where ten_sp=N'"+model.getValueAt(row, 1)+"'";
                        ResultSet rs = cls.excuteQueryGetTable(sql);
                        if(rs.next())
                        {
                            tonKho1 = rs.getInt("ton_kho");
                            soLuong1 = rs.getInt("so_luong");
                            SPID = rs.getInt("id");
                        }
                        sql = "select id from phieu_nhap where ma_pn='"+model.getValueAt(row, 0)+"'";
                        rs = cls.excuteQueryGetTable(sql);
                        if(rs.next())
                        {
                            PNID = rs.getInt("id");
                        }

                        double thanhTien = Integer.parseInt(txtSoLuongCTHD2.getText()) * Float.parseFloat(txtDonGiaCTHD2.getText());
                        sql = "UPDATE ct_phieu_nhap SET ma_pn="+listPNID.get(cbMAPN.getSelectedIndex())+", "
                                + "ma_sp="+listSPID.get(cbSanPhamNH.getSelectedIndex())+", so_luong_nhap="+txtSoLuongCTHD2.getText()+", "
                                + "don_gia="+txtDonGiaCTHD2.getText()+", tong_tien="+thanhTien+" "
                                + "WHERE ma_pn="+PNID+" AND ma_sp="+SPID;
                        cls.excuteQueryUpdateDB(sql);
                        updateTotalPricePN(PNID);
                        updateTotalPricePN(listPNID.get(cbMAPN.getSelectedIndex()));
                        int newTonKho1 = tonKho1 - (int)model.getValueAt(row, 2);
                        int newTonKho2 = tonKho + Integer.parseInt(txtSoLuongCTHD2.getText());
                        int newSoLuong1 = soLuong1 - (int)model.getValueAt(row, 2);
                        int newSoLuong2 = soLuong + Integer.parseInt(txtSoLuongCTHD2.getText());
                        updateSP1(SPID, newTonKho1, newSoLuong1);
                        updateSP(listSPID.get(cbSanPhamNH.getSelectedIndex()), newTonKho2, newSoLuong2, Float.parseFloat(txtDonGiaCTHD2.getText()));
                        JOptionPane.showMessageDialog(this, "Sửa chi tiết phiếu nhập thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
                        loadCTPN(listPNID.get(cbMAPN.getSelectedIndex()));
                        LoadPhieuNhap();
                        loadSP();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, regex.Mess, "Thông báo", JOptionPane.ERROR_MESSAGE); 
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn chi tiết phiếu nhập cần sửa", "Thông báo", JOptionPane.ERROR_MESSAGE); 
                } 
            }
            else
            {
                if(existsInDatabaseTable2("ct_phieu_nhap", "ma_pn", "ma_sp", maPN, maSP, 2, 2))
                {
                    JOptionPane.showMessageDialog(this, "Phiếu nhập "+cbMAPN.getSelectedItem()+" đã tồn tại sản phẩm "+cbSanPhamNH.getSelectedItem(), "Thông báo", JOptionPane.ERROR_MESSAGE); 
                }
                else
                {
                    try {
                        if(regex.checkTTSP(txtSoLuongCTHD2.getText(),regex.slc) && regex.checkTTSP(txtDonGiaCTHD2.getText(),regex.giac))
                        {
                            int tonKho1 = 0;
                            int soLuong1 = 0;
                            int SPID = 0;
                            int PNID = 0;
                            String sql = "select id, ton_kho, so_luong from san_pham where ten_sp=N'"+model.getValueAt(row, 1)+"'";
                            ResultSet rs = cls.excuteQueryGetTable(sql);
                            if(rs.next())
                            {
                                tonKho1 = rs.getInt("ton_kho");
                                soLuong1 = rs.getInt("so_luong");
                                SPID = rs.getInt("id");
                            }
                            sql = "select id from phieu_nhap where ma_pn='"+model.getValueAt(row, 0)+"'";
                            rs = cls.excuteQueryGetTable(sql);
                            if(rs.next())
                            {
                                PNID = rs.getInt("id");
                            }

                            double thanhTien = Integer.parseInt(txtSoLuongCTHD2.getText()) * Float.parseFloat(txtDonGiaCTHD2.getText());
                            sql = "UPDATE ct_phieu_nhap SET ma_pn="+listPNID.get(cbMAPN.getSelectedIndex())+", "
                                    + "ma_sp="+listSPID.get(cbSanPhamNH.getSelectedIndex())+", so_luong_nhap="+txtSoLuongCTHD2.getText()+", "
                                    + "don_gia="+txtDonGiaCTHD2.getText()+", tong_tien="+thanhTien+" "
                                    + "WHERE ma_pn="+PNID+" AND ma_sp="+SPID;
                            cls.excuteQueryUpdateDB(sql);
                            updateTotalPricePN(PNID);
                            updateTotalPricePN(listPNID.get(cbMAPN.getSelectedIndex()));
                            int newTonKho1 = tonKho1 - (int)model.getValueAt(row, 2);
                            int newTonKho2 = tonKho + Integer.parseInt(txtSoLuongCTHD2.getText());
                            int newSoLuong1 = soLuong1 - (int)model.getValueAt(row, 2);
                            int newSoLuong2 = soLuong + Integer.parseInt(txtSoLuongCTHD2.getText());
                            updateSP1(SPID, newTonKho1, newSoLuong1);
                            updateSP(listSPID.get(cbSanPhamNH.getSelectedIndex()), newTonKho2, newSoLuong2, Float.parseFloat(txtDonGiaCTHD2.getText()));
                            JOptionPane.showMessageDialog(this, "Sửa chi tiết phiếu nhập thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
                            loadCTPN(listPNID.get(cbMAPN.getSelectedIndex()));
                            LoadPhieuNhap();
                            loadSP();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(this, regex.Mess, "Thông báo", JOptionPane.ERROR_MESSAGE); 
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    catch (ArrayIndexOutOfBoundsException e) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn chi tiết phiếu nhập cần sửa", "Thông báo", JOptionPane.ERROR_MESSAGE); 
                    } 
                }
            }
        }
        else
        {
            if(existsInDatabaseTable2("ct_phieu_nhap", "ma_pn", "ma_sp", maPN, maSP, 2, 2))
            {
                JOptionPane.showMessageDialog(this, "Phiếu nhập "+cbMAPN.getSelectedItem()+" đã tồn tại sản phẩm "+cbSanPhamNH.getSelectedItem(), "Thông báo", JOptionPane.ERROR_MESSAGE); 
            }
            else
            {
                try {
                    if(regex.checkTTSP(txtSoLuongCTHD2.getText(),regex.slc) && regex.checkTTSP(txtDonGiaCTHD2.getText(),regex.giac))
                    {
                        int tonKho1 = 0;
                        int soLuong1 = 0;
                        int SPID = 0;
                        int PNID = 0;
                        String sql = "select id, ton_kho, so_luong from san_pham where ten_sp=N'"+model.getValueAt(row, 1)+"'";
                        ResultSet rs = cls.excuteQueryGetTable(sql);
                        if(rs.next())
                        {
                            tonKho1 = rs.getInt("ton_kho");
                            soLuong1 = rs.getInt("so_luong");
                            SPID = rs.getInt("id");
                        }
                        sql = "select id from phieu_nhap where ma_pn='"+model.getValueAt(row, 0)+"'";
                        rs = cls.excuteQueryGetTable(sql);
                        if(rs.next())
                        {
                            PNID = rs.getInt("id");
                        }

                        double thanhTien = Integer.parseInt(txtSoLuongCTHD2.getText()) * Float.parseFloat(txtDonGiaCTHD2.getText());
                        sql = "UPDATE ct_phieu_nhap SET ma_pn="+listPNID.get(cbMAPN.getSelectedIndex())+", "
                                + "ma_sp="+listSPID.get(cbSanPhamNH.getSelectedIndex())+", so_luong_nhap="+txtSoLuongCTHD2.getText()+", "
                                + "don_gia="+txtDonGiaCTHD2.getText()+", tong_tien="+thanhTien+" "
                                + "WHERE ma_pn="+PNID+" AND ma_sp="+SPID;
                        cls.excuteQueryUpdateDB(sql);
                        updateTotalPricePN(PNID);
                        updateTotalPricePN(listPNID.get(cbMAPN.getSelectedIndex()));
                        int newTonKho1 = tonKho1 - (int)model.getValueAt(row, 2);
                        int newTonKho2 = tonKho + Integer.parseInt(txtSoLuongCTHD2.getText());
                        int newSoLuong1 = soLuong1 - (int)model.getValueAt(row, 2);
                        int newSoLuong2 = soLuong + Integer.parseInt(txtSoLuongCTHD2.getText());
                        updateSP1(SPID, newTonKho1, newSoLuong1);
                        updateSP(listSPID.get(cbSanPhamNH.getSelectedIndex()), newTonKho2, newSoLuong2, Float.parseFloat(txtDonGiaCTHD2.getText()));
                        JOptionPane.showMessageDialog(this, "Sửa chi tiết phiếu nhập thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
                        loadCTPN(listPNID.get(cbMAPN.getSelectedIndex()));
                        LoadPhieuNhap();
                        loadSP();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, regex.Mess, "Thông báo", JOptionPane.ERROR_MESSAGE); 
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn chi tiết phiếu nhập cần sửa", "Thông báo", JOptionPane.ERROR_MESSAGE); 
                } 
            }
        }
    }//GEN-LAST:event_btnSuaCTHDNHActionPerformed

    private void cbSanPhamNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSanPhamNHActionPerformed
        // TODO add your handling code here:
        String sql = "select ton_kho, so_luong from san_pham where id="+listSPID.get(cbSanPham.getSelectedIndex())+"";
        ResultSet rs = cls.excuteQueryGetTable(sql);
        try {
            if(rs.next())
            {
                soLuong = rs.getInt("so_luong");
                tonKho = rs.getInt("ton_kho");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_cbSanPhamNHActionPerformed

    private void cbKhachHangBHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKhachHangBHActionPerformed
        // TODO add your handling code here:
        String sql = "select * from khach_hang where id="+listKHID.get(cbKhachHangBH.getSelectedIndex())+"";
        ResultSet rs = cls.excuteQueryGetTable(sql);
        try {
            if(rs.next())
            {
                khachHang = new KhachHang(rs.getInt("id"), rs.getString("ma_kh"), rs.getString("ten_kh"), rs.getString("email"), rs.getInt("sdt"), rs.getString("dia_chi"), rs.getFloat("tong_tien"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_cbKhachHangBHActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new frmTrangChu().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnDoiHinh;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnInBaoCaoBH;
    private javax.swing.JButton btnInBaoCaoNH;
    private javax.swing.JButton btnLayMatKhau;
    private javax.swing.JButton btnLocBaoCaoBH;
    private javax.swing.JButton btnLocBaoCaoNH;
    private javax.swing.JButton btnLuuHD;
    private javax.swing.JButton btnSuaCTHD;
    private javax.swing.JButton btnSuaCTHDNH;
    private javax.swing.JButton btnSuaChiNhanh;
    private javax.swing.JButton btnSuaDanhMuc;
    private javax.swing.JButton btnSuaHD;
    private javax.swing.JButton btnSuaHDNH;
    private javax.swing.JButton btnSuaKh;
    private javax.swing.JButton btnSuaNCC;
    private javax.swing.JButton btnSuaNguoiDung;
    private javax.swing.JButton btnSuaSanPham;
    private javax.swing.JButton btnThemCTHD;
    private javax.swing.JButton btnThemCTHDNH;
    private javax.swing.JButton btnThemChiNhanh3;
    private javax.swing.JButton btnThemDanhMuc;
    private javax.swing.JButton btnThemHD;
    private javax.swing.JButton btnThemHDNH;
    private javax.swing.JButton btnThemKH;
    private javax.swing.JButton btnThemKhachHang;
    private javax.swing.JButton btnThemNCC;
    private javax.swing.JButton btnThemNguoiDung;
    private javax.swing.JButton btnThemSanPham;
    private javax.swing.JButton btnTimSP;
    private javax.swing.JButton btnTimSanPham;
    private javax.swing.JButton btnXoaCTHD;
    private javax.swing.JButton btnXoaCTHDNH;
    private javax.swing.JButton btnXoaChiNhanh;
    private javax.swing.JButton btnXoaDanhMuc;
    private javax.swing.JButton btnXoaHD;
    private javax.swing.JButton btnXoaHDNH;
    private javax.swing.JButton btnXoaKH;
    private javax.swing.JButton btnXoaNCC;
    private javax.swing.JButton btnXoaNguoiDung;
    private javax.swing.JButton btnXoaSanPham;
    private javax.swing.JButton btnXuatCTHD;
    private javax.swing.JButton btnXuatCTHDNH;
    private javax.swing.JButton btnXuatChiNhanh;
    private javax.swing.JButton btnXuatDanhMuc;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JButton btnXuatHD;
    private javax.swing.JButton btnXuatHDNH;
    private javax.swing.JButton btnXuatKH;
    private javax.swing.JButton btnXuatNCC;
    private javax.swing.JButton btnXuatNguoiDung;
    private javax.swing.JComboBox<String> cbChiNhanh;
    private javax.swing.JComboBox<String> cbChiNhanhNH;
    private javax.swing.JComboBox<String> cbGioiTinh;
    private javax.swing.JComboBox<String> cbKhachHang;
    private javax.swing.JComboBox<String> cbKhachHangBH;
    private javax.swing.JComboBox<String> cbMAPN;
    private javax.swing.JComboBox<String> cbMaHoaDon;
    private javax.swing.JComboBox<String> cbNCCNH;
    private javax.swing.JComboBox<String> cbNhanVien;
    private javax.swing.JComboBox<String> cbNhanVienNH;
    private javax.swing.JComboBox<String> cbSanPham;
    private javax.swing.JComboBox<String> cbSanPhamNH;
    private javax.swing.JComboBox<String> cbxChiNanh;
    private javax.swing.JComboBox<String> cbxChiNanhBC;
    private javax.swing.JComboBox<String> cbxChiNanhBC1;
    private javax.swing.JComboBox<String> cbxChucVu;
    private javax.swing.JComboBox<String> cbxDanhMuc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelBCBanHang;
    private javax.swing.JPanel jPanelBCBanHang1;
    private javax.swing.JPanel jPanelBCXuatNhap;
    private javax.swing.JPanel jPanelBanHang;
    private javax.swing.JPanel jPanelBanHang2;
    private javax.swing.JPanel jPanelChiNhanh;
    private javax.swing.JPanel jPanelDMSanPham;
    private javax.swing.JPanel jPanelDSSanPham;
    private javax.swing.JPanel jPanelKhachHang;
    private javax.swing.JPanel jPanelNguoiDung;
    private javax.swing.JPanel jPanelNhaCungCap;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JScrollPane jScrollPaneBanHang;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTabbedPane jTabbedPaneQLNguoiDung;
    private javax.swing.JTabbedPane jTabbedPaneQLSanPham;
    private javax.swing.JTabbedPane jTabbedPaneTrangChu;
    private javax.swing.JLabel lbHinhAnh;
    private javax.swing.JTable tblBaoCaoBH;
    private javax.swing.JTable tblBaoCaoNH;
    private javax.swing.JTable tblCTHD;
    private javax.swing.JTable tblCTHDNH;
    private javax.swing.JTable tblChiNhanh;
    private javax.swing.JTable tblDanhMuc;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblHoaDonBan;
    private javax.swing.JTable tblHoaDonNH;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblNguoiDung;
    private javax.swing.JTable tblNhaCC;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblSanPhamBH;
    private java.awt.TextArea textAreaMoTa;
    private javax.swing.JTextField txtDenNgayBCBH;
    private javax.swing.JTextField txtDenNgayBCNH;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtDiaChiChiNhanh;
    private javax.swing.JTextField txtDiaChiKH;
    private javax.swing.JTextField txtDiaChiNCC;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtDonGiaCTHD;
    private javax.swing.JTextField txtDonGiaCTHD2;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmailKH;
    private javax.swing.JTextField txtEmailND;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtGiamGia;
    private javax.swing.JTextField txtGiamGiaHD;
    private javax.swing.JTextField txtKichThuoc;
    private javax.swing.JTextField txtMaChiNhanh;
    private javax.swing.JTextField txtMaDanhMuc;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaPhieuNHap;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtMaSP2;
    private javax.swing.JTextField txtMaSP3;
    private javax.swing.JTextField txtMaSP4;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtNgayBan;
    private javax.swing.JTextField txtNgayLap;
    private javax.swing.JTextField txtNgayLapNH;
    private javax.swing.JTextField txtNgayNhap;
    private javax.swing.JTextField txtNhaCC;
    private javax.swing.JTextField txtSdtChiNhanh;
    private javax.swing.JTextField txtSdtKH;
    private javax.swing.JTextField txtSdtNCC;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSoLuongCTHD;
    private javax.swing.JTextField txtSoLuongCTHD2;
    private javax.swing.JTextField txtTaiKhoan;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTenChiNhanh;
    private javax.swing.JTextField txtTenDanhMuc;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTenNhaCC;
    private javax.swing.JTextField txtTenSanPham;
    private javax.swing.JTextField txtThanhTien;
    private javax.swing.JTextField txtTimKiemSP;
    private javax.swing.JTextField txtTimSP;
    private javax.swing.JTextField txtTonKho;
    private javax.swing.JTextField txtTongTien;
    private javax.swing.JTextField txtTongTienHD;
    private javax.swing.JTextField txtTongTienHD2;
    private javax.swing.JTextField txtTongTienMua;
    private javax.swing.JTextField txtTongTienNhap;
    private javax.swing.JTextField txtTuNgayBCBH;
    private javax.swing.JTextField txtTuNgayBCNH;
    // End of variables declaration//GEN-END:variables
}
