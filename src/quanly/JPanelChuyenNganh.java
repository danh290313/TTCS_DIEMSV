/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanly;

/**
 *
 * @author hyipd
 */
import com.raven.main.KeHoach;
import com.raven.swing.ScrollBar;
import static com.raven.swing.scroll.sroll;
import com.sun.javafx.collections.FloatArraySyncer;
import doan.DataBaseHelper;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class JPanelChuyenNganh extends javax.swing.JPanel {

    /**
     * Creates new form JPanelChuyenNganh
     */
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
    
    
   
    public JPanelChuyenNganh() {
        initComponents();
        initData();
        sroll(jScrollPane1);
        sroll(jScrollPane2);
        jLabelMacn.setText(taoMaCN());
        jLabelMacn.setForeground(Color.red);
        jLabelMacn.setBackground(Color.GREEN);
        listMh.setMultipleMode(true);
        listMhChon.setMultipleMode(true);
        vohieuButtonKhiChuaChon();
        loadDsMh();
        
    }
    
    
    DefaultTableModel model = new DefaultTableModel();
    
    
    public void loadDsMh()
    {   
        listMh.removeAll();
        try(Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement("{call DS_MH_CHUACHON(?)}");)
        {   
            
            smt.setString(1,jLabelMacn.getText());
            ResultSet rs = smt.executeQuery();
            
            while(rs.next())
            {
                listMh.add(rs.getString(1));
            }
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
//    public void loadDsMhChon()
//    {   
//        listMhChon.removeAll();
//        try(Connection con = DataBaseHelper.getConnection();
//                PreparedStatement smt = con.prepareStatement("{call DS_MHCHON(?)}");)
//        {   
//            
//            smt.setString(1,jLabelMacn.getText());
//            ResultSet rs = smt.executeQuery();
//            
//            while(rs.next())
//            {
//                listMhChon.add(rs.getString(1));
//            }
//            
//            
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
    
    public boolean checkMhChon(String tenMh)
    {   

        try(Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement("{call DS_MHCHON(?)}");)
        {   
            
            smt.setString(1,jLabelMacn.getText());
            ResultSet rs = smt.executeQuery();
            
            while(rs.next())
            {
                if(rs.getString(1).equals(tenMh)) return true;
            }
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    public void initData() {
        model = (DefaultTableModel) jTableDSCN.getModel();
        String sql = "select * from chuyennganh";
        model.setRowCount(0);
        try (Connection con = DataBaseHelper.getConnection();
                Statement smt = con.createStatement();) {
            Vector vt;
            ResultSet rs = smt.executeQuery(sql);
            while (rs.next()) {
                vt = new Vector();
                vt.add(rs.getString(1));
                vt.add(rs.getString(2));
                model.addRow(vt);
            }
            jTableDSCN.setModel(model);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void initDataMhCn()
    {
        model = (DefaultTableModel) jTableDSmhcn.getModel();
        String sql = "select tenmh,hesoth,hesock from kehoach,monhoc where kehoach.mamh=monhoc.mamh and kehoach.macn='"+ jLabelMacn.getText()+"'";
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
                model.addRow(vt);
            }
            jTableDSmhcn.setModel(model);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String taoMaCN() {
        String sql = "select macn from chuyennganh";
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
        return "CN" + String.valueOf(max + 1);
    }

    

    public void lamMoi() {
        jLabelMacn.setText(taoMaCN());
        jTextTenCn.setText("");
        jTableDSCN.setRowSelectionAllowed(false);
        model = (DefaultTableModel) jTableDSmhcn.getModel();
        model.setRowCount(0);
        vohieuButtonKhiChuaChon();
        jButtonThem.setEnabled(true);
        loadDsMh();
      //  loadDsMhChon();
        listMhChon.removeAll();
        jTextHsTh.setText("");
        jTextHsck.setText("");
        

    }
    
    public void lamMoiMhcn() {
        jTextHsTh.setText("");
        jTextHsck.setText("");
        jLabelTenMhcn.setText("");
    }
    
    public void vohieuButtonKhiChuaChon()
    {
        jButtonSua.setEnabled(false);
        jButtonXoa.setEnabled(false);
        jButtonBack.setEnabled(false);
        jButtonGo.setEnabled(false);
        jButtonaddMhcn.setEnabled(false);
        jButtonsuamhcn.setEnabled(false);
        jButtonxoamhcn.setEnabled(false);
        jPanelKeHoach.setEnabled(false);
        //listMh.setVisible(false);
    }
    
    public void KichHoatButtonKhiChon()
    {
        jButtonSua.setEnabled(true);
        jButtonXoa.setEnabled(true);
        jButtonBack.setEnabled(true);
        jButtonGo.setEnabled(true);
        jButtonaddMhcn.setEnabled(true);
        jPanelKeHoach.setEnabled(true);
    }

    private static float roundFloat(float f, int places) {
 
        BigDecimal bigDecimal = new BigDecimal(Float.toString(f));
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.floatValue();
    }
    
    public String searchMaMonHoc(String tenMonHoc) {
        String sql = " SELECT mamh FROM monhoc WHERE tenmh=N'" + tenMonHoc + "'";
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
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabelMacn = new javax.swing.JLabel();
        jTextTenCn = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDSCN = new javax.swing.JTable();
        d = new javax.swing.JSeparator();
        jPanelKeHoach = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDSmhcn = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextHsTh = new javax.swing.JTextField();
        jTextHsck = new javax.swing.JTextField();
        jButtonsuamhcn = new javax.swing.JButton();
        jButtonxoamhcn = new javax.swing.JButton();
        jLabelTenMhcn = new javax.swing.JLabel();
        jButtonaddMhcn = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButtonBack = new javax.swing.JButton();
        jButtonGo = new javax.swing.JButton();
        listMh = new java.awt.List();
        listMhChon = new java.awt.List();
        jPanel2 = new javax.swing.JPanel();
        jButtonLamMoi = new javax.swing.JButton();
        jButtonThem = new javax.swing.JButton();
        jButtonSua = new javax.swing.JButton();
        jButtonXoa = new javax.swing.JButton();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setPreferredSize(new java.awt.Dimension(1050, 700));

        jLabel2.setText("Tên chuyên ngành:");

        jLabel4.setText("Mã chuyên ngành: ");

        jLabelMacn.setBackground(new java.awt.Color(204, 255, 0));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Chuyên ngành");
        jPanel1.add(jLabel1);

        jTableDSCN.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã chuyên ngành", "Tên chuyên ngành"
            }
        ));
        jTableDSCN.setOpaque(false);
        jTableDSCN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDSCNMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableDSCN);

        d.setName("adfs"); // NOI18N

        jPanelKeHoach.setBorder(javax.swing.BorderFactory.createTitledBorder("Kế Hoach"));
        jPanelKeHoach.setToolTipText("zvds");
        jPanelKeHoach.setName("Kế Hoạch"); // NOI18N
        jPanelKeHoach.setOpaque(false);

        jTableDSmhcn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tên Môn Học", "Hệ số thực hành", "Hệ số cuối kỳ"
            }
        ));
        jTableDSmhcn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDSmhcnMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableDSmhcn);

        jLabel7.setText("Hệ số thực hành:");

        jLabel8.setText("Hệ số cuối kỳ:");

        jLabel9.setText("Môn Học:");

        jButtonsuamhcn.setText("Sửa");
        jButtonsuamhcn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonsuamhcnActionPerformed(evt);
            }
        });

        jButtonxoamhcn.setText("xóa");
        jButtonxoamhcn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonxoamhcnActionPerformed(evt);
            }
        });

        jButtonaddMhcn.setText("add");
        jButtonaddMhcn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonaddMhcnActionPerformed(evt);
            }
        });

        jLabel10.setText("DS_MonChuaChon");

        jLabel11.setText("DS_MonHocChon");

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

        listMh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listMhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelKeHoachLayout = new javax.swing.GroupLayout(jPanelKeHoach);
        jPanelKeHoach.setLayout(jPanelKeHoachLayout);
        jPanelKeHoachLayout.setHorizontalGroup(
            jPanelKeHoachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelKeHoachLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelKeHoachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelKeHoachLayout.createSequentialGroup()
                        .addGap(247, 247, 247)
                        .addGroup(jPanelKeHoachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelKeHoachLayout.createSequentialGroup()
                                .addComponent(listMh, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonBack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonGo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(listMhChon, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24))
                            .addGroup(jPanelKeHoachLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(104, 104, 104)
                                .addComponent(jLabel11)))
                        .addGap(39, 39, 39)
                        .addComponent(jButtonaddMhcn)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
            .addGroup(jPanelKeHoachLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabelTenMhcn, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextHsTh, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jTextHsck, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jButtonxoamhcn)
                .addGap(18, 18, 18)
                .addComponent(jButtonsuamhcn)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanelKeHoachLayout.setVerticalGroup(
            jPanelKeHoachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelKeHoachLayout.createSequentialGroup()
                .addGroup(jPanelKeHoachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanelKeHoachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelKeHoachLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelKeHoachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelKeHoachLayout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(jPanelKeHoachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButtonGo)
                                    .addComponent(jButtonBack)))
                            .addComponent(listMh, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(listMhChon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanelKeHoachLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jButtonaddMhcn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelKeHoachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelKeHoachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8)
                        .addComponent(jTextHsTh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextHsck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonsuamhcn)
                        .addComponent(jButtonxoamhcn))
                    .addComponent(jLabelTenMhcn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
        );

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        jButtonLamMoi.setText("Làm Mới");
        jButtonLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLamMoiActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonLamMoi);

        jButtonThem.setText("Thêm");
        jButtonThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonThemActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonThem);

        jButtonSua.setText("Sửa");
        jButtonSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuaActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonSua);

        jButtonXoa.setText("Xóa");
        jButtonXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoaActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonXoa);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(31, 31, 31)
                .addComponent(jLabelMacn, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(93, 93, 93)
                .addComponent(jTextTenCn, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(149, 149, 149))
            .addGroup(layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 802, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 961, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(d, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanelKeHoach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelMacn, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextTenCn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addGap(24, 24, 24)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(d, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelKeHoach, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jPanelKeHoach.getAccessibleContext().setAccessibleName("Kế");
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemActionPerformed
        // TODO add your handling code here:

        

        String sql = "insert into chuyennganh values(?,?)";
        try (Connection con = DataBaseHelper.getConnection();
            PreparedStatement smt = con.prepareStatement(sql)) {
            smt.setString(1, taoMaCN());
            smt.setString(2, jTextTenCn.getText());

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
            String sql = "delete from chuyennganh where macn=?";
            try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
                smt.setString(1, jLabelMacn.getText());

                int kt2 = smt.executeUpdate();
                if (kt2 > 0) {
                    JOptionPane.showMessageDialog(this, "xoa thanh cong");
                }
                initData();
                initDataMhCn();
                lamMoi();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButtonXoaActionPerformed

    private void jButtonSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaActionPerformed
        //        // TODO add your handling code here:
       
        int kt = JOptionPane.showConfirmDialog(this, "ban chan chan muon sua khong");
        if (kt == JOptionPane.CANCEL_OPTION) {
            return;
        } else if (kt == JOptionPane.OK_OPTION) {
            String sql = "update chuyennganh set tencn=? where macn = ?";
            try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {

                smt.setString(1, jTextTenCn.getText());
     
                smt.setString(2, jLabelMacn.getText());

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

    private void jButtonLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLamMoiActionPerformed
        // TODO add your handling code here:
        lamMoi();
        loadDsMh();
      //  loadDsMhChon();
      listMhChon.removeAll();
        model = (DefaultTableModel) jTableDSmhcn.getModel();
        model.setRowCount(0);
    }//GEN-LAST:event_jButtonLamMoiActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        // TODO add your handling code here:
        if(listMhChon.getSelectedIndexes()!=null)
        for (String a : listMhChon.getSelectedItems()) {
            listMh.add(a);
            listMhChon.remove(a);
        }

    }//GEN-LAST:event_jButtonBackActionPerformed

    private void jButtonGoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGoActionPerformed
        // TODO add your handling code here:

        if(listMh.getSelectedIndexes()!=null)
        for (String a : listMh.getSelectedItems()) {
            listMhChon.add(a);
            listMh.remove(a);
        }
        
        
        
    }//GEN-LAST:event_jButtonGoActionPerformed

    private void listMhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listMhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listMhActionPerformed

    private void jButtonaddMhcnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonaddMhcnActionPerformed
        // TODO add your handling code here:
        int row = jTableDSCN.getSelectedRow();
        
        if(row <0)
        {
            JOptionPane.showMessageDialog(this, "Chua chon chuyen nganh, them truoc khi lap ke hoach");
            return;
        }
        String sql2 = "insert into kehoach values(?,?,0,0)";
        try (Connection con = DataBaseHelper.getConnection();
            PreparedStatement smt = con.prepareStatement(sql2)) {
            int kt=0;
            for(String mh: listMhChon.getItems())
            {
                
                    if(checkMhChon(mh)==false)
                    {
                        smt.setString(1, jLabelMacn.getText());
                        smt.setString(2, searchMaMonHoc(mh));
                        kt = smt.executeUpdate();
                    }
                
            }
            
            if (kt > 0) {
                JOptionPane.showMessageDialog(this, "insert thanh cong");
            }
            initDataMhCn();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        listMhChon.removeAll();
        
        
        
    }//GEN-LAST:event_jButtonaddMhcnActionPerformed

    private void jButtonxoamhcnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonxoamhcnActionPerformed
        // TODO add your handling code here:
        int kt = JOptionPane.showConfirmDialog(this, "ban co muon xoa khong");
        if (kt == JOptionPane.CANCEL_OPTION) {
            return;
        } else if (kt == JOptionPane.OK_OPTION) {
            String sql = "delete from kehoach where macn=? and mamh=?";
            try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
                smt.setString(1, jLabelMacn.getText());
                smt.setString(2,searchMaMonHoc(jLabelTenMhcn.getText()));
                int kt2 = smt.executeUpdate();
                if (kt2 > 0) {
                    JOptionPane.showMessageDialog(this, "xoa thanh cong");
                }
                initDataMhCn();
                lamMoiMhcn();
                loadDsMh();
              //  loadDsMhChon();
              listMhChon.removeAll();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButtonxoamhcnActionPerformed
    
    
    private void jButtonsuamhcnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonsuamhcnActionPerformed
        // TODO add your handling code here:
         int kt = JOptionPane.showConfirmDialog(this, "ban chan chan muon sua khong");
        if (kt == JOptionPane.CANCEL_OPTION) {
            return;
        } else if (kt == JOptionPane.OK_OPTION) {
            String sql = "update kehoach set hesoth=?,hesock=? where macn = ? and mamh=?";
            try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {

                smt.setString(3, jLabelMacn.getText());
                smt.setString(4, searchMaMonHoc(jLabelTenMhcn.getText()));
                               
                smt.setFloat(1,  Float.valueOf(jTextHsTh.getText()) );
                smt.setFloat(2, Float.valueOf(jTextHsck.getText()) );
                int kt2 = smt.executeUpdate();
                if (kt2 > 0) {
                    JOptionPane.showMessageDialog(this, "update thanh cong");
                }
                initDataMhCn();
                lamMoiMhcn();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.toString());
            }
        }
    }//GEN-LAST:event_jButtonsuamhcnActionPerformed

    private void jTableDSCNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDSCNMouseClicked
        // TODO add your handling code here:
        int row = jTableDSCN.getSelectedRow();
        if(row>=0)
        {
            jLabelMacn.setText(jTableDSCN.getValueAt(row, 0).toString().toUpperCase());
            jTextTenCn.setText(jTableDSCN.getValueAt(row, 1).toString());
            //new KeHoach(jLabelMacn.getText()).setVisible(true);
            initDataMhCn();
            loadDsMh();
            // loadDsMhChon();
            listMhChon.removeAll();
            KichHoatButtonKhiChon();
            jButtonThem.setEnabled(false);
            jPanelKeHoach.setVisible(true);
            jTextHsTh.setText("");
            jTextHsck.setText("");
        }
    }//GEN-LAST:event_jTableDSCNMouseClicked

    private void jTableDSmhcnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDSmhcnMouseClicked
        // TODO add your handling code here:
        int row = jTableDSmhcn.getSelectedRow();
        if(row>=0)
        {
            jLabelTenMhcn.setText(jTableDSmhcn.getValueAt(row, 0).toString().toUpperCase());
            jTextHsTh.setText(jTableDSmhcn.getValueAt(row, 1).toString());
            jTextHsck.setText(jTableDSmhcn.getValueAt(row, 2).toString());
            jButtonsuamhcn.setEnabled(true);
            jButtonxoamhcn.setEnabled(true);
        }
    }//GEN-LAST:event_jTableDSmhcnMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator d;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonGo;
    private javax.swing.JButton jButtonLamMoi;
    private javax.swing.JButton jButtonSua;
    private javax.swing.JButton jButtonThem;
    private javax.swing.JButton jButtonXoa;
    private javax.swing.JButton jButtonaddMhcn;
    private javax.swing.JButton jButtonsuamhcn;
    private javax.swing.JButton jButtonxoamhcn;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelMacn;
    private javax.swing.JLabel jLabelTenMhcn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelKeHoach;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableDSCN;
    private javax.swing.JTable jTableDSmhcn;
    private javax.swing.JTextField jTextHsTh;
    private javax.swing.JTextField jTextHsck;
    private javax.swing.JTextField jTextTenCn;
    private java.awt.List listMh;
    private java.awt.List listMhChon;
    // End of variables declaration//GEN-END:variables
}
