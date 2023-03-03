import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Game implements ActionListener {
    int n = 19, iPos, jPos, score=0;
    Border selectionBorder = BorderFactory.createLineBorder(new Color(0x544633), 2);
    JPanel frame, panelTopNavigationBar, panelMid;
    public static JLabel lblTimer;
    JLabel lblScore;
    JLabel[][] lblNumbers;
    JTextField txtUsername;
    JButton btnSaveUserName, btnQuit;
    Action upAaction, downAction, leftAction, rightAction, upRightAction,
            upLeftAction, downRightAction, downLeftAction;
    public static StopWatch timer;

    public Game() {
        // get n value
        setValueOfN();

        // start timer
        timer = new StopWatch();

        frame = new JPanel();
        panelMid = new JPanel();
        panelTopNavigationBar = new JPanel();

        // setting up panels
        panelMid.setLayout(new GridLayout(n, 3*n, 1, 1));
        panelMid.setPreferredSize(new Dimension(100, 100));
        panelMid.setBackground(Color.white);

        panelTopNavigationBar.setPreferredSize(new Dimension(100, 50));
        panelTopNavigationBar.setBackground(new Color(0xf7e4c8));
        panelTopNavigationBar.setLayout(null);

        lblScore = new JLabel("Score: 0");
        lblScore.setBounds(20, 5, 300, 40);
        lblScore.setFont(new Font("Inkpen2 Script Std", Font.PLAIN, 32));
        lblScore.setBackground(null);
        lblScore.setForeground(new Color(0x544633));

        txtUsername = new JTextField(" username...");
        txtUsername.setBounds(820, 5, 200, 40);
        txtUsername.setFont(new Font("Inkpen2 Script Std", Font.PLAIN, 32));
        txtUsername.setForeground(new Color(0x707070));
        txtUsername.setBackground(null);
        txtUsername.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // so it works only with the first mouse click
                if (txtUsername.getText().equals(" username...")){
                    txtUsername.setText("");
                    txtUsername.setForeground(new Color(0x544633));
                }
            }
        });
        btnSaveUserName = new JButton("save");
        btnSaveUserName.setBounds(740, 18, 70, 25);
        btnSaveUserName.setFont(new Font("Inkpen2 Script Std", Font.PLAIN, 22));
        btnSaveUserName.setForeground(new Color(0x595959));
        btnSaveUserName.setBackground(new Color(0xF1D7BD));
        btnSaveUserName.addActionListener(this);
        btnSaveUserName.setFocusable(false);

        lblTimer = new JLabel("00:00");
        lblTimer.setBounds(500, 18, 70, 25);
        lblTimer.setFont(new Font("Inkpen2 Script Std", Font.PLAIN, 22));
        lblTimer.setForeground(new Color(0x544633));
        lblTimer.setBackground(null);
        lblTimer.setOpaque(true);

        btnQuit = new JButton("quit");
        btnQuit.setBounds(145, 15, 70, 25);
        btnQuit.setFont(new Font("Inkpen2 Script Std", Font.PLAIN, 22));
        btnQuit.setForeground(new Color(0x595959));
        btnQuit.setBackground(new Color(0xF1D7BD));
        btnQuit.addActionListener(this);
        btnQuit.setFocusable(false);

        panelTopNavigationBar.add(btnQuit);
        panelTopNavigationBar.add(lblTimer);
        panelTopNavigationBar.add(lblScore);
        panelTopNavigationBar.add(txtUsername);
        panelTopNavigationBar.add(btnSaveUserName);

        // multi panel
        frame.setLayout(new BorderLayout());
        frame.add(panelTopNavigationBar, BorderLayout.NORTH);
        frame.add(panelMid, BorderLayout.CENTER);

        lblNumbers = new JLabel[n][3*n]; // todo: change 19 to n and set it from the setting page remove the shakes
        ImageIcon cup = new ImageIcon("pic/cup1.png");
        Color[] randomColors = {new Color(0xff5226), new Color(0xffee4f),
                new Color(0x73a4ff), new Color(0x51db5e), new Color(0xc763e6),
                    new Color(0xffdb4a), new Color(0xffc130), new Color(0xeb362d)};
        Random rand = new Random();
        Color[][] holdColor = new Color[n][n*3];

        for(int i=0; i<n; i++) {
            for(int j=0; j<3*n; j++) {
                // setting up random number and stuff
                lblNumbers[i][j] = new JLabel(String.valueOf(rand.nextInt(9)+1));
                lblNumbers[i][j].setIcon(cup);
                holdColor[i][j] = randomColors[rand.nextInt(8)];
                lblNumbers[i][j].setBackground(holdColor[i][j]);
                lblNumbers[i][j].setHorizontalTextPosition(SwingConstants.CENTER);
                lblNumbers[i][j].setVerticalTextPosition(SwingConstants.CENTER);
                lblNumbers[i][j].setOpaque(true);
                panelMid.add(lblNumbers[i][j]);
            }
        }

        iPos = n/2;
        jPos = (3*n)/2;
        lblNumbers[iPos][jPos].setText("");
        lblNumbers[iPos][jPos].setHorizontalAlignment(SwingConstants.CENTER);
        lblNumbers[iPos][jPos].setVerticalAlignment(SwingConstants.CENTER);
        lblNumbers[iPos][jPos].setBackground(new Color(0xe8e6e3));
        lblNumbers[iPos][jPos].setBorder(selectionBorder);
        lblNumbers[iPos][jPos].setIcon(new ImageIcon("pic/straw1.png"));


        upAaction = new UpAction();
        downAction = new DownAction();
        leftAction = new LeftAction();
        rightAction = new RightAction();
        upRightAction = new UpRightAction();
        upLeftAction = new UpLeftAction();
        downRightAction = new DownRightAction();
        downLeftAction = new DownLeftAction();

        // key binding stuff
        frame.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('w'),
                "walkup");
        frame.getActionMap().put("walkup", upAaction);
        frame.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('s'),
                "walkdown");
        frame.getActionMap().put("walkdown", downAction);
        frame.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('a'),
                "walkleft");
        frame.getActionMap().put("walkleft", leftAction);
        frame.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('d'),
                "walkright");
        frame.getActionMap().put("walkright", rightAction);
        frame.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('e'),
                "walkupright");
        frame.getActionMap().put("walkupright", upRightAction);
        frame.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('q'),
                "walkupleft");
        frame.getActionMap().put("walkupleft", upLeftAction);
        frame.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('c'),
                "walkdownright");
        frame.getActionMap().put("walkdownright", downRightAction);
        frame.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('z'),
                "walkdownleft");
        frame.getActionMap().put("walkdownleft", downLeftAction);


        panelMid.setVisible(true);
        frame.setVisible(true);

        showHighLight();
    }

    private void setValueOfN() {
        // read file and load n
        String data = "";
        try {
            File Obj = new File("logs/valueOfN.gametxt");
            Scanner Reader = new Scanner(Obj);

            while (Reader.hasNextLine()) {
                data = Reader.nextLine();
            }
            Reader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }
        n = Integer.parseInt(data);
    }

    private void showHighLight() {

        // w - up
        if ((iPos - 1 >= 0) && (!lblNumbers[iPos-1][jPos].getText().equals("")) && (iPos - Integer.valueOf(lblNumbers[iPos-1][jPos].getText()) >= 0)) {
            for (int i=0; i<Integer.valueOf(lblNumbers[iPos-1][jPos].getText()); i++){
                lblNumbers[iPos-1-i][jPos].setBorder(selectionBorder);
            }
        }

        // s - down
        if ((iPos + 1 < n) && (!lblNumbers[iPos+1][jPos].getText().equals("")) && (iPos + Integer.valueOf(lblNumbers[iPos+1][jPos].getText()) < n)) {
            for (int i=0; i<Integer.valueOf(lblNumbers[iPos+1][jPos].getText()); i++){
                lblNumbers[iPos+1+i][jPos].setBorder(selectionBorder);
            }
        }

        // a - left
        if ((jPos - 1 >= 0) && (!lblNumbers[iPos][jPos-1].getText().equals("")) && (jPos - Integer.valueOf(lblNumbers[iPos][jPos-1].getText()) >= 0)) {
            for (int i=0; i<Integer.valueOf(lblNumbers[iPos][jPos-1].getText()); i++){
                lblNumbers[iPos][jPos-1-i].setBorder(selectionBorder);
            }
        }

        // d - right
        if ((jPos + 1 < 3*n) && (!lblNumbers[iPos][jPos+1].getText().equals("")) && (jPos + Integer.valueOf(lblNumbers[iPos][jPos+1].getText()) < 3*n)) {
            for (int i=0; i<Integer.valueOf(lblNumbers[iPos][jPos+1].getText()); i++){
                lblNumbers[iPos][jPos+1+i].setBorder(selectionBorder);
            }
        }

        // e - up right
        if ((iPos - 1 >= 0) && (jPos + 1 < 3*n) && (!lblNumbers[iPos-1][jPos+1].getText().equals("")) && (iPos - Integer.valueOf(lblNumbers[iPos-1][jPos+1].getText()) >= 0) && (jPos + Integer.valueOf(lblNumbers[iPos-1][jPos+1].getText()) < 3*n)) {
            for (int i=0; i<Integer.valueOf(lblNumbers[iPos-1][jPos+1].getText()); i++){
                lblNumbers[iPos-1-i][jPos+1+i].setBorder(selectionBorder);
            }
        }

        // q - up left
        if ((iPos - 1 >= 0) && (jPos - 1 >= 0) && (!lblNumbers[iPos-1][jPos-1].getText().equals("")) && (iPos - Integer.valueOf(lblNumbers[iPos-1][jPos-1].getText()) >= 0) && (jPos - Integer.valueOf(lblNumbers[iPos-1][jPos-1].getText()) >= 0)) {
            for (int i=0; i<Integer.valueOf(lblNumbers[iPos-1][jPos-1].getText()); i++){
                lblNumbers[iPos-1-i][jPos-1-i].setBorder(selectionBorder);
            }
        }

        // c - down right
        if ((iPos + 1 < n) && (jPos + 1 < 3*n) && (!lblNumbers[iPos+1][jPos+1].getText().equals("")) && (iPos + Integer.valueOf(lblNumbers[iPos+1][jPos+1].getText()) < n) && (jPos + Integer.valueOf(lblNumbers[iPos+1][jPos+1].getText()) < 3*n)) {
            for (int i=0; i<Integer.valueOf(lblNumbers[iPos+1][jPos+1].getText()); i++){
                lblNumbers[iPos+1+i][jPos+1+i].setBorder(selectionBorder);
            }
        }

        // z - down left
        if ((iPos + 1 < n) && (jPos - 1 >= 0) && (!lblNumbers[iPos+1][jPos-1].getText().equals("")) && (iPos + Integer.valueOf(lblNumbers[iPos+1][jPos-1].getText()) < n) && (jPos - Integer.valueOf(lblNumbers[iPos+1][jPos-1].getText()) >= 0)) {
            for (int i=0; i<Integer.valueOf(lblNumbers[iPos+1][jPos-1].getText()); i++){
                lblNumbers[iPos+1+i][jPos-1-i].setBorder(selectionBorder);
            }
        }
    }

    private void clearHighLights() {
        for(int i=0; i<n; i++)
            for(int j=0; j<3*n; j++)
                lblNumbers[i][j].setBorder(null);
    }

    private void changePlayerPos(int i, int j) {
        lblNumbers[iPos][jPos].setIcon(null);
        lblNumbers[iPos][jPos].setBackground(Color.WHITE);

        clearHighLights();

        iPos = i;
        jPos = j;

        lblNumbers[iPos][jPos].setBorder(selectionBorder);
        lblNumbers[iPos][jPos].setIcon(new ImageIcon("pic/straw1.png"));
        lblNumbers[iPos][jPos].setBackground(new Color(0xe8e6e3));
        lblNumbers[iPos][jPos].setHorizontalAlignment(SwingConstants.CENTER);
        lblNumbers[iPos][jPos].setVerticalAlignment(SwingConstants.CENTER);
    }

    private void isFinish() {
        boolean l = true, r = true, u = true, d = true, ur =true, ul =true, dr = true, dl = true;
        if (iPos + 1 >= n || (lblNumbers[iPos+1][jPos].getBorder() != selectionBorder)) d = false;
        if (iPos - 1 < 0 || (lblNumbers[iPos-1][jPos].getBorder() != selectionBorder)) u = false;
        if (jPos + 1 >= n*3 || (lblNumbers[iPos][jPos+1].getBorder() != selectionBorder)) r = false;
        if (jPos - 1 < 0 || (lblNumbers[iPos][jPos-1].getBorder() != selectionBorder)) l = false;
        if ((jPos + 1 >= n*3 || iPos - 1 < 0) || (lblNumbers[iPos-1][jPos+1].getBorder() != selectionBorder)) ur = false;
        if ((jPos - 1 < 0 || iPos - 1 < 0) || (lblNumbers[iPos-1][jPos-1].getBorder() != selectionBorder)) ul = false;
        if ((jPos + 1 >= n*3 || iPos + 1 >= n) || (lblNumbers[iPos+1][jPos+1].getBorder() != selectionBorder)) dr = false;
        if ((jPos - 1 < 0 || iPos + 1 >= n) || (lblNumbers[iPos+1][jPos-1].getBorder() != selectionBorder)) dl = false;

        if (score == n*n*3) {
            JOptionPane.showMessageDialog(null, "", "You won",
                    JOptionPane.PLAIN_MESSAGE,new ImageIcon("pic/youwonpic.jpg"));
            System.out.println("You Won");
            saveRecordAsFile();
            timer.stop();
        }
        else if (!l && !r && !u && !d && !ur && !ul && !dr && !dl){
            JOptionPane.showMessageDialog(null, "", "You Lost",
                    JOptionPane.PLAIN_MESSAGE,new ImageIcon("pic/youlostpic.jpg"));
            System.out.println("You Lost");
            saveRecordAsFile();
            timer.stop();
        }
    }

    private void saveRecordAsFile() {
        try {
            FileWriter Writer = new FileWriter("logs/history.gametxt", true);
            Writer.write(txtUsername.getText() + " scoerd: " + score + " on " + lblTimer.getText() +
                    " time with " + n + "*" + (n*3) + " table");
            Writer.close();
            System.out.println("Successfully written.");
        }
        catch (IOException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }
    }

    private void addScore() {
        score++;
        lblScore.setText("Score: " + score);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSaveUserName) {
            txtUsername.setFocusable(false);
            btnSaveUserName.setEnabled(false);
        }
        else if (e.getSource() == btnQuit) {
            frame.setVisible(false);
            // reset everything in this frame
            //todo stop watch

            CardLaout crd =new CardLaout();
            crd.cardLayout.previous(crd.container);
        }
    }

    public class UpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("w");
            if (iPos - 1 >= 0 && lblNumbers[iPos-1][jPos].getBorder() == selectionBorder){
                int tmp = Integer.valueOf(lblNumbers[iPos-1][jPos].getText());
                for (int i=0; i<tmp; i++){
                    if (lblNumbers[iPos-1-i][jPos].getBackground() != Color.WHITE) addScore();

                    lblNumbers[iPos-1-i][jPos].setText("");
                    lblNumbers[iPos-1-i][jPos].setIcon(null);
                    lblNumbers[iPos-1-i][jPos].setBackground(Color.WHITE);
                }
                changePlayerPos(iPos - tmp, jPos);
                showHighLight();
                isFinish();
            }
        }
    }

    public class DownAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("s");
            if (iPos + 1 < n && lblNumbers[iPos+1][jPos].getBorder() == selectionBorder){
                int tmp = Integer.valueOf(lblNumbers[iPos+1][jPos].getText());
                for (int i=0; i<tmp; i++){
                    if (lblNumbers[iPos+1+i][jPos].getBackground() != Color.WHITE) addScore();

                    lblNumbers[iPos+1+i][jPos].setText("");
                    lblNumbers[iPos+1+i][jPos].setIcon(null);
                    lblNumbers[iPos+1+i][jPos].setBackground(Color.WHITE);
                }
                changePlayerPos(iPos + tmp, jPos);
                showHighLight();
                isFinish();
            }
        }
    }

    public class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("a");
            if (jPos - 1 >= 0 && lblNumbers[iPos][jPos-1].getBorder() == selectionBorder){
                int tmp = Integer.valueOf(lblNumbers[iPos][jPos-1].getText());
                for (int i=0; i<tmp; i++){
                    if (lblNumbers[iPos][jPos-1-i].getBackground() != Color.WHITE) addScore();

                    lblNumbers[iPos][jPos-1-i].setText("");
                    lblNumbers[iPos][jPos-1-i].setIcon(null);
                    lblNumbers[iPos][jPos-1-i].setBackground(Color.WHITE);
                }
                changePlayerPos(iPos, jPos - tmp);
                showHighLight();
                isFinish();
            }
        }
    }

    public class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("d");
            if (jPos + 1 < 3*n && lblNumbers[iPos][jPos+1].getBorder() == selectionBorder){
                int tmp = Integer.valueOf(lblNumbers[iPos][jPos+1].getText());
                for (int i=0; i<tmp; i++){
                    if (lblNumbers[iPos][jPos+1+i].getBackground() != Color.WHITE) addScore();

                    lblNumbers[iPos][jPos+1+i].setText("");
                    lblNumbers[iPos][jPos+1+i].setIcon(null);
                    lblNumbers[iPos][jPos+1+i].setBackground(Color.WHITE);
                }
                changePlayerPos(iPos, jPos + tmp);
                showHighLight();
                isFinish();
            }
        }
    }

    public class UpRightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("e");
            if (iPos - 1 >= 0 && jPos + 1 < 3*n && lblNumbers[iPos-1][jPos+1].getBorder() == selectionBorder){
                int tmp = Integer.valueOf(lblNumbers[iPos-1][jPos+1].getText());
                for (int i=0; i<tmp; i++){
                    if (lblNumbers[iPos-1-i][jPos+1+i].getBackground() != Color.WHITE) addScore();

                    lblNumbers[iPos-1-i][jPos+1+i].setText("");
                    lblNumbers[iPos-1-i][jPos+1+i].setIcon(null);
                    lblNumbers[iPos-1-i][jPos+1+i].setBackground(Color.WHITE);
                }
                changePlayerPos(iPos - tmp, jPos + tmp);
                showHighLight();
                isFinish();
            }
        }
    }

    public class UpLeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("q");
            if (iPos - 1 >= 0 && jPos - 1 >= 0 && lblNumbers[iPos-1][jPos-1].getBorder() == selectionBorder){
                int tmp = Integer.valueOf(lblNumbers[iPos-1][jPos-1].getText());
                for (int i=0; i<tmp; i++){
                    if (lblNumbers[iPos-1-i][jPos-1-i].getBackground() != Color.WHITE) addScore();

                    lblNumbers[iPos-1-i][jPos-1-i].setText("");
                    lblNumbers[iPos-1-i][jPos-1-i].setIcon(null);
                    lblNumbers[iPos-1-i][jPos-1-i].setBackground(Color.WHITE);
                }
                changePlayerPos(iPos - tmp, jPos - tmp);
                showHighLight();
                isFinish();
            }
        }
    }

    public class DownRightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("c");
            if (iPos + 1 < n && jPos + 1 < 3*n && lblNumbers[iPos+1][jPos+1].getBorder() == selectionBorder){
                int tmp = Integer.valueOf(lblNumbers[iPos+1][jPos+1].getText());
                for (int i=0; i<tmp; i++){
                    if (lblNumbers[iPos+1+i][jPos+1+i].getBackground() != Color.WHITE) addScore();

                    lblNumbers[iPos+1+i][jPos+1+i].setText("");
                    lblNumbers[iPos+1+i][jPos+1+i].setIcon(null);
                    lblNumbers[iPos+1+i][jPos+1+i].setBackground(Color.WHITE);
                }
                changePlayerPos(iPos + tmp, jPos + tmp);
                showHighLight();
                isFinish();
            }
        }
    }

    public class DownLeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("z");
            if (iPos + 1 < n && jPos - 1 >= 0 && lblNumbers[iPos+1][jPos-1].getBorder() == selectionBorder){
                int tmp = Integer.valueOf(lblNumbers[iPos+1][jPos-1].getText());
                for (int i=0; i<tmp; i++){
                    if (lblNumbers[iPos+1+i][jPos-1-i].getBackground() != Color.WHITE) addScore();

                    lblNumbers[iPos+1+i][jPos-1-i].setText("");
                    lblNumbers[iPos+1+i][jPos-1-i].setIcon(null);
                    lblNumbers[iPos+1+i][jPos-1-i].setBackground(Color.WHITE);
                }
                changePlayerPos(iPos + tmp, jPos - tmp);
                showHighLight();
                isFinish();
            }
        }
    }

}
