import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class CardLaout extends JFrame implements ActionListener {
    public Send client;
    CardLayout cardLayout;
    JScrollPane profUnitListScrollPanel, profUnitsscrollPaneObjection;
    JButton btnLogin, btnAddProfessor, btnAddStudent, btnAddFaculty, btnAddMajor, btnAddHeadFaculty, btnBack, btnBack1
            , btnIsHeadFaculty, btnGoBackToProf, btnOCTakingUnit, btnMakeScoreFixed, btnHeadFacultyChangeCapacity,
            btnProfReadObjections, btnProfChoose, btnProfBackPage, btnTakeUnit, btnStudentObject, btnBack2,
            btnStudentSendMsg, btnProfessorSendMsg, btnPrfRefresh, btnStuRefresh;
    JTextField txtUsername, txtPassword, adminPrfFirstname, adminPrfLastname, adminPrfUsername, adminPrfPassword,
            adminStuFirstname, adminStuLastname, adminStuUsername, adminStuPassword, adminStuMajor, adminFacName,
            adminFacPrfFirstName, adminFacPrfLastName, adminMajorName, adminMajorFacultyID, adminHeadFirst,
            adminHeadLast, adminHeadFac, headFacNewCaps, txtSetScore, txtStuObjection, txtStudentMessage, txtPrfName,
            txtProfessorMessage, txtStuName;
    JComboBox comboBoxRole;
    Container container;
    JList jlistheadFacultyUnits, jlistObjection, jlistProfessorUnits, jlistStudentOfUnit, jlistPickUnits,
            jlistReportCard, jlistStudentChat, jlistProfessorChat;
    DefaultListModel  adminProfeserList, headFacultyUnits, objectionList, professorUnitsList, studendOfUnitList,
            pickUnitsList, reportCardList, studentChatList, professorChatList;

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
        objectionList = new DefaultListModel();
        jlistObjection = new JList(objectionList);
        JScrollPane scrollPaneObjection = new JScrollPane(jlistObjection,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneObjection.setBounds(30, 70, 260, 290);

        // professor units
        professorUnitsList = new DefaultListModel();
        jlistProfessorUnits = new JList(professorUnitsList);
        profUnitsscrollPaneObjection = new JScrollPane(jlistProfessorUnits,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        profUnitsscrollPaneObjection.setBounds(550, 60, 240, 240);

        // list of students of a unit
        studendOfUnitList = new DefaultListModel();
        jlistStudentOfUnit = new JList(studendOfUnitList);
        profUnitListScrollPanel = new JScrollPane(jlistStudentOfUnit,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        profUnitListScrollPanel.setBounds(550, 60, 240, 240);
        profUnitListScrollPanel.setVisible(false);

        btnProfChoose = new JButton("choose");
        btnProfChoose.setFocusable(false);
        btnProfChoose.addActionListener(this);
        btnProfChoose.setBounds(600, 30, 90, 20);

        btnProfBackPage = new JButton("<--");
        btnProfBackPage.setFocusable(false);
        btnProfBackPage.addActionListener(this);
        btnProfBackPage.setBounds(530, 30, 60, 20);

        txtSetScore = new JTextField("12.75");
        txtSetScore.setBounds(700, 30, 90, 20);
        txtSetScore.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // to clear the prefix text
                if (txtSetScore.getText().equals("12.75")){
                    txtSetScore.setText("");
                }
            }
        });
        txtSetScore.setVisible(false);

        btnIsHeadFaculty = new JButton("go to headFaculty page");
        btnIsHeadFaculty.setFocusable(false);
        btnIsHeadFaculty.addActionListener(this);
        btnIsHeadFaculty.setBounds(600, 350, 170, 40);
        btnIsHeadFaculty.setVisible(true);

        btnProfReadObjections = new JButton("read");
        btnProfReadObjections.setFocusable(false);
        btnProfReadObjections.addActionListener(this);
        btnProfReadObjections.setBounds(200, 25, 90, 40);

        btnBack1 = new JButton("back");
        btnBack1.addActionListener(this);
        btnBack1.setBounds(10, 370, 70, 20);
        btnBack1.setFocusable(false);

        // professor chatroom
        professorChatList = new DefaultListModel();
        jlistProfessorChat = new JList(professorChatList);
        JScrollPane chatScrollProfessorPane = new JScrollPane(jlistProfessorChat,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        chatScrollProfessorPane.setBounds(300, 70, 220, 285);

        btnProfessorSendMsg = new JButton("send");
        btnProfessorSendMsg.addActionListener(this);
        btnProfessorSendMsg.setBounds(430, 32, 70, 20);
        btnProfessorSendMsg.setFocusable(false);

        btnPrfRefresh = new JButton("refresh");
        btnPrfRefresh.addActionListener(this);
        btnPrfRefresh.setBounds(430, 10, 90, 20);
        btnPrfRefresh.setFocusable(false);

        txtProfessorMessage = new JTextField("your message...");
        txtProfessorMessage.setBounds(300, 15, 120, 20);
        txtProfessorMessage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (txtProfessorMessage.getText().equals("your message...")) txtProfessorMessage.setText("");
            }
        });

        txtStuName = new JTextField("firstname lastname");
        txtStuName.setBounds(300, 45, 120, 20);
        txtStuName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (txtStuName.getText().equals("firstname lastname")) txtStuName.setText("");
            }
        });


        panelProfessor.add(scrollPaneObjection);
        panelProfessor.add(chatScrollProfessorPane);
        panelProfessor.add(profUnitsscrollPaneObjection);
        panelProfessor.add(profUnitListScrollPanel);
        panelProfessor.add(btnIsHeadFaculty);
        panelProfessor.add(btnBack1);
        panelProfessor.add(btnProfReadObjections);
        panelProfessor.add(btnProfChoose);
        panelProfessor.add(btnProfBackPage);
        panelProfessor.add(txtSetScore);
        panelProfessor.add(txtStuName);
        panelProfessor.add(txtProfessorMessage);
        panelProfessor.add(btnProfessorSendMsg);
        panelProfessor.add(btnPrfRefresh);


        // panel head of faculty
        ImageIcon headMenuBack = new ImageIcon("pic/headFaculty.jpg");
        JLabel panelHeadFaculty = new JLabel(headMenuBack);

        btnGoBackToProf = new JButton("back");
        btnGoBackToProf.addActionListener(this);
        btnGoBackToProf.setBounds(10, 370, 70, 20);
        btnGoBackToProf.setFocusable(false);

        btnOCTakingUnit = new JButton("");
        btnOCTakingUnit.addActionListener(this);
        btnOCTakingUnit.setBounds(560, 30, 180, 20);
        btnOCTakingUnit.setFocusable(false);

        btnMakeScoreFixed = new JButton("make all scores fixed");
        btnMakeScoreFixed.addActionListener(this);
        btnMakeScoreFixed.setBounds(560, 60, 180, 20);
        btnMakeScoreFixed.setFocusable(false);

        // units list
        headFacultyUnits = new DefaultListModel();
        jlistheadFacultyUnits = new JList(headFacultyUnits);
        JScrollPane headfAcunitScrollPane = new JScrollPane(jlistheadFacultyUnits,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        headfAcunitScrollPane.setBounds(550, 100, 200, 250);

        btnHeadFacultyChangeCapacity = new JButton("change capacity");
        btnHeadFacultyChangeCapacity.addActionListener(this);
        btnHeadFacultyChangeCapacity.setBounds(550, 380, 150, 20);
        btnHeadFacultyChangeCapacity.setFocusable(false);

        headFacNewCaps = new JTextField("new capacity");
        headFacNewCaps.setBounds(550, 355, 150, 20);
        headFacNewCaps.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // to clear the prefix text
                if (headFacNewCaps.getText().equals("new capacity")){
                    headFacNewCaps.setText("");
                }
            }
        });

        panelHeadFaculty.add(btnGoBackToProf);
        panelHeadFaculty.add(btnOCTakingUnit);
        panelHeadFaculty.add(btnMakeScoreFixed);
        panelHeadFaculty.add(headfAcunitScrollPane);
        panelHeadFaculty.add(btnHeadFacultyChangeCapacity);
        panelHeadFaculty.add(headFacNewCaps);



        // panel Admin
        ImageIcon adminMenuBack = new ImageIcon("pic/adminbback.jpg");
        JLabel panelAdmin = new JLabel(adminMenuBack);

        // add professor
        adminPrfFirstname = new JTextField("firstname");
        adminPrfFirstname.setBounds(10, 37, 70, 20);
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
        adminPrfLastname.setBounds(85, 37, 70, 20);
        adminPrfLastname.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (adminPrfLastname.getText().equals("lastname")) adminPrfLastname.setText("");
            }
        });
        adminPrfUsername = new JTextField("username");
        adminPrfUsername.setBounds(160, 37, 70, 20);
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
        adminPrfPassword.setBounds(235, 37, 70, 20);
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
        btnAddProfessor.setBounds(310, 37, 70, 20);
        btnAddProfessor.setFocusable(false);


        // add student
        adminStuFirstname = new JTextField("firstname");
        adminStuFirstname.setBounds(10, 90, 70, 20);
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
        adminStuLastname.setBounds(85, 90, 70, 20);
        adminStuLastname.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (adminStuLastname.getText().equals("lastname")) adminStuLastname.setText("");
            }
        });
        adminStuUsername = new JTextField("username");
        adminStuUsername.setBounds(160, 90, 70, 20);
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
        adminStuPassword.setBounds(235, 90, 70, 20);
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
        adminStuMajor.setBounds(310, 90, 70, 20);
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
        btnAddStudent.setBounds(385, 90, 70, 20);
        btnAddStudent.setFocusable(false);

        // add faculty
        adminFacName = new JTextField("faculty name");
        adminFacName.setBounds(10, 145, 100, 20);
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
        adminFacPrfFirstName.setBounds(115, 145, 130, 20);
        adminFacPrfFirstName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (adminFacPrfFirstName.getText().equals("professor first name")) adminFacPrfFirstName.setText("");
            }
        });
        adminFacPrfLastName = new JTextField("professor last name");
        adminFacPrfLastName.setBounds(250, 145, 130, 20);
        adminFacPrfLastName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (adminFacPrfLastName.getText().equals("professor last name")) adminFacPrfLastName.setText("");
            }
        });
        btnAddFaculty = new JButton("add");
        btnAddFaculty.addActionListener(this);
        btnAddFaculty.setBounds(385, 145, 70, 20);
        btnAddFaculty.setFocusable(false);

        // add major
        adminMajorName = new JTextField("major name");
        adminMajorName.setBounds(10, 202, 100, 20);
        adminMajorName.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // to clear the prefix text
                if (adminMajorName.getText().equals("major name")){
                    adminMajorName.setText("");
                }
            }
        });
        adminMajorFacultyID = new JTextField("faculty name");
        adminMajorFacultyID.setBounds(120, 202, 130, 20);
        adminMajorFacultyID.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (adminMajorFacultyID.getText().equals("faculty name")) adminMajorFacultyID.setText("");
            }
        });
        btnAddMajor = new JButton("add");
        btnAddMajor.addActionListener(this);
        btnAddMajor.setBounds(270, 202, 70, 20);
        btnAddMajor.setFocusable(false);


        // add headOfFaculty
        adminHeadFirst = new JTextField("first name");
        adminHeadFirst.setBounds(10, 260, 90, 20);
        adminHeadFirst.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // to clear the prefix text
                if (adminHeadFirst.getText().equals("first name")){
                    adminHeadFirst.setText("");
                }
            }
        });
        adminHeadLast = new JTextField("last name");
        adminHeadLast.setBounds(110, 260, 90, 20);
        adminHeadLast.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (adminHeadLast.getText().equals("last name")) adminHeadLast.setText("");
            }
        });
        adminHeadFac = new JTextField("faculty name");
        adminHeadFac.setBounds(210, 260, 90, 20);
        adminHeadFac.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (adminHeadFac.getText().equals("faculty name")) adminHeadFac.setText("");
            }
        });
        btnAddHeadFaculty = new JButton("set");
        btnAddHeadFaculty.addActionListener(this);
        btnAddHeadFaculty.setBounds(310, 260, 70, 20);
        btnAddHeadFaculty.setFocusable(false);

        btnBack = new JButton("BACK");
        btnBack.addActionListener(this);
        btnBack.setBounds(10, 370, 70, 20);
        btnBack.setFocusable(false);

        // professors list
        adminProfeserList = new DefaultListModel();
        JList jlistAdminProfessorList = new JList(adminProfeserList);
        JScrollPane prfListscrollPane = new JScrollPane(jlistAdminProfessorList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        prfListscrollPane.setBounds(550, 30, 200, 300);



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
        panelAdmin.add(btnAddMajor);
        panelAdmin.add(adminMajorName);
        panelAdmin.add(adminMajorFacultyID);
        panelAdmin.add(prfListscrollPane);
        panelAdmin.add(adminHeadFirst);
        panelAdmin.add(adminHeadLast);
        panelAdmin.add(adminHeadFac);
        panelAdmin.add(btnAddHeadFaculty);
        panelAdmin.add(btnBack);


        // panel Student
        ImageIcon studentMenuBack = new ImageIcon("pic/studentbichareh.jpg");
        JLabel panelStudent = new JLabel(studentMenuBack);

        // list of units for student (hahha i'm losing my mind)
        pickUnitsList = new DefaultListModel();
        jlistPickUnits = new JList(pickUnitsList);
        JScrollPane pickingUnitsScroller = new JScrollPane(jlistPickUnits,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pickingUnitsScroller.setBounds(20, 60, 200, 300);

        btnTakeUnit = new JButton("take");
        btnTakeUnit.addActionListener(this);
        btnTakeUnit.setBounds(100, 32, 70, 20);
        btnTakeUnit.setFocusable(false);

        btnStudentObject = new JButton("object");
        btnStudentObject.addActionListener(this);
        btnStudentObject.setBounds(350, 32, 70, 20);
        btnStudentObject.setFocusable(false);

        btnBack2 = new JButton("back");
        btnBack2.addActionListener(this);
        btnBack2.setBounds(10, 370, 70, 20);
        btnBack2.setFocusable(false);

        txtStuObjection = new JTextField("type of objection");
        txtStuObjection.setBounds(250, 32, 90, 20);
        txtStuObjection.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (txtStuObjection.getText().equals("type of objection")) txtStuObjection.setText("");
            }
        });

        // student grades
        reportCardList = new DefaultListModel();
        jlistReportCard = new JList(reportCardList);
        JScrollPane gradesScrollPane = new JScrollPane(jlistReportCard,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        gradesScrollPane.setBounds(250, 60, 150, 300);

        // student chatroom
        studentChatList = new DefaultListModel();
        jlistStudentChat = new JList(studentChatList);
        JScrollPane chatScrollStudentPane = new JScrollPane(jlistStudentChat,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        chatScrollStudentPane.setBounds(450, 60, 350, 300);

        btnStudentSendMsg = new JButton("send");
        btnStudentSendMsg.addActionListener(this);
        btnStudentSendMsg.setBounds(730, 32, 70, 20);
        btnStudentSendMsg.setFocusable(false);

        btnStuRefresh = new JButton("refresh");
        btnStuRefresh.addActionListener(this);
        btnStuRefresh.setBounds(730, 10, 80, 20);
        btnStuRefresh.setFocusable(false);

        txtStudentMessage = new JTextField("your message...");
        txtStudentMessage.setBounds(450, 32, 140, 20);
        txtStudentMessage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (txtStudentMessage.getText().equals("your message...")) txtStudentMessage.setText("");
            }
        });

        txtPrfName = new JTextField("firstname lastname");
        txtPrfName.setBounds(600, 32, 120, 20);
        txtPrfName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (txtPrfName.getText().equals("firstname lastname")) txtPrfName.setText("");
            }
        });

        panelStudent.add(pickingUnitsScroller);
        panelStudent.add(gradesScrollPane);
        panelStudent.add(chatScrollStudentPane);
        panelStudent.add(btnTakeUnit);
        panelStudent.add(btnStudentObject);
        panelStudent.add(txtStuObjection);
        panelStudent.add(btnBack2);
        panelStudent.add(txtStudentMessage);
        panelStudent.add(btnStudentSendMsg);
        panelStudent.add(txtPrfName);
        panelStudent.add(btnStuRefresh);



        container.add("Panel Menu", panelMenu);
        container.add("Panel Professor", panelProfessor);
        container.add("Panel HeadFaculty", panelHeadFaculty);
        container.add("Panel Admin", panelAdmin);
        container.add("Panel Student", panelStudent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack || e.getSource() == btnBack1 || e.getSource() == btnBack2) {
            cardLayout.first(container);
            // why do I have the feeling that socket should get close here? or else it's not secure
        }
        if (e.getSource() == btnGoBackToProf){
            cardLayout.previous(container);
        }
        if (e.getSource() == btnLogin) {
            JSONObject jsonLogin = new JSONObject();
            jsonLogin.put("request", "login");
            jsonLogin.put("username", txtUsername.getText());
            jsonLogin.put("password", txtPassword.getText());
            jsonLogin.put("role", comboBoxRole.getSelectedItem());
            try {
                JSONObject jsonRes = client.sendRequestgetObject(jsonLogin);
                if ((int) jsonRes.get("status") == 200) {

                    // load page professor
                    if (Objects.equals(comboBoxRole.getSelectedItem(), "Professor")) {
                        cardLayout.next(container);

                        if (isHeadFaculty(getProfessorID(txtUsername.getText()))){
                            btnIsHeadFaculty.setVisible(true);
                        } else {
                            btnIsHeadFaculty.setVisible(false);
                        }

                        // dump objections for this professor
                        objectionList.removeAllElements();
                        JSONArray jsonObjection = selectObjbections();
                        for (int i=0; i<jsonObjection.toArray().length; i++){
                            JSONObject jsonObjectionObject = (JSONObject) jsonObjection.get(i);
                            JSONObject dataFromGradeID = getDataFromGradeID((int) jsonObjectionObject.get("gradeID"));
                            objectionList.addElement(jsonObjectionObject.get("objectionID") + " "
                                    + dataFromGradeID.get("studentName") + " " + dataFromGradeID.get("score")
                                    + ": " + jsonObjectionObject.get("type"));
                        }

                        // load professor units
                        professorUnitsList.removeAllElements();
                        JSONArray jsonUnits = getProfessorUnit();
                        for (int i=0; i<jsonUnits.toArray().length; i++){
                            JSONObject jsonObject = (JSONObject) jsonUnits.get(i);
                            professorUnitsList.addElement(jsonObject.get("unitID") + " " + jsonObject.get("unitName"));
                        }

                        // load chats
                        professorChatList.removeAllElements();
                        JSONArray jsonArray2 = getMyChat();
                        for (int i=0; i<jsonArray2.toArray().length; i++){
                            JSONObject jsonObject = (JSONObject) jsonArray2.get(i);
                            professorChatList.addElement("contact: " + jsonObject.get("Name") + " - text: "
                                    + jsonObject.get("message"));
                        }

                    }

                    // load page admin
                    if (Objects.equals(comboBoxRole.getSelectedItem(), "Admin")) {
                        cardLayout.next(container);
                        cardLayout.next(container);
                        cardLayout.next(container);

                        // show professor list
                        adminProfeserList.removeAllElements();
                        JSONArray jsonArray = selectProfessor();
                        for (int i=0; i<jsonArray.toArray().length; i++){
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            adminProfeserList.addElement(jsonObject.get("id") + " "
                                    + jsonObject.get("firstname") + " " + jsonObject.get("lastname")
                                    + " isHead: " + isHeadFaculty((int) jsonObject.get("id")));
                        }
                    }

                    // load page student
                    if (Objects.equals(comboBoxRole.getSelectedItem(), "Student")) {
                        cardLayout.last(container);

                        // show untaken units of the student
                        pickUnitsList.removeAllElements();
                        JSONArray jsonArray = getUnitsOfStudent();
                        for (int i=0; i<jsonArray.toArray().length; i++){
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            pickUnitsList.addElement(jsonObject.get("unitID") + " " + jsonObject.get("unitName"));
                        }

                        // show grades
                        reportCardList.removeAllElements();
                        JSONArray jsonArray1 = getReportCardGrades();
                        for (int i=0; i<jsonArray1.toArray().length; i++){
                            JSONObject jsonObject = (JSONObject) jsonArray1.get(i);
                            reportCardList.addElement(jsonObject.get("gradeID") + " " + jsonObject.get("unitName")
                                    + " " + jsonObject.get("score") + " " + jsonObject.get("professorName"));
                        }

                        // todo: show each message is for who
                        // load chats
                        studentChatList.removeAllElements();
                        JSONArray jsonArray2 = getMyChat();
                        for (int i=0; i<jsonArray2.toArray().length; i++){
                            JSONObject jsonObject = (JSONObject) jsonArray2.get(i);
                            studentChatList.addElement("contact: " + jsonObject.get("Name") + " - text: "
                                    + jsonObject.get("message"));
                        }

                        // check if taking unit is allowed
                        if (!isTakingUnitAllowed()) {
                            btnTakeUnit.setEnabled(false);
                        }


                    }

                    txtPassword.setText("password");
                    txtUsername.setText("username");
                }
                else txtPassword.setText("");
            } catch (ClassNotFoundException | IOException ex) {
                ex.printStackTrace();
            }

        }
        if (e.getSource() == btnAddProfessor) {
            try {
                addProfessor(adminPrfFirstname.getText(), adminPrfLastname.getText(), adminPrfUsername.getText(), adminPrfPassword.getText());
                adminProfeserList.addElement(getProfessorID(adminPrfFirstname.getText(), adminPrfLastname.getText())
                        + " "+ adminPrfFirstname.getText() + " " + adminPrfLastname.getText() + " ");
                adminPrfFirstname.setText("firstname");
                adminPrfLastname.setText("lastname");
                adminPrfUsername.setText("username");
                adminPrfPassword.setText("password");

                // show professor list
                adminProfeserList.removeAllElements();
                JSONArray jsonArray = selectProfessor();
                for (int i=0; i<jsonArray.toArray().length; i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    adminProfeserList.addElement(jsonObject.get("id") + " "
                            + jsonObject.get("firstname") + " " + jsonObject.get("lastname")
                            + " isHead: " + isHeadFaculty((int) jsonObject.get("id")));
                }

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
        if (e.getSource() == btnAddMajor) {
            try {
                addMajor(adminMajorName.getText(), getFacultyID(adminMajorFacultyID.getText()));
                adminMajorName.setText("major name");
                adminMajorFacultyID.setText("faculty name");

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == btnAddHeadFaculty) {
            try {
                setHeadFaculty(getProfessorID(adminHeadFirst.getText(), adminHeadLast.getText()),
                        getFacultyID(adminHeadFac.getText()));
                adminHeadFirst.setText("first name");
                adminHeadLast.setText("last name");
                adminHeadFac.setText("faculty name");

                // show professor list
                adminProfeserList.removeAllElements();
                JSONArray jsonArray = selectProfessor();
                for (int i=0; i<jsonArray.toArray().length; i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    adminProfeserList.addElement(jsonObject.get("id") + " "
                            + jsonObject.get("firstname") + " " + jsonObject.get("lastname")
                            + " isHead: " + isHeadFaculty((int) jsonObject.get("id")));
                }

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == btnIsHeadFaculty) {
            cardLayout.next(container);
            try {
                if (isTakingUnitAllowed()) btnOCTakingUnit.setText("close taking units");
                else btnOCTakingUnit.setText("open taking units");

                // load units of faculty
                headFacultyUnits.removeAllElements();
                JSONArray jsonArray = getUnitsOfFaculty();
                for (int i=0; i<jsonArray.toArray().length; i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    headFacultyUnits.addElement(jsonObject.get("unitID") + " " + jsonObject.get("unitName") + " "
                            + jsonObject.get("capacity"));
                }


            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == btnOCTakingUnit) {
            if (btnOCTakingUnit.getText().equals("close taking units")) {
                try {
                    setTakingUnit(false);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                btnOCTakingUnit.setText("open taking units");
            }
            else {
                try {
                    setTakingUnit(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                btnOCTakingUnit.setText("close taking units");
            }
        }
        if (e.getSource() == btnMakeScoreFixed) {
            try {
                registerAllScore();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == btnHeadFacultyChangeCapacity) {
            try {
                String text = (String) jlistheadFacultyUnits.getSelectedValue();
                cahngeUnitCapacity(Integer.parseInt(text.split(" ")[0]),
                        Integer.parseInt(headFacNewCaps.getText()));

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // load units of faculty
            headFacultyUnits.removeAllElements();
            JSONArray jsonArray = null;
            try {
                jsonArray = getUnitsOfFaculty();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            for (int i=0; i<jsonArray.toArray().length; i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                headFacultyUnits.addElement(jsonObject.get("unitID") + " " + jsonObject.get("unitName") + " "
                        + jsonObject.get("capacity"));
            }
        }
        if (e.getSource() == btnProfReadObjections) {
            try {
                String text = (String) jlistObjection.getSelectedValue();
                answerObjections(Integer.parseInt(text.split(" ")[0]));

                // dump objections for this professor
                objectionList.removeAllElements();
                JSONArray jsonObjection = selectObjbections();
                for (int i=0; i<jsonObjection.toArray().length; i++){
                    JSONObject jsonObjectionObject = (JSONObject) jsonObjection.get(i);
                    JSONObject dataFromGradeID = getDataFromGradeID((int) jsonObjectionObject.get("gradeID"));
                    objectionList.addElement(jsonObjectionObject.get("objectionID") + " "
                            + dataFromGradeID.get("studentName") + " " + dataFromGradeID.get("score")
                            + ": " + jsonObjectionObject.get("type"));
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

        }
        if (e.getSource() == btnProfChoose) {
            try {
                if (btnProfChoose.getText().equals("choose")) {
                    String text = (String) jlistProfessorUnits.getSelectedValue();

                    profUnitsscrollPaneObjection.setVisible(false);
                    profUnitListScrollPanel.setVisible(true);

                    // load grades
                    studendOfUnitList.removeAllElements();
                    JSONArray jsonArray = getStudentsOfaUnit(Integer.parseInt(text.split(" ")[0]));
                    for (int i=0; i<jsonArray.toArray().length; i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        studendOfUnitList.addElement(jsonObject.get("gradeID") + " " + jsonObject.get("studentName")
                                + " " + jsonObject.get("score") + " is it editable = " + jsonObject.get("isEditable"));
                    }
                    btnProfChoose.setText("set score");
                    txtSetScore.setVisible(true);
                } else {
                    String text = (String) jlistStudentOfUnit.getSelectedValue();
                    addScore(Double.valueOf(txtSetScore.getText()), Integer.parseInt(text.split(" ")[0]));

                    // load grades
                    studendOfUnitList.removeAllElements();
                    JSONArray jsonArray = getStudentsOfaUnit(Integer.parseInt(text.split(" ")[0]));
                    for (int i=0; i<jsonArray.toArray().length; i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        studendOfUnitList.addElement(jsonObject.get("gradeID") + " " + jsonObject.get("studentName")
                                + " " + jsonObject.get("score") + " is it editable = " + jsonObject.get("isEditable"));
                    }

                }

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

        }
        if (e.getSource() == btnProfBackPage) {
            profUnitsscrollPaneObjection.setVisible(true);
            profUnitListScrollPanel.setVisible(false);

            btnProfChoose.setText("choose");
            txtSetScore.setVisible(false);
        }
        if (e.getSource() == btnTakeUnit) {
            try {
                String text = (String) jlistPickUnits.getSelectedValue();
                takeUnits(Integer.parseInt(text.split(" ")[0]));

                // show untaken units of the student
                pickUnitsList.removeAllElements();
                JSONArray jsonArray = getUnitsOfStudent();
                for (int i=0; i<jsonArray.toArray().length; i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    pickUnitsList.addElement(jsonObject.get("unitID") + " " + jsonObject.get("unitName"));
                }

                // show grades
                reportCardList.removeAllElements();
                JSONArray jsonArray1 = getReportCardGrades();
                for (int i=0; i<jsonArray1.toArray().length; i++){
                    JSONObject jsonObject = (JSONObject) jsonArray1.get(i);
                    reportCardList.addElement(jsonObject.get("unitID") + " " + jsonObject.get("unitName")
                            + " " + jsonObject.get("score") + " " + jsonObject.get("professorName"));
                }

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == btnStudentObject) {
            try {
                String text = (String) jlistReportCard.getSelectedValue();
                objection(Integer.parseInt(text.split(" ")[0]), txtStuObjection.getText());

                txtStuObjection.setText("type of objection");

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == btnStudentSendMsg) {
            try {
                String firstname = txtPrfName.getText().split(" ")[0];
                String lastname = txtPrfName.getText().split(" ")[1];
                sendMessageToProfessor(getProfessorID(firstname, lastname), txtStudentMessage.getText());
                txtPrfName.setText("firstname lastname");
                txtStudentMessage.setText("your message...");

                // load chats
                studentChatList.removeAllElements();
                JSONArray jsonArray2 = getMyChat();
                for (int i=0; i<jsonArray2.toArray().length; i++){
                    JSONObject jsonObject = (JSONObject) jsonArray2.get(i);
                    studentChatList.addElement("contact: " + jsonObject.get("Name") + " - text: "
                            + jsonObject.get("message"));
                }

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == btnStuRefresh) {
            try {
                // load chats
                studentChatList.removeAllElements();
                JSONArray jsonArray2 = getMyChat();
                for (int i=0; i<jsonArray2.toArray().length; i++){
                    JSONObject jsonObject = (JSONObject) jsonArray2.get(i);
                    studentChatList.addElement("contact: " + jsonObject.get("Name") + " - text: "
                            + jsonObject.get("message"));
                }

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == btnProfessorSendMsg) {
            try {
                String firstname = txtStuName.getText().split(" ")[0];
                String lastname = txtStuName.getText().split(" ")[1];
                System.out.println(getStudentID("hooman", "edraki"));
                sendMessageToStudent(getStudentID(firstname, lastname), txtProfessorMessage.getText());
                txtStuName.setText("firstname lastname");
                txtProfessorMessage.setText("your message...");

                // load chats
                professorChatList.removeAllElements();
                JSONArray jsonArray2 = getMyChat();
                for (int i=0; i<jsonArray2.toArray().length; i++){
                    JSONObject jsonObject = (JSONObject) jsonArray2.get(i);
                    professorChatList.addElement("contact: " + jsonObject.get("Name") + " - text: "
                            + jsonObject.get("message"));
                }

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == btnPrfRefresh) {
            try {
                // load chats
                professorChatList.removeAllElements();
                JSONArray jsonArray2 = getMyChat();
                for (int i=0; i<jsonArray2.toArray().length; i++){
                    JSONObject jsonObject = (JSONObject) jsonArray2.get(i);
                    professorChatList.addElement("contact: " + jsonObject.get("Name") + " - text: "
                            + jsonObject.get("message"));
                }

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

    private void sendMessageToProfessor(int professorID, String text) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "sendMessage");
        jsonData.put("text", text);
        jsonData.put("professorID", professorID);

        client.sendRequest(jsonData);
    }

    private void sendMessageToStudent(int studentID, String text) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "sendMessage");
        jsonData.put("text", text);
        jsonData.put("studentID", studentID);

        client.sendRequest(jsonData);
    }

    private JSONArray getMyChat() throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "getMyChat");

        return client.sendRequestgetArray(jsonData);
    }

    private JSONArray selectObjbections() throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "get objection");
        return client.sendRequestgetArray(jsonData);
    }

    private JSONArray getReportCardGrades() throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "getReportCardGrades");
        return client.sendRequestgetArray(jsonData);
    }
    private void objection(int gradeID, String type) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "objection");
        jsonData.put("gradeID", gradeID);
        jsonData.put("type", type);
        client.sendRequest(jsonData);
    }

    private JSONObject getDataFromGradeID(int gradeID) throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "getDataFromGradeID");
        jsonData.put("gradeID", gradeID);
        return (JSONObject) client.sendRequestgetObject(jsonData);
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

    private JSONArray selectProfessor() throws IOException, ClassNotFoundException {
        JSONObject jsonProf = new JSONObject();
        jsonProf.put("request", "select all professor");


        return (JSONArray) client.sendRequestgetArray(jsonProf);
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

    private int getProfessorID (String username) throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "get professorID by username");
        jsonData.put("username", username);

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

    private JSONArray getProfessorUnit() throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "get professorUnits");

        JSONArray jsonArray = client.sendRequestgetArray(jsonData);
        return jsonArray;
    }

    private JSONArray getStudentsOfaUnit(int unitID) throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "getStudentsOfaUnit");
        jsonData.put("unitID", unitID);
        return client.sendRequestgetArray(jsonData);
    }

    private JSONArray getUnitsOfStudent() throws IOException, ClassNotFoundException {
        // units that a student can choose (from their faculty)
        // it won't appear if they have had taken the unit
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "getUnitsOfStudent");
        return client.sendRequestgetArray(jsonData);
    }

    private JSONArray getUnitsOfFaculty() throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "getUnitsOfFaculty");

        return client.sendRequestgetArray(jsonData);
    }

    private void takeUnits(int unitID) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "takeUnit");
        jsonData.put("unitID", unitID);

        client.sendRequest(jsonData);
    }

    private void addScore (double score, int gradeID) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "set score");
        jsonData.put("score", score);
        jsonData.put("gradeID", gradeID);

        client.sendRequest(jsonData);
    }
    private void registerScore (int unitID) throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "set scoreEditable false");
        jsonData.put("unitID", unitID);

        client.sendRequest(jsonData);
    }
    private void registerAllScore () throws IOException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "set all scoreEditable false");

        client.sendRequest(jsonData);
    }

    private boolean isHeadFaculty (int professorID) throws IOException, ClassNotFoundException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "is head faculty");
        jsonData.put("professorID", professorID);

        return (boolean) client.sendRequestgetObject(jsonData).get("isHead");
    }
}
