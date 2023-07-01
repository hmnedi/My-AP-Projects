import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsWindow extends JFrame implements ActionListener {
    JButton backToMenuBtn,addBtn;
    JTextField youCountryColor,enemyplace,yourplace;
    JLayeredPane layers;
    JPanel panel;
    MenuPanel menuPanel;
    public SettingsWindow(){
        setSize(new Dimension(960,540));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setTitle("settings");
        initComponents();
        layers.add(panel);
        layers.add(menuPanel);
    }

    private void initComponents() {

        panel = new JPanel();
        layers = getLayeredPane();
        menuPanel = new MenuPanel();
        panel.setBounds(0,0,960,540);
        panel.setLayout(null);
        panel.setOpaque(false);
        menuPanel.setBounds(0,0,1200,800);


        youCountryColor = new JTextField("youCountryColor(RED, CYAN, YELLOW)");
        youCountryColor.setBounds(300,100,200,60);
        youCountryColor.setBackground(new Color(0xE8E8E8));
        youCountryColor.setBorder(new RoundedBorder(15));
        youCountryColor.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // to clear the prefix text
                if (youCountryColor.getText().equals("youCountryColor(RED, CYAN, YELLOW)")){
                    youCountryColor.setText("");
                }
            }
        });
        panel.add(youCountryColor);


        enemyplace = new JTextField("enemy country place(0, 1, 2, 3)");
        enemyplace.setBounds(300,200,200,60);
        enemyplace.setBackground(new Color(0xE8E8E8));
        enemyplace.setBorder(new RoundedBorder(15));
        enemyplace.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // to clear the prefix text
                if (enemyplace.getText().equals("enemy country place(0, 1, 2, 3)")){
                    enemyplace.setText("");
                }
            }
        });

        panel.add(enemyplace);

        yourplace = new JTextField("your country place(0, 1, 2, 3)");
        yourplace.setBounds(300,300,200,60);
        yourplace.setBackground(new Color(0xE8E8E8));
        yourplace.setBorder(new RoundedBorder(15));
        yourplace.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                // to clear the prefix text
                if (yourplace.getText().equals("your country place(0, 1, 2, 3)")){
                    yourplace.setText("");
                }
            }
        });
        panel.add(yourplace);


        addBtn = new JButton("add");
        addBtn.setFocusable(false);
        addBtn.setBounds(260,400,150,60);
        addBtn.setBackground(new Color(0xE8FFE8));
        addBtn.setBorder(new RoundedBorder(15));
        panel.add(addBtn);


        backToMenuBtn = new JButton("back to menu");
        backToMenuBtn.setFocusable(false);
        backToMenuBtn.setBounds(430,400,150,60);
        backToMenuBtn.setBackground(new Color(0xE8FFE8));
        backToMenuBtn.setBorder(new RoundedBorder(15));
        panel.add(backToMenuBtn);


        addBtn.addActionListener(this);
        backToMenuBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addBtn){
            if(youCountryColor.getText().length()>0){
                File playerCountryColor = new File("PlayerCountryColor.txt");
                try {
                    writeFile(playerCountryColor, youCountryColor.getText().toUpperCase());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if(yourplace.getText().length()>0){
                File playerCountryPlace = new File("PlayerCountryPlace.txt");
                try {
                    writeFile(playerCountryPlace, yourplace.getText());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if(enemyplace.getText().length()>0){
                File enemyCountryPlace = new File("enemyCountryPlace.txt");
                try {
                    writeFile(enemyCountryPlace, enemyplace.getText());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        if(e.getSource() == backToMenuBtn){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new MenuWindow();
                }
            });
            dispose();
        }
    }

    private void writeFile(File file, String text) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(text);
        writer.close();
    }
}
