import java.sql.*;
import java.util.Arrays;
import java.util.Vector;

public class Student extends User implements Service{
    private int passUnits, passTerms;

    private Course[] courses;

    public Student(String firstName, String lastName, String userName, String password, int passUnits, int passTerms, Course[] courses) {
        super(firstName, lastName, userName, password);
        this.passUnits = passUnits;
        this.passTerms = passTerms;
        this.courses = courses;
    }

    public int getPassUnits() {
        return passUnits;
    }

    public int getPassTerms() {
        return passTerms;
    }

    public Course[] getCourses() {
        return courses;
    }

    public void showReportCard() {
        Adaptor adaptor = new Adaptor();
        String sql = "SELECT * FROM scores WHERE username = '" + this.userName + "';";
        try {
            Connection conn = DriverManager.getConnection(Connect.url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                Course course = adaptor.findCourse(rs.getInt("courseCode"));
                Professor professor = adaptor.findProfessor(rs.getString("professor"));
                double score = rs.getDouble("score");

                System.out.println(this.toString() + " " + course + ": " + score + " - by: " + professor);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void registerUser() {
        Adaptor dbManager = new Adaptor();
        dbManager.insert(this);
    }

    @Override
    public void editUser() {
        String sql2 = "UPDATE students set firstname = '"+ this.firstName + "', lastname = '"
                + this.lastName + "', password = '" + this.password +
                "', courseList = '" + Arrays.toString(this.getCourses()) + "'"
                + " WHERE username = '" + this.userName + "';";
        Adaptor adaptor = new Adaptor();
        adaptor.runQuery(sql2);
    }

    @Override
    public Professor[] findProfessors() {
        // how to choose if two professors have the same class?
        Vector<Professor> listV = new Vector<Professor>();
        for (Course crs: courses) {
            Professor[] list = new Adaptor().findProfessor(crs);
            for (Professor prf: list) listV.add(prf);
        }

        return listV.toArray(new Professor[0]);
    }

    @Override
    public Student[] findStudents() {
        // find classmates

        Student[] list = new Student[courses.length];

        int i=0;
        for (Course crs: courses) {
            list[i] = new Adaptor().findStudents(crs);
        }

        return list;
    }

    @Override
    public boolean doesUsernameExist() {
        String sql = "SELECT * FROM professor WHERE username = '" + this.userName + "';";
        try {
            Connection conn = DriverManager.getConnection(Connect.url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isPassCorrect() {
        String sql = "SELECT * FROM professor WHERE username = '"
                + this.userName
                + "' AND password = '"
                + this.password
                + "';";

        try {
            Connection conn = DriverManager.getConnection(Connect.url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
