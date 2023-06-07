import java.sql.*;
import java.util.HashMap;

public class Connect {
    public static String url;

    public void setUrl(String filename) {
        url = "jdbc:sqlite:" + filename + ".db";
    }

    private Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createNewDatabase() {
        try {
            Connection conn = this.connect();
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
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(query);
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setUpTables(){
        this.createNewDatabase();

        String sql = "CREATE TABLE Professor (\n"
                + " professorID integer PRIMARY KEY,\n"
                + " firstname text NOT NULL,\n"
                + " lastname text NOT NULL,\n"
                + " username text NOT NULL,\n"
                + " password text NOT NULL,\n"
                + " isHeadFaculty boolean NOT NULL\n"
                + ");";
        this.runQuery(sql);

        sql = "CREATE TABLE Student (\n"
                + " studentID integer PRIMARY KEY,\n"
                + " firstname text NOT NULL,\n"
                + " lastname text NOT NULL,\n"
                + " username text NOT NULL,\n"
                + " password text NOT NULL,\n"
                + " majorID integer NOT NULL\n"
                + ");";
        this.runQuery(sql);

        sql = "CREATE TABLE Major (\n"
                + " majorID integer PRIMARY KEY,\n"
                + " majorName text NOT NULL,\n"
                + " facultyID integer NOT NULL\n"
                + ");";
        this.runQuery(sql);

        // groh amoozeshi
        sql = "CREATE TABLE Faculty (\n"
                + " facultyID integer PRIMARY KEY,\n"
                + " facultyName text NOT NULL,\n"
                + " professorID integer NOT NULL\n"
                + ");";
        this.runQuery(sql);

        sql = "CREATE TABLE Unit (\n"
                + " unitID integer PRIMARY KEY,\n"
                + " unitName text NOT NULL,\n"
                + " facultyID integer NOT NULL,\n"
                + " professorID integer NOT NULL,\n"
                + " capacity integer NOT NULL\n"
                + ");";
        this.runQuery(sql);

        sql = "CREATE TABLE Grade (\n"
                + " gradeID integer PRIMARY KEY,\n"
                + " studentID integer NOT NULL,\n"
                + " unitID integer NOT NULL,\n"
                + " score integer NOT NULL,\n"
                + " isEditable boolean NOT NULL\n"
                + ");";
        this.runQuery(sql);

        sql = "INSERT INTO Professor(firstname, lastname, username, password, isHeadFaculty) VALUES ('farid', 'feyzi', 'ffeyzi', '1234', false);";
        this.runQuery(sql);

        sql = "INSERT INTO Professor(firstname, lastname, username, password, isHeadFaculty) VALUES ('mohammad', 'salehi', 'msalehi', '4321', true);";
        this.runQuery(sql);

        sql = "INSERT INTO Faculty(facultyName, professorID) VALUES ('computer engineering', 2);";
        this.runQuery(sql);

        sql = "INSERT INTO Major(majorName, facultyID) VALUES ('computer engineering', 1);";
        this.runQuery(sql);

        sql = "INSERT INTO Student(firstname, lastname, username, password, majorID) VALUES ('hooman', 'edraki', 'human', 'toor', 1);";
        this.runQuery(sql);

        sql = "INSERT INTO Unit(unitName, facultyID, professorID, capacity) VALUES ('AP', 1, 1, 90);";
        this.runQuery(sql);

//        sql = "SELECT Unit.unitName FROM Unit INNER JOIN Professor ON Unit.professorID = Professor.professorID;";
//        try (Connection conn = this.connect();
//             Statement stmt  = conn.createStatement();
//             ResultSet rs    = stmt.executeQuery(sql)){
//
//            // loop through the result set
//            while (rs.next()) {
//                System.out.println(rs.getString("unitName"));
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

    }

}
