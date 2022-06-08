/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanly;

import com.raven.chart.ModelChart;
import static dao.Provider.searchMaCn;
import static dao.Provider.searchMaMonHoc;
import design.DataBaseHelper;
import java.awt.Color;
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
 * @author danh
 */
public class JPanelThongKe2 extends javax.swing.JPanel {

    /**
     * Creates new form JPanelThongKe
     */
    DefaultTableModel model = new DefaultTableModel();
    public JPanelThongKe2() {
        initComponents();
        initboxCn();
        chart.addLegend("0-1", new Color(245, 189, 135));
        chart.addLegend("1-2", new Color(135, 189, 245));
        chart.addLegend("2-3", new Color(189, 135, 245));
        chart.addLegend("3-4", new Color(139, 0, 222));
        chart.addLegend("4-5", new Color(120, 30, 190));
        chart.addLegend("5-6", new Color(140, 50, 180));
        chart.addLegend("6-7", new Color(160, 70, 170));
        chart.addLegend("7-8", new Color(180, 90, 160));
        chart.addLegend("8-9", new Color(190, 110, 150));
        chart.addLegend("9-10", new Color(100, 150, 130));
        
        //getContentPane().setBackground(new Color(250, 250, 250));
        
    }
    
    public void initboxCn() {
        String sql = "select tencn from chuyennganh";
        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
            ResultSet rs = smt.executeQuery();
            while (rs.next()) {
                jComboBoxCn.addItem(rs.getString(1));
            }          
            

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }
     
    public void initboxMhTheoCn(String tenCn) {
        jComboBoxMH.removeAllItems();
        String sql = "{ CALL MonHocTheoCn(?)}";
        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
            smt.setString(1, tenCn);
            ResultSet rs = smt.executeQuery();
            while (rs.next()) {
                jComboBoxMH.addItem(rs.getString(1));
            }          
            

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }
    
    public void initboxNamTheoMon(String tenMh) {
        jComboBoxNam.removeAllItems();
        String sql = "{ CALL NamHocTheoMon(?)}";
        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
            smt.setString(1, tenMh);
            ResultSet rs = smt.executeQuery();
            while (rs.next()) {
                jComboBoxNam.addItem(rs.getString(1));
            }          
            

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }
    
