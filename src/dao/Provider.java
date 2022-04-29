/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import doan.DataBaseHelper;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author danh
 */
public class Provider {
    public static String searchMaMonHoc(String tenMonHoc) {
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
    
    public static String searchMaCn(String tenCn) {
        String sql = " SELECT macn FROM chuyennganh WHERE tencn=N'" + tenCn + "'";
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
    
    
    
}
