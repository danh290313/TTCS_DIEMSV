/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design;

import com.raven.chart.blankchart2.ModelChart;
import dao.Provider;
import design.DangNhap;
import doan.SetImage;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
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
        jTableHK1.getTableHeader().setFont( new Font( "Tahoma" , Font.BOLD, 14));
        jTableHK2.getTableHeader().setFont( new Font( "Tahoma" , Font.BOLD, 14));
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
        initThongKe();
        java.util.Date date = java.util.Calendar.getInstance().getTime();
        timNamHoc.setText(String.valueOf(date.getYear()));
        initData_NamHoc();
        initData_TableDSLTC();
        initData_TableLTCDK();
        jButtonThem.setEnabled(false);
        jButtonXoa.setEnabled(false);
        
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
        int countSoMonDat = 0;
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
               if(rs.getFloat(9) >=4) 
                {
                    countDat+=Integer.parseInt(rs.getString(3));
                    countSoMonDat++;
                }
                model.addRow(vt);
            }
            jTableHK1.setModel(model);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       jLabelCountDat1.setText(String.valueOf(countDat));
       jLabelSoMon1.setText(String.valueOf(countSoMonDat));
        
    }
     
     public void initData2()
    {   
        int countDat = 0;
        int countSoMonDat = 0;
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
                if(rs.getFloat(9) >=4) 
                {
                    countDat+=Integer.parseInt(rs.getString(3));
                    countSoMonDat++;
                }
                model.addRow(vt);
            }
            jTableHK2.setModel(model);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        jLabelCountDat2.setText(String.valueOf(countDat));
        jLabelSoMon.setText(String.valueOf(countSoMonDat));
    }
     
     private void initThongKe() {
        
        lineChart.addLegend("Biểu đồ điểm trung bình qua các học kỳ", new Color(12, 84, 175), new Color(0, 108, 247));
        try {
            List<ModelChart> data = getDataByItem();
            for (int i = data.size() - 1; i >= 0; i--) {
                lineChart.addData(data.get(i));
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        lineChart.start();
    }
     
     private List<ModelChart> getDataByItem() throws SQLException {
         
        List<ModelChart> data = new ArrayList<>();
        String sql = "{CALL usp_ThongKeDiemTBSV(?)}";
        PreparedStatement p = DataBaseHelper.getConnection().prepareStatement(sql);
        p.setString(1,maSv);
        ResultSet r = p.executeQuery();
        
        while (r.next()) {
            String productName = r.getString(1).trim()+"-"+r.getString(2).trim();
            int qty = r.getInt(3);
            data.add(new ModelChart(productName, new double[]{qty}));
        }
        r.close();
        p.close();
        return data;
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
        jPanel9 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabelSoMon1 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableHK2 = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabelCountDat2 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabelSoMon = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jComboNamHoc = new javax.swing.JComboBox<>();
        jPanelDoiMK = new javax.swing.JPanel();
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
        jPanelThongKe = new javax.swing.JPanel();
        panelShadow4 = new com.raven.swing2.PanelShadow();
        lineChart = new com.raven.chart2.LineChart();
        jLabel10 = new javax.swing.JLabel();
        jPanel1DangKi = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableDSLTCDK = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableDSLTC = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButtonThem = new javax.swing.JButton();
        jButtonXoa = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        timLTC = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        timNamHoc = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelMSv = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabelHoTen = new javax.swing.JLabel();
        jLabelHinhAnh = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
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
                "Mã Môn Học", "Tên Môn Học", "Số Tín Chỉ", "% Thực Hành", "% Cuối Kỳ", "Điểm CC", "Điểm TH", "Điểm Cuối Kỳ", "Điểm Tổng Kết"
            }
        ));
        jScrollPane3.setViewportView(jTableHK1);

        jPanel7.setOpaque(false);
        jPanel7.setLayout(new java.awt.GridLayout(1, 2));

        jLabel5.setText("Số tín chỉ đạt:");
        jPanel7.add(jLabel5);

        jLabelCountDat1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabelCountDat1.setForeground(new java.awt.Color(255, 51, 51));
        jLabelCountDat1.setText(".............");
        jPanel7.add(jLabelCountDat1);

        jPanel9.setOpaque(false);
        jPanel9.setLayout(new java.awt.GridLayout(1, 2));

        jLabel12.setText("Số môn đạt:");
        jPanel9.add(jLabel12);

        jLabelSoMon1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabelSoMon1.setForeground(new java.awt.Color(255, 51, 51));
        jLabelSoMon1.setText(".............");
        jPanel9.add(jLabelSoMon1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1042, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(232, 232, 232))
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
                "Mã Môn Học", "Tên Môn Học", "Số Tín Chỉ", "% Thực Hành", "% Cuối Kỳ", "Điểm CC", "Điểm TH", "Điểm Cuối Kỳ", "Điểm Tổng Kết"
            }
        ));
        jScrollPane4.setViewportView(jTableHK2);

        jPanel10.setLayout(new java.awt.GridLayout(1, 2));

        jLabel9.setText("Số tính chỉ đạt:");
        jPanel10.add(jLabel9);

        jLabelCountDat2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabelCountDat2.setForeground(new java.awt.Color(255, 0, 51));
        jLabelCountDat2.setText(".............");
        jPanel10.add(jLabelCountDat2);

        jPanel11.setLayout(new java.awt.GridLayout(1, 2));

        jLabel13.setText("Số môn đạt:");
        jPanel11.add(jLabel13);

        jLabelSoMon.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabelSoMon.setForeground(new java.awt.Color(255, 0, 51));
        jLabelSoMon.setText(".............");
        jPanel11.add(jLabelSoMon);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 985, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(85, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(200, 200, 200))
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

        javax.swing.GroupLayout jPanelDoiMKLayout = new javax.swing.GroupLayout(jPanelDoiMK);
        jPanelDoiMK.setLayout(jPanelDoiMKLayout);
        jPanelDoiMKLayout.setHorizontalGroup(
            jPanelDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDoiMKLayout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(jLabel3)
                .addContainerGap(994, Short.MAX_VALUE))
            .addGroup(jPanelDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelDoiMKLayout.createSequentialGroup()
                    .addGap(348, 348, 348)
                    .addGroup(jPanelDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelDoiMKLayout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addGap(11, 11, 11)
                            .addGroup(jPanelDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtConfirmPass)
                                .addGroup(jPanelDoiMKLayout.createSequentialGroup()
                                    .addGroup(jPanelDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbConfirmPass)
                                        .addGroup(jPanelDoiMKLayout.createSequentialGroup()
                                            .addComponent(btnOK)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton2)))
                                    .addGap(0, 174, Short.MAX_VALUE))))
                        .addGroup(jPanelDoiMKLayout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(35, 35, 35)
                            .addGroup(jPanelDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelDoiMKLayout.createSequentialGroup()
                                    .addComponent(lbPassNew)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(txtPassNew)))
                        .addGroup(jPanelDoiMKLayout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(42, 42, 42)
                            .addGroup(jPanelDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtPassCu)
                                .addGroup(jPanelDoiMKLayout.createSequentialGroup()
                                    .addComponent(lbPassCu)
                                    .addGap(0, 0, Short.MAX_VALUE)))))
                    .addGap(349, 349, 349)))
        );
        jPanelDoiMKLayout.setVerticalGroup(
            jPanelDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDoiMKLayout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jLabel3)
                .addContainerGap(372, Short.MAX_VALUE))
            .addGroup(jPanelDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelDoiMKLayout.createSequentialGroup()
                    .addGap(100, 100, 100)
                    .addGroup(jPanelDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtPassCu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lbPassCu)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanelDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtPassNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lbPassNew)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanelDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtConfirmPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lbConfirmPass)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanelDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnOK)
                        .addComponent(jButton2))
                    .addContainerGap(136, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Đổi mật khẩu", jPanelDoiMK);

        panelShadow4.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));

        javax.swing.GroupLayout panelShadow4Layout = new javax.swing.GroupLayout(panelShadow4);
        panelShadow4.setLayout(panelShadow4Layout);
        panelShadow4Layout.setHorizontalGroup(
            panelShadow4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lineChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelShadow4Layout.setVerticalGroup(
            panelShadow4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lineChart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 51));
        jLabel10.setText("Biểu đồ điểm trung bình của sinh viên qua các học kỳ");

        javax.swing.GroupLayout jPanelThongKeLayout = new javax.swing.GroupLayout(jPanelThongKe);
        jPanelThongKe.setLayout(jPanelThongKeLayout);
        jPanelThongKeLayout.setHorizontalGroup(
            jPanelThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelShadow4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanelThongKeLayout.createSequentialGroup()
                .addGap(311, 311, 311)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(348, Short.MAX_VALUE))
        );
        jPanelThongKeLayout.setVerticalGroup(
            jPanelThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelThongKeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(panelShadow4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Thống kê kết quả học tập qua các học kỳ", jPanelThongKe);

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));
        jPanel2.setMinimumSize(new java.awt.Dimension(89, 32));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(89, 32));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setText("Đăng Ký");
        jPanel2.add(jLabel14, new java.awt.GridBagConstraints());

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText(" Danh sách LTC đã đăng kí:");

        jTableDSLTCDK.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "MALTC", "TENMH", "HOCKI", "NGAYBD", "NGAYKT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableDSLTCDK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDSLTCDKMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTableDSLTCDK);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText(" Danh sách LTC hiện có:");

        jTableDSLTC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "MALTC", "TENMH", "HOCKI", "SLCONLAI", "SLTOIDA", "NGAYBD", "NGAYKT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
        jScrollPane6.setViewportView(jTableDSLTC);

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        jButtonThem.setBackground(new java.awt.Color(0, 102, 204));
        jButtonThem.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonThem.setForeground(new java.awt.Color(255, 255, 255));
        jButtonThem.setText("Đăng ký");
        jButtonThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonThemActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonThem);

        jButtonXoa.setBackground(new java.awt.Color(0, 102, 204));
        jButtonXoa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonXoa.setForeground(new java.awt.Color(255, 255, 255));
        jButtonXoa.setText("Hủy Đăng Ký");
        jButtonXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoaActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonXoa);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("LTC chọn:");

        timLTC.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        timLTC.setForeground(new java.awt.Color(255, 0, 0));
        timLTC.setText("       ");

        jPanel6.setBackground(new java.awt.Color(255, 255, 204));
        jPanel6.setOpaque(false);
        jPanel6.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("  Năm học:");
        jPanel6.add(jLabel18);

        timNamHoc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        timNamHoc.setForeground(new java.awt.Color(255, 0, 0));
        jPanel6.add(timNamHoc);

        javax.swing.GroupLayout jPanel1DangKiLayout = new javax.swing.GroupLayout(jPanel1DangKi);
        jPanel1DangKi.setLayout(jPanel1DangKiLayout);
        jPanel1DangKiLayout.setHorizontalGroup(
            jPanel1DangKiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1DangKiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1DangKiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1DangKiLayout.createSequentialGroup()
                        .addGroup(jPanel1DangKiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1DangKiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1DangKiLayout.createSequentialGroup()
                        .addGroup(jPanel1DangKiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1DangKiLayout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(timLTC, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(212, 212, 212)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1DangKiLayout.setVerticalGroup(
            jPanel1DangKiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1DangKiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1DangKiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1DangKiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1DangKiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timLTC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(280, 280, 280))
        );

        jTabbedPane1.addTab("Đăng Kí LTC", jPanel1DangKi);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 221, Short.MAX_VALUE)
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
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 503, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jLabel6.setText("jLabel6");

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
                PreparedStatement smt = con.prepareStatement("Update taikhoan set matkhau=CONVERT(VARCHAR(32), HashBytes('MD5', ?),2) where maTK=?");
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
                PreparedStatement smt = con.prepareStatement("select maTK from taikhoan where maTK=? and matkhau=CONVERT(VARCHAR(32), HashBytes('MD5', ?), 2)");
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

    private void jTableDSLTCDKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDSLTCDKMouseClicked
        jTableDSLTCDK.setRowSelectionAllowed(true);
        int row = jTableDSLTCDK.getSelectedRow();
        timLTC.setText(jTableDSLTCDK.getValueAt(row, 0).toString().toUpperCase());
        jButtonXoa.setEnabled(true);
        jButtonThem.setEnabled(false);
    }//GEN-LAST:event_jTableDSLTCDKMouseClicked

    private void jTableDSLTCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDSLTCMouseClicked
        jTableDSLTC.setRowSelectionAllowed(true);
        int row = jTableDSLTC.getSelectedRow();
        timLTC.setText(jTableDSLTC.getValueAt(row, 0).toString().toUpperCase());
        jButtonThem.setEnabled(true);
        jButtonXoa.setEnabled(false);
        if (jTableDSLTC.getValueAt(jTableDSLTC.getSelectedRow(), 3).toString().equals("0"))
            jButtonThem.setEnabled(false);
    }//GEN-LAST:event_jTableDSLTCMouseClicked

    public void initData_NamHoc()
    {
        java.util.Date date = java.util.Calendar.getInstance().getTime();
        StringBuilder a = new StringBuilder(date.toString());
        String b = a.reverse().toString().substring(0, 4);
        StringBuilder c = new StringBuilder(b.toString());
        String d = c.reverse().toString();
        timNamHoc.setText(d);
    }
    
    //--- Bảng LTC
    public void initData_TableDSLTC()
    {   
        model = (DefaultTableModel) jTableDSLTC.getModel();
        String sql = "{CALL usp_DSLTC_Nam(?)}";
        model.setRowCount(0);
        try(Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);)
        {   
            smt.setString(1, timNamHoc.getText());
         
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

                model.addRow(vt);
            }
            jTableDSLTC.setModel(model);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
        timLTC.setText("");
        
    }
    
        //--- Bảng DangKi
        public void initData_TableLTCDK() {
            model = (DefaultTableModel) jTableDSLTCDK.getModel();
            String sql = "select ltc.MaLTC,MaMH,HocKi,NgayBD,NgayKT from loptinchi ltc,dangki dk where huy=0 and ltc.MaLTC=dk.MaLTC and dk.MaSV=N'"+jLabelMSv.getText()+"' order by cast(substring(ltc.MaLTC,4,10) as int)";
            model.setRowCount(0);
            try (Connection con = DataBaseHelper.getConnection();
                    Statement smt = con.createStatement();) {
                Vector vt;
                ResultSet rs = smt.executeQuery(sql);
                while (rs.next()) {
                    vt = new Vector();
                    vt.add(rs.getString(1));
                    vt.add(searchTenMonHoc(rs.getString(2)));
                    vt.add(rs.getString(3));
                    vt.add(rs.getString(4));
                    vt.add(rs.getString(5));
                    model.addRow(vt);
                }
                jTableDSLTCDK.setModel(model);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.toString());
            }
        }
    //---
    
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
    public boolean insertDangki()
    {   
        int row = jTableDSLTC.getSelectedRow();
        
        if (jTableDSLTC.getValueAt(row, 0).toString().equals(jTableDSLTCDK.getValueAt(row, 0).toString()))
        {
            JOptionPane.showMessageDialog(this,jTableDSLTC.getValueAt(row, 0).toString().toUpperCase()+" đã được đăng ký!");
            jButtonThem.setEnabled(false);
            return false;
        }
            
        //System.out.println(jTableDSLTC.getValueAt(jTableDSLTC.getSelectedRow(), 0).toString());
        
        if(Provider.kiemTraDangKi(jTableDSLTC.getValueAt(row, 0).toString(), jLabelMSv.getText()) )
            Provider.capNhatLTC((jTableDSLTC.getValueAt(row, 0).toString()), jLabelMSv.getText(), false);
        else
        {
            String sql = "insert into dangki values(?,?,?,?,?,?)";
       
        try (Connection con = DataBaseHelper.getConnection();
            PreparedStatement smt = con.prepareStatement(sql);) {   
            smt.setString(1, jTableDSLTC.getValueAt(row, 0).toString().toUpperCase());
            smt.setString(2, jLabelMSv.getText());
            smt.setString(3, "0");
            smt.setString(4, "0");
            smt.setString(5, "0");
            smt.setBoolean(6, false);
            smt.executeUpdate();  
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,ex.toString());
            return false;           
        }
        }
        return true;   
    }
    
    public void xoaDangKiTheoSV() {
        int row = jTableDSLTCDK.getSelectedRow();
        String sql = "delete from dangki where maltc=? and masv=? and huy=1";
        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
            smt.setString(1, jTableDSLTCDK.getValueAt(row, 0).toString().toUpperCase());
            smt.setString(2, jLabelMSv.getText());
            smt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
            jButtonXoa.setEnabled(false);
        } 
    }  
    
    private void jButtonThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemActionPerformed
        if (insertDangki())
        {
            JOptionPane.showMessageDialog(this, "Đăng ký "+ jTableDSLTC.getValueAt(jTableDSLTC.getSelectedRow(), 0).toString().toUpperCase() +" thành công!");
            jButtonThem.setEnabled(false);
        }
        initData_TableDSLTC();
        initData_TableLTCDK();
        jTableDSLTC.setRowSelectionAllowed(false);

    }//GEN-LAST:event_jButtonThemActionPerformed

    private void jButtonXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaActionPerformed
        int row = jTableDSLTCDK.getSelectedRow();
        int kt = JOptionPane.showConfirmDialog(this, "Bạn có muốn hủy đăng ký không?");
        if (kt == JOptionPane.CANCEL_OPTION) {
            return;
        } else if (kt == JOptionPane.OK_OPTION) {
//            int row1 = jTableDSLTCDK.getRowCount();
//            xoaDangKiTheoSV();
//            initData_TableLTCDK();
//            int row2 = jTableDSLTCDK.getRowCount();
//            if (row1!=row2)
//                JOptionPane.showMessageDialog(this, "Xóa thành công!");
//            else
//                JOptionPane.showMessageDialog(this, "Xóa thất bại!");

        Provider.capNhatLTC(jTableDSLTCDK.getValueAt(row, 0).toString().toUpperCase(),  jLabelMSv.getText(), true);
        }
        initData_TableLTCDK();
        initData_TableDSLTC();
        jTableDSLTCDK.setRowSelectionAllowed(false);
    }//GEN-LAST:event_jButtonXoaActionPerformed

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
    private javax.swing.JButton jButtonThem;
    private javax.swing.JButton jButtonXoa;
    private javax.swing.JComboBox<String> jComboNamHoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCountDat1;
    private javax.swing.JLabel jLabelCountDat2;
    private javax.swing.JLabel jLabelHinhAnh;
    private javax.swing.JLabel jLabelHoTen;
    private javax.swing.JLabel jLabelMSv;
    private javax.swing.JLabel jLabelSoMon;
    private javax.swing.JLabel jLabelSoMon1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItemTttk;
    private javax.swing.JMenuItem jMenuItemdoiMk;
    private javax.swing.JMenu jMenuTk;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel1DangKi;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelDoiMK;
    private javax.swing.JPanel jPanelThongKe;
    private javax.swing.JPanel jPanelXemDiem;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableDSLTC;
    private javax.swing.JTable jTableDSLTCDK;
    private javax.swing.JTable jTableHK1;
    private javax.swing.JTable jTableHK2;
    private javax.swing.JLabel lbConfirmPass;
    private javax.swing.JLabel lbPassCu;
    private javax.swing.JLabel lbPassNew;
    private com.raven.chart2.LineChart lineChart;
    private com.raven.swing.PanelBorder panelBorder1;
    private com.raven.swing2.PanelShadow panelShadow4;
    private javax.swing.JLabel timLTC;
    private javax.swing.JLabel timNamHoc;
    private javax.swing.JPasswordField txtConfirmPass;
    private javax.swing.JPasswordField txtPassCu;
    private javax.swing.JPasswordField txtPassNew;
    // End of variables declaration//GEN-END:variables
}
