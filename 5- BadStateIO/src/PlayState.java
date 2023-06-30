import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class PlayState extends GameState{

    public static Country[] counteries = new Country[4];
    public long lastPeopleUpdate;
    public long lastMoveBullets;
    MyMouseListener mouse;

    public PlayState(MyMouseListener mouse) {
      super();
      this.mouse = mouse;
        counteries[0] = new Country(200, 100);
        counteries[1] = new Country(200, 300);
        counteries[2] = new Country(600, 100);
        counteries[2].army = 2;
        counteries[3] = new Country(600, 300);

        for(int i=0; i<4;i++){
            counteries[i].setImage("country" + (i+1) + ".png");
            switch (i) {
                case 0 -> counteries[i].setColor(Color.BLUE);
                case 1 -> counteries[i].setColor(Color.ORANGE);
                case 2 -> counteries[i].setColor(Color.GREEN);
                case 3 -> counteries[i].setColor(Color.PINK);
            }
        }
        lastPeopleUpdate = System.currentTimeMillis();
      gameOver = false;
      won = false;
    }
    public Country[] getCounteries() {
        return counteries;
    }

    public Country[] getCountries() {
        return counteries;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void update() {
        /*
         * to update the countries army
         */
        if(System.currentTimeMillis() - lastPeopleUpdate >= 3000){
            for (int i=0; i<4; i++){
                // normal country
                if (counteries[i] instanceof Country){
                    counteries[i].army += 1;
                }
            }
            lastPeopleUpdate = System.currentTimeMillis();
        }


        /*
        * shooting and collision
        */
        for(int i=0; i<4; i++){
            if (counteries[i].isShooting && counteries[i].army > 0){
                for(int j=0; j<counteries[i].army; j++){
                    if (System.currentTimeMillis() - counteries[i].LAST_SHOOT_TIME >= 100){
                        counteries[i].addBullet(new NormalBullet(counteries[i].getX()+(Country.WIDTH/2),counteries[i].getY()+(Country.HEIGHT/2)));
                        counteries[i].army -= 1;
                        counteries[i].LAST_SHOOT_TIME = System.currentTimeMillis();
                    }
                }
            }
            else if (counteries[i].army == 0) counteries[i].isShooting = false;


            /*
             * moving the bullets and check for collision
             */
            if(counteries[i].getBullets().size()>0){
                for(Iterator<NormalBullet> bulletIterator = counteries[i].getBullets().iterator();bulletIterator.hasNext();){
                    NormalBullet bullet = bulletIterator.next();

                    if (bullet.getTargetCountry() == -1) bullet.setTargetCountry(mouse.countryTarget);
                    if (i == 0 && bullet.getTargetCountry() == 2) bullet.moveRight();
                    else if (i == 2 && bullet.getTargetCountry() == 0) bullet.moveLeft();
                    else if (i == 0 && bullet.getTargetCountry() == 1) bullet.moveDown(0);
                    else if (i == 1 && bullet.getTargetCountry() == 0) bullet.moveUp(0);
                    else if (i == 3 && bullet.getTargetCountry() == 2) bullet.moveUp(0);
                    else if (i == 2 && bullet.getTargetCountry() == 3) bullet.moveDown(0);
                    else if (i == 3 && bullet.getTargetCountry() == 1) bullet.moveLeft();
                    else if (i == 1 && bullet.getTargetCountry() == 3) bullet.moveRight();
                    else if (i == 0 && bullet.getTargetCountry() == 3) {
                        bullet.moveRight();
                        bullet.moveDown(1);
                    }
                    else if (i == 3 && bullet.getTargetCountry() == 0) {
                        bullet.moveLeft();
                        bullet.moveUp(1);
                    }
                    else if (i == 1 && bullet.getTargetCountry() == 2) {
                        bullet.moveRight();
                        bullet.moveUp(1);
                    }
                    else if (i == 2 && bullet.getTargetCountry() == 1) {
                        bullet.moveLeft();
                        bullet.moveDown(2);
                    }


                    /*
                    * collision
                    */
                    for(int j=0; j<4; j++){
                        if (i != j && Physics.collision(bullet.getHitBox(), counteries[j].getHitBox())){
                            bullet.setTargetCountry(-1);
                            bulletIterator.remove();

                            if (counteries[j].getColor() == counteries[i].getColor()) counteries[j].army += 1;
                            else counteries[j].army -= 1;

                            if (counteries[j].army < 0){
                                counteries[j].army = Math.abs(counteries[j].army);
//                                alliedCountryIndex[j] = alliedCountryIndex[i];
                                counteries[j].setColor(counteries[i].getColor());
                            }
                        }
                    }

                }
            }
        }



        /*
        * win check
        */
        ArrayList<Color> colors = new ArrayList<>();
        for(int j=0; j<4; j++){
            if (!colors.contains(counteries[j].getColor())){
                colors.add(counteries[j].getColor());
            }
        }
        if (colors.size() == 1) {
            finished = true;
            gameOver = true;
//            won =true
        }
    }
}
