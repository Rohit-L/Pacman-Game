import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class GameState {

    private String[][] configuration;
    private int numFood;
    private int numCapsules;
    private HashSet<Point> food = new HashSet<Point>();

    public GameState(String[][] boardArray) {
        this.configuration = boardArray;
        System.out.println("GameState Class");
        this.populateFood();
        System.out.println(this.food);
    }

    public void keyPressed(String direction) {
        System.out.println(direction + " Key Pressed");
    }

    public String toString() {
        for (int i = this.configuration[0].length - 1; i >= 0; i--) {
            for (int j = 0; j < this.configuration.length; j++) {
                System.out.print(this.configuration[j][i] + " ");
            }
            System.out.print("\n");
        }
        return "";
    }

    public void populateFood() {
        for (int i = 0; i < this.configuration.length; i++) {
            for (int j = 0; j < this.configuration[0].length; j++) {
                if (this.configuration[i][j].equals(".")) {
                    this.food.add(new Point(i, j));
                }
            }
        }
    }


}
