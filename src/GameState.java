import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class GameState {

    private String[][] configuration;

    public GameState(String[][] boardArray) {
        this.configuration = boardArray;
        System.out.println("GameState Class");

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

}
