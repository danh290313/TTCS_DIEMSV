/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design;

import java.sql.*;
import java.sql.DriverManager;

/**
 *
 * @author hyipd
 */
public class DataBaseHelper {
    
    public static Connection con=null;
    public static Connection getConnection() throws SQLException
    {   
        String url = "jdbc:sqlserver://;databaseName=qldiem";
        String name ="sa";
        String pass ="sa";
        Connection conn = DriverManager.getConnection(url,name,pass);
        return conn; 
    }
   
    
}

