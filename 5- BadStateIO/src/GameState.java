import java.io.Serializable;

public abstract class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    protected boolean finished;
    public static int total = 9;

    protected static int totalPeople = 1;
    boolean gameOver;
    public static final int ROW = 3;
    public static final int COLUMN = 3;
    boolean won;

    protected GameState(){
        finished = false;
    }
    public abstract boolean isFinished();
    public abstract void update();
}
