import java.awt.*;

public class Pacman extends Agent {

    int eatenFood;
    Point currentPosition;
    Point initialPosition;

    public Pacman (Point initialPosition) {
        this.eatenFood = 0;
        this.currentPosition = initialPosition;
        this.initialPosition = initialPosition;
    }

    public Point getInitialPosition() { return this.initialPosition; }

    public Point getCurrentPosition() {
        return this.currentPosition;
    }

    public void setCurrentPosition(Point p) {
        this.currentPosition = p;
    }
}
