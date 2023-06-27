import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class PlayState extends GameState{

    Country[] counteries = new Country[4];
    public long lastPeopleUpdate;
//    MyMouseListener mouse;

//    public PlayState(MyMouseListener mouse){
    public PlayState() {
      super();
//      this.mouse = mouse;
        counteries[0] = new Country(200, 100);
        counteries[1] = new Country(200, 300);
        counteries[2] = new Country(600, 100);
        counteries[3] = new Country(600, 300);
        for(int i=0; i<4;i++){
            counteries[i].setImage("country" + (i+1) + ".png");
            counteries[i].alliedCountryIndex[0] = i;
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


        /**
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
    }
}
