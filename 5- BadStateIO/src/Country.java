import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Country {
    private ArrayList<NormalBullet> bullets;
    protected int x;
    protected int y;
    protected int army;
    public boolean isShooting = false;
    public long LAST_SHOOT_TIME;
    protected transient Image image;
    protected static final int HEIGHT = 190;
    protected static final int WIDTH = 190;
    protected Rectangle hitBox;

    public Country(int x,int y) {
        army = 10;
        this.x = x;
        this.y = y;
        hitBox = new Rectangle(getX(),getY(),WIDTH,HEIGHT);
        bullets = new ArrayList<>();
    }

    public ArrayList<NormalBullet> getBullets() {
        return bullets;
    }

    public void addBullet(NormalBullet bullet) {
        bullets.add(bullet);
    }

    public int getY(){
        return y;
    }

    public int getX(){
        return x;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getArmy(){
        return army;
    }

    public void setArmy(int army) {
        this.army = army;
    }

    public  Image getImage() {
        return image;
    }

    public void setImage(String pic) {
        image = new ImageIcon(pic).getImage();
    }

}
