import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            insertInto();
        }
        else if (keyWord.equals("UPDATE")){
            updateInto();
        }
        else if (keyWord.equals("DELETE")) {
            deleteInto();
        }
    }

    private void deleteInto() throws IOException {
        String tableName = query.split(" ")[2];
        // remove ; from the name
        if (tableName.charAt(tableName.length()-1) == ';') tableName = tableName.substring(0, tableName.length()-1);
        File tableFile = new File("tables/" + tableName + ".txt");
        Scanner scanner = new Scanner(tableFile);
        String[] tableColumns = scanner.nextLine().split("\\|");


        if (query.contains(" WHERE ")) {
            // load every row
            List<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of("tables/" + tableName + ".txt")));
            fileContent.remove(0); // remove the columns names

            for (int i=0; i<fileContent.size(); i++) {
                String[] values = fileContent.get(i).split("\\|");
                values = replaceNullWithSpace(values, tableColumns.length);

                if (evaluatePostfix(
                        infixToPostfix(parseIntoInfix(query.substring(query.indexOf(" WHERE ") + 7))),
                        values, tableColumns)) {
                    fileContent.remove(i);
                }
            }

            writeFile(tableFile, tableColumns, fileContent, true);
        }
        else {
            writeFile(tableFile, tableColumns);
        }

    }

    private void updateInto() throws IOException {
        String tableName = query.split(" ")[1];
        File tableFile = new File("tables/" + tableName + ".txt");
        Scanner scanner = new Scanner(tableFile);

        String[] queryColumns;
        String[] tableColumns = scanner.nextLine().split("\\|");

        if (query.contains(" WHERE ")) queryColumns = commaSplitter(query.substring(query.indexOf("SET")+3, query.indexOf(" WHERE ")));
        else queryColumns = commaSplitter(query.substring(query.indexOf("SET")+3, query.indexOf(";")));
        removeRedundantSpaces(queryColumns);

        // load every row
        List<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of("tables/" + tableName + ".txt")));
        fileContent.remove(0); // remove the columns names

        for (int i=0; i<fileContent.size(); i++){
            String[] values = fileContent.get(i).split("\\|");
            values = replaceNullWithSpace(values, tableColumns.length);

            if (query.contains(" WHERE ")){
                if (evaluatePostfix(
                        infixToPostfix(parseIntoInfix(query.substring(query.indexOf(" WHERE ")+7))),
                        values, tableColumns)) {
                    for (String column: queryColumns) {
                        String valueQuery = column.split("=")[1];
                        column = column.split("=")[0];
                        values[Arrays.asList(tableColumns).indexOf(column)] = deleteQuotation(valueQuery);
                    }
                }
            }
            else {
                for (String column: queryColumns) {
                    String valueQuery = column.split("=")[1];
                    column = column.split("=")[0];
                    values[Arrays.asList(tableColumns).indexOf(column)] = deleteQuotation(valueQuery);
                }
            }
            fileContent.set(i, columnsToDB(values));
        }

        writeFile(tableFile, tableColumns, fileContent);

    }

    private void removeRedundantSpaces(String[] array) {
        for (int i=0; i<array.length; i++){
            String tmp = "";
            for (int j=0; j<array[i].length(); j++){
                if (array[i].charAt(j) != ' ') tmp += array[i].charAt(j);
            }
            array[i] = tmp;
        }

    }

    private void insertInto() throws IOException {
        String tableName = query.split(" ")[2];
        File tableFile = new File("tables/" + tableName + ".txt");

        String[] values = commaSplitter(getParenthesesSubString(query.substring(query.indexOf("VALUES")+6)));
        // INSERT INTO Custom Columns
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

        Vector<String> orderAggregateFunctions = new Vector<>();
        Vector<String> aggregateCount = new Vector<>();
        Vector<String> aggregateAvg = new Vector<>();
        Vector<String> aggregateSum = new Vector<>();

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

            // check for Aggregate Functions
            for (int i=0; i<queryColumns.length; i++){
                if (queryColumns[i].contains("COUNT(")){
                    // todo: search about regex here
                    queryColumns[i] = queryColumns[i].substring(queryColumns[i].indexOf("(")+1, queryColumns[i].indexOf(")"));
                    aggregateCount.add(queryColumns[i]);
                    orderAggregateFunctions.add("COUNT");
                }
                else if (queryColumns[i].contains("AVG(")) {
                    queryColumns[i] = queryColumns[i].substring(queryColumns[i].indexOf("(")+1, queryColumns[i].indexOf(")"));
                    aggregateAvg.add(queryColumns[i]);
                    orderAggregateFunctions.add("AVG");

                }
                else if (queryColumns[i].contains("SUM(")) {
                    queryColumns[i] = queryColumns[i].substring(queryColumns[i].indexOf("(")+1, queryColumns[i].indexOf(")"));
                    aggregateSum.add(queryColumns[i]);
                    orderAggregateFunctions.add("SUM");

                }
            }
        }
        System.out.println();
        System.out.println("==================================");

        // PRINT COLUMNS
        Vector<Vector<String>> printSelect = new Vector<>();

        while (scanner.hasNextLine()){
            Vector<String> rows = new Vector<>();
            String[] values = scanner.nextLine().split("\\|");

            // so if I split id||name|| it gives me [id, , name, , ]. I could probably save as file in a better format
            values = replaceNullWithSpace(values, tableColumns.length);

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
                    printSelect.add(rows);
                }
            }
            // NO WHERE
            else {
                printSelect.add(rows);
            }
        }

        // decide printing aggregateFunction or columns
        if (!aggregateCount.isEmpty() || !aggregateAvg.isEmpty() || !aggregateSum.isEmpty()){
            int[] res = new int[queryColumns.length];
            int[] counteResForAvg = new int[queryColumns.length];

            for (int i=0; i<res.length; i++) res[i] = 0;
            for (int i=0; i<res.length; i++) counteResForAvg[i] = 0;

            for (Vector<String> row: printSelect){
                for (int i=0; i<row.size(); i++) {
                    // inja check bokonam tartib count, sum, avg
                    if (orderAggregateFunctions.get(i).equals("COUNT")){
                        if (!row.get(i).equals("")){
                            res[i]++;
                        }
                    }
                    else if (orderAggregateFunctions.get(i).equals("AVG")){
                        if (!row.get(i).equals("")){
                            res[i] += Integer.parseInt(row.get(i));
                            counteResForAvg[i]++;
                        }
                    }
                    else if (orderAggregateFunctions.get(i).equals("SUM")){
                        if (!row.get(i).equals("")){
                            res[i] += Integer.parseInt(row.get(i));
                        }
                    }
                }
            }

            Vector<String> javab = new Vector<>();
            for (int i=0; i<orderAggregateFunctions.size();i++) {
                if (orderAggregateFunctions.get(i).equals("AVG")){
                    float dblTmp = (float) res[i] / counteResForAvg[i];
                    javab.add(String.valueOf(dblTmp));
                }
                else {
                    javab.add(String.valueOf(res[i]));
                }
            }

            System.out.println(javab);

        }
        else {
            for (Vector<String> strings : printSelect) {
                System.out.println(strings);

            }
        }

    }

    private String[] replaceNullWithSpace(String[] values, int length) {
        if (values.length != length) {
            values = Arrays.copyOf(values, length);
            for (int i=0; i<values.length; i++) {
                if (values[i] == null){
                    values[i] = "";
                }
            }
        }
        return values;
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
                String equation = "=";

                // before the =
                while (s.charAt(i) != '=') {
                    if (s.charAt(i) != ' ') tmp += s.charAt(i);
                    i++;
                    if (s.startsWith(" LIKE", i)) {
                        equation = "LIKE";
                        break;
                    }
                    if (s.startsWith(" REGEXP", i)) {
                        equation = "REGEXP";
                        break;
                    }
                }
                if (s.charAt(i) != ' ') {
                    tmp += s.charAt(i);
                    i++;
                }

                // LIKE
                if (equation.equals("LIKE")) {
                    tmp += " LIKE ";
                    i += 6;
                }

                // REGEXP
                if (equation.equals("REGEXP")) {
                    tmp += " REGEXP ";
                    i += 8;
                }

                // skip the spaces
                while (s.charAt(i) == ' ') i++;

                // if it's digit, 12
                if (Character.isDigit(s.charAt(i))) {
                    while (Character.isDigit(s.charAt(i))) {
                        tmp += s.charAt(i);
                        if (Character.isDigit(s.charAt(i+1))) i++;
                        else break;
                    }
                }
                else{
                    // if it's string, 'hooman'
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
        if (token.contains(" LIKE ")) {
            String[] splited = token.split(" ");
            splited[2] = deleteQuotation(splited[2]);
            if (splited[2].charAt(0) == '%' && splited[2].charAt(splited[2].length()-1) == '%') {
                String theWord = splited[2].substring(1, splited[2].length()-1);
                return String.valueOf(values[Arrays.asList(tableColumns).indexOf(splited[0])].contains(theWord));
            }
            else if (splited[2].charAt(0) == '%') {
                String theWord = splited[2].substring(1);
                return String.valueOf(values[Arrays.asList(tableColumns).indexOf(splited[0])].endsWith(theWord));
            }
            else if (splited[2].charAt(splited[2].length()-1) == '%') {
                String theWord = splited[2].substring(0, splited[2].length()-1);
                return String.valueOf(values[Arrays.asList(tableColumns).indexOf(splited[0])].startsWith(theWord));
            }
            else {
                String theWord = splited[2];
                return String.valueOf(values[Arrays.asList(tableColumns).indexOf(splited[0])].equals(theWord));
            }
        }
        else if (token.contains(" REGEXP ")) {
            String[] splited = token.split(" ");
            splited[2] = deleteQuotation(splited[2]);
            Pattern pattern = Pattern.compile(splited[2], Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(values[Arrays.asList(tableColumns).indexOf(splited[0])]);
            return String.valueOf(matcher.find());
        }
        else {
            String[] splited = token.split("=");
            splited[1] = deleteQuotation(splited[1]);
            return String.valueOf(values[Arrays.asList(tableColumns).indexOf(splited[0])].equals(splited[1]));
        }

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

    private String columnsToDB(String[] columns){
        // I don't remember when I wrote this, but I could have used String.join("|", columns)
        String ret = "";
        for (String column : columns) {
            ret += column + "|";
        }
        return ret.substring(0, ret.length()-1)+"\n";
    }

    private void writeFile(File file, String[] tableColumns) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(String.join("|", tableColumns) + "\n");
        writer.close();
    }

    private void writeFile(File file, String line) throws IOException {
        FileWriter writer = new FileWriter(file, true);
        writer.write(line);
        writer.close();
    }

    private void writeFile(File file, String[] tableColumns, List<String> lines) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(String.join("|", tableColumns) + "\n");
        for (String line: lines) writer.write(line);
        writer.close();
    }

    private void writeFile(File file, String[] tableColumns, List<String> lines, boolean goNextLine) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(String.join("|", tableColumns) + "\n");
        for (String line: lines) {
            if (goNextLine) {
                writer.write(line + "\n");
            }
            else {
                writer.write(line);
            }
        }
        writer.close();
    }

}
