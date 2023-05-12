import java.sql.*;
import java.util.Vector;

public class Course{
    private final String name;
    private int code, units, capacity;

    public Course(String name, int code, int units, int capacity) {
        this.name = name;
        this.code = code;
        this.units = units;
        this.capacity = capacity;
    }

    public Student[] studentsList() {
        Vector<Student> students = new Vector<Student>();

        String sql = "SELECT * FROM students;";
        try {
            Connection conn = DriverManager.getConnection(Connect.url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if (rs.getString("courseList").contains(this.name)){
                    Student tmp = new Student(rs.getString("firstname"),
                            rs.getString("lastname"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getInt("passUnits"),
                            rs.getInt("passTerms"),
                            new Course[]{this}  // todo: HOW CAN I GET other COURSE[] FROM DB? plus: i didn't add this method as a list
                    );
                    students.add(tmp);
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return students.toArray(new Student[0]);
    }

    public void remainingCapacity(){
        String sql = "SELECT * FROM students;";
        int cnt = 0;
        try {
            Connection conn = DriverManager.getConnection(Connect.url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if (rs.getString("courseList").contains(this.name)){
                    cnt++;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(capacity - cnt);
    }


    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public int getUnits() {
        return units;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }



    @Override
    public String toString() {
        return getName();
    }


}
