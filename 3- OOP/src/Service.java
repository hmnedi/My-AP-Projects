public interface Service {
    Professor[] findProfessors();
    Student[] findStudents();

    boolean doesUsernameExist();
    boolean isPassCorrect();
}
