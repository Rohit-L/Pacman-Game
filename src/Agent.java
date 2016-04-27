import java.awt.*;

public class Agent {

    protected Point currentPosition;
    protected final Point initialPosition;

    public Agent(Point initialPosition) {
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
