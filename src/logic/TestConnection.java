package logic;

import java.sql.*;

public class TestConnection {

    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement p_stmt = null;
        ResultSet rs = null;

        String dbHost = "134.108.190.89";
        String dbPort = "1433";
        String dbName = "Spieler2_LAB";
        String dbUser =	"wkb4";
        String dbPass = "wkb4";

        try {
            String connectionUrl = "jdbc:sqlserver://" +
                    dbHost + ":" + dbPort + ";" + "databaseName=" + dbName + ";" +
                    "user=" + dbUser + ";" + "password=" + dbPass + ";";

            //get database connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);

            //create statement
            String SQL = "SELECT * FROM spieler";
            p_stmt = con.prepareStatement(SQL);

            //execute SQL query
            rs = p_stmt.executeQuery();

            //process the result set
            while(rs.next()) {
                System.out.println(rs.getString("vorname") + " " + rs.getString("name") );
            }


        } catch(Exception e) {
            e.printStackTrace();
        }

        finally {
            if(rs!=null)
                try {
                    rs.close();
                } catch(Exception e) {}
            if(p_stmt != null)
                try {
                    p_stmt.close();
                } catch(Exception e) {}
            if(con!= null)
                try {
                    con.close();
                } catch(Exception e) {}
        }

    }

}
