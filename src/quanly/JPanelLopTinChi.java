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
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

/**
 *
 * @author Admin
 */
public class JPanelLopTinChi extends javax.swing.JPanel {

    /**
     * Creates new form JPanelLopTinChi
     */
    DefaultTableModel model = new DefaultTableModel();
    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
    Date date = java.util.Calendar.getInstance().getTime();

    public JPanelLopTinChi() {
        initComponents();
        initData();
        initjComboBoxMaMH();
        jLabelMaLTC.setText(taoMaLTC());
        jLabelMaLTC.setForeground(Color.RED);
        jButtonXoa.setEnabled(false);
        jButtonSua.setEnabled(false);
        jPanelGv1.setVisible(false);
        jPanelGv2.setVisible(false);
        jPanelGv3.setVisible(false);
        jSpinnerSlGv.setEnabled(false);
        jButtonLocGV.setEnabled(false);

    }

    public void initjComboBoxMaMH() {
        String sql = "select tenmh from monhoc";
        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
            ResultSet rs = smt.executeQuery();
            while (rs.next()) {
                jComboBoxMonHoc.addItem(rs.getString(1));
            }
            jComboBoxMonHoc.setSelectedIndex(-1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void initData() {
        model = (DefaultTableModel) jTableDSLTC.getModel();
        String sql = "select * from loptinchi";
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
                vt.add(rs.getString(6));
                vt.add(rs.getString(7));
                vt.add(searchTenMonHoc(rs.getString(8)));
                model.addRow(vt);
            }
            jTableDSLTC.setModel(model);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void timKiemltc(String s)
    {   
        model = (DefaultTableModel) jTableDSLTC.getModel();
        String sql = "select * from loptinchi where maltc like N'%"+s+"%' or namhoc like N'%"+s+"%' or hocki like N'%"+s+"%'";
        model.setRowCount(0);
        try(Connection con = DataBaseHelper.getConnection();
                Statement smt = con.createStatement();)
        {   
            Vector vt;
            ResultSet rs = smt.executeQuery(sql);
            while (rs.next()) {
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
            ex.printStackTrace();
        }
        System.out.println("" + sql);
        
    }

    public String taoMaLTC() {
        String sql = "select maltc from loptinchi";
        int max = 0;
        try (Connection con = DataBaseHelper.getConnection();
                Statement smt = con.createStatement();) {
            ResultSet rs = smt.executeQuery(sql);
            while (rs.next()) {
                int ma = Integer.parseInt(rs.getString(1).substring(3, rs.getString(1).length()).trim());
                if (ma > max) {
                    max = ma;
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "LTC" + String.valueOf(max + 1);
    }

    public String taoMaMonHoc() {
        String sql = "select mamh from monhoc";
        int max = 0;
        try (Connection con = DataBaseHelper.getConnection();
                Statement smt = con.createStatement();) {
            ResultSet rs = smt.executeQuery(sql);
            while (rs.next()) {
                int ma = Integer.parseInt(rs.getString(1).substring(2, rs.getString(1).length()).trim());
                if (ma > max) {
                    max = ma;
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "MH" + String.valueOf(max + 1);
    }

    public String searchMaMonHoc(String tenMH) {
        String sql = " SELECT mamh FROM monhoc WHERE tenmh=N'" + tenMH + "'";
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
            ex.printStackTrace();
        }
        return null;
    }

    public void lamMoi() {
        jLabelMaLTC.setText(taoMaLTC());
        jTextNamHoc.setText("");
        jComboBoxHocKi.setSelectedIndex(-1);
        jTextSLToiThieu.setText("");
        jTextSLToiDa.setText("");
        jComboBoxMonHoc.setSelectedIndex(-1);
        jDateBd.setDate(date);
        jDateKt.setDate(date);
        jButtonSua.setEnabled(false);
        jButtonXoa.setEnabled(false);
        jTableDSLTC.setRowSelectionAllowed(false);
    }

    public void initjComboBoxGvDayMH(JComboBox comBox) {
        comBox.removeAllItems();
        String sql = "{call DS_GV_DAYMH(?)}";
        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
            smt.setString(1, searchMaMonHoc(jComboBoxMonHoc.getSelectedItem().toString()));
            ResultSet rs = smt.executeQuery();
            while (rs.next()) {
                comBox.addItem(rs.getString(1) + " " + rs.getString(2));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public String hotenGv(String maGv)
    {
        String sql = " SELECT hoten FROM giangvien WHERE magv=N'" + maGv + "'";
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

    public void xoaPhanCongTheoTinChi() {
        String sql = "delete from phancong where maltc=?";
        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
            smt.setString(1, jLabelMaLTC.getText());
            int kt2 = smt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insertPhanCong() {
        String sql2 = "insert into phancong values(?,?,?,?)";
        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql2);) {
            int kt=0;
            if (jPanelGv1.isVisible()) {
                smt.setString(1, jLabelMaLTC.getText());
                smt.setString(2, jComboBoxGv1.getSelectedItem().toString().split(" ")[0]);
                smt.setString(3, (jCheckBoxLt1.isSelected()) ? "1" : "0");
                smt.setString(4, (jCheckBoxTh1.isSelected()) ? "1" : "0");
                kt = smt.executeUpdate();
            }
            if (jPanelGv2.isVisible()) {
                smt.setString(1, jLabelMaLTC.getText());
                smt.setString(2, jComboBoxGv2.getSelectedItem().toString().split(" ")[0]);
                smt.setString(3, (jCheckBoxLt2.isSelected()) ? "1" : "0");
                smt.setString(4, (jCheckBoxTh2.isSelected()) ? "1" : "0");
                 kt = smt.executeUpdate();
            }
            if (jPanelGv3.isVisible()) {
                smt.setString(1, jLabelMaLTC.getText());
                smt.setString(2, jComboBoxGv3.getSelectedItem().toString().split(" ")[0]);
                smt.setString(3, (jCheckBoxLt3.isSelected()) ? "1" : "0");
                smt.setString(4, (jCheckBoxTh3.isSelected()) ? "1" : "0");
                 kt = smt.executeUpdate();
            }

            
            if (kt > 0) {
                JOptionPane.showMessageDialog(this, "Cap nhat phan cong theo lop tin chi thanh cong");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void phanCongGv1Dong(JComboBox combox,JCheckBox checkBox1,JCheckBox checkBox2,ResultSet rs) throws SQLException
    {
        combox.setSelectedItem(rs.getString(2) + " " + hotenGv(rs.getString(2)));
        System.out.println("" + rs.getString(2) + " " + hotenGv(rs.getString(2)));

        if(rs.getString(3).equals("1"))
            checkBox1.setSelected(true);
        else
            checkBox1.setSelected(false);

        if(rs.getString(4).equals("1"))
            checkBox2.setSelected(true);
        else
            checkBox2.setSelected(false);
    }

    public void phanCong() {
        String sql = "select * from phancong where maltc=?";
        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {

            smt.setString(1, jLabelMaLTC.getText());
            ResultSet rs = smt.executeQuery();
            int row=0;
            while(rs.next())
            {   
                ++row;
            }
            
            int soLuong;
            jSpinnerSlGv.setValue(row);
            jSpinnerSlGv.setEnabled(true);
            soLuong = row;
            ResultSet rs2 = smt.executeQuery();
            
            if (soLuong == 0) {
                jPanelGv1.setVisible(false);
                jPanelGv2.setVisible(false);
                jPanelGv3.setVisible(false);
            } 
            else if (soLuong == 1) {
                jPanelGv1.setVisible(true);
                jPanelGv2.setVisible(false);
                jPanelGv3.setVisible(false);
                initjComboBoxGvDayMH(jComboBoxGv1);
                while(rs2.next())
                    phanCongGv1Dong(jComboBoxGv1, jCheckBoxLt1, jCheckBoxTh1, rs2);
            } 
            else if (soLuong == 2) {
                jPanelGv1.setVisible(true);
                jPanelGv2.setVisible(true);
                jPanelGv3.setVisible(false);
                initjComboBoxGvDayMH(jComboBoxGv1);
                initjComboBoxGvDayMH(jComboBoxGv2);
                jComboBoxGv2.removeItem(jComboBoxGv1.getSelectedItem());
                rs2.next();
                phanCongGv1Dong(jComboBoxGv1, jCheckBoxLt1, jCheckBoxTh1, rs2);
                rs2.next();
                phanCongGv1Dong(jComboBoxGv2, jCheckBoxLt2, jCheckBoxTh2, rs2);
                

            } else if (soLuong == 3) {
                jPanelGv1.setVisible(true);
                jPanelGv2.setVisible(true);
                jPanelGv3.setVisible(true);
                initjComboBoxGvDayMH(jComboBoxGv1);
                initjComboBoxGvDayMH(jComboBoxGv2);
                initjComboBoxGvDayMH(jComboBoxGv3);

                jComboBoxGv2.removeItem(jComboBoxGv1.getSelectedItem());
                jComboBoxGv3.removeItem(jComboBoxGv1.getSelectedItem());
                jComboBoxGv3.removeItem(jComboBoxGv2.getSelectedItem());
                rs2.next();
                phanCongGv1Dong(jComboBoxGv1, jCheckBoxLt1, jCheckBoxTh1, rs2);
                rs2.next();
                phanCongGv1Dong(jComboBoxGv2, jCheckBoxLt2, jCheckBoxTh2, rs2);
                rs2.next();
                phanCongGv1Dong(jComboBoxGv3, jCheckBoxLt3, jCheckBoxTh3, rs2);


            }
            
            
            
            
            

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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

        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        label1 = new java.awt.Label();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabelMaLTC = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextNamHoc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxHocKi = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jTextSLToiThieu = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextSLToiDa = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jDateBd = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jDateKt = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jComboBoxMonHoc = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDSLTC = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButtonLamMoi = new javax.swing.JButton();
        jButtonThem = new javax.swing.JButton();
        jButtonSua = new javax.swing.JButton();
        jButtonXoa = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSpinnerSlGv = new javax.swing.JSpinner();
        jPanelGv1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jCheckBoxLt1 = new javax.swing.JCheckBox();
        jCheckBoxTh1 = new javax.swing.JCheckBox();
        jComboBoxGv1 = new javax.swing.JComboBox<>();
        jPanelGv3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jComboBoxGv3 = new javax.swing.JComboBox<>();
        jCheckBoxLt3 = new javax.swing.JCheckBox();
        jCheckBoxTh3 = new javax.swing.JCheckBox();
        jPanelGv2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jComboBoxGv2 = new javax.swing.JComboBox<>();
        jCheckBoxLt2 = new javax.swing.JCheckBox();
        jCheckBoxTh2 = new javax.swing.JCheckBox();
        jButtonLocGV = new javax.swing.JButton();
        jTextTimKiem = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();

        jFormattedTextField1.setText("jFormattedTextField1");

        label1.setText("label1");

        setBackground(new java.awt.Color(204, 255, 255));
        setForeground(new java.awt.Color(0, 102, 204));
        setPreferredSize(new java.awt.Dimension(1050, 700));

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(89, 32));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Lớp Tín Chỉ ");
        jPanel1.add(jLabel1, new java.awt.GridBagConstraints());

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridLayout(0, 2));

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel2.setText("Mã LTC:");
        jPanel2.add(jLabel2);
        jPanel2.add(jLabelMaLTC);

        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel4.setText("Năm học:");
        jPanel2.add(jLabel4);

        jTextNamHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNamHocActionPerformed(evt);
            }
        });
        jPanel2.add(jTextNamHoc);

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel5.setText("Học Kì:");
        jPanel2.add(jLabel5);

        jComboBoxHocKi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HK1", "HK2" }));
        jPanel2.add(jComboBoxHocKi);

        jLabel6.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel6.setText("SL tối thiểu:");
        jPanel2.add(jLabel6);
        jPanel2.add(jTextSLToiThieu);

        jLabel7.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel7.setText("SL tối đa:");
        jPanel2.add(jLabel7);
        jPanel2.add(jTextSLToiDa);

        jLabel8.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel8.setText("Ngày BĐ:");
        jPanel2.add(jLabel8);
        jPanel2.add(jDateBd);

        jLabel9.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel9.setText("Ngày KT:");
        jPanel2.add(jLabel9);
        jPanel2.add(jDateKt);

        jLabel10.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel10.setText("Thuộc môn học:");
        jPanel2.add(jLabel10);

        jComboBoxMonHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMonHocActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBoxMonHoc);

        jTableDSLTC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MALTC", "NAMHOC", "HOCKI", "SOLGTOITHIEU", "SOLGTOIDA", "NGAYBD", "NGAYKT", "TENMH"
            }
        ));
        jTableDSLTC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDSLTCMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTableDSLTCMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(jTableDSLTC);

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        jButtonLamMoi.setBackground(new java.awt.Color(0, 102, 204));
        jButtonLamMoi.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButtonLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLamMoi.setText("Làm Mới");
        jButtonLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLamMoiActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonLamMoi);

        jButtonThem.setBackground(new java.awt.Color(0, 102, 204));
        jButtonThem.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButtonThem.setForeground(new java.awt.Color(255, 255, 255));
        jButtonThem.setText("Thêm");
        jButtonThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonThemActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonThem);

        jButtonSua.setBackground(new java.awt.Color(0, 102, 204));
        jButtonSua.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButtonSua.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSua.setText("Sửa");
        jButtonSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuaActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonSua);

        jButtonXoa.setBackground(new java.awt.Color(0, 102, 204));
        jButtonXoa.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButtonXoa.setForeground(new java.awt.Color(255, 255, 255));
        jButtonXoa.setText("Xóa");
        jButtonXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoaActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonXoa);

        jPanel4.setBackground(new java.awt.Color(0, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Phân Công", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16), new java.awt.Color(255, 51, 255))); // NOI18N
        jPanel4.setForeground(new java.awt.Color(0, 255, 255));

        jLabel3.setText("Số Lượng GV:");

        jSpinnerSlGv.setModel(new javax.swing.SpinnerNumberModel(0, 0, 3, 1));
        jSpinnerSlGv.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerSlGvStateChanged(evt);
            }
        });

        jPanelGv1.setBackground(new java.awt.Color(0, 153, 255));

        jLabel11.setText("Giảng Viên 1:");

        jCheckBoxLt1.setSelected(true);
        jCheckBoxLt1.setText("Dạy lý thuyết");
        jCheckBoxLt1.setOpaque(false);

        jCheckBoxTh1.setSelected(true);
        jCheckBoxTh1.setText("Dạy thực hành");
        jCheckBoxTh1.setOpaque(false);

        jComboBoxGv1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxGv1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelGv1Layout = new javax.swing.GroupLayout(jPanelGv1);
        jPanelGv1.setLayout(jPanelGv1Layout);
        jPanelGv1Layout.setHorizontalGroup(
            jPanelGv1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGv1Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(34, 34, 34)
                .addComponent(jComboBoxGv1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxLt1)
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxTh1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelGv1Layout.setVerticalGroup(
            jPanelGv1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGv1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel11)
                .addComponent(jCheckBoxLt1)
                .addComponent(jCheckBoxTh1)
                .addComponent(jComboBoxGv1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanelGv3.setBackground(new java.awt.Color(0, 153, 255));

        jLabel13.setText("Giảng Viên 3:");

        jCheckBoxLt3.setSelected(true);
        jCheckBoxLt3.setText("Dạy lý thuyết");
        jCheckBoxLt3.setOpaque(false);

        jCheckBoxTh3.setSelected(true);
        jCheckBoxTh3.setText("Dạy thực hành");
        jCheckBoxTh3.setOpaque(false);

        javax.swing.GroupLayout jPanelGv3Layout = new javax.swing.GroupLayout(jPanelGv3);
        jPanelGv3.setLayout(jPanelGv3Layout);
        jPanelGv3Layout.setHorizontalGroup(
            jPanelGv3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGv3Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addGap(36, 36, 36)
                .addComponent(jComboBoxGv3, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxLt3)
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxTh3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelGv3Layout.setVerticalGroup(
            jPanelGv3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGv3Layout.createSequentialGroup()
                .addGroup(jPanelGv3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(jPanelGv3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBoxGv3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBoxLt3)
                        .addComponent(jCheckBoxTh3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanelGv2.setBackground(new java.awt.Color(0, 153, 255));

        jLabel12.setText("Giảng Viên 2:");

        jCheckBoxLt2.setSelected(true);
        jCheckBoxLt2.setText("Dạy lý thuyết");
        jCheckBoxLt2.setOpaque(false);

        jCheckBoxTh2.setSelected(true);
        jCheckBoxTh2.setText("Dạy thực hành");
        jCheckBoxTh2.setOpaque(false);

        javax.swing.GroupLayout jPanelGv2Layout = new javax.swing.GroupLayout(jPanelGv2);
        jPanelGv2.setLayout(jPanelGv2Layout);
        jPanelGv2Layout.setHorizontalGroup(
            jPanelGv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGv2Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addGap(33, 33, 33)
                .addComponent(jComboBoxGv2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxLt2)
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxTh2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelGv2Layout.setVerticalGroup(
            jPanelGv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel12)
                .addComponent(jComboBoxGv2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jCheckBoxLt2)
                .addComponent(jCheckBoxTh2))
        );

        jButtonLocGV.setText("Lọc Gv dạy theo môn");
        jButtonLocGV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLocGVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelGv2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelGv3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelGv1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(32, 32, 32)
                                .addComponent(jSpinnerSlGv, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButtonLocGV))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonLocGV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jSpinnerSlGv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelGv1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelGv2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelGv3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        jLabel14.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel14.setText("Tìm Kiếm:");
        jLabel14.setMaximumSize(new java.awt.Dimension(90, 22));
        jLabel14.setPreferredSize(new java.awt.Dimension(90, 13));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGap(4, 4, 4)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextNamHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNamHocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNamHocActionPerformed

    private void jTableDSLTCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDSLTCMouseClicked
        int row = jTableDSLTC.getSelectedRow();
        jButtonThem.setEnabled(false);
        jButtonXoa.setEnabled(true);
        jButtonSua.setEnabled(true);
        jTableDSLTC.setRowSelectionAllowed(true);
        if (row >= 0) {
            try {
                jLabelMaLTC.setText(jTableDSLTC.getValueAt(row, 0).toString().toUpperCase());
                jTextNamHoc.setText(jTableDSLTC.getValueAt(row, 1).toString());
                jComboBoxHocKi.setSelectedItem(jTableDSLTC.getValueAt(row, 2).toString());
                jTextSLToiThieu.setText(jTableDSLTC.getValueAt(row, 3).toString());
                jTextSLToiDa.setText(jTableDSLTC.getValueAt(row, 4).toString());

                //jTextNgayBD.setText(jTableDSLTC.getValueAt(row, 5).toString());
                Date ngaybd = ft.parse((String) jTableDSLTC.getValueAt(row, 5));
                jDateBd.setDate(ngaybd);
                //jDateBd.setDateFormatString( jTableDSLTC.getValueAt(row, 5).toString());
                Date ngaykt = ft.parse((String) jTableDSLTC.getValueAt(row, 6));
                //jDateKt.setDateFormatString(jTableDSLTC.getValueAt(row, 6).toString());
                jDateKt.setDate(ngaykt);

                jComboBoxMonHoc.setSelectedItem(jTableDSLTC.getValueAt(row, 7).toString());
            } catch (ParseException ex) {
                Logger.getLogger(JPanelLopTinChi.class.getName()).log(Level.SEVERE, null, ex);
            }
          phanCong();
        }
    }//GEN-LAST:event_jTableDSLTCMouseClicked

    private void jButtonLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLamMoiActionPerformed
        lamMoi();
        jButtonThem.setEnabled(true);
    }//GEN-LAST:event_jButtonLamMoiActionPerformed

    private void jButtonThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemActionPerformed
        String sql = "insert into loptinchi values(?,?,?,?,?,?,?,?)";
        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
            smt.setString(1, taoMaLTC());
            smt.setString(2, jTextNamHoc.getText());
            smt.setString(3, jComboBoxHocKi.getSelectedItem().toString());
            smt.setInt(4, Integer.parseInt(jTextSLToiThieu.getText()));
            smt.setInt(5, Integer.parseInt(jTextSLToiDa.getText()));
            smt.setString(6, ft.format(jDateBd.getDate()));
            smt.setString(7, ft.format(jDateKt.getDate()));
            smt.setString(8, searchMaMonHoc(jComboBoxMonHoc.getSelectedItem().toString()));

            int kt = smt.executeUpdate();
            if (kt > 0) {
                JOptionPane.showMessageDialog(this, "insert thanh cong");
            }
            insertPhanCong();
            initData();
            lamMoi();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }//GEN-LAST:event_jButtonThemActionPerformed

    private void jButtonXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaActionPerformed
        int kt = JOptionPane.showConfirmDialog(this, "Ban co muon xoa khong?");
        if (kt == JOptionPane.OK_OPTION) {
            String sql = "delete from loptinchi where maltc=?";
            try (Connection con = DataBaseHelper.getConnection();
                    PreparedStatement smt = con.prepareStatement(sql);) {
                smt.setString(1, jLabelMaLTC.getText());

                int kt2 = smt.executeUpdate();
                if (kt2 > 0) {
                    JOptionPane.showMessageDialog(this, "Xoa thanh cong!");
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
        if (kt == JOptionPane.OK_OPTION) {
            String sql = "update loptinchi set [namhoc]=?,[hocki]=?,[sltoithieu]=?, [sltoida]=?, [ngaybd]=?, [ngaykt]=?, [mamh]=? where [maltc] = ?";
            try (Connection con = DataBaseHelper.getConnection();
                    PreparedStatement smt = con.prepareStatement(sql);) {

                smt.setString(1, jTextNamHoc.getText());
                smt.setString(2, jComboBoxHocKi.getSelectedItem().toString());
                smt.setInt(3, Integer.parseInt(jTextSLToiThieu.getText()));
                smt.setInt(4, Integer.parseInt(jTextSLToiDa.getText()));
                smt.setString(5, ft.format(jDateBd.getDate()));
                smt.setString(6, ft.format(jDateKt.getDate()));
                smt.setString(7, searchMaMonHoc(jComboBoxMonHoc.getSelectedItem().toString()));
                smt.setString(8, jLabelMaLTC.getText());

                int kt2 = smt.executeUpdate();
                if (kt2 > 0) {
                    JOptionPane.showMessageDialog(this, "update thanh cong");
                }
                xoaPhanCongTheoTinChi();
                insertPhanCong();
                initData();
                lamMoi();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.toString());
            }

        }
    }//GEN-LAST:event_jButtonSuaActionPerformed

    private void jTableDSLTCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDSLTCMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableDSLTCMouseEntered

    private void jSpinnerSlGvStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerSlGvStateChanged
        int soLuong = (int) jSpinnerSlGv.getValue();

        if (soLuong == 0) {
            jPanelGv1.setVisible(false);
            jPanelGv2.setVisible(false);
            jPanelGv3.setVisible(false);
        } else if (soLuong == 1) {
            jPanelGv1.setVisible(true);
            jPanelGv2.setVisible(false);
            jPanelGv3.setVisible(false);
            initjComboBoxGvDayMH(jComboBoxGv1);
        } else if (soLuong == 2) {
            jPanelGv1.setVisible(true);
            jPanelGv2.setVisible(true);
            jPanelGv3.setVisible(false);
            initjComboBoxGvDayMH(jComboBoxGv1);
            initjComboBoxGvDayMH(jComboBoxGv2);
            jComboBoxGv2.removeItem(jComboBoxGv1.getSelectedItem());

        } else if (soLuong == 3) {
            jPanelGv1.setVisible(true);
            jPanelGv2.setVisible(true);
            jPanelGv3.setVisible(true);
            initjComboBoxGvDayMH(jComboBoxGv1);
            initjComboBoxGvDayMH(jComboBoxGv2);
            jComboBoxGv2.removeItem(jComboBoxGv1.getSelectedItem());
            initjComboBoxGvDayMH(jComboBoxGv3);
            jComboBoxGv3.removeItem(jComboBoxGv1.getSelectedItem());
            jComboBoxGv3.removeItem(jComboBoxGv2.getSelectedItem());

        }
    }//GEN-LAST:event_jSpinnerSlGvStateChanged

    private void jComboBoxGv1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxGv1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxGv1ActionPerformed

    private void jComboBoxMonHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMonHocActionPerformed
        // TODO add your handling code here:
        jButtonLocGV.setEnabled(true);
        jSpinnerSlGv.setEnabled(false);
        jPanelGv1.setVisible(false);
        jPanelGv2.setVisible(false);
        jPanelGv3.setVisible(false);


    }//GEN-LAST:event_jComboBoxMonHocActionPerformed

    private void jButtonLocGVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLocGVActionPerformed
        // TODO add your handling code here:
        jSpinnerSlGv.setEnabled(true);
        jSpinnerSlGv.setValue(0);

    }//GEN-LAST:event_jButtonLocGVActionPerformed

