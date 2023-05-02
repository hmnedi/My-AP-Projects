public class Professor extends User{

    Course[] courses;

    public Professor(Course[] course, String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, userName, password);
        this.courses = course;

    }

    @Override
    public void registerUser() {

    }

    @Override
    public void editUser() {

    }
}
