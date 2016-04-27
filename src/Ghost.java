import java.awt.*;

public class Ghost extends Agent {

    private int scaredTimer;
    public final static int SCARED_TIME = 50;
    int id;
    Point currentPosition;
    Point initialPosition;


    public Ghost (int id, Point initialPosition) {
        this.scaredTimer = 0;
        this.id = id;
        this.currentPosition = initialPosition;
        this.initialPosition = initialPosition;
    }

    public void setScaredTimer(int scaredTime) {
        this.scaredTimer = scaredTime;
    }

    public int getScaredTimer() {
        return this.scaredTimer;
    }

    public Point getInitialPosition() { return this.initialPosition; }

    public Point getCurrentPosition() {
        return this.currentPosition;
    }

    public void setCurrentPosition(Point p) {
        this.currentPosition = p;
    }

    //Subtract ghost timer
    public String chooseAction(GameState gameState) {
        Point pacmanPosition = gameState.getPacmanPosition();
        if (this.scaredTimer > 0) {
            this.scaredTimer -= 1;
        }
        return "Up";
    }
}
