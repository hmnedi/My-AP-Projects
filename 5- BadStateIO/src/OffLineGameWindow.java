import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class OffLineGameWindow extends JFrame {
    MyMouseListener mouse;
    BufferStrategy bs;
    BufferedImage background;
    Canvas canvas;

    public OffLineGameWindow() throws IOException {
        setSize(new Dimension(960,540));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setTitle("offline");
        createBufferStrategy(2);
        bs = getBufferStrategy();
        loadImages();

        setIconImage(ImageIO.read(new File("icon.png")));

        mouse = new MyMouseListener("offline");
        addMouseListener(mouse);
        addMouseMotionListener(mouse);

    }

    private void loadImages() throws IOException {
        background = ImageIO.read(new File("watersea.jpg"));

    }

    public void render(PlayState state) {
        do {
            do {
                Graphics2D g = null;
                try {
                    // g is like a paint brush
                    g =(Graphics2D) bs.getDrawGraphics();
                    actualRender(state,g);

                } finally {
                    if( g != null ) {
                        g.dispose();

                    }
                }
            } while( bs.contentsRestored() );
            bs.show();
        } while( bs.contentsLost() );

    }

    private void actualRender(PlayState state,Graphics2D g){
        Country[] countries = state.getCountries();
        int[] alliedCountryIndex = state.getAlliedCountryIndex();

        g.clearRect(0, 0, 960, 540);
        g.drawImage(background,0,27,getWidth(),getHeight()-27,null);


        /*
         * this is for rendering the countries
         */
        for(int i = 0;i<4;i++){
            switch (alliedCountryIndex[i]) {
                case 0 -> g.setColor(Color.BLUE); // 300x 185y
                case 1 -> g.setColor(Color.ORANGE); // 300x 385y
                case 2 -> g.setColor(Color.GREEN); // 700x 185y
                case 3 -> g.setColor(Color.PINK); // 700x 385
            }
            // drawPolygon(int[] xPoints, int[] yPoints, int numPoint); // this could make shapes with points
            g.fillRect(countries[i].getX(), countries[i].getY(),countries[i].WIDTH,countries[i].HEIGHT);
            g.drawImage(countries[i].getImage(),countries[i].getX(),countries[i].getY(),countries[i].WIDTH,countries[i].HEIGHT,null);
            g.draw(countries[i].getHitBox()); //todo: hide this and make it circle
            Font myFont = new Font("Courier", Font.BOLD,15);
            g.setFont(myFont);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(countries[i].getArmy()),countries[i].getX()+(countries[i].WIDTH)/2,countries[i].getY()+(countries[i].HEIGHT)/2);

        }

        /*
        * rendering the health bar
        */
        g.setColor(Color.BLUE);
        g.fillRect(300-countries[2].army, 50,(4*countries[0].army),10);
        g.setColor(Color.ORANGE);
        g.fillRect(300-countries[2].army+(4*countries[0].army), 50,(4*countries[1].army),10);
        g.setColor(Color.GREEN);
        g.fillRect(300-countries[2].army+(4*countries[1].army)+(4*countries[0].army), 50,(4*countries[2].army),10);
        g.setColor(Color.PINK);
        g.fillRect(300-countries[2].army+(4*countries[2].army)+(4*countries[1].army)+(4*countries[0].army), 50,(4*countries[3].army),10);

        /*
        * draw line while dragging the mouse
         */
        g.setColor(Color.BLACK);
        g.drawLine(mouse.mousePressX, mouse.mousePressY, mouse.mouseX, mouse.mouseY);


        /*
        * shoot coutnry
        */
        //todo: age can shoot bood bebin kodom country hastesh bad shooting oon ro faal kon bad canshoot ro khamoosh
        if (MyMouseListener.canShoot){
            countries[0].isShooting = true;
//            g.fillOval(mouse.mousePressX, mouse.mousePressY, 20, 20);
//            g.setColor(Color.BLACK);
//            g.drawOval(mouse.mousePressX, mouse.mousePressY, 20, 20);
//            countries[alliedCountryIndex[mouse.shooterID.get(0)]].shoot(countries[mouse.countryTarget].getX(), countries[mouse.countryTarget].getY());

            // todo: loop for shooterID
            for(NormalBullet bullet:countries[mouse.shooterID.get(0)].getBullets()) {
//                g.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), NormalBullet.WIDTH, NormalBullet.HEIGHT, null);
                switch (alliedCountryIndex[mouse.shooterID.get(0)]) {
                    case 0 -> g.setColor(Color.BLUE); // 300x 185y
                    case 1 -> g.setColor(Color.ORANGE); // 300x 385y
                    case 2 -> g.setColor(Color.GREEN); // 700x 185y
                    case 3 -> g.setColor(Color.PINK); // 700x 385
                }
                g.fillOval(bullet.getX(), bullet.getY(), NormalBullet.WIDTH, NormalBullet.HEIGHT);
//                g.fillOval(mouse.mousePressX, mouse.mousePressY, 20, 20);
                g.setColor(Color.BLACK);
                g.drawOval(bullet.getX(), bullet.getY(), NormalBullet.WIDTH, NormalBullet.HEIGHT);
//                g.drawOval(mouse.mousePressX, mouse.mousePressY, 20, 20);
            }
        }

    }

}

