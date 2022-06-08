/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanly;

import dao.Provider;
import static dao.Provider.searchMaMonHoc;
import design.DataBaseHelper;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class JPanelDangKy_ChamDiem extends javax.swing.JPanel {

    /**
     * Creates new form JPanelDangKy
     */
    DefaultTableModel model = new DefaultTableModel();
    public JPanelDangKy_ChamDiem() {
        initComponents();
        initData_DSSVDangKy();
        initData_TableDSLTC();
        jRadioButton1.setSelected(true);
        jButtonSua.setEnabled(false);
        jTextMSSV.setForeground(Color.red);
        jPanel2.hide();
        jTableDSSVDangKy.getTableHeader().setFont( new Font( "Tahoma" , Font.BOLD, 14));
        jTableDSSVDangKy.getColumnModel().getColumn(0).setPreferredWidth(200);
    }
    
    //--- Bảng LTC
    public void initData_TableDSLTC()
    {   
        model = (DefaultTableModel) jTableDSLTC.getModel();
        
        String sql1 = "select MaLTC,MaMH,HocKi from loptinchi ltc "
                + "order by cast(substring(ltc.MaLTC,4,10) as int)";
        
        String sql2 = "select ltc.MaLTC,MaMH,HocKi from loptinchi ltc,phancong pc "
                + "where pc.MaLTC=ltc.MaLTC and pc.MaGV= '" + Provider.maGv + "'  order by cast(substring(ltc.MaLTC,4,10) as int)";
        model.setRowCount(0);
        try(Connection con = DataBaseHelper.getConnection();
                Statement smt = con.createStatement();)
        {   
            Vector vt;
            ResultSet rs = smt.executeQuery(Provider.maGv==null?sql1:sql2);
            while(rs.next())
            {
                vt = new Vector();
                vt.add(rs.getString(1));
                vt.add(searchTenMonHoc(rs.getString(2)));
                vt.add(rs.getString(3));
                
                model.addRow(vt);
            }
            jTableDSLTC.setModel(model);
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
        timLTC.setText("");
    }
    
    public String searchTenMonHoc(String maMH) {
        String sql = " SELECT tenmh FROM monhoc WHERE mamh=N'" + maMH + "'";
        try {
            Connection con = DataBaseHelper.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
        return null;
    }
    //---
    
    //--- Bảng DangKi
    public void initData_DSSVDangKy() {
        model = (DefaultTableModel) jTableDSSVDangKy.getModel();
        //String sql = "select MaLTC,MaSV,DiemCC,DiemGK,DiemCK,Huy from dangki where MaLTC=N'"+timLTC.getText()+"' order by cast(substring(MaSV,3,10) as int)";
        String sql = "select HOTEN,sv.MaSV,DiemCC,DiemGK,DiemCK,Huy from dangki,sinhvien sv where sv.MaSV=dangki.MaSV and MaLTC='"+timLTC.getText()+"' order by cast(substring(sv.MaSV,3,10) as int)";
        model.setRowCount(0);
        try (Connection con = DataBaseHelper.getConnection();
                Statement smt = con.createStatement();) {
            Vector vt;
            ResultSet rs = smt.executeQuery(sql);
            while (rs.next()) {
                vt = new Vector();
                vt.add(rs.getString(1));
                vt.add(rs.getString(2).toUpperCase());
                vt.add(rs.getString(3));
                vt.add(rs.getString(4));
                vt.add(rs.getString(5));
                vt.add((rs.getString(6).equals("1")) ? "True" : "False");
                model.addRow(vt);
            }
            jTableDSSVDangKy.setModel(model);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }
    //---

//    public boolean insertDangki()
//    {
//        String sql = "insert into dangki values(?,?,?,?,?,?)";
//
//        try (Connection con = DataBaseHelper.getConnection();
//            PreparedStatement smt = con.prepareStatement(sql);) {
//            
//
//            smt.setString(2, jTextMSSV.getText());
//            smt.setString(3, jTextCC.getText());
//            smt.setString(4, jTextTH.getText());
//            smt.setString(5, jTextCK.getText());
//            if (jRadioButton1.isSelected()) {
//                smt.setBoolean(6, false);
//            } else {
//                smt.setBoolean(6, true);
//            }
//
//            smt.executeUpdate();  
//        } catch (SQLException ex) {
//
////            JOptionPane.showMessageDialog(this, ex.toString());
//            return false;           
//        }
//        return true;
//    }
    
//    public void xoaDangKiTheoSV() {
//        int row = jTableDSSVDangKy.getSelectedRow();
//        String sql = "delete from dangki where maltc=? and masv=?";
//        try (Connection con = DataBaseHelper.getConnection();
//                PreparedStatement smt = con.prepareStatement(sql);) {
//            smt.setString(1, jTableDSSVDangKy.getValueAt(row, 0).toString().toUpperCase());
//            smt.setString(2, jTableDSSVDangKy.getValueAt(row, 1).toString().toUpperCase());
//            smt.executeUpdate();
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(this, ex.toString());
//        } 
//    }  
    
    public void lamMoi() {
        initData_TableDSLTC();
        initData_DSSVDangKy();
        jButtonSua.setEnabled(false);
        jPanel2.hide();
        jTextMSSV.setText("");
    }
    @Override
    protected void paintChildren(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, Color.decode("#FFFDE4"), 0, getHeight(), Color.decode("#005AA7"));
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());
        super.paintChildren(grphcs);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupHuy = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextCC = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextTH = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextCK = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jRadioButton2 = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDSSVDangKy = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        timLTC = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextMSSV = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDSLTC = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jButtonLamMoi1 = new javax.swing.JButton();
        jButtonSua = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTextTimKiem = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 204));
        setPreferredSize(new java.awt.Dimension(1050, 700));

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setMinimumSize(new java.awt.Dimension(89, 32));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(89, 32));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Điểm Sinh Viên");
        jPanel1.add(jLabel1, new java.awt.GridBagConstraints());

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridLayout(0, 2));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Điểm CC:");
        jPanel2.add(jLabel4);

        jTextCC.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCCActionPerformed(evt);
            }
        });
        jPanel2.add(jTextCC);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Điểm TH:");
        jPanel2.add(jLabel6);

        jTextTH.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextTH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextTHActionPerformed(evt);
            }
        });
        jPanel2.add(jTextTH);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Điểm CK:");
        jPanel2.add(jLabel5);

        jTextCK.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextCK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCKActionPerformed(evt);
            }
        });
        jPanel2.add(jTextCK);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Hủy:");
        jPanel2.add(jLabel2);

        buttonGroupHuy.add(jRadioButton1);
        jRadioButton1.setText("False");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jRadioButton1);
        jPanel2.add(jLabel9);

        buttonGroupHuy.add(jRadioButton2);
        jRadioButton2.setText("True");
        jPanel2.add(jRadioButton2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));
        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jTableDSSVDangKy.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "HoTen", "MSSV", "DCC", "DTH", "DCK", "Huy"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableDSSVDangKy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDSSVDangKyMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableDSSVDangKy);

        jPanel3.add(jScrollPane1);

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("  LTC chọn:");
        jPanel4.add(jLabel8);

        timLTC.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        timLTC.setForeground(new java.awt.Color(255, 0, 0));
        jPanel4.add(timLTC);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("SV chọn:");
        jPanel4.add(jLabel10);

        jTextMSSV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextMSSV.setForeground(new java.awt.Color(255, 51, 51));
        jTextMSSV.setText("       ");
        jPanel4.add(jTextMSSV);

        jPanel7.setBackground(new java.awt.Color(255, 255, 204));
        jPanel7.setLayout(new java.awt.GridLayout(1, 0));

        jTableDSLTC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "MaLTC", "TenMH", "HocKi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableDSLTC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDSLTCMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableDSLTC);

        jPanel7.add(jScrollPane2);

        jPanel8.setBackground(new java.awt.Color(255, 255, 204));
        jPanel8.setMinimumSize(new java.awt.Dimension(89, 32));
        jPanel8.setOpaque(false);
        jPanel8.setPreferredSize(new java.awt.Dimension(89, 32));
        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.LINE_AXIS));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Danh sách LTC:");
        jPanel8.add(jLabel3);

        jLabel11.setText("                                                                            ");
        jPanel8.add(jLabel11);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Danh sách SV:");
        jPanel8.add(jLabel7);

        jPanel10.setBackground(new java.awt.Color(255, 255, 204));
        jPanel10.setOpaque(false);
        jPanel10.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        jButtonLamMoi1.setBackground(new java.awt.Color(0, 102, 204));
        jButtonLamMoi1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonLamMoi1.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLamMoi1.setText("Làm Mới");
        jButtonLamMoi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLamMoi1ActionPerformed(evt);
            }
        });
        jPanel10.add(jButtonLamMoi1);

        jButtonSua.setBackground(new java.awt.Color(0, 102, 204));
        jButtonSua.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonSua.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSua.setText("Cập nhật");
        jButtonSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuaActionPerformed(evt);
            }
        });
        jPanel10.add(jButtonSua);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        jLabel12.setBackground(new java.awt.Color(255, 255, 204));
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Tìm Kiếm SV:");
        jLabel12.setMaximumSize(new java.awt.Dimension(90, 22));
        jLabel12.setPreferredSize(new java.awt.Dimension(90, 13));
        jPanel5.add(jLabel12);

        jTextTimKiem.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextTimKiem.setPreferredSize(new java.awt.Dimension(79, 13));
        jTextTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextTimKiemActionPerformed(evt);
            }
        });
        jTextTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextTimKiemKeyReleased(evt);
            }
        });
        jPanel5.add(jTextTimKiem);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 62, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(745, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(97, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(193, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextTHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextTHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextTHActionPerformed

    private void jTextCKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCKActionPerformed

    private void jTextCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCCActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jTableDSSVDangKyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDSSVDangKyMouseClicked

        jPanel2.show();
   
        jButtonSua.setEnabled(true);
        jTableDSSVDangKy.setRowSelectionAllowed(true);
        
        int row = jTableDSSVDangKy.getSelectedRow();
        jTextMSSV.setText(jTableDSSVDangKy.getValueAt(row, 1).toString().toUpperCase());
        timLTC.setText(jTableDSLTC.getValueAt(jTableDSLTC.getSelectedRow(), 0).toString());
        jTextCC.setText(jTableDSSVDangKy.getValueAt(row, 2).toString());
        jTextTH.setText(jTableDSSVDangKy.getValueAt(row, 3).toString());
        jTextCK.setText(jTableDSSVDangKy.getValueAt(row, 4).toString());
        if ("False".equals(jTableDSSVDangKy.getValueAt(row, 5).toString()))
            jRadioButton1.setSelected(true);
        else 
            jRadioButton2.setSelected(true);
                
    }//GEN-LAST:event_jTableDSSVDangKyMouseClicked

    private void jTableDSLTCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDSLTCMouseClicked

        jTableDSLTC.setRowSelectionAllowed(true);
        int row = jTableDSLTC.getSelectedRow();
        timLTC.setText(jTableDSLTC.getValueAt(row, 0).toString().toUpperCase());
        initData_DSSVDangKy();
    }//GEN-LAST:event_jTableDSLTCMouseClicked

    private void jButtonLamMoi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLamMoi1ActionPerformed
        lamMoi();
    }//GEN-LAST:event_jButtonLamMoi1ActionPerformed

    private void jButtonSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaActionPerformed
        int row = jTableDSLTC.getSelectedRow();
        String a=jTableDSLTC.getValueAt(row, 0).toString();
        int kt = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa?");
        if (kt == JOptionPane.OK_OPTION) {
            String sql = "update DangKi set [DiemCC]=?,[DiemGK]=?,[DiemCK]=?,[Huy]=? where [MaSV]=? and [MaLTC]=?";
            try (Connection con = DataBaseHelper.getConnection();
                    PreparedStatement smt = con.prepareStatement(sql);) {
                
                smt.setFloat(1, Float.parseFloat(jTextCC.getText()));
                smt.setFloat(2, Float.parseFloat(jTextTH.getText()));
                smt.setFloat(3, Float.parseFloat(jTextCK.getText()));
                if (jRadioButton1.isSelected()) {
                    smt.setBoolean(4, false);
                } else {
                    smt.setBoolean(4, true);
                }
                
                smt.setString(5, jTextMSSV.getText());
                smt.setString(6, timLTC.getText());
                
                if(Float.parseFloat(jTextCC.getText())<0 || Float.parseFloat(jTextCC.getText())>10)
                {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập 0<=điểm<=10 .");
                    jTextCC.grabFocus();
                    return;
                }
                if(Float.parseFloat(jTextTH.getText())<0 || Float.parseFloat(jTextTH.getText())>10)
                {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập 0<=điểm<=10.");
                    jTextTH.grabFocus();
                    return;
                }
                if(Float.parseFloat(jTextCK.getText())<0 || Float.parseFloat(jTextCK.getText())>10)
                {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập 0<=điểm<=10.");
                    jTextCK.grabFocus();
                    return;
                }
                
                int kt2 = smt.executeUpdate();
                if (kt2 > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                    jButtonSua.setEnabled(false);
                    jPanel2.hide();
                    jTextMSSV.setText("");
                    timLTC.setText(a);
                    initData_DSSVDangKy();
                    return;
                }
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.toString());
            }
