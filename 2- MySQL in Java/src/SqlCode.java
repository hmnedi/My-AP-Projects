import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SqlCode {
    String query;

    public SqlCode(String query) throws IOException {
        this.query = query.strip();
        detectCommand();
    }

    private void detectCommand() throws IOException {
        String keyWord = query.split(" ")[0];
        if (query.contains("CREATE TABLE")){
            //checkCreateTableCommand();
            runCreateTable();
        }
        else if (query.contains("DROP TABLE")){
            runDleteTable();
        }
        else if (keyWord.equals("SELECT")){
            Select();
        }
        else if (keyWord.equals("INSERT")){
            InsertInto();
        }



    }

    private void InsertInto() throws IOException {
        String tableName = query.split(" ")[2];
        File tableFile = new File("tables/" + tableName + ".txt");

        String[] values = commaSplitter(getParenthesesSubString(query.substring(query.indexOf("VALUES")+6)));
        if ((query.substring(11, query.indexOf("VALUES"))).contains("(")) {
            String[] queryColumns = commaSplitter(getParenthesesSubString(query.substring(11, query.indexOf("VALUES"))));
            String[] tableColumns = getTableColumns(tableName);

            String tmp = "";
            for (String columnName: tableColumns){
                if (Arrays.asList(queryColumns).contains(columnName)){
                    tmp += values[Arrays.asList(queryColumns).indexOf(columnName)];
                }
                tmp += "|";
            }
            writeFile(tableFile, tmp.substring(0, tmp.length()-1)+"\n");
        }
        else {
            writeFile(tableFile, columnsToDB(values));
        }
    }

    private void Select() throws IOException {
        String tableName = query.substring(query.indexOf("FROM")+4).strip().split(" ")[0];
        tableName = tableName.substring(0, tableName.length()-1);
        File tableFile = new File("tables/" + tableName + ".txt");
        String[] queryColumns = commaSplitter(query.substring(6, query.indexOf("FROM")));
        String[] tableColumns = getTableColumns(tableName);


        Scanner scanner = new Scanner(tableFile);
        // check for *
        boolean hasStar = false;
        if (query.contains(" * ")){
            System.out.print(scanner.nextLine());
            hasStar = true;
        }
        else {
            scanner.nextLine();
            // ALIASES
            boolean oneTimeStand1 = false; // to print '|' correctly
            for(int i=0; i<queryColumns.length; i++){
                if (oneTimeStand1) System.out.print("|");
                if (queryColumns[i].contains(" AS ")){
                    System.out.print(queryColumns[i].split(" ")[2]);
                    queryColumns[i] = queryColumns[i].split(" ")[0];
                }
                else {
                    System.out.print(queryColumns[i]);
                }
                oneTimeStand1 =true;
            }
        }
        System.out.println();
        System.out.println("==================================");

        // PRINT COLUMNS
        while (scanner.hasNextLine()){
            Vector<String> rows = new Vector<>();
            String[] values = scanner.nextLine().split("\\|");
            // store columns in rows
            if (hasStar){
                //todo check this line
                Collections.addAll(rows, values);
            }
            else {
                for (String columnName: queryColumns){
                    if (Arrays.asList(tableColumns).contains(columnName)){
                        rows.add(values[Arrays.asList(tableColumns).indexOf(columnName)]);
                    }
                }
            }

            // WHERE
            if (query.contains(" WHERE ")) {
                for (int i=0; i<rows.size(); i++){
                    if (findWhere(values, tableColumns, query.substring(query.indexOf(" WHERE ")+7))){
                        System.out.print(rows); //todo: print it legit
                    }
                }
            }
            else {
                System.out.print(rows);
            }
            System.out.println();
        }

    }

    private boolean findWhere(String[] values, String[] tableColumns,String condition){
        return true;
//        "city='rasht'";
//        if (values[Arrays.asList(tableColumns).indexOf("city")].equals("rasht")){
//
//        }

    }

    private static Vector<String> parseIntoInfix(String s){
        Stack<String> infix = new Stack<>();

        int i = 0;
        while (i < s.length()){
            if (s.startsWith(" AND ", i)){
                infix.push("AND");
                i += 3;
            }
            else if (s.startsWith("NOT ", i)){
                infix.push("NOT");
                i += 3;
            }
            else if (s.startsWith(" OR ", i)){
                infix.push("OR");
                i += 2;
            }
            else if (s.charAt(i) == '('){
                infix.push("(");
            }
            else if (s.charAt(i) == ')'){
                infix.push(")");
            }
            else if (s.charAt(i) != ' ') {
                String tmp = "";
                boolean flag = false;
                while (s.charAt(i) != '\'' || !flag) {
                    if (s.charAt(i) == '\'') flag = true;
                    if (s.charAt(i) != ' ') tmp += s.charAt(i);
                    if (s.charAt(i) == ';') break;
                    i++;
                }
                tmp += '\'';
                infix.push(tmp);
            }
            i++;
        }

        infix.pop();
        return infix;
    }

    private void runDleteTable() {
        String tableName = query.split(" ")[2];
        File tableFile = new File("tables/" + tableName + ".txt");
        tableFile.delete();
    }

    private void runCreateTable() throws IOException {
        String tableName = query.split(" ")[2];
        File tableFile = new File("tables/" + tableName + ".txt");

        String[] columns = commaSplitter(getParenthesesSubString(query));
        writeFile(tableFile, columnsToDB(columns));
    }

    private String[] getTableColumns(String tableName) throws IOException {
        File tableFile = new File("tables/" + tableName + ".txt");
        String line = new Scanner(tableFile).nextLine();
        return line.split("\\|");
    }

    private String getParenthesesSubString(String str){
        return str.substring(str.indexOf("(") + 1, str.indexOf(")"));
    }

    private String[] commaSplitter(String str) {
        String[] ret = str.split(",");
        for (int i=0; i<ret.length; i++){
            ret[i] = ret[i].strip();
        }
        return ret;
    }

    private static String columnsToDB(String[] columns){
        String ret = "";
        for (String column : columns) {
            ret += column + "|";
        }
        return ret.substring(0, ret.length()-1)+"\n";
    }

    private static void writeFile(File file, String line) throws IOException {
        FileWriter writer = new FileWriter(file, true);
        writer.write(line);
        writer.close();
    }

    //todo: nomre ezafe
    /*private void checkCreateTableCommand() {
        String[] words = query.split(" ");
        // also check the ; at the end
        if (words.length < 4) printInError("");
        if (words[0].equals("CREATE") && words[1].equals("TABLE") && )
    }

    private void printInError(String msg) {
        System.out.println("\u001B[31m" + msg + "\u001B[30m");
    }

    private void printInError(String msg, boolean goNextLine) {
        if (goNextLine){
            printInError(msg);
        }
        else{
            System.out.print("\u001B[31m" + msg + "\u001B[30m");
        }
    }*/

}
