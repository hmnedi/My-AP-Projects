import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class CardLaout extends JFrame implements ActionListener {
    public Send client;
    CardLayout cardLayout;
    JButton btnLogin, btnAddProfessor, btnAddStudent, btnAddFaculty, btnAddMajor;
    JTextField txtUsername, txtPassword, adminPrfFirstname, adminPrfLastname, adminPrfUsername, adminPrfPassword,
            adminStuFirstname, adminStuLastname, adminStuUsername, adminStuPassword, adminStuMajor, adminFacName,
            adminFacPrfFirstName, adminFacPrfLastName;
    JComboBox comboBoxRole;
    Container container;
    JPanel pnlObjections;
    JList listObjections;

    public CardLaout() throws IOException, ClassNotFoundException {

        // client object
        Socket socket = new Socket("localhost", 7070);
        client = new Send(socket);

        // GUI
        container = getContentPane();
        cardLayout = new CardLayout();
        container.setLayout(cardLayout);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("uni management");
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0x5b5870));
//        ImageIcon logo = new ImageIcon("pic/logo.png");
//        this.setIconImage(logo.getImage());

        // setting up the menu page
        comboBoxRole = new JComboBox(new String[]{"Professor", "Student", "Admin"});
        comboBoxRole.setBounds(455, 119, 120, 30);

        btnLogin = new JButton("Login");

        txtUsername = new JTextField("username");
        txtUsername.setBounds(581, 118, 186, 40);
        txtUsername.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // to clear the prefix text
                if (txtUsername.getText().equals("username")){
                    txtUsername.setText("");
                }
            }
        });

        txtPassword = new JTextField("password");
        txtPassword.setBounds(581, 164, 186, 40);
        txtPassword.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // to clear the prefix text
                if (txtPassword.getText().equals("password")){
                    txtPassword.setText("");
                }
            }
        });

        btnLogin.addActionListener(this);
        btnLogin.setBounds(610, 215, 125, 40);
        btnLogin.setFocusable(false);

        // simple trick to set background image, I'm using a label as the panel
        ImageIcon pageMenuBack = new ImageIcon("pic/back.JPG");
        JLabel panelMenu = new JLabel(pageMenuBack);

        panelMenu.setLayout(null);
        panelMenu.add(btnLogin);
        panelMenu.add(txtUsername);
        panelMenu.add(txtPassword);
        panelMenu.add(comboBoxRole);


        // panel professor
        ImageIcon professorMenuBack = new ImageIcon("pic/wp6596813.jpg");
        JLabel panelProfessor = new JLabel(professorMenuBack);

        // objection box
        // data dumping is in the action listener
        pnlObjections = new JPanel();
        pnlObjections.setLayout(new GridLayout(10, 2));
        pnlObjections.setBackground(new Color(0xE89F4955, true));

        JScrollPane scrollPaneObjection = new JScrollPane(pnlObjections, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneObjection.setBounds(30, 30, 260, 350);

        panelProfessor.add(scrollPaneObjection);


        // panel Admin
        ImageIcon adminMenuBack = new ImageIcon("pic/adminbback.jpg");
        JLabel panelAdmin = new JLabel(adminMenuBack);

        // add professor
        adminPrfFirstname = new JTextField("firstname");
        adminPrfFirstname.setBounds(10, 30, 70, 20);
        adminPrfFirstname.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // to clear the prefix text
                if (adminPrfFirstname.getText().equals("firstname")){
                    adminPrfFirstname.setText("");
                }
            }
        });
        adminPrfLastname = new JTextField("lastname");
        adminPrfLastname.setBounds(85, 30, 70, 20);
        adminPrfLastname.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (adminPrfLastname.getText().equals("lastname")) adminPrfLastname.setText("");
            }
        });
        adminPrfUsername = new JTextField("username");
        adminPrfUsername.setBounds(160, 30, 70, 20);
        adminPrfUsername.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // to clear the prefix text
                if (adminPrfUsername.getText().equals("username")){
                    adminPrfUsername.setText("");
                }
            }
        });
        adminPrfPassword = new JTextField("password");
        adminPrfPassword.setBounds(235, 30, 70, 20);
        adminPrfPassword.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // to clear the prefix text
                if (adminPrfPassword.getText().equals("password")){
                    adminPrfPassword.setText("");
                }
            }
        });
        btnAddProfessor = new JButton("add");
        btnAddProfessor.addActionListener(this);
        btnAddProfessor.setBounds(310, 30, 70, 20);
        btnAddProfessor.setFocusable(false);


        // add student
        adminStuFirstname = new JTextField("firstname");
        adminStuFirstname.setBounds(10, 80, 70, 20);
        adminStuFirstname.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // to clear the prefix text
                if (adminStuFirstname.getText().equals("firstname")){
                    adminStuFirstname.setText("");
                }
            }
        });
        adminStuLastname = new JTextField("lastname");
        adminStuLastname.setBounds(85, 80, 70, 20);
        adminStuLastname.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (adminStuLastname.getText().equals("lastname")) adminStuLastname.setText("");
            }
        });
        adminStuUsername = new JTextField("username");
        adminStuUsername.setBounds(160, 80, 70, 20);
        adminStuUsername.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // to clear the prefix text
                if (adminStuUsername.getText().equals("username")){
                    adminStuUsername.setText("");
                }
            }
        });
        adminStuPassword = new JTextField("password");
        adminStuPassword.setBounds(235, 80, 70, 20);
        adminStuPassword.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // to clear the prefix text
                if (adminStuPassword.getText().equals("password")){
                    adminStuPassword.setText("");
                }
            }
        });
        adminStuMajor = new JTextField("majorName"); //todo: make it combobox
        adminStuMajor.setBounds(310, 80, 70, 20);
        adminStuMajor.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // to clear the prefix text
                if (adminStuMajor.getText().equals("major name")){
                    adminStuMajor.setText("");
                }
            }
        });
        btnAddStudent = new JButton("add");
        btnAddStudent.addActionListener(this);
        btnAddStudent.setBounds(385, 80, 70, 20);
        btnAddStudent.setFocusable(false);

        // add faculty
        adminFacName = new JTextField("faculty name");
        adminFacName.setBounds(10, 130, 100, 20);
        adminFacName.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // to clear the prefix text
                if (adminFacName.getText().equals("faculty name")){
                    adminFacName.setText("");
                }
            }
        });
        adminFacPrfFirstName = new JTextField("professor first name");
        adminFacPrfFirstName.setBounds(115, 130, 130, 20);
        adminFacPrfFirstName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (adminFacPrfFirstName.getText().equals("professor first name")) adminFacPrfFirstName.setText("");
            }
        });
        adminFacPrfLastName = new JTextField("professor last name");
        adminFacPrfLastName.setBounds(250, 130, 130, 20);
        adminFacPrfLastName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (adminFacPrfLastName.getText().equals("professor last name")) adminFacPrfLastName.setText("");
            }
        });
        btnAddFaculty = new JButton("add");
        btnAddFaculty.addActionListener(this);
        btnAddFaculty.setBounds(385, 130, 70, 20);
        btnAddFaculty.setFocusable(false);



        panelAdmin.add(adminPrfFirstname);
        panelAdmin.add(adminPrfLastname);
        panelAdmin.add(adminPrfUsername);
        panelAdmin.add(adminPrfPassword);
        panelAdmin.add(adminStuFirstname);
        panelAdmin.add(adminStuLastname);
        panelAdmin.add(adminStuUsername);
        panelAdmin.add(adminStuPassword);
        panelAdmin.add(adminStuMajor);
        panelAdmin.add(adminFacName);
        panelAdmin.add(adminFacPrfFirstName);
        panelAdmin.add(adminFacPrfLastName);
        panelAdmin.add(btnAddStudent);
        panelAdmin.add(btnAddProfessor);
        panelAdmin.add(btnAddFaculty);



        container.add("Panel Menu", panelMenu);
        container.add("Panel Professor", panelProfessor);
        container.add("Panel Admin", panelAdmin);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            JSONObject jsonLogin = new JSONObject();
            jsonLogin.put("request", "login");
            jsonLogin.put("username", txtUsername.getText());
            jsonLogin.put("password", txtPassword.getText());
            jsonLogin.put("role", comboBoxRole.getSelectedItem());
            try {
                JSONObject jsonRes = client.sendRequestgetObject(jsonLogin);
                if ((int) jsonRes.get("status") == 200) {
                    txtPassword.setText("");
                    txtUsername.setText("");

                    if (Objects.equals(comboBoxRole.getSelectedItem(), "Professor")) {
                        cardLayout.next(container);
                        // dump objections for this professor
                        JSONArray jsonObjection = selectObjbections();
                        for (int i=0; i<jsonObjection.toArray().length; i++){
                            JSONObject jsonObjectionObject = (JSONObject) jsonObjection.get(i);
                            if (i==0) {
                                pnlObjections.add(new JLabel("ObjectionID | type | gradeID(firstname lastname student + score"));
                                System.out.println(jsonObjectionObject.toString());
                            }
                            for (int j=0; j<2; j++){
                                if (j == 0) {
                                    pnlObjections.add(new JLabel((String) jsonObjectionObject.get("objectionID") + jsonObjectionObject.get("type")));
                                }
                                else pnlObjections.add(new JButton("register")); //todo checkbox
                            }
                        }
                    }

                    if (Objects.equals(comboBoxRole.getSelectedItem(), "Admin")) {
                        cardLayout.next(container);
                        cardLayout.next(container);

                    }


                }
                else txtPassword.setText("");
            } catch (ClassNotFoundException | IOException ex) {
                ex.printStackTrace();
            }

        }
        if (e.getSource() == btnAddProfessor) {
            try {
                addProfessor(adminPrfFirstname.getText(), adminPrfLastname.getText(), adminPrfUsername.getText(), adminPrfPassword.getText());
                adminPrfFirstname.setText("firstname");
                adminPrfLastname.setText("lastname");
                adminPrfUsername.setText("username");
                adminPrfPassword.setText("password");

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == btnAddStudent) {
            try {
                addStudent(adminStuFirstname.getText(), adminStuLastname.getText(),
                        adminStuUsername.getText(), adminStuPassword.getText(), getMajorID(adminStuMajor.getText()));
                adminStuFirstname.setText("firstname");
                adminStuLastname.setText("lastname");
                adminStuUsername.setText("username");
                adminStuPassword.setText("password");
                adminStuMajor.setText("major name");

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == btnAddFaculty) {
            try {
                addFaculty(adminFacName.getText(),
                        getProfessorID(adminFacPrfFirstName.getText(), adminFacPrfLastName.getText()));
                adminFacName.setText("faculty name");
                adminFacPrfFirstName.setText("professor first name");
                adminFacPrfLastName.setText("professor last name");

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }


    private void answerObjections(int objectionID) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "read objection");
        jsonData.put("objectionID", objectionID);

        client.sendRequest(jsonData);
    }

    private JSONArray selectObjbections() throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "get objection");
        return client.sendRequestgetArray(jsonData);
    }
    private void objection(int gradeID, String type) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "objection");
        jsonData.put("gradeID", gradeID);
        jsonData.put("type", type);
        client.sendRequest(jsonData);
    }

    private void cahngeUnitCapacity(int unitID, int capacity) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "cahngeUnitCapacity");
        jsonData.put("unitID", unitID);
        jsonData.put("capacity", capacity);
        client.sendRequest(jsonData);
    }

    private void setTakingUnit(boolean b) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "set TakingUnit");
        jsonData.put("state", b);
        client.sendRequest(jsonData);
    }

    private boolean isTakingUnitAllowed() throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "isTakingUnitAllowed");
        return (boolean) client.sendRequestgetObject(jsonData).get("takingUnitStats");
    }


    private void addMajor (String name, int facultyID) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "insert major");
        jsonData.put("majorName", name);
        jsonData.put("facultyID", facultyID);

        client.sendRequest(jsonData);
    }
    private void addFaculty(String name, int profHeadId) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "insert faculty");
        jsonData.put("facultyName", name);
        jsonData.put("professorID", profHeadId);

        client.sendRequest(jsonData);
    }

    private void addProfessor(String firstname, String lastname, String username, String password) throws IOException, ClassNotFoundException {
        JSONObject jsonProf = new JSONObject();
        jsonProf.put("request", "insert professor");
        jsonProf.put("firstname", firstname);
        jsonProf.put("lastname", lastname);
        jsonProf.put("username", username);
        jsonProf.put("password", password);

        client.sendRequest(jsonProf);
    }

    private void addStudent(String firstname, String lastname, String username, String password, int majorID) throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "insert student");
        jsonData.put("firstname", firstname);
        jsonData.put("lastname", lastname);
        jsonData.put("username", username);
        jsonData.put("password", password);
        jsonData.put("majorID", majorID);

        client.sendRequest(jsonData);
    }

    private void selectProfessor() throws IOException, ClassNotFoundException {
        JSONObject jsonProf = new JSONObject();
        jsonProf.put("request", "select all professor");


        System.out.println(client.sendRequestgetArray(jsonProf).toString());
    }

    private int getMajorID (String name) throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "get MajorID");
        jsonData.put("majorName", name);

        return (int) client.sendRequestgetObject(jsonData).get("majorID");

    }

    private int getProfessorID (String firstname, String lastname) throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "get professorID");
        jsonData.put("firstname", firstname);
        jsonData.put("lastname", lastname);

        return (int) client.sendRequestgetObject(jsonData).get("professorID");

    }

    private int getStudentID (String firstname, String lastname) throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "get studentID");
        jsonData.put("firstname", firstname);
        jsonData.put("lastname", lastname);

        return (int) client.sendRequestgetObject(jsonData).get("studentID");

    }

    private int getFacultyID (String name) throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "get facultyID");
        jsonData.put("facultyName", name);

        return (int) client.sendRequestgetObject(jsonData).get("facultyID");
    }

    private void setHeadFaculty(int professorID, int facultyID) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "set headFaculty");
        jsonData.put("professorID", professorID);
        jsonData.put("facultyID", facultyID);

        // we can send id if we show all the professors to the client

        client.sendRequest(jsonData);
    }

    private void getProfessorUnit() throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "get professorUnits");

        JSONArray jsonArray = client.sendRequestgetArray(jsonData);
        System.out.println(jsonArray.toString());
    }

    private void getStudentsOfaUnit(int unitID) throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "getStudentsOfaUnit");
        jsonData.put("unitID", unitID);
        System.out.println(client.sendRequestgetArray(jsonData));
    }

    private void getUnitsOfStudent() throws IOException, ClassNotFoundException {
        // units that a student can choose (from their faculty)
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "getUnitsOfStudent");
        System.out.println(client.sendRequestgetArray(jsonData));

    }

    private void getUnitsOfMajor() throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "getUnitsOfFaculty");
        System.out.println(client.sendRequestgetArray(jsonData));

    }

    private void takeUnits(int unitID) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "takeUnit");
        jsonData.put("unitID", unitID);

        client.sendRequest(jsonData);

    }

    private void addScore (double score) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "set score");
        jsonData.put("score", score);

        client.sendRequest(jsonData);
    }
    private void registerScore (int unitID) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "set scoreEditable false");
        jsonData.put("unitID", unitID);

        client.sendRequest(jsonData);
    }
}
