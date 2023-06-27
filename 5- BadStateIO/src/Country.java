import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Country {
    protected int x;
    protected int y;
    protected int army;
    protected transient Image image;
    protected int alliedCountryIndex[] = new int[4];
    protected static final int HEIGHT = 190;
    protected static final int WIDTH = 190;
    protected Rectangle hitBox;

    public Country(int x,int y) {
        army = 10;
        this.x = x;
        this.y = y;
        hitBox = new Rectangle(getX(),getY(),WIDTH,HEIGHT);

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
