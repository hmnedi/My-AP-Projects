import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopWatch {

    int elapsedTime = 0, seconds = 0, minutes = 0;
    String secondsString = String.format("%02d", seconds);
    String minutesString = String.format("%02d", minutes);
    Timer timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            elapsedTime += 1000;
            minutes = (elapsedTime/60000) % 60;
            seconds = (elapsedTime/1000) % 60;
            secondsString = String.format("%02d", seconds);
            minutesString = String.format("%02d", minutes);
            JLabel lblTime = Game.lblTimer;
            lblTime.setText(getTime());
        }
    });

    void start() {
        timer.start();
    }

    void stop() {
        timer.stop();
    }


    String getTime() {
        return minutesString + ":" + secondsString;
    }
}
