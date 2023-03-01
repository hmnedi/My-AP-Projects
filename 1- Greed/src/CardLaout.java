import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardLaout extends JFrame implements ActionListener {
    CardLayout cardLayout;
    JButton btnSinglePlayer, btnExit, btnSetting;
    Container container;

    public CardLaout() {

        container = getContentPane();
        cardLayout = new CardLayout();
        container.setLayout(cardLayout);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Greedy Eating Number");
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

        panelMenu.setLayout(null);
        panelMenu.add(lblGameTittle);
        panelMenu.add(btnSinglePlayer);
        panelMenu.add(btnSetting);
        panelMenu.add(btnExit);

        // calling the panelGame from Game class
        JPanel panelGame = new Game().frame;

        container.add("Panel Menu", panelMenu);
        container.add("Panel Game", panelGame);
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
