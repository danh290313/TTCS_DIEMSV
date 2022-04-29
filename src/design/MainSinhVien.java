/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design;

import design.DangNhap;
import doan.DataBaseHelper;
import doan.SetImage;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import sinhvien.Information;
import sinhvien.PasswordChange;

/**
 *
 * @author danh
 */
public class MainSinhVien extends javax.swing.JFrame {

    /**
     * Creates new form MainSinhVien
     */
    DefaultTableModel model = new DefaultTableModel();
    boolean flag1 = false, flag2 = false, flag3 = false;
    String maSv;
    public MainSinhVien() {
        new MainSinhVien("sv1").setVisible(true);
        
    }
    
    public MainSinhVien(String maSv) {
        initComponents();
        ImageIcon img = new ImageIcon("image//ptit.png");
        this.setIconImage(img.getImage());
        
        this.setLocationRelativeTo(null);
        String hoten = searchTenSinhVien(maSv);
        jLabelMSv.setText(maSv.toUpperCase());
        jLabelMSv.setForeground(Color.red);
        jLabelHoTen.setText(hoten.toUpperCase());
        jLabelHoTen.setForeground(Color.red);
        
        String hinhAnh = searchHinhAnh(maSv);
        if(hinhAnh!=null)
            new SetImage().setImageLabel(jLabelHinhAnh,hinhAnh);
        initboxNamTheoSv();
        this.maSv = maSv;
        
        
    }
    
