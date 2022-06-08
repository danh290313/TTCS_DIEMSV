/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import core.ChuyenNganh;
import core.MonHoc;
import design.DataBaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author danh
 */
public class Provider {
    
    public static String maGv=null;
    
    public static String searchMaMonHoc(String tenMonHoc) {
        String sql = " SELECT mamh FROM monhoc WHERE tenmh=N'" + tenMonHoc + "'";
        try {Statement st = DataBaseHelper.con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static String searchMaCn(String tenCn) {
        String sql = " SELECT macn FROM chuyennganh WHERE tencn=N'" + tenCn + "'";
        try {
           
            Statement st = DataBaseHelper.con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static MonHoc getMhTheoMa(String maMh)
    {
        MonHoc mh = new MonHoc();
        String sql = " SELECT * FROM monhoc WHERE mamh=N'" + maMh + "'";
        try {
            
            Statement st = DataBaseHelper.con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                mh.setMaMH(rs.getString(1));
                mh.setTenMH(rs.getString(2)); 
                mh.setSoTietLT(Integer.parseInt(rs.getString(3))); 
                mh.setSoTietTH(Integer.parseInt(rs.getString(4))); 
                mh.setSoTinChi(Integer.parseInt(rs.getString(5))); 
                return mh;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
     public static  String taoMaCN() {
        String sql = "select macn from chuyennganh";
        int max = 0;
        try {
            Statement smt = DataBaseHelper.con.createStatement();
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
     
     public static ChuyenNganh getCnTheoMa(String maCn)
    {
        ChuyenNganh cn = new ChuyenNganh();
        String sql = " SELECT * FROM chuyennganh WHERE macn=N'" + maCn + "'";
        try {
            
            Statement st = DataBaseHelper.con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                cn.setMacn(rs.getString(1));
                cn.setTencn(rs.getString(2)); 
                return cn;
            }
        } catch (Exception ex) {
             ex.printStackTrace();
        }
        return null;
    }
    
     public static String taoMaMH()
    {
        String sql = "select mamh from monhoc";
        int max =0;
        try
        {   Statement smt = DataBaseHelper.con.createStatement();
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
        return "MH" + String.valueOf(max +1);
    }
       
    public static boolean kiemTraDangKi(String maLTC, String maSV)
    {
        String sql = " SELECT * FROM dangki WHERE maltc=N'" + maLTC + "' and maSV=N'" + maSV + "'" ;
        try {
            
            Statement st = DataBaseHelper.con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {      
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    } 
    
     public static boolean capNhatLTC(String maLTC, String maSV,boolean huy)
     {
          String sql = "update dangki set huy=? where maltc= ? and masv = ?";
            try  {
                PreparedStatement smt = DataBaseHelper.con.prepareStatement(sql);

                smt.setBoolean(1, huy);
                smt.setString(2, maLTC);
                smt.setString(3, maSV);

                int kt2 = smt.executeUpdate();
                if (kt2 > 0) {
                    return true;
                }

            } catch (SQLException ex) {
                 ex.printStackTrace();
            }
            return false;
     }
    
   

    
    
}
