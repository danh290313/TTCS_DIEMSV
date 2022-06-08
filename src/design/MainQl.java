
package design;

import BACKUP_RESTORE.FormBackUp;
import TTCS.FormCapQuyen;
import com.raven.event.EventMenuSelected;
import dao.Provider;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import quanly.*;

/**
 *
 * @author danh
 */
public class MainQl extends javax.swing.JFrame {

 

    private JPanelMonHoc QLMonHoc = new JPanelMonHoc();
    private JPanelChuyenNganh QLCn = new JPanelChuyenNganh();
    private JPanelLopTinChi QLLtc = new JPanelLopTinChi();
    private JPanelGiangVien QLGv = new JPanelGiangVien();
    private JPanelDangKy_ChamDiem QLDk = new JPanelDangKy_ChamDiem();
    private JPanelSinhVien QLSV = new JPanelSinhVien();
    private JPanelDoAnTotNghiep QLDA = new JPanelDoAnTotNghiep();
    private JPanelCapTkGv taoTKGV = new JPanelCapTkGv();
    private JPanelCapTkSv taoTkSv = new JPanelCapTkSv();
    private JPanelThongKe ThongKe = new JPanelThongKe();
    private JPanelThongKe2 ThongKe2 = new JPanelThongKe2();
    public MainQl() {}
    public MainQl(String maGV,boolean quanLy) {
        initComponents();
        jLabelTenGV.setText("Giảng Viên: "+searchTenGiangVien(maGV).toUpperCase());
       
        ImageIcon img = new ImageIcon("image//ptit.png");
        this.setIconImage(img.getImage());
        //setBackground(new Color(0, 0, 0, 0));
        this.setLocationRelativeTo(null);
        
        menu.initMoving(this);
        menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) {
                if(quanLy)
                {
                    if (index == 0) {
                        setForm(QLCn);
                    } else if (index == 1) {
                       setForm(QLMonHoc);
                    } else if (index == 2) {
                        setForm(QLLtc);
                    } else if (index == 3) {
                       setForm(QLGv);
                    } else if (index == 4) {
                       setForm(QLDk);
                    } else if (index ==5)
                    {
                       setForm(QLSV);
                    } else if (index == 6)
                    {
                       setForm(QLDA);

                    } 
                    else if (index == 9)
                    {
                        setForm(taoTKGV);
                    }
                    else if (index == 10)
                    {
                        setForm(taoTkSv);
                    }
                    else if (index == 11)
                    {
                        setForm(ThongKe);
                    }
                    else if (index == 12)
                    {
                        setForm(ThongKe2);
                    }
                    else if (index == 13)
                    {
                      
                      new FormCapQuyen().setVisible(true);
                      
                        
                    }
                    else if (index == 14)
                    {
                        
                        new FormBackUp().setVisible(true);
                    }
                    

                    else if (index ==15)
                    {
                        int click = JOptionPane.showConfirmDialog(null, "Đăng xuất ngay bây giờ?");
                        if (click == JOptionPane.OK_OPTION) {
                            new DangNhap().setVisible(true);
                            setVisible(false);
                             Provider.maGv=null;

                        }
                    }
                }
                else
                {
                    if (index == 4) {
                       setForm(QLDk);
                    } 
                    else if (index == 6)
                    {
                       setForm(QLDA);

                    } 
                    else if (index == 11)
                    {
                        setForm(ThongKe);
                    }
                    else if (index == 12)
                    {
                        setForm(ThongKe2);
                    }

                    else if (index ==13)
                    {
                        int click = JOptionPane.showConfirmDialog(null, "Đăng xuất ngay bây giờ?");
                        if (click == JOptionPane.OK_OPTION) {
                            new DangNhap().setVisible(true);
                            setVisible(false);
                            Provider.maGv=null;

                        }
                    }
                }
                 
                
            }

        });
        //  set when system open start with home form
        
    }
    
    public String searchTenGiangVien(String maGV) {
        String sql = " SELECT hoten FROM giangvien WHERE magv=N'" + maGV + "'";
        try {
            Connection con = DataBaseHelper.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    private void setForm(JComponent com) {
        jPanelQL.removeAll();
        jPanelQL.add(com);
        jPanelQL.repaint();
        jPanelQL.revalidate();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu = new com.raven.component.Menu();
        jPanelQL = new javax.swing.JPanel();
        jLabelMain = new javax.swing.JLabel();
        jLabelTenGV = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 255));

        jPanelQL.setBackground(new java.awt.Color(153, 255, 255));
        jPanelQL.setOpaque(false);
        jPanelQL.setLayout(new java.awt.BorderLayout(0, 2));

        jLabelMain.setFont(new java.awt.Font("Times New Roman", 3, 100)); // NOI18N
        jLabelMain.setForeground(new java.awt.Color(255, 0, 102));
        jLabelMain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMain.setText("Quản Lý Điểm Sinh Viên");
        jPanelQL.add(jLabelMain, java.awt.BorderLayout.CENTER);

        jLabelTenGV.setBackground(new java.awt.Color(255, 204, 255));
        jLabelTenGV.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelTenGV.setForeground(new java.awt.Color(255, 51, 51));
        jLabelTenGV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTenGV.setText("Giảng Viên: ");
        jPanelQL.add(jLabelTenGV, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanelQL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelQL, javax.swing.GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(MainQl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainQl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainQl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainQl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainQl().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelMain;
    private javax.swing.JLabel jLabelTenGV;
    private javax.swing.JPanel jPanelQL;
    private com.raven.component.Menu menu;
    // End of variables declaration//GEN-END:variables
}
