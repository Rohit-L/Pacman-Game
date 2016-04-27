import java.awt.*;

public class Agent {

    private String currentDirection;
    protected static int SCARED_TIME = 20;

    public Agent() {
        this.currentDirection = "Up";
    }

    public String chooseAction() {
        return this.currentDirection;
    }

    public Point getCurrentPosition(Board board) {
        return new Point();
    }

    public void setCurrentDirection(String direction) {
        this.currentDirection = direction;
    }
}
