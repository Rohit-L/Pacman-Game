import java.awt.*;

public class Agent {

    String currentDirection;

    public Agent() {
        this.currentDirection = "Up";
    }

    public String chooseAction() {
        return this.currentDirection;
    }

    public Point getCurrentPosition() {
        return new Point();
    }

    public void setCurrentPosition(Point p) {

    }

    public void setCurrentDirection(String direction) {
        this.currentDirection = direction;
    }
}
