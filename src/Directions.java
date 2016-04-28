import java.awt.*;
import java.util.HashMap;

/**
 * Created by Monu on 4/26/16.
 */
public class Directions {

    private HashMap<String, Integer[]> dirToShift = new HashMap<String, Integer[]>();

    public Directions() {
        dirToShift.put("Left", new Integer[]{-1, 0});
        dirToShift.put("Right", new Integer[]{1, 0});
        dirToShift.put("Up", new Integer[]{0, 1});
        dirToShift.put("Down", new Integer[]{0, -1});
        dirToShift.put("Stop", new Integer[]{0, 0});

    }

    public void next(Point p, String direction) {
        p.translate(dirToShift.get(direction)[0], dirToShift.get(direction)[1]);
    }
}
