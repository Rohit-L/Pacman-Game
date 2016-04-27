import java.awt.*;
import java.util.ArrayList;

public class Pacman extends Agent {

    int eatenFood;
    String currentDirection;
    String futureDirection;

    public Pacman (Point initialPosition) {
        super(initialPosition);
        this.eatenFood = 0;
        this.currentDirection = "Up";
        this.futureDirection = "Up";
    }

    public void setCurrentDirection(String direction) {
        this.currentDirection = direction;
    }

    public void setFutureDirection(String direction) {
        this.futureDirection = direction;
    }

    public ArrayList<String> chooseAction(GameState gameState) {
        ArrayList<String> actions = new ArrayList<String>();
        actions.add(this.futureDirection);
        actions.add(this.currentDirection);
        return actions;
    }

}
