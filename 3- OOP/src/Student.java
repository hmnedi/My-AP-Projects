public class Student extends User{
    private int passUnits, passTerms;

    private Course[] courses;

    public Student(String firstName, String lastName, String userName, String password, int passUnits, int passTerms, Course[] courses) {
        super(firstName, lastName, userName, password);
        this.passUnits = passUnits;
        this.passTerms = passTerms;
        this.courses = courses;
    }

    public void getReportCard() {

    }

    @Override
    public void registerUser() {

    }

    @Override
    public void editUser() {

    }
}
