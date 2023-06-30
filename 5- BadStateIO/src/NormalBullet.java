import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class NormalBullet {
    private transient BufferedImage image;
    public static final int DAMAGE = 1;
    public static final int HEIGHT = 20;
    public static final int WIDTH = 20;
    private int x;
    private int y;
    private Rectangle hitBox;
    public static final int VELOCITY = 3;
    public NormalBullet(int x, int y) {
        try {
            image = ImageIO.read(new File("bullet.png"));
        }
        catch (Exception e){
            e.getStackTrace();
        }
        this.x = x;
        this.y = y;
        hitBox = new Rectangle(x,y,WIDTH,HEIGHT);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void moveRight(){
       x+=VELOCITY;
       hitBox.setLocation(getX(),getY());
    }

    public void moveLeft(){
        x-=VELOCITY;
        hitBox.setLocation(getX(),getY());
    }

    public void moveDown(int veloc){
        y+=VELOCITY-veloc;
        hitBox.setLocation(getX(),getY());
    }

    public void moveUp(int veloc){
        y-=VELOCITY-veloc;
        hitBox.setLocation(getX(),getY());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }
}
