import java.sql.*;
import java.util.Arrays;
import java.util.Vector;

public class Professor extends User implements Service{

    Course[] courses;

    public Professor(Course[] course, String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, userName, password);
        this.courses = course;

    }

    public void removeStudentFromCourse(Student student, Course course){
        String sql = "SELECT * FROM students;";
        String courses = "";
        try {
            Connection conn = DriverManager.getConnection(Connect.url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // todo: why didn't I use the WHERE in SELECT?
            while (rs.next()) {
                if (rs.getString("firstname").contains(student.firstName) && rs.getString("lastname").contains(student.lastName)){
                    courses = rs.getString("courseList");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sql2 = "UPDATE students set courseList = '"+ courses.replace(course.getName(), " ")
                + "' WHERE username = '" + student.userName + "';";
        Adaptor adaptor = new Adaptor();
        adaptor.runQuery(sql2);

    }

    public boolean submitScore(Student student, Course course, double score) {
        // check if prf and student have the course
        Course[] cs = getCourses();
        boolean flag = false;
        for (Course course1: cs) {
            if (course.equals(course1)) {
                flag = true;
                break;
            }
        }
        if (!flag) return false;
        cs = student.getCourses();
        for (Course course1: cs) {
            if (course.equals(course1)) {
                flag = false;
                break;
            }
        }
        if (flag) return false;

        Adaptor adaptor = new Adaptor();
        adaptor.insert(student, course, this, score);

        return true;
    }

    public boolean editScore(Student student, Course course, double score) {
        // check if prf and student have the course
        Course[] cs = getCourses();
        boolean flag = false;
        for (Course course1: cs) {
            if (course.equals(course1)) {
                flag = true;
                break;
            }
        }
        if (!flag) return false;
        cs = student.getCourses();
        for (Course course1: cs) {
            if (course.equals(course1)) {
                flag = false;
                break;
            }
        }
        if (flag) return false;

        String sql2 = "UPDATE scores set score = "+ score +" WHERE username = '" + student.userName + "';";
        Adaptor adaptor = new Adaptor();
        adaptor.runQuery(sql2);

        return true;
    }

    public boolean showReportCard (Student student) {
        Course[] studentCourses = student.getCourses();
        Course[] professorCourses = this.getCourses();

        //todo: I think I could have convert the Course to Array
        boolean flag = false;
        for (Course stu: studentCourses) {
            for (Course prof: professorCourses) {
                if (stu.equals(prof)){
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) return false;


        Adaptor adaptor = new Adaptor();
        String sql = "SELECT * FROM scores WHERE username = '" + student.userName + "';";
        try {
            Connection conn = DriverManager.getConnection(Connect.url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                Course course = adaptor.findCourse(rs.getInt("courseCode"));
                Professor professor = adaptor.findProfessor(rs.getString("professor"));
                double score = rs.getDouble("score");

                System.out.println(student.toString() + " " + course + ": " + score + " - by: " + professor);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    public Course[] getCourses() {
        return courses;
    }

    public void setCourses(Course[] courses) {
        this.courses = courses;
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

    //todo: I think I could have implement it in users but the courselist in prf and students are not he same so...
    @Override
    public Professor[] findProfessors() {
        // find colleagues
        Vector<Professor> listV = new Vector<>();
        for (Course crs: courses) {
            Professor[] list = new Adaptor().findProfessor(crs);
            for (Professor prf: list) listV.add(prf);
        }

        return listV.toArray(new Professor[0]);
    }

    @Override
    public Student[] findStudents() {

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
