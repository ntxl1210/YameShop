/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author NeedNguyen
 */
public class clsConnectDB {
    String connectionString = "jdbc:sqlserver://localhost:1433;" 
            + "databaseName=Yame Shop;user=sa;password=123;";
    Connection conn;
    public clsConnectDB(){
       
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(connectionString);
            
            if(conn != null){
                System.out.println("Kết nối CSDL SQL Server thành công!");
            }
            
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    
    public static Connection getJDBCConnection() throws SQLException {
         String connectionString = "jdbc:sqlserver://localhost:1433;\\SQL2019" 
            + "databaseName=Yame Shop;user=sa;password=123;";
        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(connectionString);
        } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    //Thực thi câu lệnh SELECT
    public ResultSet excuteQueryGetTable(String cauTruyVanSQL){
        try {
            Statement stmt = conn.createStatement();           
            ResultSet rs = stmt.executeQuery(cauTruyVanSQL);
            return rs;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
            
        return null;
    }
    //Thực thi INSERT, DELETE, UPDATE
    public void excuteQueryUpdateDB(String cauTruyVanSQL){
       
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cauTruyVanSQL);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
}
