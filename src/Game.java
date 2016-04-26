import java.util.HashMap;

public class Game {

    private static HashMap<String, Integer> keyBindings = new HashMap<String, Integer>();

    public static void main(String[] args) {

        keyBindings.put("Left", 37);
        keyBindings.put("Right", 39);
        keyBindings.put("Up", 38);
        keyBindings.put("Down", 40);

        String[][] boardArray = new String[35][15];

        StdDraw.setCanvasSize(1300, 500);

        Board board = new Board(boardArray);


        board.readLayout("standardLayout.txt");

        String[][] boardLayout = board.getBoardArray();

        GameState gameState = new GameState(boardLayout);
        System.out.println(gameState);


        while(true) {


            /* Mouse Click */
            if (StdDraw.isKeyPressed(keyBindings.get("Left"))) {
                gameState.keyPressed("Left");
            }

            if (StdDraw.isKeyPressed(keyBindings.get("Right"))) {
                gameState.keyPressed("Right");
            }

            if (StdDraw.isKeyPressed(keyBindings.get("Up"))) {
                gameState.keyPressed("Up");
            }

            if (StdDraw.isKeyPressed(keyBindings.get("Down"))) {
                gameState.keyPressed("Down");
            }

            /* Refresh Board */
            board.drawBoard();
            StdDraw.show(50);

        }
    }
}