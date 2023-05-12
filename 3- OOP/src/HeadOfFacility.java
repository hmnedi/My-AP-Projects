import java.sql.*;
import java.util.Arrays;

public class HeadOfFacility extends Professor {
    public HeadOfFacility(Course[] courses, String firstName, String lastName, String userName, String password) {
        super(courses, firstName, lastName, userName, password);
    }

    public void addProfessor (Professor professor) {
        Adaptor adaptor = new Adaptor();
        adaptor.insert(professor);
    }

    public void showProfessors() {
        Adaptor adaptor = new Adaptor();
        adaptor.select(new Professor(new Course[]{}, "", "", "", ""));
    }

    public void showCourses() {
        Adaptor adaptor = new Adaptor();
        adaptor.select(new Course("", 1, 1, 1));
    }

    public void addCourse (Course course) {
        Adaptor adaptor = new Adaptor();
        adaptor.insert(course);
    }

    public void addStudent (Student student) {
        Adaptor adaptor = new Adaptor();
        adaptor.insert(student);
    }

    public void showStudents() {
        Adaptor adaptor = new Adaptor();
        adaptor.select(new Student("", "", "", "" ,1, 1, new Course[]{}));
    }

    // removeFromCourse is inherited

    // todo: add this to run query method and just send the query

    // Professor class has setters so I'll just update the database
    public void editProfessor(Professor professor){
        // todo: wish i had ID PRIMARY KEY in users table
        String sql2 = "UPDATE professor set firstname = '"+ professor.firstName + "', lastname = '"
                + professor.lastName + "', password = '" + professor.password
                + "', courseList = '" + Arrays.toString(professor.getCourses()) + "'"
                + " WHERE username = '" + professor.userName + "';";
        Adaptor adaptor = new Adaptor();
        adaptor.runQuery(sql2);
    }

    public void editStudent(Student student){
        String sql2 = "UPDATE students set firstname = '"+ student.firstName + "', lastname = '"
                + student.lastName + "', password = '" + student.password +
                "', courseList = '" + Arrays.toString(student.getCourses()) + "'"
                + " WHERE username = '" + student.userName + "';";
        Adaptor adaptor = new Adaptor();
        adaptor.runQuery(sql2);
    }

    public void editCourse(Course course){
        String sql2 = "UPDATE courses set code = "+ course.getCode() + ", units = "
                + course.getUnits() + ", capacity = " + course.getCapacity()
                + " WHERE name = '" + course.getName() + "';";
        Adaptor adaptor = new Adaptor();
        adaptor.runQuery(sql2);
    }

    public void removeProfessor(Professor professor) {
        String sql2 = "DELETE FROM professor WHERE username = '" + professor.userName + "';";
        Adaptor adaptor = new Adaptor();
        adaptor.runQuery(sql2);
    }

    public void removeStudent(Student student) {
        String sql2 = "DELETE FROM students WHERE username = '" + student.userName + "';";
        Adaptor adaptor = new Adaptor();
        adaptor.runQuery(sql2);
    }

    public void removeCourse(Course course) {
        String sql2 = "DELETE FROM courses WHERE name = '" + course.getName() + "';";
        Adaptor adaptor = new Adaptor();
        adaptor.runQuery(sql2);
    }

    public void addStudentToCourse (Student student, Course course) {
        String sql = "SELECT * FROM students;";
        String courses = "";
        try {
            Connection conn = DriverManager.getConnection(Connect.url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if (rs.getString("firstname").contains(student.firstName) && rs.getString("lastname").contains(student.lastName)){
                    courses = rs.getString("courseList");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        courses += ", " + course.getName();
        String sql2 = "UPDATE students set courseList = '"+ courses
                + "' WHERE username = '" + student.userName + "';";
        Adaptor adaptor = new Adaptor();
        adaptor.runQuery(sql2);
    }

    // کنترل تعداد واحدهای انتخابی
    // returns how many units the student have
    public int getStudentUnits(Student student) {
        Course[] courseList = student.getCourses();
        int sum = 0;

        for (Course course: courseList) {
            sum += course.getUnits();
        }

        return sum;
    }


    //todo: maybe a better way of overriding?
    @Override
    public boolean showReportCard(Student student) {
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
}
