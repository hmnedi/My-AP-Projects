import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Adaptor dbManager = new Adaptor();

        University guilanUni = new University("University of Guilan", "Public", "Rasht");
        dbManager.createDB(guilanUni);
        dbManager.createTables();

        Course AP = new Course("Advanced Programming", 321, 3, 90);
        Course BP = new Course("Basic Programming", 319, 4, 45);
        Course synthetic = new Course("synthetic 1", 102, 3, 45);
        Course logicGates = new Course("Logical Gates", 320, 3, 45);

        dbManager.insert(AP);
        dbManager.insert(BP);
        dbManager.insert(synthetic);
        dbManager.insert(logicGates);
        //dbManager.select(AP);


        Professor siamakAshrafi = new Professor(new Course[]{synthetic}, "siamak", "ashrafi", "s_ashrafi", "1234");
        Professor feyzi = new Professor(new Course[]{AP, logicGates}, "farid", "feyzi", "feyzi", "toor");
        Professor shekarian = new Professor(new Course[]{BP}, "mahdad", "shekarian", "shekarian", "4321");
        HeadOfFacility salehi = new HeadOfFacility(new Course[]{logicGates}, "mohammad", "salehi", "mmdsalehi", "root");

        Student human = new Student("hooman", "edraki", "human", "toor@root", 16, 1, new Course[]{BP, AP});
        Student arash = new Student("arash", "niazi", "arash","arash1234", 9, 1, new Course[]{BP});

//        System.out.println(Arrays.toString(feyzi.getCourses()));
        dbManager.insert(siamakAshrafi);

        feyzi.registerUser(); // or dbManager.insert(feyzi);
        dbManager.insert(shekarian);
        dbManager.insert(salehi);
        dbManager.insert(human);
        dbManager.insert(arash);
        dbManager.select(siamakAshrafi);
        System.out.println("==========================");
        dbManager.select(human);

//        Student[] std = BP.studentsList();
//        for(Student s: std) System.out.println(s.getFirstName());
        BP.remainingCapacity();


//        feyzi.removeStudentFromCourse(human, feyzi.getCourses()[2]);
//        dbManager.select(human);

//        feyzi.setFirstName("notFarid");
//        salehi.editProfessor(feyzi); // or feyzi.editUser();
//        dbManager.select(feyzi);

//        AP.setCapacity(85);
//        salehi.editCourse(AP);
//        dbManager.select(AP);

//        salehi.removeCourse(AP);
//        dbManager.select(AP);

//        salehi.addStudentToCourse(arash, AP);
//        dbManager.select(arash);


//        boolean b1 = feyzi.submitScore(arash, AP, 17.5);
//        boolean b2 = feyzi.submitScore(human, AP, 20);
//        boolean b3 = shekarian.submitScore(human, BP, 20);
//        boolean b4 = shekarian.submitScore(arash, BP, 20);
//
//        System.out.println(b1 + " " + b2 + " " + b3 + b4);
//
//        feyzi.showReportCard(arash);
//        feyzi.showReportCard(human);
//        salehi.showReportCard(arash); // modir goroh karnameh hame ro mitoone bebine
//
//        feyzi.editScore(human, AP, 19.25);
//        salehi.showReportCard(human);

        String[] someMajors = new String[]{"computer engineering", "electrical engineering", "chemical engineering"};
        Department fani = new Department(guilanUni, "Fani", siamakAshrafi, someMajors);

        Faculty CE = new Faculty("computer engineering", salehi, new Professor[]{salehi, feyzi}, new Student[]{human, arash});








//        String cmd;
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("1. Sign Up");
//        System.out.println("2. Sign In");
//
//        do {
//            cmd = scanner.nextLine();
//
//        } while (cmd.equals("EXIT"));

    }
}