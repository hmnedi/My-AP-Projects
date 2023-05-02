public class Department {
    // daneshkhadeh

    private University uni;
    private String name;
    private Professor headOfDepartment; //todo: no one said anything about this
    private String[] majors;

    public Department(University uni, String name, Professor headOfDepartment, String[] majors) {
        this.uni = uni;
        this.name = name;
        this.headOfDepartment = headOfDepartment;
        this.majors = majors;
    }

    public University getUni() {
        return uni;
    }

    public String getName() {
        return name;
    }

    public Professor getHeadOfDepartment() {
        return headOfDepartment;
    }

    public String[] getMajors() {
        return majors;
    }
}
