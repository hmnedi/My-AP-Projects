import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        University guilanUni = new University("University of Guilan", "Public", "Rasht");

        Course AP = new Course("Advanced Programming", 321, 3, 90);
        Course BP = new Course("Basic Programming", 319, 4, 45);
        Course synthetic = new Course("synthetic 1", 102, 3, 45);
        Course logicGates = new Course("Logical Gates", 320, 3, 45);

        Professor siamakAshrafi = new Professor(new Course[]{synthetic}, "siamak", "ashrafi", "s_ashrafi", "1234");
        Professor feyzi = new Professor(new Course[]{AP, logicGates}, "farid", "feyzi", "feyzi", "toor");
        HeadOfFacility salehi = new HeadOfFacility(new Course[]{logicGates}, "mohammad", "salehi", "mmdsalehi", "root");

        Student human = new Student("hooman", "edraki", "human", "toor@root", 16, 1, new Course[]{BP, AP});
        Student arash = new Student("arash", "niazi", "arash","arash1234", 9, 1, new Course[]{BP});

        String[] someMajors = new String[]{"computer engineering", "electrical engineering", "chemical engineering"};
        Department fani = new Department(guilanUni, "Fani", siamakAshrafi, someMajors);

        Faculty CE = new Faculty("computer engineering", salehi, new Professor[]{salehi, feyzi}, new Student[]{human, arash});


        System.out.println(human);


















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