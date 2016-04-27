import java.awt.*;

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

    public String chooseAction(GameState gameState) {
        return this.currentDirection;
    }
}
