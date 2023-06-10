import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;
import java.util.HashMap;
import java.util.Vector;

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
                + " password text NOT NULL\n"
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
                + " professorID integer NOT NULL,\n"
                + " takingUnit boolean NOT NULL\n"
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
                + " score REAL,\n"
                + " professorID integer,\n"
                + " isEditable boolean NOT NULL\n"
                + ");";
        this.runQuery(sql);

        sql = "CREATE TABLE Objection (\n"
                + " objectID integer PRIMARY KEY,\n"
                + " studentID integer NOT NULL,\n"
                + " gradeID integer NOT NULL,\n"
                + " type text NOT NULL,\n"
                + " isRead boolean NOT NULL\n"
                + ");";
        this.runQuery(sql);

        sql = "CREATE TABLE Message (\n"
                + " messageID integer PRIMARY KEY,\n"
                + " professorID integer NOT NULL,\n"
                + " studentID integer NOT NULL,\n"
                + " text TEXT NOT NULL\n"
                + ");";
        this.runQuery(sql);

        sql = "INSERT INTO Professor(firstname, lastname, username, password) VALUES ('farid', 'feyzi', 'ffeyzi', '1234');";
        this.runQuery(sql);

        sql = "INSERT INTO Professor(firstname, lastname, username, password) VALUES ('mohammad', 'salehi', 'msalehi', '4321');";
        this.runQuery(sql);

        sql = "INSERT INTO Faculty(facultyName, professorID, takingUnit) VALUES ('computer engineering', 2, true);";
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

    public boolean loginCorrect(String username, String password, String role) {
        if (username.equals("admin") && password.equals("admin") && role.equals("Admin")) return true;

        String sql = "SELECT * FROM " + role + " WHERE username = '" + username + "' AND password = '" + password + "';";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            if (rs.next()) {
                return !rs.getString("firstname").equals("");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public JSONArray selectProfessor () {
        JSONArray lists = new JSONArray();

        String sql = "SELECT * FROM Professor;";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                JSONObject professorData = new JSONObject();
                professorData.put("id", rs.getInt("professorID"));
                professorData.put("firstname", rs.getString("firstname"));
                professorData.put("lastname", rs.getString("lastname"));
                professorData.put("username", rs.getString("username"));

                lists.add(professorData);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lists;
    }

    public int getMajorID (String majorName) {
        String sql = "SELECT * FROM Major WHERE majorName = '" + majorName + "';";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            if (rs.next()) {
                return rs.getInt("majorID");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int getFacultyIdFromStudent (String username) {
        String sql = "SELECT majorID FROM Student WHERE username = '" + username + "';";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            if (rs.next()) {
                int majorID = rs.getInt("majorID");
                String sql2 = "SELECT facultyID FROM Major WHERE majorID = " + majorID + ";";
                try (Statement stmt2 = conn.createStatement();
                     ResultSet rs2 = stmt.executeQuery(sql2)) {
                    if (rs2.next()) return rs2.getInt("facultyID");
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int getFacultyIdFromProfessor (String username) {
        int id = getProfessorID(username);
        String sql = "SELECT facultyID FROM Faculty WHERE professorID = " + id + ";";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            if (rs.next()) {
                return rs.getInt("facultyID");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }


    public int getStudentIDbyUsername (String username) {
        String sql = "SELECT * FROM Student WHERE username = '" + username + "';";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            if (rs.next()) {
                return rs.getInt("studentID");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int getStudentID (String firstname, String lastname) {
        String sql = "SELECT * FROM Student WHERE firstname = '" + firstname + "' AND lastname = '" + lastname + "';";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            if (rs.next()) {
                return rs.getInt("studentID");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int getStudentIDfromUsername (String username) {
        String sql = "SELECT * FROM Student WHERE username = '" + username + "';";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            if (rs.next()) {
                return rs.getInt("studentID");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int getProfessorID (String firstname, String lastname) {
        String sql = "SELECT * FROM Professor WHERE firstname = '" + firstname + "' AND lastname = '" + lastname + "';";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            if (rs.next()) {
                return rs.getInt("professorID");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int getProfessorID (String username) {
        String sql = "SELECT * FROM Professor WHERE username = '" + username + "';";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            if (rs.next()) {
                return rs.getInt("professorID");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int getFacultyID (String name) {
        String sql = "SELECT * FROM Faculty WHERE facultyName = '" + name + "';";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            if (rs.next()) {
                return rs.getInt("facultyID");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public JSONArray getStudentsFromUnits (int unitID) {
        JSONArray jsonArray = new JSONArray();

        String sql = "SELECT * FROM Grade WHERE unitID = " + unitID + ";";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("gradeID", rs.getInt("gradeID"));
                jsonObject.put("studentID", rs.getInt("studentID"));
                jsonObject.put("score", rs.getInt("score"));
                jsonObject.put("professorID", rs.getInt("professorID"));
                jsonObject.put("isEditable", rs.getBoolean("isEditable"));

                jsonArray.add(jsonObject);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return jsonArray;
    }

    public JSONArray getUnitIdName (String username) {
        int idprf = getProfessorID(username);
        String sql = "SELECT unitID, unitName FROM Unit INNER JOIN Professor ON Unit.professorID" +
                " = Professor.professorID WHERE Professor.professorID = " + idprf + ";";
        JSONArray jsonArray = new JSONArray();

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                JSONObject jsonData = new JSONObject();
                jsonData.put("unitName", rs.getString("unitName"));
                jsonData.put("unitID", rs.getInt("unitID"));

                jsonArray.add(jsonData);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonArray;
    }

    public JSONArray getUnitsOfStudent (String username) {
        int facultyID = getFacultyIdFromStudent(username);

        String sql = "SELECT unitID, unitName FROM Unit WHERE facultyID = " + facultyID +  ";";
        JSONArray jsonArray = new JSONArray();

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                JSONObject jsonData = new JSONObject();
                jsonData.put("unitName", rs.getString("unitName"));
                jsonData.put("unitID", rs.getInt("unitID"));

                jsonArray.add(jsonData);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonArray;

    }

    public JSONArray getObjections (int prfID) {
        String sql = "SELECT Objection.objectID, Objection.type, Grade.gradeID FROM Objection INNER JOIN Grade ON Object" +
                "ion.gradeID = Grade.gradeID WHERE Grade.professorID = " + prfID + " ;";
        JSONArray jsonArray = new JSONArray();

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("objectionID", rs.getString("objectID"));
                jsonObject.put("type", rs.getString("type"));
                jsonObject.put("gradeID", rs.getString("gradeID"));

                jsonArray.add(jsonObject);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonArray;

    }
    public JSONArray getUnitsOfFaculty (String username) {

        int facultyID = getFacultyIdFromProfessor(username);

        String sql = "SELECT * FROM Unit WHERE facultyID = " + facultyID +  ";";
        JSONArray jsonArray = new JSONArray();

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                JSONObject jsonData = new JSONObject();
                jsonData.put("unitName", rs.getString("unitName"));
                jsonData.put("unitID", rs.getInt("unitID"));
                jsonData.put("capacity", rs.getInt("capacity"));

                jsonArray.add(jsonData);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonArray;

    }

    public boolean isTakingUnitAllowed (int facultyID) {
        boolean isit = true;
        String sql = "SELECT takingUnit FROM Faculty WHERE facultyID = " + facultyID +  ";";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            if (rs.next()) {
                isit = rs.getBoolean("takingUnit");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return isit;

    }

}
