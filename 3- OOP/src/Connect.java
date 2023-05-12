import java.sql.*;
import java.util.HashMap;

public class Connect {
    public static String url;

    public void setUrl(String filename) {
        url = "jdbc:sqlite:" + filename + ".db";
    }

    public void createNewDatabase() {
        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void runQuery(String query) {
        try{
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(query);
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}