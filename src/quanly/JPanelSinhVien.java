/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanly;

import doan.DataBaseHelper;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author danh
 */
public class JPanelSinhVien extends javax.swing.JPanel {

    /**
     * Creates new form JPanelSinhVien
     */
    public JPanelSinhVien() {
        initComponents();
        initData();
        jLabelMaSv.setText(taoMaSv());
        jLabelMaSv.setForeground(Color.RED);
    }
    DefaultTableModel model = new DefaultTableModel();
    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date date = java.util.Calendar.getInstance().getTime();
    
    public String taoMaSv()
    {
        String sql = "select masv from sinhvien";
        int max =0;
        try(Connection con = DataBaseHelper.getConnection();
                Statement smt = con.createStatement();
                 )
        {   
            ResultSet rs = smt.executeQuery(sql);
            while(rs.next())
            {   
                int ma = Integer.parseInt(rs.getString(1).substring(2,rs.getString(1).length()).trim());
                if(ma> max)
                    max = ma;
            }
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "SV" + String.valueOf(max +1);
    }
    
    public void initData()
    {   
        model = (DefaultTableModel) jTableDSSV.getModel();
        String sql = "select * from sinhvien";
        model.setRowCount(0);
        try(Connection con = DataBaseHelper.getConnection();
                Statement smt = con.createStatement();)
        {   
            Vector vt;
            ResultSet rs = smt.executeQuery(sql);
            while(rs.next())
            {
                vt = new Vector();
                vt.add(rs.getString(1));
                vt.add(rs.getString(2));
                vt.add(rs.getString(3));
                vt.add(rs.getString(4));
                vt.add(rs.getString(5));
                model.addRow(vt);
            }
            jTableDSSV.setModel(model);
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void lamMoi()
    {
        jTextHoTenSv.setText("");
        jTextDiaChi.setText("");
        jLabelMaSv.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabelMaSv = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextHoTenSv = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jRadioNam = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jRadioNu = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        jDateNgaySinhSv = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        jTextDiaChi = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxKhoaHoc = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jComboBoxLopHoc = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jComboBoxCn = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxTotNghiep = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jRadioButtonDangDiHoc = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        jRadioButtonDaNghi = new javax.swing.JRadioButton();
        jButtonLamMoi = new javax.swing.JButton();
        jButtonThem = new javax.swing.JButton();
        jButtonXoa = new javax.swing.JButton();
        jButtonSua = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDSSV = new javax.swing.JTable();

        jPanel2.setLayout(new java.awt.GridLayout(6, 2));

        jLabel12.setBackground(new java.awt.Color(255, 204, 204));
        jLabel12.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel12.setText("Mã Sinh Viên: ");
        jPanel2.add(jLabel12);

        jLabelMaSv.setBackground(new java.awt.Color(255, 204, 204));
        jPanel2.add(jLabelMaSv);

        jLabel16.setBackground(new java.awt.Color(255, 204, 204));
        jLabel16.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel16.setText("Họ Tên: ");
        jLabel16.setPreferredSize(new java.awt.Dimension(50, 22));
        jPanel2.add(jLabel16);

        jTextHoTenSv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextHoTenSvActionPerformed(evt);
            }
        });
        jPanel2.add(jTextHoTenSv);

        jLabel15.setBackground(new java.awt.Color(255, 204, 204));
        jLabel15.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel15.setText("Giới Tính: ");
        jPanel2.add(jLabel15);

        buttonGroup1.add(jRadioNam);
        jRadioNam.setSelected(true);
        jRadioNam.setText("Nam");
        jPanel2.add(jRadioNam);
        jPanel2.add(jLabel1);

        buttonGroup1.add(jRadioNu);
        jRadioNu.setText("Nữ");
        jPanel2.add(jRadioNu);

        jLabel13.setBackground(new java.awt.Color(255, 204, 204));
        jLabel13.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel13.setText("Ngày Sinh: ");
        jPanel2.add(jLabel13);
        jPanel2.add(jDateNgaySinhSv);

        jLabel17.setBackground(new java.awt.Color(255, 204, 204));
        jLabel17.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel17.setText("Địa Chỉ: ");
        jPanel2.add(jLabel17);

        jTextDiaChi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextDiaChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDiaChiActionPerformed(evt);
            }
        });
        jPanel2.add(jTextDiaChi);

        jPanel3.setLayout(new java.awt.GridLayout(6, 2));

        jLabel4.setBackground(new java.awt.Color(255, 204, 204));
        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel4.setText("Khóa Học: ");
        jPanel3.add(jLabel4);

        jComboBoxKhoaHoc.setEditable(true);
        jComboBoxKhoaHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2020-2024", "2021-2025", "2022-2026" }));
        jPanel3.add(jComboBoxKhoaHoc);

        jLabel14.setBackground(new java.awt.Color(255, 204, 204));
        jLabel14.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel14.setText("Lớp Học");
        jPanel3.add(jLabel14);

        jComboBoxLopHoc.setEditable(true);
        jComboBoxLopHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HK1", "HK2", "HK3" }));
        jComboBoxLopHoc.setSelectedIndex(-1);
        jPanel3.add(jComboBoxLopHoc);

        jLabel9.setBackground(new java.awt.Color(255, 204, 204));
        jLabel9.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel9.setText("Chuyên Ngành");
        jPanel3.add(jLabel9);

        jComboBoxCn.setEditable(true);
        jComboBoxCn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HK1", "HK2", "HK3" }));
        jComboBoxCn.setSelectedIndex(-1);
        jPanel3.add(jComboBoxCn);

        jLabel8.setBackground(new java.awt.Color(255, 204, 204));
        jLabel8.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel8.setText("Đề Án Tốt Nghiệp: ");
        jPanel3.add(jLabel8);

        jComboBoxTotNghiep.setEditable(true);
        jComboBoxTotNghiep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HK1", "HK2", "HK3" }));
        jComboBoxTotNghiep.setSelectedIndex(-1);
        jPanel3.add(jComboBoxTotNghiep);

        jLabel10.setBackground(new java.awt.Color(255, 204, 204));
        jLabel10.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel10.setText("Trạng Thái Nghỉ:");
        jPanel3.add(jLabel10);

        buttonGroup2.add(jRadioButtonDangDiHoc);
        jRadioButtonDangDiHoc.setSelected(true);
        jRadioButtonDangDiHoc.setText("Đang đi học");
        jRadioButtonDangDiHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonDangDiHocActionPerformed(evt);
            }
        });
        jPanel3.add(jRadioButtonDangDiHoc);

        jLabel11.setBackground(new java.awt.Color(255, 204, 204));
        jLabel11.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jPanel3.add(jLabel11);

        buttonGroup2.add(jRadioButtonDaNghi);
        jRadioButtonDaNghi.setText("Đã nghỉ");
        jPanel3.add(jRadioButtonDaNghi);

        jButtonLamMoi.setBackground(new java.awt.Color(255, 204, 204));
        jButtonLamMoi.setText("Làm Mới");
        jButtonLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLamMoiActionPerformed(evt);
            }
        });

        jButtonThem.setBackground(new java.awt.Color(255, 204, 204));
        jButtonThem.setText("Thêm");
        jButtonThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonThemActionPerformed(evt);
            }
        });

        jButtonXoa.setBackground(new java.awt.Color(255, 204, 204));
        jButtonXoa.setText("Xóa");
        jButtonXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoaActionPerformed(evt);
            }
        });

        jButtonSua.setBackground(new java.awt.Color(255, 204, 204));
        jButtonSua.setText("Sửa");
        jButtonSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuaActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 204, 204));
        jPanel5.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Sinh Viên");
        jPanel5.add(jLabel2);

        jScrollPane1.setBackground(new java.awt.Color(255, 204, 204));

        jTableDSSV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Sinh Viên", "Họ và Tên", "Phái", "NGày Sinh", "Địa Chỉ"
            }
        ));
        jTableDSSV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDSSVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableDSSV);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(216, 216, 216)
                                .addComponent(jButtonLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jButtonThem, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jButtonXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jButtonSua, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonThem, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSua, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextHoTenSvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextHoTenSvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextHoTenSvActionPerformed

    private void jTextDiaChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextDiaChiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDiaChiActionPerformed

    private void jRadioButtonDangDiHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonDangDiHocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonDangDiHocActionPerformed

    private void jButtonLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLamMoiActionPerformed
        // TODO add your handling code here:
        lamMoi();
    }//GEN-LAST:event_jButtonLamMoiActionPerformed

    private void jButtonThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemActionPerformed
        // TODO add your handling code here:

        String sql = "insert into sinhvien values(?,?,?,?,?,  ?,?,?,?,?,?)";
        try (Connection con = DataBaseHelper.getConnection();
            PreparedStatement smt = con.prepareStatement(sql);) {
            smt.setString(1, taoMaSv());
            smt.setString(2, jTextHoTenSv.getText());
            //smt.setInt(3, jRadioNam.isSelected() ? '0': '1' );
            if (jRadioNam.isSelected()) {
                smt.setBoolean(3, false);
            } else {
                smt.setBoolean(3, true);
            }
            
            smt.setString(4, ft.format(jDateNgaySinhSv.getDate()));
            smt.setString(5, jTextDiaChi.getText());
            smt.setString(6, jComboBoxKhoaHoc.getSelectedItem().toString());
            //smt.setInt(7, jRadioButtonDaNghi.isSelected() ? '1': '0' );
            if (jRadioButtonDaNghi.isSelected()) {
                smt.setBoolean(7, true);
            } else {
                smt.setBoolean(7, false);
            }
            
            smt.setString(8, jComboBoxTotNghiep.getSelectedItem().toString());
            smt.setString(9, jComboBoxCn.getSelectedItem().toString());
            smt.setString(10, jComboBoxLopHoc.getSelectedItem().toString());
            smt.setString(11, "123");
            
            
            
            int kt = smt.executeUpdate();
            if (kt > 0) {
                JOptionPane.showMessageDialog(this, "insert thanh cong");
            }
            initData();
            lamMoi();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButtonThemActionPerformed

    private void jButtonXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaActionPerformed
        // TODO add your handling code here:
        int kt = JOptionPane.showConfirmDialog(this, "ban co muon xoa khong");
        if (kt == JOptionPane.CANCEL_OPTION) {
            return;
        } else if (kt == JOptionPane.OK_OPTION) {
            String sql = "delete from sinhvien where masv=?";
            try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
                smt.setString(1, jLabelMaSv.getText());

                int kt2 = smt.executeUpdate();
                if (kt2 > 0) {
                    JOptionPane.showMessageDialog(this, "xoa thanh cong");
                }
                initData();
                lamMoi();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButtonXoaActionPerformed

    private void jButtonSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaActionPerformed
        // TODO add your handling code here:
        int kt = JOptionPane.showConfirmDialog(this, "ban chan chan muon sua khong");
        if (kt == JOptionPane.CANCEL_OPTION) {
            return;
        } else if (kt == JOptionPane.OK_OPTION) {
            String sql = "update monhoc set hoten=?,phai=?,ngaysinh=?,diachi=?,khoahoc=?,trangthainghi=?,mada=?,macn=?,malop=? where masv = ?";
            try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {

                smt.setString(1, jTextHoTenSv.getText());
                smt.setInt(2, jRadioNam.isSelected() ? '0': '1' );
                smt.setString(3, ft.format(jDateNgaySinhSv.getDate()));
                smt.setString(4, jTextDiaChi.getText());
                smt.setString(5, jComboBoxKhoaHoc.getSelectedItem().toString());
                smt.setInt(6, jRadioButtonDaNghi.isSelected() ? '1': '0' );
                smt.setString(7, jComboBoxTotNghiep.getSelectedItem().toString());
                smt.setString(8, jComboBoxCn.getSelectedItem().toString());
                smt.setString(9, jComboBoxLopHoc.getSelectedItem().toString());
                smt.setString(10, jLabelMaSv.getText());

                int kt2 = smt.executeUpdate();
                if (kt2 > 0) {
                    JOptionPane.showMessageDialog(this, "update thanh cong");
                }
                initData();
                lamMoi();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.toString());
            }
        }
    }//GEN-LAST:event_jButtonSuaActionPerformed

    private void jTableDSSVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDSSVMouseClicked
        // TODO add your handling code here:
        int row = jTableDSSV.getSelectedRow();
        if(row>=0)
        {
//            jLabelMaMH.setText(jTableDSSV.getValueAt(row, 0).toString().toUpperCase());
//            jTextMh.setText(jTableDSSV.getValueAt(row, 1).toString());
//            jTextStLt.setText(jTableDSSV.getValueAt(row, 2).toString());
//            jTextStTh.setText(jTableDSSV.getValueAt(row, 3).toString());
//            jTextSTc.setText(jTableDSSV.getValueAt(row, 4).toString());
        }
    }//GEN-LAST:event_jTableDSSVMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButtonLamMoi;
    private javax.swing.JButton jButtonSua;
    private javax.swing.JButton jButtonThem;
    private javax.swing.JButton jButtonXoa;
    private javax.swing.JComboBox<String> jComboBoxCn;
    private javax.swing.JComboBox<String> jComboBoxKhoaHoc;
    private javax.swing.JComboBox<String> jComboBoxLopHoc;
    private javax.swing.JComboBox<String> jComboBoxTotNghiep;
    private com.toedter.calendar.JDateChooser jDateNgaySinhSv;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelMaSv;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButtonDaNghi;
    private javax.swing.JRadioButton jRadioButtonDangDiHoc;
    private javax.swing.JRadioButton jRadioNam;
    private javax.swing.JRadioButton jRadioNu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableDSSV;
    private javax.swing.JTextField jTextDiaChi;
    private javax.swing.JTextField jTextHoTenSv;
    // End of variables declaration//GEN-END:variables
}