    private void jTextTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextTimKiemActionPerformed

    private void jTextTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextTimKiemKeyReleased
        // TODO add your handling code here:
        timKiemltc(jTextTimKiem.getText());
    }//GEN-LAST:event_jTextTimKiemKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLamMoi;
    private javax.swing.JButton jButtonLocGV;
    private javax.swing.JButton jButtonSua;
    private javax.swing.JButton jButtonThem;
    private javax.swing.JButton jButtonXoa;
    private javax.swing.JCheckBox jCheckBoxLt1;
    private javax.swing.JCheckBox jCheckBoxLt2;
    private javax.swing.JCheckBox jCheckBoxLt3;
    private javax.swing.JCheckBox jCheckBoxTh1;
    private javax.swing.JCheckBox jCheckBoxTh2;
    private javax.swing.JCheckBox jCheckBoxTh3;
    private javax.swing.JComboBox<String> jComboBoxGv1;
    private javax.swing.JComboBox<String> jComboBoxGv2;
    private javax.swing.JComboBox<String> jComboBoxGv3;
    private javax.swing.JComboBox<String> jComboBoxHocKi;
    private javax.swing.JComboBox<String> jComboBoxMonHoc;
    private com.toedter.calendar.JDateChooser jDateBd;
    private com.toedter.calendar.JDateChooser jDateKt;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelMaLTC;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelGv1;
    private javax.swing.JPanel jPanelGv2;
    private javax.swing.JPanel jPanelGv3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerSlGv;
    private javax.swing.JTable jTableDSLTC;
    private javax.swing.JTextField jTextNamHoc;
    private javax.swing.JTextField jTextSLToiDa;
    private javax.swing.JTextField jTextSLToiThieu;
    private javax.swing.JTextField jTextTimKiem;
    private java.awt.Label label1;
    // End of variables declaration//GEN-END:variables
}
