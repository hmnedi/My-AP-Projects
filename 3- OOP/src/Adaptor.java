import java.sql.*;
import java.util.Arrays;
import java.util.Vector;

public class Adaptor {
    Connect connect = new Connect();

    public void createDB(University uni) {
        String[] name = uni.getName().split(" ");
        String filename = "";
        for (String s: name) {
            filename += s;
        }
        connect.setUrl(filename);
        connect.createNewDatabase();
    }

    public void insert(Course course) {
        String sql = "INSERT INTO courses VALUES (?, ?, ?, ?);";
        try{
            Connection conn = DriverManager.getConnection(Connect.url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, course.getName());
            pstmt.setInt(2, course.getCode());
            pstmt.setInt(3, course.getUnits());
            pstmt.setInt(4, course.getCapacity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insert(Professor professor) {
        String sql = "INSERT INTO professor VALUES (?, ?, ?, ?, ?, ?);";
        try{
            Connection conn = DriverManager.getConnection(Connect.url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, professor.getFirstName());
            pstmt.setString(2, professor.getLastName());
            pstmt.setString(3, professor.getUserName());
            pstmt.setString(4, professor.getPassword());
            pstmt.setString(5, Arrays.toString(professor.getCourses()));
            pstmt.setBoolean(6, false);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(HeadOfFacility professor) {
        String sql = "INSERT INTO professor VALUES (?, ?, ?, ?, ?, ?);";
        try{
            Connection conn = DriverManager.getConnection(Connect.url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, professor.getFirstName());
            pstmt.setString(2, professor.getLastName());
            pstmt.setString(3, professor.getUserName());
            pstmt.setString(4, professor.getPassword());
            pstmt.setString(5, Arrays.toString(professor.getCourses()));
            pstmt.setBoolean(6, true);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(Student student) {
        String sql = "INSERT INTO students VALUES (?, ?, ?, ?, ?, ?, ?);";
        try{
            Connection conn = DriverManager.getConnection(Connect.url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getUserName());
            pstmt.setString(4, student.getPassword());
            pstmt.setInt(5, student.getPassUnits());
            pstmt.setInt(6, student.getPassTerms());
            pstmt.setString(7, Arrays.toString(student.getCourses()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(Student student, Course course, Professor professor, double score) {
        String sql = "INSERT INTO scores VALUES (?, ?, ?, ?);";
        try{
            Connection conn = DriverManager.getConnection(Connect.url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getUserName());
            pstmt.setInt(2, course.getCode());
            pstmt.setString(3, professor.getUserName());
            pstmt.setDouble(4, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void select(Course tbl) {
       String sql = "SELECT * FROM courses;";
        try {
            Connection conn = DriverManager.getConnection(Connect.url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("name") + "\t" +
                        rs.getInt("code") + "\t" +
                        rs.getInt("units") + "\t" +
                        rs.getInt("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void select(Professor tbl) {
        String sql = "SELECT * FROM professor;";
        try {
            Connection conn = DriverManager.getConnection(Connect.url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("firstname") + "\t" +
                        rs.getString("lastname") + "\t" +
                        rs.getString("username") + "\t" +
                        rs.getString("password") + "\t" +
                        rs.getString("courseList") + "\t" +
                        rs.getBoolean("isHeadFaculty"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void select(Student tbl) {
        String sql = "SELECT * FROM students;";
        try {
            Connection conn = DriverManager.getConnection(Connect.url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("firstname") + "\t" +
                        rs.getString("lastname") + "\t" +
                        rs.getString("username") + "\t" +
                        rs.getString("password") + "\t" +
                        rs.getInt("passUnits") + "\t" +
                        rs.getInt("passTerms") + "\t" +
                        rs.getString("courseList"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Course findCourse(int code) {
        Course course = null;
        String sql = "SELECT * FROM courses WHERE code = " + code + ";";
        try {
            Connection conn = DriverManager.getConnection(Connect.url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                course = new Course(rs.getString("name"),
                        rs.getInt("code"), rs.getInt("units"), rs.getInt("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return course;
    }

    public Professor findProfessor(String username) {
        Professor professor = null;
        String sql = "SELECT * FROM professor WHERE username = '" + username + "';";
        try {
            Connection conn = DriverManager.getConnection(Connect.url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                professor = new Professor(new Course[]{},
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return professor;
    }

    public Professor[] findProfessor(Course course) {
        Vector<Professor> list = new Vector<Professor>();

        String sql = "SELECT * FROM professor WHERE courseList LIKE '%" + course + "%';";
        try {
            Connection conn = DriverManager.getConnection(Connect.url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                Professor professor = new Professor(new Course[]{course}, // inja bazam buge DB hasttssshshshhsh
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("password"));
                list.add(professor);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return list.toArray(new Professor[0]);
    }

    public Student findStudents(Course course) {
        Student student = null;
        String sql = "SELECT * FROM students WHERE courseList LIKE '%" + course + "%';";
        try {
            Connection conn = DriverManager.getConnection(Connect.url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            if (rs.next()) {
                student = new Student(rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("passUnits"),
                        rs.getInt("passTerms"),
                        new Course[]{course}
                        );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return student;
    }


    // todo: replace the hardcode with HashMap as args of the function
    public void createTables(){
        String sql1 = "CREATE TABLE IF NOT EXISTS department (\n"
                + " name text NOT NULL,\n"
                + " headOfDepartment text NOT NULL,\n"
                + " majorList text NOT NULL\n"
                + ");";

        String sql2 = "CREATE TABLE IF NOT EXISTS faculty (\n"
                + " name text NOT NULL,\n"
                + " headOfFaculty text NOT NULL,\n"
                + " masterList text NOT NULL,\n"
                + " studentList text NOT NULL\n"
                + ");";

        String sql3 = "CREATE TABLE IF NOT EXISTS students (\n"
                + " firstname text NOT NULL,\n"
                + " lastname text NOT NULL,\n"
                + " username text NOT NULL,\n"
                + " password text NOT NULL,\n"
                + " passUnits integer NOT NULL,\n"
                + " passTerms integer NOT NULL,\n"
                + " courseList text NOT NULL\n"
                + ");";

        String sql4 = "CREATE TABLE IF NOT EXISTS professor (\n"
                + " firstname text NOT NULL,\n"
                + " lastname text NOT NULL,\n"
                + " username text NOT NULL,\n"
                + " password text NOT NULL,\n"
                + " courseList text NOT NULL,\n"
                + " isHeadFaculty boolean NOT NULL\n"
                + ");";

        String sql5 = "CREATE TABLE IF NOT EXISTS courses (\n"
                + " name text NOT NULL,\n"
                + " code integer NOT NULL,\n"
                + " units integer NOT NULL,\n"
                + " capacity integer NOT NULL\n"
                + ");";

        String sql6 = "CREATE TABLE IF NOT EXISTS scores (\n"
                + " username text NOT NULL,\n"
                + " courseCode integer NOT NULL,\n"
                + " professor text NOT NULL,\n"
                + " score REAL\n"
                + ");";

        connect.runQuery(sql1);
        connect.runQuery(sql2);
        connect.runQuery(sql3);
        connect.runQuery(sql4);
        connect.runQuery(sql5);
        connect.runQuery(sql6);
    }

    public void runQuery(String sql) {
        connect.runQuery(sql);
    }
}
