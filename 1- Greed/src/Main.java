import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);

        CardLaout frame = new CardLaout();
        frame.setSize(750 + 300, 500 + 200);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        // todo: since I used key bindings I can add a multi Player Mode, maybe a solution to getting n from user
    }
}
