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
        if (tableName.charAt(tableName.length()-1) == ';') {
            tableName = tableName.substring(0, tableName.length()-1);
        }
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

            // so if I split id||name|| it gives me [id, , name, , ]. I could probably save as file in a better format
            if (values.length != tableColumns.length) {
                values = Arrays.copyOf(values, tableColumns.length);
                for (int i=0; i<values.length; i++) {
                    if (values[i] == null){
                        values[i] = "";
                    }
                }
            }

            // store columns in rows
            if (hasStar){
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
                if (evaluatePostfix(
                        infixToPostfix(parseIntoInfix(query.substring(query.indexOf(" WHERE ")+7))),
                        values, tableColumns)) {
                    System.out.println(rows);
                }
            }
            else {
                System.out.println(rows);
            }
        }

    }

    private Stack<String> parseIntoInfix(String s){
        Stack<String> infix = new Stack<>();

        int i = 0;
        while (i < s.length()){
            if (s.startsWith("AND ", i)){
                infix.push("AND");
                i += 3;
            }
            else if (s.startsWith("NOT ", i)){
                infix.push("NOT");
                i += 3;
            }
            else if (s.startsWith("OR ", i)){
                infix.push("OR");
                i += 2;
            }
            else if (s.charAt(i) == '('){
                infix.push("(");
            }
            else if (s.charAt(i) == ')'){
                infix.push(")");
            }
            else if (s.charAt(i) == ';'){
                break;
            }
            else if (s.charAt(i) != ' ') {
                String tmp = "";
                // before the =
                while (s.charAt(i) != '=') {
                    if (s.charAt(i) != ' ') tmp += s.charAt(i);
                    i++;
                }
                tmp += s.charAt(i);
                i++;

                // skip the spaces
                while (s.charAt(i) == ' ') i++;

                // if it's digit, id=2
                if (Character.isDigit(s.charAt(i))) {
                    while (Character.isDigit(s.charAt(i))) {
                        tmp += s.charAt(i);
                        if (Character.isDigit(s.charAt(i+1))) i++;
                        else break;
                    }
                }
                else{
                    // id it's string, firstname='hooman'
                    boolean flag = false;
                    while (s.charAt(i) != '\'' || !flag){
                        if (s.charAt(i) == '\'') flag = true;
                        if (s.charAt(i) == '\\' && s.charAt(i+1) == '\''){
                            tmp += '\'';
                            i++;
                        }
                        else if (s.charAt(i) != ' ') tmp += s.charAt(i);
                        else if (s.charAt(i) == ';') break;
                        i++;
                    }
                    tmp += '\'';
                }

                infix.push(tmp);
            }
            i++;
        }

        //infix.pop();
        return infix;
    }

    private Stack<String> infixToPostfix (Stack<String> infix) {
        Stack<String> postfix = new Stack<>(), stack = new Stack<>();
        HashMap<String, Integer> priority = new HashMap<String, Integer>();
        priority.put("(", -1);
        priority.put("AND", 0);
        priority.put("OR", 0);
        priority.put("NOT", 1);

        for (String token: infix) {
            if (token.equals("(")) {
                stack.push(token);
            }
            else if (token.equals(")")) {
                while (!stack.peek().equals("(")) {
                    postfix.push(stack.pop());
                }
                stack.pop();
            }
            else if (!isOperator(token)) {
                postfix.push(token);
            }
            else {
                while (!stack.isEmpty() && priority.get(stack.peek()) >= priority.get(token)) {
                    postfix.push(stack.pop());
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) {
            postfix.push(stack.pop());
        }

        return postfix;
    }

    private boolean evaluatePostfix (Stack<String> tokens, String[] values, String[] tableColumns) {
        Stack<String> stack = new Stack<>();

        for (String token: tokens) {
            String result;
            if (!isOperator(token)) {
                result = ops(token, values, tableColumns);
            }
            else if (token.equals("AND") || token.equals("OR")){
                String n2 = stack.pop();
                String n1 = stack.pop();
                result = ops(n1, n2, token, values, tableColumns);
            }
            else if (token.equals("NOT")){
                String n = stack.pop();
                result = ops (n, token, values, tableColumns);
            }
            else {
                result = "";
            }

            stack.push(result);
        }

        return stack.peek().equals("true");
    }


    private String ops(String n, String operation, String[] values, String[] tableColumns) {
        if (operation.equals("NOT")) {
            return String.valueOf(!n.equals("true"));
        }
        return "";
    }

    private String ops(String token, String[] values, String[] tableColumns) {
        String[] splited = token.split("=");
        splited[1] = deleteQuotation(splited[1]);

        return String.valueOf(values[Arrays.asList(tableColumns).indexOf(splited[0])].equals(splited[1]));
    }

    private String ops(String n1, String n2, String operation, String[] values, String[] tableColumns) {
        if (operation.equals("AND")) {
            return String.valueOf(n1.equals("true") && n2.equals("true"));
        }
        else if (operation.equals("OR")){
            return String.valueOf(n1.equals("true") || n2.equals("true"));
        }
        return "";
    }

    private String deleteQuotation(String s) {
        if (s.charAt(0) == '\'' && s.charAt(s.length()-1) == '\'') {
            return s.substring(1, s.length()-1);
        }
        return s;
    }

    private boolean isOperator(String token) {
        return token.equals("AND") || token.equals("OR") || token.equals("NOT");
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
            ret[i] = deleteQuotation(ret[i]);
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
