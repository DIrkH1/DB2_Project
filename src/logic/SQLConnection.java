package logic;

import java.sql.*;

public class SQLConnection {
    Connection con ;
    PreparedStatement p_stmt;
    ResultSet rs;

    public SQLConnection(){
        this.con = null;
        this.p_stmt = null;
        this.rs = null;
    }

    public void getConnection () {
        String dbHost = "134.108.190.89";
        String dbPort = "1433";
        String dbName = "WKB4_DB2_Projekt";
        String dbUser = "wkb4";
        String dbPass = "wkb4";
        String connectionUrl = "jdbc:sqlserver://" +  dbHost
                + ":" + dbPort + ";" + "databaseName=" + dbName + ";"
                + "user=" + dbUser + ";" + "password=" + dbPass + ";";
        try {
            //get database connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void closeConnection() {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            if (p_stmt != null)
                try {
                    p_stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            if (con != null)
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }
}

