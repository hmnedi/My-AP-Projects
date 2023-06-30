import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class PlayState extends GameState{

    public static Country[] counteries = new Country[4];
    public static int[] alliedCountryIndex = new int[4];

    public long lastPeopleUpdate;
    MyMouseListener mouse;

    public PlayState(MyMouseListener mouse) {
      super();
      this.mouse = mouse;
        counteries[0] = new Country(200, 100);
        counteries[1] = new Country(200, 300);
        counteries[2] = new Country(600, 100);
        counteries[3] = new Country(600, 300);
        for(int i=0; i<4;i++){
            counteries[i].setImage("country" + (i+1) + ".png");
            alliedCountryIndex[i] = i;
        }
        lastPeopleUpdate = System.currentTimeMillis();
      gameOver = false;
      won = false;
    }

    public int[] getAlliedCountryIndex() {
        return alliedCountryIndex;
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
        if (counteries[0].isShooting && counteries[0].army > 0){
            for(int i=0; i<counteries[0].army; i++){
                if (System.currentTimeMillis() - counteries[0].LAST_SHOOT_TIME >= 200){
                    counteries[0].addBullet(new NormalBullet(counteries[0].getX()+(Country.WIDTH/2),counteries[0].getY()+(Country.HEIGHT/2)));
                    counteries[0].army -= 1;
                    counteries[0].LAST_SHOOT_TIME = System.currentTimeMillis();
                }
            }
        }
        //todo: loo[ countries
        if(counteries[0].getBullets().size()>0){
            for(Iterator<NormalBullet> bulletIterator = counteries[0].getBullets().iterator();bulletIterator.hasNext();){
                NormalBullet bullet = bulletIterator.next();
                bullet.move();
                if(bullet.getX()>960){
                    bulletIterator.remove();
                    counteries[0].isShooting = false; //cahge thisdigjsgfgsg
                }
            }
            // todo: add collision
        }

    }
}
