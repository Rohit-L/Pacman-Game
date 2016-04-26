import java.util.HashMap;

public class Game {

    private static HashMap<String, Integer> keyBindings = new HashMap<String, Integer>();
    public String[][] boardArray;

    public static void main(String[] args) {

        keyBindings.put("Left", 37);
        keyBindings.put("Right", 39);
        keyBindings.put("Up", 38);
        keyBindings.put("Down", 40);

        String[][] boardArray = new String[35][15];

        StdDraw.setCanvasSize(1300, 500);

        GameState game = new GameState(boardArray);
        Board board = new Board(boardArray);

        board.readLayout("standardLayout.txt");

        while(true) {


            /* Mouse Click */
            if (StdDraw.isKeyPressed(keyBindings.get("Left"))) {
                game.keyPressed("Left");
            }

            if (StdDraw.isKeyPressed(keyBindings.get("Right"))) {
                game.keyPressed("Right");
            }

            if (StdDraw.isKeyPressed(keyBindings.get("Up"))) {
                game.keyPressed("Up");
            }

            if (StdDraw.isKeyPressed(keyBindings.get("Down"))) {
                game.keyPressed("Down");
            }

            /* Refresh Board */
            board.drawBoard();
            StdDraw.show(50);

        }
    }
}