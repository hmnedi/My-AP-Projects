import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
    public static Send client;
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket socket = new Socket("localhost", 7070);

        // client object
        client = new Send(socket);

        JSONObject jsonLogin = new JSONObject();
        jsonLogin.put("request", "login");
        jsonLogin.put("username", "ffeyzi");
        jsonLogin.put("password", "1234");
        jsonLogin.put("role", "Professor"); // Student, Professor, Admin

//        jsonLogin.put("request", "login");
//        jsonLogin.put("username", "human");
//        jsonLogin.put("password", "toor");
//        jsonLogin.put("role", "Student"); // Student, Professor, Admin


        // login
        System.out.println(client.sendRequestgetObject(jsonLogin));

        // role admin:
//        addProfessor("mahdad", "shekarian", "sshekarian", "1111");
//        selectProfessor();
//        System.out.println(getMajorID("computer engineering"));
//        System.out.println(getProfessorID("shahbaz", "reyhani"));
//        System.out.println(getFacultyID("electrical engineering"));
//        addStudent("arash", "niazi", "arash_niazi", "7777", getMajorID("computer engineering"));
//        addProfessor("shahbaz", "reyhani", "reyhani", "0000");
//        addFaculty("electrical engineering", getProfessorID("shahbaz", "reyhani"));
//        addMajor("electrical engineering", getFacultyID("electrical engineering"));
//        setHeadFaculty(getProfessorID("mahdad", "shekarian"), getFacultyID("computer engineering"));

        // role professor:
        // 1- vahed haye ostad ro neshoon bede
//        getProfessorUnit();
        // 2- send the vahedID and daneshjohaye vahed ro neshoon bede
//        getStudentsOfaUnit(2);
        // 3- age nomre nadashtan sabt kon age dshtn edit kon
        // if (editable):
//        addScore(20);

        // role student:
        // show units of major
//        getUnitsOfStudent();
        // take units
//        takeUnits(1);
//        takeUnits(2);


        // role headOfFaculty



/*
//        create streamer (in this case we use ObjectInputStream/ObjectOutputStream)
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

        JSONObject jsonLogin = new JSONObject();
        jsonLogin.put("request", "login");
        jsonLogin.put("username", "human");
        jsonLogin.put("password", "toor");
        jsonLogin.put("role", "Student"); // Student, Professor, Admin

        outputStream.writeObject(jsonLogin);
        outputStream.flush();//after use outputStream, call outputStream.flush();
        JSONObject jsonStat = (JSONObject) inputStream.readObject();
        if ((int) jsonStat.get("status") == 200) {
            System.out.println("logged in");
            // TODO: open panel based on the role
        }
        else {
            System.out.println("wrong user pass");
        }
        */

    }



    public static void addMajor (String name, int facultyID) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "insert major");
        jsonData.put("majorName", name);
        jsonData.put("facultyID", facultyID);

        client.sendRequest(jsonData);
    }
    public static void addFaculty(String name, int profHeadId) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "insert faculty");
        jsonData.put("facultyName", name);
        jsonData.put("professorID", profHeadId);

        client.sendRequest(jsonData);
    }

    public static void addProfessor(String firstname, String lastname, String username, String password) throws IOException, ClassNotFoundException {
        JSONObject jsonProf = new JSONObject();
        jsonProf.put("request", "insert professor");
        jsonProf.put("firstname", firstname);
        jsonProf.put("lastname", lastname);
        jsonProf.put("username", username);
        jsonProf.put("password", password);

        client.sendRequest(jsonProf);
    }

    public static void addStudent(String firstname, String lastname, String username, String password, int majorID) throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "insert student");
        jsonData.put("firstname", firstname);
        jsonData.put("lastname", lastname);
        jsonData.put("username", username);
        jsonData.put("password", password);
        jsonData.put("majorID", majorID);

        client.sendRequest(jsonData);
    }

    public static void selectProfessor() throws IOException, ClassNotFoundException {
        JSONObject jsonProf = new JSONObject();
        jsonProf.put("request", "select all professor");


        System.out.println(client.sendRequestgetArray(jsonProf).toString());
    }

    public static int getMajorID (String name) throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "get MajorID");
        jsonData.put("majorName", name);

        return (int) client.sendRequestgetObject(jsonData).get("majorID");

    }

    public static int getProfessorID (String firstname, String lastname) throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "get professorID");
        jsonData.put("firstname", firstname);
        jsonData.put("lastname", lastname);

        return (int) client.sendRequestgetObject(jsonData).get("professorID");

    }

    public static int getStudentID (String firstname, String lastname) throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "get studentID");
        jsonData.put("firstname", firstname);
        jsonData.put("lastname", lastname);

        return (int) client.sendRequestgetObject(jsonData).get("studentID");

    }

    public static int getFacultyID (String name) throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "get facultyID");
        jsonData.put("facultyName", name);

        return (int) client.sendRequestgetObject(jsonData).get("facultyID");
    }

    public static void setHeadFaculty(int professorID, int facultyID) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "set headFaculty");
        jsonData.put("professorID", professorID);
        jsonData.put("facultyID", facultyID);

        // we can send id if we show all the professors to the client

        client.sendRequest(jsonData);
    }

    public static void getProfessorUnit() throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "get professorUnits");

        JSONArray jsonArray = client.sendRequestgetArray(jsonData);
        System.out.println(jsonArray.toString());
    }

    private static void getStudentsOfaUnit(int unitID) throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "getStudentsOfaUnit");
        jsonData.put("unitID", unitID);
        System.out.println(client.sendRequestgetArray(jsonData));
    }

    private static void getUnitsOfStudent() throws IOException, ClassNotFoundException {
        // units that a student can choose (from their faculty)
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "getUnitsOfStudent");
        System.out.println(client.sendRequestgetArray(jsonData));

    }

    public static void takeUnits(int unitID) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "takeUnit");
        jsonData.put("unitID", unitID);

        client.sendRequest(jsonData);
    }

    public static void addScore (double score) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "set score");
        jsonData.put("score", score);

        client.sendRequest(jsonData);
    }
}
