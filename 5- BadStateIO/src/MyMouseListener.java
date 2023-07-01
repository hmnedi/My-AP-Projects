import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class MyMouseListener implements MouseListener, MouseMotionListener {
    public int mouseX = -1;
    public int mouseY = -1;
    public int mousePressX = -1;
    public int mousePressY = -1;
    public static boolean canShoot = false;
    public int countryTarget = -1;
    public ArrayList<Integer> shooterID = new ArrayList<>();
    String status;
    public MyMouseListener(String status){
        this.status  = status;
    }

    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        resetMouseInfo();
        mouseX = e.getX();
        mouseY = e.getY();
        mousePressY = e.getY();
        mousePressX = e.getX();
        System.out.println("Mouse is pressed x: " + mouseX + " y: " + mouseY);

        // who wants to shoot
        if(status.equals("offline")) {
            if (mouseX > 200 && mouseX < 390){
                if (mouseY > 100 && mouseY < 245) {
//                    System.out.println("blue");
                    if (PlayState.counteries[0].getColor() != Color.DARK_GRAY) shooterID.add(0);
                }
                if (mouseY > 300 && mouseY < 450) {
//                    System.out.println("orange");
                    if (PlayState.counteries[1].getColor() != Color.DARK_GRAY) shooterID.add(1);
                }
            }
            if (mouseX > 600 && mouseX < 770){
                if (mouseY > 100 && mouseY < 245){
//                    System.out.println("green");
                    if (PlayState.counteries[2].getColor() != Color.DARK_GRAY) shooterID.add(2);
                }
                if (mouseY > 300 && mouseY < 450) {
//                    System.out.println("pink");
                    if (PlayState.counteries[3].getColor() != Color.DARK_GRAY) shooterID.add(3);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        System.out.println("Mouse is Released x: " + e.getX() + " y: " + e.getY());



        // shoot then clear selection
        if (!shooterID.isEmpty() && countryTarget != -1){
            canShoot = true;
        }
        else if (!shooterID.isEmpty()){
            countryTarget = shooterID.get(shooterID.size()-1);
            shooterID.remove(shooterID.size()-1);
            canShoot = true;
        }
        System.out.println(shooterID.toString() + " attak " + countryTarget);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void resetMouseInfo(){
        mouseX = -1;
        mouseY = -1;
        mousePressX = -1;
        mousePressY = -1;
        countryTarget = -1;
        shooterID.clear();
        canShoot = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if(status.equals("offline")) {
            if (mouseX > 200 && mouseX < 390){
                if (mouseY > 100 && mouseY < 245) {
//                    System.out.println("blue");
                    if (shooterID.get(0)!=0){
                        if (PlayState.counteries[shooterID.get(0)].getColor() == PlayState.counteries[0].getColor() && !shooterID.contains(0)) shooterID.add(0);
                        else if (PlayState.counteries[shooterID.get(0)].getColor() != PlayState.counteries[0].getColor()) countryTarget = 0;

                    }
                }
                if (mouseY > 300 && mouseY < 450) {
//                    System.out.println("orange");
                    if (shooterID.get(0)!=1){
                        if (PlayState.counteries[shooterID.get(0)].getColor() == PlayState.counteries[1].getColor() && !shooterID.contains(1)) shooterID.add(1);
                        else if (PlayState.counteries[shooterID.get(0)].getColor() != PlayState.counteries[1].getColor()) countryTarget = 1;

                    }
                }
            }
            if (mouseX > 600 && mouseX < 770){
                if (mouseY > 100 && mouseY < 245){
//                    System.out.println("green");
                    if (shooterID.get(0)!=2){
                        if (PlayState.counteries[shooterID.get(0)].getColor() == PlayState.counteries[2].getColor() && !shooterID.contains(2)) shooterID.add(2);
                        else if (PlayState.counteries[shooterID.get(0)].getColor() != PlayState.counteries[2].getColor()) countryTarget = 2;
                    }
                }
                if (mouseY > 300 && mouseY < 450) {
//                    System.out.println("pink");
                    if (shooterID.get(0)!=3){
                        if (PlayState.counteries[shooterID.get(0)].getColor() == PlayState.counteries[3].getColor() && !shooterID.contains(3)) shooterID.add(3);
                        else if (PlayState.counteries[shooterID.get(0)].getColor() != PlayState.counteries[3].getColor()) countryTarget = 3;

                    }
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
