/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanly;

import com.raven.chart.ModelChart;
import doan.DataBaseHelper;
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
public class JPanelThongKe extends javax.swing.JPanel {

    /**
     * Creates new form JPanelThongKe
     */
    DefaultTableModel model = new DefaultTableModel();
    public JPanelThongKe() {
        initComponents();
        initData();
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
    
     public void timKiemmh(String s)
    {   
        model = (DefaultTableModel) jTableDSMH.getModel();
        String sql = "select * from monhoc where mamh like N'%"+s+"%' or tenmh like N'%"+s+"%' or sotietlt like N'%"+s+"%' or sotietth like N'%"+s+"%' or sotinchi like N'%"+s+"%'";
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
            jTableDSMH.setModel(model);
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
        System.out.println("" + sql);
        
    }
     
     public void initData()
    {   
        model = (DefaultTableModel) jTableDSMH.getModel();
        String sql = "select * from monhoc";
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
            jTableDSMH.setModel(model);
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }
     
    public void initChart(String maMH,String tenMH)
    {   
        chart.removeData();
        
        String sql = "{CALL usp_ThongKeDiemTheoMon(?)}";
        try(Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);)
        {   
            smt.setString(1, maMH);
           
            ResultSet rs = smt.executeQuery();
            int i =0;
            double[] countDiem = new double[]{0,0,0,0,0,0,0,0,0,0};
     
            while(rs.next())
            {   
                switch(rs.getString(1))
                {
                    case "0-1": 
                    {
                        countDiem[0]=rs.getDouble(2);
                    }
                    case "1-2": 
                    {
                        countDiem[1]=rs.getDouble(2);
                    }
                    case "2-3": 
                    {
                        countDiem[2]=rs.getDouble(2);
                    }
                    case "3-4": 
                    {
                        countDiem[3]=rs.getDouble(2);
                    }
                    case "4-5": 
                    {
                        countDiem[4]=rs.getDouble(2);
                    }
                    case "5-6": 
                    {
                        countDiem[5]=rs.getDouble(2);
                    }
                    case "6-7": 
                    {
                        countDiem[6]=rs.getDouble(2);
                    }
                    case "7-8": 
                    {
                        countDiem[7]=rs.getDouble(2);
                    }
                    case "8-9": 
                    {
                        countDiem[8]=rs.getDouble(2);
                    }
                    case "9-10": 
                    {
                        countDiem[9]=rs.getDouble(2);
                    }
                  
                    
                }
              
                
            }
            
            chart.addData(new ModelChart(tenMH, countDiem));
           
            
        } catch (SQLException ex) {
            ex.printStackTrace();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDSMH = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jTextTimKiem = new javax.swing.JTextField();
        jLabelTenMH1 = new javax.swing.JLabel();
        jLabelTenMh = new javax.swing.JLabel();

        searchText1.setText("searchText1");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Thống Kê");
        jPanel1.add(jLabel1);

        jScrollPane1.setBackground(new java.awt.Color(255, 204, 204));

        jTableDSMH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Môn Học", "Tên Môn Học", "Số tiết lý thuyết", "Số tiết thưc hành", "Số tín chỉ"
            }
        ));
        jTableDSMH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDSMHMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableDSMH);

        jLabel12.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel12.setText("Tìm Kiếm:");
        jLabel12.setMaximumSize(new java.awt.Dimension(90, 22));
        jLabel12.setPreferredSize(new java.awt.Dimension(90, 13));

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

        jLabelTenMH1.setBackground(new java.awt.Color(255, 204, 204));
        jLabelTenMH1.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabelTenMH1.setText("Tên Môn Học: ");

        jLabelTenMh.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1053, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108)
                        .addComponent(jLabelTenMH1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTenMh, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84))
                    .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jTextTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTenMh, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTenMH1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94)
                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTableDSMHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDSMHMouseClicked
        // TODO add your handling code here:
        int row = jTableDSMH.getSelectedRow();
        if(row>=0)
        {
            jLabelTenMh.setText(jTableDSMH.getValueAt(row, 1).toString().toUpperCase());
            initChart(jTableDSMH.getValueAt(row, 0).toString(),jTableDSMH.getValueAt(row, 1).toString().toUpperCase());
            chart.setVisible(false);
            chart.setVisible(true);
        }
    }//GEN-LAST:event_jTableDSMHMouseClicked

    private void jTextTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextTimKiemActionPerformed

    private void jTextTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextTimKiemKeyReleased
        // TODO add your handling code here:
        timKiemmh(jTextTimKiem.getText());
    }//GEN-LAST:event_jTextTimKiemKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.chart.Chart chart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabelTenMH1;
    private javax.swing.JLabel jLabelTenMh;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableDSMH;
    private javax.swing.JTextField jTextTimKiem;
    private com.raven.swing.SearchText searchText1;
    // End of variables declaration//GEN-END:variables
}
