import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardLaout extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel panelGame;
    private JButton btnSinglePlayer, btnExit, btnSetting;
    private Container container;

    public CardLaout() {

        container = getContentPane();
        cardLayout = new CardLayout();
        container.setLayout(cardLayout);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Greedy Eating Number");
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0x5b5870));
        this.setLocationRelativeTo(null);
        ImageIcon logo = new ImageIcon("pic/logo.png");
        this.setIconImage(logo.getImage());

        panelGame = new JPanel();

        btnSinglePlayer = new JButton("Single Player");
        btnSetting = new JButton("Setting");
        btnExit = new JButton("Exit");

        btnSinglePlayer.addActionListener(this);
        btnSetting.addActionListener(this);
        btnExit.addActionListener(this);

        btnSinglePlayer.setFont(new Font("Inkpen2 Script Std", Font.PLAIN, 36)); //TODO: auto install font
        btnSetting.setFont(new Font("Inkpen2 Script Std", Font.PLAIN, 38));
        btnExit.setFont(new Font("Inkpen2 Script Std", Font.PLAIN, 32));

        btnSinglePlayer.setBounds(275, 80 + 80, 200, 80);
        btnSetting.setBounds(275, 180 + 80, 200, 80);
        btnExit.setBounds(285 + 30, 282 + 80, 120, 50);

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
        JLabel panelMenu = new JLabel(pageMenuBack);

        panelMenu.setLayout(null);
        panelMenu.add(btnSinglePlayer);
        panelMenu.add(btnSetting);
        panelMenu.add(btnExit);

        container.add("Panel Menu", panelMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSinglePlayer) {
            cardLayout.next(container);
        }
        else if (e.getSource() == btnExit){
            System.exit(0);
        }

    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardLaout extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel panelGame;
    private JButton btnSinglePlayer, btnExit, btnSetting;
    private Container container;

    public CardLaout() {

        container = getContentPane();
        cardLayout = new CardLayout();
        container.setLayout(cardLayout);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Greedy Eating Number");
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0x5b5870));
        this.setLocationRelativeTo(null);
        ImageIcon logo = new ImageIcon("pic/logo.png");
        this.setIconImage(logo.getImage());

        panelGame = new JPanel();

        btnSinglePlayer = new JButton("Single Player");
        btnSetting = new JButton("Setting");
        btnExit = new JButton("Exit");

        btnSinglePlayer.addActionListener(this);
        btnSetting.addActionListener(this);
        btnExit.addActionListener(this);

        btnSinglePlayer.setFont(new Font("Inkpen2 Script Std", Font.PLAIN, 36)); //TODO: auto install font
        btnSetting.setFont(new Font("Inkpen2 Script Std", Font.PLAIN, 38));
        btnExit.setFont(new Font("Inkpen2 Script Std", Font.PLAIN, 32));

        btnSinglePlayer.setBounds(275, 80 + 80, 200, 80);
        btnSetting.setBounds(275, 180 + 80, 200, 80);
        btnExit.setBounds(285 + 30, 282 + 80, 120, 50);

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
        JLabel panelMenu = new JLabel(pageMenuBack);

        panelMenu.setLayout(null);
        panelMenu.add(btnSinglePlayer);
        panelMenu.add(btnSetting);
        panelMenu.add(btnExit);

        container.add("Panel Menu", panelMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSinglePlayer) {
            cardLayout.next(container);
        }
        else if (e.getSource() == btnExit){
            System.exit(0);
        }

    }
}