//            JOptionPane.showMessageDialog(this, "Cập nhật thông tin đăng ký thành công");
//            lamMoi();    
        }
    }//GEN-LAST:event_jButtonSuaActionPerformed

    private void jTextTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextTimKiemActionPerformed

    public void timKiemSv(String s)
    {   

        model = (DefaultTableModel) jTableDSSVDangKy.getModel();

        String sql = "select HOTEN,sv.MaSV,DiemCC,DiemGK,DiemCK,Huy from dangki,sinhvien sv where sv.MaSV=dangki.MaSV and MaLTC='"+timLTC.getText()+"' and hoten like N'%"+s+"%' or sv.maSV like N'%"+s+"%'";
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
                vt.add(rs.getString(2).toUpperCase());
                vt.add(rs.getString(3));
                vt.add(rs.getString(4));
                vt.add(rs.getString(5));
                vt.add((rs.getString(6).equals("1")) ? "True" : "False");
                model.addRow(vt);
            }
            jTableDSSVDangKy.setModel(model);
//            if ("".equals(jTextTimKiem.getText())){
//                model.setRowCount(0); 
//            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
        System.out.println("" + sql);
           if ("".equals(jTextTimKiem.getText())){
                initData_DSSVDangKy();
            }
        
    }
    private void jTextTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextTimKiemKeyReleased
        // TODO add your handling code here:
        timKiemSv(jTextTimKiem.getText());
    }//GEN-LAST:event_jTextTimKiemKeyReleased
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupHuy;
    private javax.swing.JButton jButtonLamMoi1;
    private javax.swing.JButton jButtonSua;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableDSLTC;
    private javax.swing.JTable jTableDSSVDangKy;
    private javax.swing.JTextField jTextCC;
    private javax.swing.JTextField jTextCK;
    private javax.swing.JLabel jTextMSSV;
    private javax.swing.JTextField jTextTH;
    private javax.swing.JTextField jTextTimKiem;
    private javax.swing.JLabel timLTC;
    // End of variables declaration//GEN-END:variables
}
