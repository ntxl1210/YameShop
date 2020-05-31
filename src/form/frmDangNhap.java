/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import database.clsConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import SHAHashing.SHAHashing;
import GlobalData.GlobalData;
import NguoiDung.NguoiDung;
import java.awt.event.KeyEvent;

/**
 *
 * @author NeedNguyen
 */
public class frmDangNhap extends javax.swing.JFrame {

    /**
     * Creates new form frmDangNhap1
     */
    public frmDangNhap() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtUserName = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        ckGhiNho = new java.awt.Checkbox();
        btnDangNhap1 = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ĐĂNG NHẬP");

        txtUserName.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtUserName.setForeground(new java.awt.Color(51, 102, 255));
        txtUserName.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 102, 255));
        jLabel1.setText("QUẢN LÝ CỬA HÀNG YAME SHOP");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Tên đăng nhập");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Mật khẩu");

        ckGhiNho.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        ckGhiNho.setForeground(new java.awt.Color(51, 51, 255));
        ckGhiNho.setLabel("Nhớ mật khẩu");

        btnDangNhap1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnDangNhap1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/login.png"))); // NOI18N
        btnDangNhap1.setText("Đăng nhập");
        btnDangNhap1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangNhap1ActionPerformed(evt);
            }
        });

        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswordKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout txtUserNameLayout = new javax.swing.GroupLayout(txtUserName);
        txtUserName.setLayout(txtUserNameLayout);
        txtUserNameLayout.setHorizontalGroup(
            txtUserNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtUserNameLayout.createSequentialGroup()
                .addGroup(txtUserNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, txtUserNameLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE))
                    .addGroup(txtUserNameLayout.createSequentialGroup()
                        .addGroup(txtUserNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(txtUserNameLayout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(txtUserNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ckGhiNho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(txtUserNameLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32)))
                .addContainerGap())
            .addGroup(txtUserNameLayout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(btnDangNhap1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        txtUserNameLayout.setVerticalGroup(
            txtUserNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtUserNameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23)
                .addGroup(txtUserNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(txtUserNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(ckGhiNho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDangNhap1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtUserName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangNhap1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangNhap1ActionPerformed
        // TODO add your handling code here:
        dangNhap();
    }//GEN-LAST:event_btnDangNhap1ActionPerformed

    private void txtPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            dangNhap();
        }
    }//GEN-LAST:event_txtPasswordKeyPressed

    private void dangNhap(){
        String taiKhoan = txtUsername.getText();
        String matKhau = String.valueOf(txtPassword.getPassword());
        String matKhauDaMaHoa = SHAHashing.getSHAHash(matKhau);
        
        if(taiKhoan.equals(""))
        {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đăng nhập!");
        }
        else if(matKhau.equals(""))
        {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu!");
        }
        else
        {
            PreparedStatement ps = null;
            Connection conn = null;
            try 
            {
                conn = clsConnectDB.getJDBCConnection();
                conn.setCatalog("Yame Shop");
                String sql = "SELECT  * FROM nguoi_dung WHERE tai_khoan = ? AND mat_khau = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, taiKhoan);
                ps.setString(2, matKhauDaMaHoa);
                
                ResultSet rs = ps.executeQuery();
                
                if(rs.next())
                {
                    boolean ghiNhoDangNhap = false;
                    if(ckGhiNho.getState())
                    {
                        ghiNhoDangNhap = true;
                    }
                    
                    NguoiDung nguoiDung = new NguoiDung(rs.getInt("id"), rs.getString("tai_khoan"), rs.getString("mat_khau"), rs.getString("ten"), 
                            rs.getString("email"), rs.getInt("ma_cv"), rs.getInt("sdt"), rs.getString("dia_chi"), rs.getString("gioi_tinh"), rs.getInt("luong"), ghiNhoDangNhap);
            
                    GlobalData.setNguoiDung(nguoiDung);
                    
                    form.frmTrangChu frmTC = new   form.frmTrangChu();
                    this.hide();
                    frmTC.show();
                    JOptionPane.showMessageDialog(this, "Xin chào " + nguoiDung.getTen() + ", bạn đã đăng nhập thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Đăng nhập thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } 
    }
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
            java.util.logging.Logger.getLogger(frmDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmDangNhap().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangNhap1;
    private java.awt.Checkbox ckGhiNho;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPanel txtUserName;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
