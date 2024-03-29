import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class Main {
    public static void main(String[] args) throws IOException {
        

        Connect connect = new Connect();
        connect.setUrl("university");
//        connect.setUpTables();

        ServerSocket serverSocket = new ServerSocket(7070);


        while (true) {
            Socket clientSocket = serverSocket.accept();
//        get connected socket and save it in ClientHandler class
            ClientHandler clienthandler = new ClientHandler(clientSocket);

//            After defining the function of each ClientHandler (run function), we start it
            clienthandler.start();
//            then go to first line of loop and wait for new client
        }

    }
}

class ClientHandler extends Thread {
    Socket clientSocket;
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;

    }

    @Override
    public void run() {
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;

        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String username = null, password = null, role = null;
        int unitID = 0;
        while (clientSocket.isConnected()) {

            try {
                // parse the request from jason
                JSONObject jsonReq = (JSONObject) inputStream.readObject();
                String req = (String) jsonReq.get("request");

                if (req.equals("login")) {
                    System.out.println("checking data...");

                    username = (String) jsonReq.get("username");
                    password = (String) jsonReq.get("password");
                    role = (String) jsonReq.get("role");

                    JSONObject jsonStat = new JSONObject();
                    if (new Connect().loginCorrect(username, password, role)){
                        jsonStat.put("status", 200);
                        System.out.println("login successful");
                    }
                    else {
                        jsonStat.put("status", 401);
                        System.out.println("login failed");
                    }

                    outputStream.writeObject(jsonStat);
                    outputStream.flush();
                }
                else if (req.equals("insert professor")) {
                    String sql = "INSERT INTO Professor(firstname, lastname, username, password)"
                            + " VALUES ('"+ jsonReq.get("firstname")
                            +"', '"+ jsonReq.get("lastname")
                            +"', '"+ jsonReq.get("username")
                            +"', '"+ jsonReq.get("password")
                            +"');";
                    new Connect().runQuery(sql);
                }
                else if (req.equals("select all professor")) {
                    JSONArray jsonArray = new Connect().selectProfessor();
                    outputStream.writeObject(jsonArray);
                    outputStream.flush();
                }
                else if (req.equals("get professorID")){
                    int id = new Connect().getProfessorID((String) jsonReq.get("firstname"), (String) jsonReq.get("lastname"));
                    JSONObject jsonDataSend = new JSONObject();
                    jsonDataSend.put("professorID", id);
                    outputStream.writeObject(jsonDataSend);
                    outputStream.flush();
                }
                else if (req.equals("get professorID by username")){
                    int id = new Connect().getProfessorID((String) jsonReq.get("username"));
                    JSONObject jsonDataSend = new JSONObject();
                    jsonDataSend.put("professorID", id);
                    outputStream.writeObject(jsonDataSend);
                    outputStream.flush();
                }
                else if (req.equals("get studentID")){
                    int id = new Connect().getStudentID((String) jsonReq.get("firstname"), (String) jsonReq.get("lastname"));
                    JSONObject jsonDataSend = new JSONObject();
                    jsonDataSend.put("studentID", id);
                    outputStream.writeObject(jsonDataSend);
                    outputStream.flush();
                }
                else if (req.equals("get MajorID")){
                    int id = new Connect().getMajorID((String) jsonReq.get("majorName"));
                    JSONObject jsonDataSend = new JSONObject();
                    jsonDataSend.put("majorID", id);
                    outputStream.writeObject(jsonDataSend);
                    outputStream.flush();
                }
                else if (req.equals("get facultyID")){
                    int id = new Connect().getFacultyID((String) jsonReq.get("facultyName"));
                    JSONObject jsonDataSend = new JSONObject();
                    jsonDataSend.put("facultyID", id);
                    outputStream.writeObject(jsonDataSend);
                    outputStream.flush();
                }
                else if (req.equals("insert student")) {
                    String sql = "INSERT INTO Student(firstname, lastname, username, password, majorID)"
                            + " VALUES ('"+ jsonReq.get("firstname")
                            +"', '"+ jsonReq.get("lastname")
                            +"', '"+ jsonReq.get("username")
                            +"', '"+ jsonReq.get("password")
                            +"', "+ jsonReq.get("majorID")
                            +");";
                    new Connect().runQuery(sql);
                }
                else if (req.equals("insert faculty")) {
                    String sql = "INSERT INTO Faculty(facultyName, professorID, takingUnit)"
                            + " VALUES ('"+ jsonReq.get("facultyName")
                            +"', "+ jsonReq.get("professorID")
                            +", true);";
                    new Connect().runQuery(sql);
                }
                else if (req.equals("insert major")) {
                    String sql = "INSERT INTO Major(majorName, facultyID)"
                            + " VALUES ('"+ jsonReq.get("majorName")
                            +"', "+ jsonReq.get("facultyID")
                            +");";
                    new Connect().runQuery(sql);
                }
                else if (req.equals("set headFaculty")) {
                    String sql = "UPDATE Faculty SET professorID = "
                            + jsonReq.get("professorID")
                            + " WHERE facultyID = "
                            + jsonReq.get("facultyID")
                            + " ;";
                    new Connect().runQuery(sql);
                }
                else if (req.equals("get professorUnits")) {
                    JSONArray jsonData = new Connect().getUnitIdName(username);
                    outputStream.writeObject(jsonData);
                    outputStream.flush();
                }
                else if (req.equals("getUnitsOfStudent")) {
                    JSONArray jsonVahedPassShode = new Connect().getUnitsOfStudent(username);
                    JSONArray jsonKollUnitFacult = new Connect().getUnitsOfFacultyForStudent(username);
                    JSONArray jsonMotamam = new JSONArray();
                    for (int i=0; i<jsonVahedPassShode.toArray().length; i++){
                        JSONObject pass = (JSONObject) jsonVahedPassShode.get(i);

                        JSONObject jsonData = new JSONObject();
                        for (int j=0; j<jsonKollUnitFacult.toArray().length;j++){
                            JSONObject kolli = (JSONObject) jsonKollUnitFacult.get(j);
                            if (pass.get("unitID") != kolli.get("unitID")) {
                                jsonData.put("unitID", kolli.get("unitID"));
                                jsonData.put("unitName", new Connect().getNameFromUnitID((int) kolli.get("unitID")));
                                jsonMotamam.add(jsonData);
                            }
                        }
                    }
                    outputStream.writeObject(jsonMotamam);
                    outputStream.flush();
                }
                else if (req.equals("takeUnit")) {
                    String sql = "INSERT INTO Grade(studentID, unitID, isEditable)"
                            + " VALUES (" + new Connect().getStudentIDfromUsername(username)
                            +", "+ jsonReq.get("unitID") + ", true);";
                    new Connect().runQuery(sql);
                }
                else if (req.equals("getStudentsOfaUnit")) {
                    unitID = (int) jsonReq.get("unitID");
                    JSONArray jsonArray = new Connect().getStudentsFromUnits(unitID);
                    outputStream.writeObject(jsonArray);
                    outputStream.flush();
                }
                else if (req.equals("set score")) {
                    String sql = "UPDATE Grade SET score = "
                            + jsonReq.get("score")
                            + ", professorID = "
                            + new Connect().getProfessorID(username)
                            + " WHERE gradeID = "
                            + jsonReq.get("gradeID")
                            + ";";

                    new Connect().runQuery(sql);
                }
                else if (req.equals("getUnitsOfFaculty")) {
                    // i could have used a query with 3 table inner join
                    JSONArray jsonArray = new Connect().getUnitsOfFaculty(username);
                    outputStream.writeObject(jsonArray);
                    outputStream.flush();
                }
                else if (req.equals("isTakingUnitAllowed")) {
                    Connect connect = new Connect();
                    JSONObject jsonObject = new JSONObject();
                    if (role.equals("Student")) {
                        jsonObject.put("takingUnitStats", connect.isTakingUnitAllowed(connect.getFacultyIdFromStudent(username)));
                    } else {
                        jsonObject.put("takingUnitStats", connect.isTakingUnitAllowed(connect.getFacultyIdFromProfessor(username)));
                    }
                    outputStream.writeObject(jsonObject);
                    outputStream.flush();

                }
                else if (req.equals("set TakingUnit")) {
                    Connect connect = new Connect();

                    String sql = "UPDATE Faculty SET takingUnit = "
                            + jsonReq.get("state")
                            + " WHERE facultyID = "
                            + connect.getFacultyIdFromProfessor(username)
                            + ";";

                    connect.runQuery(sql);
                }
                else if (req.equals("cahngeUnitCapacity")) {
                    Connect connect = new Connect();

                    String sql = "UPDATE Unit SET capacity = "
                            + jsonReq.get("capacity")
                            + " WHERE unitID = "
                            + jsonReq.get("unitID")
                            + ";";

                    connect.runQuery(sql);
                }
                else if (req.equals("set scoreEditable false")) {
                    Connect connect = new Connect();

                    String sql = "UPDATE Grade SET isEditable = false WHERE unitID = "
                            + jsonReq.get("unitID")
                            + ";";

                    connect.runQuery(sql);
                }
                else if (req.equals("set all scoreEditable false")) {
                    Connect connect = new Connect();

                    String sql = "UPDATE Grade SET isEditable = false;";

                    connect.runQuery(sql);
                }
                else if (req.equals("objection")) {
                    Connect connect = new Connect();
                    String sql = "INSERT INTO Objection(studentID, gradeID, type, isRead)"
                            + " VALUES ("+ connect.getStudentIDbyUsername(username)
                            +", "+ jsonReq.get("gradeID")
                            +", '"+ jsonReq.get("type")
                            +"', false);";

                    connect.runQuery(sql);
                }
                else if (req.equals("get objection")) {
                    Connect connect = new Connect();;
                    JSONArray jsonArray= connect.getObjections(connect.getProfessorID(username));
                    outputStream.writeObject(jsonArray);
                    outputStream.flush();
                }
                else if (req.equals("read objection")) {
                    Connect connect = new Connect();
                    String sql = "UPDATE Objection SET isRead = true WHERE objectID = "
                            + jsonReq.get("objectionID")
                            + ";";

                    connect.runQuery(sql);
                }
                else if (req.equals("is head faculty")) {
                    Connect connect = new Connect();
                    JSONObject data = new JSONObject();
                    data.put("isHead", connect.isHeadOfFaculty((int) jsonReq.get("professorID")));
                    outputStream.writeObject(data);
                    outputStream.flush();
                }
                else if (req.equals("getDataFromGradeID")) {
                    Connect connect = new Connect();
                    JSONObject data = connect.getDataFromGradeID((int) jsonReq.get("gradeID"));
                    outputStream.writeObject(data);
                    outputStream.flush();
                }
                else if (req.equals("getReportCardGrades")) {
                    Connect connect = new Connect();
                    JSONArray data = connect.getReportCardGrades(username);
                    outputStream.writeObject(data);
                    outputStream.flush();
                }
                else if (req.equals("getMyChat")) {
                    Connect connect = new Connect();
                    JSONArray data;

                    if (role.equals("Student")) {
                        data = connect.getMyChatStudent(username);
                    } else {
                        data = connect.getMyChatProfessor(username);
                    }
                    outputStream.writeObject(data);
                    outputStream.flush();
                }
                else if (req.equals("sendMessage")) {
                    Connect connect = new Connect();
                    int studentID, professorID;

                    if (role.equals("Student")) {
                        studentID = connect.getStudentIDfromUsername(username);
                        professorID = (int) jsonReq.get("professorID");
                    } else {
                        professorID = connect.getProfessorID(username);
                        studentID = (int) jsonReq.get("studentID");
                    }

                    String sql = "INSERT INTO Message (studentID, professorID, text)"
                            + " VALUES ("+ studentID + ", " + professorID + ", '" + jsonReq.get("text") + "');";

                    connect.runQuery(sql);
                }
                else {
                    System.out.println("not ready");
                }

            } catch (IOException | ClassNotFoundException e) {
                //e.printStackTrace();
                // if disconnect break the loop
                System.out.println("disconnect");
                break;
            }
        }

    }
}