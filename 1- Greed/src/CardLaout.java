import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CardLaout extends JFrame implements ActionListener, ChangeListener {
    CardLayout cardLayout;
    JButton btnSinglePlayer, btnExit, btnSetting, btnBack;
    Container container;
    JPanel panelSetting;
    JSlider sliderValueN;
    JLabel lblSliderN;

    public CardLaout() {

        container = getContentPane();
        cardLayout = new CardLayout();
        container.setLayout(cardLayout);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Milk Shake's Greed"); // todo: or milkshakes grid
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0x5b5870));
        ImageIcon logo = new ImageIcon("pic/logo.png");
        this.setIconImage(logo.getImage());

        // setting up the menu page
        btnSinglePlayer = new JButton("Single Player");
        btnSetting = new JButton("Setting");
        btnExit = new JButton("Exit");

        btnSinglePlayer.addActionListener(this);
        btnSetting.addActionListener(this);
        btnExit.addActionListener(this);

        btnSinglePlayer.setFont(new Font("Inkpen2 Script Std", Font.PLAIN, 46)); //TODO: auto install font
        btnSetting.setFont(new Font("Inkpen2 Script Std", Font.PLAIN, 48));
        btnExit.setFont(new Font("Inkpen2 Script Std", Font.PLAIN, 44));

        btnSinglePlayer.setBounds(375, 250, 300, 100);
        btnSetting.setBounds(375, 375 , 300, 100);
        btnExit.setBounds(425, 302 + 200, 200, 70);

        btnSetting.setFocusable(false);
        btnSinglePlayer.setFocusable(false);
        btnExit.setFocusable(false);

        btnSinglePlayer.setBackground(new Color(0xdbbc91));
        btnSinglePlayer.setBorder(BorderFactory.createLineBorder(new Color(0x544633)));
        btnSetting.setBackground(new Color(0xdbbc91));
        btnSetting.setBorder(BorderFactory.createLineBorder(new Color(0x544633)));
        btnExit.setBackground(new Color(0xdbbc91));
        btnExit.setBorder(BorderFactory.createLineBorder(new Color(0x544633)));

        // simple trick to set background image, I'm using a label as the panel
        ImageIcon pageMenuBack = new ImageIcon("pic/backpic.jpg");
        JLabel panelMenu = new JLabel(pageMenuBack), lblGameTittle = new JLabel("Milk Shake's Greed");

        lblGameTittle.setBounds(300, 120, 450, 50);
        lblGameTittle.setHorizontalAlignment(SwingConstants.CENTER);
        lblGameTittle.setVerticalAlignment(SwingConstants.CENTER);
        lblGameTittle.setFont(new Font("Inkpen2 Script Std", Font.BOLD, 62));
        lblGameTittle.setForeground(new Color(0x544633));

        // read file and load game's records
        String data = "<html>Records<hr/>";
        try {
            File Obj = new File("logs/history.gametxt");
            Scanner Reader = new Scanner(Obj);

            while (Reader.hasNextLine()) {
                data += "- " + Reader.nextLine() + "<br/>";
            }
            Reader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }
        data += "</html>";

        JLabel lblRecords = new JLabel(data);
        lblRecords.setVerticalAlignment(SwingConstants.TOP);
        lblRecords.setHorizontalAlignment(SwingConstants.CENTER);
        lblRecords.setFont(new Font("Inkpen2 Script Std", Font.PLAIN, 20));
        lblRecords.setForeground(new Color(0x544633));

        JScrollPane scroller = new JScrollPane(lblRecords, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.getViewport().setBackground(new Color(0x77DBBC91, true));
        scroller.setBorder(BorderFactory.createLineBorder(new Color(0x544633)));
        scroller.setBounds(30, 30, 260, 600);


        panelMenu.add(scroller);
        panelMenu.setLayout(null);
        panelMenu.add(lblGameTittle);
        panelMenu.add(btnSinglePlayer);
        panelMenu.add(btnSetting);
        panelMenu.add(btnExit);

        // calling the panelGame from Game class
        JPanel panelGame = new Game().frame;

        // setting up panel setting, it could have been in another class if I wanted to add music
        panelSetting = new JPanel();
        panelSetting.setBackground(new Color(0xf7e4c8));
        panelSetting.setLayout(null);
        btnBack = new JButton("Back");
        btnBack.setBounds(15, 15, 70, 25);
        btnBack.setFont(new Font("Inkpen2 Script Std", Font.PLAIN, 22));
        btnBack.setForeground(new Color(0x595959));
        btnBack.setBackground(new Color(0xF1D7BD));
        btnBack.addActionListener(this);
        btnBack.setFocusable(false);
        JLabel lblPleaseRestart = new JLabel("Please Restart the game after any changes");
        lblPleaseRestart.setBounds(300, 300, 500, 100);
        lblPleaseRestart.setFont(new Font("Inkpen2 Script Std", Font.PLAIN, 25));
        lblPleaseRestart.setForeground(new Color(0x544633));
        lblPleaseRestart.setBackground(null);
        lblPleaseRestart.setOpaque(false);

        sliderValueN = new JSlider(19, 49, 19);
        sliderValueN.setBounds(300, 150, 400, 100);
        sliderValueN.setPaintTicks(true);
        sliderValueN.setMinorTickSpacing(1);
        sliderValueN.setBackground(null);
        sliderValueN.setPaintTrack(true);
        sliderValueN.setMajorTickSpacing(10);
        sliderValueN.setPaintLabels(true);

        lblSliderN = new JLabel("19");
        lblSliderN.setBounds(500, 50, 400, 50);
        lblSliderN.setFont(new Font("Inkpen2 Script Std", Font.PLAIN, 25));
        lblSliderN.setForeground(new Color(0x544633));
        lblSliderN.setBackground(null);

        sliderValueN.addChangeListener(this);

        panelSetting.add(lblSliderN);
        panelSetting.add(sliderValueN);
        panelSetting.add(lblPleaseRestart);
        panelSetting.add(btnBack);


        container.add("Panel Menu", panelMenu);
        container.add("Panel Game", panelGame);
        container.add("Panel Setting", panelSetting);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSinglePlayer) {
            cardLayout.next(container);
            Game.timer.start();
            // todo: I can hold the state of CardLayout and reset and reload every page when the state changes
        }
        else if (e.getSource() == btnSetting) {
            cardLayout.last(container);
        }
        else if (e.getSource() == btnExit){
            System.exit(0);
        }
        else if (e.getSource() == btnBack) {
            cardLayout.first(container);
        }

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        String tmp = String.valueOf(sliderValueN.getValue());
        lblSliderN.setText(tmp);
        try {
            FileWriter Writer = new FileWriter("logs/valueOfN.gametxt");
            Writer.write(tmp);
            Writer.close();
        }
        catch (IOException ea) {
            System.out.println("An error has occurred.");
            ea.printStackTrace();
        }
    }
}
