import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

public class MenuWindow extends JFrame implements ActionListener {
    JButton startGameBtn, createGameBtn, joinGameBtn, settingBtn;
    MenuPanel menuPanel;
    JLayeredPane layers;
    JPanel buttonPanel;

    public MenuWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(960,540));
        setPreferredSize(new Dimension(960,540));
        setIcon();

        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setTitle("menu");
        layers = getLayeredPane();
        menuPanel = new MenuPanel();
        buttonPanel = new JPanel();
        buttonPanel.setBounds(0,0,960,540);
        buttonPanel.setLayout(null);
        buttonPanel.setOpaque(false);
        menuPanel.setBounds(0,0,960,540);
        initButtons();
        layers.add(buttonPanel);
        layers.add(menuPanel);
        setVisible(true);
    }

    private void setIcon() {
        try {
            setIconImage(ImageIO.read(new File("icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        startGameBtn = new JButton("start game");
        startGameBtn.setFocusable(false);
        startGameBtn.setBounds(380,100,200,60);
        startGameBtn.setBackground(new Color(0xE8E8E8));
        startGameBtn.setBorder(new RoundedBorder(15));
        buttonPanel.add(startGameBtn);

        createGameBtn = new JButton("create game");
        createGameBtn.setFocusable(false);
        createGameBtn.setBounds(380,200,200,60);
        createGameBtn.setBackground(new Color(0xE8E8E8));
        createGameBtn.setBorder(new RoundedBorder(15));
        buttonPanel.add(createGameBtn);


        joinGameBtn = new JButton("join game");
        joinGameBtn.setFocusable(false);
        joinGameBtn.setBounds(380,300,200,60);
        joinGameBtn.setBackground(new Color(0xE8E8E8));
        joinGameBtn.setBorder(new RoundedBorder(15));
        buttonPanel.add(joinGameBtn);


        settingBtn = new JButton("settings");
        settingBtn.setFocusable(false);
        settingBtn.setBounds(380,400,200,60);
        settingBtn.setBackground(new Color(0xE8E8E8));
        settingBtn.setBorder(new RoundedBorder(15));
        buttonPanel.add(settingBtn);


        startGameBtn.addActionListener(this);
        createGameBtn.addActionListener(this);
        joinGameBtn.addActionListener(this);
        settingBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startGameBtn){
            SwingUtilities.invokeLater(() -> {
                try {
                    OffLineGameWindow gameWindow = new OffLineGameWindow();

                    GameLoop gameLoop = new GameLoop(gameWindow,"offline");
                    Executors.newCachedThreadPool().execute(gameLoop);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            });
            dispose();
        }
        else if (e.getSource() == settingBtn){
            SwingUtilities.invokeLater(() -> {
                new SettingsWindow();
            });

            dispose();
        }
    }
}