    public String searchTenSinhVien(String masv) {
        String sql = " SELECT hoten FROM sinhvien WHERE masv=N'" + masv + "'";
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
    
    public String searchHinhAnh(String masv) {
        String sql = " SELECT HinhAnh FROM sinhvien WHERE masv=N'" + masv + "'";
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
    
     public void initboxNamTheoSv() {
        String sql = "{CALL TIM_NAM_THEOSV(?)}";
        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
            smt.setString(1,jLabelMSv.getText());
            ResultSet rs = smt.executeQuery();
            while (rs.next()) {
                jComboNamHoc.addItem(rs.getString(1));
            }          
            

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
     
     public void initData()
    {   
        int countDat = 0;
        model = (DefaultTableModel) jTableHK1.getModel();
        String sql = "{CALL DS_MH_THEOHOCKY(?,?,?)}";
        model.setRowCount(0);
        try(Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);
                PreparedStatement smt2 = con.prepareStatement(sql);)
        {   
            smt.setString(1, jLabelMSv.getText());
            smt.setString(2, jComboNamHoc.getSelectedItem().toString());
            smt.setString(3, "HK1");
            Vector vt;
            ResultSet rs = smt.executeQuery();
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
                vt.add(rs.getString(8));
                vt.add(rs.getString(9));
                if(rs.getFloat(9) >=4) countDat++;
                model.addRow(vt);
            }
            jTableHK1.setModel(model);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       jLabelCountDat1.setText(String.valueOf(countDat));
        
    }
     
     public void initData2()
    {   
        int countDat = 0;
        model = (DefaultTableModel) jTableHK2.getModel();
        String sql = "{CALL DS_MH_THEOHOCKY(?,?,?)}";
        model.setRowCount(0);
        try(Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);)
        {   
            smt.setString(1, jLabelMSv.getText());
            smt.setString(2, jComboNamHoc.getSelectedItem().toString());
            smt.setString(3, "HK2");
            Vector vt;
            ResultSet rs = smt.executeQuery();
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
                vt.add(rs.getString(8));
                vt.add(rs.getFloat(9));
                if(rs.getFloat(9) >=4) countDat++;
                model.addRow(vt);
            }
            jTableHK2.setModel(model);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        jLabelCountDat2.setText(String.valueOf(countDat));
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new com.raven.swing.PanelBorder();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelXemDiem = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableHK1 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabelCountDat1 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableHK2 = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabelCountDat2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jComboNamHoc = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtPassNew = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        txtConfirmPass = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbPassCu = new javax.swing.JLabel();
        lbPassNew = new javax.swing.JLabel();
        lbConfirmPass = new javax.swing.JLabel();
        btnOK = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txtPassCu = new javax.swing.JPasswordField();
        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelMSv = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabelHoTen = new javax.swing.JLabel();
        jLabelHinhAnh = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuTk = new javax.swing.JMenu();
        jMenuItemTttk = new javax.swing.JMenuItem();
        jMenuItemdoiMk = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sinh Viên");
        setBackground(new java.awt.Color(0, 255, 255));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanelXemDiem.setBackground(new java.awt.Color(0, 153, 204));

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.setLayout(new java.awt.GridLayout(2, 0));

        jPanel5.setBackground(new java.awt.Color(0, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Học kỳ 1:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16), new java.awt.Color(102, 102, 255))); // NOI18N
        jPanel5.setOpaque(false);

        jTableHK1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Môn Học", "Tên Môn Học", "Số Tính Chỉ", "% Thực Hành", "% Cuối Kỳ", "Điểm CC", "Điểm TH", "Điểm Cuối Kỳ", "Kết Quả"
            }
        ));
        jScrollPane3.setViewportView(jTableHK1);

        jPanel7.setOpaque(false);
        jPanel7.setLayout(new java.awt.GridLayout(1, 2));

        jLabel5.setText("số tính chỉ đạt:");
        jPanel7.add(jLabel5);

        jLabelCountDat1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabelCountDat1.setForeground(new java.awt.Color(255, 51, 51));
        jLabelCountDat1.setText(".............");
        jPanel7.add(jLabelCountDat1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 805, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(284, 284, 284))
        );

        jPanel3.add(jPanel5);

        jPanel8.setBackground(new java.awt.Color(0, 204, 204));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Học kỳ 2:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16), new java.awt.Color(0, 102, 255))); // NOI18N

        jTableHK2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Môn Học", "Tên Môn Học", "Số Tính Chỉ", "% Thực Hành", "% Cuối Kỳ", "Điểm CC", "Điểm TH", "Điểm Cuối Kỳ", "Kết Quả"
            }
        ));
        jScrollPane4.setViewportView(jTableHK2);

        jPanel10.setLayout(new java.awt.GridLayout(1, 2));

        jLabel9.setText("số tính chỉ đạt:");
        jPanel10.add(jLabel9);

        jLabelCountDat2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabelCountDat2.setForeground(new java.awt.Color(255, 0, 51));
        jLabelCountDat2.setText(".............");
        jPanel10.add(jLabelCountDat2);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 985, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(85, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(252, 252, 252))
        );

        jPanel3.add(jPanel8);

        jScrollPane1.setViewportView(jPanel3);

        jLabel11.setText("Năm Học: ");

        jComboNamHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboNamHocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelXemDiemLayout = new javax.swing.GroupLayout(jPanelXemDiem);
        jPanelXemDiem.setLayout(jPanelXemDiemLayout);
        jPanelXemDiemLayout.setHorizontalGroup(
            jPanelXemDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelXemDiemLayout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboNamHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelXemDiemLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelXemDiemLayout.setVerticalGroup(
            jPanelXemDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelXemDiemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelXemDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboNamHoc))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(202, 202, 202))
        );

        jTabbedPane1.addTab("Xem Điểm", new javax.swing.ImageIcon(getClass().getResource("/image/Health_Insurance-512.png")), jPanelXemDiem); // NOI18N

        txtPassNew.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtPassNewCaretUpdate(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 51, 0));
        jLabel4.setText("Mật khẩu cũ:");

        txtConfirmPass.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtConfirmPassCaretUpdate(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 51, 0));
        jLabel7.setText("Mật khẩu mới:");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 51, 0));
        jLabel8.setText("Nhập lại mật khẩu:");

        lbPassCu.setForeground(new java.awt.Color(255, 0, 0));
        lbPassCu.setText(" ");

        lbPassNew.setForeground(new java.awt.Color(255, 0, 0));
        lbPassNew.setText(" ");

        lbConfirmPass.setForeground(new java.awt.Color(255, 0, 0));
        lbConfirmPass.setText(" ");

        btnOK.setText("Đồng ý");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        jButton2.setText("Làm mới");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtPassCu.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtPassCuCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(jLabel3)
                .addContainerGap(994, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(348, 348, 348)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addGap(11, 11, 11)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtConfirmPass)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbConfirmPass)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addComponent(btnOK)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton2)))
                                    .addGap(0, 174, Short.MAX_VALUE))))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(35, 35, 35)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(lbPassNew)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(txtPassNew)))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(42, 42, 42)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtPassCu)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(lbPassCu)
                                    .addGap(0, 0, Short.MAX_VALUE)))))
                    .addGap(349, 349, 349)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jLabel3)
                .addContainerGap(336, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(100, 100, 100)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtPassCu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lbPassCu)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtPassNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lbPassNew)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtConfirmPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lbConfirmPass)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnOK)
                        .addComponent(jButton2))
                    .addContainerGap(100, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Đổi mật khẩu", jPanel4);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1174, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 428, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab3", jPanel6);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(1, 4));

        jLabel1.setText("Mã số sinh viên: ");
        jPanel1.add(jLabel1);

        jLabelMSv.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabelMSv.setText("...................");
        jPanel1.add(jLabelMSv);

        jLabel2.setText("Họ Và Tên:");
        jPanel1.add(jLabel2);

        jLabelHoTen.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabelHoTen.setText("...................");
        jPanel1.add(jLabelHoTen);

        jLabelHinhAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/person.png"))); // NOI18N
        jLabelHinhAnh.setText("Hình Ảnh");
        jLabelHinhAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 51), 2));

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(jLabelHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 206, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 749, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelBorder1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTabbedPane1)
                    .addContainerGap()))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(522, Short.MAX_VALUE))
            .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelBorder1Layout.createSequentialGroup()
                    .addGap(153, 153, 153)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(49, Short.MAX_VALUE)))
        );

        jMenuBar1.setBackground(new java.awt.Color(204, 204, 255));

        jMenuTk.setText("Tài Khoản ");

        jMenuItemTttk.setText("Thông tin tài khoản");
        jMenuItemTttk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTttkActionPerformed(evt);
            }
        });
        jMenuTk.add(jMenuItemTttk);

        jMenuItemdoiMk.setText("Đổi mật khẩu");
        jMenuItemdoiMk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemdoiMkActionPerformed(evt);
            }
        });
        jMenuTk.add(jMenuItemdoiMk);

        jMenuItem1.setText("Đăng Xuất");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuTk.add(jMenuItem1);

        jMenuBar1.add(jMenuTk);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemTttkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTttkActionPerformed
        // TODO add your handling code here:
        new Information(maSv).setVisible(true);
    }//GEN-LAST:event_jMenuItemTttkActionPerformed

    private void jMenuItemdoiMkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemdoiMkActionPerformed
        // TODO add your handling code here:
        new PasswordChange(maSv).setVisible(true);
    }//GEN-LAST:event_jMenuItemdoiMkActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        int click = JOptionPane.showConfirmDialog(null, "Đăng xuất ngay bây giờ?");
        if (click == JOptionPane.OK_OPTION) {
            this.setVisible(false);
            new DangNhap().setVisible(true);;
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jComboNamHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboNamHocActionPerformed
        // TODO add your handling code here:
        initData();
        initData2();
    }//GEN-LAST:event_jComboNamHocActionPerformed

    private void txtPassNewCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtPassNewCaretUpdate
        if (txtPassNew.getText().trim().equals("")) {
            lbPassNew.setText(" ");
            btnOK.setEnabled(false);
        } else if (txtPassNew.getText().equals(txtPassCu.getText())) {
            lbPassNew.setText("Mật khẩu mới phải khác mật khẩu cũ.");
            lbPassNew.setForeground(Color.red);
            btnOK.setEnabled(false);
        } else {
            char x;
            for (int i = 0; i < txtPassNew.getText().length(); i++) {
                x = txtPassNew.getText().charAt(i);
                if (x == ' ') {
                    lbPassNew.setText("Mật khẩu không thể chứa khoảng trắng.");
                    lbPassNew.setForeground(Color.red);
                    flag2 = false;
                    btnOK.setEnabled(false);
                    return;
                }
            }
            while (true) {
                if (txtPassNew.getText().length() < 6 || txtPassNew.getText().length() > 18) {
                    lbPassNew.setText("Độ dài mật khẩu trong khoảng 6-18 kí tự.");
                    lbPassNew.setForeground(Color.red);
                    flag2 = false;
                    btnOK.setEnabled(false);
                    return;
                } else {
                    lbPassNew.setText("Mật khẩu hợp lệ.");
                    lbPassNew.setForeground(Color.blue);
                    flag2 = true;
                    btnOK.setEnabled(false);
                    break;
                }
            }
        }
    }//GEN-LAST:event_txtPassNewCaretUpdate

    private void txtConfirmPassCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtConfirmPassCaretUpdate
        if (txtConfirmPass.getText().trim().equals("")) {
            lbConfirmPass.setText(" ");
            btnOK.setEnabled(false);
        } else {
            while (true) {
                if (!txtConfirmPass.getText().equals(txtPassNew.getText())) {
                    lbConfirmPass.setText("Nhập lại mật khẩu phải giống mật khẩu.");
                    lbConfirmPass.setForeground(Color.red);
                    flag3 = false;
                    btnOK.setEnabled(false);
                    return;
                } else {
                    lbConfirmPass.setText("Hợp lệ.");
                    lbConfirmPass.setForeground(Color.blue);
                    flag3 = true;
                    btnOK.setEnabled(true);
                    break;
                }
            }
        }
    }//GEN-LAST:event_txtConfirmPassCaretUpdate

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        if (flag1 == true && flag2 == true && flag3 == true) {
            try {
                Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement("Update taikhoansv set matkhau=CONVERT(VARCHAR(32), HashBytes('MD5', ?),2) where masv=?");
                smt.setString(1, txtConfirmPass.getText());
                smt.setString(2, jLabelMSv.getText());
                smt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công.");
                this.setVisible(false);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Đổi mật khẩu thất bại!");
        }
    }//GEN-LAST:event_btnOKActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        txtPassCu.setText("");
        txtPassNew.setText("");
        txtConfirmPass.setText("");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtPassCuCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtPassCuCaretUpdate
        if (txtPassCu.getText().trim().equals("")) {
            lbPassCu.setText(" ");
            btnOK.setEnabled(false);
        } else {
            try {
                Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement("select masv from taikhoansv where masv=? and matkhau=CONVERT(VARCHAR(32), HashBytes('MD5', ?), 2)");
                smt.setString(1, jLabelMSv.getText());
                smt.setString(2, txtPassCu.getText());
                ResultSet  rs = smt.executeQuery();
                while (true) {
                    if (!rs.next()) {
                        lbPassCu.setText("Sai mật khẩu.");
                        lbPassCu.setForeground(Color.red);
                        flag1 = false;
                        btnOK.setEnabled(false);
                        return;
                    } else {
                        lbPassCu.setText("Đúng mật khẩu.");
                        lbPassCu.setForeground(Color.blue);
                        flag1 = true;
                        btnOK.setEnabled(false);
                        break;
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
            }
        }
    }//GEN-LAST:event_txtPassCuCaretUpdate

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
            java.util.logging.Logger.getLogger(MainSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainSinhVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOK;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboNamHoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCountDat1;
    private javax.swing.JLabel jLabelCountDat2;
    private javax.swing.JLabel jLabelHinhAnh;
    private javax.swing.JLabel jLabelHoTen;
    private javax.swing.JLabel jLabelMSv;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItemTttk;
    private javax.swing.JMenuItem jMenuItemdoiMk;
    private javax.swing.JMenu jMenuTk;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelXemDiem;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableHK1;
    private javax.swing.JTable jTableHK2;
    private javax.swing.JLabel lbConfirmPass;
    private javax.swing.JLabel lbPassCu;
    private javax.swing.JLabel lbPassNew;
    private com.raven.swing.PanelBorder panelBorder1;
    private javax.swing.JPasswordField txtConfirmPass;
    private javax.swing.JPasswordField txtPassCu;
    private javax.swing.JPasswordField txtPassNew;
    // End of variables declaration//GEN-END:variables
}
