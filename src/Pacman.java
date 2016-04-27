import java.awt.*;

/**
 * Created by Monu on 4/26/16.
 */
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
