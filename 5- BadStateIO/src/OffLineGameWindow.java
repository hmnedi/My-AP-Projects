import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class OffLineGameWindow extends JFrame {
//    MyMouseListener mouse;
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

//        mouse = new MyMouseListener("offline");
//        addMouseListener(mouse);
//        addMouseMotionListener(mouse);
        setIconImage(ImageIO.read(new File("icon.png")));

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
            switch (i) {
                case 0 -> g.setColor(Color.BLUE);
                case 1 -> g.setColor(Color.ORANGE);
                case 2 -> g.setColor(Color.GREEN);
                case 3 -> g.setColor(Color.PINK);
            }
            g.fillRect(countries[i].getX(), countries[i].getY(),countries[i].WIDTH,countries[i].HEIGHT);
            g.drawImage(countries[i].getImage(),countries[i].getX(),countries[i].getY(),countries[i].WIDTH,countries[i].HEIGHT,null);
            g.draw(countries[i].getHitBox()); //todo: hide this and make it circle
            Font myFont = new Font("Courier", Font.BOLD,15);
            g.setFont(myFont);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(countries[i].getArmy()),countries[i].getX()+(countries[i].WIDTH)/2,countries[i].getY()+(countries[i].HEIGHT)/2);

        }

    }

}

