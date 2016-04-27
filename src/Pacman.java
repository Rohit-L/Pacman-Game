import java.awt.*;

/**
 * Created by Monu on 4/26/16.
 */
public class Pacman extends Agent {

    int eatenFood;

    public Pacman () {
        this.eatenFood = 0;
    }

    public Point getCurrentPosition(Board board) {
        for (int x = 0; x < 35; x++) {
            for (int y = 0; y < 15; y++) {
                if (board.getBoardArray()[x][y].equals("P")) {
                    return new Point(x,y);
                }
            }
        }
        return null;
    }
}
