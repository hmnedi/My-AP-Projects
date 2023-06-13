import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
    public static Send client;
    public static void main(String[] args) throws IOException, ClassNotFoundException {

//        Socket socket = new Socket("localhost", 7070);
//
//        // client object
//        client = new Send(socket);

        CardLaout frame = new CardLaout();
        frame.setSize(855, 447);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);


//        JSONObject jsonLogin = new JSONObject();
//        jsonLogin.put("request", "login");
//        jsonLogin.put("username", "ffeyzi");
//        jsonLogin.put("password", "1234");
//        jsonLogin.put("role", "Professor"); // Student, Professor, Admin

//        jsonLogin.put("request", "login");
//        jsonLogin.put("username", "human");
//        jsonLogin.put("password", "toor");
//        jsonLogin.put("role", "Student"); // Student, Professor, Admin


        // login
//        System.out.println(client.sendRequestgetObject(jsonLogin));

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
//        selectObjbections();
//        answerObjections(1);

        // role student:
        // show units of major
//        getUnitsOfStudent();
//        System.out.println(isTakingUnitAllowed());
        // take units
//        takeUnits(1);
//        takeUnits(2);
//        objection(1, "miss calculation");


        // role headOfFaculty
//        getUnitsOfMajor();
//        setTakingUnit(false);
//        cahngeUnitCapacity(1, 95);
//        registerScore(1);



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
}