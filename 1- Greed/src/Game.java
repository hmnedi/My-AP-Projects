import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class Game {
    int n = 19, iPos, jPos;
    Border selectionBorder = BorderFactory.createLineBorder(new Color(0x544633), 2);
    JPanel frame, panelTopNavigationBar, panelMid;
    JLabel[][] lblNumbers;
    Action upAaction, downAction, leftAction, rightAction, upRightAction,
            upLeftAction, downRightAction, downLeftAction;

    public Game() {
        frame = new JPanel();
        panelMid = new JPanel();
        panelTopNavigationBar = new JPanel();

        // setting up panels
        panelMid.setLayout(new GridLayout(n, 3*n, 1, 1));
        panelMid.setPreferredSize(new Dimension(100, 100));
        panelMid.setBackground(Color.white);

        panelTopNavigationBar.setPreferredSize(new Dimension(100, 50));
        panelTopNavigationBar.setBackground(new Color(0xf7e4c8));

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

    public class UpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("w");
            if (iPos - 1 >= 0 && lblNumbers[iPos-1][jPos].getBorder() == selectionBorder){
                int tmp = Integer.valueOf(lblNumbers[iPos-1][jPos].getText());
                for (int i=0; i<tmp; i++){
                    lblNumbers[iPos-1-i][jPos].setText("");
                    lblNumbers[iPos-1-i][jPos].setIcon(null);
                    lblNumbers[iPos-1-i][jPos].setBackground(Color.WHITE);
                }
                changePlayerPos(iPos - tmp, jPos);
                showHighLight();
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
                    lblNumbers[iPos+1+i][jPos].setText("");
                    lblNumbers[iPos+1+i][jPos].setIcon(null);
                    lblNumbers[iPos+1+i][jPos].setBackground(Color.WHITE);
                }
                changePlayerPos(iPos + tmp, jPos);
                showHighLight();
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
                    lblNumbers[iPos][jPos-1-i].setText("");
                    lblNumbers[iPos][jPos-1-i].setIcon(null);
                    lblNumbers[iPos][jPos-1-i].setBackground(Color.WHITE);
                }
                changePlayerPos(iPos, jPos - tmp);
                showHighLight();
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
                    lblNumbers[iPos][jPos+1+i].setText("");
                    lblNumbers[iPos][jPos+1+i].setIcon(null);
                    lblNumbers[iPos][jPos+1+i].setBackground(Color.WHITE);
                }
                changePlayerPos(iPos, jPos + tmp);
                showHighLight();
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
                    lblNumbers[iPos-1-i][jPos+1+i].setText("");
                    lblNumbers[iPos-1-i][jPos+1+i].setIcon(null);
                    lblNumbers[iPos-1-i][jPos+1+i].setBackground(Color.WHITE);
                }
                changePlayerPos(iPos - tmp, jPos + tmp);
                showHighLight();
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
                    lblNumbers[iPos-1-i][jPos-1-i].setText("");
                    lblNumbers[iPos-1-i][jPos-1-i].setIcon(null);
                    lblNumbers[iPos-1-i][jPos-1-i].setBackground(Color.WHITE);
                }
                changePlayerPos(iPos - tmp, jPos - tmp);
                showHighLight();
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
                    lblNumbers[iPos+1+i][jPos+1+i].setText("");
                    lblNumbers[iPos+1+i][jPos+1+i].setIcon(null);
                    lblNumbers[iPos+1+i][jPos+1+i].setBackground(Color.WHITE);
                }
                changePlayerPos(iPos + tmp, jPos + tmp);
                showHighLight();
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
                    lblNumbers[iPos+1+i][jPos-1-i].setText("");
                    lblNumbers[iPos+1+i][jPos-1-i].setIcon(null);
                    lblNumbers[iPos+1+i][jPos-1-i].setBackground(Color.WHITE);
                }
                changePlayerPos(iPos + tmp, jPos - tmp);
                showHighLight();
            }
        }
    }
}
