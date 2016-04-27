import java.awt.*;
import java.util.ArrayList;

public class Ghost extends Agent {

    private int scaredTimer;
    public final static int SCARED_TIME = 50;
    int id;


    public Ghost (int id, Point initialPosition) {
        super(initialPosition);
        this.scaredTimer = 0;
        this.id = id;
    }

    public void setScaredTimer(int scaredTime) {
        this.scaredTimer = scaredTime;
    }

    public int getScaredTimer() {
        return this.scaredTimer;
    }



    public void setCurrentPosition(Point p) {
        this.currentPosition = p;
    }

    //Subtract ghost timer
    public String chooseAction(GameState gameState) {

        Point pacmanPosition = gameState.getPacmanPosition();
        Point myPosition = this.getCurrentPosition();
        ArrayList<String> legalActions = gameState.getLegalActions(myPosition);

        if (this.scaredTimer > 0) {
            this.scaredTimer -= 1;
        }

        double minimumDistance = 999;
        double maxDistance = -999;
        String bestAction = null;

        for (String action: legalActions) {
            Point nextPosition = gameState.getNextPositionGivenAction(myPosition, action);

            int dx = nextPosition.x - pacmanPosition.x;
            int dy = nextPosition.y - pacmanPosition.y;

            double x2 = Math.pow(dx, 2);
            double y2 = Math.pow(dy, 2);

            double distanceToPacman = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
            if (this.scaredTimer > 0) {
                if (distanceToPacman >= maxDistance) {
                    maxDistance = distanceToPacman;
                    bestAction = action;
                }
            } else {
                if (distanceToPacman < minimumDistance) {
                    minimumDistance = distanceToPacman;
                    bestAction = action;
                }
            }
        }
        return bestAction;
    }

    public int getId() { return this.id; }
}
