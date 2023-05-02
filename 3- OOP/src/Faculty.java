public class Faculty {
    // goroh amoozeshi
    private String name;
    private HeadOfFacility modirGroup;
    private Professor[] professors;
    private Student[] students;

    public Faculty(String name, HeadOfFacility modirGroup, Professor[] professors, Student[] students) {
        this.name = name;
        this.modirGroup = modirGroup;
        this.professors = professors;
        this.students = students;
    }
}
