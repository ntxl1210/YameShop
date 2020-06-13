/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import SHAHashing.SHAHashing;
import NguoiDung.NguoiDung;
import GlobalData.GlobalData;
import database.clsConnectDB;
import javax.swing.JOptionPane;

/**
 *
 * @author NeedNguyen
 */
public class frmDoiMatKhau extends javax.swing.JFrame {
    NguoiDung nguoiDung = new NguoiDung();
    clsConnectDB cls = new clsConnectDB();
    /**
     * Creates new form frmDangKy
     */
    public frmDoiMatKhau() {
        initComponents();
        try 
        {
            
            nguoiDung = GlobalData.getNguoiDung();
        } 
        catch (NullPointerException e) 
        {
            nguoiDung = null;
        }
        if(nguoiDung == null)
        {
            JOptionPane.showMessageDialog(this, "Đã có lỗi xảy ra");
            form.frmDangNhap frmDN = new   form.frmDangNhap();
            this.hide();
            frmDN.show();
        }
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
        jLabel3 = new javax.swing.JLabel();
        btnLuu = new javax.swing.JButton();
        txtMatKhauCu = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        txtMatKhauMoi = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        txtNhapLai = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ĐỔI MẬT KHẨU");

        txtUserName.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtUserName.setForeground(new java.awt.Color(51, 102, 255));
        txtUserName.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("ĐỔI MẬT KHẨU");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Mật khẩu cũ");

        btnLuu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Mật khẩu mới");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Nhập lại mật khẩu");

        javax.swing.GroupLayout txtUserNameLayout = new javax.swing.GroupLayout(txtUserName);
        txtUserName.setLayout(txtUserNameLayout);
        txtUserNameLayout.setHorizontalGroup(
            txtUserNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtUserNameLayout.createSequentialGroup()
                .addGroup(txtUserNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtUserNameLayout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jLabel1))
                    .addGroup(txtUserNameLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(txtUserNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtUserNameLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNhapLai, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtUserNameLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMatKhauMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtUserNameLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMatKhauCu, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(txtUserNameLayout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(btnLuu)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        txtUserNameLayout.setVerticalGroup(
            txtUserNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtUserNameLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(txtUserNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMatKhauCu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(txtUserNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMatKhauMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(txtUserNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNhapLai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(35, 35, 35)
                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
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

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
        String matKhauCu = String.valueOf(txtMatKhauCu.getPassword());
        String matKhauCuDaMaHoa = SHAHashing.getSHAHash(matKhauCu);
        String matKhauMoi = String.valueOf(txtMatKhauMoi.getPassword());
        String matKhauNhapLai = String.valueOf(txtNhapLai.getPassword());
        
        if(matKhauCu.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Mật khẩu cũ không được để trống!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            if(!matKhauCuDaMaHoa.equals(nguoiDung.getMatKhau()))
            {
                JOptionPane.showMessageDialog(this, "Mật khẩu cũ không đúng!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                if(matKhauMoi.isEmpty() | matKhauNhapLai.isEmpty())
                {
                    JOptionPane.showMessageDialog(this, "Mật khẩu mới không được để trống!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    if(matKhauCu.equals(matKhauMoi))
                    {
                        JOptionPane.showMessageDialog(this, "Mật khẩu mới không được giống với mật khẩu cũ!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        if(matKhauMoi.equals(matKhauNhapLai))
                        {
                            String matKhauDaMaHoa = SHAHashing.getSHAHash(matKhauMoi);
                            nguoiDung.setMatKhau(matKhauDaMaHoa);
                            String sql = "UPDATE nguoi_dung "
                                    + "SET mat_khau = '"+matKhauDaMaHoa+"' "
                                    + "WHERE id = '"+nguoiDung.getId()+"'";
                            cls.excuteQueryUpdateDB(sql);
                            JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công, vui lòng đăng nhập lại", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            GlobalData.xoaNguoiDung();
                            frmDangNhap frmDN = new frmDangNhap();
                            this.hide();
                            frmDN.show();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không giống mật khẩu mới!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }    
        }
            
        
        
    }//GEN-LAST:event_btnLuuActionPerformed

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
            java.util.logging.Logger.getLogger(frmDoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmDoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmDoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmDoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmDoiMatKhau().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLuu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPasswordField txtMatKhauCu;
    private javax.swing.JPasswordField txtMatKhauMoi;
    private javax.swing.JPasswordField txtNhapLai;
    private javax.swing.JPanel txtUserName;
    // End of variables declaration//GEN-END:variables
}
