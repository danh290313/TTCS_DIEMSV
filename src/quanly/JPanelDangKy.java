/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanly;

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
public class JPanelDangKy extends javax.swing.JPanel {

    /**
     * Creates new form JPanelDangKy
     */
    DefaultTableModel model = new DefaultTableModel();
    public JPanelDangKy() {
        initComponents();
        initData(1);
        initData_TableLTC();
        jTextMSSV.setForeground(Color.RED);
        jRadioButton1.setSelected(true);
        jButtonXoa.setEnabled(false);
        jButtonSua.setEnabled(false);
        jTableDSLTC.getTableHeader().setFont( new Font( "Tahoma" , Font.BOLD, 14));
        jTableDSSVDangKy.getTableHeader().setFont( new Font( "Tahoma" , Font.BOLD, 14));
    }
    
    //--- Bảng LTC
    public void initData_TableLTC()
    {   
        model = (DefaultTableModel) jTableDSLTC.getModel();
        String sql = "select * from loptinchi";
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
                vt.add(rs.getString(6));
                vt.add(rs.getString(7));
                vt.add(searchTenMonHoc(rs.getString(8)));
                model.addRow(vt);
            }
            jTableDSLTC.setModel(model);
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
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
    public void initData(int n) {
        model = (DefaultTableModel) jTableDSSVDangKy.getModel();
        String sql = "select * from dangki";
        if (n==1)
            sql = "select * from dangki";
        if (n==2)
            sql = "select * from dangki where masv=N'"+jTextMSSV.getText()+"'";
        model.setRowCount(0);
        try (Connection con = DataBaseHelper.getConnection();
                Statement smt = con.createStatement();) {
            Vector vt;
            ResultSet rs = smt.executeQuery(sql);
            while (rs.next()) {
                vt = new Vector();
                vt.add(rs.getString(1));
                vt.add(rs.getString(2));
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

    public boolean insertDangki()
    {
        String sql = "insert into dangki values(?,?,?,?,?,?)";
        int row = jTableDSLTC.getSelectedRow();
        try (Connection con = DataBaseHelper.getConnection();
            PreparedStatement smt = con.prepareStatement(sql);) {
            
            smt.setString(1, jTableDSLTC.getValueAt(row, 0).toString().toUpperCase());
            smt.setString(2, jTextMSSV.getText());
            smt.setString(3, jTextCC.getText());
            smt.setString(4, jTextTH.getText());
            smt.setString(5, jTextCK.getText());
            if (jRadioButton1.isSelected()) {
                smt.setBoolean(6, false);
            } else {
                smt.setBoolean(6, true);
            }

            smt.executeUpdate();  
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog
                    (this,jTableDSSVDangKy.getValueAt(row, 0).toString().toUpperCase()+" đã được "
                        +jTableDSSVDangKy.getValueAt(row, 1).toString().toUpperCase()+" đăng ký!");
//            JOptionPane.showMessageDialog(this, ex.toString());
            return false;           
        }
        return true;
    }
    
    public void xoaDangKiTheoSV() {
        int row = jTableDSSVDangKy.getSelectedRow();
        String sql = "delete from dangki where maltc=? and masv=?";
        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
            smt.setString(1, jTableDSSVDangKy.getValueAt(row, 0).toString().toUpperCase());
            smt.setString(2, jTableDSSVDangKy.getValueAt(row, 1).toString().toUpperCase());
            smt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        } 
    }  
    
    public void lamMoi() {
        jTextMSSV.setText("");
        jTextMSSV.setEnabled(true);
        jTextCC.setText("");
        jTextTH.setText("");
        jTextCK.setText("");
        jRadioButton1.setSelected(true);
        jButtonXoa.setEnabled(false);
        jButtonSua.setEnabled(false);   
        timLTC.setText("");
        jTableDSLTC.setEnabled(true);
        initData(1);
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
        jButtonLamMoi = new javax.swing.JButton();
        jButtonThem = new javax.swing.JButton();
        jButtonSua = new javax.swing.JButton();
        jButtonXoa = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDSLTC = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextMSSV = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        timLTC = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButtonTimKiem = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 204));
        setPreferredSize(new java.awt.Dimension(1050, 700));

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setMinimumSize(new java.awt.Dimension(89, 32));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(89, 32));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Đăng Ký");
        jPanel1.add(jLabel1, new java.awt.GridBagConstraints());

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridLayout(0, 2));

        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel4.setText("            Điểm CC:");
        jPanel2.add(jLabel4);

        jTextCC.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCCActionPerformed(evt);
            }
        });
        jPanel2.add(jTextCC);

        jLabel6.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel6.setText("            Điểm TH:");
        jPanel2.add(jLabel6);

        jTextTH.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextTH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextTHActionPerformed(evt);
            }
        });
        jPanel2.add(jTextTH);

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel5.setText("            Điểm CK:");
        jPanel2.add(jLabel5);

        jTextCK.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextCK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCKActionPerformed(evt);
            }
        });
        jPanel2.add(jTextCK);

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel2.setText("                   Hủy:");
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
                "LopTinChi", "MSSV", "DiemCC", "DiemTH", "DiemCK", "Huy"
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

        jButtonLamMoi.setBackground(new java.awt.Color(0, 102, 204));
        jButtonLamMoi.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLamMoi.setText("Làm Mới");
        jButtonLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLamMoiActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonLamMoi);

        jButtonThem.setBackground(new java.awt.Color(0, 102, 204));
        jButtonThem.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonThem.setForeground(new java.awt.Color(255, 255, 255));
        jButtonThem.setText("Thêm");
        jButtonThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonThemActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonThem);

        jButtonSua.setBackground(new java.awt.Color(0, 102, 204));
        jButtonSua.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonSua.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSua.setText("Sửa");
        jButtonSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuaActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonSua);

        jButtonXoa.setBackground(new java.awt.Color(0, 102, 204));
        jButtonXoa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonXoa.setForeground(new java.awt.Color(255, 255, 255));
        jButtonXoa.setText("Xóa");
        jButtonXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoaActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonXoa);

        jTableDSLTC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MALTC", "NAMHOC", "HOCKI", "SOLGTOITHIEU", "SOLGTOIDA", "NGAYBD", "NGAYKT", "TENMH"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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

        jPanel5.setBackground(new java.awt.Color(255, 255, 204));
        jPanel5.setOpaque(false);
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        jLabel7.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel7.setText("     MSSV:");
        jLabel7.setMaximumSize(new java.awt.Dimension(90, 22));
        jLabel7.setPreferredSize(new java.awt.Dimension(90, 13));
        jPanel5.add(jLabel7);

        jTextMSSV.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextMSSV.setPreferredSize(new java.awt.Dimension(79, 13));
        jTextMSSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextMSSVActionPerformed(evt);
            }
        });
        jPanel5.add(jTextMSSV);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel3.setText("Danh sách LTC hiện có:");

        timLTC.setForeground(new java.awt.Color(255, 0, 0));
        timLTC.setText("       ");

        jLabel8.setText("LTC chọn:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel10.setText("Danh sách đăng ký:");

        jButtonTimKiem.setText("Tìm kiếm");
        jButtonTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(800, 800, 800))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(818, 818, 818))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(timLTC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonTimKiem)
                                .addGap(268, 268, 268)))
                        .addGap(105, 105, 105)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonTimKiem)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 14, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(timLTC, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                        .addGap(14, 14, 14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextMSSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextMSSVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextMSSVActionPerformed

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

    private void jButtonLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLamMoiActionPerformed
        lamMoi();
        jButtonThem.setEnabled(true);
        jTableDSSVDangKy.setRowSelectionAllowed(false);
        jTableDSLTC.setRowSelectionAllowed(false);  
    }//GEN-LAST:event_jButtonLamMoiActionPerformed

    private void jButtonThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemActionPerformed
        
        if ("".equals(jTextMSSV.getText()))
        {
            JOptionPane.showMessageDialog(this, "MSSV không được để trống.");
            return;
        }
        
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
        if (insertDangki())
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        lamMoi();
        
    }//GEN-LAST:event_jButtonThemActionPerformed

    private void jButtonXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaActionPerformed
       
        int kt = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa?");
        if (kt == JOptionPane.CANCEL_OPTION) {
            return;
        } else if (kt == JOptionPane.OK_OPTION) {
            xoaDangKiTheoSV();
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            lamMoi();
        }
    }//GEN-LAST:event_jButtonXoaActionPerformed

    private void jButtonSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaActionPerformed
        // TODO add your handling code here:
        int row = jTableDSLTC.getSelectedRow();
        int kt = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa?");
        if (kt == JOptionPane.OK_OPTION) {
//            xoaDangKiTheoSV();
//            insertDangki();
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
                
                int kt2 = smt.executeUpdate();
                if (kt2 > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                }
                lamMoi();
            } catch (SQLException ex) {
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
            }
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin đăng ký thành công");
            lamMoi();    
        }
    }//GEN-LAST:event_jButtonSuaActionPerformed

    private void jTableDSLTCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDSLTCMouseClicked

        jTableDSLTC.setRowSelectionAllowed(true);
        int row = jTableDSLTC.getSelectedRow();
        timLTC.setText(jTableDSLTC.getValueAt(row, 0).toString().toUpperCase());
    }//GEN-LAST:event_jTableDSLTCMouseClicked

    private void jTableDSSVDangKyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDSSVDangKyMouseClicked
        jTextMSSV.setEnabled(false);
        jButtonThem.setEnabled(false);
        jButtonXoa.setEnabled(true);
        jButtonSua.setEnabled(true);
        jTableDSSVDangKy.setRowSelectionAllowed(true);
        jTableDSLTC.setEnabled(false);
        int row = jTableDSSVDangKy.getSelectedRow();
        jTextMSSV.setText(jTableDSSVDangKy.getValueAt(row, 1).toString().toUpperCase());
        timLTC.setText(jTableDSSVDangKy.getValueAt(row, 0).toString().toUpperCase());
        jTextCC.setText(jTableDSSVDangKy.getValueAt(row, 2).toString());
        jTextTH.setText(jTableDSSVDangKy.getValueAt(row, 3).toString());
        jTextCK.setText(jTableDSSVDangKy.getValueAt(row, 4).toString());
        if ("False".equals(jTableDSSVDangKy.getValueAt(row, 5).toString()))
            jRadioButton1.setSelected(true);
        else 
            jRadioButton2.setSelected(true);
                
    }//GEN-LAST:event_jTableDSSVDangKyMouseClicked

    private void jButtonTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTimKiemActionPerformed
    if ("".equals(jTextMSSV.getText()))
        JOptionPane.showMessageDialog(this, "MSSV không được để trống.");
    else
        initData(2);
    }//GEN-LAST:event_jButtonTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupHuy;
    private javax.swing.JButton jButtonLamMoi;
    private javax.swing.JButton jButtonSua;
    private javax.swing.JButton jButtonThem;
    private javax.swing.JButton jButtonTimKiem;
    private javax.swing.JButton jButtonXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableDSLTC;
    private javax.swing.JTable jTableDSSVDangKy;
    private javax.swing.JTextField jTextCC;
    private javax.swing.JTextField jTextCK;
    private javax.swing.JTextField jTextMSSV;
    private javax.swing.JTextField jTextTH;
    private javax.swing.JLabel timLTC;
    // End of variables declaration//GEN-END:variables
}
