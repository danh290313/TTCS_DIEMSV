/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanly;

import static dao.Provider.searchMaMonHoc;
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
import javax.swing.ButtonModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class JPanelGiangVien extends javax.swing.JPanel {

    /**
     * Creates new form giangvien
     */
    DefaultTableModel model = new DefaultTableModel();
    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date date = java.util.Calendar.getInstance().getTime();

    public JPanelGiangVien() {
        initComponents();
        initData();
        jLabelMaGV.setText(taoMaGV());
        jLabelMaGV.setForeground(Color.RED);
        jLabelMaGV2.setText(taoMaGV());
        jLabelMaGV2.setForeground(Color.RED);
        jRadioButtonNam.setSelected(true);
        listMh.setMultipleMode(true);
        listMhChon.setMultipleMode(true);
        jButtonXoa.setEnabled(false);
        jButtonSua.setEnabled(false);
        loadDsMh();
        loadDsMhChon();
    }

    public void loadDsMh() {
        listMh.removeAll();
        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement("{call DS_MH_CHUACHON_GV(?)}");) {

            smt.setString(1, jLabelMaGV.getText());
            ResultSet rs = smt.executeQuery();

            while (rs.next()) {
                listMh.add(rs.getString(1));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }

    public void loadDsMhChon() {
        listMhChon.removeAll();
        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement("{call DS_MHCHON_GV(?)}");) {

            smt.setString(1, jLabelMaGV.getText());
            ResultSet rs = smt.executeQuery();

            while (rs.next()) {
                listMhChon.add(rs.getString(1));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }

    public boolean checkMhChon(String tenMh) {

        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement("{call DS_MHCHON_GV(?)}");) {

            smt.setString(1, jLabelMaGV.getText());
            ResultSet rs = smt.executeQuery();

            while (rs.next()) {
                if (rs.getString(1).equals(tenMh)) {
                    return true;
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
        return false;
    }

    public void xoaDayTheoGṿ̣() {
        String sql = "delete from day where magv=?";
        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
            smt.setString(1, jLabelMaGV.getText());
            int kt2 = smt.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }

    public void initData() {
        model = (DefaultTableModel) jTableDSGV.getModel();
        String sql = "select * from giangvien";
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
                vt.add((rs.getString(5).equals("1")) ? "Nam" : "Nữ");
                vt.add(rs.getString(6));
                vt.add(rs.getString(7));
                vt.add(rs.getString(8));
                vt.add(rs.getString(9).equals("1") ? "Đang làm việc" : "Đã nghỉ");
                model.addRow(vt);
            }
            jTableDSGV.setModel(model);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }

    public String taoMaGV() {
        String sql = "select magv from giangvien";
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
            JOptionPane.showMessageDialog(this, ex.toString());
        }
        return "GV" + String.valueOf(max + 1);
    }


    public void lamMoi() {
        jLabelMaGV.setText(taoMaGV());
        jLabelMaGV2.setText(taoMaGV());
        jTextHoTen.setText("");
        jComboBoxHocVi.setSelectedIndex(-1);
        jComboBoxHocHam.setSelectedIndex(-1);
        jRadioButtonNam.setSelected(true);
        jDateNgaySinh.setDate(date);
        jTextDiaChi.setText("");
        jTextChuyenMon.setText("");
        jRadioButtonDangLam.setSelected(true);
        jButtonXoa.setEnabled(false);
        jButtonSua.setEnabled(false);
        
    }
    
    public void insertDay()
    {
        String sql2 = "insert into day values(?,?)";
        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql2)) {
            int kt = 0;
            for (String mh : listMhChon.getItems()) {

                smt.setString(1, jLabelMaGV.getText());
                smt.setString(2, searchMaMonHoc(mh));
                kt = smt.executeUpdate();

            }

            if (kt > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật những môn giáo viên dạy thành công");
            }
            //initDataMhCn();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
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

        buttonGroupPhai = new javax.swing.ButtonGroup();
        buttonGroupTTNghi = new javax.swing.ButtonGroup();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabelMaGV = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextHoTen = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxHocVi = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jComboBoxHocHam = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jRadioButtonNam = new javax.swing.JRadioButton();
        jTextPhai = new javax.swing.JLabel();
        jRadioButtonNu = new javax.swing.JRadioButton();
        jLabel12 = new javax.swing.JLabel();
        jDateNgaySinh = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jTextDiaChi = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextChuyenMon = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jRadioButtonDangLam = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        jRadioButtonDaNghi = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDSGV = new javax.swing.JTable();
        listMh = new java.awt.List();
        jButtonBack = new javax.swing.JButton();
        jButtonGo = new javax.swing.JButton();
        listMhChon = new java.awt.List();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButtonLamMoi = new javax.swing.JButton();
        jButtonThem = new javax.swing.JButton();
        jButtonSua = new javax.swing.JButton();
        jButtonXoa = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabelMaGV2 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setBackground(new java.awt.Color(255, 204, 255));
        setPreferredSize(new java.awt.Dimension(1050, 700));

        jPanel1.setBackground(new java.awt.Color(255, 204, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(89, 32));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(89, 32));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Giảng Viên");
        jPanel1.add(jLabel1, new java.awt.GridBagConstraints());

        jPanel2.setBackground(new java.awt.Color(255, 204, 255));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridLayout(0, 2));

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel2.setText("            Mã GV:");
        jPanel2.add(jLabel2);
        jPanel2.add(jLabelMaGV);

        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel4.setText("            Họ Tên:");
        jPanel2.add(jLabel4);

        jTextHoTen.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextHoTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextHoTenActionPerformed(evt);
            }
        });
        jPanel2.add(jTextHoTen);

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel5.setText("            Học Vị:");
        jPanel2.add(jLabel5);

        jComboBoxHocVi.setEditable(true);
        jComboBoxHocVi.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jComboBoxHocVi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cử nhân", "Thạc sĩ", "Tiến sĩ", "Tiến sĩ khoa học", "Không" }));
        jComboBoxHocVi.setSelectedIndex(-1);
        jPanel2.add(jComboBoxHocVi);

        jLabel11.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel11.setText("            Học Hàm:");
        jPanel2.add(jLabel11);

        jComboBoxHocHam.setEditable(true);
        jComboBoxHocHam.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jComboBoxHocHam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Phó Giáo Sư", "Giáo Sư", "Không" }));
        jComboBoxHocHam.setSelectedIndex(-1);
        jPanel2.add(jComboBoxHocHam);

        jLabel6.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel6.setText("            Phái:");
        jPanel2.add(jLabel6);

        buttonGroupPhai.add(jRadioButtonNam);
        jRadioButtonNam.setSelected(true);
        jRadioButtonNam.setText("Nam");
        jRadioButtonNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonNamActionPerformed(evt);
            }
        });
        jPanel2.add(jRadioButtonNam);
        jPanel2.add(jTextPhai);

        buttonGroupPhai.add(jRadioButtonNu);
        jRadioButtonNu.setText("Nữ");
        jPanel2.add(jRadioButtonNu);

        jLabel12.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel12.setText("            Ngày Sinh:");
        jPanel2.add(jLabel12);
        jPanel2.add(jDateNgaySinh);

        jLabel7.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel7.setText("            Địa chỉ:");
        jPanel2.add(jLabel7);

        jTextDiaChi.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jPanel2.add(jTextDiaChi);

        jLabel8.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel8.setText("             Chuyên Môn:");
        jPanel2.add(jLabel8);

        jTextChuyenMon.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jPanel2.add(jTextChuyenMon);

        jLabel9.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel9.setText("            Trạng Thái Nghỉ:");
        jPanel2.add(jLabel9);

        buttonGroupTTNghi.add(jRadioButtonDangLam);
        jRadioButtonDangLam.setSelected(true);
        jRadioButtonDangLam.setText("Đang làm việc");
        jRadioButtonDangLam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonDangLamActionPerformed(evt);
            }
        });
        jPanel2.add(jRadioButtonDangLam);
        jPanel2.add(jLabel10);

        buttonGroupTTNghi.add(jRadioButtonDaNghi);
        jRadioButtonDaNghi.setText("Đã nghỉ");
        jPanel2.add(jRadioButtonDaNghi);

        jTableDSGV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MAGV", "HOTEN", "HOCVI", "HOCHAM", "PHAI", "NGAYSINH", "DIACHI", "CHUYENMON", "TRANGTHAINGHI"
            }
        ));
        jTableDSGV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDSGVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableDSGV);

        listMh.setBackground(new java.awt.Color(204, 255, 255));
        listMh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listMhActionPerformed(evt);
            }
        });

        jButtonBack.setText("<");
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        jButtonGo.setText(">");
        jButtonGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGoActionPerformed(evt);
            }
        });

        listMhChon.setBackground(new java.awt.Color(204, 255, 255));
        listMhChon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listMhChonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setText("Các môn hiện có:");

        jPanel3.setBackground(new java.awt.Color(255, 204, 255));
        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jPanel4.setBackground(new java.awt.Color(255, 204, 255));
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

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel13.setText("Các môn");

        jLabelMaGV2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel15.setText("dạy:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(listMh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(16, 16, 16)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jButtonBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonGo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(35, 35, 35)
                                .addComponent(listMhChon, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                                .addGap(32, 32, 32))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(99, 99, 99)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addComponent(jLabelMaGV2, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)))
                        .addGap(118, 118, 118))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 978, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelMaGV2, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(listMh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(listMhChon, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButtonBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonGo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(73, 73, 73)))
                        .addGap(108, 108, 108))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 309, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addGap(52, 52, 52)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextHoTenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextHoTenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextHoTenActionPerformed

    private void jTableDSGVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDSGVMouseClicked
        int row = jTableDSGV.getSelectedRow();
        jButtonThem.setEnabled(false);
        jButtonXoa.setEnabled(true);
        jButtonSua.setEnabled(true);
        jTableDSGV.setRowSelectionAllowed(true);
        if (row >= 0) {
            try {
                jLabelMaGV.setText(jTableDSGV.getValueAt(row, 0).toString().toUpperCase());
                jLabelMaGV2.setText(jTableDSGV.getValueAt(row, 0).toString().toUpperCase());
                jTextHoTen.setText(jTableDSGV.getValueAt(row, 1).toString());
                jComboBoxHocVi.setSelectedItem(jTableDSGV.getValueAt(row, 2).toString());
                jComboBoxHocHam.setSelectedItem(jTableDSGV.getValueAt(row, 3).toString());
                if ("Nam".equals(jTableDSGV.getValueAt(row, 4).toString())) {
                    jRadioButtonNam.setSelected(true);
                } else {
                    jRadioButtonNu.setSelected(true);
                }
                jDateNgaySinh.setDate(ft.parse(jTableDSGV.getValueAt(row, 5).toString()));
                jTextDiaChi.setText(jTableDSGV.getValueAt(row, 6).toString());
                jTextChuyenMon.setText(jTableDSGV.getValueAt(row, 7).toString());
                if ("Đang làm việc".equals(jTableDSGV.getValueAt(row, 8).toString())) {
                    jRadioButtonDangLam.setSelected(true);
                } else {
                    jRadioButtonDaNghi.setSelected(true);
                }

            } catch (ParseException ex) {
                Logger.getLogger(JPanelGiangVien.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadDsMh();
            loadDsMhChon();

        }
    }//GEN-LAST:event_jTableDSGVMouseClicked

    private void listMhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listMhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listMhActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        // TODO add your handling code here:
        if (listMhChon.getSelectedIndexes() != null) {
            for (String a : listMhChon.getSelectedItems()) {
                listMh.add(a);
                listMhChon.remove(a);
            }
        }
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void jButtonGoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGoActionPerformed
        // TODO add your handling code here:

        if (listMh.getSelectedIndexes() != null) {
            for (String a : listMh.getSelectedItems()) {
                listMhChon.add(a);
                listMh.remove(a);
            }
        }
    }//GEN-LAST:event_jButtonGoActionPerformed

    private void jRadioButtonNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonNamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonNamActionPerformed

    private void jRadioButtonDangLamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonDangLamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonDangLamActionPerformed

    private void listMhChonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listMhChonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listMhChonActionPerformed

    private void jButtonSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaActionPerformed
        // TODO add your handling code here:
        int kt = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa?");

        if (kt == JOptionPane.OK_OPTION) {
            String sql = "update giangvien set [hoten]=?,[hocvi]=?,[hocham]=?, [phai]=?, [ngaysinh]=?, [diachi]=?, [chuyenmon]=?, [trangthainghi]=? where [magv] = ?";
            try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
                smt.setString(1, jTextHoTen.getText());
                smt.setString(2, jComboBoxHocVi.getSelectedItem().toString());
                smt.setString(3, jComboBoxHocHam.getSelectedItem().toString());
                if (jRadioButtonNam.isSelected()) {
                    smt.setBoolean(4, true);
                } else {
                    smt.setBoolean(4, false);
                }
                smt.setString(5, ft.format(jDateNgaySinh.getDate()));
                smt.setString(6, jTextDiaChi.getText());
                smt.setString(7, jTextChuyenMon.getText());
                if (jRadioButtonDangLam.isSelected()) {
                    smt.setBoolean(8, true);
                } else {
                    smt.setBoolean(8, false);
                }
                smt.setString(9, jLabelMaGV.getText());

                int kt2 = smt.executeUpdate();
                if (kt2 > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin giáo viên thành công");
                }

                xoaDayTheoGṿ̣();
                insertDay();
                loadDsMh();
                loadDsMhChon();
                initData();
                lamMoi();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.toString());
            }

        }
    }//GEN-LAST:event_jButtonSuaActionPerformed

    private void jButtonXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaActionPerformed
        int kt = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa?");
        if (kt == JOptionPane.CANCEL_OPTION) {
            return;
        } else if (kt == JOptionPane.OK_OPTION) {
            String sql = "delete from giangvien where magv=?";
            try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
                smt.setString(1, jLabelMaGV.getText());

                int kt2 = smt.executeUpdate();
                if (kt2 > 0) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                }
                xoaDayTheoGṿ̣();
                initData();
                lamMoi();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.toString());
            }

        }
    }//GEN-LAST:event_jButtonXoaActionPerformed

    private void jButtonThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemActionPerformed
        String sql = "insert into giangvien values(?,?,?,?,?,?,?,?,?)";
        try (Connection con = DataBaseHelper.getConnection();
            PreparedStatement smt = con.prepareStatement(sql);) {
            smt.setString(1, taoMaGV());
            smt.setString(2, jTextHoTen.getText());
            smt.setString(3, jComboBoxHocVi.getSelectedItem().toString());
            smt.setString(4, jComboBoxHocHam.getSelectedItem().toString());
            if (jRadioButtonNam.isSelected()) {
                smt.setBoolean(5, true);
            } else {
                smt.setBoolean(5, false);
            }
            smt.setString(6, ft.format(jDateNgaySinh.getDate()));
            smt.setString(7, jTextDiaChi.getText());
            smt.setString(8, jTextChuyenMon.getText());
            if (jRadioButtonDangLam.isSelected()) {
                smt.setBoolean(9, true);
            } else {
                smt.setBoolean(9, false);
            }
            int kt = smt.executeUpdate();
            if (kt > 0) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
            }
            insertDay();
            initData();
            lamMoi();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }

    }//GEN-LAST:event_jButtonThemActionPerformed

    private void jButtonLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLamMoiActionPerformed
        lamMoi();
        jButtonThem.setEnabled(true);
        jTableDSGV.setRowSelectionAllowed(false);
        loadDsMh();
        loadDsMhChon();
    }//GEN-LAST:event_jButtonLamMoiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupPhai;
    private javax.swing.ButtonGroup buttonGroupTTNghi;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonGo;
    private javax.swing.JButton jButtonLamMoi;
    private javax.swing.JButton jButtonSua;
    private javax.swing.JButton jButtonThem;
    private javax.swing.JButton jButtonXoa;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JComboBox<String> jComboBoxHocHam;
    private javax.swing.JComboBox<String> jComboBoxHocVi;
    private com.toedter.calendar.JDateChooser jDateNgaySinh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelMaGV;
    private javax.swing.JLabel jLabelMaGV2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButtonDaNghi;
    private javax.swing.JRadioButton jRadioButtonDangLam;
    private javax.swing.JRadioButton jRadioButtonNam;
    private javax.swing.JRadioButton jRadioButtonNu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableDSGV;
    private javax.swing.JTextField jTextChuyenMon;
    private javax.swing.JTextField jTextDiaChi;
    private javax.swing.JTextField jTextHoTen;
    private javax.swing.JLabel jTextPhai;
    private java.awt.List listMh;
    private java.awt.List listMhChon;
    // End of variables declaration//GEN-END:variables
}
