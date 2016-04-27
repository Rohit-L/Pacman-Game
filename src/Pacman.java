import java.awt.*;

public class Pacman extends Agent {

    int eatenFood;
    Point currentPosition;

    public Pacman () {
        this.eatenFood = 0;
        this.currentPosition = null;
    }

    public Point getCurrentPosition() {
        return this.currentPosition;
    }

    public void setCurrentPosition(Point p) {
        this.currentPosition = p;
    }
}