    public Float diemTrungBinhMonHoc(String maCn, String maMH,String Nam, String Hocki)
    {   
        
        
        String sql = "{CALL usp_ThongKeDiemTB(?,?,?,?)}";
        try(Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);)
        {   
            smt.setString(1, maCn);
            smt.setString(2, maMH);
            smt.setString(3, Nam);
            smt.setString(4, Hocki);
            
            ResultSet rs = smt.executeQuery();
            if(rs.next()) return rs.getFloat(1);

            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
     
    
     
    public void initChart(String maCn, String maMH,String Nam, String Hocki)
    {   
        chart.removeData();
        double[] countDiem = new double[]{0,0,0,0,0,0,0,0,0,0};
        String sql = "{CALL usp_ThongKeDiemTheoMonNam(?,?,?,?)}";
        try(Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);)
        {   
            smt.setString(1, maCn);
            smt.setString(2, maMH);
            smt.setString(3, Nam);
            smt.setString(4, Hocki);
            
           
            ResultSet rs = smt.executeQuery();
            int i =0;
            while(rs.next())
            {   
                System.out.println("" + rs.getString(1));
                switch(rs.getString(1))
                {
                    case "0-1": 
                    {
                        countDiem[0]=rs.getDouble(2);
                        break;
                    }
                    case "1-2": 
                    {
                        countDiem[1]=rs.getDouble(2);
                         break;
                    }
                    case "2-3": 
                    {
                        countDiem[2]=rs.getDouble(2);
                         break;
                    }
                    case "3-4": 
                    {
                        countDiem[3]=rs.getDouble(2);
                         break;
                    }
                    case "4-5": 
                    {
                        countDiem[4]=rs.getDouble(2);
                         break;
                    }
                    case "5-6": 
                    {
                        countDiem[5]=rs.getDouble(2);
                        break;
                    }
                    case "6-7": 
                    {
                        countDiem[6]=rs.getDouble(2);
                         break;
                    }
                    case "7-8": 
                    {
                        countDiem[7]=rs.getDouble(2);
                         break;
                    }
                    case "8-9": 
                    {
                        countDiem[8]=rs.getDouble(2);
                         break;
                    }
                    case "9-10": 
                    {
                        countDiem[9]=rs.getDouble(2);
                        break;
                    }
                  
                    
                }
              
                
            }
            
            chart.addData(new ModelChart(jComboBoxMH.getSelectedItem().toString(), countDiem));
           
            
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
         for(int i=0; i<10;i++)
            {
                System.out.println(i+ " " + countDiem[i]);
            }
           
        
        
        
        
        //chart.addData(new ModelChart("January", new double[]{500, 200, 80,89,600, 750, 90,150}));
        //chart.addData(new ModelChart("February", new double[]{600, 750, 90,150}));
        //chart.addData(new ModelChart("March", new double[]{200, 350, 460,900}));
        //chart.addData(new ModelChart("April", new double[]{480, 150, 750,700}));
        //chart.addData(new ModelChart("May", new double[]{350, 540, 300,150}));
        //chart.addData(new ModelChart("June", new double[]{190, 280, 81,200}));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchText1 = new com.raven.swing.SearchText();
        chart = new com.raven.chart.Chart();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBoxCn = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jComboBoxMH = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jComboBoxNam = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jComboBoxHk = new javax.swing.JComboBox<>();
        jButtonThongKe = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabelDiemTB = new javax.swing.JLabel();

        searchText1.setText("searchText1");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Thống Kê Chi Tiết");
        jPanel1.add(jLabel1);

        jLabel9.setBackground(new java.awt.Color(255, 204, 204));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Chuyên Ngành:");

        jComboBoxCn.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jComboBoxCn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBoxCnMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jComboBoxCnMousePressed(evt);
            }
        });
        jComboBoxCn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCnActionPerformed(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(255, 204, 204));
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Môn Học:");

        jComboBoxMH.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jComboBoxMH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMHActionPerformed(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(255, 204, 204));
        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setText("Năm học:");

        jComboBoxNam.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jComboBoxNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2014", "2015", "2016", "2017", "2018", "2019", "2020" }));
        jComboBoxNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxNamActionPerformed(evt);
            }
        });

        jLabel15.setBackground(new java.awt.Color(255, 204, 204));
        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setText("Học Kì:");

        jComboBoxHk.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jComboBoxHk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HK1", "HK2" }));

        jButtonThongKe.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonThongKe.setText("Thống Kê");
        jButtonThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonThongKeActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Điểm trung bình môn:");

        jLabelDiemTB.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelDiemTB.setForeground(new java.awt.Color(204, 0, 51));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1053, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(434, 434, 434)
                                .addComponent(jButtonThongKe))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBoxCn, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(42, 42, 42)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(72, 72, 72)
                                        .addComponent(jComboBoxMH, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(224, 224, 224))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jComboBoxNam, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(11, 11, 11)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(72, 72, 72)
                                                .addComponent(jComboBoxHk, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabelDiemTB, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxCn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxMH, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxNam, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxHk, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jButtonThongKe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDiemTB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxCnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCnActionPerformed
        // TODO add your handling code here:
        initboxMhTheoCn(jComboBoxCn.getSelectedItem().toString().trim());
        
    }//GEN-LAST:event_jComboBoxCnActionPerformed

    private void jComboBoxCnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxCnMousePressed
        // TODO add your handling code here:
      
    }//GEN-LAST:event_jComboBoxCnMousePressed

    private void jComboBoxCnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxCnMouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jComboBoxCnMouseClicked

    private void jComboBoxMHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMHActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBoxMHActionPerformed

    private void jComboBoxNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxNamActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBoxNamActionPerformed

    private void jButtonThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThongKeActionPerformed
        // TODO add your handling code here:
        initChart(searchMaCn(jComboBoxCn.getSelectedItem().toString().trim()), searchMaMonHoc(jComboBoxMH.getSelectedItem().toString().trim()), jComboBoxNam.getSelectedItem().toString(), jComboBoxHk.getSelectedItem().toString());
        chart.setVisible(false);
        chart.setVisible(true);
        Float diemTb = diemTrungBinhMonHoc(searchMaCn(jComboBoxCn.getSelectedItem().toString().trim()), searchMaMonHoc(jComboBoxMH.getSelectedItem().toString().trim()), jComboBoxNam.getSelectedItem().toString(), jComboBoxHk.getSelectedItem().toString());
        if(diemTb!=null)
            jLabelDiemTB.setText(String.valueOf(diemTb));
        
        
        
    }//GEN-LAST:event_jButtonThongKeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.chart.Chart chart;
    private javax.swing.JButton jButtonThongKe;
    private javax.swing.JComboBox<String> jComboBoxCn;
    private javax.swing.JComboBox<String> jComboBoxHk;
    private javax.swing.JComboBox<String> jComboBoxMH;
    private javax.swing.JComboBox<String> jComboBoxNam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelDiemTB;
    private javax.swing.JPanel jPanel1;
    private com.raven.swing.SearchText searchText1;
    // End of variables declaration//GEN-END:variables
}
