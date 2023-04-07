import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your query or a .txt file...");
        String input = "";
        // command/querylist1.txt
        // SELECT id AS ID, firstname AS NAME, city AS City FROM Persons WHERE (id=1) OR (firstname LIKE 'hoo%') ORDER BY id DESC;

        while(!input.equals("EXIT;")){
            System.out.print("> ");
            input = scanner.nextLine();

            if (input.endsWith(".txt")){
                // I read each line as a command but I could have split(";") it from a string too
                List<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of(input)));
                for (String query: fileContent) {
                    new SqlCode(query);
                }
            }
            else {
                new SqlCode(input);
            }

        }

    }
}
