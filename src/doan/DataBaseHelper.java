/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doan;

import java.sql.*;
import java.sql.DriverManager;

/**
 *
 * @author hyipd
 */
public class DataBaseHelper {
    
    
    static String name,pass;
    
    public static Connection getConnection() throws SQLException
    {   
        String url = "jdbc:sqlserver://;databaseName=qldiem";
        Connection con = DriverManager.getConnection(url,name,pass);
        return con; 
    }
    
}

