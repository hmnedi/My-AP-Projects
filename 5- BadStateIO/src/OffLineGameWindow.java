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
    int[] healthbar = new int[4];
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

        g.clearRect(0, 0, 960, 540);
        g.drawImage(background,0,27,getWidth(),getHeight()-27,null);


        /*
         * this is for rendering the countries
         */
        for(int i = 0;i<4;i++){

            g.setColor(countries[i].getColor());




            // drawPolygon(int[] xPoints, int[] yPoints, int numPoint); // this could make shapes with points
            g.fillRect(countries[i].getX(), countries[i].getY(), Country.WIDTH, Country.HEIGHT);
            g.drawImage(countries[i].getImage(),countries[i].getX(),countries[i].getY(), Country.WIDTH, Country.HEIGHT,null);
            g.draw(countries[i].getHitBox());
            Font myFont = new Font("Courier", Font.BOLD,15);
            g.setFont(myFont);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(countries[i].getArmy()),countries[i].getX()+(Country.WIDTH)/2,countries[i].getY()+(Country.HEIGHT)/2);

        }

        /*
         * rendering the health bar
         */
        ArrayList<Color> colors = new ArrayList<>();
        ArrayList<Integer> aliveCountries = new ArrayList<>();
        for(int j=0; j<4; j++){
            if (!colors.contains(countries[j].getColor())){
                colors.add(countries[j].getColor());
                aliveCountries.add(j);
            }
        }

        for(int i=0; i<aliveCountries.size(); i++){
            int indexCountry = aliveCountries.get(i);
            int armyTeams = 0;
            for(int j=0; j<4; j++){
                if (countries[j].getColor() == countries[indexCountry].getColor()) {
                    armyTeams += countries[j].army;
                }
            }
            healthbar[i] = armyTeams;

            g.setColor(countries[indexCountry].getColor());
            switch (i) {
                case 0 -> g.fillRect(300-healthbar[2], 50, (4 * healthbar[i]), 10);
                case 1 -> g.fillRect(300-healthbar[2]+(4 * healthbar[0]), 50, (4 * healthbar[i]), 10);
                case 2 -> g.fillRect(300-healthbar[2]+(4 * healthbar[1])+(4 * healthbar[0]), 50, (4 * healthbar[i]), 10);
                case 3 -> g.fillRect(300-healthbar[2]+(4 * healthbar[2])+(4 * healthbar[1])+(4 * healthbar[0]), 50, ((4 * healthbar[i])),10);
            }
        }


        /*
        * draw line while dragging the mouse
         */
        g.setColor(Color.BLACK);
        g.drawLine(mouse.mousePressX, mouse.mousePressY, mouse.mouseX, mouse.mouseY);


        /*
        * shoot country
        */
        if (MyMouseListener.canShoot){
            for(int i=0; i<mouse.shooterID.size(); i++){
                countries[mouse.shooterID.get(i)].isShooting = true;
//                System.out.println(" sfs" + mouse.shooterID.get(i));
            }
            MyMouseListener.canShoot = false;
        }


        for(int i=0; i<4; i++){
            if ( countries[i].getBullets().size() > 0){
                for(NormalBullet bullet:countries[i].getBullets()) {
//                    System.out.println("haha f");
                    g.setColor(countries[i].getColor());

                    g.fillOval(bullet.getX(), bullet.getY(), NormalBullet.WIDTH, NormalBullet.HEIGHT);
                    g.setColor(Color.BLACK);
                    g.drawOval(bullet.getX(), bullet.getY(), NormalBullet.WIDTH, NormalBullet.HEIGHT);
                }
            }
        }



    }

}

